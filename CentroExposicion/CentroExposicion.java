package CentroExposicion;

import java.time.LocalDate;
import java.time.LocalTime;

import Sala.Sala;
import Expofy.*;
import java.io.File;

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import Exposicion.*;
import Obra.Obra;

/**
 * Clase CentroExposicion.
 * Esta clase ofrece funcionalidades para manejar las salas, exposiciones,
 * obras, empleados, y descuentos dentro del centro de exposición.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */

public class CentroExposicion implements Serializable {
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
    private Set<Sorteo> sorteos = new HashSet<>();
    private Set<Obra> obras = new HashSet<>();
    private Set<Empleado> empleados = new HashSet<>();
    private Set<Descuento> descuentos = new HashSet<>();
    private Set<Sala> salas = new HashSet<>();
    private Gestor gestor;

    /**
     * Comprueba si este centro de exposición es igual a otro objeto.
     * Todos los elementos de cada colección deben ser iguales entre los dos objetos
     * de {@code CentroExposicion} para que se consideren iguales en su totalidad.
     *
     * @param obj el objeto con el que se compara
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
        this.ID = IDcount++;
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
     * Devuelve el identificador único de la entidad.
     * 
     * @return Identificador único del objeto.
     */
    public Integer getID() {
        return ID;
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
     */
    public void addSala(Sala sala) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir salas si no eres el gestor");
            return;
        }

