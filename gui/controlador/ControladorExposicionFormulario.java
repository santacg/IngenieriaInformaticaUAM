package gui.controlador;

import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.exposicion.EstadoExposicion;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.TipoExpo;
import gui.modelo.utils.ExcepcionMensaje;
import gui.vistas.*;

/**
 * Clase ControladorExposicionFormulario.
 * Crea un controlador para la vista ExposicionFormulario, permite aceptar o
 * cancelar la acción realizada en la vista y permite cancelar una exposición o
 * prorrogarla.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorExposicionFormulario {
    private ExposicionFormulario vista;
    private GestorPrincipal frame;
    private CentroExposicion centroExposicion;
    private Exposicion exposicion;
    private String accion;

    /**
     * Constructor de la clase ControladorExposicionFormulario.
     * 
     * @param frame            GestorPrincipal
     * @param centroExposicion CentroExposicion
     * @param exposicion       Exposicion
     * @param accion           String
     */
    public ControladorExposicionFormulario(GestorPrincipal frame, CentroExposicion centroExposicion,
            Exposicion exposicion, String accion) {
        this.frame = frame;
        this.vista = frame.getVistaExposicionFormulario(accion);
        this.centroExposicion = centroExposicion;
        this.exposicion = exposicion;
        this.accion = accion;

    }

    public ControladorExposicionFormulario(GestorPrincipal frame, CentroExposicion centroExposicion) {
        this.frame = frame;
        this.vista = frame.getVistaExposicionFormulario("Agregar Exposicion");
        this.accion = "Agregar Exposicion";
        this.centroExposicion = centroExposicion;
    }

    /**
     * Método que inicializa la vista y añade los listeners para el botón aceptar
     */
    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            switch (accion) {
                case "Agregar Exposicion":

                    LocalDate fechaInicio = vista.getFechaInicio();
                    LocalDate fechaFin = vista.getFechaFin();
                    Double precio = vista.getPrecio();

                    if (vista.getNombre().equals("") || fechaInicio == null
                            || vista.getDescripcion().equals("")
                            || precio == null || precio <= 0) {
                        JOptionPane.showMessageDialog(vista,
                                "Debes rellenar todos los campos o hacerlo de forma correcta.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (vista.getTipoSeleccionado().equals(TipoExpo.TEMPORAL) && fechaFin == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha de fin.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (vista.getTipoSeleccionado().equals(TipoExpo.TEMPORAL)) {
                        if (vista.getFechaInicio().isAfter(fechaFin)) {
                            JOptionPane.showMessageDialog(vista,
                                    "La fecha de inicio no puede ser posterior a la fecha de fin.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    if (fechaFin != null) {
                        if (fechaInicio.isAfter(fechaFin) || fechaInicio.isEqual(fechaFin)) {
                            JOptionPane.showMessageDialog(vista,
                                    "La fecha de inicio no puede ser posterior a la fecha de fin.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (fechaFin.isBefore(LocalDate.now()) || fechaFin.isEqual(LocalDate.now())) {
                            JOptionPane.showMessageDialog(vista, "La fecha de fin no puede ser anterior a la fecha actual",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    if (fechaInicio.isBefore(LocalDate.now()) || fechaInicio.isEqual(LocalDate.now())) {
                        JOptionPane.showMessageDialog(vista, "La fecha de inicio no puede ser anterior a la fecha actual",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Exposicion expoNueva = new Exposicion(vista.getNombre(), vista.getFechaInicio(),
                            vista.getFechaFin(), vista.getDescripcion(), vista.getTipoSeleccionado(),
                            vista.getPrecio());

                    if (centroExposicion.addExposicion(expoNueva) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede añadir la exposición.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición añadida correctamente.", "Exposición añadida",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Cancelar Exposicion":
                    
                    fechaInicio = vista.getFechaInicio();

                    if (fechaInicio == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha de cancelación o hacerlo con el formato correcto.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        exposicion.expoCancelar(fechaInicio);
                    } catch (ExcepcionMensaje e1) {
                        JOptionPane.showMessageDialog(vista, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    frame.actualizarTablaSorteos(centroExposicion);
                    JOptionPane.showMessageDialog(vista, "Exposición cancelada correctamente.", "Exposición cancelada",
                            JOptionPane.INFORMATION_MESSAGE);

                    break;
                case "Prorrogar Exposicion":

                    fechaFin = vista.getFechaFin();

                    if (fechaFin == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha de fin.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        exposicion.expoProrrogar(fechaFin);
                    } catch (ExcepcionMensaje e1) {
                        JOptionPane.showMessageDialog(vista, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición prorrogada correctamente.",
                            "Exposición prorrogada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Cerrar Temporalmente":

                    fechaInicio = vista.getFechaInicio();
                    fechaFin = vista.getFechaFin();

                    if (fechaFin == null || fechaFin == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar las fechas de inicio y fin.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        exposicion.expoCerrarTemporalmente(fechaInicio, fechaFin);  
                    } catch (ExcepcionMensaje e1) {
                        JOptionPane.showMessageDialog(vista, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    } 

                    frame.actualizarTablaObras(centroExposicion);

                    JOptionPane.showMessageDialog(vista, "Exposición cerrada temporalmente correctamente.",
                            "Exposición cerrada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Publicar Exposicion":

                    try {
                        exposicion.expoPublicar();
                    } catch (ExcepcionMensaje e1) {
                        JOptionPane.showMessageDialog(vista, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición publicada correctamente.", "Exposición publicada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Establecer como Temporal":

                    fechaFin = vista.getFechaFin();

                    if (fechaFin == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha de fin.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        exposicion.expoTemporal(fechaFin);
                    } catch (ExcepcionMensaje e1) {
                        JOptionPane.showMessageDialog(vista, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición establecida como temporal correctamente.",
                            "Exposición temporal",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Establecer como Permanente":

                    try {
                        exposicion.expoPermanente();
                    } catch (ExcepcionMensaje e1) {
                        JOptionPane.showMessageDialog(vista, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición establecida como permanente correctamente.",
                            "Exposición permanente",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Eliminar Exposicion":

                    try {
                        centroExposicion.removeExposicion(exposicion);
                    } catch (ExcepcionMensaje e1) {
                        JOptionPane.showMessageDialog(vista, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición eliminada correctamente.", "Exposición eliminada",
                            JOptionPane.INFORMATION_MESSAGE);

                    break;
            }

            frame.actualizarTablaExposiciones(centroExposicion);
            vista.dispose();
        }
    };

    /**
     * Método que inicializa el listener para el botón cancelar
     */
    private ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
        }
    };

    /**
     * Método que devuelve el listener para el botón aceptar
     * 
     * @return ActionListener
     */
    public ActionListener getAceptarListener() {
        return aceptarListener;
    }

    /**
     * Método que devuelve el listener para el botón cancelar
     * 
     * @return ActionListener
     */
    public ActionListener getCancelarListener() {
        return cancelarListener;
    }
}
