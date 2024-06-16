package gui.modelo.centroExposicion;

import java.time.LocalDate;
import java.time.LocalTime;

import es.uam.eps.padsof.tickets.NonExistentFileException;
import es.uam.eps.padsof.tickets.TicketSystem;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;
import gui.modelo.expofy.*;
import gui.modelo.exposicion.*;
import gui.modelo.obra.Estado;
import gui.modelo.obra.Obra;
import gui.modelo.sala.Sala;

import java.io.File;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase CentroExposicion.
 * Esta clase ofrece funcionalidades para manejar las salas, exposiciones,
 * obras, empleados, y descuentos dentro del centro de exposición.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */

public class CentroExposicion implements Serializable {
    private String nombre;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private String localizacion;
    private String contraseniaEmpleado;
    private String contraseniaGestor;
    private Integer sancion = 0;
    private Set<Exposicion> exposiciones = new HashSet<>();
    private Set<Sorteo> sorteos = new HashSet<>();
    private Set<Obra> obras = new HashSet<>();
    private Set<Empleado> empleados = new HashSet<>();
    private Set<Sala> salas = new HashSet<>();
    private Set<Actividad> actividades = new HashSet<>();
    private Gestor gestor;

    /**
     * Genera el codigo hash para un centro de exposición.
     * 
     * @return el código hash generado.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((localizacion == null) ? 0 : localizacion.hashCode());
        return result;
    }

    /**
     * Comprueba si este centro de exposición es igual al objeto proporcionado.
     * Dos centros de exposición se consideran iguales si tienen el mismo nombre y
     * localización.
     * 
     * @param obj el objeto con el que comparar este centro de exposición.
     * 
     * @return true si los objetos son iguales, false en caso contrario.
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CentroExposicion other = (CentroExposicion) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (localizacion == null) {
            if (other.localizacion != null)
                return false;
        } else if (!localizacion.equals(other.localizacion))
            return false;
        return true;
    }

    /**
     * Constructor de un centro de exposición con los parámetros proporcionados.
     *
     * @param nombre              el nombre del centro de exposición
     * @param horaApertura        la hora de apertura del centro
     * @param horaCierre          la hora de cierre del centro
     * @param localizacion        la ubicación del centro
     * @param contraseniaEmpleado la contraseña para los empleados del centro
     * @param contraseniaGestor   la contraseña para el gestor del centro
     * @param gestor              el gestor del centro
     * @param salas               el conjunto inicial de salas
     */
    public CentroExposicion(String nombre, LocalTime horaApertura,
            LocalTime horaCierre, String localizacion,
            String contraseniaEmpleado, String contraseniaGestor,
            Gestor gestor, Set<Sala> salas) {
        this.nombre = nombre;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.localizacion = localizacion;
        this.contraseniaEmpleado = contraseniaEmpleado;
        this.contraseniaGestor = contraseniaGestor;
        this.gestor = gestor;
        this.salas = salas;
    }

