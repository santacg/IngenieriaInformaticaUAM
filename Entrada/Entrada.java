package Entrada;

import Expofy.ClienteRegistrado;
import TarjetaDeCredito.TarjetaDeCredito;

public class Entrada {
    private Integer IDEntrada;
    private Integer nEntradas;
    private ClienteRegistrado clienteRegistrado;
    private TarjetaDeCredito tarjetaDeCredito;
   
    public Entrada(Integer IDEntrada) {
        this.IDEntrada = IDEntrada;
    }

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
