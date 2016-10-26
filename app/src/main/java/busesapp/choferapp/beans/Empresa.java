package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Empresa {

    private int id;
    private String nombre;
    private String correo;
    private String direccion;
    private String telefono;

    public Empresa(int id, String nombre, String correo, String direccion, String telefono) {
        this.setId(id);
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public Empresa setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getCorreo() {
        return correo;
    }

    public Empresa setCorreo(String correo) {
        this.correo = correo;
        return this;
    }

    public String getDireccion() {
        return direccion;
    }

    public Empresa setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public String getTelefono() {
        return telefono;
    }

    public Empresa setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public int getId() {
        return id;
    }

    public Empresa setId(int id) {
        this.id = id;
        return this;
    }
}

