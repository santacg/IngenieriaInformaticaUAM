package GUI.modelo.usuario;

import java.util.HashSet;
import java.util.Set;

import GUI.modelo.expofy.Notificacion;

import java.io.Serializable;

/**
 * Clase Usuario.
 * Es una clase abstra que aporta las funcionalidades básicas de los usuarios:
 * gestor, empleado y cliente registrado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public abstract class Usuario implements Serializable {
    private String NIF;
    private Set<Notificacion> notificaciones = new HashSet<>();
    private Boolean loged = false;

    /**
     * Comprueba si este usuario es igual al objeto proporcionado.
     * Dos usuarios se consideran iguales si tienen el mismo NIF.
     *
     * @param obj El objeto con el que comparar este {@code Usuario}.
     * @return {@code true} si los objetos son iguales, {@code false} en caso
     *         contrario.
     */
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

    /**
     * Constructor para crear un nuevo usuario.
     *
     * @param NIF El Número de Identificación Fiscal del usuario.
     */
    public Usuario(String NIF) {
        this.NIF = NIF;
    }

    /**
     * Obtiene el NIF del usuario.
     *
     * @return El NIF del usuario.
     */
    public String getNIF() {
        return NIF;
    }

    /**
     * Establece el NIF del usuario.
     *
     * @param NIF El nuevo NIF del usuario.
     */
    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    /**
     * Obtiene el conjunto de notificaciones del usuario.
     *
     * @return El conjunto de notificaciones.
     */
    public Set<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    /**
     * Establece el conjunto de notificaciones del usuario.
     *
     * @param notificaciones El nuevo conjunto de notificaciones.
     */
    public void setNotificaciones(Set<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    /**
     * Añade una notificación al conjunto de notificaciones del usuario.
     *
     * @param notificacion La notificación a añadir.
     */
    public void addNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }

    /**
     * Elimina una notificación del conjunto de notificaciones del usuario.
     *
     * @param notificacion La notificación a eliminar.
     */
    public void removeNotificacion(Notificacion notificacion) {
        notificaciones.remove(notificacion);
    }

    /**
     * Elimina todas las notificaciones del usuario.
     */
    public void removeAllNotificaciones() {
        notificaciones.clear();
    }

    /**
     * Comprueba si el usuario está actualmente logueado.
     *
     * @return true si el usuario está logueado, false en caso contrario.
     */
    public Boolean isLoged() {
        return loged;
    }

    /**
     * Marca al usuario como logueado.
     */
    public void logIn() {
        this.loged = true;
    }

    /**
     * Marca al usuario como no logueado.
     */
    public void logOut() {
        this.loged = false;
    }

    /**
     * Obtiene el mensaje de una notificación dada.
     *
     * @param notificacion La notificacion de la que se desea obtener el mensaje.
     * @return El mensaje de la notificación proporcionada.
     */
    public String getMensajeNotificacion(Notificacion notificacion) {
        notificacion.setLeida(true);
        return notificacion.getMensaje();
    }
}
