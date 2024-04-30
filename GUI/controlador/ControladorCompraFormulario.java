package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import GUI.modelo.expofy.ClienteRegistrado;
import GUI.modelo.exposicion.*;
import GUI.vistas.*;

public class ControladorCompraFormulario {

    private Exposicion exposicion;
    private ClientePrincipal frame;
    private CompraFormulario vista;
    private ClienteRegistrado cliente;

    public ControladorCompraFormulario(ClientePrincipal frame, Exposicion exposicion, ClienteRegistrado cliente){
        
        this.exposicion = exposicion;
        this.frame = frame;
        this.cliente = cliente;
        this.vista = frame.getVistaCompraFormulario(exposicion.getNombre(), exposicion.getPrecio());

    }

    public ActionListener getSiguienteListener() {
        return siguienteListener;
    }

    private ActionListener siguienteListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(vista, "Mostrar resumen de compra.");
        }
    };

    public ActionListener getCancelarListener() {
        return cancelarListener;
    }

    private ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
        }
    };

}