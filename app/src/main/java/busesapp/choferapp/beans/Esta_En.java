package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Esta_En {

    private int id, usuario_id,paradero_id;

    public Esta_En(int id, int usuario_id, int paradero_id) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.paradero_id=paradero_id;



    }

    public int getParadero_id() {
        return paradero_id;
    }

    public Esta_En setParadero_id(int paradero_id) {
        this.paradero_id = paradero_id;
        return this;
    }

    public int getUsuario_Id() {
        return usuario_id;
    }

    public Esta_En setUsuario_Id(int usuario_id) {
        this.usuario_id = usuario_id;
        return this;
    }

    public int getId() {
        return id;
    }

    public Esta_En setId(int id) {
        this.id = id;
        return this;
    }



}