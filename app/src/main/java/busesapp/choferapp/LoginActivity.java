package busesapp.choferapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etContraseña = (EditText) findViewById(R.id.etContraseña);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataHelper helper = new DataHelper(LoginActivity.this);
                SQLiteDatabase db = helper.getReadableDatabase();
                helper.openDataBase();
                main_login();
/*
                String getCorreo = etCorreo.getText().toString();
                String getContraseña = etContraseña.getText().toString();

                Cursor correo = db.rawQuery("SELECT u.Correo,c.Contraseña FROM usuario AS u INNER JOIN chofer AS c " +
                        "ON u.Id=c.Usuario_Id WHERE u.Correo='"+getCorreo+"' AND c.Contraseña='"+getContraseña+"';",null);
                if (correo.moveToFirst()) {
                    do {
                        Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                        startActivity(intent);;
                    } while (correo.moveToNext());
                    correo.close();
                }else{
                    Toast.makeText(LoginActivity.this,"Datos no validos",Toast.LENGTH_LONG).show();
                }*/
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

            String correo=null,contraseña=null;
            pdialog.dismiss();
            try {

                JSONArray object = new JSONArray(s);

                for (int i = 0; i < object.length(); i++)
                {
                    JSONObject Jasonobject = object.getJSONObject(i);
                    correo= Jasonobject.getString("Correo");
                    contraseña= Jasonobject.getString("Contraseña");
                }

                if (correo.equals("") || contraseña.equals("") || correo.equals(null) || contraseña.equals(null)) {

                    Toast.makeText(ctx, "Correo y/o Contraseña incorrectos post 1", Toast.LENGTH_LONG).show();

                }else {
                    Intent i = new Intent(ctx, PrincipalActivity.class);
                    //i.putExtra("Nomb", NOMBRE);
                    //i.putExtra("rut",RUT);
                    startActivity(i);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ctx, "Correo y/o Contraseña incorrectos post 2", Toast.LENGTH_LONG).show();
            }

        }
    }
}
    /*
    public class LoginActivity extends Activity {

    EditText rut, contraseña;
    String Rut, Contraseña;
    Context ctx=this;
    ProgressDialog pdialog = null;
    String NOMBRE=null;
    Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rut = (EditText) findViewById(R.id.main_name);
        contraseña = (EditText) findViewById(R.id.main_password);
        context = this;
    }

    // public void main_register(View v){
    //   startActivity(new Intent(this,Register.class));
    //}

    public void main_login(View v){
        Rut = rut.getText().toString();
        Contraseña = contraseña.getText().toString();
        BackGround b = new BackGround();
        if(Rut.equals("")||Contraseña.equals(""))
        {
            Toast.makeText(ctx, "Inserte campos validos", Toast.LENGTH_LONG).show();

        }
        else
        {
            b.execute(Rut, Contraseña);
            pdialog = ProgressDialog.show(context, "", "CONECTANDO...", true);
        }


    }

    class BackGround extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String rut1 = params[0];
            String contraseña1 = params[1];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://www.intramovil.hol.es/login.php");
                String urlParams = "name="+rut1+"&password="+contraseña1;
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

            String RUT=null,CONTRASEÑA=null;
            pdialog.dismiss();
            try {
                    JSONObject root = new JSONObject(s);
                    JSONObject user_data = root.getJSONObject("user_data");
                    NOMBRE= user_data.getString("nombre");
                    RUT= user_data.getString("rut");
                    CONTRASEÑA= user_data.getString("contraseña");

                if(RUT.equals("")||CONTRASEÑA.equals("")||RUT.equals(null)||CONTRASEÑA.equals(null))
                {

                    Toast.makeText(ctx, "Rut y contraseña incorrectos ", Toast.LENGTH_LONG).show();

                }
                else
                {

                    Intent i = new Intent(ctx, Menu.class);
                    i.putExtra("Nomb", NOMBRE);
                    i.putExtra("rut",RUT);
                    startActivity(i);
                }


                    //CORREO= user_data.getString("correo");
                    //CARRERA_ID= user_data.getString("carrera_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                Toast.makeText(ctx, "Rut y contraseña incorrectos ", Toast.LENGTH_LONG).show();
                }







        }
    }

}
     */

