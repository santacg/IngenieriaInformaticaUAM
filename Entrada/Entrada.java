package Entrada;

import Expofy.ClienteRegistrado;
import TarjetaDeCredito.TarjetaDeCredito;

/**
 * Clase Entrada.
 * Esta clase representa una entrada adquirida por un cliente en el sistema
 * Expofy. Cada entrada está vinculada a un cliente registrado y puede estar
 * asociada a una tarjeta de crédito utilizada para la compra.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 *
 */
public class Entrada {
    private Integer IDEntrada;
    private Integer nEntradas;
    private ClienteRegistrado clienteRegistrado;
    private TarjetaDeCredito tarjetaDeCredito;

    /**
     * Constructor para crear una entrada con un identificador específico.
     * 
     * @param IDEntrada Identificador único de la entrada.
     */
    public Entrada(Integer IDEntrada) {
        this.IDEntrada = IDEntrada;
    }

    /**
     * Getters y setters que facilitan la gestión de una entrada.
     */
    public Integer getIDEntrada() {
        return IDEntrada;
    }

    public TarjetaDeCredito getTarjetaDeCredito() {
        return tarjetaDeCredito;
    }

    public void setTarjetaDeCredito(TarjetaDeCredito tarjetaDeCredito) {
        this.tarjetaDeCredito = tarjetaDeCredito;
    }

    public Integer getnEntradas() {
        return nEntradas;
    }

    public void setnEntradas(Integer nEntradas) {
        if (nEntradas <= 0) {
            throw new IllegalArgumentException("El número de entradas no puede ser menor o igual a 0");
        }
        this.nEntradas = nEntradas;
    }

    public ClienteRegistrado getClienteRegistrado() {
        return clienteRegistrado;
    }

    public void setClienteRegistrado(ClienteRegistrado clienteRegistrado) {
        this.clienteRegistrado = clienteRegistrado;
    }

}
