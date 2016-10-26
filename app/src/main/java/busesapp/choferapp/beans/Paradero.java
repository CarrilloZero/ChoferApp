package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Paradero {
    private String nombre;
    private int id;
    private double longitud, latitud;

    public Paradero(int id, double longitud, double latitud, String nombre) {
        this.id = id;

        this.longitud = longitud;
        this.latitud = latitud;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Paradero setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public int getId() {
        return id;
    }

    public Paradero setId(int id) {
        this.id = id;
        return this;
    }

    public double getLongitud(double longitud){ return longitud; }

    public Paradero setLongitud(double longitud) {
        this.longitud = longitud;
        return this;
    }

    public double getLatitud(double latitud){ return latitud; }

    public Paradero setLatitud(double latitud) {
        this.latitud = latitud;
        return this;
    }

}