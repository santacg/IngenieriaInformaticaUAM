package gui.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.exposicion.*;
import gui.vistas.*;

/**
 * Clase ControladorVentaFormulario
 * Actúa como controlador de la vista VentaFormulario. Se encarga de gestionar
 * los eventos de la vista y de realizar las operaciones necesarias para
 * completar la compra de entradas.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorVentaFormulario {

    private CentroExposicion centro;
    private Exposicion exposicion;
    private VentaEntradas frame;
    private VentaFormulario vista;
    private ConfirmarVenta vistaConfirmarVenta;
    private LocalDate fecha;
    private Integer hora = 9, nEntradas = 1;
    private Integer diaExpo = 1, mesExpo = 1, anioExpo = 1990;
    private JComboBox<Integer> nEntradasCombo;
    private JComboBox<String> horaCombo;
    private double precioFinal;

    /**
     * Constructor de la clase ControladorVentaFormulario
     * 
     * @param frame      VentaEntradas
     * @param centro     CentroExposicion
     * @param exposicion Exposicion
     */
    public ControladorVentaFormulario(VentaEntradas frame, CentroExposicion centro, Exposicion exposicion) {

        this.exposicion = exposicion;
        this.frame = frame;
        this.centro = centro;
        this.vista = frame.getVistaVentaFormulario(exposicion.getNombre(), exposicion.getPrecio());

        horaCombo = vista.getHora();

        nEntradasCombo = vista.getNentradas();

    }

    /**
     * Método que devuelve el ActionListener del botón de siguiente
     * 
     * @return sigueinteListener
     */
    public ActionListener getSiguienteListener() {
        return siguienteListener;
    }

    /**
     * Método que inicializa el ActionListener del botón de siguiente
     */
    private ActionListener siguienteListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String exposicionNombre = vista.getExposicionNombre();
            String precioPorEntrada = vista.getPrecio();

            precioFinal = (exposicion.getPrecio() * (double) nEntradas);

            fecha = LocalDate.now();
            if (fecha.isBefore(LocalDate.now()) || fecha.isBefore(exposicion.getFechaInicio())
                    || fecha.isAfter(exposicion.getFechaFin())) {
                JOptionPane.showMessageDialog(frame, "La fecha de la exposición no es válida.");
                return;
            }

            if (nEntradas <= 0 || nEntradas >= exposicion.getHora(fecha, hora).getnEntradasDisp()) {
                JOptionPane.showMessageDialog(frame, "No hay ese número de entradas disponibles para la exposición.");
                return;
            }

            vistaConfirmarVenta = new ConfirmarVenta(exposicionNombre, String.valueOf(nEntradas),
                    fecha.toString(), String.valueOf(hora) + ":00", String.valueOf(precioFinal));
            vistaConfirmarVenta.setControlador(confirmarListener, atrasListener);

        }
    };

    /**
     * Método que devuelve el ActionListener del botón de cancelar
     * 
     * @return cancelarListener
     */
    public ActionListener getCancelarListener() {
        return cancelarListener;
    }

    /**
     * Método que inicializa el ActionListener del botón de cancelar
     */
    private ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
        }
    };

    /**
     * Mtodo que inicializa el ActionListener del botón de confirmar
     */
    private ActionListener confirmarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (centro.venderEntrada(exposicion, exposicion.getHora(fecha, hora), nEntradas)) {
                JOptionPane.showMessageDialog(vista,
                        "La compra se ha realizado con éxito, encontrará el pdf con su entrada en la carpeta resources");
                vistaConfirmarVenta.dispose();
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "Ha habido un error en la compra", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    };

    /**
     * Método que inicializa el ActionListener del botón de atras
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vistaConfirmarVenta.dispose();
        }
    };

    /**
     * Método que inicializa el ActionListener del botón del número de entradas
     */
    private ActionListener nEntradasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            nEntradas = (Integer) nEntradasCombo.getSelectedItem();
        }
    };

    /**
     * Método que devuelve el ActionListener del botón del número de entradas
     * 
     * @return nEntradasListener
     */
    public ActionListener getNentradasListener() {
        return nEntradasListener;
    }

    /**
     * Método que inicializa el ActionListener del botón de la hora
     */
    private ActionListener horaListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            hora = horaCombo.getSelectedIndex();
            hora += 9;
        }
    };

    /**
     * Método que devuelve el ActionListener del botón de la hora
     * 
     * @return horaListener
     */
    public ActionListener getHoraListener() {
        return horaListener;
    }

}