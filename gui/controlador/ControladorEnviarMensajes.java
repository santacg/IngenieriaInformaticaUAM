package gui.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.Empleado;
import gui.modelo.expofy.Expofy;
import gui.vistas.EnviarMensajes;
import gui.vistas.Ventana;

/**
 * Clase ControladorEnviarMensajes
 * Actúa como controlador de la vista EnviarMensajes.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorEnviarMensajes {
    private Ventana frame;
    private EnviarMensajes vistaEnviarMensajes;
    private Expofy expofy;
    private Empleado empleado;

    /**
     * Constructor de la clase ControladorEnviarMensajes
     * 
     * @param frame    Ventana en la que se muestra la vista
     * @param expofy   Expofy que contiene los datos de la aplicación
     * @param empleado Empleado que está utilizando la aplicación
     */
    public ControladorEnviarMensajes(Ventana frame, Expofy expofy, Empleado empleado) {
        this.frame = frame;
        this.expofy = expofy;
        this.vistaEnviarMensajes = frame.getVistaEnviarMensajes();
        this.empleado = empleado;
    }

    /**
     * Inicializa el controlador enviarListener.
     */
    private ActionListener enviarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String destinatarios = vistaEnviarMensajes.getDestinatario().trim();
            String mensaje = vistaEnviarMensajes.getMensaje();
            if (mensaje.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Debes introducir un mensaje.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (destinatarios.isEmpty()) {
                expofy.enviarNotificacionesClientesPublicidad(mensaje);
                JOptionPane.showMessageDialog(frame, "Mensaje enviado a todos los clientes que permiten publicidad!");
            } else {
                String[] NIFS = destinatarios.split("\\s+");
                for (String NIF : NIFS) {
                    expofy.enviarNotificacionCliente(mensaje, NIF, empleado);
                }
                JOptionPane.showMessageDialog(frame, "Mensaje enviado a los destinatarios especificados!");
            }
        }
    };
    /**
     * Inicializa el controlador atrasListener.
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vistaEnviarMensajes.update();
            frame.mostrarPanelPrevio();
        }
    };

    /**
     * Devuelve el ActionListener del botón atras.
     * 
     * @return atrasListener
     */
    public ActionListener getAtrasListener() {
        return atrasListener;
    }

    /**
     * Devuelve el ActionListener del botón enviar.
     * 
     * @return enviarListener
     */
    public ActionListener getEnviarListener() {
        return enviarListener;
    }
}