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
public class EnviarCoordenadas  {

    public void enviarCoordenadas(double latitud, double longitud, int haceid){
        try{
            String lat = String.valueOf(latitud);
            String lon = String.valueOf(longitud);
            String haceId = String.valueOf(haceid);

            BackGround b = new BackGround();
            b.execute(lat,lon,haceId);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class BackGround extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String lat = params[0];
            String lon = params[1];
            String hace = params[2];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://www.busearch.pe.hu/enviarcoordenadas.php");
                String urlParams = "Latitud="+lat+"&Longitud="+lon+"&HaceId="+hace;
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
            String lat,lon,hace;
            try {

                JSONObject root = new JSONObject(s);
                lat= root.getString("Latitud");
                lon= root.getString("Longitud");
                hace= root.getString("HaceId");

                if (lat.equals("") || lat.equals(null)) {

                    //Toast.makeText(ctx, "Patente no valida", Toast.LENGTH_LONG).show();

                }else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(ctx, "Patente no valida", Toast.LENGTH_LONG).show();
            }

        }
    }
}
