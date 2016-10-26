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
public class NotificacionDAO extends DataHelper {

    private String tabla = "notificación";

    public NotificacionDAO(Context ctx){
        super(ctx);
    }

    public Notificacion buscar(int usuario_id){
        Notificacion dev = null;
        try{
            openDataBase();
            String[] campos = {"Id","Comentario","Usuario_Id", "Hora"};
            Cursor c = db.query(tabla, campos, "Usuario_Id=" + usuario_id,null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Notificacion(c.getInt(0),c.getString(1),usuario_id,c.getString(3));
            c.close();
            close();
        }catch(Exception ex){
            close();
            return null;
        }

        return dev;
    }

    public ArrayList<Notificacion> listado(){
        ArrayList<Notificacion> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Notificacion>();
            String[] campos = {"Id","Comentario","Usuario_Id","Hora"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    String comentario = c.getString(1);
                    int usuario_id = c.getInt(2);
                    String hora = c.getString(3);
                    Notificacion notificacion = new Notificacion(id,comentario,usuario_id,hora);
                    dev.add(notificacion);
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

    public boolean modificar(int id, String comentario, int usuario_id, String hora){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();

            valores.put("Id", id);
            valores.put("Comentario", comentario);
            valores.put("Usuario_Id", usuario_id);
            valores.put("Hora",hora);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(String comentario, int usuario_id, String hora){
        ContentValues valores = new ContentValues();
        valores.put("Comentario", comentario);
        valores.put("Usuario_Id", usuario_id);
        valores.put("Hora", hora);
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

    public ArrayList<String>listarComentarios(){
        ArrayList<String> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<String>();
            String[] campos = {"Id","Comentario","Usuario_Id","Hora"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    String comentario = c.getString(1);
                    int usuario_id = c.getInt(2);
                    String hora = c.getString(3);
                    Notificacion notificacion = new Notificacion(id,comentario,usuario_id,hora);
                    dev.add(comentario);
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



}
