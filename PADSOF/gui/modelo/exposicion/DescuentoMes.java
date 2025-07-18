package gui.modelo.exposicion;

import java.time.LocalDate;

/**
 * Clase DescuentoMes.
 * Esta clase hereda de {@link Descuento} y aplica funcionalidades para manejar
 * los descuentos para un determinado mes dentro de la aplicación
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

    /**
     * Determina si un descuento es aplicable respecto a una fecha
     * 
     * @param fecha La fecha que se evalua.
     * @return boolean true si es aplicable, false si no
     */
    public boolean validezDescuento(LocalDate fecha) {
        if (fecha.plusMonths(this.getCantidad()).isAfter(LocalDate.now())) {
            return true;
        }
        return false;
    }
}
