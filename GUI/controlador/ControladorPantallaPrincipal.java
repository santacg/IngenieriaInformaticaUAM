package GUI.controlador;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import GUI.modelo.expofy.Expofy;
import GUI.vistas.*;

public class ControladorPantallaPrincipal {
    private Ventana frame;
    private PantallaPrincipal vista;
    private Expofy expofy;

    public ControladorPantallaPrincipal(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.vista = frame.getVistaPantallaPrincipal();
        this.expofy = expofy;
    }

    private ActionListener buscaListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Buscando...");
        }
    };

    private ActionListener gestorListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Gestor...");
            frame.mostrarPanel(frame.getLogInGestor());
        }
    };

    private ActionListener empleadoListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Empleado...");
            frame.mostrarPanel(frame.getLogInEmpleado());
        }
    };

    private ActionListener registrarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Registrando...");
        }
    };

    private ActionListener acceptListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame, "Aceptando...");

            String usuario = vista.getUsuario();
            String password = vista.getPassword();
            if (usuario.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(frame, "Debes introducir un nombre de usuario y una contraseña.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (expofy.loginCliente(usuario, password) == false) {
                JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(frame, "Bienvenido " + usuario + "!");

        }
    };

    public ActionListener getBuscaListener() {
        return buscaListener;
    }

    public ActionListener getGestorListener() {
        return gestorListener;
    }

    public ActionListener getEmpleadoListener() {
        return empleadoListener;
    }

    public ActionListener getRegistrarListener() {
        return registrarListener;
    }

    public ActionListener getAcceptListener() {
        return acceptListener;
    }

}
