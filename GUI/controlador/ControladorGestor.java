package GUI.controlador;

import java.util.Set;

import javax.swing.JFrame;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.exposicion.Exposicion;
import GUI.vistas.GestorPrincipal;
import GUI.vistas.Ventana;

public class ControladorGestor {
    
    private Ventana frame;
    private GestorPrincipal vista;
    private CentroExposicion centro;

    public ControladorGestor(Ventana frame, CentroExposicion centro) {
        this.frame = frame;
        this.centro = centro;
        this.vista = frame.getVistaGestorPrincipal();
    }

    public void setExposiciones(Set<Exposicion> exposiciones) {
        vista.setExposiciones(exposiciones);
    }
}
