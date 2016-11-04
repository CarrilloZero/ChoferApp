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
 * Created by daniel on 02/11/2016.
 */
public class CrearHace  {

    public void crearHace(String hora, String ciudadOrigen, String ciudadDestino,int busId){
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
                URL url = new URL("http://www.busearch.pe.hu/crearhace.php");
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
            String hora,ciuOri,ciuDes,busId,haceid;
            try {

                JSONObject root = new JSONObject(s);
                haceid = root.getString("HaceId");

                if (haceid.equals(""))
                {

                }else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(ctx, "Datos no validos", Toast.LENGTH_LONG).show();
            }

        }
    }
}
