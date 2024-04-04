package CentroExposicion;

import java.time.LocalDate;
import Exposicion.Exposicion;

/**
 * Clase SorteoExpo.
 * Esta clase hereda de {@link Sorteo} para añadir la cobertura de la fecha
 * en la que una exposición está vigente.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class SorteoExpo extends Sorteo{

    /**
     * Constructor de un sorteo para la vigencia de exposición.
     * 
     * @param exposicion   La exposición del sorteo
     * @param fechaSorteo  La fecha en la que se realizará el sorteo
     */
    public SorteoExpo(Exposicion exposicion, LocalDate fechaSorteo) {
        super(fechaSorteo, exposicion);
    }

    public LocalDate getFechaLimite() {
        return getExposicion().getFechaFin();
    }

}
