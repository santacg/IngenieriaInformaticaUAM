package gui.controlador;

import java.awt.event.*;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.vistas.DescuentoFormulario;
import gui.vistas.GestorPrincipal;

public class ControladorDescuentoFormulario {
    private DescuentoFormulario vista;
    private GestorPrincipal frame;
    private CentroExposicion centroExposicion;
    private String accion;

    /**
     * Constructor de la clase ControladorSalaFormulario
     * 
     * @param frame            GestorPrincipal
     * @param centroExposicion CentroExposicion
     * @param accion           String
     */
    public ControladorDescuentoFormulario(GestorPrincipal frame, CentroExposicion centroExposicion) {
        this.frame = frame;
        this.vista = frame.getVistaDescuentoFormulario();
        this.centroExposicion = centroExposicion;
    }

    /**
     * Método que inicializa el listener del botón aceptar
     */
    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
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
