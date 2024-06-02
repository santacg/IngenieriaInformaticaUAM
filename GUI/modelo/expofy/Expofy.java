package GUI.modelo.expofy;

import java.io.*;
import java.util.*;
import java.io.File;

import java.time.LocalDate;

import GUI.modelo.centroExposicion.*;
import GUI.modelo.exposicion.*;
import GUI.modelo.inscripcion.Inscripcion;
import GUI.modelo.tarjetaDeCredito.TarjetaDeCredito;
import GUI.modelo.usuario.Usuario;
import es.uam.eps.padsof.tickets.NonExistentFileException;
import es.uam.eps.padsof.tickets.TicketSystem;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;

import es.uam.eps.padsof.telecard.*;

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
    private Set<CentroExposicion> centrosExposicion = new HashSet<CentroExposicion>();
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
    public Set<CentroExposicion> getCentrosExposicion() {
        return centrosExposicion;
    }

    /**
     * Establece el conjunto de centros de exposición en Expofy.
     * 
     * @param centroExposicion El conjunto de centros de exposición a establecer.
     */
    public void setCentrosExposicion(Set<CentroExposicion> centroExposicion) {
        this.centrosExposicion = centroExposicion;
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
     * Obtiene el conjunto de clientes con la cuenta bloqueada en Expofy.
     * 
     * @return Un conjunto de clientes bloqueados.
     */
    public Set<ClienteRegistrado> getClientesBloqueados() {
        Set<ClienteRegistrado> clientesBloqueados = new HashSet<>();
        for (ClienteRegistrado clienteRegistrado : clientesRegistrados) {
            clientesBloqueados.add(clienteRegistrado);
        }
        return clientesBloqueados;
    }

    /**
     * Obtiene un cliente registrado en Expofy basándose en su NIF.
     * 
     * @param NIF El NIF del cliente a obtener.
     * @return El cliente registrado, o null si no se encuentra.
     */
    public ClienteRegistrado getClienteRegistrado(String NIF) {
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getNIF().equals(NIF)) {
                return c;
            }
        }
        return null;
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
    public Boolean addCentroExposicion(CentroExposicion centroExposicion) {
        if (this.centrosExposicion.add(centroExposicion)) {
            return true;
        } else {
            return false;
        }
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
     * Elimina un centro de exposición del conjunto de centros gestionados por
     * Expofy.
     * 
     * @param centroExposicion El centro de exposición a eliminar.
     */
    public void removeCentroExposicion(CentroExposicion centroExposicion) {
        this.centrosExposicion.remove(centroExposicion);
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
     * Registra un nuevo cliente en Expofy, creando un perfil con sus datos
     * personales y preferencias.
     * 
     * @param NIF         El NIF del cliente.
     * @param Contrasenia La contraseña del cliente.
     * @param publicidad  Indica si el cliente acepta recibir publicidad.
     */
    public Boolean registrarCliente(String NIF, String Contrasenia, Boolean publicidad) {
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getNIF().equals(NIF)) {
                return false;
            }
        }

        ClienteRegistrado cliente = new ClienteRegistrado(NIF, publicidad, Contrasenia, false, null, null);
        this.clientesRegistrados.add(cliente);
        return true;
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

    /**
     * Intenta enviar una notificación a un cliente específico si el empleado que
     * realiza la acción está logueado y tiene permisos para enviar mensajes.
     *
     * @param mensaje  El mensaje de la notificación a enviar.
     * @param NIF      El NIF del cliente al que se enviará la notificación.
     * @param empleado El empleado que intenta enviar la notificación.
     * @return true si la notificación se envió con éxito, false en caso contrario 
     */
    public boolean enviarNotificacionCliente(String mensaje, String NIF, Empleado empleado) {
        if (empleado.isLoged() == false || empleado.getPermisoMensajes() == false) {
            return false;
        }
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getNIF().equals(NIF)) {
                c.addNotificacion(notificacion);
                this.notificaciones.add(notificacion);
                return true;
            }
        }
        return false;
    }

    /**
     * Envía una notificación a todos los clientes registrados en Expofy que hayan
     * aceptado recibir publicidad.
     * 
     * @param mensaje El mensaje de la notificación.
     */
    public void enviarNotificacionesClientesPublicidad(String mensaje) {
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        for (ClienteRegistrado c : this.clientesRegistrados) {
            if (c.getPublicidad() == true) {
                c.addNotificacion(notificacion);
            }
        }
    }

    /**
     * Envía una notificación a un conjunto específico de usuarios.
     * 
     * @param mensaje  El mensaje de la notificación.
     * @param usuarios Los usuarios que recibirán la notificación.
     *
     */
    public void enviarNotificacionUsuarios(String mensaje, Set<Usuario> usuarios) {
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        for (Usuario u : usuarios) {
            u.addNotificacion(notificacion);
        }
        this.notificaciones.add(notificacion);
    }

    /**
     * Envía una notificación a un único usuario.
     * 
     * @param mensaje   El mensaje de la notificación.
     * @param usuario   El usuario qeu recibe la notificacion
     *
     */
    public void enviarNotificacionUsuario(String mensaje, Usuario usuario) {
        Notificacion notificacion = new Notificacion(mensaje, LocalDate.now());
        usuario.addNotificacion(notificacion);
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
            Hora hora, Integer nEntradas, TarjetaDeCredito tarjetaDeCredito, String... codigos) {
        Double precioFinal;

        // Verifica si el cliente está logueado. Si no, la compra no puede proceder.
        if (clienteRegistrado.isLoged() == false) {
            System.out.println("El cliente no está logeado");
            return false;
        }

        // Verifica si la fecha de la visita está dentro del rango de fechas de la
        // exposición.
        if (fecha.isBefore(exposicion.getFechaInicio()) || fecha.isAfter(exposicion.getFechaFin())) {
            System.out.println("La fecha no está dentro del rango de la exposición");
            return false;
        }

        // Verifica el estado de la exposición, solo procede si está Prorrogada o
        // Publicada.
        if (!exposicion.getEstado().equals(EstadoExposicion.PUBLICADA)) {
            System.out.println("La exposición no está disponible");
            return false;
        }

        // Asegura que la fecha y la hora de la visita coincidan.
        if (!fecha.isEqual(hora.getFecha())) {
            System.out.println("La fecha no coincide con la fecha de la hora");
            return false;
        }

        // Verifica la disponibilidad de la hora elegida dentro del horario de la
        // exposición.

        if (nEntradas <= 0) {
            System.out.println("El número de entradas no puede ser menor o igual a 0");
            return false;
        }

        // Verifica la disponibilidad de entradas para la hora seleccionada.
        if (nEntradas >= hora.getCountEntradas() || nEntradas > hora.getCountEntradas()) {
            System.out.println("No hay suficientes entradas disponibles");
            return false;
        }
        precioFinal = exposicion.getPrecio() * nEntradas;
        // Aplica descuento si corresponde y verifica la validez del código del código.
        Descuento descuento = exposicion.getDescuento();
        if (descuento != null) {
            if (descuento.validezDescuento(clienteRegistrado.getUltimaCompra())) {
                precioFinal = precioFinal * descuento.getDescuento();
            }
        }
        for (String codigo : codigos) {
            if (validezCodigo(codigo, clienteRegistrado) == true) {
                precioFinal = precioFinal - exposicion.getPrecio();
            }
        }

        // Verifica la validez del número de tarjeta de crédito y realiza el cobro.
        if (!TeleChargeAndPaySystem.isValidCardNumber((tarjetaDeCredito.getNumero())))
            return false;

        try {
            TeleChargeAndPaySystem.charge(tarjetaDeCredito.getNumero(), "Entradas", precioFinal, true);
        } catch (InvalidCardNumberException e) {
            return false;
        } catch (FailedInternetConnectionException e) {
            return false;
        } catch (OrderRejectedException e) {
            return false;
        }

        // Procede con la compra, actualizando las estadísticas y asignando entradas.
        Estadisticas estadisticas = exposicion.getEstadisticas();
        int i;
        for (i = 0; i < nEntradas; i++) {
            Entrada entrada = new Entrada(clienteRegistrado, tarjetaDeCredito);
            exposicion.addEntrada(entrada);
            hora.entradaVendida();
            estadisticas.incrementarTicketsVendidos();
            estadisticas.incrementarIngresosTotales(exposicion.getPrecio());
        }

        try {
            TicketSystem.createTicket(new Ticket(exposicion, precioFinal, nEntradas, fecha, hora),
                    "." + File.separator + "GUI" +  File.separator + "resources");
        } catch (NonExistentFileException e) {
            return false;
        } catch (UnsupportedImageTypeException e) {
            return false;
        }

        clienteRegistrado.setUltimaCompra(LocalDate.now());
        enviarNotificacionUsuario("Las " + nEntradas + "entrada/s se ha/n comprado con éxito", clienteRegistrado);

        return true;
    }

    /**
     * Persiste el estado actual de Expofy en un archivo .dat para poder reanudarlo
     * más tarde.
     */
    public void persistirExpofy() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream("ExpofyData.dat"))) {
            out.writeObject(this);
        } catch (IOException except) {
            except.printStackTrace();
        }
    }

    /**
     * Reanuda la instancia de Expofy desde un archivo guardado, si existe.
     */
    public void reanudarExpofy() {
        // Definir la ruta del archivo donde se guardan los datos de Expofy
        File file = new File("ExpofyData.dat");

        // Comprobar si el archivo existe para evitar errores
        if (!file.exists()) {
            System.out.println("El archivo de datos de Expofy no existe. Se creará una nueva instancia.");
            return; // Salir del método si el archivo no existe
        }

        // Intentar leer el objeto Expofy desde el archivo
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
            // Leer la instancia de Expofy desde el archivo
            Expofy nuevo = (Expofy) input.readObject();
            copiarExpofy(nuevo);
            System.out.println("La instancia de Expofy ha sido reanudada con éxito desde el archivo.");
        } catch (IOException except) {
            System.err.println("Se produjo un error al leer el archivo de datos de Expofy: " + except.getMessage());
            except.printStackTrace();
        } catch (ClassNotFoundException except) {
            System.err.println("No se encontró la clase Expofy durante la deserialización: " + except.getMessage());
            except.printStackTrace();
        }
    }

    /**
     * Copia el estado de una instancia de Expofy a esta instancia.
     *
     * @param nuevo La nueva instancia de Expofy cuyo estado se copiará.
     */
    private void copiarExpofy(Expofy nuevo) {
        centrosExposicion = new HashSet<>(nuevo.getCentrosExposicion());
        notificaciones = new ArrayList<>(nuevo.getNotificaciones());
        clientesRegistrados = new HashSet<>(nuevo.getClientesRegistrados());
    }

    /**
     * Valida si un código de inscripción es válido para un cliente específico.
     *
     * @param codigo  El código de inscripción a validar.
     * @param cliente El cliente al que pertenece el código.
     * @return true si el código es válido y pertenece al cliente,
     *        false en caso contrario.
     */
    public boolean validezCodigo(String codigo, ClienteRegistrado cliente) {
        for (CentroExposicion centroExposicion : centrosExposicion) {
            for (Sorteo sorteo : centroExposicion.getSorteos()) {
                for (Inscripcion inscripcion : sorteo.getInscripciones()) {
                    if (inscripcion.getCliente().equals(cliente)) {
                        for (String codigo_c : inscripcion.getCodigos()) {
                            if (codigo_c.compareTo(codigo) == 0) {
                                inscripcion.removeCodigo(codigo);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Obtiene una exposición por su nombre.
     * 
     * @param nombreExposicion el nombre de la exposición a buscar.
     * @return la exposición con el nombre.
     */
    public Exposicion getExposicionPorNombre(String nombreExposicion) {
        for (CentroExposicion centro : centrosExposicion) {
            for (Exposicion exposicion : centro.getExposiciones()) {
                if (nombreExposicion.equals(exposicion.getNombre())) {
                    return exposicion;
                }
            }
        }
        return null;
    }

    /**
     * Genera una representación en cadena de la instancia de Expofy, incluyendo
     * centros de exposición, notificaciones y clientes registrados.
     * 
     * @return La representación en cadena de Expofy.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Expofy:\n");
        sb.append("Centros de Exposición:\n");
        for (CentroExposicion centro : centrosExposicion) {
            sb.append(centro.toString()).append("\n");
        }
        sb.append("Notificaciones:\n");
        for (Notificacion notificacion : notificaciones) {
            sb.append(notificacion.toString()).append("\n");
        }
        sb.append("Clientes Registrados:\n");
        for (ClienteRegistrado cliente : clientesRegistrados) {
            sb.append(cliente.toString()).append("\n");
        }
        return sb.toString();
    }
}
