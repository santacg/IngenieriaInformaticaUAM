package GUI.controlador;

import java.awt.event.*;
import javax.swing.*;

import GUI.modelo.expofy.*;
import GUI.vistas.*;

public class ControladorObraFormulario {
    private Ventana frame;
    private ObraFormulario vistObraFormulario;
    private Expofy expofy;

    public ControladorObraFormulario(Ventana frame) {
        this.frame = frame;
        this.vistObraFormulario = frame.getVistaObraFormulario();
    }

    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
 
        }
    };
 
}
