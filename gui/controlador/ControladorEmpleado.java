package gui.controlador;

import java.util.List;
import java.awt.event.*;
import java.util.*;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.*;
import gui.modelo.expofy.Expofy;
import gui.vistas.EmpleadoPrincipal;
import gui.vistas.Ventana;

/**
 * Clase ControladorEmpleado.
 * Implementa el controlador de la vista EmpleadoPrincipal.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorEmpleado {
    private Ventana frame;
    private EmpleadoPrincipal vista;
    private Expofy expofy;
    private Empleado empleado;
    private CentroExposicion centro;

    /**
     * Constructor de la clase ControladorEmpleado.
     * 
     * @param frame    Ventana
     * @param expofy   Expofy
     * @param centro   CentroExposicion
     * @param empleado String
     */
    public ControladorEmpleado(Ventana frame, Expofy expofy, CentroExposicion centro, Empleado empleado) {
        this.frame = frame;
        this.frame.setCartaEmpleadoPrincipal();
        this.expofy = expofy;
        this.centro = centro;
        this.vista = frame.getVistaEmpleadoPrincipal();
        this.empleado = empleado;
        this.vista.hideButtons(this.empleado.getPermisoMensajes(), this.empleado.getPermisoControl(),
                this.empleado.getPermisoVenta(), this.empleado.getPermisoActividades());
        vista.addTitle();

    }

    /**
     * Método que inicializa el controlador cambiarClimatizacionListener.
     */
    private ActionListener cambiarClimatizacionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (empleado.getPermisoControl() == false) {
                JOptionPane.showMessageDialog(frame,
                        "Actualmente no tienes permiso para cambiar la climatización. Solicita al gestor que modifique tu permiso.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            ControladorAjustarClimatizacion controladorAjustarClimatizacion = new ControladorAjustarClimatizacion(frame,
                    centro);
            frame.setControladorAjustarClimatizacion(controladorAjustarClimatizacion);
            frame.mostrarPanel(frame.getAjustarClimatizacion());
        }
    };

    /**
     * Método que devuelve el listener del botón de cambiar climatización.
     * 
     * @return ActionListener
     */
    public ActionListener getClimatizacionListener() {
        return cambiarClimatizacionListener;
    }

    /**
     * Método que inicializa el controlador enviarMsjListener.
     */
    private ActionListener enviarMsjListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (empleado.getPermisoMensajes() == false) {
                JOptionPane.showMessageDialog(frame,
                        "Actualmente no tienes permiso para enviar mensajes. Solicita al gestor que modifique tu permiso.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;

            }
            ControladorEnviarMensajes controladorEnviarMensajes = new ControladorEnviarMensajes(frame, expofy,
                    empleado);
            frame.setControladorEnviarMensajes(controladorEnviarMensajes);
            frame.mostrarPanel(frame.getEnviarMensajes());
        }
    };

    /**
     * Método que devuelve el listener del botón de enviar mensajes.
     * 
     * @return ActionListener
     */
    public ActionListener getEnviarMsjListener() {
        return enviarMsjListener;
    }

    /**
     * Método que inicializa el listener del boton de inscribir en actividades.
     * 
     * @return ActionListener
     */
    private ActionListener inscribirActividadListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (empleado.getPermisoActividades() == false) {
                JOptionPane.showMessageDialog(frame,
                        "Actualmente no tienes permiso para inscribir en actividades. Solicita al gestor que modifique tu permiso.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Set<Actividad> actividades = centro.getActividades();
            List<String> nombresActividades = new ArrayList<>();

            if (actividades.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No hay actividades disponibles.");
                return;
            }

            for (Actividad actividad : actividades) {
                nombresActividades.add(actividad.getNombre());
            }

            String nombreActividad = (String) JOptionPane.showInputDialog(frame,
                    "Selecciona la actividad a la que quieres inscribir al cliente:",
                    "Inscribir en actividad", JOptionPane.QUESTION_MESSAGE, null, nombresActividades.toArray(),
                    nombresActividades.get(0));

            if (nombreActividad == null) {
                JOptionPane.showMessageDialog(frame, "No se ha seleccionado ninguna actividad.");
                return;
            }

            Actividad actividad = centro.getActividadPorNombre(nombreActividad);

            String NIF = JOptionPane.showInputDialog(frame,
                    "Introduce el NIF del cliente a inscribir en la actividad:");

            if (NIF == null) {
                JOptionPane.showMessageDialog(frame, "No se ha introducido ningún NIF.");
                return;
            }

            if (centro.inscribirClienteActividad(actividad, NIF) == false) {
                JOptionPane.showMessageDialog(frame, "No se ha podido inscribir al cliente en la actividad.");
                return;
            }

            JOptionPane.showMessageDialog(frame, "Cliente inscrito correctamente en la actividad.");
        }
    };

    /**
     * Método que devuelve el listener del botón de inscribir en actividades.
     * 
     * @return ActionListener
     */
    public ActionListener getInscribirActividad() {
        return inscribirActividadListener;
    }

    /**
     * Método que inicializa el controlador cerrarSesionListener.
     */
    private ActionListener cerrarSesionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            empleado.logOut();
            vista.removeControlador(enviarMsjListener, cambiarClimatizacionListener, cambiarClimatizacionListener,
                    cerrarSesionListener, desbloquearListener, inscribirActividadListener);
            vista.removeAll();
            ControladorPantallaPrincipal controlador = new ControladorPantallaPrincipal(frame, expofy);
            frame.setControladorPantallaPrincipal(controlador);
            JOptionPane.showMessageDialog(frame, "Sesión cerrada correctamente");
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

    /**
     * Método que devuelve el listener del botón de cerrar sesión.
     * 
     * @return ActionListener
     */
    public ActionListener getCerrarSesionListener() {
        return cerrarSesionListener;
    }

    /**
     * Método que devuelve el listener del botón de desbloquear clientes.
     * 
     * @return ActionListener
     */
    public ActionListener getDesbloquearListener() {
        return desbloquearListener;
    }

    /**
     * Método que inicializa el controlador venderEntradaListener.
     */
    private ActionListener desbloquearListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorDesbloqueoClientes controladorDesbloqueoClientes = new ControladorDesbloqueoClientes(frame,
                    expofy);
            frame.setControladorDesbloqueoClientes(controladorDesbloqueoClientes);
            frame.mostrarPanel(frame.getDesbloqueoClientes());
        }
    };

    /**
     * Método que inicializa el controlador venderEntradaListener.
     */
    private ActionListener venderEntradaListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (empleado.getPermisoVenta() == false) {
                JOptionPane.showMessageDialog(frame,
                        "Actualmente no tienes permiso para la venta de entradas. Solicita al gestor que modifique tu permiso.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            ControladorVentaEntradas controladorVentaEntradas = new ControladorVentaEntradas(frame, expofy, centro,
                    empleado);
            frame.setControladorVentaEntradas(controladorVentaEntradas);
            frame.mostrarPanel(frame.getVentaEntradas());
        }
    };

    /**
     * Método que devuelve el listener del botón de vender entradas.
     * 
     * @return ActionListener
     */
    public ActionListener getVenderEntrada() {
        return venderEntradaListener;
    }

}
