package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Hace {

    private int id,  bus_id, recorrido_id;
    String hora_inicio,hora_termino;

    public Hace(int id, String hora_inicio,String hora_termino, int bus_id, int recorrido_id) {
        this.id = id;
        this.hora_inicio=hora_inicio;
        this.hora_termino=hora_termino;
        this.bus_id = bus_id;
        this.recorrido_id=recorrido_id;




    }

    public int getBus_Id() {
        return bus_id;
    }

    public Hace setBus_Id(int bus_id) {
        this.bus_id = bus_id;
        return this;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public Hace setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
        return this;
    }

    public String getHora_termino() {
        return hora_termino;
    }

    public Hace setHora_termino(String hora_termino) {
        this.hora_termino = hora_termino;
        return this;
    }

    public int getRecorrido_id() {
        return recorrido_id;
    }

    public Hace setRecorrido_id(int recorrido_id) {
        this.recorrido_id = recorrido_id;
        return this;
    }


    public int getId() {
        return id;
    }

    public Hace setId(int id) {
        this.id = id;
        return this;
    }



}