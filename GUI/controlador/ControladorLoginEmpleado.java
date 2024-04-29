package GUI.controlador;

import java.awt.event.*;
import javax.swing.*;

import GUI.modelo.expofy.Expofy;
import GUI.modelo.centroExposicion.*;
import GUI.vistas.*;

public class ControladorLoginEmpleado {
    private Ventana frame;
    private LoginEmpleado vista;
    private Expofy expofy;

    public ControladorLoginEmpleado(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.expofy = expofy;
        this.vista = frame.getVistaLoginEmpleado();
    }

    private ActionListener acceptListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String usuario = vista.getUsuario();
            String password = vista.getPassword();
            if (usuario.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(frame, "Debes introducir un NIF y una contraseña.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (CentroExposicion centro : expofy.getCentrosExposicion()) {
                if (centro.loginEmpleado(usuario, password) == true) {
                    JOptionPane.showMessageDialog(frame, "Bienvenido " + usuario + "!");
                    ControladorEmpleado controladorEmpleado = new ControladorEmpleado(frame, centro);
                    frame.setControladorEmpleado(controladorEmpleado);
                    vista.update();
                    frame.mostrarPanel(frame.getEmpleadoPrincipal());
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "NIF o contraseña incorrectos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    };

    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.update();
            frame.mostrarPanelPrevio();
        }
    };

    public ActionListener getAcceptListener() {
        return acceptListener;
    }

    public ActionListener getAtrasListener() {
        return atrasListener;
    }

}
