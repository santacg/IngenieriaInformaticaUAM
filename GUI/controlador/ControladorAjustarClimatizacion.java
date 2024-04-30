package GUI.controlador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.sala.Sala;
import GUI.vistas.AjustarClimatizacion;
import GUI.vistas.Ventana;

public class ControladorAjustarClimatizacion {
    private Ventana frame;
    private AjustarClimatizacion vista;
    private CentroExposicion centro;

    public ControladorAjustarClimatizacion(Ventana frame, CentroExposicion centro) {
        this.frame = frame;
        this.centro = centro;
        this.vista = frame.getVistaAjustarClimatizacion();

        mostrarTemperatura();
        mostrarHumedad();
    }

    public void mostrarTemperatura() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (Sala sala : centro.getSalas()) {
            data.add(new Object[] {
                    sala.getNombre(),
                    sala.getClimatizador(),
                    sala.getTemperatura()
            });
        }
        vista.addTemperatura(data);
    }

    public void mostrarHumedad() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (Sala sala : centro.getSalas()) {
            data.add(new Object[] {
                    sala.getNombre(),
                    sala.getClimatizador(),
                    sala.getHumedad()
            });
        }
        vista.addHumedad(data);
    }

    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.mostrarPanelPrevio();
        }
    };

    public ActionListener getAtrasListener() {
        return atrasListener;
    }
}
