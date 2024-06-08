package gui.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.*;
import gui.modelo.expofy.*;
import gui.modelo.exposicion.Exposicion;
import gui.vistas.VentaEntradas;
import gui.vistas.Ventana;

/**
 * Clase ControladorCliente.
 * Este controlador se encarga de gestionar la vista del cliente registrado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorVentaEntradas {

    private Ventana frame;
    private VentaEntradas vista;
    private CentroExposicion centro;
    private Empleado empleado;
    private Expofy expofy;

    /**
     * Constructor de la clase ControladorCliente.
     * 
     * @param frame   Ventana principal de la aplicación.
     * @param expofy  Instancia de la apslicación.
     * @param centro  centro del empleado.
     * @param empleado empleado activo.
     */
    public ControladorVentaEntradas(Ventana frame, Expofy expofy, CentroExposicion centro, Empleado empleado) {
        this.frame = frame;
        this.centro = centro;
        this.empleado = empleado;
        this.expofy = expofy;
        this.vista = frame.getVistaVentaEntradas();

        mostrarExposiciones();
    }

    /**
     * Método que muestra las exposiciones en la vista.
     */
    public void mostrarExposiciones() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (Exposicion exposicion : centro.getExposiciones()) {
            data.add(new Object[] {
                    exposicion.getNombre(),
                    exposicion.getDescripcion(),
                    exposicion.getFechaInicio(),
                    exposicion.getFechaFin(),
                    exposicion.getPrecio(),
                    centro.getNombre(),
                    centro.getLocalizacion()
            });
        }
        vista.addTablaExposiciones(data);
    }

    /**
     * Método que devuelve el ActionListener para Venta de una entrada.
     * 
     * @return ActionListener para Venta de una entrada.
     */
    public ActionListener getVentaListener() {
        return VentaListener;
    }

    /**
     * Método que devuelve el ActionListener para cerrar la sesión del cliente.
     * 
     * @return ActionListener para cerrar la sesión del cliente.
     */
    public ActionListener getCerrarSesionListener() {
        return cerrarSesionListener;
    }

    /**
     * Método que devuelve el listener del botón de atrás.
     * 
     * @return ActionListener
     */
    public ActionListener getAtrasListener() {
        return atrasListener;
    }

    /**
     * Método que inicializa el listener del botón de atrás.
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.mostrarPanelPrevio();
        }
    };

    /**
     * ActionListener para Venta una entrada.
     */
    private ActionListener VentaListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = vista.getTablaExposiciones().getSelectedRow();
            if (selectedRow >= 0) {
                vista.getTablaExposiciones().clearSelection();
                String nombreExposicion = (String) vista.getTablaExposiciones().getValueAt(selectedRow, 0);
                JOptionPane.showMessageDialog(frame,
                        "Mostrando formulario de venta de entradas"
                                + nombreExposicion);
                Exposicion exposicion = expofy.getExposicionPorNombre(nombreExposicion);
                ControladorVentaFormulario controladorVentaFormulario = new ControladorVentaFormulario(vista, centro,
                        exposicion);
                vista.setVentaFormularioControlador(controladorVentaFormulario);

            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona una exposición.");
            }
        }
    };

    /**
     * ActionListener para cerrar la sesión del cliente.
     */
    private ActionListener cerrarSesionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // centro.logOut(empleado);
            ControladorPantallaPrincipal controlador = new ControladorPantallaPrincipal(frame, expofy);
            frame.setControladorPantallaPrincipal(controlador);
            JOptionPane.showMessageDialog(frame, "Se ha cerrado la sesión.");
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

}