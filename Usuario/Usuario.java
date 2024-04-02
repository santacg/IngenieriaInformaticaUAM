package Usuario;

import java.util.Set;

import Expofy.Notificacion;

/**
 * Clase Usuario.
 * Es una clase abstra que aporta las funcionalidades básicas de los usuarios:
 * gestor, empleado y cliente registrado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public abstract class Usuario {
    private String NIF;
    private Set<Notificacion> notificaciones;
    private Boolean loged = false;
/**
     * Constructor para crear un nuevo usuario.
     *
     * @param NIF El Número de Identificación Fiscal del usuario.
     */
    public Usuario(String NIF) {
        this.NIF = NIF;
    }

    /**
     * Comprueba si este usuario es igual a otro objeto.
     *
     * @param obj El objeto con el que se compara.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        // Implementación omitida para brevedad
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
}
