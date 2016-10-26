package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class TipoPasaje_Recorrido {

    private int id, tipopasaje_id, recorrido_id, precio;


    public TipoPasaje_Recorrido(int tipopasaje_id, int recorrido_id, int id, int precio ) {
        this.tipopasaje_id=tipopasaje_id;
        this.recorrido_id=recorrido_id;
        this.id = id;
        this.precio=precio;





    }

    public int getTipopasaje_id() {
        return tipopasaje_id;
    }

    public TipoPasaje_Recorrido setTipopasaje_id(int tipopasaje_id) {
        this.tipopasaje_id=tipopasaje_id;
        return this;
    }

    public int getRecorrido_id() {
        return recorrido_id;
    }

    public TipoPasaje_Recorrido setRecorrido_id(int recorrido_id) {
        this.recorrido_id=recorrido_id;
        return this;
    }

    public int getPrecio() {
        return precio;
    }

    public TipoPasaje_Recorrido setPrecio(int precio) {
        this.precio=precio;
        return this;
    }



    public int getId() {
        return id;
    }

    public TipoPasaje_Recorrido setId(int id) {
        this.id = id;
        return this;
    }



}