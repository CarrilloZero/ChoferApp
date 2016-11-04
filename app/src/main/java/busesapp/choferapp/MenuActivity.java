package busesapp.choferapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MenuActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {

    private ImageButton fabComentario;
    private GoogleMap mMap;
    double latitud, longitud;
    Marker marcador;
    private Context ctx=this;
    private ProgressDialog pdialog = null;
    private String dia,fecha,hora,ciudadOrigen,ciudadDestino,correo;
    private int busId,haceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dia = getIntent().getStringExtra("Dia");
        fecha = getIntent().getStringExtra("Fecha");
        hora = getIntent().getStringExtra("Hora");
        ciudadOrigen = getIntent().getStringExtra("CiudadOrigen");
        ciudadDestino = getIntent().getStringExtra("CiudadDestino");
        busId = getIntent().getExtras().getInt("BusId");
        correo = getIntent().getStringExtra("Correo");

        CrearHace hace = new CrearHace();
        hace.crearHace(hora,ciudadOrigen,ciudadDestino,busId);

        CambiarEstado estado = new CambiarEstado();
        estado.cambiarEstado(correo,1,String.valueOf(busId));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button btnTerminar = (Button)findViewById(R.id.btnTerminar);
        btnTerminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CambiarEstado ter = new CambiarEstado();
                ter.cambiarEstado(correo,0,String.valueOf(busId));
                Intent intent = new Intent(MenuActivity.this, PrincipalActivity.class);
                intent.putExtra("Correo",correo);
                startActivity(intent);
            }
        });

        Button btnAlerta = (Button)findViewById(R.id.btnAlerta);
        btnAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ComentarioActivity.class);
                intent.putExtra("Hora",hora);
                intent.putExtra("CiudadOrigen",ciudadOrigen);
                intent.putExtra("CiudadDestino",ciudadDestino);
                intent.putExtra("BusId",busId);
                intent.putExtra("Correo",correo);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        miUbicacion();
    }

    private void miUbicacion(){
        try{
            LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location loc = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            actualizarUbicacion(loc);
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,locListener);
        }catch (SecurityException ex){
            ex.printStackTrace();
        }
    }

    private void actualizarUbicacion (Location location){
        if(location != null){
            try{
                latitud = location.getLatitude();
                longitud = location.getLongitude();
                buscarHaceId(hora,ciudadOrigen,ciudadDestino,busId);
                EnviarCoordenadas ec = new EnviarCoordenadas();
                ec.enviarCoordenadas(latitud,longitud,haceid);
                agregarMarcador(latitud, longitud);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void agregarMarcador (double latitud, double longitud){
        LatLng coordenadas = new LatLng(latitud,longitud);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas,16);
        if(marcador!= null){
            marcador.remove();
        }
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(""));
        mMap.animateCamera(miUbicacion);
    }

    private void agregarTerminal(){
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        helper.openDataBase();
        Cursor cursorTerrminales = db.rawQuery("SELECT Latitud,Longitud,Nombre FROM terminal", null);
        if (cursorTerrminales.moveToFirst()) {
            do {
                LatLng terminal = new LatLng(cursorTerrminales.getDouble(0), cursorTerrminales.getDouble(1));
                String nombre = cursorTerrminales.getString(2);
                mMap.addMarker(new MarkerOptions()
                        .position(terminal)
                        .title(nombre)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_terminal)));
            } while (cursorTerrminales.moveToNext());
            cursorTerrminales.close();
        }
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void buscarHaceId(String hora, String ciudadOrigen, String ciudadDestino,int busId){
        try{
            String getHora = hora;
            String getCiudadOrigen = ciudadOrigen;
            String getCiudadDestino = ciudadDestino;
            int getBusId = busId;

            if(getHora.equals("")||getCiudadOrigen.equals("")||getCiudadDestino.equals(""))
            {
                //Toast.makeText(ctx, "Inserte campos validos", Toast.LENGTH_LONG).show();
            }
            else
            {
                BackGround b = new BackGround();
                b.execute(getHora,getCiudadOrigen,getCiudadDestino,String.valueOf(getBusId));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

    }

    class BackGround extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String hora = params[0];
            String ciuOri = params[1];
            String ciuDes = params[2];
            String busId = params[3];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://www.busearch.pe.hu/buscarhaceid.php");
                String urlParams = "Hora="+hora+"&CiudadOrigen="+ciuOri+"&CiudadDestino="+ciuDes+"&BusId="+busId;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            String hora,ciuOri,ciuDes,busId,haid;
            try {

                JSONObject root = new JSONObject(s);
                haid = root.getString("HaceId");
                haceid = Integer.parseInt(haid);

            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(ctx, "Datos no validos", Toast.LENGTH_LONG).show();
            }

        }
    }

}
