package gui.controlador;

import java.awt.event.*;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.exposicion.EstadoExposicion;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.SalaExposicion;
import gui.modelo.exposicion.TipoExpo;
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

                    if (vista.getNombre().equals("") || vista.getFechaInicio() == null
                            || vista.getDescripcion().equals("")
                            || vista.getPrecio() == null) {
                        JOptionPane.showMessageDialog(vista,
                                "Debes rellenar todos los campos o hacerlo de forma correcta.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (vista.getTipoSeleccionado().equals(TipoExpo.TEMPORAL) && vista.getFechaFin() == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha de fin.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (vista.getTipoSeleccionado().equals(TipoExpo.TEMPORAL)) {
                        if (vista.getFechaInicio().isAfter(vista.getFechaFin())) {
                            JOptionPane.showMessageDialog(vista,
                                    "La fecha de inicio no puede ser posterior a la fecha de fin.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    Exposicion expoNueva = new Exposicion(vista.getNombre(), vista.getFechaInicio(),
                            vista.getFechaFin(), vista.getDescripcion(), vista.getTipoSeleccionado(),
                            vista.getPrecio());

                    if (centroExposicion.addExposicion(expoNueva) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede añadir la exposición.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición añadida correctamente.", "Exposición añadida",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Cancelar Exposicion":
                    if (vista.getFechaInicio() == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha de inicio.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (exposicion.getEstado() == EstadoExposicion.EN_CREACION) {
                        JOptionPane.showMessageDialog(vista, "No se puede cancelar una exposición está en creación.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    if (exposicion.getEstado() == EstadoExposicion.CANCELADA) {
                        JOptionPane.showMessageDialog(vista, "La exposición ya está cancelada.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (exposicion.expoCancelar(vista.getFechaInicio()) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede cancelar la exposición.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    frame.actualizarTablaSorteos(centroExposicion);
                    JOptionPane.showMessageDialog(vista, "Exposición cancelada correctamente.", "Exposición cancelada",
                            JOptionPane.INFORMATION_MESSAGE);

                    break;
                case "Prorrogar Exposicion":
                    if (vista.getFechaFin() == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha de fin.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (exposicion.getTipo() == TipoExpo.PERMANENTE) {
                        JOptionPane.showMessageDialog(vista,
                                "No se puede prorrogar una exposición permanente.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (exposicion.getEstado() != EstadoExposicion.PUBLICADA
                            && exposicion.getEstado() != EstadoExposicion.PRORROGADA) {
                        JOptionPane.showMessageDialog(vista,
                                "No se puede prorrogar una exposición que no está publicada o prorrogada.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    if (exposicion.expoProrrogar(vista.getFechaFin()) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede prorrogar la exposición.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición prorrogada correctamente.",
                            "Exposición prorrogada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Cerrar Temporalmente":

                    if (vista.getFechaInicio() == null || vista.getFechaFin() == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar las fechas de inicio y fin.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (exposicion.getEstado() != EstadoExposicion.PUBLICADA
                            && exposicion.getEstado() != EstadoExposicion.PRORROGADA) {
                        JOptionPane.showMessageDialog(vista,
                                "No se puede cerrar una exposición que no está publicada o prorrogada.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    if (exposicion.expoCerrarTemporalmente(vista.getFechaInicio(), vista.getFechaFin()) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede cerrar la exposición temporalmente.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    frame.actualizarTablaObras(centroExposicion);

                    JOptionPane.showMessageDialog(vista, "Exposición cerrada temporalmente correctamente.",
                            "Exposición cerrada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Publicar Exposicion":

                    if (!exposicion.getEstado().equals(EstadoExposicion.EN_CREACION)) {
                        JOptionPane.showMessageDialog(vista,
                                "No se puede publicar una exposición que no está en creación.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    for (SalaExposicion sala : exposicion.getSalas()) {
                        if (sala.getObras().size() == 0) {
                            JOptionPane.showMessageDialog(vista,
                                    "No se puede publicar una exposición con una o más salas sin obras.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    if (exposicion.expoPublicar() == false) {
                        JOptionPane.showMessageDialog(vista,
                                "Error al publicar la exposición",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición publicada correctamente.", "Exposición publicada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Establecer como Temporal":

                    if (vista.getFechaFin() == null) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha de fin.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (exposicion.getTipo() == TipoExpo.TEMPORAL) {
                        JOptionPane.showMessageDialog(vista,
                                "La exposición ya es temporal.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (exposicion.getEstado() == EstadoExposicion.CANCELADA) {
                        JOptionPane.showMessageDialog(vista,
                                "No se puede establecer como temporal una exposición cancelada.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (exposicion.expoTemporal(vista.getFechaFin()) == false) {
                        JOptionPane.showMessageDialog(vista, "Error al establecer la exposición como temporal.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Exposición establecida como temporal correctamente.",
                            "Exposición temporal",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Establecer como Permanente":

                    if (exposicion.getTipo() == TipoExpo.PERMANENTE) {
                        JOptionPane.showMessageDialog(vista,
                                "La exposición ya es permanente.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (exposicion.getEstado().equals(EstadoExposicion.CANCELADA)
                            || exposicion.getEstado().equals(EstadoExposicion.CERRADATEMPORALMENTE)) {
                        JOptionPane.showMessageDialog(vista,
                                "No se puede establecer como permanente una exposición cancelada o cerrada temporalmente.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    exposicion.expoPermanente();
                    JOptionPane.showMessageDialog(vista, "Exposición establecida como permanente correctamente.",
                            "Exposición permanente",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Eliminar Exposicion":

                    if (!exposicion.getEstado().equals(EstadoExposicion.EN_CREACION)) {
                        JOptionPane.showMessageDialog(vista,
                                "No se puede eliminar una exposición que no está en creación.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    if (centroExposicion.removeExposicion(exposicion) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede eliminar la exposición.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break;
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
