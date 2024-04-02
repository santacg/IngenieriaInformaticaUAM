package CentroExposicion;

import Usuario.Usuario;

/**
 * Clase Empleado.
 * Representa un empleado dentro del sistema de gestión de usuarios.
 * Esta clase extiende de {@link Usuario}, heredando sus atributos y métodos.
 * Proporciona características específicas de un empleado, como su nombre,
 * número de Seguridad Social (numSS), número de cuenta bancaria, permisos para
 * realizar ventas, controlar el sistema, y enviar mensajes, además de su
 * dirección.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class Empleado extends Usuario {
    private String nombre;
    private String numSS;
    private String numCuenta;
    private Boolean permisoVenta;
    private Boolean permisoControl;
    private Boolean permisoMensajes;
    private String direccion;

    /**
     * Construye una instancia de Empleado con todos los detalles necesarios.
     *
     * @param NIF             El Número de Identificación Fiscal del empleado
     * @param nombre          El nombre del empleado
     * @param numSS           El número de Seguridad Social del empleado
     * @param numCuenta       El número de cuenta bancaria del empleado
     * @param permisoVenta    Verdadero si el empleado tiene permiso para ventas
     * @param permisoControl  Verdadero si el empleado tiene permiso para controlar
     * @param permisoMensajes Verdadero si el empleado puede enviar mensajes
     * @param direccion       La dirección del empleado
     */
    public Empleado(String NIF, String nombre, String numSS, String numCuenta, Boolean permisoVenta,
            Boolean permisoControl, Boolean permisoMensajes, String direccion) {
        super(NIF);
        this.nombre = nombre;
        this.numSS = numSS;
        this.numCuenta = numCuenta;
        this.permisoVenta = permisoVenta;
        this.permisoControl = permisoControl;
        this.permisoMensajes = permisoMensajes;
        this.direccion = direccion;
    }

    /**
     * Retorna el nombre actual.
     * @return nombre actual.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Actualiza el nombre.
     * @param nombre nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número de Seguridad Social.
     * @return número de Seguridad Social.
     */
    public String getNumSS() {
        return numSS;
    }

    /**
     * Establece el número de Seguridad Social.
     * @param numSS nuevo número de Seguridad Social.
     */
    public void setNumSS(String numSS) {
        this.numSS = numSS;
    }

    /**
     * Retorna el número de cuenta bancaria.
     * @return número de cuenta bancaria.
     */
    public String getNumCuenta() {
        return numCuenta;
    }

    /**
     * Asigna un nuevo número de cuenta bancaria.
     * @param numCuenta nuevo número de cuenta.
     */
    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    /**
     * Verifica si tiene permiso de venta.
     * @return true si tiene permiso, false en caso contrario.
     */
    public Boolean getPermisoVenta() {
        return permisoVenta;
    }

    /**
     * Otorga o revoca el permiso de venta.
     * @param permisoVenta true para otorgar, false para revocar.
     */
    public void setPermisoVenta(Boolean permisoVenta) {
        this.permisoVenta = permisoVenta;
    }

    /**
     * Verifica si tiene permiso de control de inventario.
     * @return true si tiene permiso, false en caso contrario.
     */
    public Boolean getPermisoControl() {
        return permisoControl;
    }

    /**
     * Otorga o revoca el permiso de control de inventario.
     * @param permisoControl true para otorgar, false para revocar.
     */
    public void setPermisoControl(Boolean permisoControl) {
        this.permisoControl = permisoControl;
    }

    /**
     * Verifica si tiene permiso para enviar mensajes.
     * @return true si tiene permiso, false en caso contrario.
     */
    public Boolean getPermisoMensajes() {
        return permisoMensajes;
    }

    /**
     * Otorga o revoca el permiso para enviar mensajes.
     * @param permisoMensajes true para otorgar, false para revocar.
     */
    public void setPermisoMensajes(Boolean permisoMensajes) {
        this.permisoMensajes = permisoMensajes;
    }

    /**
     * Obtiene la dirección actual.
     * @return dirección actual.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Actualiza la dirección.
     * @param direccion nueva dirección.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve una representación en cadena de la información del empleado.
     *
     * @return Cadena de texto que representa al empleado con todos sus detalles.
     */
    @Override
    public String toString() {
        return "Empleado [nombre=" + nombre + ", numSS=" + numSS + ", numCuenta=" + numCuenta + ", permisoVenta="
                + permisoVenta + ", permisoControl=" + permisoControl + ", permisoMensajes=" + permisoMensajes
                + ", direccion=" + direccion + "]";
    }

}
