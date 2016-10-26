package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class TipoPasaje {
    private String tipo;
    private int id;

    public TipoPasaje(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public TipoPasaje setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public int getId() {
        return id;
    }

    public TipoPasaje setId(int id) {
        this.id = id;
        return this;
    }
}