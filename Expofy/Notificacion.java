package Expofy;

import java.time.LocalDate;

public class Notificacion {
    private String mensaje;
    private LocalDate fecha;
    private boolean leida;

    public Notificacion(String mensaje, LocalDate fecha) {
        this.mensaje = mensaje;
        this.fecha = fecha;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String toString() {
        return "Notificacion [mensaje=" + mensaje + ", fecha=" + fecha + ", leida=" + leida + "]";
    }

}