    /**
     * Obtiene el nombre de la entidad.
     * 
     * @return El nombre actual de la entidad.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la hora de apertura.
     * 
     * @return La hora de apertura en formato de uso horario.
     */
    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    /**
     * Establece la hora de apertura.
     * 
     * @param horaApertura La hora de apertura a establecer.
     */
    public void setHoraApertura(LocalTime horaApertura) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes cambiar la hora de apertura si no eres el gestor");
            return;
        }

        this.horaApertura = horaApertura;
    }

    /**
     * Obtiene la hora de cierre.
     * 
     * @return La hora de cierre en formato de uso horario.
     */
    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    /**
     * Establece la hora de cierre.
     * 
     * @param horaCierre La hora de cierre a establecer.
     */
    public void setHoraCierre(LocalTime horaCierre) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes cambiar la hora de cierre si no eres el gestor");
            return;
        }

        this.horaCierre = horaCierre;
    }

    /**
     * Devuelve la localización de la entidad.
     * 
     * @return La localización actual.
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     * Accede a la contraseña de empleado.
     * 
     * @return La contraseña de empleado.
     */
    public String getContraseniaEmpleado() {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes acceder a la contraseña de empleado si no eres el gestor");
            return null;
        }

        return contraseniaEmpleado;
    }

    /**
     * Establece una nueva contraseña para empleado.
     * 
     * @param contraseniaEmpleado La nueva contraseña a asignar.
     */
    public void setContraseniaEmpleado(String contraseniaEmpleado) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes cambiar la contraseña de empleado si no eres el gestor");
            return;
        }

        this.contraseniaEmpleado = contraseniaEmpleado;
    }

    /**
     * Obtiene la contraseña de gestor.
     * 
     * @return La contraseña de gestor.
     */
    public String getContraseniaGestor() {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes acceder a la contraseña de gestor si no eres el gestor");
            return null;
        }

        return contraseniaGestor;
    }

    /**
     * Devuelve el valor de la sanción actual.
     * 
     * @return El valor de la sanción.
     */
    public Integer getSancion() {
        return sancion;
    }

    /**
     * Establece el valor de la sanción.
     * 
     * @param sancion El nuevo valor de la sanción a asignar.
     */
    public void setSancion(Integer sancion) {
        this.sancion = sancion;
    }

    /**
     * Obtiene las actividades del centro de exposiciones.
     * 
     * @return Un conjunto de actividades.
     */
    public Set<Actividad> getActividades() {
        return actividades;
    }

    /**
     * Establece las actividades del centro de exposiciones.
     * 
     * @param actividades Las actividades a asignar.
     */
    public Actividad getActividadPorNombre(String nombre) {
        for (Actividad actividad : actividades) {
            if (actividad.getNombre().equals(nombre)) {
                return actividad;
            }
        }

        return null;
    }

    /**
     * Añade una actividad al centro de exposiciones.
     * 
     * @param actividades Las actividades a asignar.
     */
    public Boolean addActividad(String nombre, TipoActividad tipo, String descripcion, Integer maxParticipantes,
            LocalDate fecha, LocalTime hora, Sala salaCelebracion) {

        for (Actividad actividad : actividades) {
            if (actividad.getSalaCelebracion().equals(salaCelebracion) && actividad.getFecha().equals(fecha)
                    && actividad.getHora().equals(hora)) {
                System.out.println("La sala ya está siendo utilizada por otra actividad");
                return false;
            }
        }

        if (salaCelebracion.getAforo() < maxParticipantes) {
            System.out.println("El número de participantes supera el aforo de la sala");
            return false;
        }

        Actividad actividad = new Actividad(nombre, tipo, descripcion, maxParticipantes, fecha, hora, salaCelebracion);

        if (actividades.add(actividad) == false) {
            System.out.println("La actividad ya está en el centro de exposiciones");
            return false;
        }

        return true;
    }

    /**
     * Elimina una actividad del centro de exposiciones.
     * 
     * @param actividad La actividad a eliminar.
     */
    public void removeActividad(Actividad actividad) {
        actividades.remove(actividad);
    }

    /**
     * Añade un cliente a una actividad si se realiza en la fecha correcta y hay
     * espacio disponible.
     * 
     * @param actividad La actividad a la que se quiere inscribir el cliente.
     * @param NIF       El NIF del cliente que se quiere inscribir.
     * @return true si el cliente se inscribe correctamente, false en caso
     *         contrario.
     */
    public Boolean inscribirClienteActividad(Actividad actividad, String NIF) {

        if (actividad.getFecha().isBefore(LocalDate.now())
                || (actividad.getHora().isBefore(LocalTime.now()) && actividad.getFecha().isEqual(LocalDate.now()))) {
            System.out.println("La actividad ya ha pasado");
            return false;
        }

        if (actividad.addParticipante(NIF) == false) {
            System.out.println("No se ha podido inscribir al cliente en la actividad");
            return false;
        }

        return true;
    }

    /**
     * Retorna el conjunto de salas asociadas a la entidad.
     * 
     * @return Un conjunto de salas.
     */
    public Set<Sala> getSalas() {
        return salas;
    }

    /**
     * Asigna un nuevo conjunto de salas a la entidad.
     * 
     * @param salas El conjunto de salas a asignar.
     */
    public void setSalas(Set<Sala> salas) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir salas si no eres el gestor");
            return;
        }

        this.salas = salas;
    }

    /**
     * Añade una sala a un centro de exposiciones.
     * 
     * @param sala la sala a añadir
     * @return true si la sala se añade correctamente, false en caso contrario
     */
    public Boolean addSala(Sala sala) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir salas si no eres el gestor");
            return false;
        }

        if (this.salas.add(sala) == false) {
            System.out.println("La sala ya está en el centro de exposiciones");
            return false;
        }

        return true;
    }

    /**
     * Elimina una sala de un centro de exposiciones.
     * 
     * @param sala la sala a eliminar
     * @return true si la sala se elimina correctamente, false en caso contrario
     */
    public Boolean removeSala(Sala sala) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes eliminar salas si no eres el gestor");
            return false;
        }

        for (Exposicion exposicion : exposiciones) {
            for (SalaExposicion salaExpo : exposicion.getSalas()) {
                if (salaExpo.getSala().equals(sala)) {
                    System.out.println("No se puede eliminar una sala que está siendo utilizada por una exposición");
                    return false;
                }
            }
        }

        if (this.salas.remove(sala) == false) {
            System.out.println("La sala no está en el centro de exposiciones");
            return false;
        }

        return true;
    }

    /**
     * Obtiene una sala por su nombre.
     * 
     * @param nombre el nombre de la sala a buscar
     * @return la sala con el nombre proporcionado, o null si no existe
     */
    public Sala getSalaPorNombre(String nombre) {
        for (Sala sala : salas) {
            if (sala.getNombre().equals(nombre)) {
                return sala;
            }
        }
        return null;
    }

    /**
     * Obtiene una sub-sala por su nombre.
     * 
     * @param nombre el nombre de la sub-sala a buscar
     * @return la sub-sala con el nombre proporcionado, o null si no existe
     */
    public Sala getSubSalaPorNombre(String nombre) {
        for (Sala sala : salas) {
            Sala resultado = buscarSalaRecursiva(sala, nombre);
            if (resultado != null) {
                return resultado;
            }
        }
        return null;
    }

    /**
     * Busca una sala recursivamente.
     * 
     * @param sala          la sala a buscar
     * @param nombreBuscado el nombre de la sala a buscar
     * @return la sala con el nombre proporcionado, o null si no existe
     */
    private Sala buscarSalaRecursiva(Sala sala, String nombreBuscado) {
        if (sala.getNombre().equals(nombreBuscado)) {
            return sala;
        }
        for (Sala subSala : sala.getSubSalas()) {
            Sala resultado = buscarSalaRecursiva(subSala, nombreBuscado);
            if (resultado != null) {
                return resultado;
            }
        }
        return null;
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
     * Obtiene una exposición por su nombre.
     * 
     * @param nombre el nombre de la exposición a buscar
     * @return la exposición con el nombre proporcionado, o null si no existe
     */
    public Exposicion getExposicionPorNombre(String nombre) {
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getNombre().equals(nombre)) {
                return exposicion;
            }
        }
        return null;
    }

    /**
     * Obtiene un conjunto de exposiciones que están actualmente publicadas o
     * prorrogadas filtrando todas las exposiciones disponibles y retornando solo
     * aquellas cuyo estado es {@link EstadoExposicion#PUBLICADA} o
     * {@link EstadoExposicion#PRORROGADA}.
     *
     * @return Un {@code Set<Exposicion>} que contiene todas las exposiciones en
     *         estado Publicada o Prorrogada.
     */
    public Set<Exposicion> getExposicionesPublicadas() {
        Set<Exposicion> exposicionesPublicadas = new HashSet<>();
        for (Exposicion exposicion : exposiciones) {
            if (exposicion.getEstado().equals(EstadoExposicion.PUBLICADA)
                    || exposicion.getEstado().equals(EstadoExposicion.PRORROGADA)) {
                exposicionesPublicadas.add(exposicion);
            }
        }
        return exposicionesPublicadas;
    }

    /**
     * Obtiene las exposiciones disponibles en el centro por un rango de fechas.
     *
     * @param fechaInicio la fecha de inicio
     * @param fechaFinal  la fecha de fin
     * @return un conjunto de exposiciones disponibles en el rango de fechas dado
     */
    public Set<Exposicion> getExposicionesPorFecha(LocalDate fechaInicio, LocalDate fechaFinal) {
        if (fechaInicio.isAfter(fechaFinal) || fechaFinal.isBefore(fechaInicio)) {
            System.out.println("Las fechas se han introducido de forma incorrecta");
            return null;
        }

        if (fechaFinal.isBefore(LocalDate.now())) {
            System.out.println("La fecha final no puede ser anterior a la fecha actual");
            return null;
        }

        Set<Exposicion> exposicionesPorFecha = new HashSet<>();
        for (Exposicion exposicion : getExposicionesPublicadas()) {
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
        for (Exposicion exposicion : getExposicionesPublicadas()) {
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
        for (Exposicion exposicion : getExposicionesPublicadas()) {
            if (exposicion.getTipo().equals(TipoExpo.PERMANENTE)) {
                exposicionesPermanentes.add(exposicion);
            }
        }
        return exposicionesPermanentes;
    }

    /**
     * Obtiene las exposiciones por tipo de obra.
     * 
     * @param tipoObra el tipo de obra a buscar
     * @return un conjunto de exposiciones que contienen obras del tipo
     *         proporcionado
     */
    public Set<Exposicion> getExposicionesPorTipoObra(Class<? extends Obra> tipoObra) {
        Set<Exposicion> exposicionesPorTipoObra = new HashSet<>();
        for (Exposicion exposicion : getExposicionesPublicadas()) {
            for (SalaExposicion salaExpo : exposicion.getSalas()) {
                for (Obra obra : salaExpo.getObras()) {
                    if (tipoObra.isInstance(obra)) {
                        exposicionesPorTipoObra.add(exposicion);
                        break;
                    }
                }
            }
        }
        return exposicionesPorTipoObra;
    }

    /**
     * Añade una exposicion a un centro de exposiciones.
     * 
     * @param exposicion la sala a añadir
     * @return true si la exposicion se añade correctamente, false en caso contrario
     */
    public Boolean addExposicion(Exposicion exposicion) {

        if (this.exposiciones.add(exposicion) == false) {
            System.out.println("La exposición ya está en el centro de exposiciones");
            return false;
        }

        return true;
    }


    /**
     * Elimina una exposicion determinada de un centro de exposiciones.
     * 
     * @param exposicion la sala a eliminar
     * @return true si la exposicion se elimina correctamente, false en caso
     *         contrario
     */
    public Boolean removeExposicion(Exposicion exposicion) {

        if (!exposicion.getEstado().equals(EstadoExposicion.EN_CREACION)) {
            System.out.println("No se puede eliminar una exposición que no este en creación");
            return false;
        }

        if (this.exposiciones.remove(exposicion) == false) {
            System.out.println("La exposición no está en el centro de exposiciones");
            return false;
        }

        return true;
    }

    /**
     * Retorna el conjunto de sorteos relacionados al centro.
     * 
     * @return Un conjunto de sorteos.
     */
    public Set<Sorteo> getSorteos() {
        return sorteos;
    }

    /**
     * Establece el los sorteos del centro.
     * 
     * @param sorteos Los sorteos a asignar.
     */
    public void setSorteos(Set<Sorteo> sorteos) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir sorteos si no eres el gestor");
            return;
        }

        this.sorteos = sorteos;
    }

    /**
     * Añade un sorteo determinado a un centro de exposiciones.
     * 
     * @param sorteo el sorteo a añadir
     */
    public Boolean addSorteo(Sorteo sorteo) {

        if (this.sorteos.add(sorteo) == false) {
            System.out.println("El sorteo ya está en el centro de exposiciones");
            return false;
        }

        return true;
    }

    /**
     * Configura un sorteo de tipo dia y hora.
     * 
     * @param exposicion  la exposicion de la que se quiere configurar el sorteo
     * @param fechaSorteo la fecha en la que se realizará el sorteo
     * @param n_entradas  el número de entradas que se sortearán
     * @param dia         el día en el que se realizará la exposición
     * @param hora        la hora en la que se realizará la exposición
     */
    public Boolean confgiurarSorteoDiaHora(Exposicion exposicion, LocalDate fechaSorteo, int n_entradas, LocalDate dia,
            Hora hora) {

        if (exposicion == null) {
            System.out.println("No se ha podido añadir el sorteo");
            return false;
        }

        if (exposicion.getEstado().equals(EstadoExposicion.CANCELADA)
                || exposicion.getEstado().equals(EstadoExposicion.CERRADATEMPORALMENTE)) {
            System.out.println("No se puede sortear una exposición cancelada o cerrada temporalmente");
            return false;
        }

        if (exposicion.getFechaFin().isBefore(fechaSorteo) || exposicion.getFechaInicio().isAfter(fechaSorteo)) {
            System.out.println("La fecha de sorteo no está dentro del rango de fechas de la exposición");
            return false;
        }

        if (dia.isAfter(exposicion.getFechaFin()) || dia.isBefore(exposicion.getFechaInicio())) {
            System.out.println("El día del sorteo no está dentro del rango de fechas de la exposición");
            return false;
        }

        SorteoDiaHora sorteo = new SorteoDiaHora(exposicion, fechaSorteo, n_entradas, dia, hora);
        if (addSorteo(sorteo) == false) {
            System.out.println("No se ha podido añadir el sorteo");
            return false;
        }

        return true;
    }

    /**
     * Configura un sorteo de tipo exposición.
     * 
     * @param exposicion  la exposicion de la que se quiere configurar el sorteo
     * @param fechaSorteo la fecha en la que se realizará el sorteo
     * @param n_entradas  el número de entradas que se sortearán
     */
    public Boolean confgiurarSorteoExposicion(Exposicion exposicion, LocalDate fechaSorteo, int n_entradas) {
        if (exposicion == null) {
            System.out.println("No se ha podido añadir el sorteo");
            return false;
        }

        if (exposicion.getEstado().equals(EstadoExposicion.CANCELADA)
                || exposicion.getEstado().equals(EstadoExposicion.CERRADATEMPORALMENTE)) {
            System.out.println("No se puede sortear una exposición cancelada o cerrada temporalmente");
            return false;
        }

        if (exposicion.getFechaFin().isBefore(fechaSorteo) || exposicion.getFechaInicio().isAfter(fechaSorteo)) {
            System.out.println("La fecha de sorteo no está dentro del rango de fechas de la exposición");
            return false;
        }

        SorteoExpo sorteo = new SorteoExpo(exposicion, fechaSorteo, n_entradas);
        if (addSorteo(sorteo) == false) {
            System.out.println("No se ha podido añadir el sorteo");
            return false;
        }

        return true;
    }

    /**
     * Configura un sorteo de tipo fechas.
     * 
     * @param exposicion  la exposicion de la que se quiere configurar el sorteo
     * @param fechaSorteo la fecha en la que se realizará el sorteo
     * @param n_entradas  el número de entradas que se sortearán
     * @param fechaInicio la fecha de inicio de la exposición
     * @param fechaFin    la fecha de fin de la exposición
     */
    public Boolean confgiurarSorteoFechas(Exposicion exposicion, LocalDate fechaSorteo, int n_entradas,
            LocalDate fechaInicio, LocalDate fechaFin) {
        if (exposicion == null) {
            System.out.println("No se ha podido añadir el sorteo");
            return false;
        }

        if (exposicion.getEstado().equals(EstadoExposicion.CANCELADA)
                || exposicion.getEstado().equals(EstadoExposicion.CERRADATEMPORALMENTE)) {
            System.out.println("No se puede sortear una exposición cancelada o cerrada temporalmente");
            return false;
        }

        if (fechaFin.isBefore(fechaInicio)) {
            System.out.println("La fecha de fin no puede ser anterior a la fecha de inicio");
            return false;
        }

        if (fechaInicio.isAfter(exposicion.getFechaFin()) || fechaInicio.isBefore(exposicion.getFechaInicio())
                || fechaFin.isBefore(exposicion.getFechaInicio()) || fechaFin.isAfter(exposicion.getFechaFin())) {
            System.out.println("El rango de fechas del sorteo no está dentro del rango de fechas de la exposición");
            return false;
        }

        if (fechaInicio.isBefore(fechaSorteo)) {
            System.out.println("La fecha de sorteo no es correcta");
            return false;
        }

        SorteoFechas sorteo = new SorteoFechas(exposicion, fechaSorteo, n_entradas, fechaInicio, fechaFin);

        if (addSorteo(sorteo) == false) {
            System.out.println("No se ha podido añadir el sorteo");
            return false;
        }

        return true;
    }

    /**
     * Elimina un sorteo determinado de un centro de exposiciones.
     * 
     * @param sorteo el sorteo a eliminar
     */
    public void removeSorteo(Sorteo sorteo) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes eliminar sorteos si no eres el gestor");
            return;
        }

        this.sorteos.remove(sorteo);
    }

    /**
     * Obtiene los sorteos que están activos hasta la fecha actual.
     * Un sorteo se considera activo si su fecha de sorteo es hoy o ha pasado.
     *
     * @return Un conjunto de sorteosactivos.
     */
    public Set<Sorteo> getSorteosActivos() {
        Set<Sorteo> sorteosActivos = new HashSet<>();
        for (Sorteo sorteo : sorteos) {
            if (sorteo.getFechaSorteo().isBefore(LocalDate.now()) || sorteo.getFechaSorteo().equals(LocalDate.now())) {
                sorteosActivos.add(sorteo);
            }
        }
        return sorteosActivos;
    }

    /**
     * Obtiene todas las obras gestionadas por el centro de exposición.
     *
     * @return Un conjunto de obras.
     */
    public Set<Obra> getObras() {
        return obras;
    }

    /**
     * Obtiene las obras almacenadas
     * 
     * @return Un conjunto de obras almacenadas.
     */
    public Set<Obra> getObrasAlmacenadas() {
        Set<Obra> obrasAlmacenadas = new HashSet<>();
        for (Obra obra : obras) {
            if (obra.getEstado().equals(Estado.ALMACENADA)) {
                obrasAlmacenadas.add(obra);
            }
        }
        return obrasAlmacenadas;
    }

    /**
     * Obtiene una obra del centro de exposición a partir de su nombre.
     * 
     * @param nombre el nombre de la obra a buscar
     * @return la obra con el nombre proporcionado, o null si no existe
     */
    public Obra getObraPorNombre(String nombre) {
        for (Obra obra : obras) {
            if (obra.getNombre().equals(nombre)) {
                return obra;
            }
        }
        return null;
    }

    /**
     * Establece el conjunto de obras gestionadas por el centro de exposición.
     *
     * @param obras el conjunto de obras a establecer.
     */

    public void setObras(Set<Obra> obras) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir obras si no eres el gestor");
            return;
        }

        this.obras = obras;
    }

    /**
     * Añade una obra determinada a un centro de exposiciones.
     * 
     * @param obra la obra a añadir
     * @return true si la obra se añade correctamente, false en caso contrario
     */
    public Boolean addObra(Obra obra) {

        if (this.obras.add(obra) == false) {
            System.out.println("La obra ya está en el centro de exposiciones");
            return false;
        }

        obra.almacenarObra();
        return true;
    }

    /**
     * Elimina una obra determinada de un centro de exposiciones.
     * 
     * @param obra la obra a eliminar
     */
    public Boolean removeObra(Obra obra) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes eliminar obras si no eres el gestor");
            return false;
        }

        if (obra.getEstado().equals(Estado.EXPUESTA)) {
            System.out.println("No se puede eliminar una obra que está expuesta");
            return false;
        }

        if (obra.getEstado().equals(Estado.PRESTADA)) {
            System.out.println("No se puede eliminar una obra que está prestada");
            return false;
        }

        if (this.obras.remove(obra) == false) {
            System.out.println("La obra no está en el centro de exposiciones");
            return false;
        }

        return true;
    }

    /**
     * Obtiene todos los empleados del centro de exposición.
     *
     * @return Un conjunto de empleados.
     */
    public Set<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * Obtiene un empleado del centro de exposición a partir de su NumSS.
     *
     * @return El empleado o null si no existe.
     */
    public Empleado getEmpleado(String NumSS) {
        for (Empleado empleado : empleados) {
            if (empleado.getNumSS().equals(NumSS)) {
                return empleado;
            }
        }
        return null;
    }

    /**
     * Establece el conjunto de empleados del centro de exposición.
     *
     * @param empleados el conjunto de empleados a establecer.
     */
    public void setEmpleados(Set<Empleado> empleados) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir empleados si no eres el gestor");
            return;
        }

        this.empleados = empleados;
    }

    /**
     * Añade un empleado determinado a un centro de exposiciones.
     * 
     * @param empleado el empleado a añadir
     * @return true si el empleado se añade correctamente, false en caso contrario
     */
    public Boolean addEmpleado(Empleado empleado) {
        if (this.empleados.add(empleado) == false) {
            System.out.println("El empleado ya esta registrado en el centro");
            return false;
        }

        return true;
    }

    /**
     * Elimina un empleado determinado de un centro de exposiciones.
     * 
     * @param empleado el empleado a eliminar
     */
    public Boolean removeEmpleado(Empleado empleado) {
        if (gestor.isLoged()) {
            System.out.println("No puedes eliminar empleados si no eres el gestor");
            return false;
        }

        if (this.empleados.remove(empleado) == false) {
            System.out.println("No existe el empleado");
            return false;
        }

        return true;
    }

    /**
     * Cambia la temperatura de una sala.
     * 
     * @param empleado    el empleado que cambia la temperatura de la sala
     * @param temperatura la temperatura a la que se quiere cambiar
     * @param sala        la sala a la que se le quiere cambiar la temperatura
     * 
     * @return true si la temperatura se cambia correctamente, false en caso
     *         contrario
     * 
     */
    public Boolean setSalaTemperatura(Sala sala, Integer temperatura, Empleado empleado) {
        if (empleados.contains(empleado) == false) {
            System.out.println("El empleado no trabaja en el centro de exposiciones");
            return false;
        }

        if (empleado.isLoged() == false) {
            System.out.println("No puedes cambiar la temperatura si no estás logeado");
            return false;
        }

        if (empleado.getPermisoControl() == false) {
            System.out.println("No tienes permiso para controlar la temperatura de la sala");
            return false;
        }

        if (salas.contains(sala) == false) {
            System.out.println("La sala no pertenece al centro de exposiciones");
            return false;
        }

        sala.setTemperatura(temperatura);
        return true;
    }

    /**
     * Cambia la humedad de una sala.
     * 
     * @param empleado el empleado que cambia la humedad de la sala
     * @param humedad  la humedad a la que se quiere cambiar
     * @param sala     la sala a la que se le quiere cambiar la humedad
     * 
     * @return true si la humedad se cambia correctamente, false en caso contrario
     */
    public Boolean setSalaHumedad(Sala sala, Integer humedad, Empleado empleado) {
        if (empleados.contains(empleado) == false) {
            System.out.println("El empleado no trabaja en el centro de exposiciones");
            return false;
        }

        if (empleado.isLoged() == false) {
            System.out.println("No puedes cambiar la temperatura si no estás logeado");
            return false;
        }

        if (empleado.getPermisoControl() == false) {
            System.out.println("No tienes permiso para controlar la temperatura de la sala");
            return false;
        }

        if (salas.contains(sala) == false) {
            System.out.println("La sala no pertenece al centro de exposiciones");
            return false;
        }

        sala.setHumedad(humedad);
        return true;
    }

    /**
     * Obtiene el gestor del centro de exposición.
     *
     * @return El {@link Gestor} del centro.
     */
    public Gestor getGestor() {
        return gestor;
    }

    /**
     * Establece el gestor del centro de exposición.
     *
     * @param gestor el gestor a establecer.
     */
    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    /**
     * Permite el login de un empleado al sistema, verificando su NIF, número de
     * seguridad social y contraseña.
     *
     * @param NIF         El NIF del empleado.
     * @param contrasenia La contraseña de acceso.
     * @return true si el login es exitoso, false en caso contrario.
     */
    public Boolean loginEmpleado(String numSS, String contrasenia) {
        for (Empleado e : empleados) {
            if (e.getNumSS().equals(numSS) && contrasenia.equals(contraseniaEmpleado)) {
                e.logIn();
                return true;
            }
        }
        return false;
    }

    /**
     * Permite la venta de entradas para una exposición en un horario determinado.
     * 
     * @param exposicion la exposicion que se quiere vender
     * @param hora       la hora en la que se quiere vender
     * @param nEntradas  el número de entradas que se quieren vender
     * @return true si la venta es exitosa, false en caso contrario
     */
    public Boolean venderEntrada(Exposicion exposicion, Hora hora, Integer nEntradas) {
        LocalDate fecha = LocalDate.now();

        // Verifica si la fecha de la visita está dentro del rango de fechas de la
        // exposición.
        if (fecha.isBefore(exposicion.getFechaInicio()) || fecha.isAfter(exposicion.getFechaFin())) {
            System.out.println("La fecha no está dentro del rango de la exposición");
            return false;
        }

        // Verifica el estado de la exposición, solo procede si está Prorrogada o
        // Publicada.
        if (!exposicion.getEstado().equals(EstadoExposicion.PRORROGADA)
                && !exposicion.getEstado().equals(EstadoExposicion.PUBLICADA)) {
            System.out.println("La exposición no está disponible");
            return false;
        }

        // Asegura que la fecha y la hora de la visita coincidan.
        if (!fecha.equals(hora.getFecha())) {
            System.out.println("La fecha no coincide con la fecha de la hora");
            return false;
        }

        if (nEntradas <= 0) {
            System.out.println("El número de entradas no puede ser menor o igual a 0");
            return false;
        }

        // Verifica la disponibilidad de entradas para la hora seleccionada.
        if (nEntradas >= hora.getCountEntradas() || nEntradas > hora.getCountEntradas()) {
            System.out.println("No hay suficientes entradas disponibles");
            return false;
        }

        // Procede con la compra, actualizando las estadísticas y asignando entradas.
        Estadisticas estadisticas = exposicion.getEstadisticas();
        int i;
        for (i = 0; i < nEntradas; i++) {
            hora.entradaVendida();
            estadisticas.incrementarTicketsVendidos();
            estadisticas.incrementarIngresosTotales(exposicion.getPrecio());
        }

        try {
            TicketSystem.createTicket(
                    new Ticket(exposicion, exposicion.getPrecio() * nEntradas, nEntradas, fecha, hora),
                    "." + File.separator + "gui" + File.separator + "resources");
        } catch (NonExistentFileException e) {
            return false;
        } catch (UnsupportedImageTypeException e) {
            return false;
        }
        return true;
    }

    /**
     * Permite el login del gestor al sistema, verificando su NIF y contraseña.
     *
     * @param contrasenia La contraseña del gestor.
     * @return true si el login es exitoso, false en caso contrario.
     */
    public Boolean loginGestor(String contrasenia) {
        if (contrasenia.equals(contraseniaGestor)) {
            gestor.logIn();
            return true;
        }
        return false;
    }

    /**
     * Crea una cadena que representa un centro de exposición.
     * 
     * @return La cadena de representación del centro de exposición.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Detalles centro de exposición:\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Hora de Apertura: ").append(horaApertura).append("\n");
        sb.append("Hora de Cierre: ").append(horaCierre).append("\n");
        sb.append("Localización: ").append(localizacion).append("\n");
        sb.append("Contraseña Empleado: ").append(contraseniaEmpleado).append("\n");
        sb.append("Contraseña Gestor: ").append(contraseniaGestor).append("\n");
        sb.append("Sanción: ").append(sancion).append("\n");
        sb.append("Salas: ").append(salas).append("\n");
        sb.append("Exposiciones: ").append(exposiciones).append("\n");
        sb.append("Sorteos: ").append(sorteos).append("\n");
        sb.append("Obras: ").append(obras).append("\n");
        sb.append("Empleados: ").append(empleados).append("\n");
        sb.append("Gestor: ").append(gestor).append("\n");

        return sb.toString();
    }
}
