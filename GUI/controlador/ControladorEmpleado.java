package GUI.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.vistas.EmpleadoPrincipal;
import GUI.vistas.Ventana;

public class ControladorEmpleado {
    private Ventana frame;
    private EmpleadoPrincipal vista;
    private CentroExposicion centro;

    public ControladorEmpleado(Ventana frame, CentroExposicion centro) {
        this.frame = frame;
        this.centro = centro;
        this.vista = frame.getVistaEmpleadoPrincipal();

        // mostrarClimatizaciones();
        // mostrarExposiciones();

    }
    private ActionListener cambiarClimatizacionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.mostrarPanel(frame.getAjustarClimatizacion());
        }
    };

    public ActionListener getClimatizacionListener() {
        return cambiarClimatizacionListener;
    }

    private ActionListener enviarMsjListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.mostrarPanel(frame.getEnviarMensajes());
        }
    };

    public ActionListener getEnviarMsjListener() {
        return enviarMsjListener;
    }
}
