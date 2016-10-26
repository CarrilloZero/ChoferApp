package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Pasajero {

    private int id, usuario_id;

    public Pasajero(int id, int usuario_id) {
        this.id = id;
        this.usuario_id = usuario_id;



    }

    public int getUsuario_Id() {
        return usuario_id;
    }

    public Pasajero setUsuario_Id(int usuario_id) {
        this.usuario_id = usuario_id;
        return this;
    }

    public int getId() {
        return id;
    }

    public Pasajero setId(int id) {
        this.id = id;
        return this;
    }



}