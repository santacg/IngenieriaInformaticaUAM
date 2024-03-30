package TarjetaDeCredito;

import java.time.LocalDate;

public class TarjetaDeCredito {
    private String numero;
    private LocalDate fechaCaducidad;
    private int CVV;

    public TarjetaDeCredito(String numero, LocalDate fechaCaducidad, int CVV) {
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

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCVV() {
        return CVV;
    }

    public void setCVV(int cVV) {
        CVV = cVV;
    }

}