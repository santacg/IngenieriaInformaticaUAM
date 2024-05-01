package GUI.modelo.exposicion;

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
     * @param fecha La fecha que se evalua.
     * @return boolean true si es aplicable, false si no
     */
    public abstract boolean validezDescuento(LocalDate fecha);

    /**
     * Genera el código hash de descuento.
     * 
     * @return El código hash del descuento.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descuento == null) ? 0 : descuento.hashCode());
        result = prime * result + ((cantidad == null) ? 0 : cantidad.hashCode());
        return result;
    }

    /**
     * Comprueba si este descuento es igual al objeto proporcionado.
     * Dos descuentos se consideran iguales si tienen el mismo descuento y cantidad.
     * 
     * @param obj El objeto con el que comparar este {@code Descuento}.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Descuento other = (Descuento) obj;
        if (descuento == null) {
            if (other.descuento != null)
                return false;
        } else if (!descuento.equals(other.descuento))
            return false;
        if (cantidad == null) {
            if (other.cantidad != null)
                return false;
        } else if (!cantidad.equals(other.cantidad))
            return false;
        return true;
    }

    /**
     * Devuelve una representación en cadena del descuento.
     * 
     * @return Una representación en cadena del descuento.
     */
    public String toString() {
        return "Descuento [descuento=" + descuento + ", cantidad=" + cantidad + "]";
    }

}
