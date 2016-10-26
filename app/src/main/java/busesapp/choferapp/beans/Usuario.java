package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Usuario {
    private String correo;
    private int id;
    private double longitud, latitud;

    public Usuario(int id, double longitud, double latitud, String correo) {
        this.id = id;

        this.longitud = longitud;
        this.latitud = latitud;
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public Usuario setCorreo(String correo) {
        this.correo = correo;
        return this;
    }

    public int getId() {
        return id;
    }

    public Usuario setId(int id) {
        this.id = id;
        return this;
    }

    public double getLongitud(double longitud){ return longitud; }

    public Usuario setLongitud(double longitud) {
        this.longitud = longitud;
        return this;
    }

    public double getLatitud(double latitud){ return latitud; }

    public Usuario setLatitud(double latitud) {
        this.latitud = latitud;
        return this;
    }

}