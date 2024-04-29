package GUI.controlador;

import GUI.modelo.expofy.*;
import GUI.vistas.ClientePrincipal;
import GUI.vistas.Ventana;

import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;

import javax.swing.JOptionPane;

public class ControladorCliente {

    private Ventana frame;
    private ClientePrincipal vista;
    private Expofy expofy;
    private ClienteRegistrado cliente;

    public ControladorCliente(Ventana frame, Expofy expofy, ClienteRegistrado cliente) {
        this.frame = frame;
        this.cliente = cliente;
        this.expofy = expofy;
        this.vista = frame.getVistaClientePrincipal();

        mostrarExposiciones();
        mostrarPerfil();
    }

    public void mostrarExposiciones() {
        vista.addTablaExposiciones(expofy);
    }

    public void mostrarPerfil() {
        vista.addPerfil(cliente);
    }

    public ActionListener getComprarListener() {
        return comprarListener;
    }

    public ActionListener getActualizarDatos() {
        return actualizarDatosListener;
    }

    private ActionListener comprarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = vista.getTablaExposiciones().getSelectedRow();
            if (selectedRow >= 0) {
                String nombreExposicion = (String) vista.getTablaExposiciones().getValueAt(selectedRow, 0);
                JOptionPane.showMessageDialog(frame, "Mostrando horarios para la exposición: " + nombreExposicion);
                // exposicion = expofy.getExposicionPorNombre(nombreExposicion);
                // ControladorHorario controladorHorario = new ControladorHorario(exposicion);
                // frame.setHorarioControlador(controladorHorario);
                // vista.update();
                // frame.mostrarPanel(frame.getHorario());
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona una exposición.");
            }
        }
    };

    private ActionListener actualizarDatosListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String mensaje = "";
            if (vista.getCheckBoxPublicidad().isSelected()) {
                cliente.setPublicidad(true);
            } else {
                cliente.setPublicidad(true);
            }

            String contrasena = vista.getFieldContrasena();
            String contrasenaCofirmada = vista.getFieldContrasenaConfirmar();
            if (contrasena.equals("") || contrasenaCofirmada.equals("")) {
                
            }
        }
    };

}