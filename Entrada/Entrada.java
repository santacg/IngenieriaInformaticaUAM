package Entradas;

import TarjetaDeCredito.TarjetaDeCredito;

public class Entradas {
    private Integer nEntrada;
    private TarjetaDeCredito tarjetaDeCredito;
   
    public Entradas(Integer nEntrada, TarjetaDeCredito tarjetaDeCredito) {
        this.nEntrada = nEntrada;
        this.tarjetaDeCredito = tarjetaDeCredito;
    }

    public Integer getNEntrada() {
        return nEntrada;
    }

    public void setNEntrada(Integer nEntrada) {
        this.nEntrada = nEntrada;
    }

    public TarjetaDeCredito getTarjetaDeCredito() {
        return tarjetaDeCredito;
    }

    public void setTarjetaDeCredito(TarjetaDeCredito tarjetaDeCredito) {
        this.tarjetaDeCredito = tarjetaDeCredito;
    }
}
