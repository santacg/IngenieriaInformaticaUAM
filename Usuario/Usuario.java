package Usuario;

import java.util.Set;

import Expofy.Notificacion;

public abstract class Usuario {    
    private String NIF;
    private Set<Notificacion> notificaciones;
    private Boolean loged = false;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (NIF == null) {
            if (other.NIF != null)
                return false;
        } else if (!NIF.equals(other.NIF))
            return false;
        return true;
    }

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

    public void removeNotificacion(Notificacion notificacion) {
        notificaciones.remove(notificacion);
    }

    public void removeAllNotificaciones() {
        notificaciones.clear();
    }

    public Boolean isLoged() {
        return loged;
    }

    public void logIn() {
        this.loged = true;
    }

    public void logOut() {
        this.loged = false;
    }
}
