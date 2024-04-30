package GUI.controlador;

import java.awt.event.*;

import javax.swing.JOptionPane;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.sala.Sala;
import GUI.vistas.*;

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
     * @param frame GestorPrincipal
     * @param centroExposicion CentroExposicion
     * @param accion String
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
                    Sala sala = new Sala(vista.getNombre(), vista.getAforo(), vista.getTemperatura(),
                            vista.getHumedad(), vista.getClimatizador(), vista.getTomasElectricidad(), vista.getAncho(),
                            vista.getLargo());

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

                        if (salaSeleccionada.addSubsala(vista.getAncho(), vista.getLargo(),
                                vista.getTomasElectricidad(), vista.getAforo()) == false) {
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
