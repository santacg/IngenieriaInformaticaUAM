package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTable;

import GUI.modelo.centroExposicion.CentroExposicion;
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

    private ActionListener ejecutarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String accion = vista.getAccionSeleccionada();
            JTable tabla = vista.getTablaObras();
            ModeloTablaObras modelo = (ModeloTablaObras) tabla.getModel();

            for (int i = 0; i < modelo.getRowCount(); i++) {

            }
            
            
        }
    };

    public ActionListener getEjecutarListener() {
        return ejecutarListener;
    }
}
