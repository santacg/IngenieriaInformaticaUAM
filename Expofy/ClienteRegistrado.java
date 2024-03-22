package Expofy;

import java.util.Date;
import Usuario.Usuario;

public class ClienteRegistrado extends Usuario {
    private Boolean publicidad;
    private String contrasenia;
    private Boolean sancionado;
    private Date ultimaCompra;
    private Date sancionadoHasta;

    public ClienteRegistrado(String NIF, Boolean publicidad, String contrasenia, Boolean sancionado, Date ultimaCompra, Date sancionadoHasta) {
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

    public Date getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(Date ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    public Date getSancionadoHasta() {
        return sancionadoHasta;
    }

    public void setSancionadoHasta(Date sancionadoHasta) {
        this.sancionadoHasta = sancionadoHasta;
    }

}
