package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Notificacion {
    private String comentario, hora;
    private int id;
    private int usuario_id;

    public Notificacion(int id, String comentario, int usuario_id, String hora) {
        this.id = id;
        this.comentario=comentario;
        this.usuario_id=usuario_id;
        this.hora=hora;

    }

    public  String getHora() {return hora;}

    public Notificacion setHora(String hora){
        this.hora=hora;
        return this;
    }

    public String getComentario() {
        return comentario;
    }

    public Notificacion setComentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public int getId() {
        return id;
    }

    public Notificacion setId(int id) {
        this.id = id;
        return this;
    }
    public int getUsuario_id() {
        return usuario_id;
    }

    public Notificacion setUsuario_Id(int usuario_id) {
        this.usuario_id = usuario_id;
        return this;
    }





}