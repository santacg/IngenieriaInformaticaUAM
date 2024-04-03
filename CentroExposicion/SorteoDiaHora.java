package CentroExposicion;

import java.time.LocalDate;
import java.time.LocalTime;

import Exposicion.Exposicion;

/**
 * Clase SorteoDiaHora.
 * Esta clase hereda de {@link Sorteo} para añadir la gestión de la fecha y hora
 * concretas en que se debe realizar el sorteo.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class SorteoDiaHora extends Sorteo{
    private LocalDate dia;
    private LocalTime hora;

    /**
     * Constructor de un sorteo para un día y una hora especificados.
     * 
     * @param exposicion  La exposición del sorteo
     * @param fechaSorteo La fecha en la que se realizará el sorteo
     * @param dia         El día específico en que se realizará el sorteo
     * @param hora        La hora específica a la que se realizará el sorteo
     */
    public SorteoDiaHora(Exposicion exposicion, LocalDate fechaSorteo, LocalDate dia, LocalTime hora) {
        super(fechaSorteo, exposicion);
        this.dia = dia;
        this.hora = hora;
    }

    /**
     * Retorna el día asignado.
     * 
     * @return El día establecido para el evento o actividad.
     */
    public LocalDate getDia() {
        return dia;
    }

    /**
     * Retorna la hora asignada.
     * 
     * @return La hora establecida para el evento o actividad.
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * Establece el día para el evento o actividad.
     * 
     * @param dia El nuevo día a establecer.
     */
    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    /**
     * Establece la hora para el evento o actividad.
     * 
     * @param hora La nueva hora a establecer.
     */
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public LocalDate getFechaLimite() {
        return dia;
    }

}
