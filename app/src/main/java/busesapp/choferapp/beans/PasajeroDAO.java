package busesapp.choferapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import busesapp.choferapp.DataHelper;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class PasajeroDAO extends DataHelper {

    private String tabla = "pasajero";

    public PasajeroDAO(Context ctx){
        super(ctx);
    }

    public Pasajero buscar(int usuario_id){
        Pasajero dev = null;
        try{

            openDataBase();
            String[] campos = {"Id","Usuario_Id"};
            Cursor c = db.query(tabla, campos, "Usuario_Id=" +usuario_id+";",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Pasajero(c.getInt(0),usuario_id);
            c.close();
            close();

        }catch(Exception ex){
            ex.printStackTrace();
            close();
            return null;
        }


        return dev;
    }

    public ArrayList<Pasajero> listado(){
        ArrayList<Pasajero> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Pasajero>();
            String[] campos = {"Id","Usuario_Id"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    int usuario_id=c.getInt(1);
                    Pasajero pasajero = new Pasajero(id,usuario_id);
                    dev.add(pasajero);
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

    public boolean modificar(int id,int usuario_id){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("Usuario_Id", usuario_id);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(int usuario_id){
        ContentValues valores = new ContentValues();
        valores.put("Usuario_Id", usuario_id);
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
