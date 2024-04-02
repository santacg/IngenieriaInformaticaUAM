package CentroExposicion;

/**
 * Clase DescuentoMes.
 * Esta clase hereda de Descuento y aplica funcionalidades para manejar los
 * descuentos para un determinado mes dentro de la aplicación
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class DescuentoMes extends Descuento {
    /**
     * Referencia al constructor de la superclase descuento.
     *
     * @param descuento el porcentaje de descuento
     * @param cantidad  la cantidad a descontar
     */
    public DescuentoMes(Double descuento, Integer cantidad) {
        super(descuento, cantidad);
    }
}
