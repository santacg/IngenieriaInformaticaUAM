package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.modelo.expofy.Expofy;
import GUI.vistas.EnviarMensajes;
import GUI.vistas.Ventana;
/* TODO: NO FUNCIONA BIEN */
public class ControladorEnviarMensajes {
    private Ventana frame;
    private EnviarMensajes vistaEnviarMensajes;
    private Expofy expofy;

    public ControladorEnviarMensajes(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.expofy = expofy;
        this.vistaEnviarMensajes = frame.getVistaEnviarMensajes();
    }
    private ActionListener enviarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String destinatario = vistaEnviarMensajes.getDestinatario();
            String mensaje = vistaEnviarMensajes.getMensaje();
            if (destinatario.equals("") || mensaje.equals("")) {
                JOptionPane.showMessageDialog(frame, "Debes introducir un nombre de usuario y un mensaje.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
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
}