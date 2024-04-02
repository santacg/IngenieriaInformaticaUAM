package CentroExposicion;

import java.time.LocalDate;
import java.time.LocalTime;

import Sala.Sala;
import java.util.HashSet;
import java.util.Set;
import Exposicion.EstadoExposicion;
import Exposicion.Exposicion;
import Exposicion.TipoExpo;
import Obra.Obra;

/**
 * Clase CentroExposicion.
 * Esta clase ofrece funcionalidades para manejar las salas, exposiciones,
 * obras, empleados, y descuentos dentro del centro de exposición.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */

public class CentroExposicion {
    private Integer ID;
    private static Integer IDcount = 0;
    private String nombre;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private String localizacion;
    private String contraseniaEmpleado;
    private String contraseniaGestor;
    private Integer sancion;
    private Set<Exposicion> exposiciones = new HashSet<>();
    private Set<Sorteo> sorteos;
    private Set<Obra> obras;
    private Set<Empleado> empleados;
    private Set<Descuento> descuentos;
    private Set<Sala> salas = new HashSet<>();
    private Gestor gestor;

    /**
     * Constructor de un centro de exposición con los parámetros proporcionados.
     *
     * @param nombre              el nombre del centro de exposición
     * @param horaApertura        la hora de apertura del centro
     * @param horaCierre          la hora de cierre del centro
     * @param localizacion        la ubicación del centro
     * @param contraseniaEmpleado la contraseña para los empleados del centro
     * @param contraseniaGestor   la contraseña para el gestor del centro
     * @param empleados           el conjunto inicial de empleados
     * @param gestor              el gestor del centro
     * @param salas               el conjunto inicial de salas
     */
    public CentroExposicion(String nombre, LocalTime horaApertura,
            LocalTime horaCierre, String localizacion,
            String contraseniaEmpleado, String contraseniaGestor,
            Set<Empleado> empleados, Gestor gestor, Set<Sala> salas) {
        this.ID = IDcount++;
        this.nombre = nombre;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.localizacion = localizacion;
        this.contraseniaEmpleado = contraseniaEmpleado;
        this.contraseniaGestor = contraseniaGestor;
        this.empleados = empleados;
        this.gestor = gestor;
        this.salas = salas;
    }

    /**
     * Getters y setters que facilitan la gestión de un centro de exposicion.
     */
    public Integer getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getContraseniaEmpleado() {
        return contraseniaEmpleado;
    }

    public void setContraseniaEmpleado(String contraseniaEmpleado) {
        this.contraseniaEmpleado = contraseniaEmpleado;
    }

    public String getContraseniaGestor() {
        return contraseniaGestor;
    }

    public void setContraseniaGestor(String contraseniaGestor) {
        this.contraseniaGestor = contraseniaGestor;
    }

    public Integer getSancion() {
        return sancion;
    }

    public void setSancion(Integer sancion) {
        this.sancion = sancion;
    }

    public Set<Sala> getSalas() {
        return salas;
    }

    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

    /**
     * Añade una sala a un centro de exposiciones.
     * 
     * @param sala la sala a añadir
     */
    public void addSala(Sala sala) {
        this.salas.add(sala);
    }

    /**
     * Elimina una sala de un centro de exposiciones.
     * 
     * @param sala la sala a eliminar
     */
    public void removeSala(Sala sala) {
        this.salas.remove(sala);
    }

    /**
     * Obtiene las exposiciones totales en el centro.
     * 
     * @return un conjunto de las exposiciones totales
     */
    public Set<Exposicion> getExposiciones() {
        return exposiciones;
    }

