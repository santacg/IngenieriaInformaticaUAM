package Expofy;

import java.time.LocalDate;

import CentroExposicion.Sorteo;
import Inscripcion.Inscripcion;
import Usuario.Usuario;

/**
 * Clase ClienteRegistrado
 * Extiende la clase {@code Usuario} para representar un cliente registrado en
 * el sistema. Incluye información sobre la preferencia de recibir publicidad,
 * la contraseña del cliente, si está sancionado, la fecha de la última compra y
 * la fecha hasta la cual está sancionado. Además, permite la inscripción a
 * sorteos.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ClienteRegistrado extends Usuario {
    private Boolean publicidad;
    private String contrasenia;
    private Boolean sancionado;
    private LocalDate ultimaCompra;
    private LocalDate sancionadoHasta;

    /**
     * Constructor para crear un nuevo cliente registrado.
     * 
     * @param NIF             Identificador único del usuario.
     * @param publicidad      Preferencia del cliente sobre recibir publicidad.
     * @param contrasenia     Contraseña del cliente.
     * @param sancionado      Indica si el cliente está sancionado.
     * @param ultimaCompra    Fecha de la última compra realizada.
     * @param sancionadoHasta Fecha hasta la cual el cliente está sancionado.
     */
    public ClienteRegistrado(String NIF, Boolean publicidad, String contrasenia, Boolean sancionado,
            LocalDate ultimaCompra, LocalDate sancionadoHasta) {
        super(NIF);
        this.publicidad = publicidad;
        this.contrasenia = contrasenia;
        this.sancionado = sancionado;
        this.ultimaCompra = ultimaCompra;
        if (sancionadoHasta == null) {
            this.sancionadoHasta = LocalDate.of(1970, 1, 1);
        } else {
            this.sancionadoHasta = sancionadoHasta;
        }
    }

    /**
     * Devuelve la preferencia del cliente sobre recibir publicidad.
     *
     * @return {@code true} si el cliente desea recibir publicidad, {@code false} en
     *         caso contrario.
     */
    public Boolean getPublicidad() {
        return publicidad;
    }

    /**
     * Establece la preferencia del cliente sobre recibir publicidad.
     *
     * @param publicidad {@code true} para aceptar recibir publicidad, {@code false}
     *                   para rechazarla.
     */
    public void setPublicidad(Boolean publicidad) {
        this.publicidad = publicidad;
    }

    /**
     * Obtiene la contraseña del cliente.
     *
     * @return La contraseña del cliente.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Establece la contraseña del cliente.
     *
     * @param contrasenia La nueva contraseña.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Verifica si el cliente está sancionado.
     *
     * @return {@code true} si el cliente está sancionado, {@code false} en caso
     *         contrario.
     */
    public Boolean getSancionado() {
        return sancionado;
    }

    /**
     * Establece el estado de sanción del cliente.
     *
     * @param sancionado {@code true} para sancionar al cliente, {@code false} para
     *                   quitar la sanción.
     */
    public void setSancionado(Boolean sancionado) {
        this.sancionado = sancionado;
    }

    /**
     * Obtiene la fecha de la última compra realizada por el cliente.
     *
     * @return La fecha de la última compra.
     */
    public LocalDate getUltimaCompra() {
        return ultimaCompra;
    }

    /**
     * Establece la fecha de la última compra realizada por el cliente.
     *
     * @param ultimaCompra La fecha de la última compra.
     */
    public void setUltimaCompra(LocalDate ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    /**
     * Obtiene la fecha hasta la cual el cliente está sancionado.
     *
     * @return La fecha hasta la cual el cliente está sancionado.
     */
    public LocalDate getSancionadoHasta() {
        return sancionadoHasta;
    }

    /**
     * Establece la fecha hasta la cual el cliente está sancionado.
     *
     * @param sancionadoHasta La nueva fecha hasta la cual el cliente está
     *                        sancionado.
     */
    public void setSancionadoHasta(LocalDate sancionadoHasta) {
        this.sancionadoHasta = sancionadoHasta;
    }

    /**
     * Intenta inscribir al cliente en un sorteo y le confirma el resultado con
     * una notificación. La inscripción solo es posible si
     * el cliente no está sancionado y si la fecha del sorteo no ha expirado.
     * 
     * @param sorteo     El sorteo al cual el cliente desea inscribirse.
     * @param n_entradas Número de entradas que el cliente desea inscribir.
     */

    public void inscribirse(Sorteo sorteo, int n_entradas) {
        String mensaje = "Tu participación por " + n_entradas + " entradas para la exposición " +
                sorteo.getExposicion().getNombre();
        if (sancionadoHasta.isAfter(LocalDate.now()) || sorteo.getFechaSorteo().isBefore(LocalDate.now())) {
            mensaje = mensaje + " no ha podido realizarse ya que estás sancionado o el sorteo ha expirado";
        } else {
            sorteo.addInscripcion(new Inscripcion(n_entradas, this));
            mensaje = mensaje + " se ha registrado correctamente";
        }
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        this.addNotificacion(notificacion);
    }

    /**
     * Proporciona una representación en cadena de la información del cliente
     * registrado.
     * 
     * @return Cadena de texto con los detalles del cliente registrado.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente Registrado:\n");
        sb.append("Publicidad: ").append(publicidad).append("\n");
        sb.append("Contraseña: ").append(contrasenia).append("\n");
        sb.append("Sancionado: ").append(sancionado).append("\n");
        sb.append("Última Compra: ").append(ultimaCompra).append("\n");
        sb.append("Sancionado Hasta: ").append(sancionadoHasta).append("\n");
        return sb.toString();
    }
}
