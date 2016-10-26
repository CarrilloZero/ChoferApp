package busesapp.choferapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Bus {

    private int id, empresa_id;
    private String patente;

    public Bus(int id, int empresa_id, String patente) {
        this.id = id;
        this.empresa_id= empresa_id;
        this.patente=patente;


    }

    public String getPatente(){return patente; }

    public Bus setPatente(String patente){
        this.patente=patente;
        return this;
    }

    public int getEmpresa_Id() {
        return empresa_id;
    }

    public Bus setEmpresa_Id(int empresa_id) {
        this.empresa_id = empresa_id;
        return this;
    }



    public int getId() {
        return id;
    }

    public Bus setId(int id) {
        this.id = id;
        return this;
    }



}