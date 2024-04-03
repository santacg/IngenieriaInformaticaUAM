package Exposicion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;
import Entrada.Entrada;

/**
 * Clase Hora.
 * Representa un horario específico para una actividad en una exposición,
 * incluyendo información sobre la disponibilidad y precio de las entradas.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Hora implements Serializable {
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer nEntradas;
    private Integer countEntradas;
    private Set<Entrada> entradas = new HashSet<>();

    /**
     * Devuelve el conjunto de entradas asociadas a este horario.
     * 
     * @return Un conjunto de entradas.
     */
    public Set<Entrada> getEntradas() {
        return entradas;
    }

    /**
     * Añade una entrada al conjunto de entradas para este horario.
     * 
     * @param entrada La entrada a añadir.
     */
    public void addEntrada(Entrada entrada) {
        if (countEntradas + 1 > nEntradas) {
            System.out.println("No se pueden añadir más entradas");
            return;
        }
        this.entradas.add(entrada);
        nEntradas++;
        countEntradas++;
    }

    /**
     * Elimina una entrada del conjunto de entradas para este horario.
     * 
     * @param entrada La entrada a eliminar.
     */
    public void removeEntrada(Entrada entrada) {
        this.entradas.remove(entrada);
        nEntradas--;
        countEntradas--;
    }

    /**
     * Elimina todas las entradas del conjunto de entradas para este horario y
     * reinicia el contador de entradas disponibles.
     */
    public void removeAllEntradas() {
        countEntradas = 0;
        this.entradas.clear();
    }

    /**
     * Constructor para crear un nuevo horario con detalles específicos.
     * 
     * @param fecha      La fecha de la actividad.
     * @param horaInicio La hora de inicio de la actividad.
     * @param horaFin    La hora de fin de la actividad.
     * @param nEntradas  El número total de entradas disponibles para la actividad.
     * @param precio     El precio por entrada.
     */
    public Hora(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Integer nEntradas, Double precio) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.nEntradas = nEntradas;
        countEntradas = nEntradas;
    }

    /**
     * Obtiene la fecha de la actividad.
     * 
     * @return La fecha en que se realiza la actividad.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la actividad.
     * 
     * @param fecha La nueva fecha para la actividad.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la hora de inicio de la actividad.
     * 
     * @return La hora de inicio de la actividad.
     */
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * Establece la hora de inicio de la actividad.
     * 
     * @param horaInicio La nueva hora de inicio para la actividad.
     */
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Obtiene la hora de fin de la actividad.
     * 
     * @return La hora de fin de la actividad.
     */
    public LocalTime getHoraFin() {
        return horaFin;
    }

    /**
     * Establece la hora de fin de la actividad.
     * 
     * @param horaFin La nueva hora de fin para la actividad.
     */
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * Obtiene el número actual de entradas no vendidas, es decir, disponibles.
     *
     * @return La cantidad de entradas disponibles.
     */
    public Integer getCountEntradas() {
        return countEntradas;
    }

    /**
     * Obtiene el número de entradas totales para la actividad.
     * 
     * @return El número total de entradas totales.
     */
    public Integer getnEntradas() {
        return nEntradas;
    }

    /**
     * Registra la venta de una entrada recudiendo en una unidad el contador.
     */
    public void entradaVendida() {
        countEntradas--;
    }

    /**
     * Obtiene el número de entradas disponibles para la actividad.
     * 
     * @return El número total de entradas disponibles.
     */
    public Integer getnEntradasDisp() {
        return countEntradas;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Hora Details:\n");
        sb.append("Fecha: ").append(fecha).append("\n");
        sb.append("Hora de Inicio: ").append(horaInicio).append("\n");
        sb.append("Hora de Fin: ").append(horaFin).append("\n");
        sb.append("Número de Entradas: ").append(nEntradas).append("\n");

        return sb.toString();
    }

    /**
     * Comprueba si este objeto Hora es igual a otro objeto.
     * La igualdad se basa en la comparación de la fecha, las horas de inicio y fin,
     * el número y precio de las entradas, y el conjunto de entradas.
     * 
     * @param obj El objeto con el que comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hora other = (Hora) obj;
        if (fecha == null) {
            if (other.fecha != null)
                return false;
        } else if (!fecha.equals(other.fecha))
            return false;
        if (horaInicio == null) {
            if (other.horaInicio != null)
                return false;
        } else if (!horaInicio.equals(other.horaInicio))
            return false;
        if (horaFin == null) {
            if (other.horaFin != null)
                return false;
        } else if (!horaFin.equals(other.horaFin))
            return false;
        return true;
    }
}
