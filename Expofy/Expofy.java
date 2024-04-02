package Expofy;

import java.util.HashSet;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import CentroExposicion.CentroExposicion;
import Exposicion.Exposicion;
import Exposicion.Hora;
import TarjetaDeCredito.TarjetaDeCredito;
import Usuario.Usuario;  

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

    public void registrarCliente(String NIF, String Contrasenia, Boolean publicidad) {
        ClienteRegistrado cliente = new ClienteRegistrado(NIF, publicidad, Contrasenia, false, null, null);
        this.clientesRegistrados.add(cliente);
    }

    public void eliminarCliente(String NIF) {
        ClienteRegistrado cliente = null;
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getNIF().equals(NIF)) {
                cliente = c;
            }
        }
        this.clientesRegistrados.remove(cliente);
    }

    public boolean loginCliente(String NIF, String Contrasenia) {
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getNIF().equals(NIF) && c.getContrasenia().equals(Contrasenia)) {
                c.logIn();
                return true;
            }
        }
        return false;
    }
    
    public void logOut(ClienteRegistrado clienteRegistrado) {
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getNIF().equals(clienteRegistrado.getNIF())) {
                c.logOut();
            }
        }
    }

    public void enviarNotificacion(String mensaje, Set<Usuario> usuarios) {
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        notificacion.setUsarios(usuarios);
        this.notificaciones.add(notificacion);
    }

    public void enviarNotificacionAll(String mensaje) {
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        for (ClienteRegistrado c : this.clientesRegistrados) {
            notificacion.addUsuario(c);
        }
        this.notificaciones.add(notificacion);
    }

    public boolean comprarEntrada(ClienteRegistrado clienteRegistrado, Exposicion exposicion, LocalDate fecha, Hora hora, Integer nEntradas, TarjetaDeCredito tarjetaDeCredito) {
        Boolean horaDisponible = false;
        
        if (clienteRegistrado.isLoged() == false) {
            System.out.println("El cliente no está logeado");
            return false;
        }

        if (fecha.isBefore(exposicion.getFechaInicio()) || fecha.isAfter(exposicion.getFechaFin())) {
            System.out.println("La fecha no está dentro del rango de la exposición");
            return false;
        }

        for (Hora h : exposicion.getHorario()) {
            if (hora.equals(h)) {
                horaDisponible = true;
                break;
            }
        }

        if (horaDisponible == false) {
            System.out.println("La hora no está dentro del rango de la exposición");
            return false;
        }

        if (nEntradas <= 0) {
            System.out.println("El número de entradas no puede ser menor o igual a 0");
            return false;
        }

        return true;
    }

    public String toString() {
        return "Expofy [centroExposicion=" + centroExposicion + ", notificaciones=" + notificaciones.toString()
                + ", clientesRegistrados=" + clientesRegistrados.toString() + "]";
    }

}
