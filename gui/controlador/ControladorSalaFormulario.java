package gui.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.*;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.exposicion.Exposicion;
import gui.modelo.exposicion.SalaExposicion;
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
                    if (vista.getNombre().equals("") || vista.getAforo().equals("") || vista.getAncho().equals("")
                            || vista.getLargo().equals("")) {
                        JOptionPane.showMessageDialog(vista, "Debes rellenar todos los campos.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Sala sala = new Sala(vista.getNombre(), Integer.parseInt(vista.getAforo()), vista.getClimatizador(),
                            Integer.parseInt(vista.getTomasElectricidad()), Double.parseDouble(vista.getAncho()),
                            Double.parseDouble(vista.getLargo()));

                    if (centroExposicion.addSala(sala) == false) {
                        JOptionPane.showMessageDialog(vista, "Ya existe una sala con ese nombre.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Object[] salaData = new Object[] {
                            sala.getNombre(),
                            sala.getAforo(),
                            sala.getClimatizador(),
                            sala.getTomasElectricidad(),
                            sala.getAncho(),
                            sala.getLargo()
                    };

                    frame.añadirFilaTablaSalas(salaData);
                    JOptionPane.showMessageDialog(vista, "Sala añadida correctamente.", "Sala añadida",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Añadir Subsala":
                    int selectedRow = frame.getTablaSalas().getSelectedRow();

                    if (selectedRow != -1) {
                        frame.getTablaSalas().clearSelection();
                        String nombre = (String) frame.getTablaSalas().getValueAt(selectedRow, 0);
                        Sala salaSeleccionada = centroExposicion.getSalaPorNombre(nombre);
                        // Si no se ha encontrado sala con ese nombre es que es una subsala
                        if (salaSeleccionada == null) {
                            salaSeleccionada = centroExposicion.getSubSalaPorNombre(nombre);
                        }

                        if (salaSeleccionada.addSubsala(Double.parseDouble(vista.getAncho()),
                                Double.parseDouble(vista.getLargo()),
                                Integer.parseInt(vista.getTomasElectricidad()),
                                Integer.parseInt(vista.getAforo())) == false) {
                            JOptionPane.showMessageDialog(vista,
                                    "No se ha podido añadir la subsala (recursos de la sala padre insuficientes).",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        frame.actualizarTablaSalas(centroExposicion);
                    } else {
                        JOptionPane.showMessageDialog(vista, "Selecciona una sala para añadir una subsala.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(vista, "Subsala añadida correctamente.", "Subsala añadida",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Eliminar Sala":
                    selectedRow = frame.getTablaSalas().getSelectedRow();

                    if (selectedRow != -1) {
                        frame.getTablaSalas().clearSelection();
                        String nombre = (String) frame.getTablaSalas().getValueAt(selectedRow, 0);
                        Sala salaSeleccionada = centroExposicion.getSalaPorNombre(nombre);

                        // Si es null es una subsala
                        if (salaSeleccionada == null) {
                            salaSeleccionada = centroExposicion.getSubSalaPorNombre(nombre);
                            salaSeleccionada.removeSubsala();
                        } else {
                            if (centroExposicion.removeSala(salaSeleccionada) == false) {
                                JOptionPane.showMessageDialog(vista, "No se ha podido eliminar la sala.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        frame.actualizarTablaSalas(centroExposicion);
                    } else {
                        JOptionPane.showMessageDialog(vista, "Selecciona una sala para eliminar.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(vista, "Sala eliminada correctamente.", "Sala eliminada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Añadir Sala a Exposicion":
                    selectedRow = frame.getTablaSalas().getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(vista, "Selecciona una sala para añadir a una exposición.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    frame.getTablaSalas().clearSelection();

                    String nombreSala = (String) frame.getTablaSalas().getValueAt(selectedRow, 0);
                    Sala salaSeleccionada = centroExposicion.getSalaPorNombre(nombreSala);

                    if (salaSeleccionada == null) {
                        salaSeleccionada = centroExposicion.getSubSalaPorNombre(nombreSala);
                        if (salaSeleccionada == null) {
                            JOptionPane.showMessageDialog(vista, "Sala no encontrada.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
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
                        break;
                    }

                    String exposicionSeleccionada = listaExposiciones.getSelectedValue();
                    Exposicion exposicion = centroExposicion.getExposicionPorNombre(exposicionSeleccionada);
                    if (exposicion == null) {
                        JOptionPane.showMessageDialog(vista, "Exposición no encontrada.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    SalaExposicion nuevaSalaExposicion = new SalaExposicion(salaSeleccionada);
                    if (!exposicion.addSala(nuevaSalaExposicion)) {
                        JOptionPane.showMessageDialog(vista, "No se ha podido añadir la sala a la exposición.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    frame.actualizarTablaExposiciones(centroExposicion);
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
