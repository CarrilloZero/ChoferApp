package busesapp.choferapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by daniel on 03/11/2016.
 */
public class CambiarEstado  {

    public void cambiarEstado(String correo, int estado, String busid){
        try{
            String corr = correo;
            int est = estado;
            String busId = busid;

            BackGround b = new BackGround();
            b.execute(corr,String.valueOf(est),busId);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class BackGround extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String correo = params[0];
            String estado = params[1];
            String busid = params[2];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://www.busearch.pe.hu/cambiarestado.php");
                String urlParams = "Correo="+correo+"&Estado="+estado+"&BusId="+busid;
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
            String correo;
            int estado;
            int busid;
            try {

                JSONObject root = new JSONObject(s);
                correo= root.getString("Correo");
                estado= root.getInt("Estado");
                busid= root.getInt("BusId");

                if (correo.equals("") || correo.equals(null)) {


                }else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
