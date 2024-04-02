package CentroExposicion;

import Usuario.Usuario;

/**
 * Clase Empleado.
 * Esta clase ofrece funcionalidades para manejar las salas, exposiciones,
 * obras, empleados, y descuentos dentro del centro de exposición.
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumSS() {
        return numSS;
    }

    public void setNumSS(String numSS) {
        this.numSS = numSS;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public Boolean getPermisoVenta() {
        return permisoVenta;
    }

    public void setPermisoVenta(Boolean permisoVenta) {
        this.permisoVenta = permisoVenta;
    }

    public Boolean getPermisoControl() {
        return permisoControl;
    }

    public void setPermisoControl(Boolean permisoControl) {
        this.permisoControl = permisoControl;
    }

    public Boolean getPermisoMensajes() {
        return permisoMensajes;
    }

    public void setPermisoMensajes(Boolean permisoMensajes) {
        this.permisoMensajes = permisoMensajes;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Empleado [nombre=" + nombre + ", numSS=" + numSS + ", numCuenta=" + numCuenta + ", permisoVenta="
                + permisoVenta + ", permisoControl=" + permisoControl + ", permisoMensajes=" + permisoMensajes
                + ", direccion=" + direccion + "]";
    }

}
