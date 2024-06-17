package gui.modelo.exposicion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.centroExposicion.Sorteo;
import gui.modelo.expofy.*;
import gui.modelo.inscripcion.Inscripcion;
import gui.modelo.obra.Obra;
import gui.modelo.usuario.Usuario;
import gui.modelo.utils.ExcepcionMensaje;

import java.io.Serializable;

/**
 * Clase Exposición.
 * Esta clase representa una exposición en un centro de exposiciones,
 * incluyendo información sobre su duración, salas, tipo...
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Exposicion implements Serializable {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;
    private Double benificios;
    private Double precio;
    private EstadoExposicion estado;
    private Set<SalaExposicion> salas = new HashSet<>();
    private Set<Hora> horario = new HashSet<>();
    private Set<Entrada> entradas = new HashSet<>();
    private Descuento descuento = null;
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
     * @param tipo        El tipo de exposición, puede ser temporal o permanente.
     * @param precio      Precio de la exposición.
     */
    public Exposicion(String nombre, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, TipoExpo tipo,
            Double precio) {
        LocalDate fechaAux;

        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        if (tipo.equals(TipoExpo.PERMANENTE)) {
            this.fechaFin = LocalDate.MAX;
        } else {
            this.fechaFin = fechaFin;
        }

        for (fechaAux = fechaInicio; fechaAux.isBefore(fechaAux); fechaAux.plusDays(1)) {
            for (int i = 0; i < 11; i++) {
                horario.add(new Hora(fechaAux, LocalTime.of(9, 0, 0).plusHours(i),
                        LocalTime.of(9, 0, 0).plusHours(i).plusMinutes(50), 40));
            }
        }
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.estado = EstadoExposicion.EN_CREACION;
        Estadisticas estadisticas = new Estadisticas();
        this.estadisticas = estadisticas;
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
     * @return boolean true si todo bien false si no.
     */
    public boolean setFechaInicio(LocalDate fechaInicio) {
        if (this.fechaFin.isBefore(fechaInicio)) {
            System.out.println("La fecha de fin no puede ser anterior a la fecha de inicio");
            return false;
        }

        this.fechaInicio = fechaInicio;
        return true;
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
     * @return boolean true si todo bien false si no.
     */
    public boolean setFechaFin(LocalDate fechaFin) {
        if (this.fechaInicio.isAfter(fechaFin)) {
            return false;
        }

        this.fechaFin = fechaFin;
        return true;
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
     * Devuelve el precio de la exposición.
     * 
     * @return El precio de la exposición.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Obtiene las entradas
     * 
     * @return entradas
     */
    public Set<Entrada> getEntradas() {
        return entradas;
    }

    /**
     * Actualiza el precio de la exposición.
     * 
     * @param precio El nuevo precio de la exposición.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Cambia el estado de la exposición a EN_CREACION.
     */
    public void expoCrear() {
        this.estado = EstadoExposicion.EN_CREACION;
    }

    /**
     * Cambia el estado de la exposición a PUBLICADA.
     * 
     * @throws ExcepcionMensaje si la exposición no está en estado de creación, no
     *                          tiene salas o las salas no tienen obras.
     */
    public void expoPublicar() throws ExcepcionMensaje {

        if (this.estado != EstadoExposicion.EN_CREACION) {
            throw new ExcepcionMensaje("No se puede publicar una exposición que no esta en creacion");
        }

        if (this.salas.size() == 0) {
            throw new ExcepcionMensaje("No se puede publicar una exposición sin salas");
        }

        for (SalaExposicion sala : this.salas) {
            if (sala.getObras().size() == 0) {
                throw new ExcepcionMensaje("No se puede publicar una exposición con salas sin obras");
            }
        }

        Expofy expofy = Expofy.getInstance();
        expofy.enviarNotificacionesClientesPublicidad(
                "Se ha publicado una nueva exposicion " + getNombre() + ": " + getDescripcion());

        this.estado = EstadoExposicion.PUBLICADA;
    }

    /**
     * Cambia el estado de la exposición a CANCELADA.
     * 
     * @param fechaCancelacion La fecha de cancelación de la exposición.
     * 
     * @throws ExcepcionMensaje si la exposición no está en estado de creación, la
     *                          fecha de cancelación es anterior a la fecha actual,
     *                          la
     *                          fecha de cancelación es posterior a la fecha de fin
     *                          de
     *                          la exposición o hay entradas compradas con menos de
     *                          7 días
     *                          de antelación a la fecha de cancelación.
     */
    public void expoCancelar(LocalDate fechaCancelacion) throws ExcepcionMensaje {
        if (this.estado == EstadoExposicion.EN_CREACION || this.estado == EstadoExposicion.CANCELADA) {
            throw new ExcepcionMensaje(
                    "No se puede cancelar una exposición que no ha sido publicada o ya está cancelada");
        }

        if (fechaCancelacion.isBefore(LocalDate.now().plusDays(7))) {
            throw new ExcepcionMensaje("No se puede cancelar la exposición con menos de 7 días de antelación");
        }

        if (fechaCancelacion.isAfter(this.fechaFin)) {
            throw new ExcepcionMensaje("No se puede cancelar la exposición con una fecha de fin posterior a la actual");
        }

        Expofy expofy = Expofy.getInstance();
        Set<Usuario> clientes = new HashSet<>();
        for (Entrada entrada : this.entradas) {
            if (entrada.getFechaDeCompra().isAfter(fechaCancelacion)) {
                clientes.add(entrada.getClienteRegistrado());
            }
        }

        for (CentroExposicion centro : expofy.getCentrosExposicion()) {
            for (Sorteo sorteo : centro.getSorteos()) {
                if (sorteo.getExposicion().equals(this)) {
                    if (sorteo.getFechaSorteo().isAfter(fechaCancelacion)) {
                        for (Inscripcion inscripcion : sorteo.getInscripciones()) {
                            expofy.enviarNotificacionUsuario("Se ha cancelado la exposición " + this.nombre
                                    + "el sorteo al que te has inscrito es después de la fecha de cancelación por ello se ha eliminado tu inscripción",
                                    inscripcion.getCliente());
                            sorteo.removeInscripcion(inscripcion);
                        }
                        centro.removeSorteo(sorteo);
                        break;
                    }
                }
            }
        }

        expofy.enviarNotificacionUsuarios("Se ha cancelado la exposicion: " + this.nombre
                + ".Se ha reintegrado el importe de la entrada en tu cuenta bancaria", clientes);

        this.fechaFin = fechaCancelacion;
        this.estado = EstadoExposicion.CANCELADA;

        if (this.tipo == TipoExpo.PERMANENTE) {
            this.tipo = TipoExpo.TEMPORAL;
        }
    }

    /**
     * Prorroga la fecha de fin de la exposición.
     * 
     * @param fechaFin La nueva fecha de fin para la exposición.
     * 
     * @throws ExcepcionMensaje si la exposición es permanente, no ha sido publicada
     *                          o prorrogada, o la fecha de fin es anterior o igual
     *                          a
     *                          la actual.
     * 
     */
    public void expoProrrogar(LocalDate fechaFin) throws ExcepcionMensaje {

        if (this.tipo == TipoExpo.PERMANENTE) {
            throw new ExcepcionMensaje("No se puede prorrogar una exposición permanente");
        }

        if (this.estado != EstadoExposicion.PUBLICADA && this.estado != EstadoExposicion.PRORROGADA) {
            throw new ExcepcionMensaje("No se puede prorrogar una exposición que no ha sido publicada o prorrogada");
        }

        if (fechaFin.isBefore(this.fechaFin) || fechaFin.isEqual(this.fechaFin)) {
            throw new ExcepcionMensaje(
                    "No se puede prorrogar la exposición con una fecha de fin anterior o igual a la actual");
        }

        this.fechaFin = fechaFin;
        this.estado = EstadoExposicion.PRORROGADA;
    }

    /**
     * Cierra temporalmente la exposición.
     * 
     * @param fechaInicioCierre La fecha de inicio del cierre temporal.
     * @param fechaFinCierre    La fecha de fin del cierre temporal.
     */
    public void expoCerrarTemporalmente(LocalDate fechaInicioCierre, LocalDate fechaFinCierre) throws ExcepcionMensaje {
        if (this.estado != EstadoExposicion.PUBLICADA && this.estado != EstadoExposicion.PRORROGADA) {
            throw new ExcepcionMensaje(
                    "No se puede cerrar temporalmente una exposición que no ha sido publicada o prorrogada");
        }

        if (fechaInicioCierre.isBefore(LocalDate.now()) || fechaFinCierre.isBefore(LocalDate.now())
                || fechaInicioCierre.isAfter(fechaFinCierre)) {
            throw new ExcepcionMensaje(
                    "La fecha de inicio de cierre temporal no puede ser anterior a la fecha actual o la fecha de fin de cierre");
        }

        if (this.fechaFin.isBefore(fechaFinCierre)) {
            throw new ExcepcionMensaje(
                    "La fecha de fin de cierre temporal no puede ser mayor que la fecha de fin de la exposición");
        }

        if (this.fechaInicio.isAfter(fechaInicioCierre)) {
            throw new ExcepcionMensaje(
                    "La fecha de inicio de cierre temporal no puede ser anterior a la fecha de inicio de la exposición");
        }

        this.estado = EstadoExposicion.CERRADATEMPORALMENTE;
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
     * @return boolean true si todo bien false si no.
     */
    public boolean addSala(SalaExposicion sala) {
        if (this.salas.add(sala) == false) {
            System.out.println("La sala ya pertenece a la exposición");
            return false;
        }
        return true;
    }

    /**
     * Elimina una sala del conjunto de salas de la exposición.
     * 
     * @param sala La sala a eliminar.
     * @return boolean true si todo bien false si no.
     */
    public boolean removeSala(SalaExposicion sala) {
        if (this.salas.contains(sala) == false) {
            System.out.println("La sala no pertenece a la exposición");
            return false;
        }

        for (Obra obra : sala.getObras()) {
            try {
                obra.almacenarObra();
            } catch (ExcepcionMensaje e) {
                e.printStackTrace();
                return false;
            }
        }

        this.salas.remove(sala);
        return true;
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
     * Cambia el tipo de la exposición a temporal.
     * 
     * @param fechaFin La fecha de fin de la exposición.
     */
    public void expoTemporal(LocalDate fechaFin) throws ExcepcionMensaje {

        if (this.estado == EstadoExposicion.CANCELADA) {
            throw new ExcepcionMensaje("No se puede cambiar el tipo de la exposición a temporal si ha sido cancelada");
        }

        if (this.tipo == TipoExpo.TEMPORAL) {
            throw new ExcepcionMensaje("La exposición ya es temporal");
        }

        if (setFechaFin(fechaFin) == false) {
            throw new ExcepcionMensaje("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        this.tipo = TipoExpo.TEMPORAL;
    }

    /**
     * Cambia el tipo de la exposición a permanente.
     */
    public void expoPermanente() throws ExcepcionMensaje {

        if (this.tipo == TipoExpo.PERMANENTE) {
            throw new ExcepcionMensaje("La exposición ya es permanente");
        }

        if (this.estado == EstadoExposicion.CANCELADA) {
            throw new ExcepcionMensaje(
                    "No se puede cambiar el tipo de la exposición a permanente si ha sido cancelada");
        }

        this.tipo = TipoExpo.PERMANENTE;
        this.fechaFin = LocalDate.MAX;
    }

    /**
     * Devuelve el conjunto de descuentos aplicables a la exposición.
     * 
     * @return El conjunto de descuentos de la exposición.
     */
    public Descuento getDescuento() {
        return descuento;
    }

    /**
     * Establece un descuento para la exposición y envia un mensaje a los clientes
     * que reciban publicidad.
     * 
     * @param descuento El descuento a aplicar.
     */
    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;

        Expofy expofy = Expofy.getInstance();

        if (descuento.getClass() == DescuentoDia.class) {
            expofy.enviarNotificacionesClientesPublicidad("Se ha añadido un nuevo descuento del "
                    + descuento.getDescuento() + "% "
                    + " a la exposición " + getNombre()
                    + " que se aplicará automaticamente en tu siguiente compra si has comprado una entrada en los últimos "
                    + descuento.getCantidad() + " días");
        } else {
            expofy.enviarNotificacionesClientesPublicidad("Se ha añadido un nuevo descuento del "
                    + descuento.getDescuento() + "% "
                    + " a la exposición " + getNombre()
                    + " que se aplicará automaticamente en tu siguiente compra si has comprado una entrada en los últimos "
                    + descuento.getCantidad() + " meses");
        }
    }

    /**
     * Configura un descuento para la exposición basado en la cantidad de días
     * 
     * @param cantidadDescuento la cantidad que se descuenta del precio.
     * @param dias              los dias con los que se aplica el descuento.
     */
    public void configurarDescuentoDia(double cantidadDescuento, int dias) {
        if (cantidadDescuento <= 0) {
            System.out.println("El descuento no puede ser negativo o nulo");
            return;
        }

        if (dias <= 0) {
            System.out.println("El número de días no puede ser negativo o nulo");
            return;
        }

        DescuentoDia descuento = new DescuentoDia(cantidadDescuento, dias);
        setDescuento(descuento);
    }

    /**
     * Configura un descuento para la exposición basado en la cantidad de meses
     * 
     * @param cantidadDescuento La cantidad de descuento.
     * @param meses             El número de meses.
     */
    public void configurarDescuentoMes(double cantidadDescuento, int meses) {
        if (cantidadDescuento <= 0) {
            System.out.println("El descuento no puede ser negativo o nulo");
            return;
        }

        if (meses <= 0) {
            System.out.println("El número de meses no puede ser negativo o nulo");
            return;
        }

        DescuentoMes descuento = new DescuentoMes(cantidadDescuento, meses);
        setDescuento(descuento);
    }

    /**
     * Configura un descuento para la exposición basado en la cantidad de meses
     * 
     * @param entrada Entrada a añadir.
     */
    public boolean addEntrada(Entrada entrada) {
        if (this.entradas.add(entrada) == false) {
            System.out.println("La entrada ya existe");
            return false;
        }

        return true;
    }

    /**
     * Devuelve una hora.
     * 
     * @param fecha    La fecha de la hora.
     * @param hora_num El número de la hora.
     * @return La hora.
     */
    public Hora getHora(LocalDate fecha, int hora_num) {
        for (Hora hora : horario) {
            if (hora.getFecha().isEqual(fecha) && hora_num == hora.getHoraInicio().getHour()) {
                return hora;
            }
        }
        return new Hora(fecha, LocalTime.of(hora_num, 0, 0), LocalTime.of(hora_num, 59, 0), 40);
    }

    /**
     * Devuelve un conjunto de obras.
     * 
     * @return El conjunto de obras.
     */
    public Set<Obra> getObras() {
        Set<Obra> obras = new HashSet<>();
        for (SalaExposicion sala : this.salas) {
            obras.addAll(sala.getObras());
        }
        return obras;
    }

    /**
     * Genera una representación en cadena de la exposición, incluyendo detalles
     * como el ID, nombre, fechas, y más.
     * 
     * @return Una cadena que representa la exposición.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Detalles exposción:\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Fecha de Inicio: ").append(fechaInicio).append("\n");
        sb.append("Fecha de Fin: ").append(fechaFin).append("\n");
        sb.append("Descripción: ").append(descripcion).append("\n");
        sb.append("Beneficios: ").append(benificios).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
        sb.append("Salas: ").append(salas).append("\n");
        sb.append("Horario: ").append(horario).append("\n");
        sb.append("Descuentos: ").append(descuento).append("\n");
        sb.append("Estadísticas: ").append(estadisticas).append("\n");
        sb.append("Tipo: ").append(tipo).append("\n");

        return sb.toString();
    }

    /**
     * Genera un código hash para la exposición.
     * 
     * @return Un código hash para la exposición.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
        result = prime * result + ((fechaFin == null) ? 0 : fechaFin.hashCode());
        return result;
    }

    /**
     * Compara esta exposición con otro objeto dado. Dos exposiciones se consideran
     * iguales si tienen el mismo nombre, fecha de inicio y fecha de fin.
     * 
     * @param obj El objeto con el que comparar esta exposición.
     * 
     * @return {@code true} si los objetos son iguales, {@code false} en caso
     *         contrario.
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
        return true;
    }

    /**
     * Unicamente util para los test no se debe usar en otro caso
     * 
     * @param estado estado al que va a pasar la exposicion
     */
    public void setEstado(EstadoExposicion estado) {
        this.estado = estado;
    }

}