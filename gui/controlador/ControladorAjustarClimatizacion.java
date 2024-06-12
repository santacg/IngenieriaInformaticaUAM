package gui.controlador;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.sala.Sala;
import gui.vistas.AjustarClimatizacion;
import gui.vistas.Ventana;

import java.awt.event.ActionEvent;

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
        this.frame.setCartaAjustarClimatizacion();
        this.centro = centro;
        this.vista = frame.getVistaAjustarClimatizacion();

        mostrarSalas();
    }

    /**
     * Método que muestra los sorteos en la vista.
     */
    public void mostrarSalas() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (Sala sala : centro.getSalas()) {
            if (sala.getClimatizador()) {
                data.add(new Object[] { sala.getNombre(), sala.getTemperatura(), sala.getHumedad() });
            }
            vista.addTablaSalas(data);
        }
    }

    /**
     * Devuelve un ActionListener que va a la panatalla anterior
     * 
     * @return ActionListener que va a la panatalla anterior
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.mostrarPanel(frame.getEmpleadoPrincipal());
        }
    };

    /**
     * Devuelve un ActionListener que ajusta la temperatura y humedad del centro de
     * exposición.
     * 
     * @return ActionListener que ajusta la temperatura y humedad del centro de
     *         exposición.
     */
    private ActionListener confirmarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = vista.getTablaSalas().getSelectedRow();
            if (selectedRow >= 0) {
                vista.getTablaSalas().clearSelection();
                String nombreSala = (String) vista.getTablaSalas().getValueAt(selectedRow, 0);
                Sala sala = centro.getSalaPorNombre(nombreSala);

                sala.setTemperatura(vista.getTemperatura());
                sala.setHumedad(vista.getHumedad());

                vista.removeAll();
                frame.setCartaAjustarClimatizacion();
                ControladorAjustarClimatizacion controladorAjustarClimatizacion = new ControladorAjustarClimatizacion(
                        frame,
                        centro);
                frame.setControladorAjustarClimatizacion(controladorAjustarClimatizacion);
                frame.mostrarPanel(frame.getAjustarClimatizacion());
                JOptionPane.showMessageDialog(frame, "Se ha cambiado la configuración de la sala.");

                // mostrarSalas();
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona una sala.");
            }
        }
    };

    /**
     * Devuelve un ChangeListener que sirve para conocer el valor del slider de
     * temepratura.
     * 
     * @return ChangeListener que sirve para conocer el valor del slider de
     *         temepratura.
     */
    private ChangeListener temperaturaListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            vista.updateTemperatura();
        }
    };

    /**
     * Devuelve un ChangeListener que sirve para conocer el valor del slider de
     * humedad.
     * 
     * @return ChangeListener que sirve para conocer el valor del slider de
     *         humedad.
     */
    private ChangeListener humedadListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            vista.updateHumedad();
        }
    };

    /**
     * Devuelve un ActionListener que va a la panatalla anterior
     * 
     * @return ActionListener que va a la panatalla anterior
     */
    public ActionListener getAtrasListener() {
        return atrasListener;
    }

    /**
     * Devuelve un ActionListener que ajusta la temperatura y humedad del centro de
     * exposición.
     * 
     * @return ActionListener que ajusta la temperatura y humedad del centro de
     *         exposición.
     */
    public ActionListener getConfirmarListener() {
        return confirmarListener;
    }

    /**
     * Devuelve un ChangeListener que sirve para conocer el valor del slider de
     * temepratura.
     * 
     * @return ChangeListener que sirve para conocer el valor del slider de
     *         temepratura
     */
    public ChangeListener getTemperaturaListener() {
        return temperaturaListener;
    }

    /**
     * Devuelve un ChangeListener que sirve para conocer el valor del slider de
     * humedad.
     * 
     * @return ChangeListener que sirve para conocer el valor del slider de
     *         humedad
     */
    public ChangeListener getHumedadListener() {
        return humedadListener;
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
