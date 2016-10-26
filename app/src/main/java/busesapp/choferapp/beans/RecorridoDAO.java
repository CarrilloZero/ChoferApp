package busesapp.choferapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;



import java.util.ArrayList;

import busesapp.choferapp.DataHelper;

/**
 * Created by daniel on 11/07/2016.
 */
public class RecorridoDAO extends DataHelper {

    private String tabla = "recorrido";

    public RecorridoDAO(Context ctx){
        super(ctx);
    }

    public Recorrido buscar(int id){
        Recorrido dev = null;
        try{
            openDataBase();
            String[] campos = {"Id","HorarioOrigen","HorarioDestino","Ciudad_Origen_Id","Ciudad_Destino_Id", "Día"};
            Cursor c = db.query(tabla, campos, "Id=" + id,null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Recorrido(id,c.getString(1),c.getString(2),c.getInt(3),c.getInt(4), c.getString(5));
            c.close();
            close();
        }catch(Exception ex){
            close();
            return null;
        }

        return dev;
    }

    public Recorrido buscarDosCiudades(int ciudad_origen, int ciudad_destino){
        Recorrido dev = null;
        try{
            openDataBase();
            String[] campos = {"Id","HorarioOrigen","HorarioDestino","Ciudad_Origen_Id","Ciudad_Destino_Id", "Día"};
            Cursor c = db.query(tabla, campos, "Ciudad_Origen_Id=" +ciudad_origen+" AND Ciudad_Destino_Id="+ciudad_destino+";",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Recorrido(c.getInt(0),c.getString(1),c.getString(2),ciudad_origen,ciudad_destino, c.getString(5));
            c.close();
            close();
        }catch(Exception ex){
            close();
            return null;
        }

        return dev;
    }

    public ArrayList<Recorrido> listado(){
        ArrayList<Recorrido> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Recorrido>();
            String[] campos = {"Id","HorarioOrigen","HorarioDestino","Ciudad_Origen_Id","Ciudad_Destino_Id", "Día"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id=c.getInt(0);
                    String horaOri = c.getString(1);
                    String horaDes = c.getString(2);
                    int ciuOri = c.getInt(3);
                    int ciuDes = c.getInt(4);
                    String day= c.getString(5);

                    Recorrido recorrido = new Recorrido(id,horaOri,horaDes,ciuOri,ciuDes,day);
                    dev.add(recorrido);

                } while(c.moveToNext());
            }
            c.close();
            close();

        }catch(Exception ex){
            close();
            return null;
        }

        return dev;
    }

    public ArrayList<String> listadoNombres(String ciudadOrigen,String ciudadDestino, String horaOrigen,String horaDestino, String dia){
        ArrayList<Recorrido> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Recorrido>();
            String[] campos = {"Id","HorarioOrigen","HorarioDestino","Ciudad_Origen_Id","Ciudad_Destino_Id"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id=c.getInt(0);
                    String horaOri = c.getString(1);
                    String horaDes = c.getString(2);
                    int ciuOri = c.getInt(3);
                    int ciuDes = c.getInt(4);
                    String day= c.getString(5);

                    Recorrido recorrido = new Recorrido(id,horaOri,horaDes,ciuOri,ciuDes,day);
                    dev.add(recorrido);

                } while(c.moveToNext());
            }
            c.close();
            close();

        }catch(Exception ex){
            close();
            return null;
        }

        return null;
    }

    public boolean eliminar(int id) {
        try{
            openDataBase();
            db.delete(tabla, "Id="+id, null);
            close();
        }catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean modificar(int id,String horaOrigen,String horaDestino, String ciudadOrigen,String ciudadDestino, String dia){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();

            valores.put("HorarioOrigen", horaOrigen);
            valores.put("HorarioDestino", horaDestino);
            valores.put("Ciudad_Origen_Id", ciudadOrigen);
            valores.put("Ciudad_Destino_Id", ciudadDestino);
            valores.put("Día",dia);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(String horaOrigen,String horaDestino, String ciudadOrigen,String ciudadDestino, String dia){
        ContentValues valores = new ContentValues();
        valores.put("HorarioOrigen", horaOrigen);
        valores.put("HorarioDestino", horaDestino);
        valores.put("Ciudad_Origen_Id", ciudadOrigen);
        valores.put("Ciudad_Destino_Id", ciudadDestino);
        valores.put("Día",dia);
        try{
            openDataBase();
            db.insert(tabla, null, valores);
            close();
            return true;
        }
        catch(Exception ex){
            close();
            return false;
        }
    }
}
