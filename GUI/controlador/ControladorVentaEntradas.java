package GUI.controlador;

import GUI.modelo.centroExposicion.*;
import GUI.modelo.expofy.*;
import GUI.modelo.exposicion.Exposicion;
import GUI.vistas.ClientePrincipal;
import GUI.vistas.EmpleadoPrincipal;
import GUI.vistas.VentaEntradas;
import GUI.vistas.Ventana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Clase ControladorCliente.
 * Este controlador se encarga de gestionar la vista del cliente registrado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorVentaEntradas {

    private Ventana frame;
    private VentaEntradas vista;
    private Expofy expofy;
    private Empleado empleado;

    /**
     * Constructor de la clase ControladorCliente.
     * 
     * @param frame   Ventana principal de la aplicación.
     * @param expofy  Instancia de la apslicación.
     * @param cliente Cliente registrado.
     */
    public ControladorVentaEntradas(Ventana frame, Expofy expofy, Empleado empleado) {
        this.frame = frame;
        this.expofy = expofy;
        this.empleado = empleado;
        this.vista = frame.getVistaVentaEntradas();

        mostrarExposiciones();
    }

    /**
     * Método que muestra las exposiciones en la vista.
     */
    public void mostrarExposiciones() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (CentroExposicion centro : expofy.getCentrosExposicion()) {
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
     * ActionListener para Venta una entrada.
     */
    private ActionListener VentaListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = vista.getTablaExposiciones().getSelectedRow();
            if (selectedRow >= 0) {
                vista.getTablaExposiciones().clearSelection();
                String nombreExposicion = (String) vista.getTablaExposiciones().getValueAt(selectedRow, 0);
                JOptionPane.showMessageDialog(frame,
                        "La venta de entradas no se ha podido implementar por falta de tiempo, inicie sesión como cliente para comprar una entrada, funcionalidad que sí está correctamente implementada"
                                + nombreExposicion);
                Exposicion exposicion = expofy.getExposicionPorNombre(nombreExposicion);
                // ControladorCompraFormulario controladorCompraFormulario = new
                // ControladorCompraFormulario(vista, expofy,
                // exposicion, cliente);
                // vista.setCompraFormularioControlador(controladorCompraFormulario);

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
            // expofy.logOut(empleado);
            JOptionPane.showMessageDialog(frame, "Se ha cerrado la sesión.");
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

}