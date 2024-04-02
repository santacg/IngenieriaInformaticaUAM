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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClienteRegistrado other = (ClienteRegistrado) obj;
        if (contrasenia == null) {
            if (other.contrasenia != null)
                return false;
        } else if (!contrasenia.equals(other.contrasenia))
            return false;
        return true;
    }

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
        this.sancionadoHasta = sancionadoHasta;
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
     * Intenta inscribir al cliente en un sorteo. La inscripción solo es posible si
     * el cliente no está sancionado.
     * 
     * @param sorteo     El sorteo al cual el cliente desea inscribirse.
     * @param n_entradas Número de entradas que el cliente desea inscribir.
     * @return {@code true} si la inscripción es exitosa, {@code false} en caso
     *         contrario.
     */

    public boolean inscribirse(Sorteo sorteo, int n_entradas) {
        if (sancionadoHasta.isAfter(LocalDate.now())) {
            return false;
        }
        sorteo.addInscripcion(new Inscripcion(n_entradas, this));
        return true;
    }

    /**
     * Proporciona una representación en cadena de la información del cliente
     * registrado.
     * 
     * @return Cadena de texto con los detalles del cliente registrado.
     */
    public String toString() {
        return "ClienteRegistrado [publicidad=" + publicidad + ", contrasenia=" + contrasenia + ", sancionado="
                + sancionado + ", ultimaCompra=" + ultimaCompra + ", sancionadoHasta=" + sancionadoHasta + "]";
    }
}
