package gui.controlador;

import java.awt.event.*;
import javax.swing.*;

import gui.modelo.centroExposicion.*;
import gui.modelo.expofy.Expofy;
import gui.vistas.*;

/**
 * Clase ControladorLoginEmpleado.
 * Implementa el controlador de la vista LoginEmpleado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorLoginEmpleado {
    private Ventana frame;
    private LoginEmpleado vista;
    private Expofy expofy;

    /**
     * Constructor de la clase ControladorLoginEmpleado.
     * 
     * @param frame  Ventana
     * @param expofy Expofy
     */
    public ControladorLoginEmpleado(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.expofy = expofy;
        this.vista = frame.getVistaLoginEmpleado();
    }

    /**
     * Método que inicializa el listener del botón de aceptar.
     */
    private ActionListener acceptListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String usuario = vista.getUsuario();
            String password = vista.getPassword();
            if (usuario.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(frame,
                        "Debes introducir el número de la Seguridad Social y una contraseña.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (CentroExposicion centro : expofy.getCentrosExposicion()) {
                if (centro.loginEmpleado(usuario, password) == true) {
                    JOptionPane.showMessageDialog(frame, "Bienvenido " + usuario + "!");
                    ControladorEmpleado controladorEmpleado = new ControladorEmpleado(frame, expofy, centro, usuario);
                    frame.setControladorEmpleado(controladorEmpleado);
                    vista.update();
                    frame.mostrarPanel(frame.getEmpleadoPrincipal());
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Número de la Seguridad Social o contraseña incorrectos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    };

    /**
     * Método que inicializa el listener del botón de atrás.
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.update();
            ControladorPantallaPrincipal controlador = new ControladorPantallaPrincipal(frame, expofy);
            frame.setControladorPantallaPrincipal(controlador);
            frame.mostrarPanelPrevio();
        }
    };

    /**
     * Método que devuelve el listener del botón de aceptar.
     * 
     * @return ActionListener
     */
    public ActionListener getAcceptListener() {
        return acceptListener;
    }

    /**
     * Método que devuelve el listener del botón de atrás.
     * 
     * @return ActionListener
     */
    public ActionListener getAtrasListener() {
        return atrasListener;
    }

}
