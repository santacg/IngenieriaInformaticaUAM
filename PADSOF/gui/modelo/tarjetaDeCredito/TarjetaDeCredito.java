package gui.modelo.tarjetaDeCredito;

import java.time.LocalDate;
import java.io.Serializable;
/**
 * Clase TarjetaDeCredito.
 * Es una clase que representa la tarjeta de credito para realizar la compra de entradas.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class TarjetaDeCredito implements Serializable{
    private String numero;
    private LocalDate fechaCaducidad;
    private int CVV;

    /**
     * Constructor para crear una nueva tarjeta de crédito.
     * 
     * @param numero El número de la tarjeta de crédito.
     * @param fechaCaducidad La fecha de caducidad de la tarjeta de crédito.
     * @param CVV El código CVV de la tarjeta de crédito.
     */
    public TarjetaDeCredito(String numero, LocalDate fechaCaducidad, int CVV) {
        this.numero = numero;
        this.fechaCaducidad = fechaCaducidad;
        this.CVV = CVV;
    }

    /**
     * Obtiene el número de la tarjeta de crédito.
     * 
     * @return El número de la tarjeta.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Establece el número de la tarjeta de crédito.
     * 
     * @param numero El nuevo número de la tarjeta.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obtiene la fecha de caducidad de la tarjeta de crédito.
     * 
     * @return La fecha de caducidad.
     */
    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * Establece la fecha de caducidad de la tarjeta de crédito.
     * 
     * @param fechaCaducidad La nueva fecha de caducidad.
     */
    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * Obtiene el código CVV de la tarjeta de crédito.
     * 
     * @return El código CVV.
     */
    public int getCVV() {
        return CVV;
    }

    /**
     * Establece el código CVV de la tarjeta de crédito.
     * 
     * @param CVV El nuevo código CVV.
     */
    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

}