package GUI.controlador;

import java.awt.event.*;

import javax.swing.JOptionPane;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.sala.Sala;
import GUI.vistas.*;

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

                    frame.actualizarTablaSalas(centroExposicion);
                    JOptionPane.showMessageDialog(vista, "Sala añadida correctamente.", "Sala añadida",
                            JOptionPane.INFORMATION_MESSAGE);
                    vista.dispose();
                    break;
            }
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
