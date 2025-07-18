package gui.controlador;

import java.awt.event.*;
import javax.swing.*;

import gui.modelo.centroExposicion.Empleado;
import gui.modelo.expofy.Expofy;
import gui.vistas.PerfilEmpleado;
import gui.vistas.Ventana;

/**
 * Clase ControladorPerfilEmpleado.
 * Implementa el controlador de la vista PerfilEmpleado.
 */
public class ControladorPerfilEmpleado {
    private Ventana frame;
    private PerfilEmpleado vista;
    private Expofy expofy;
    private Empleado empleado;

    /**
     * Constructor de la clase ControladorPerfilEmpleado.
     * 
     * @param frame    Ventana
     * @param expofy   Expofy
     * @param empleado Empleado
     */
    public ControladorPerfilEmpleado(Ventana frame, Expofy expofy, Empleado empleado) {
        this.frame = frame;
        this.expofy = expofy;
        this.empleado = empleado;
        this.vista = frame.getVistaPerfilEmpleado();
        cargarDatosEmpleado();
    }

    /**
     * Carga los datos del empleado en la vista.
     */
    private void cargarDatosEmpleado() {
        vista.setDatosEmpleado(empleado.getNombre(), empleado.getNumSS(), empleado.getNumCuenta(),
                empleado.getDireccion());
    }

    /**
     * Listener para guardar cambios hechos al perfil del empleado.
     */
    private ActionListener guardarCambiosListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            String nombre = vista.getNombre().trim();
            String numSS = vista.getNumSS().trim();
            String numCuenta = vista.getNumCuenta().trim();
            String direccion = vista.getDireccion().trim();

            if (nombre.isEmpty() || numSS.isEmpty() || numCuenta.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Todos los campos deben estar completos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!numSS.matches("\\d+") || !numCuenta.matches("\\d+")) {
                JOptionPane.showMessageDialog(frame,
                        "El número de la seguridad social y el número de cuenta bancaria deben contener solo números.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            empleado.setNombre(nombre);
            empleado.setNumSS(numSS);
            empleado.setNumCuenta(numCuenta);
            empleado.setDireccion(direccion);

            JOptionPane.showMessageDialog(frame, "Cambios guardados correctamente.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            vista.actualizarCampos(nombre,numSS,numCuenta,direccion);
        }
    };

    /**
     * Listener para cancelar los cambios y volver a la pantalla anterior.
     */
    private ActionListener cancelarCambiosListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.limpiarCampos();
            frame.mostrarPanelPrevio();
        }
    };

    /**
     * Devuelve el listener para el botón de guardar cambios.
     * 
     * @return ActionListener
     */
    public ActionListener getGuardarCambiosListener() {
        return guardarCambiosListener;
    }

    /**
     * Devuelve el listener para el botón de cancelar.
     * 
     * @return ActionListener
     */
    public ActionListener getCancelarCambiosListener() {
        return cancelarCambiosListener;
    }

}
