package CentroExposicion;

import java.util.Date;
import Exposicion.Exposicion;

/**
 * Clase SorteoExpo.
 * Esta clase hereda de {@link Sorteo} para añadir la cobertura de la fecha
 * en la que una exposición está vigente.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class SorteoExpo extends Sorteo {
    private Boolean vigenciaExpo;

    /**
     * Constructor de un sorteo para la vigencia de exposición.
     * 
     * @param exposicion   La exposición del sorteo
     * @param fechaSorteo  La fecha en la que se realizará el sorteo
     * @param vigenciaExpo La vigencia de la exposición
     */
    public SorteoExpo(Exposicion exposicion, Date fechaSorteo, Boolean vigenciaExpo) {
        super(fechaSorteo, exposicion);
        this.vigenciaExpo = vigenciaExpo;
    }

    /**
     * Getters y setters de VigenciaExpo
     */
    public Boolean getVigenciaExpo() {
        return vigenciaExpo;
    }

    public void setVigenciaExpo(Boolean vigenciaExpo) {
        this.vigenciaExpo = vigenciaExpo;
    }

}
