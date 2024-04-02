package CentroExposicion;

/**
 * Clase Descuento.
 * Esta clase abstracta ofrece funcionalidades para manejar los descuentos
 * dentro de la aplicación.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */

public abstract class Descuento {
    private Double descuento;
    private Integer cantidad;

    /**
     * Constructor de un descuento con los parámetros proporcionados.
     *
     * @param descuento el porcentaje de descuento
     * @param cantidad  la cantidad a descontar
     */
    public Descuento(Double descuento, Integer cantidad) {
        this.descuento = descuento;
        this.cantidad = cantidad;
    }

    /**
     * Getters y setters que facilitan la gestión de descuentos.
     */
    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Integer getcantidad() {
        return cantidad;
    }

    public void setcantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
