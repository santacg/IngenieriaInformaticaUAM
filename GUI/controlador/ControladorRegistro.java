package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.modelo.expofy.Expofy;
import GUI.vistas.RegistroUsuario;
import GUI.vistas.Ventana;

/**
 * Clase ControladorRegistro.
 * Actúa como controlador de la vista RegistroUsuario.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorRegistro {
    private Ventana frame;
    private RegistroUsuario vistaRegistro;
    private Expofy expofy;

    /**
     * Constructor de la clase ControladorRegistro.
     * 
     * @param frame  Ventana principal de la aplicación.
     * @param expofy Objeto de la clase Expofy.
     */
    public ControladorRegistro(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.expofy = expofy;
        this.vistaRegistro = frame.getVistaRegistro();
    }

    /**
     * Listener del botón de registro.
     */
    private ActionListener registrarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String usuario = vistaRegistro.getUsuario();
            String password = vistaRegistro.getPassword();
            if (usuario.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(frame, "Debes introducir un nombre de usuario y una contraseña.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Boolean publicidad = vistaRegistro.getPublicidad();
            if (expofy.registrarCliente(usuario, password, publicidad) == false) {
                JOptionPane.showMessageDialog(frame, "Usuario ya registrado.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(frame, "Usuario registrado correctamente.", "Registro",
                    JOptionPane.INFORMATION_MESSAGE);
            vistaRegistro.update();
            ControladorPantallaPrincipal controlador = new ControladorPantallaPrincipal(frame, expofy);
            frame.setControladorPantallaPrincipal(controlador);
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

    /**
     * Listener del botón de cancelar.
     */
    public ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vistaRegistro.update();
            ControladorPantallaPrincipal controlador = new ControladorPantallaPrincipal(frame, expofy);
            frame.setControladorPantallaPrincipal(controlador);
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

    /**
     * Getter del listener del botón de registro.
     * 
     * @return ActionListener Listener del botón de registro.
     */
    public ActionListener getRegistrarListener() {
        return registrarListener;
    }

    /**
     * Getter del listener del botón de cancelar.
     * 
     * @return ActionListener Listener del botón de cancelar.
     */
    public ActionListener getCancelarListener() {
        return cancelarListener;
    }
}
