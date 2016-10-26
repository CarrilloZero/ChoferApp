package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Recorrido_Paradero {

    private int id, recorrido_id,paradero_id;

    public Recorrido_Paradero(int recorrido_id, int paradero_id, int id) {

        this.recorrido_id = recorrido_id;
        this.paradero_id=paradero_id;
        this.id = id;


    }

    public int getParadero_id() {
        return paradero_id;
    }

    public Recorrido_Paradero setParadero_id(int paradero_id) {
        this.paradero_id = paradero_id;
        return this;
    }

    public int getRecorrido_id() {
        return recorrido_id;
    }

    public Recorrido_Paradero setRecorrido_id(int usuario_id) {
        this.recorrido_id = recorrido_id;
        return this;
    }

    public int getId() {
        return id;
    }

    public Recorrido_Paradero setId(int id) {
        this.id = id;
        return this;
    }



}