package busesapp.choferapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import busesapp.choferapp.DataHelper;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class UsuarioDAO extends DataHelper {

    private String tabla = "usuario";

    public UsuarioDAO(Context ctx){
        super(ctx);
    }

    public Usuario buscar(String correo){
        Usuario dev = null;
        try{

            openDataBase();
            String[] campos = {"Id","Longitud","Latitud","Correo"};
            Cursor c = db.query(tabla, campos, "Correo='" + correo+"';",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Usuario(c.getInt(0),c.getDouble(1),c.getDouble(2),correo);
            c.close();
            close();

        }catch(Exception ex){
            ex.printStackTrace();
            close();
            return null;
        }


        return dev;
    }

    public ArrayList<Usuario> listado(){
        ArrayList<Usuario> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Usuario>();
            String[] campos = {"Id","Longitud","Latitud","Correo"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    double longitud = c.getDouble(1);
                    double latitud = c.getDouble(2);
                    String correo = c.getString(3);
                    Usuario usuario = new Usuario(id,longitud,latitud,correo);
                    dev.add(usuario);
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

    public boolean modificar(int id, double longitud, double latitud, String correo){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("Longitud", longitud);
            valores.put("Latitud", latitud);
            valores.put("Correo", correo);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(double longitud, double latitud, String correo){
        ContentValues valores = new ContentValues();
        valores.put("Longitud", longitud);
        valores.put("Latitud", latitud);
        valores.put("Correo", correo);
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