    /**
     * Obtiene las exposiciones disponibles en el centro por un rango de fechas.
     *
     * @param fechaInicio la fecha de inicio
     * @param fechaFinal  la fecha de fin
     * @return un conjunto de exposiciones disponibles en el rango de fechas dado
     */
    public Set<Exposicion> getExposicionesPorFecha(LocalDate fechaInicio, LocalDate fechaFinal) {
        Set<Exposicion> exposicionesPorFecha = new HashSet<>();
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getFechaInicio().isAfter(fechaInicio) && exposicion.getFechaFin().isBefore(fechaFinal)) {
                exposicionesPorFecha.add(exposicion);
            }
        }
        return exposicionesPorFecha;
    }

    /**
     * Obtiene las exposiciones temporales del centro.
     * 
     * @return un conjunto de las exposiciones de tipo temporal
     */
    public Set<Exposicion> getExposicionesTemporales() {
        Set<Exposicion> exposicionesTemporales = new HashSet<>();
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getTipo().equals(TipoExpo.TEMPORAL)) {
                exposicionesTemporales.add(exposicion);
            }
        }
        return exposicionesTemporales;
    }

    /**
     * Obtiene las exposiciones permanentes del centro.
     * 
     * @return un conjunto de las exposiciones de tipo permanente
     */
    public Set<Exposicion> getExposicionesPermanentes() {
        Set<Exposicion> exposicionesPermanentes = new HashSet<>();
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getTipo().equals(TipoExpo.PERMANENTE)) {
                exposicionesPermanentes.add(exposicion);
            }
        }
        return exposicionesPermanentes;
    }

    /**
     * Obtiene las exposiciones publicadas del centro.
     * 
     * @return un conjunto de las exposiciones en estado publicada
     */
    public Set<Exposicion> getExposicionesPublicadas() {
        Set<Exposicion> exposicionesPublicadas = new HashSet<>();
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getEstado().equals(EstadoExposicion.PUBLICADA)) {
                exposicionesPublicadas.add(exposicion);
            }
        }
        return exposicionesPublicadas;
    }

    public void setExposiciones(Set<Exposicion> exposiciones) {
        this.exposiciones = exposiciones;
    }

    /**
     * Añade una exposicion a un centro de exposiciones.
     * 
     * @param exposicion la sala a añadir
     */
    public void addExposicion(Exposicion exposicion) {
        this.exposiciones.add(exposicion);
    }

    /**
     * Elimina una exposicion determinada de un centro de exposiciones.
     * 
     * @param exposicion la sala a eliminar
     */
    public void removeExposicion(Exposicion exposicion) {
        this.exposiciones.remove(exposicion);
    }

    /**
     * Elimina todas las exposiciones de un centro de exposiciones.
     */
    public void removeAllExposiciones() {
        this.exposiciones.clear();
    }

    public Set<Sorteo> getSorteos() {
        return sorteos;
    }

    public void setSorteos(Set<Sorteo> sorteos) {
        this.sorteos = sorteos;
    }

    /**
     * Añade un sorteo determinado a un centro de exposiciones.
     * 
     * @param sorteo el sorteo a añadir
     */
    public void addSorteo(Sorteo sorteo) {
        this.sorteos.add(sorteo);
    }

    /**
     * Elimina un sorteo determinado de un centro de exposiciones.
     * 
     * @param sorteo el sorteo a eliminar
     */
    public void removeSorteo(Sorteo sorteo) {
        this.sorteos.remove(sorteo);
    }

    /**
     * Elimina todos los sorteos de un centro de exposiciones.
     */
    public void removeAllSorteos() {
        this.sorteos.clear();
    }

    public Set<Obra> getObras() {
        return obras;
    }

    public void setObras(Set<Obra> obras) {
        this.obras = obras;
    }

    /**
     * Añade una obra determinada a un centro de exposiciones.
     * 
     * @param obra la obra a añadir
     */
    public void addObra(Obra obra) {
        this.obras.add(obra);
    }

    /**
     * Elimina una obra determinada de un centro de exposiciones.
     * 
     * @param obra la obra a eliminar
     */
    public void removeObra(Obra obra) {
        this.obras.remove(obra);
    }

    /**
     * Elimina todas las obras de un centro de exposiciones.
     */
    public void removeAllObras() {
        this.obras.clear();
    }

    public Set<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }

    /**
     * Añade un empleado determinado a un centro de exposiciones.
     * 
     * @param empleado el empleado a añadir
     */
    public void addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
    }

    /**
     * Elimina un empleado determinado de un centro de exposiciones.
     * 
     * @param empleado el empleado a eliminar
     */
    public void removeEmpleado(Empleado empleado) {
        this.empleados.remove(empleado);
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    public Set<Descuento> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(Set<Descuento> descuentos) {
        this.descuentos = descuentos;
    }

    /**
     * Añade un descuento determinado a un centro de exposiciones.
     * 
     * @param descuento el descuento a añadir
     */
    public void addDescuento(Descuento descuento) {
        this.descuentos.add(descuento);
    }

    /**
     * Elimina un descuento determinado de un centro de exposiciones.
     * 
     * @param empleado el empleado a eliminar
     */
    public void removeDescuento(Descuento descuento) {
        this.descuentos.remove(descuento);
    }

    /**
     * Elimina todos los desceuntos de un centro de exposiciones.
     */
    public void removeAllDescuentos() {
        this.descuentos.clear();
    }

    /**
     * Crea una cadena que representa un centro de exposición.
     * 
     * @return La cadena de representación del centro de exposición
     */
    public String toString() {
        return "CentroExposicion [ID=" + ID + ", nombre=" + nombre + ", horaApertura=" + horaApertura + ", horaCierre="
                + horaCierre + ", localizacion=" + localizacion + ", contraseniaEmpleado=" + contraseniaEmpleado
                + ", contraseniaGestor=" + contraseniaGestor + ", sancion=" + sancion + ", salas="
                + salas.toString() + ", exposiciones=" + exposiciones.toString() + ", sorteos=" + sorteos + ", obras="
                + obras
                + ", empleados=" + empleados.toString() + ", gestor=" + gestor + ", descuentos=" + descuentos + "]";
    }

}
