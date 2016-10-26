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
public class EmpresaDAO extends DataHelper {

    String tabla = "empresa";

    public EmpresaDAO(Context context) {
        super(context);
    }

    public Empresa buscar(int id){
        Empresa dev = null;
        try{
            openDataBase();
            String[] campos = {"Id","Nombre","Correo","Dirección","Telefono"};
            Cursor c = db.query(tabla, campos, "Id=" + id,null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Empresa(id,c.getString(0),c.getString(1),c.getString(2),c.getString(3));
            c.close();
            close();
        }catch(Exception ex){
            close();
            return null;
        }

        return dev;
    }

    public ArrayList<Empresa> listado(){
        ArrayList<Empresa> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Empresa>();
            String[] campos = {"Id","Nombre","Correo","Dirección","Telefono"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    String nombre = c.getString(1);
                    String correo = c.getString(2);
                    String direccion = c.getString(3);
                    String telefono = c.getString(4);
                    Empresa empresa = new Empresa(id,nombre,correo,direccion,telefono);
                    dev.add(empresa);
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
    public ArrayList<String> listadoNombre(){
        ArrayList<String> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<>();
            String[] campos = {"Nombre"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    String nombre = c.getString(1);

                    //Empresa empresa = new Empresa(id,nombre,correo,direccion,telefono);
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

    public boolean modificar(int id,String nombre, String correo, String direccion, String telefono){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("Nombre", nombre);
            valores.put("Correo", correo);
            valores.put("Dirección", direccion);
            valores.put("Telefono", telefono);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(String nombre, String correo, String direccion, String telefono){
        ContentValues valores = new ContentValues();
        valores.put("Nombre", nombre);
        valores.put("Correo", correo);
        valores.put("Dirección", direccion);
        valores.put("Telefono", telefono);
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
