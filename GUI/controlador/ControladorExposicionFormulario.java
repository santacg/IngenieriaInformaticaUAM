package GUI.controlador;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.exposicion.Exposicion;
import GUI.modelo.exposicion.SalaExposicion;
import GUI.modelo.obra.Obra;
import GUI.modelo.sala.Sala;
import GUI.vistas.*;

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
        mostrarObras();
        mostrarSalas();
    }

    public void mostrarObras() {
        vista.mostrarObras(centroExposicion);
    }

    public void mostrarSalas() {
        vista.mostrarSalas(centroExposicion);
    }

    /**
     * Método que inicializa la vista y añade los listeners para el botón aceptar
     */
    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            switch (accion) {
                case "Agregar Exposicion":
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) vista.getTreeSalas()
                            .getLastSelectedPathComponent();
                    if (selectedNode == null) {
                        JOptionPane.showMessageDialog(vista, "Debes seleccionar una sala.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String nombreSala = (String) selectedNode.getUserObject();
                    Sala sala = centroExposicion.getSalaPorNombre(nombreSala);
                    if (sala == null) {
                        sala = centroExposicion.getSubSalaPorNombre(nombreSala);
                    }

                    List<String> nombresObrasSeleccionadas = vista.getObras();
                    Set<Obra> obrasSeleccionadas = new HashSet<>();
                    for (String nombreObra : nombresObrasSeleccionadas) {
                        Obra obra = centroExposicion.getObraPorNombre(nombreObra);
                        obrasSeleccionadas.add(obra);
                    }

                    Set<SalaExposicion> salasExpo = new HashSet<>();
                    SalaExposicion salaExpo = new SalaExposicion(sala);
                    for (Obra obra : obrasSeleccionadas) {
                        if (salaExpo.addObra(obra) == false) {
                            JOptionPane.showMessageDialog(vista, "No se puede añadir la obra a la sala de exposicion.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    salasExpo.add(salaExpo);

                    Exposicion expoNueva = new Exposicion(vista.getNombre(), vista.getFechaInicio(),
                            vista.getFechaFin(), vista.getDescripcion(), salasExpo, vista.getTipoExpo(),
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
                    if (exposicion.expoCancelar(vista.getFechaInicio()) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede cancelar la exposición.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(vista, "Exposición cancelada correctamente.", "Exposición cancelada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Prorrogar Exposicion":
                    if (exposicion.expoProrrogar(vista.getFechaFin()) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede prorrogar la exposición.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(vista, "Exposición prorrogada correctamente.",
                            "Exposición prorrogada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Cerrar Exposicion":
                    if (exposicion.expoCerrarTemporalmente(vista.getFechaInicio(), vista.getFechaFin()) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede cerrar la exposición temporalmente.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(vista, "Exposición cerrada temporalmente correctamente.",
                            "Exposición cerrada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Publicar Exposicion":
                    if (exposicion.expoPublicar() == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede publicar la exposición.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(vista, "Exposición publicada correctamente.", "Exposición publicada",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Establecer como Temporal":
                    if (exposicion.expoTemporal(vista.getFechaFin()) == false) {
                        JOptionPane.showMessageDialog(vista, "No se puede establecer la exposición como temporal.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(vista, "Exposición establecida como temporal correctamente.",
                            "Exposición temporal",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Establecer como Permanente":
                    exposicion.expoPermanente();
                    JOptionPane.showMessageDialog(vista, "Exposición establecida como permanente correctamente.",
                            "Exposición permanente",
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
