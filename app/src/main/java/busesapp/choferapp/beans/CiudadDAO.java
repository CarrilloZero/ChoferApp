package busesapp.choferapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import busesapp.choferapp.DataHelper;
import busesapp.choferapp.DbHelper;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class CiudadDAO extends DataHelper {

    private String tabla = "ciudad";

    public CiudadDAO(Context ctx){
        super(ctx);
    }

    public Ciudad buscar(int id){
        Ciudad dev = null;
        try{
            openDataBase();
            String[] campos = {"Id","Nombre"};
            Cursor c = db.query(tabla, campos, "Id=" + id,null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Ciudad(id,c.getString(0));
            c.close();
            close();
        }catch(Exception ex){
            close();
            return null;
        }

        return dev;
    }
    public Ciudad buscarNombre(String nombre){
        Ciudad dev = null;
        try{
            openDataBase();
            String[] campos = {"Id","Nombre"};
            Cursor c = db.query(tabla, campos, "Nombre='" +nombre+"';",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Ciudad(c.getInt(0), nombre);
            c.close();
            close();
        }catch(Exception ex){
            close();
            return null;
        }

        return dev;
    }

    public ArrayList<Ciudad> listado(){
        ArrayList<Ciudad> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Ciudad>();

            String[] campos = {"Id","Nombre"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    String nombre = c.getString(1);
                    Ciudad ciudad = new Ciudad(id,nombre);
                    dev.add(ciudad);
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

    public ArrayList<String> listadoCiudad(){
        ArrayList<String> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<String>();

            String[] campos = {"Id","Nombre"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    String nombre = c.getString(1);
                    Ciudad ciudad = new Ciudad(id,nombre);
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

    public boolean modificar(int id,String nombre){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
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

    public boolean insertar(String nombre){
        ContentValues valores = new ContentValues();
        valores.put("Nombre", nombre);
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
