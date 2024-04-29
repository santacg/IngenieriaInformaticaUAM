package GUI.controlador;


import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.expofy.*;
import GUI.modelo.exposicion.Exposicion;
import GUI.vistas.BusquedaExposiciones;
import GUI.vistas.ClientePrincipal;
import GUI.vistas.Ventana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.awt.event.*;
import javax.swing.*;

import javax.swing.JOptionPane;

public class ControladorBusquedaExposiciones {
    private Ventana frame;
    private BusquedaExposiciones vista;
    private Expofy expofy;

    public ControladorBusquedaExposiciones(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.expofy = expofy;
        this.vista = frame.getVistaBusquedaExposiciones();

        mostrarExposiciones();
    }

    public void mostrarExposiciones() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (CentroExposicion centro : expofy.getCentrosExposicion()) {
            for (Exposicion exposicion : centro.getExposiciones()) {
                data.add(new Object[] {
                        exposicion.getNombre(),
                        exposicion.getDescripcion(),
                        exposicion.getFechaInicio(),
                        exposicion.getFechaFin(),
                        exposicion.getPrecio(),
                        centro.getNombre(),
                        centro.getLocalizacion()
                });
            }
        }
        vista.addTablaExposiciones(data);
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
