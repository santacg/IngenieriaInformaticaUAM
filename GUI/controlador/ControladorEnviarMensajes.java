package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.centroExposicion.Empleado;
import GUI.modelo.expofy.Expofy;
import GUI.vistas.EnviarMensajes;
import GUI.vistas.Ventana;

public class ControladorEnviarMensajes {
    private Ventana frame;
    private EnviarMensajes vistaEnviarMensajes;
    private Expofy expofy;
    private Empleado empleado;

    public ControladorEnviarMensajes(Ventana frame, Expofy expofy, Empleado empleado) {
        this.frame = frame;
        this.expofy = expofy;
        this.vistaEnviarMensajes = frame.getVistaEnviarMensajes();
        this.empleado = empleado;
    }
    private ActionListener enviarListener = new ActionListener() {        
        public void actionPerformed(ActionEvent e) {
            String destinatario = vistaEnviarMensajes.getDestinatario();
            String mensaje = vistaEnviarMensajes.getMensaje();
            if (mensaje.equals("")) {
                JOptionPane.showMessageDialog(frame, "Debes introducir un mensaje.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else if (destinatario.equals("")) {
                expofy.enviarNotificacionesClientesPublicidad(mensaje);
                JOptionPane.showMessageDialog(frame, "Mensaje enviado!");
            }
            else {
                expofy.enviarNotificacionCliente(mensaje, destinatario, empleado);
                JOptionPane.showMessageDialog(frame, "Mensaje enviado a "+ destinatario + "!");
            }
        }
    };
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vistaEnviarMensajes.update();
            frame.mostrarPanelPrevio();
        }
    };
    public ActionListener getAtrasListener() {
        return atrasListener;
    }
    public ActionListener getEnviarListener() {
        return enviarListener;
    }
}