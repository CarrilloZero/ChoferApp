package busesapp.choferapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ComentarioActivity extends AppCompatActivity {

    private EditText getAlerta;
    private Button btnEnviarAlerta;
    private Context context;
    private String hora,ciudadOrigen,ciudadDestino,correo;
    private int busid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        hora = getIntent().getStringExtra("Hora");
        ciudadOrigen = getIntent().getStringExtra("CiudadOrigen");
        ciudadDestino = getIntent().getStringExtra("CiudadDestino");
        busid = getIntent().getExtras().getInt("BusId");
        correo = getIntent().getStringExtra("Correo");

        btnEnviarAlerta = (Button)findViewById(R.id.btnEnviarAlerta);
        btnEnviarAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAlerta = (EditText) findViewById(R.id.etAlerta);
                String alerta = getAlerta.getText().toString();
                enviarAlerta(alerta,hora,ciudadOrigen,ciudadDestino,busid);
                Intent intent = new Intent(ComentarioActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {
    }

    public void enviarAlerta (String alerta, String hora, String ciudadOrigen, String ciudadDestino,int busId){
        try{
            String corr = correo;
            String alert = alerta;
            String getHora = hora;
            String getCiudadOrigen = ciudadOrigen;
            String getCiudadDestino = ciudadDestino;
            int getBusId = busId;

            BackGround b = new BackGround();
            b.execute(corr,alert,getHora,getCiudadOrigen,getCiudadDestino,String.valueOf(getBusId));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class BackGround extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String correo = params[0];
            String alerta = params[1];
            String hora = params[2];
            String ciuOri = params[3];
            String ciuDes = params[4];
            String busid = params[5];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://www.busearch.pe.hu/alerta.php");
                String urlParams = "Correo="+correo+"&Alerta="+alerta+"&Hora="+hora+"&CiudadOrigen="+ciuOri+"&CiudadDestino="+ciuDes+"&BusId="+busid;
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
            String alerta;
            int estado;
            int busid;
            try {

                JSONObject root = new JSONObject(s);
                alerta= root.getString("Alerta");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
