package Expofy;

import java.io.*;
import java.util.*;

import CentroExposicion.CentroExposicion;
import CentroExposicion.Sorteo;
import Entrada.Entrada;
import Exposicion.Estadisticas;
import Exposicion.EstadoExposicion;
import Exposicion.Exposicion;
import Exposicion.Hora;
import Inscripcion.Inscripcion;
import TarjetaDeCredito.TarjetaDeCredito;
import Usuario.Usuario;

/**
 * Clase Expofy.
 * Esta clase principal de la aplicacion gestiona centros de exposición,
 * notificaciones y clientes registrados.
 * Implementa el patrón Singleton ( instancia única es un patrón de diseño que
 * restringe la creación a un único objeto) para asegurar una única instancia de
 * la clase en la aplicación.
 *
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class Expofy implements Serializable {
    private static Expofy instance;
    private Set<CentroExposicion> centroExposicion = new HashSet<CentroExposicion>();
    private List<Notificacion> notificaciones = new ArrayList<Notificacion>();
    private Set<ClienteRegistrado> clientesRegistrados = new HashSet<ClienteRegistrado>();

    /**
     * Obtiene la instancia única de Expofy. Si no existe, la crea.
     * 
     * @return La instancia única de Expofy.
     */
    public static Expofy getInstance() {
        if (instance == null) {
            instance = new Expofy();
        }
        return instance;
    }

    /**
     * Establece una instancia de Expofy, principalmente para propósitos de prueba.
     * 
     * @param instance La instancia de Expofy a establecer.
     */
    public static void setInstance(Expofy instance) {
        Expofy.instance = instance;
    }

    /**
     * Obtiene el conjunto de centros de exposición registrados en Expofy.
     * 
     * @return Un conjunto de centros de exposición.
     */
    public Set<CentroExposicion> getCentroExposicion() {
        return centroExposicion;
    }

    /**
     * Establece el conjunto de centros de exposición en Expofy.
     * 
     * @param centroExposicion El conjunto de centros de exposición a establecer.
     */
    public void setCentroExposicion(Set<CentroExposicion> centroExposicion) {
        this.centroExposicion = centroExposicion;
    }

    /**
     * Obtiene la lista de notificaciones registradas en Expofy.
     * 
     * @return Una lista de notificaciones.
     */
    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    /**
     * Establece la lista de notificaciones en Expofy.
     * 
     * @param notificaciones La lista de notificaciones a establecer.
     */
    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    /**
     * Obtiene el conjunto de clientes registrados en Expofy.
     * 
     * @return Un conjunto de clientes registrados.
     */
    public Set<ClienteRegistrado> getClientesRegistrados() {
        return clientesRegistrados;
    }

    /**
     * Establece el conjunto de clientes registrados en Expofy.
     * 
     * @param clientesRegistrados El conjunto de clientes registrados a establecer.
     */
    public void setClientesRegistrados(Set<ClienteRegistrado> clientesRegistrados) {
        this.clientesRegistrados = clientesRegistrados;
    }

    /**
     * Añade un nuevo centro de exposición al conjunto de centros gestionados por
     * Expofy.
     * 
     * @param centroExposicion El centro de exposición a añadir.
     */
    public void addCentroExposicion(CentroExposicion centroExposicion) {
        this.centroExposicion.add(centroExposicion);
    }

    /**
     * Añade una nueva notificación a la lista de notificaciones de Expofy.
     * 
     * @param notificacion La notificación a añadir.
     */
    public void addNotificacion(Notificacion notificacion) {
        this.notificaciones.add(notificacion);
    }

    /**
     * Añade un nuevo cliente registrado al conjunto de clientes de Expofy.
     * 
     * @param clienteRegistrado El cliente registrado a añadir.
     */
    public void addClienteRegistrado(ClienteRegistrado clienteRegistrado) {
        this.clientesRegistrados.add(clienteRegistrado);
    }

    /**
     * Elimina un centro de exposición del conjunto de centros gestionados por
     * Expofy.
     * 
     * @param centroExposicion El centro de exposición a eliminar.
     */
    public void removeCentroExposicion(CentroExposicion centroExposicion) {
        this.centroExposicion.remove(centroExposicion);
    }

    /**
     * Elimina una notificación de la lista de notificaciones de Expofy.
     * 
     * @param notificacion La notificación a eliminar.
     */
    public void removeNotificacion(Notificacion notificacion) {
        this.notificaciones.remove(notificacion);
    }

    /**
     * Elimina un cliente registrado del conjunto de clientes de Expofy.
     * 
     * @param clienteRegistrado El cliente registrado a eliminar.
     */
    public void removeClienteRegistrado(ClienteRegistrado clienteRegistrado) {
        this.clientesRegistrados.remove(clienteRegistrado);
    }

    /**
     * Registra un nuevo cliente en Expofy, creando un perfil con sus datos
     * personales y preferencias.
     * 
     * @param NIF         El NIF del cliente.
     * @param Contrasenia La contraseña del cliente.
     * @param publicidad  Indica si el cliente acepta recibir publicidad.
     */
    public void registrarCliente(String NIF, String Contrasenia, Boolean publicidad) {
        ClienteRegistrado cliente = new ClienteRegistrado(NIF, publicidad, Contrasenia, false, null, null);
        this.clientesRegistrados.add(cliente);
    }

    /**
     * Elimina un cliente registrado basándose en su NIF.
     * 
     * @param NIF El NIF del cliente a eliminar.
     */
    public void eliminarCliente(String NIF) {
        ClienteRegistrado cliente = null;
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getNIF().equals(NIF)) {
                cliente = c;
            }
        }
        this.clientesRegistrados.remove(cliente);
    }

    /**
     * Permite a un cliente registrado acceder a su cuenta en Expofy, verificando su
     * identidad.
     * 
     * @param NIF         El NIF del cliente.
     * @param Contrasenia La contraseña del cliente.
     * @return true si el login es exitoso, false en caso contrario.
     */
    public boolean loginCliente(String NIF, String Contrasenia) {
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getNIF().equals(NIF) && c.getContrasenia().equals(Contrasenia)) {
                c.logIn();
                return true;
            }
        }
        return false;
    }

    /**
     * Cierra la sesión de un cliente registrado.
     * 
     * @param clienteRegistrado El cliente que cierra sesión.
     */
    public void logOut(ClienteRegistrado clienteRegistrado) {
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getNIF().equals(clienteRegistrado.getNIF())) {
                c.logOut();
            }
        }
    }

    public boolean enviarNotificacionCliente(String mensaje, Set<ClienteRegistrado> clientes, Empleado empleado) {
        if (empleado.isLoged() == false || empleado.getPermisoMensajes() == false) {
            return false;
        }
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        for (ClienteRegistrado c : clientes) {
            c.addNotificacion(notificacion);
        }
        this.notificaciones.add(notificacion);
    }

    /**
     * Envía una notificación a un conjunto específico de usuarios.
     * 
     * @param mensaje  El mensaje de la notificación.
     * @param usuarios Los usuarios que recibirán la notificación.
     */
    public void enviarNotificacion(String mensaje, Set<Usuario> usuarios) {
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        for (Usuario u : usuarios) {
            u.addNotificacion(notificacion);
        }
        this.notificaciones.add(notificacion);
    }

    /**
     * Envía una notificación a todos los clientes registrados en Expofy.
     * 
     * @param mensaje El mensaje de la notificación.
     */
    public void enviarNotificacionAll(String mensaje) {
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        for (ClienteRegistrado c : this.clientesRegistrados) {
            c.addNotificacion(notificacion);
        }
        this.notificaciones.add(notificacion);
    }

    /**
     * Valida y procesa la compra de entradas para una exposición, verificando
     * diversos criterios.
     * 
     * @param clienteRegistrado El cliente que realiza la compra.
     * @param exposicion        La exposición para la que se compran las entradas.
     * @param fecha             La fecha de la visita.
     * @param hora              La hora de la visita.
     * @param nEntradas         El número de entradas a comprar.
     * @param tarjetaDeCredito  La tarjeta de crédito para el pago.
     * @return true si la compra es exitosa, false en caso contrario.
     */
    public boolean comprarEntrada(ClienteRegistrado clienteRegistrado, Exposicion exposicion, LocalDate fecha,
            Hora hora, Integer nEntradas, TarjetaDeCredito tarjetaDeCredito, String codigo) {
        Boolean horaDisponible = false;
        Double precioFinal;
        // Verificaciones varias: cliente logueado, fecha y hora dentro del rango, y
        // número de entradas válido.
        if (clienteRegistrado.isLoged() == false) {
            System.out.println("El cliente no está logeado");
            return false;
        }

        if (fecha.isBefore(exposicion.getFechaInicio()) || fecha.isAfter(exposicion.getFechaFin())) {
            System.out.println("La fecha no está dentro del rango de la exposición");
            return false;
        }

        if (!exposicion.getEstado().equals(EstadoExposicion.PRORROGADA)
                || !exposicion.getEstado().equals(EstadoExposicion.PUBLICADA)) {
            System.out.println("La exposición no está disponible");
            return false;
        }

        if (!fecha.equals(hora.getFecha())) {
            System.out.println("La fecha no coincide con la fecha de la hora");
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

        if (nEntradas >= hora.getCountEntradas() || nEntradas > hora.getCountEntradas()) {
            System.out.println("No hay suficientes entradas disponibles");
            return false;
        }

        

        if (!TeleChargeAndPaySystem.isValidCardNumber(tarjetaDeCredito.getNumero()))
            return false;

        if(!TeleChargeAndPaySystem.charge(tatarjetaDeCredito.getNumero(), "Entrada exposición", precioFinal, true)){
            return false;
        }

        Estadisticas estadisticas = exposicion.getEstadisticas();
        int i;
        for (i = 0; i < nEntradas; i++) {
            Entrada entrada = new Entrada();
            entrada.addClienteRegistrado(clienteRegistrado);
            entrada.setTarjetaDeCredito(tarjetaDeCredito);
            hora.entradaVendida();
            estadisticas.incrementarTicketsVendidos();
            estadisticas.incrementarIngresosTotales(exposicion.getPrecio());
        }

        return true;
    }
    
    public void updateSanciones() {
        for (CentroExposicion centro : centroExposicion) {
            for (Sorteo sorteo : centro.getSorteos()) {
                for (Inscripcion inscripcion : sorteo.getInscripciones()) {
                    for (String codigo : inscripcion.getCodigos()) {
                        if (codigo != null && sorteo.getFechaLimite().isBefore(LocalDate.now())) {
                            inscripcion.getCliente().setSancionadoHasta(LocalDate.now().plusDays(centro.getSancion()));
                        }
                    }
                }
            }
        }
    }

    public void persistirExpofy() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream("ExpofyData.dat"))) {
            out.writeObject(this);
        } catch (IOException except) {
            e.printStackTrace();
        }
    }

    public void reanudarExpofy() {
        File file = new File("ExpofyData.dat");
        if (!file.exists())
            return;

        try (
                ObjectInputStream input = new ObjectInputStream(
                        new FileInputStream("ExpofyData.dat"))) {
            Expofy nuevo = (Expofy) in.readObject();
            copiarExpofy(nuevo);
            for (Exposicion exposicion : exposiciones) {
                e.checkEstadoExposicion();
            }
            instance = this;
        } catch (IOException | ClassNotFoundException except) {
            e.printStackTrace();
        }
    }

    private void copiarExpofy(Expofy nuevo) {
        centroExposicion = new HashSet<>(nuevo.CentroExposicion);
        notificaciones = new ArrayList<>(nuevo.Notificacion);
        clientesRegistrados = new HashSet<>(nuevo.ClienteRegistrado);
    }

    /**
     * Genera una representación en cadena de la instancia de Expofy, incluyendo
     * centros de exposición, notificaciones y clientes registrados.
     * 
     * @return La representación en cadena de Expofy.
     */
    public String toString() {
        return "Expofy [centroExposicion=" + centroExposicion + ", notificaciones=" + notificaciones.toString()
                + ", clientesRegistrados=" + clientesRegistrados.toString() + "]";
    }

}
