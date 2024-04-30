package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import GUI.modelo.exposicion.*;
import GUI.vistas.*;

public class ControladorCompraFormulario {

    private Exposicion exposicion;
    private ClientePrincipal frame;
    private CompraFormulario vista;

    public ControladorCompraFormulario(ClientePrincipal frame, Exposicion exposicion){
        
        this.exposicion = exposicion;
        this.frame = frame;
        this.vista = frame.getVistaCompraFormulario();

        
    }
}