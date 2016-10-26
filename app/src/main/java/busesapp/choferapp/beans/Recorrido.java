package busesapp.choferapp.beans;

/**
 * Created by daniel on 11/07/2016.
 */
public class Recorrido {

    private String HorarioOrigen,HorarioDestino, día;
    private int Ciudad_Origen_id,Ciudad_Destino_id,id;

    public Recorrido(int id, String horarioOrigen, String horarioDestino, int ciudad_Origen_id, int ciudad_Destino_id, String día) {
        this.id=id;
        HorarioOrigen = horarioOrigen;
        HorarioDestino = horarioDestino;
        Ciudad_Origen_id = ciudad_Origen_id;
        Ciudad_Destino_id = ciudad_Destino_id;
        this.día=día;
    }

    public String getDía(){return día;}

    public Recorrido getDía(String día){
        this.día=día;
        return this;
    }

    public String getHorarioDestino() {
        return HorarioDestino;
    }

    public Recorrido setHorarioDestino(String horarioDestino) {
        HorarioDestino = horarioDestino;
        return this;
    }

    public int getCiudad_Origen_id() {
        return Ciudad_Origen_id;
    }

    public Recorrido setCiudad_Origen_id(int ciudad_Origen_id) {
        Ciudad_Origen_id = ciudad_Origen_id;
        return this;
    }

    public int getCiudad_Destino_id() {
        return Ciudad_Destino_id;
    }

    public Recorrido setCiudad_Destino_id(int ciudad_Destino_id) {
        Ciudad_Destino_id = ciudad_Destino_id;
        return this;
    }

    public String getHorarioOrigen() {
        return HorarioOrigen;
    }

    public Recorrido setHorarioOrigen(String horarioOrigen) {
        HorarioOrigen = horarioOrigen;
        return this;
    }

    public int getId(){return id;}

    public Recorrido setId(int id){
        this.id=id;
        return this;
    }
}
