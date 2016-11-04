package busesapp.choferapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import busesapp.choferapp.beans.Ciudad;
import busesapp.choferapp.beans.CiudadDAO;

public class PrincipalActivity extends AppCompatActivity {
    private Spinner spnOrigen,spnDestino;
    private Button btnEnviar,btnDesconectar;
    private EditText etPatente;
    private TextView tvCorreo;
    private String correo;
    private Context ctx=this;
    private ProgressDialog pdialog = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        correo = getIntent().getStringExtra("Correo");
        tvCorreo = (TextView) findViewById(R.id.tvCorreo);
        tvCorreo.setText(correo);

        etPatente = (EditText) findViewById(R.id.etPatente);

        //DataHelper dataHelper = new DataHelper(this);
        CiudadDAO ciudad = new CiudadDAO(this);

        ArrayList<String> listaCiudad = ciudad.listadoCiudad();
        spnOrigen = (Spinner) findViewById(R.id.spnOrigen);
        spnDestino = (Spinner) findViewById(R.id.spnDestino);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCiudad);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOrigen.setAdapter(adapter);
        spnDestino.setAdapter(adapter);
        spnDestino.setSelection(1);

        btnEnviar = (Button) findViewById(R.id.btnEnviarAlerta);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getPatente;
                String ciudadOrigen = "";
                String ciudadDestino = "";

                try {
                    ciudadOrigen = spnOrigen.getSelectedItem().toString();
                    ciudadDestino = spnDestino.getSelectedItem().toString();

                    if(ciudadOrigen.equals(ciudadDestino)){
                        Toast.makeText(ctx, "Ingrese ciudades válidas", Toast.LENGTH_LONG);
                    }else
                    {
                        getPatente = etPatente.getText().toString();
                        verificarPatente(getPatente);

                    }

                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(ctx,"Error en el ingreso de datos", Toast.LENGTH_LONG);
                }
            }
        });

        btnDesconectar = (Button) findViewById(R.id.btnDesconectar);
        btnDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void verificarPatente(String patente){
        try{
            String pat = patente;

            if(pat.equals("")||pat.equals(null))
            {
                Toast.makeText(ctx, "Inserte campos validos", Toast.LENGTH_LONG).show();
            }
            else
            {
                BackGround b = new BackGround();
                b.execute(pat);
                pdialog = ProgressDialog.show(this, "", "Verificando Datos...", true);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class BackGround extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String pat = params[0];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://www.busearch.pe.hu/verificarpatente.php");
                String urlParams = "Patente="+pat;
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
            String pat;
            pdialog.dismiss();
            try {

                JSONObject root = new JSONObject(s);
                pat= root.getString("BusId");
                int busid = Integer.parseInt(pat);

                if (pat.equals("") || pat.equals(null)) {

                    Toast.makeText(ctx, "Patente no valida", Toast.LENGTH_LONG).show();

                }else {
                    String getPatente = etPatente.getText().toString();
                    String ciudadOrigen = spnOrigen.getSelectedItem().toString();
                    String ciudadDestino = spnDestino.getSelectedItem().toString();
                    CiudadDAO ciudadQ = new CiudadDAO(ctx);
                    Ciudad ciuori = ciudadQ.buscarNombre(ciudadOrigen);
                    Ciudad ciudes = ciudadQ.buscarNombre(ciudadDestino);

                    String fecha = obtenerFecha();
                    String dia = obtenerDia();
                    String hora = obtenerHora();
                    String ciuOrigen = ciuori.getNombre();
                    String ciuDestino = ciudes.getNombre();

                    Intent intent = new Intent(ctx, MenuActivity.class);
                    intent.putExtra("Fecha",fecha);
                    intent.putExtra("Dia",dia);
                    intent.putExtra("Hora",hora);
                    intent.putExtra("CiudadOrigen",ciuOrigen);
                    intent.putExtra("CiudadDestino",ciuDestino);
                    intent.putExtra("Correo",correo);
                    intent.putExtra("BusId",busid);
                    startActivity(intent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ctx, "Patente no valida", Toast.LENGTH_LONG).show();
            }

        }
    }

    public static String obtenerFecha(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String obtenerDia(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("E");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date
            String dia = traductorDia(currentTimeStamp);
            return dia;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String obtenerHora(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String traductorDia(String day){
        String dia = null;

        if(day.equals("lun.")){
            dia = "Lunes";
        }
        if(day.equals("mar.")){
            dia = "Martes";
        }
        if(day.equals("mié.")){
            dia = "Miercoles";
        }
        if(day.equals("jue.")){
            dia = "Jueves";
        }
        if(day.equals("vie.")){
            dia = "Viernes";
        }
        if(day.equals("sab.")){
            dia = "Sabado";
        }
        if(day.equals("dom.")){
            dia = "Domingo";
        }

        return dia;

    }


}
