package Expofy;

import java.util.Date;

public class Notificacion {
    private String mensaje;
    private Date fecha;
    private boolean leida;

    public Notificacion(String mensaje) {
        this.mensaje = mensaje;
        this.leida = false;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
