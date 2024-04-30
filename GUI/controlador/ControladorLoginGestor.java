package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.expofy.Expofy;
import GUI.vistas.LoginGestor;
import GUI.vistas.Ventana;

/**
 * Clase ControladorLoginGestor
 * Actúa como controlador de la vista LoginGestor.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorLoginGestor {
    private Ventana frame;
    private LoginGestor vista;
    private Expofy expofy;

    /**
     * Constructor de la clase ControladorLoginGestor.
     * 
     * @param frame Ventana principal de la aplicación.
     * @param expofy Objeto de la clase Expofy.
     */
    public ControladorLoginGestor(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.expofy = expofy;
        this.vista = frame.getVistaLogInGestor();
    }

    /**
     * Método que inicializa el listener del botón de aceptar.
     */
    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String password = vista.getPassword();

            if (password.equals("")) {
                JOptionPane.showMessageDialog(frame, "Debes introducir una contraseña.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (CentroExposicion centro : expofy.getCentrosExposicion()) {
                if (centro.loginGestor(password) == true) {
                    JOptionPane.showMessageDialog(frame, "Bienvenido gestor!");
                    ControladorGestor controladorGestor = new ControladorGestor(frame, centro);
                    frame.setControladorGestor(controladorGestor);
                    vista.update();
                    frame.mostrarPanel(frame.getGestorPrincipal());
                    return;
                }
            }

            JOptionPane.showMessageDialog(frame, "Contraseña incorrecta.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    };

    /**
     * Método que inicializa el listener del botón de atrás.
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.update();
            frame.mostrarPanelPrevio();
        }
    };

    /**
     * Método que devuelve el listener del botón de aceptar.
     * 
     * @return Listener del botón de aceptar.
     */
    public ActionListener getAceptarListener() {
        return aceptarListener;
    }

    /**
     * Método que devuelve el listener del botón de atrás.
     * 
     * @return Listener del botón de atrás.
     */
    public ActionListener getAtrasListener() {
        return atrasListener;
    }

}
