package src.CentroExposicion;

/**
 * Clase Descuento.
 * Esta clase abstracta ofrece funcionalidades para manejar los descuentos
 * dentro de la aplicación.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
import java.io.Serializable;
import java.time.LocalDate;

public abstract class Descuento implements Serializable{
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
     * Obtiene el valor del descuento aplicable.
     * @return El porcentaje de descuento como double.
     */
    public Double getDescuento() {
        return descuento;
    }

    /**
     * Establece un nuevo valor para el descuento.
     * @param descuento El nuevo porcentaje de descuento a aplicar.
     */
    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    /**
     * Retorna la cantidad de items o unidades.
     * @return La cantidad actual como entero.
     */
    public Integer getcantidad() {
        return cantidad;
    }

    /**
     * Asigna un nuevo valor a la cantidad de items o unidades.
     * @param cantidad La nueva cantidad a establecer.
     */
    public void setcantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Determina si un descuento es aplicable respecto a una fecha
     * @param cantidad La nueva cantidad a establecer.
     * @return boolean true si es aplicable, false si no
     */
    public abstract boolean validezDescuento(LocalDate fecha);
}
