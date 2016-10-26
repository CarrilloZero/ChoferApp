package busesapp.choferapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.sql.Time;
import java.util.ArrayList;

import busesapp.choferapp.DataHelper;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class TipoPasajeDAO extends DataHelper {

    private String tabla = "tipopasaje";
    
    public TipoPasajeDAO(Context ctx){
        super(ctx);
    }

    public TipoPasaje buscar(int id){
        TipoPasaje dev = null;
        try{
            openDataBase();
            String[] campos = {"Id","Tipo"};
            Cursor c = db.query(tabla, campos, "Id=" + id,null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new TipoPasaje(id,c.getString(1));
            c.close();
            close();
        }catch(Exception ex){
            close();
            return null;
        }

        return dev;
    }

    public ArrayList<TipoPasaje> listado(){
        ArrayList<TipoPasaje> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<TipoPasaje>();

            String[] campos = {"Id","Tipo"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    String tipo = c.getString(1);
                    TipoPasaje tipoPasaje = new TipoPasaje(id,tipo);
                    dev.add(tipoPasaje);
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



    public boolean eliminar(String tipo) {
        try{
            openDataBase();
            db.delete(tabla, "Nombre='"+tipo+"';", null);
            close();
        }catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean modificar(int id,String tipo){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("Tipo", tipo);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(String tipo){
        ContentValues valores = new ContentValues();
        valores.put("Tipo", tipo);
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
