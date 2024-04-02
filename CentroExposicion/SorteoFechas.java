package CentroExposicion;

import java.time.LocalDate;
import Exposicion.Exposicion;

/**
 * Clase SorteoFechas.
 * Esta clase hereda de {@link Sorteo} para determinar entre qué fechas
 * concretas se debe realizar el sorteo.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class SorteoFechas extends Sorteo {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    /**
     * Constructor de un sorteo entre unas fechas especificadas.
     * 
     * @param exposicion  La exposición del sorteo
     * @param fechaSorteo La fecha en la que se realizará el sorteo
     * @param fechaInicio La fecha de inicio
     * @param fechaFin    La fecha de fin
     */
    public SorteoFechas(Exposicion exposicion, LocalDate fechaSorteo, LocalDate fechaInicio, LocalDate fechaFin) {
        super(fechaSorteo, exposicion);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene la fecha de inicio.
     * @return La fecha de inicio.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece una nueva fecha de inicio.
     * @param fechaInicio La nueva fecha de inicio.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de fin.
     * @return La fecha de fin.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece una nueva fecha de fin.
     * @param fechaFin La nueva fecha de fin.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaLimite() {
        return fechaFin;
    } 

}