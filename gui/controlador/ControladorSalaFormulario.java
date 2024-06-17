package gui.controlador;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.exposicion.*;
import gui.modelo.sala.Sala;
import gui.vistas.*;

/**
 * Clase ControladorSalaFormulario
 * Implementa el controlador de la vista SalaFormulario.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorSalaFormulario {
    private SalaFormulario vista;
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
    public ControladorSalaFormulario(GestorPrincipal frame, CentroExposicion centroExposicion, String accion) {
        this.frame = frame;
        this.vista = frame.getVistaSalaFormulario(accion);
        this.centroExposicion = centroExposicion;
        this.accion = accion;
    }

    /**
     * Método que inicializa el listener del botón aceptar
     */
    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            switch (accion) {
                case "Añadir Sala":

                    Integer aforo = vista.getAforo();
                    Double ancho = vista.getAncho();
                    Double largo = vista.getLargo();
                    Double alto = vista.getAlto();
                    Integer tomasElectricidad = vista.getTomasElectricidad();

                    if (aforo == null || ancho == null || largo == null || alto == null || tomasElectricidad == null
                            || vista.getNombre().equals("")) {
                        JOptionPane.showMessageDialog(vista,
                                "Debes rellenar todos los campos o hacerlo de forma correcta.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (aforo <= 0 || ancho <= 0 || largo <= 0 || alto <= 0 || tomasElectricidad < 0) {
                        JOptionPane.showMessageDialog(vista,
                                "Debes rellenar todos los campos con valores mayores que 0 (o 0 en caso de las tomas).",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Sala sala = new Sala(vista.getNombre(), aforo, vista.getClimatizador(),
                            tomasElectricidad, ancho,
                            largo, alto);

                    if (centroExposicion.addSala(sala) == false) {
                        JOptionPane.showMessageDialog(vista, "Ya existe una sala con ese nombre.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    frame.actualizarTablaSalas(centroExposicion);
                    JOptionPane.showMessageDialog(vista, "Sala añadida correctamente.", "Sala añadida",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Añadir Subsala":
                    int selectedRow = frame.getTablaSalas().getSelectedRow();

                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(vista, "Selecciona una sala para añadir una subsala.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    frame.getTablaSalas().clearSelection();
                    String nombre = (String) frame.getTablaSalas().getValueAt(selectedRow, 0);
                    Sala salaSeleccionada = centroExposicion.getSalaPorNombre(nombre);

                    if (salaSeleccionada == null) {
                        salaSeleccionada = centroExposicion.getSubSalaPorNombre(nombre);
                    }

                    aforo = vista.getAforo();
                    ancho = vista.getAncho();
                    largo = vista.getLargo();
                    tomasElectricidad = vista.getTomasElectricidad();

                    if (aforo == null || ancho == null || largo == null
                            || tomasElectricidad == null) {
                        JOptionPane.showMessageDialog(vista,
                                "Debes rellenar todos los campos o hacerlo de forma correcta.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (aforo <= 0 || ancho <= 0 || largo <= 0 || tomasElectricidad < 0) {
                        JOptionPane.showMessageDialog(vista,
                                "Debes rellenar todos los campos con valores mayores que 0 (o 0 en caso de las tomas).",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (salaSeleccionada.addSubsala(ancho,
                            largo,
                            tomasElectricidad,
                            aforo) == false) {
                        JOptionPane.showMessageDialog(vista,
                                "No se ha podido añadir la subsala (recursos de la sala padre insuficientes).",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    frame.actualizarTablaSalas(centroExposicion);

                    JOptionPane.showMessageDialog(vista, "Subsala añadida correctamente.", "Subsala añadida",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Eliminar Sala":
                    selectedRow = frame.getTablaSalas().getSelectedRow();

                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(vista, "Selecciona una sala para eliminar.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    frame.getTablaSalas().clearSelection();
                    nombre = (String) frame.getTablaSalas().getValueAt(selectedRow, 0);
                    salaSeleccionada = centroExposicion.getSalaPorNombre(nombre);

                    if (salaSeleccionada == null) {
                        salaSeleccionada = centroExposicion.getSubSalaPorNombre(nombre);
                    }

                    for (Exposicion exposicion : centroExposicion.getExposiciones()) {
                        for (SalaExposicion salaExposicion : exposicion.getSalas()) {
                            if (salaExposicion.getSala().getNombre().equals(salaSeleccionada.getNombre())) {
                                JOptionPane.showMessageDialog(vista,
                                        "No se puede eliminar una sala que está asignada a una exposición.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                    }

                    if (centroExposicion.removeSala(salaSeleccionada) == false) {
                        JOptionPane.showMessageDialog(vista, "No se ha podido eliminar la sala.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    frame.actualizarTablaSalas(centroExposicion);

                    JOptionPane.showMessageDialog(vista, "Sala eliminada correctamente.", "Sala eliminada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Añadir Sala a Exposicion":

                    selectedRow = frame.getTablaSalas().getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(vista, "Selecciona una sala para añadir a una exposición.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    frame.getTablaSalas().clearSelection();

                    String nombreSala = (String) frame.getTablaSalas().getValueAt(selectedRow, 0);
                    salaSeleccionada = centroExposicion.getSalaPorNombre(nombreSala);

                    if (salaSeleccionada == null) {
                        JOptionPane.showMessageDialog(vista, "No se puede añadir una subsala, debes añadir salas.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    List<String> nombresExposiciones = new ArrayList<>();
                    for (Exposicion exposicion : centroExposicion.getExposiciones()) {
                        nombresExposiciones.add(exposicion.getNombre());
                    }

                    JList<String> listaExposiciones = new JList<>(nombresExposiciones.toArray(new String[0]));
                    JScrollPane scrollPane = new JScrollPane(listaExposiciones);

                    int result = JOptionPane.showConfirmDialog(vista, scrollPane, "Selecciona una exposición",
                            JOptionPane.OK_CANCEL_OPTION);

                    if (result != JOptionPane.OK_OPTION || listaExposiciones.getSelectedValue() == null) {
                        JOptionPane.showMessageDialog(vista, "No se ha seleccionado ninguna exposición.",
                                "Acción cancelada", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    String exposicionSeleccionada = listaExposiciones.getSelectedValue();
                    Exposicion exposicion = centroExposicion.getExposicionPorNombre(exposicionSeleccionada);

                    if (exposicion == null) {
                        JOptionPane.showMessageDialog(vista, "Exposición no encontrada.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    if (exposicion.getEstado() != EstadoExposicion.EN_CREACION) {
                        JOptionPane.showMessageDialog(vista, "La exposición no se puede modificar.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    for (SalaExposicion salaExposicion : exposicion.getSalas()) {
                        if (salaExposicion.getSala().getNombre().equals(salaSeleccionada.getNombre())) {
                            JOptionPane.showMessageDialog(vista, "La sala ya está asignada a la exposición.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    for (Exposicion exposicionCentro : centroExposicion.getExposiciones()) {
                        if (!(exposicion.getFechaFin().isBefore(exposicionCentro.getFechaInicio()) ||
                                exposicion.getFechaInicio().isAfter(exposicionCentro.getFechaFin()))) {

                            for (SalaExposicion salaExposicion : exposicionCentro.getSalas()) {
                                if (salaExposicion.getSala().getNombre().equals(salaSeleccionada.getNombre())) {
                                    JOptionPane.showMessageDialog(vista,
                                            "La sala ya está asignada a otra exposición en las mismas fechas.",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                        }
                    }

                    if (exposicion.addSala(new SalaExposicion(salaSeleccionada)) == false) {
                        JOptionPane.showMessageDialog(vista, "No se ha podido añadir la sala a la exposición.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    for (Sala subSalas : salaSeleccionada.getSubSalas()) {
                        if (exposicion.addSala(new SalaExposicion(subSalas)) == false) {
                            JOptionPane.showMessageDialog(vista,
                                    "No se han podido añadir las subsalas a la exposición.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    frame.actualizarTablaExposiciones(centroExposicion);
                    frame.actualizarTablaSalasExposicion(centroExposicion);

                    JOptionPane.showMessageDialog(vista, "Sala añadida a la exposición correctamente.",
                            "Sala añadida", JOptionPane.INFORMATION_MESSAGE);

                    break;

            }
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
     * @break ActionListener
     */
    public ActionListener getAceptarListener() {
        return aceptarListener;
    }

    /**
     * Método que devuelve el listener del botón cancelar
     * 
     * @break ActionListener
     */
    public ActionListener getCancelarListener() {
        return cancelarListener;
    }

}
