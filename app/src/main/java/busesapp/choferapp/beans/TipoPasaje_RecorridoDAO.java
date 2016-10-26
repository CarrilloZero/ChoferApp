package busesapp.choferapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import busesapp.choferapp.DataHelper;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class TipoPasaje_RecorridoDAO extends DataHelper {

    private String tabla = "tipopasaje_recorrido";

    public TipoPasaje_RecorridoDAO(Context ctx){
        super(ctx);
    }

    public TipoPasaje_Recorrido buscar(int id){
        TipoPasaje_Recorrido dev = null;
        try{

            openDataBase();
            String[] campos = {"TipoPasaje_Id","Recorrido_Id","Id","Precio" };
            Cursor c = db.query(tabla, campos, "Id=" +id+";",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new TipoPasaje_Recorrido(c.getInt(0),c.getInt(1),id, c.getInt(3));
            c.close();
            close();

        }catch(Exception ex){
            ex.printStackTrace();
            close();
            return null;
        }


        return dev;
    }

    public ArrayList<TipoPasaje_Recorrido> listado(){
        ArrayList<TipoPasaje_Recorrido> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<TipoPasaje_Recorrido>();
            String[] campos = {"TipoPasaje_Id","Recorrido_Id","Id","Precio" };
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int tipopasaje_id= c.getInt(0);
                    int recorrido_id=c.getInt(1);
                    int id=c.getInt(2);
                    int precio=c.getInt(3);

                    TipoPasaje_Recorrido tipoPasaje_recorrido = new TipoPasaje_Recorrido(tipopasaje_id,recorrido_id,id,precio);
                    dev.add(tipoPasaje_recorrido);
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

    public boolean modificar(int tipopasaje_id, int recorrido_id, int id, int precio ){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("TipoPasaje_Id", tipopasaje_id);
            valores.put("Recorrido_Id", recorrido_id);
            valores.put("Id", id);
            valores.put("Precio", precio);

            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(int tipopasaje_id, int recorrido_id, int precio){
        ContentValues valores = new ContentValues();
        valores.put("TipoPasaje_Id", tipopasaje_id);
        valores.put("Recorrido_Id", recorrido_id);

        valores.put("Precio", precio);
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
