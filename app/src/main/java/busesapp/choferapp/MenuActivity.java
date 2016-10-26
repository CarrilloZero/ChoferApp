package busesapp.choferapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Pattern;

import busesapp.choferapp.beans.Chofer;
import busesapp.choferapp.beans.ChoferDAO;
import busesapp.choferapp.beans.HaceDAO;
import busesapp.choferapp.beans.Notificacion;
import busesapp.choferapp.beans.NotificacionDAO;
import busesapp.choferapp.beans.ParaderoDAO;
import busesapp.choferapp.beans.Usuario;
import busesapp.choferapp.beans.UsuarioDAO;

public class MenuActivity extends AppCompatActivity {

    private ImageButton fabComentario;
    private ListView listParadero, countParadero, listAlerta;
    TextView longi, lati;
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        fabComentario = (ImageButton) findViewById(R.id.fabComentario);
        fabComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ComentarioActivity.class);
                startActivity(intent);
            }
        });



        GPSTracker gps = new GPSTracker(this);
        gps.getLongitude();
        gps.getLatitude();

        longi = (TextView) findViewById(R.id.txtLong);
        lati = (TextView) findViewById(R.id.txtLat);
        longi.setText("Longitud: "+gps.getLongitude());
        lati.setText("Latitud: "+gps.getLatitude());

        ParaderoDAO paradero = new ParaderoDAO(this);

        ArrayList<String> paraderos = paradero.listadoNombres();


        listParadero = (ListView)findViewById(R.id.lwParadero);
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paraderos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listParadero.setAdapter(adapter);

        listAlerta = (ListView)findViewById(R.id.listAlerta);

        NotificacionDAO noti = new NotificacionDAO(this);
        String correo="";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                correo = (account.name).toString();

            }
        }
        correo="daniel.carrillo05@hotmail.com";


        UsuarioDAO usu = new UsuarioDAO(this);

        Usuario usuario = usu.buscar(correo);
        int id = usuario.getId();

        ArrayList<Notificacion> notis = noti.listado();
        int count= notis.size();
        int c = 0;
        ArrayList<String>comentariosHora = new ArrayList<String>();
        while(c<count) {
            Notificacion notificacion = notis.get(0);
            String alerta = notificacion.getHora()+" - "+notificacion.getComentario();
            comentariosHora.add(alerta);

            c++;
        }
        ArrayAdapter<String> adapterAlerts= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comentariosHora);
        listParadero.setAdapter(adapterAlerts);

    }




    public void onClickFinish(View v){

        String correo="";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                correo = (account.name).toString();

            }
        }
        correo="daniel.carrillo05@hotmail.com";


        UsuarioDAO usu = new UsuarioDAO(this);
        Usuario usuario = usu.buscar(correo);

        int user = usuario.getId();


        ChoferDAO chofer = new ChoferDAO(this);
        Chofer conductor = chofer.buscar(user);
        int idBus = conductor.getBus_Id();
        int idChofer = conductor.getId();
        int idEmpresa = conductor.getEmpresa_Id();
        String contraseña = conductor.getContraseña();

        HaceDAO hace = new HaceDAO(this);
        try {
            hace.eliminar(idBus);
            chofer.modificar(idChofer, user, idBus, idEmpresa, 0,contraseña);
            Toast toast = Toast.makeText(this, "Viaje terminado con éxito!", Toast.LENGTH_LONG);
            toast.show();
        } catch(Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "ERROR", Toast.LENGTH_LONG);
            toast.show();
        }

        Button btnTerminar = (Button)findViewById(R.id.btnFinish);
        btnTerminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MenuActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });

        Button btnMapa = (Button)findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });



    }


    @Override
    public void onBackPressed() {
    }


    class Sender extends AsyncTask<String, Object, String> {

        @Override
        protected String doInBackground(String... strings) {
            String text = "";
            BufferedReader reader = null;


            // Send data
            try {

                // Defined URL  where to send data
                URL url = new URL("http://192.168.1.7:8080/Usuario/Actualizar?longitud=" + strings[0] + "&latitud=" + strings[1]+ "&correo=" + strings[2]);

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                text = sb.toString();
            } catch (Exception ex) {
                String mensaje = ex.getMessage();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }

            // Show response on activity
            return text;

            //Snackbar.make(view,text, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    public void onLocationChanged(Location location) {
        GPSTracker gps = new GPSTracker(this);
        double lon = gps.getLongitude();
        double lat = gps.getLatitude();

        String correo="";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                correo = (account.name).toString();

            }
        }
        UsuarioDAO usuario = new UsuarioDAO((this));
        Usuario usu = usuario.buscar(correo);
        int usu_id = usu.getId();
        ChoferDAO chof = new ChoferDAO(this);
        Chofer chofer = chof.buscar(usu_id);
        int online = chofer.getOnline();


        LocationManager locationManager=null;
        try {

            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (SecurityException se) {
        }

        if (location != null) {
            new Sender().execute(String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()), correo);
        }




        usuario.modificar(usu.getId(),lon,lat,usu.getCorreo());


        longi = (TextView) findViewById(R.id.txtLong);
        lati = (TextView) findViewById(R.id.txtLat);
        longi.setText("Longitud: "+gps.getLongitude());
        lati.setText("Latitud: "+gps.getLatitude());


    }

    public void onClickActualizar(View v){



    }


}
