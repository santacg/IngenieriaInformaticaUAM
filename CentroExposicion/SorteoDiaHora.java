package CentroExposicion;

import java.util.Date;
import Exposicion.Exposicion;

public class SorteoDiaHora extends Sorteo {
    private String dia;
    private String hora;
    
    public SorteoDiaHora(Exposicion exposicion, Date fechaSorteo, String dia, String hora) {
        super(fechaSorteo, exposicion);
        this.dia = dia;
        this.hora = hora;
    }

    public String getDia() {
        return dia;
    }

    public String getHora() {
        return hora;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
