package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.exposicion.Exposicion;
import GUI.modelo.obra.Estado;
import GUI.modelo.obra.Obra;
import GUI.vistas.GestorPrincipal;
import GUI.vistas.ModeloTablaObras;
import GUI.vistas.Ventana;

/**
 * Clase ControladorGestor.
 * Implementa el controlador de la vista del gestor.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorGestor {

    private Ventana frame;
    private GestorPrincipal vista;
    private CentroExposicion centro;

    /**
     * Constructor de la clase ControladorGestor.
     * 
     * @param frame  Ventana principal de la aplicación.
     * @param centro Centro de exposición.
     */
    public ControladorGestor(Ventana frame, CentroExposicion centro) {
        this.frame = frame;
        this.centro = centro;
        this.vista = frame.getVistaGestorPrincipal();

        mostrarExposiciones();
        mostrarSalas();
        mostrarObras();
    }

    /**
     * Método que muestra la vista del gestor.
     */
    public void mostrarExposiciones() {
        vista.addPanelExposiciones(centro);
    }

    /**
     * Método que muestra la vista de las salas.
     */
    public void mostrarSalas() {
        vista.addPanelSalas(centro);
    }

    /**
     * Método que muestra la vista de las obras.
     */
    public void mostrarObras() {
        vista.addPanelObras(centro);
    }

    /**
     * Método que actualiza la vista de las exposiciones segun la ejecucion que se
     * haya realizado.
     */
    private ActionListener exposicionEjecutarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accion = vista.getExposicionAccionSeleccionada();
            JTable tabla = vista.getTablaExposiciones();

            int selectedRow = tabla.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Debes seleccionar una exposición.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Exposicion exposicion : centro.getExposiciones()) {
                if (exposicion.getNombre().equals(tabla.getValueAt(selectedRow, 0))) {
                    ControladorExposicionFormulario controladorExposicionFormulario = new ControladorExposicionFormulario(
                            vista, centro, exposicion, accion);
                    vista.setControladorExposicionFormulario(controladorExposicionFormulario);
                    break;
                }
            }

        }
    };

    /**
     * Método que actualiza la vista de las obras segun la ejecucion que se haya
     * realizado.
     */
    private ActionListener obraEjecutarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accion = vista.getObraAccionSeleccionada();
            JTable tabla = vista.getTablaObras();
            ModeloTablaObras modelo = (ModeloTablaObras) tabla.getModel();
            List<Obra> obras = new ArrayList<>();

            for (int i = 0; i < modelo.getRowCount(); i++) {
                Boolean seleccionado = (Boolean) modelo.getValueAt(i, 0);
                if (seleccionado) {
                    String nombreObra = (String) modelo.getValueAt(i, 1);
                    for (Obra obra : centro.getObras()) {
                        if (obra.getNombre().equals(nombreObra)) {
                            obras.add(obra);
                            switch (accion) {
                                case "Retirar Obra":
                                    if (obra.retirarObra() == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede retirar la obra " + nombreObra);
                                        continue;
                                    }
                                    modelo.setValueAt(Estado.RETIRADA, i, 8);
                                    break;
                                case "Almacenar Obra":
                                    obra.almacenarObra();
                                    modelo.setValueAt(Estado.ALMACENADA, i, 8);
                                    break;
                                case "Exponer Obra":
                                    if (obra.exponerObra() == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede exponer la obra " + nombreObra);
                                        continue;
                                    }
                                    modelo.setValueAt(Estado.EXPUESTA, i, 8);
                                    break;
                                case "Prestar Obra":
                                    if (obra.prestarObra() == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede prestar la obra " + nombreObra);
                                        continue;
                                    }
                                    modelo.setValueAt(Estado.PRESTADA, i, 8);
                                    break;
                                case "Restaurar Obra":
                                    if (obra.restaurarObra() == false) {
                                        JOptionPane.showMessageDialog(frame,
                                                "No se puede restaurar la obra " + nombreObra);
                                        continue;
                                    }
                                    modelo.setValueAt(Estado.RESTAURACION, i, 8);
                                    break;
                            }
                            break;
                        }
                    }
                }
            }

            vista.deseleccionarTabla();
        }
    };

    /**
     * Método que actualiza la vista de las obras incializando un listener.
     */
    private ActionListener obraAgregarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorObraFormulario controladorObraFormulario = new ControladorObraFormulario(vista, centro);
            vista.setControladorObraFormulario(controladorObraFormulario);
        }
    };

    /**
     * Método que inicializa un listener para ejecutar las salas.
     */
    private ActionListener salaEjecutarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accion = vista.getSalaAccionSeleccionada();
            ControladorSalaFormulario controladorSalaFormulario = new ControladorSalaFormulario(vista, centro, accion);
            vista.setControladorSalaFormulario(controladorSalaFormulario);
        }
    };

    private ActionListener exposicionAgregarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorExposicionFormulario controladorExposicionFormulario = new ControladorExposicionFormulario(vista,
                    centro);
            vista.setControladorExposicionFormulario(controladorExposicionFormulario);
        }
    };

    private ActionListener cerrarSesionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            centro.getGestor().logOut();
            JOptionPane.showMessageDialog(frame, "Se ha cerrado la sesión.");
            frame.mostrarPanel(frame.getPanelPrincipal());
        }
    };

    /**
     * Método que obtiene el listener de de ejecucion de obras.
     */
    public ActionListener getObraEjecutarListener() {
        return obraEjecutarListener;
    }

    /**
     * Método que obtiene el listener de agregar obras.
     */
    public ActionListener getObraAgregarListener() {
        return obraAgregarListener;
    }

    /**
     * Método que obtiene el listener de ejecucion de salas.
     */
    public ActionListener getSalaEjecutarListener() {
        return salaEjecutarListener;
    }

    /**
     * Método que obtiene el listener de ejecucion de exposiciones.
     */
    public ActionListener getExposicionEjecutarListener() {
        return exposicionEjecutarListener;
    }

    /**
     * Método que obtiene el listener de agregar exposiciones.
     */
    public ActionListener getExposicionAgregarListener() {
        return exposicionAgregarListener;
    }

    /**
     * Método que devuelve el ActionListener para cerrar la sesión del gestor.
     * 
     * @return ActionListener para cerrar la sesión del cliente.
     */
    public ActionListener getCerrarSesionListener() {
        return cerrarSesionListener;
    }


}
