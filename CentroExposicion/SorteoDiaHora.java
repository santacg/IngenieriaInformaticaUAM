package CentroExposicion;

import java.util.Date;
import Exposicion.Exposicion;

/**
 * Clase SorteoDiaHora.
 * Esta clase hereda de {@link Sorteo} para añadir la gestión de la fecha y hora
 * concretas en que se debe realizar el sorteo.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class SorteoDiaHora extends Sorteo {
    private String dia;
    private String hora;

    /**
     * Constructor de un sorteo para un día y una hora especificados.
     * 
     * @param exposicion  La exposición del sorteo
     * @param fechaSorteo La fecha en la que se realizará el sorteo
     * @param dia         El día específico en que se realizará el sorteo
     * @param hora        La hora específica a la que se realizará el sorteo
     */
    public SorteoDiaHora(Exposicion exposicion, Date fechaSorteo, String dia, String hora) {
        super(fechaSorteo, exposicion);
        this.dia = dia;
        this.hora = hora;
    }

    /**
     * Getters y setters que facilitan la gestión de un sorteo
     * a un día y una hora concretos.
     */
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
