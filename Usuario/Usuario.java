package Usuario;

import java.util.Set;

import Expofy.Notificacion;

public abstract class Usuario {    
    private String NIF;
    private Set<Notificacion> notificaciones;

    public Usuario(String NIF) {
        this.NIF = NIF;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public Set<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Set<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public void addNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }
}
