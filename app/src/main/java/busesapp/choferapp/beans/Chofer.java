package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Chofer {

    private int id, usuario_id, bus_id, empresa_id, online;
    private String contraseña;

    public Chofer(int id, int usuario_id, int bus_id, int empresa_id, int online, String contraseña) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.bus_id = bus_id;
        this.empresa_id= empresa_id;
        this.online=online;
        this.contraseña=contraseña;
    }

    public int getBus_Id() {
        return bus_id;
    }

    public Chofer setBus_Id(int bus_id) {
        this.bus_id = bus_id;
        return this;
    }

    public int getEmpresa_Id() {
        return empresa_id;
    }

    public Chofer setEmpresa_Id(int empresa_id) {
        this.empresa_id = empresa_id;
        return this;
    }

    public int getUsuario_Id() {
        return usuario_id;
    }

    public Chofer setUsuario_Id(int usuario_id) {
        this.usuario_id = usuario_id;
        return this;
    }

    public int getId() {
        return id;
    }

    public Chofer setId(int id) {
        this.id = id;
        return this;
    }

    public int getOnline() {return online;}

    public Chofer getOnline(int online){
        this.online=online;
        return this;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Chofer setContraseña(String contraseña) {
        this.contraseña = contraseña;
        return this;
    }
}