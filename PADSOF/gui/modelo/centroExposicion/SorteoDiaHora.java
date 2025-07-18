package gui.modelo.centroExposicion;

import java.time.LocalDate;

import gui.modelo.exposicion.*;

/**
 * Clase SorteoDiaHora.
 * Esta clase hereda de {@link Sorteo} para añadir la gestión de la fecha y hora
 * concretas en que se debe realizar el sorteo.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class SorteoDiaHora extends Sorteo {
    private LocalDate dia;
    private Hora hora;

    /**
     * Constructor de un sorteo para un día y una hora especificados.
     * 
     * @param exposicion  La exposición del sorteo
     * @param fechaSorteo La fecha en la que se realizará el sorteo
     * @param n_entradas  Numero de entradas sorteadas
     * @param dia         El día en el que se atenderá a la exposición
     * @param tiempo      La hora en el que se atenderá a la exposición *
     */
    public SorteoDiaHora(Exposicion exposicion, LocalDate fechaSorteo, int n_entradas, LocalDate dia, Hora tiempo) {
        super(fechaSorteo, exposicion, n_entradas);
        this.dia = dia;
        this.hora = tiempo;
        hora.reservarEntradas(n_entradas);
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
    public Hora getHora() {
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
    public void setHora(Hora hora) {
        this.hora = hora;
    }

    /**
     * Retorna la fecha límite de la exposición.
     * 
     * @return La fecha en la que la exposición finaliza.
     */
    public LocalDate getFechaLimite() {
        return dia;
    }

}
