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

    public CentroExposicion(String nombre, LocalTime horaApertura, LocalTime horaCierre, String localizacion,
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

    public void addSala(Sala sala) {
        this.salas.add(sala);
    }

    public void removeSala(Sala sala) {
        this.salas.remove(sala);
    }

    public Set<Exposicion> getExposiciones() {
        return exposiciones;
    }

    public Set<Exposicion> getExposicionesPorFecha(LocalDate fechaInicio, LocalDate fechaFinal) {
        Set<Exposicion> exposicionesPorFecha = new HashSet<>();
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getFechaInicio().isAfter(fechaInicio) && exposicion.getFechaFin().isBefore(fechaFinal)) {
                exposicionesPorFecha.add(exposicion);
            }
        }
        return exposicionesPorFecha;
    }

    public Set<Exposicion> getExposicionesTemporales() {
        Set<Exposicion> exposicionesTemporales = new HashSet<>();
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getTipo().equals(TipoExpo.TEMPORAL)) {
                exposicionesTemporales.add(exposicion);
            }
        }
        return exposicionesTemporales;
    }

    public Set<Exposicion> getExposicionesPermanentes() {
        Set<Exposicion> exposicionesPermanentes = new HashSet<>();
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getTipo().equals(TipoExpo.PERMANENTE)) {
                exposicionesPermanentes.add(exposicion);
            }
        }
        return exposicionesPermanentes;
    }

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

    public void addExposicion(Exposicion exposicion) {
        this.exposiciones.add(exposicion);
    }

    public void removeExposicion(Exposicion exposicion) {
        this.exposiciones.remove(exposicion);
    }   

    public void removeAllExposiciones() {
        this.exposiciones.clear();
    }

    public Set<Sorteo> getSorteos() {
        return sorteos;
    }

    public void setSorteos(Set<Sorteo> sorteos) {
        this.sorteos = sorteos;
    } 

    public void addSorteo(Sorteo sorteo) {
        this.sorteos.add(sorteo);
    }

    public void removeSorteo(Sorteo sorteo) {
        this.sorteos.remove(sorteo);
    }

    public void removeAllSorteos() {
        this.sorteos.clear();
    }

    public Set<Obra> getObras() {
        return obras;
    }

    public void setObras(Set<Obra> obras) {
        this.obras = obras;
    }

    public void addObra(Obra obra) {
        this.obras.add(obra);
    } 

    public void removeObra(Obra obra) {
        this.obras.remove(obra);
    }

    public void removeAllObras() {
        this.obras.clear();
    }

    public Set<Empleado> getEmpleados() {
        return empleados;
    } 

    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }

    public void addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
    }

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

    public void addDescuento(Descuento descuento) {
        this.descuentos.add(descuento);
    }

    public void removeDescuento(Descuento descuento) {
        this.descuentos.remove(descuento);
    }   

    public void removeAllDescuentos() {
        this.descuentos.clear();
    }

    public String toString() {
        return "CentroExposicion [ID=" + ID + ", nombre=" + nombre + ", horaApertura=" + horaApertura + ", horaCierre="
                + horaCierre + ", localizacion=" + localizacion + ", contraseniaEmpleado=" + contraseniaEmpleado
                + ", contraseniaGestor=" + contraseniaGestor + ", sancion=" + sancion + ", salas="
                + salas.toString() + ", exposiciones=" + exposiciones.toString() + ", sorteos=" + sorteos + ", obras=" + obras
                + ", empleados=" + empleados.toString() + ", gestor=" + gestor + ", descuentos=" + descuentos + "]";
    }

}