        this.salas.add(sala);
    }

    /**
     * Elimina una sala de un centro de exposiciones.
     * 
     * @param sala la sala a eliminar
     */
    public void removeSala(Sala sala) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes eliminar salas si no eres el gestor");
            return;
        }

        for (Exposicion exposicion : exposiciones) {
            for (SalaExposicion salaExpo : exposicion.getSalas()) {
                if (salaExpo.getSala().equals(sala)) {
                    System.out.println("No se puede eliminar una sala que está siendo utilizada por una exposición");
                    return;
                }
            }
        }
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
     */
    public void addExposicion(Exposicion exposicion) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir exposiciones si no eres el gestor");
            return;
        }
        this.exposiciones.add(exposicion);
    }

    /**
     * Elimina una exposicion determinada de un centro de exposiciones.
     * 
     * @param exposicion la sala a eliminar
     */
    public void removeExposicion(Exposicion exposicion) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes eliminar exposiciones si no eres el gestor");
            return;
        }

        if (exposicion.getEstado().equals(EstadoExposicion.PUBLICADA) || exposicion.getEstado().equals(EstadoExposicion.PRORROGADA)) {
            System.out.println("No se puede eliminar una exposición publicada o prorrogada");
            return;
        }

        this.exposiciones.remove(exposicion);
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
    public void addSorteo(Sorteo sorteo) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir sorteos si no eres el gestor");
            return;
        }

        this.sorteos.add(sorteo);
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
     */
    public void addObra(Obra obra) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir obras si no eres el gestor");
            return;
        }

        if (this.obras.add(obra) == false) {
            System.out.println("La obra ya está en el centro de exposiciones");
        }
        else {
            obra.almecenarObra();
        }
    }

    /**
     * Elimina una obra determinada de un centro de exposiciones.
     * 
     * @param obra la obra a eliminar
     */
    public void removeObra(Obra obra) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes eliminar obras si no eres el gestor");
            return;
        }

        this.obras.remove(obra);
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
     */
    public void addEmpleado(Empleado empleado) {
        if (gestor.isLoged()) {
            System.out.println("No puedes añadir empleados si no eres el gestor");
            return;
        }
        this.empleados.add(empleado);
    }

    /**
     * Elimina un empleado determinado de un centro de exposiciones.
     * 
     * @param empleado el empleado a eliminar
     */
    public void removeEmpleado(Empleado empleado) {
        if (gestor.isLoged()) {
            System.out.println("No puedes eliminar empleados si no eres el gestor");
            return;
        }

        this.empleados.remove(empleado);
    }

    /**
     * Cambia la temperatura de una sala.
     * 
     * @param empleado el empleado que cambia la temperatura de la sala
     * @param temperatura la temperatura a la que se quiere cambiar
     * @param sala la sala a la que se le quiere cambiar la temperatura
     * 
     */
    public void setSalaTemperatura(Sala sala, Integer temperatura, Empleado empleado) {
        if (empleado.getPermisoControl() == false) {
            System.out.println("No tienes permiso para controlar la temperatura de la sala");
            return;
        }
        sala.setTemperatura(temperatura);
    }

    /**
    * Cambia la humedad de una sala.
    * 
    * @param empleado el empleado que cambia la humedad de la sala
    * @param humedad la humedad a la que se quiere cambiar
    * @param sala la sala a la que se le quiere cambiar la humedad
    * 
    */
    public void setSalaHumedad(Sala sala, Integer humedad, Empleado empleado) {
        if (empleado.getPermisoControl() == false) {
            System.out.println("No tienes permiso para controlar la humedad de la sala");
            return;
        }
        sala.setHumedad(humedad);
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
     * Obtiene los descuentos disponibles en el centro de exposición.
     *
     * @return Un conjunto de {@link Descuento}.
     */
    public Set<Descuento> getDescuentos() {
        return descuentos;
    }

    /**
     * Establece los descuentos disponibles en el centro de exposición.
     *
     * @param descuentos el conjunto de descuentos a establecer.
     */
    public void setDescuentos(Set<Descuento> descuentos) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir descuentos si no eres el gestor");
            return;
        }

        this.descuentos = descuentos;
    }

    /**
     * Añade un descuento determinado a un centro de exposiciones.
     * 
     * @param descuento el descuento a añadir
     */
    public void addDescuento(Descuento descuento) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes añadir descuentos si no eres el gestor");
            return;
        }

        this.descuentos.add(descuento);
    }

    /**
     * Elimina un descuento determinado de un centro de exposiciones.
     * 
     * @param empleado el empleado a eliminar
     */
    public void removeDescuento(Descuento descuento) {
        if (gestor.isLoged() == false) {
            System.out.println("No puedes eliminar descuentos si no eres el gestor");
            return;
        }

        this.descuentos.remove(descuento);
    }


    /**
     * Permite el login de un empleado al sistema, verificando su NIF, número de
     * seguridad social y contraseña.
     *
     * @param NIF         El NIF del empleado.
     * @param numSS       El número de seguridad social del empleado.
     * @param contrasenia La contraseña de acceso.
     * @return true si el login es exitoso, false en caso contrario.
     */
    public Boolean loginEmpleado(String NIF, String numSS, String contrasenia) {
        for (Empleado e : empleados) {
            if (e.getNIF().equals(NIF) && e.getNumSS().equals(numSS) && contrasenia.equals(contraseniaEmpleado)) {
                e.logIn();
                return true;
            }
        }
        return false;
    }

    public boolean venderEntrada(Exposicion exposicion, Hora hora, Integer nEntradas) {
        LocalDate fecha = LocalDate.now();
        Boolean horaDisponible = false;

        // Verifica si la fecha de la visita está dentro del rango de fechas de la
        // exposición.
        if (fecha.isBefore(exposicion.getFechaInicio()) || fecha.isAfter(exposicion.getFechaFin())) {
            System.out.println("La fecha no está dentro del rango de la exposición");
            return false;
        }

        // Verifica el estado de la exposición, solo procede si está Prorrogada o
        // Publicada.
        if (!exposicion.getEstado().equals(EstadoExposicion.PRORROGADA)
                || !exposicion.getEstado().equals(EstadoExposicion.PUBLICADA)) {
            System.out.println("La exposición no está disponible");
            return false;
        }

        // Asegura que la fecha y la hora de la visita coincidan.
        if (!fecha.equals(hora.getFecha())) {
            System.out.println("La fecha no coincide con la fecha de la hora");
            return false;
        }

        // Verifica la disponibilidad de la hora elegida dentro del horario de la
        // exposición.
        for (Hora h : exposicion.getHorario()) {
            if (hora.equals(h)) {
                horaDisponible = true;
                break;
            }
        }

        if (horaDisponible == false) {
            System.out.println("La hora no está dentro del rango de la exposición");
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
        TicketSystem.createTicket(new Ticket(exposicion, exposicion.getPrecio(), nEntradas, fecha, hora), "." + File.separator + "tmp");
        return true;
    }
    

    /**
     * Permite el login del gestor al sistema, verificando su NIF y contraseña.
     *
     * @param NIF         El NIF del gestor.
     * @param contrasenia La contraseña del gestor.
     * @return true si el login es exitoso, false en caso contrario.
     */
    public Boolean loginGestor(String NIF, String contrasenia) {
        if (gestor.getNIF().equals(NIF) && contrasenia.equals(contraseniaGestor)) {
            gestor.logIn();
            return true;
        }
        return false;
    }

    /**
     * Crea una cadena que representa un centro de exposición.
     * 
     * @return La cadena de representación del centro de exposición
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("CentroExposicion Details:\n");
        sb.append("ID: ").append(ID).append("\n");
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
        sb.append("Descuentos: ").append(descuentos).append("\n");

        return sb.toString();
    }
}
