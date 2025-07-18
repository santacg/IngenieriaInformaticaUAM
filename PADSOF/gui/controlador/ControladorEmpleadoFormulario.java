package gui.controlador;

import java.awt.event.*;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.centroExposicion.Empleado;
import gui.vistas.*;

/**
 * Clase ControladorObraFormulario
 * Implementa el controlador de la vista ObraFormulario.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorEmpleadoFormulario {
    private EmpleadoFormulario vista;
    private GestorPrincipal frame;
    private CentroExposicion centroExposicion;

    /**
     * Constructor de la clase ControladorObraFormulario
     * 
     * @param frame            GestorPrincipal
     * @param centroExposicion CentroExposicion
     */
    public ControladorEmpleadoFormulario(GestorPrincipal frame, CentroExposicion centroExposicion) {
        this.frame = frame;
        this.vista = frame.getVistaEmpleadoFormulario();
        this.centroExposicion = centroExposicion;
    }

    /**
     * Método que inicializa el listener del botón de guardar.
     */
    private ActionListener guardarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (vista.getEmpleadoNIF().equals("") || vista.getEmpleadoNombre().equals("")
                    || vista.getEmpleadoDireccion().equals("") || vista.getEmpleadoNumSS().equals("")) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar todos los campos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Empleado empleado = new Empleado(vista.getEmpleadoNIF(), vista.getEmpleadoNombre(),
                    vista.getEmpleadoNumSS(), vista.getEmpleadoNumCuenta(), vista.getEmpleadoDireccion(),
                    vista.getPermisoVenta(), vista.getPermisoControl(), vista.getPermisoMensajes(), vista.getPermisoActividades());
            
            if (centroExposicion.addEmpleado(empleado) == false) {
                JOptionPane.showMessageDialog(vista, "Ya existe un empleado con ese NIF.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Object[] empleadoData = new Object[] { empleado.getNIF(), empleado.getNombre(), empleado.getNumSS(),
                    empleado.getNumCuenta(), empleado.getDireccion(), empleado.getPermisoVenta(),
                    empleado.getPermisoControl(), empleado.getPermisoMensajes() };

            frame.añadirFilaTablaEmpleados(empleadoData);
            JOptionPane.showMessageDialog(vista, "Empleado añadido correctamente.");
            vista.dispose();

        }
    };

    /**
     * Método que inicializa el listener del botón de cancelar.
     */
    private ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
        }
    };

    /**
     * Método que devuelve el listener del botón para guardar.
     * 
     * @return ActionListener Listener del botón de guardar.
     */
    public ActionListener getGuardarListener() {
        return guardarListener;
    }

    /**
     * Método que devuelve el listener del botón para cancelar.
     * 
     * @return ActionListener Listener del botón de cancelar.
     */
    public ActionListener getCancelarListener() {
        return cancelarListener;
    }
}
