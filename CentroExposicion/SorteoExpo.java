package CentroExposicion;

import java.time.LocalDate;
import java.io.Serializable;
import Exposicion.Exposicion;

/**
 * Clase SorteoExpo.
 * Esta clase hereda de {@link Sorteo} para añadir la cobertura de la fecha
 * en la que una exposición está vigente.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class SorteoExpo extends Sorteo implements Serializable{
    private Boolean vigenciaExpo;

    /**
     * Constructor de un sorteo para la vigencia de exposición.
     * 
     * @param exposicion   La exposición del sorteo
     * @param fechaSorteo  La fecha en la que se realizará el sorteo
     * @param vigenciaExpo La vigencia de la exposición
     */
    public SorteoExpo(Exposicion exposicion, LocalDate fechaSorteo, Boolean vigenciaExpo) {
        super(fechaSorteo, exposicion);
        this.vigenciaExpo = vigenciaExpo;
    }

    /**
     * Obtiene el estado de vigencia de la exposición.
     * Este método devuelve un valor booleano que indica si la exposición está actualmente vigente.
     * 
     * @return {@code true} si la exposición está vigente, {@code false} en caso contrario.
     */
    public Boolean getVigenciaExpo() {
        return vigenciaExpo;
    }

    /**
     * Establece el estado de vigencia de la exposición.
     * Este método permite definir si una exposición debe considerarse vigente o no.
     * 
     * @param vigenciaExpo {@code true} para marcar la exposición como vigente, {@code false} para indicar que no lo está.
     */
    public void setVigenciaExpo(Boolean vigenciaExpo) {
        this.vigenciaExpo = vigenciaExpo;
    }
    public LocalDate getFechaLimite() {
        return getExposicion().getFechaFin();
    }

}
