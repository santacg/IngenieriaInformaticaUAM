package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.obra.Estado;
import GUI.modelo.obra.Obra;
import GUI.vistas.GestorPrincipal;
import GUI.vistas.ModeloTablaObras;
import GUI.vistas.Ventana;

public class ControladorGestor {

    private Ventana frame;
    private GestorPrincipal vista;
    private CentroExposicion centro;

    public ControladorGestor(Ventana frame, CentroExposicion centro) {
        this.frame = frame;
        this.centro = centro;
        this.vista = frame.getVistaGestorPrincipal();

        mostrarExposiciones();
        mostrarSalas();
        mostrarObras();
    }

    public void mostrarExposiciones() {
        vista.addTablaExposiciones(centro);
    }

    public void mostrarSalas() {
        vista.addTablaSalas(centro);
    }

    public void mostrarObras() {
        vista.addPanelObras(centro);
    }

    // Obras 
    private ActionListener ejecutarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accion = vista.getAccionSeleccionada();
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
                                default:
                            }
                            break;
                        }
                    }
                }
            }

            vista.deseleccionarTabla();
        }
    };

    private ActionListener agregarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ControladorObraFormulario controladorObraFormulario = new ControladorObraFormulario(vista);
            vista.setControladorObraFormulario(controladorObraFormulario);
        }
    };

    public ActionListener getEjecutarListener() {
        return ejecutarListener;
    }

    public ActionListener getAgregarListener() {
        return agregarListener;
    }
}
