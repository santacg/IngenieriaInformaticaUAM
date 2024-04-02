package Expofy;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import Usuario.Usuario;

public class Notificacion {
    private String mensaje;
    private LocalDate fecha;
    private boolean leida;
    private Set<Usuario> usuarios = new HashSet<>();

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

    public void setLeida() {
        this.leida = true;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
    }   

    public void removeAllUsuarios() {
        this.usuarios.clear();
    }

    public String toString() {
        return "Notificacion [mensaje=" + mensaje + ", fecha=" + fecha + ", leida=" + leida + "]";
    }

}
