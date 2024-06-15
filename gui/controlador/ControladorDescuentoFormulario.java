package gui.controlador;

import java.awt.event.*;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.exposicion.Exposicion;
import gui.vistas.DescuentoFormulario;
import gui.vistas.GestorPrincipal;

public class ControladorDescuentoFormulario {
    private DescuentoFormulario vista;
    private GestorPrincipal frame;
    private CentroExposicion centroExposicion;

    /**
     * Constructor de la clase ControladorDescuentoFormulario
     * 
     * @param frame            GestorPrincipal
     * @param centroExposicion CentroExposicion
     * @param accion           String
     */
    public ControladorDescuentoFormulario(GestorPrincipal frame, CentroExposicion centroExposicion) {
        this.frame = frame;
        this.vista = frame.getVistaDescuentoFormulario();
        this.centroExposicion = centroExposicion;
        mostrarExpo();
    }

    /**
     * Método que muestra las exposiciones
     */
    public void mostrarExpo() {
        vista.mostrarExposiciones(centroExposicion);
    }

    /**
     * Método que inicializa el listener del botón aceptar
     */
    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String tipoDescuento = vista.getTipoDescuento();

            Double descuentoPorcentaje = vista.getDescuento();
            if (descuentoPorcentaje == null || descuentoPorcentaje <= 0) {
                JOptionPane.showMessageDialog(vista,
                        "Debes rellenar el porcentaje de descuento o introducir un porcentaje de descuento correcto.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer cantidad = vista.getCantidad();
            if (cantidad == null || cantidad <= 0) {
                JOptionPane.showMessageDialog(vista,
                        "Debes rellenar la cantidad de descuento o introducir una cantidad de descuento correcta.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Exposicion exposicion = centroExposicion.getExposicionPorNombre(vista.getSelectedExposicion());
            if (exposicion.getDescuento() != null) {
                int dialogResult = JOptionPane.showConfirmDialog(vista,
                        "Ya existe un descuento para esta exposición. ¿Deseas sobrescribirlo?", "Advertencia",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.NO_OPTION) {
                    vista.dispose();
                    return;
                }
            }

            if (exposicion.getDescuento() != null) {
                JOptionPane.showMessageDialog(vista, "Ya existe un descuento para esta exposición.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            switch (tipoDescuento) {
                case "Descuento por dia":
                    exposicion.configurarDescuentoDia(descuentoPorcentaje, cantidad);
                    break;
                case "Descuento por mes":
                    exposicion.configurarDescuentoMes(descuentoPorcentaje, cantidad);
                    break;
            }

            frame.actualizarTablaDescuentos(centroExposicion);
            vista.dispose();
        }
    };

    /**
     * Método que inicializa el listener del botón cancelar
     */
    private ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
        }
    };

    /**
     * Método que devuelve el listener del botón aceptar
     * 
     * @return ActionListener
     */
    public ActionListener getAceptarListener() {
        return aceptarListener;
    }

    /**
     * Método que devuelve el listener del botón cancelar
     * 
     * @return ActionListener
     */
    public ActionListener getCancelarListener() {
        return cancelarListener;
    }

}
