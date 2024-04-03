package CentroExposicion;

/**
 * Clase DescuentoDia.
 * Esta clase hereda de {@link Descuento} y aplica funcionalidades para manejar
 * los descuentos para un determinado día dentro de la aplicación
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
import java.io.Serializable;

public class DescuentoDia extends Descuento implements Serializable{
    /**
     * Referencia al constructor de la superclase descuento.
     *
     * @param descuento el porcentaje de descuento
     * @param cantidad  la cantidad a descontar
     */
    public DescuentoDia(Double descuento, Integer cantidad) {
        super(descuento, cantidad);
    }
}
