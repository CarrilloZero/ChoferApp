package busesapp.choferapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import busesapp.choferapp.DataHelper;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class BusDAO extends DataHelper {

    private String tabla = "bus";

    public BusDAO(Context ctx){
        super(ctx);
    }

    public Bus buscar(String patente){
        Bus dev = null;
        try{

            openDataBase();
            String[] campos = {"Id", "Empresa_Id", "Patente"};
            Cursor c = db.query(tabla, campos, "Patente='" +patente+"';",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Bus(c.getInt(0), c.getInt(1), patente);
            c.close();
            close();

        }catch(Exception ex){
            ex.printStackTrace();
            close();
            return null;
        }


        return dev;
    }

    public ArrayList<Bus> listado(){
        ArrayList<Bus> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Bus>();
            String[] campos = {"Id", "Empresa_Id", "Patente"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    int empresa_id=c.getInt(1);
                    String patente=c.getString(2);
                    Bus bus = new Bus(id, empresa_id, patente);
                    dev.add(bus);
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

    public boolean eliminar(String patente) {
        try{
            openDataBase();
            db.delete(tabla, "Patente='"+patente+"';", null);
            close();
        }catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean modificar(int id, int empresa_id, String patente){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("Empresa_Id", empresa_id);
            valores.put("Patente", patente);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(int empresa_id, String patente){
        ContentValues valores = new ContentValues();
        valores.put("Empresa_Id", empresa_id);
        valores.put("Patente", patente);
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
