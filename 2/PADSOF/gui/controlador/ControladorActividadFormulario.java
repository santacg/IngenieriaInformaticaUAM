package gui.controlador;

import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.*;
import gui.modelo.sala.Sala;
import gui.modelo.utils.ExcepcionMensaje;
import gui.vistas.GestorPrincipal;
import gui.vistas.ActividadFormulario;

/**
 * Controlador de la vista ActividadFormulario
 * 
 * @author Carlos Garcia Santa, Joaquin Abad Diaz, Eduardo Junoy Ortega
 */
public class ControladorActividadFormulario {
    private ActividadFormulario vista;
    private GestorPrincipal frame;
    private CentroExposicion centroExposicion;

    /**
     * Constructor de la clase ControladorActividadFormulario
     * 
     * @param frame            GestorPrincipal
     * @param centroExposicion CentroExposicion
     */
    public ControladorActividadFormulario(GestorPrincipal frame, CentroExposicion centroExposicion) {
        this.frame = frame;
        this.vista = frame.getVistaActividadFormulario();
        this.centroExposicion = centroExposicion;
        mostrarExpo();
    }

    /**
     * Método que muestra las salas
     */
    public void mostrarExpo() {
        vista.mostrarSalas(centroExposicion);
    }

    /**
     * Método que inicializa el listener del botón aceptar
     */
    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            TipoActividad tipoActividad = vista.getTipoActividad();

            LocalDate fechaActividad = vista.getFechaActividad();
            LocalTime horaActividad = vista.getHoraActividad();
            if (fechaActividad == null || horaActividad == null) {
                JOptionPane.showMessageDialog(vista,
                        "Debes rellenar la fecha y hora de la Actividad o introducirlas correctamente.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer maxParticipantes = vista.getActividadMaxParticipantes();
            if (maxParticipantes == null || maxParticipantes <= 0) {
                JOptionPane.showMessageDialog(vista,
                        "Debes rellenar el número de participantes o introducir un número de participantes correctamente.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (vista.getNombreActividad().equals("") || vista.getDescripcion().equals("")) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar todos los campos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (vista.getSelectedSala() == null) {
                JOptionPane.showMessageDialog(vista, "Debes seleccionar una sala.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Sala sala = centroExposicion.getSalaPorNombre(vista.getSelectedSala());

            try {
                centroExposicion.addActividad(vista.getNombreActividad(), tipoActividad, vista.getDescripcion(),
                    maxParticipantes, fechaActividad, horaActividad, sala);
            } catch (ExcepcionMensaje e1) {
                JOptionPane.showMessageDialog(vista, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(vista, "Actividad añadida correctamente.", "Actividad añadida",
                    JOptionPane.INFORMATION_MESSAGE);

            frame.actualizarTablaActividades(centroExposicion);
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