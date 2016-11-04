package busesapp.choferapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private Button btnIngresar;
    private EditText etCorreo,etContraseña;
    private Context ctx=this;
    private ProgressDialog pdialog = null;
    private int PERMISSION_CODE_1=23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestpermisions();
            }
        }

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etContraseña = (EditText) findViewById(R.id.etContraseña);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                main_login();
            }
        });

    }

    public void main_login(){
        try{
            String getCorreo = etCorreo.getText().toString();
            String getContraseña = etContraseña.getText().toString();

            if(getCorreo.equals("")||getContraseña.equals(""))
            {
                Toast.makeText(ctx, "Inserte campos validos", Toast.LENGTH_LONG).show();

            }
            else
            {
                BackGround b = new BackGround();
                b.execute(getCorreo, getContraseña);
                pdialog = ProgressDialog.show(this, "", "Conectando...", true);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class BackGround extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String correo = params[0];
            String contraseña = params[1];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://www.busearch.pe.hu/login.php");
                String urlParams = "Correo="+correo+"&Contraseña="+contraseña;
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

            String correo,contraseña;
            pdialog.dismiss();
            try {

                JSONObject root = new JSONObject(s);
                correo= root.getString("Correo");
                contraseña= root.getString("Contraseña");

                if (correo.equals("") || contraseña.equals("") || correo.equals(null) || contraseña.equals(null)) {

                    Toast.makeText(ctx, "Correo y/o Contraseña incorrectos", Toast.LENGTH_LONG).show();

                }else {
                    Intent i = new Intent(ctx, PrincipalActivity.class);
                    i.putExtra("Correo", correo);
                    startActivity(i);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ctx, "Correo y/o Contraseña incorrectos", Toast.LENGTH_LONG).show();
            }

        }
    }
    public void requestpermisions() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE_1);

    }
}


