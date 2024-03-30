package Expofy;

import java.time.LocalDate;
import Usuario.Usuario;

public class ClienteRegistrado extends Usuario {
    private Boolean publicidad;
    private String contrasenia;
    private Boolean sancionado;
    private LocalDate ultimaCompra;
    private LocalDate sancionadoHasta;

    public ClienteRegistrado(String NIF, Boolean publicidad, String contrasenia, Boolean sancionado, LocalDate ultimaCompra, LocalDate sancionadoHasta) {
        super(NIF);
        this.publicidad = publicidad;
        this.contrasenia = contrasenia;
        this.sancionado = sancionado;
        this.ultimaCompra = ultimaCompra;
        this.sancionadoHasta = sancionadoHasta;
    }

    public Boolean getPublicidad() {
        return publicidad;
    }

    public void setPublicidad(Boolean publicidad) {
        this.publicidad = publicidad;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getSancionado() {
        return sancionado;
    }

    public void setSancionado(Boolean sancionado) {
        this.sancionado = sancionado;
    }

    public LocalDate getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(LocalDate ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    public LocalDate getSancionadoHasta() {
        return sancionadoHasta;
    }

    public void setSancionadoHasta(LocalDate sancionadoHasta) {
        this.sancionadoHasta = sancionadoHasta;
    }

}
