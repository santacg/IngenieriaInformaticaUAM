package TarjetaDeCredito;

import java.util.Date;

public class TarjetaDeCredito {
    private String numero;
    private Date fechaCaducidad;
    private int CVV;

    public TarjetaDeCredito(String numero, Date fechaCaducidad, int CVV) {
        this.numero = numero;
        this.fechaCaducidad = fechaCaducidad;
        this.CVV = CVV;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCVV() {
        return CVV;
    }

    public void setCVV(int cVV) {
        CVV = cVV;
    }

}