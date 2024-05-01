package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.centroExposicion.Empleado;
import GUI.vistas.EmpleadoPrincipal;
import GUI.vistas.Ventana;
import GUI.modelo.expofy.Expofy;

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
     * @param frame   Ventana
     * @param expofy  Expofy
     * @param centro  CentroExposicion
     * @param usuario String
     */
    public ControladorEmpleado(Ventana frame, Expofy expofy, CentroExposicion centro, String usuario) {
        this.frame = frame;
        this.expofy = expofy;
        this.centro = centro;
        this.vista = frame.getVistaEmpleadoPrincipal();
        this.empleado = centro.getEmpleado(usuario);

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
     * Método que inicializa el controlador cerrarSesionListener.
     */
    private ActionListener cerrarSesionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            empleado.logOut();
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
            JOptionPane.showMessageDialog(frame,
                        "Mostrando pantalla de venta de entradas.");
            ControladorVentaEntradas controladorVentaEntradas = new ControladorVentaEntradas(frame,expofy, centro, empleado);
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
