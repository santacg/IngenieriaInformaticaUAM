package Exposicion;

import Sala.SalaExposicion;

import java.time.LocalDate;
import java.util.Set;
import CentroExposicion.Descuento;

/**
 * Clase Exposición.
 * Esta clase representa una exposición en un centro de exposiciones,
 * incluyendo información sobre su duración, salas, tipo...
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Exposicion {
    private static Integer IDcount = 0;
    private Integer ID;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;
    private Double benificios;
    private EstadoExposicion estado;
    private Set<SalaExposicion> salas;
    private Set<Hora> horario;
    private Set<Descuento> descuentos;
    private Estadisticas estadisticas;
    private TipoExpo tipo;

    /**
     * Constructor para crear una nueva exposición con detalles específicos.
     * Inicializa una exposición con su nombre, fechas de inicio y fin, descripción,
     * conjunto de salas y tipo.
     * 
     * @param nombre      El nombre de la exposición.
     * @param fechaInicio La fecha de inicio de la exposición.
     * @param fechaFin    La fecha de conclusión de la exposición.
     * @param descripcion Una breve descripción de la exposición.
     * @param salas       Un conjunto de salas en las que se llevará a cabo la
     *                    exposición.
     * @param tipo        El tipo de exposición, puede ser temporal o permanente.
     */
    public Exposicion(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion,
            Set<SalaExposicion> salas, TipoExpo tipo) {
        this.ID = ++IDcount;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.salas = salas;
        this.tipo = tipo;
    }

    /**
     * Devuelve el identificador único de la exposición.
     * 
     * @return El ID de la exposición.
     */
    public Integer getID() {
        return ID;
    }

    /**
     * Devuelve el nombre de la exposición.
     * 
     * @return El nombre de la exposición.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Actualiza el nombre de la exposición.
     * 
     * @param nombre El nuevo nombre de la exposición.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la fecha de inicio de la exposición.
     * 
     * @return La fecha de inicio de la exposición.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Actualiza la fecha de inicio de la exposición.
     * 
     * @param fechaInicio La nueva fecha de inicio.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Devuelve la fecha de fin de la exposición.
     * 
     * @return La fecha de fin de la exposición.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Actualiza la fecha de fin de la exposición.
     * 
     * @param fechaFin La nueva fecha de fin.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Devuelve la descripción de la exposición.
     * 
     * @return La descripción de la exposición.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Actualiza la descripción de la exposición.
     * 
     * @param descripcion La nueva descripción de la exposición.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve los beneficios generados por la exposición.
     * 
     * @return Los beneficios generados por la exposición.
     */
    public Double getBenificios() {
        return benificios;
    }

    /**
     * Actualiza los beneficios generados por la exposición.
     * 
     * @param benificios Los nuevos beneficios generados por la exposición.
     */
    public void setBenificios(Double benificios) {
        this.benificios = benificios;
    }

    /**
     * Actualiza el estado actual de la exposición.
     * 
     * @param estado El nuevo estado de la exposición.
     */
    public void setEstado(EstadoExposicion estado) {
        this.estado = estado;
    }

    /**
     * Cambia el estado de la exposición a EN_CREACION.
     */
    public void expoCrear() {
        this.estado = EstadoExposicion.EN_CREACION;
    }

    /**
     * Cambia el estado de la exposición a PUBLICADA.
     */
    public void expoPublicar() {
        this.estado = EstadoExposicion.PUBLICADA;
    }

    /**
     * Cambia el estado de la exposición a CANCELADA.
     */
    public void expoCancelar() {
        this.estado = EstadoExposicion.CANCELADA;
    }

    /**
     * Prorroga la fecha de fin de la exposición.
     * 
     * @param fechaFin La nueva fecha de fin para la exposición.
     */
    public void expoProrrogar(LocalDate fechaFin) {
        this.estado = EstadoExposicion.PRORROGADA;
        this.fechaFin = fechaFin;
    }

    /**
     * Devuelve el estado actual de la exposición.
     * 
     * @return El estado de la exposición.
     */
    public EstadoExposicion getEstado() {
        return this.estado;
    }

    /**
     * Establece el conjunto de salas asignadas a la exposición.
     * 
     * @param salas El nuevo conjunto de salas para la exposición.
     */
    public void setSalas(Set<SalaExposicion> salas) {
        this.salas = salas;
    }

    /**
     * Devuelve las salas asignadas a la exposición.
     * 
     * @return Un conjunto de salas de exposición.
     */
    public Set<SalaExposicion> getSalas() {
        return this.salas;
    }

    /**
     * Añade una sala al conjunto de salas de la exposición.
     * 
     * @param sala La sala a añadir.
     */
    public void addSala(SalaExposicion sala) {
        this.salas.add(sala);
    }

    /**
     * Elimina una sala del conjunto de salas de la exposición.
     * 
     * @param sala La sala a eliminar.
     */
    public void removeSala(SalaExposicion sala) {
        this.salas.remove(sala);
    }

    /**
     * Establece el horario de visitas para la exposición.
     * 
     * @param horario El conjunto de horarios de la exposición.
     */
    public void setHorario(Set<Hora> horario) {
        this.horario = horario;
    }

    /**
     * Devuelve el conjunto de horarios de visitas de la exposición.
     * 
     * @return El conjunto de horarios de la exposición.
     */
    public Set<Hora> getHorario() {
        return this.horario;
    }

    /**
     * Añade un horario al conjunto de horarios de la exposición.
     * 
     * @param hora El horario a añadir.
     */
    public void addHorario(Hora hora) {
        this.horario.add(hora);
    }

    /**
     * Elimina un horario del conjunto de horarios de la exposición.
     * 
     * @param hora El horario a eliminar.
     */
    public void removeHorario(Hora hora) {
        this.horario.remove(hora);
    }

    /**
     * Establece las estadísticas asociadas a la exposición.
     * 
     * @param estadisticas Las estadísticas a establecer.
     */
    public void setEstadisticas(Estadisticas estadisticas) {
        this.estadisticas = estadisticas;
    }

    /**
     * Devuelve las estadísticas asociadas a la exposición.
     * 
     * @return Las estadísticas de la exposición.
     */
    public Estadisticas getEstadisticas() {
        return this.estadisticas;
    }

    /**
     * Devuelve el tipo de exposición.
     * 
     * @return El tipo de la exposición.
     */
    public TipoExpo getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de exposición.
     * 
     * @param tipo El nuevo tipo de exposición.
     */
    public void setTipo(TipoExpo tipo) {
        this.tipo = tipo;
    }

    /**
     * Cambia el tipo de la exposición a temporal.
     */
    public void expoTemporal() {
        this.tipo = TipoExpo.TEMPORAL;
    }

    /**
     * Cambia el tipo de la exposición a permanente.
     */
    public void expoPermanente() {
        this.tipo = TipoExpo.PERMANENTE;
    }

    /**
     * Devuelve el conjunto de descuentos aplicables a la exposición.
     * 
     * @return El conjunto de descuentos de la exposición.
     */
    public Set<Descuento> getDescuentos() {
        return descuentos;
    }

    /**
     * Establece el conjunto de descuentos aplicables a la exposición.
     * 
     * @param descuentos El nuevo conjunto de descuentos para la exposición.
     */
    public void setDescuentos(Set<Descuento> descuentos) {
        this.descuentos = descuentos;
    }

    /**
     * Añade un descuento al conjunto de descuentos de la exposición.
     * 
     * @param descuento El descuento a añadir.
     */
    public void addDescuento(Descuento descuento) {
        this.descuentos.add(descuento);
    }

    /**
     * Elimina un descuento del conjunto de descuentos de la exposición.
     * 
     * @param descuento El descuento a eliminar.
     */
    public void removeDescuento(Descuento descuento) {
        this.descuentos.remove(descuento);
    }

    /**
     * Elimina todos los descuentos del conjunto de descuentos de la exposición.
     */
    public void removeAllDescuentos() {
        this.descuentos.clear();
    }

    /**
     * Genera una representación en cadena de la exposición, incluyendo detalles
     * como el ID, nombre, fechas, y más.
     * 
     * @return Una cadena que representa la exposición.
     */
    public String toString() {
        return "Exposicion [ID=" + ID + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
                + ", descripcion=" + descripcion + ", benificios=" + benificios + ", estado=" + estado + ", salas="
                + salas + ", horario=" + horario + ", descuentos=" + descuentos + ", estadisticas=" + estadisticas
                + ", tipo=" + tipo + "]";
    }

    /**
     * Compara esta exposición con otro objeto para verificar si son iguales.
     * Dos exposiciones se consideran iguales si todos sus atributos son iguales.
     * 
     * @param obj El objeto con el que se compara esta exposición.
     * @return {@code true} si este objeto es igual al objeto argumento;
     *         {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Exposicion other = (Exposicion) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (fechaInicio == null) {
            if (other.fechaInicio != null)
                return false;
        } else if (!fechaInicio.equals(other.fechaInicio))
            return false;
        if (fechaFin == null) {
            if (other.fechaFin != null)
                return false;
        } else if (!fechaFin.equals(other.fechaFin))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (benificios == null) {
            if (other.benificios != null)
                return false;
        } else if (!benificios.equals(other.benificios))
            return false;
        if (estado != other.estado)
            return false;
        if (salas == null) {
            if (other.salas != null)
                return false;
        } else if (!salas.equals(other.salas))
            return false;
        if (horario == null) {
            if (other.horario != null)
                return false;
        } else if (!horario.equals(other.horario))
            return false;
        if (descuentos == null) {
            if (other.descuentos != null)
                return false;
        } else if (!descuentos.equals(other.descuentos))
            return false;
        if (estadisticas == null) {
            if (other.estadisticas != null)
                return false;
        } else if (!estadisticas.equals(other.estadisticas))
            return false;
        if (tipo != other.tipo)
            return false;
        return true;
    }

}