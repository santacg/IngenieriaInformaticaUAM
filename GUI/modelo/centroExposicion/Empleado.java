package GUI.modelo.centroExposicion;

import GUI.modelo.usuario.Usuario;

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
    public Empleado(String NIF, String nombre, String numSS, String numCuenta, String direccion, Boolean permisoVenta,
            Boolean permisoControl, Boolean permisoMensajes) {
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
     * Cambia los permisos de un empleado.
     * 
     * Este método actualiza los permisos de venta, control y envío de mensajes del
     * empleado.
     *
     * @param pVenta    indica si el empleado tiene permiso de venta.
     * @param pControl  indica si el empleado tiene permiso de control.
     * @param pMensajes indica si el empleado tiene permiso de enviar mensajes.
     */
    public void cambiarPermisos(Boolean pVenta, Boolean pControl, Boolean pMensajes) {
        this.permisoVenta = pVenta;
        this.permisoControl = pControl;
        this.permisoMensajes = pMensajes;
    }

    /**
     * Actualiza los datos personales del empleado.
     * 
     * Este método permite al empleado modificar su número de seguridad social, el
     * número de cuenta bancaria y la dirección del empleado.
     *
     * @param numSS     El nuevo número de seguridad social del empleado.
     * @param numCuenta El nuevo número de cuenta bancaria del empleado.
     * @param direccion La nueva dirección del empleado.
     */
    public void modificarDatos(String numSS, String numCuenta, String direccion) {
        this.numSS = numSS;
        this.numCuenta = numCuenta;
        this.direccion = direccion;
    }

    /**
     * Retorna el nombre actual.
     * 
     * @return nombre actual.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Actualiza el nombre.
     * 
     * @param nombre nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número de Seguridad Social.
     * 
     * @return número de Seguridad Social.
     */
    public String getNumSS() {
        return numSS;
    }

    /**
     * Establece el número de Seguridad Social.
     * 
     * @param numSS nuevo número de Seguridad Social.
     */
    public void setNumSS(String numSS) {
        this.numSS = numSS;
    }

    /**
     * Retorna el número de cuenta bancaria.
     * 
     * @return número de cuenta bancaria.
     */
    public String getNumCuenta() {
        return numCuenta;
    }

    /**
     * Asigna un nuevo número de cuenta bancaria.
     * 
     * @param numCuenta nuevo número de cuenta.
     */
    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    /**
     * Verifica si tiene permiso de venta.
     * 
     * @return true si tiene permiso, false en caso contrario.
     */
    public Boolean getPermisoVenta() {
        return permisoVenta;
    }

    /**
     * Otorga o revoca el permiso de venta.
     * 
     * @param permisoVenta true para otorgar, false para revocar.
     */
    public void setPermisoVenta(Boolean permisoVenta) {
        this.permisoVenta = permisoVenta;
    }

    /**
     * Verifica si tiene permiso de control de inventario.
     * 
     * @return true si tiene permiso, false en caso contrario.
     */
    public Boolean getPermisoControl() {
        return permisoControl;
    }

    /**
     * Otorga o revoca el permiso de control de inventario.
     * 
     * @param permisoControl true para otorgar, false para revocar.
     */
    public void setPermisoControl(Boolean permisoControl) {
        this.permisoControl = permisoControl;
    }

    /**
     * Verifica si tiene permiso para enviar mensajes.
     * 
     * @return true si tiene permiso, false en caso contrario.
     */
    public Boolean getPermisoMensajes() {
        return permisoMensajes;
    }

    /**
     * Otorga o revoca el permiso para enviar mensajes.
     * 
     * @param permisoMensajes true para otorgar, false para revocar.
     */
    public void setPermisoMensajes(Boolean permisoMensajes) {
        this.permisoMensajes = permisoMensajes;
    }

    /**
     * Obtiene la dirección actual.
     * 
     * @return dirección actual.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Actualiza la dirección.
     * 
     * @param direccion nueva dirección.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

 
    /**
     * Método que devuelve el hash code del empleado.
     * 
     * @return hash code del empleado.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((super.getNIF() == null) ? 0 : super.getNIF().hashCode());
        return result;
    }

    /**
     * Método que compara dos empleados por el NIF.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }

    /**
     * Devuelve una representación en cadena de la información del empleado.
     *
     * @return Cadena de texto que representa al empleado con todos sus detalles.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Detalles empleado:\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Número de Seguridad Social: ").append(numSS).append("\n");
        sb.append("Número de Cuenta: ").append(numCuenta).append("\n");
        sb.append("Permiso de Venta: ").append(permisoVenta).append("\n");
        sb.append("Permiso de Control: ").append(permisoControl).append("\n");
        sb.append("Permiso de Mensajes: ").append(permisoMensajes).append("\n");
        sb.append("Dirección: ").append(direccion).append("\n");

        return sb.toString();
    }

}
