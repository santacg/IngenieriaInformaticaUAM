package Exposicion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import Entrada.Entrada;

/**
 * Clase Hora.
 * Representa un horario específico para una actividad en una exposición,
 * incluyendo información sobre la disponibilidad y precio de las entradas.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Hora {
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer nEntradas;
    private Double precio;
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
     * Establece el conjunto de entradas para este horario.
     * 
     * @param entradas El conjunto de entradas a establecer.
     */
    public void setEntradas(Set<Entrada> entradas) {
        this.entradas = entradas;
    }

    /**
     * Añade una entrada al conjunto de entradas para este horario.
     * 
     * @param entrada La entrada a añadir.
     */
    public void addEntrada(Entrada entrada) {
        this.entradas.add(entrada);
    }

    /**
     * Elimina una entrada del conjunto de entradas para este horario.
     * 
     * @param entrada La entrada a eliminar.
     */
    public void removeEntrada(Entrada entrada) {
        this.entradas.remove(entrada);
    }

    /**
     * Elimina todas las entradas del conjunto de entradas para este horario y
     * reinicia el contador de entradas disponibles.
     */
    public void removeAllEntradas() {
        this.nEntradas = 0;
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
        this.precio = precio;
        for (int i = 0; i < nEntradas; i++) {
            this.entradas.add(new Entrada(i));
        }
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
     * Obtiene el número de entradas disponibles para la actividad.
     * 
     * @return El número total de entradas disponibles.
     */
    public Integer getnEntradas() {
        return nEntradas;
    }

    /**
     * Establece el número de entradas disponibles para la actividad.
     * 
     * @param nEntradas El nuevo número de entradas disponibles.
     */
    public void setnEntradas(Integer nEntradas) {
        this.nEntradas = nEntradas;
    }

    /**
     * Obtiene el precio por entrada de la actividad.
     * 
     * @return El precio por entrada.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio por entrada de la actividad.
     * 
     * @param precio El nuevo precio por entrada.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
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
        if (nEntradas == null) {
            if (other.nEntradas != null)
                return false;
        } else if (!nEntradas.equals(other.nEntradas))
            return false;
        if (precio == null) {
            if (other.precio != null)
                return false;
        } else if (!precio.equals(other.precio))
            return false;
        if (entradas == null) {
            if (other.entradas != null)
                return false;
        } else if (!entradas.equals(other.entradas))
            return false;
        return true;
    }
}
