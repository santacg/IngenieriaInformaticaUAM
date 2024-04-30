package GUI.controlador;

import java.awt.event.*;

import javax.swing.JOptionPane;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.sala.Sala;
import GUI.vistas.*;

import java.util.*;

public class ControladorSalaFormulario {
    private SalaFormulario vista;
    private GestorPrincipal frame;
    private CentroExposicion centroExposicion;
    private String accion;

    public ControladorSalaFormulario(GestorPrincipal frame, CentroExposicion centroExposicion, String accion) {
        this.frame = frame;
        this.vista = frame.getVistaSalaFormulario(accion);
        this.centroExposicion = centroExposicion;
        this.accion = accion;
    }

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

                    if (selectedRow >= 0) {
                        String nombre = (String) frame.getTablaSalas().getValueAt(selectedRow, 0);
                        Sala salaSeleccionada = centroExposicion.getSalaPorNombre(nombre);
                        // Esto es muy inificiente, habría que replantear salas y subsalas posiblemente
                        // (Carlos)
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

                        // Actualizacion
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
                    break;
            }
            vista.dispose();
        }
    };

    private ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
        }
    };

    public ActionListener getAceptarListener() {
        return aceptarListener;
    }

    public ActionListener getCancelarListener() {
        return cancelarListener;
    }

}
