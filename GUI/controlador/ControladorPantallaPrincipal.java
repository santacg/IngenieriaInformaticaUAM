package GUI.controlador;

import java.awt.event.*;
import javax.swing.*;

import GUI.modelo.expofy.*;
import GUI.vistas.*;

/**
 * Clase ControladorPantallaPrincipal
 * Implementa el controlador de la pantalla principal de la aplicación.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorPantallaPrincipal {
    private Ventana frame;
    private PantallaPrincipal vista;
    private Expofy expofy;

    /**
     * Constructor de la clase ControladorPantallaPrincipal
     * 
     * @param frame  Ventana de la aplicación
     * @param expofy Modelo de la aplicación
     */
    public ControladorPantallaPrincipal(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.expofy = expofy;
        this.vista = frame.getVistaPantallaPrincipal();
    }

    /**
     * Inicializa el listener de búsqueda de la pantalla principal
     */
    private ActionListener buscaListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.update();
            frame.mostrarPanel(frame.getExposiciones());
        }
    };

    /**
     * Inicializa el listener de gestor de la pantalla principal
     */
    private ActionListener gestorListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.update();
            frame.mostrarPanel(frame.getLogInGestor());
        }
    };

    /**
     * Inicializa el listener de empleado de la pantalla principal
     */
    private ActionListener empleadoListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.update();
            frame.mostrarPanel(frame.getLogInEmpleado());
        }
    };

    /**
     * Inicializa el listener de registro de la pantalla principal
     */
    private ActionListener registrarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.update();
            frame.mostrarPanel(frame.getRegistro());
        }
    };

    /**
     * Inicializa el listener de aceptar de la pantalla principal
     */
    private ActionListener acceptListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
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
            ClienteRegistrado clienteRegistrado = expofy.getClienteRegistrado(usuario);
            ControladorCliente controladorCliente = new ControladorCliente(frame, expofy, clienteRegistrado);
            frame.setControladorCliente(controladorCliente);
            JOptionPane.showMessageDialog(frame, "Bienvenido " + usuario + "!");
            vista.update();
            frame.mostrarPanel(frame.getClientePrincipal());
        }
    };

    /**
     * Devuelve el listener de búsqueda de la pantalla principal
     * 
     * @return ActionListener Listener de búsqueda
     */
    public ActionListener getBuscaListener() {
        return buscaListener;
    }

    /**
     * Devuelve el listener de gestor de la pantalla principal
     * 
     * @return ActionListener Listener de gestor
     */
    public ActionListener getGestorListener() {
        return gestorListener;
    }

    /**
     * Devuelve el listener de empleado de la pantalla principal
     * 
     * @return ActionListener Listener de empleado
     */
    public ActionListener getEmpleadoListener() {
        return empleadoListener;
    }

    /**
     * Devuelve el listener de registro de la pantalla principal
     * 
     * @return ActionListener Listener de registro
     */
    public ActionListener getRegistrarListener() {
        return registrarListener;
    }

    /**
     * Devuelve el listener de aceptar de la pantalla principal
     * 
     * @return ActionListener Listener de aceptar
     */
    public ActionListener getAcceptListener() {
        return acceptListener;
    }

}
