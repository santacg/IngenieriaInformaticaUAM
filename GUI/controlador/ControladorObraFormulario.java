package GUI.controlador;

import java.awt.event.*;

import GUI.vistas.*;

public class ControladorObraFormulario {
    private ObraFormulario vista;

    public ControladorObraFormulario(GestorPrincipal frame) {
        this.vista = frame.getVistaObraFormulario();
    }

    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
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
