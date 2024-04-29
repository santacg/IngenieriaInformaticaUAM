package GUI.controlador;

import GUI.modelo.centroExposicion.CentroExposicion;
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

}
