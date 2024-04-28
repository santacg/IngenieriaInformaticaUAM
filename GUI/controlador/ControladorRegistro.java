package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import GUI.modelo.expofy.Expofy;
import GUI.vistas.RegistroUsuario;
import GUI.vistas.Ventana;

public class ControladorRegistro {
    private Ventana frame;
    private RegistroUsuario vistaRegistro;
    private Expofy expofy;

    public ControladorRegistro(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.expofy = expofy;
        this.vistaRegistro = frame.getVistaRegistro();
    }

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
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

    public ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vistaRegistro.update();
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

    public ActionListener getRegistrarListener() {
        return registrarListener;
    }

    public ActionListener getCancelarListener() {
        return cancelarListener;
    }
}
