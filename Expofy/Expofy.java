package Expofy;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import CentroExposicion.CentroExposicion;  

public class Expofy {
    private static Expofy instance;
    private Set<CentroExposicion> centroExposicion = new HashSet<CentroExposicion>();
    private List<Notificacion> notificaciones = new ArrayList<Notificacion>();
    private Set<ClienteRegistrado> clientesRegistrados = new HashSet<ClienteRegistrado>();

    public static Expofy getInstance() {
        if (instance == null) {
            instance = new Expofy();
        }
        return instance;
    }

    public static void setInstance(Expofy instance) {
        Expofy.instance = instance;
    }

    public Set<CentroExposicion> getCentroExposicion() {
        return centroExposicion;
    }

    public void setCentroExposicion(Set<CentroExposicion> centroExposicion) {
        this.centroExposicion = centroExposicion;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public Set<ClienteRegistrado> getClientesRegistrados() {
        return clientesRegistrados;
    }

    public void setClientesRegistrados(Set<ClienteRegistrado> clientesRegistrados) {
        this.clientesRegistrados = clientesRegistrados;
    }

    public void addCentroExposicion(CentroExposicion centroExposicion) {
        this.centroExposicion.add(centroExposicion);
    }

    public void addNotificacion(Notificacion notificacion) {
        this.notificaciones.add(notificacion);
    }

    public void addClienteRegistrado(ClienteRegistrado clienteRegistrado) {
        this.clientesRegistrados.add(clienteRegistrado);
    }

    public void removeCentroExposicion(CentroExposicion centroExposicion) {
        this.centroExposicion.remove(centroExposicion);
    }

    public void removeNotificacion(Notificacion notificacion) {
        this.notificaciones.remove(notificacion);
    }

    public void removeClienteRegistrado(ClienteRegistrado clienteRegistrado) {
        this.clientesRegistrados.remove(clienteRegistrado);
    }

    public String toString() {
        return "Expofy [centroExposicion=" + centroExposicion + ", notificaciones=" + notificaciones.toString()
                + ", clientesRegistrados=" + clientesRegistrados.toString() + "]";
    }

}
