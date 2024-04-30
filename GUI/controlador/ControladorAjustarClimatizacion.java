package GUI.controlador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.vistas.AjustarClimatizacion;
import GUI.vistas.Ventana;

/**
 * Clase ControladorAjustarClimatizacion
 * Controlador de la vista AjustarClimatizacion.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorAjustarClimatizacion {
    private Ventana frame;
    private AjustarClimatizacion vista;
    private CentroExposicion centro;

    /**
     * Constructor de la clase ControladorAjustarClimatizacion.
     * 
     * @param frame  Ventana en la que se muestra la vista.
     * @param centro Centro de exposición que se va a modificar.
     */
    public ControladorAjustarClimatizacion(Ventana frame, CentroExposicion centro) {
        this.frame = frame;
        this.centro = centro;
        this.vista = frame.getVistaAjustarClimatizacion();

        mostrarTemperaturaHumedad();
    }

    /**
     * Muestra la temperatura y humedad del centro de exposición.
     */
    public void mostrarTemperaturaHumedad() {
        vista.addTemperaturaHumedad(centro);
    }

    /**
     * Devuelve un ActionListener que ajusta la temperatura y humedad del centro de
     * exposición.
     * 
     * @return ActionListener que ajusta la temperatura y humedad del centro de
     *         exposición.
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.mostrarPanelPrevio();
        }
    };

    /**
     * Devuelve un ActionListener que ajusta la temperatura y humedad del centro de
     * exposición.
     * 
     * @return ActionListener que ajusta la temperatura y humedad del centro de
     *         exposición.
     */
    public ActionListener getAtrasListener() {
        return atrasListener;
    }

    /**
     * Devuelve el centro de exposición que se está modificando.
     * 
     * @return Centro de exposición que se está modificando.
     */
    public CentroExposicion getCentro() {
        return centro;
    }
}
