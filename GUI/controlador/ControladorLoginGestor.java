package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.expofy.Expofy;
import GUI.vistas.LoginGestor;
import GUI.vistas.Ventana;

public class ControladorLoginGestor {
    private Ventana frame;
    private LoginGestor vista;
    private Expofy expofy;
    private GestorInterfaz gestorInterfaz;

    public ControladorLoginGestor(Ventana frame, Expofy expofy, GestorInterfaz gestorInterfaz) {
        this.frame = frame;
        this.expofy = expofy;
        this.vista = frame.getVistaLogInGestor();
        this.gestorInterfaz = gestorInterfaz;
    }

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
                    gestorInterfaz.onGestorLogin(centro);
                    vista.update();
                    frame.mostrarPanel(frame.getGestorPrincipal());
                    return;
                }
            }

            JOptionPane.showMessageDialog(frame, "Contraseña incorrecta.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    };

    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.update();
            frame.mostrarPanelPrevio();
        }
    };

    public ActionListener getAceptarListener() {
        return aceptarListener;
    }

    public ActionListener getAtrasListener() {
        return atrasListener;
    }

}
