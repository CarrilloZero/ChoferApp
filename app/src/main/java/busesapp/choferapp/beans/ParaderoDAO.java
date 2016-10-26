package busesapp.choferapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import busesapp.choferapp.DataHelper;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class ParaderoDAO extends DataHelper {

    private String tabla = "paradero";

    public ParaderoDAO(Context ctx){
        super(ctx);
    }

    public Paradero buscar(String nombre){
        Paradero dev = null;
        try{

            openDataBase();
            String[] campos = {"Id","Longitud","Latitud","Nombre"};
            Cursor c = db.query(tabla, campos, "Nombre='" +nombre+"';",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Paradero(c.getInt(0),c.getDouble(1),c.getDouble(2),nombre);
            c.close();
            close();

        }catch(Exception ex){
            ex.printStackTrace();
            close();
            return null;
        }


        return dev;
    }

    public ArrayList<Paradero> listado(){
        ArrayList<Paradero> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Paradero>();
            String[] campos = {"Id","Longitud","Latitud","Nombre"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    double longitud = c.getDouble(1);
                    double latitud = c.getDouble(2);
                    String nombre = c.getString(3);
                    Paradero paradero = new Paradero(id,longitud,latitud,nombre);
                    dev.add(paradero);
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

    public ArrayList<String> listadoNombres(){
        ArrayList<String> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<String>();
            String[] campos = {"Id","Longitud","Latitud","Nombre"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    double longitud = c.getDouble(1);
                    double latitud = c.getDouble(2);
                    String nombre = c.getString(3);
                    //Paradero paradero = new Paradero(id,longitud,latitud,nombre);

                    dev.add(nombre);
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

    public boolean eliminar(String nombre) {
        try{
            openDataBase();
            db.delete(tabla, "Nombre='"+nombre+"';", null);
            close();
        }catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean modificar(int id, double longitud, double latitud, String nombre){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("Longitud", longitud);
            valores.put("Latitud", latitud);
            valores.put("Nombre", nombre);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(double longitud, double latitud, String nombre){
        ContentValues valores = new ContentValues();
        valores.put("Longitud", longitud);
        valores.put("Latitud", latitud);
        valores.put("Nombre", nombre);
        try{
            openDataBase();
            db.insert(tabla, null, valores);
            close();
            return true;
        }
        catch(Exception ex){
            ex.printStackTrace();
            close();
            return false;
        }
    }
}
