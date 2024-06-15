package gui.modelo.centroExposicion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import gui.modelo.expofy.ClienteRegistrado;
import gui.modelo.expofy.Expofy;
import gui.modelo.sala.Sala;

/**
 * Esta clase representa una actividad en un centro de exposición.
 * 
 * @author Carlos García Santa
 */
/**
 * La clase Actividades representa una actividad en un centro de exposición.
 */
public class Actividad {
    private String nombre;
    private TipoActividad tipo;
    private String descripcion;
    private Integer maxParticipantes;
    private LocalDate fecha;
    private LocalTime hora;
    private Sala salaCelebracion;
    private Set<String> participantes = new HashSet<>();

    /**
     * Constructor de la clase Actividades.
     * 
     * @param nombre           el nombre de la actividad
     * @param tipo             el tipo de la actividad
     * @param descripcion      la descripción de la actividad
     * @param maxParticipantes el numero maximo de participantes de la actividad
     * @param fecha            la fecha de la actividad
     * @param hora             la hora de la actividad
     * @param salaCelebracion  la sala de celebración de la actividad
     */
    public Actividad(String nombre, TipoActividad tipo, String descripcion, Integer maxParticipantes, LocalDate fecha,
            LocalTime hora,
            Sala salaCelebracion) {
        if (salaCelebracion.getAforo() < maxParticipantes) {
            System.out.println("El aforo de la sala es menor que el número de participantes");
            return;
        }

        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.maxParticipantes = maxParticipantes;
        this.fecha = fecha;
        this.hora = hora;
        this.salaCelebracion = salaCelebracion;
    }

    /**
     * Obtiene el nombre de la actividad.
     * 
     * @return el nombre de la actividad
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el tipo de la actividad.
     * 
     * @return el tipo de la actividad
     */
    public TipoActividad getTipo() {
        return tipo;
    }

    /**
     * Obtiene la descripción de la actividad.
     * 
     * @return la descripción de la actividad
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el maxParticipantes de la actividad.
     * 
     * @return el maxParticipantes de la actividad
     */
    public Integer getMaxParticipantes() {
        return maxParticipantes;
    }

    /**
     * Obtiene la fecha de la actividad.
     * 
     * @return la fecha de la actividad
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Obtiene la hora de la actividad.
     * 
     * @return la hora de la actividad
     */
    public LocalTime getHora() {
        return hora;
    }

    /**
     * Obtiene la sala de celebración de la actividad.
     * 
     * @return la sala de celebración de la actividad
     */
    public Sala getSalaCelebracion() {
        return salaCelebracion;
    }

    /**
     * Establece el nombre de la actividad.
     * 
     * @param nombre el nombre de la actividad
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el tipo de la actividad.
     * 
     * @param tipo el tipo de la actividad
     */
    public void setTipo(TipoActividad tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece la descripción de la actividad.
     * 
     * @param descripcion la descripción de la actividad
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece la fecha de la actividad.
     * 
     * @param fecha la fecha de la actividad
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Establece la hora de la actividad.
     * 
     * @param hora la hora de la actividad
     */
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    /**
     * Establece la sala de celebración de la actividad.
     * 
     * @param salaCelebracion la sala de celebración de la actividad
     */
    public void setSalaCelebracion(Sala salaCelebracion) {
        this.salaCelebracion = salaCelebracion;
    }

    /**
     * Obtiene los clientes inscritos en la actividad.
     * 
     * @return El conjunto de clientes inscritos en la actividad
     */
    public Set<String> getParticipantes() {
        return participantes;
    }

    /**
     * Añade un cliente a la actividad.
     * 
     * @param cliente el cliente a añadir
     * @return {@code true} si el cliente se añade correctamente, {@code false} en
     */
    public Boolean addParticipante(String NIF) {
        Expofy expofy = Expofy.getInstance();
        LocalDate hoy = LocalDate.now();
        ClienteRegistrado cliente = expofy.getClienteRegistrado(NIF);

        boolean puedeInscribirse = false;
        if (cliente != null) {
            puedeInscribirse = !this.fecha.isBefore(hoy.minusDays(1));
        } else {
            puedeInscribirse = this.fecha.isEqual(hoy);
        }

        if (puedeInscribirse && participantes.size() < maxParticipantes) {
            if (participantes.add(NIF) == false) {
                System.out.println("El cliente ya está inscrito en la actividad.");
                return false;
            }
            return true;
        } else {
            if (!puedeInscribirse) {
                System.out.println("El cliente no cumple con las condiciones de inscripción.");
            } else {
                System.out.println("La actividad está completa.");
            }
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Actividad other = (Actividad) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }

}
