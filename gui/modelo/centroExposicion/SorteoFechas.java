package gui.modelo.centroExposicion;

import java.time.LocalDate;

import gui.modelo.exposicion.Exposicion;

/**
 * Clase SorteoFechas.
 * Esta clase hereda de {@link Sorteo} para determinar entre qué fechas
 * concretas se debe realizar el sorteo.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class SorteoFechas extends Sorteo{
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    /**
     * Constructor de un sorteo entre unas fechas especificadas.
     * 
     * @param exposicion  La exposición del sorteo
     * @param fechaSorteo La fecha en la límite de inscripción 
     * @param fechaInicio La fecha de inicio
     * @param fechaFin    La fecha de fin
     */
    public SorteoFechas(Exposicion exposicion, LocalDate fechaSorteo, int n_entradas, LocalDate fechaInicio, LocalDate fechaFin) {
        super(fechaSorteo, exposicion, n_entradas);
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

    /**
     * Retorna la fecha límite de la exposición.
     * 
     * @return La fecha en la que la exposición finaliza.
     * 
     */
    public LocalDate getFechaLimite() {
        return fechaFin;
    } 

}