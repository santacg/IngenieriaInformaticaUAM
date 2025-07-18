package gui.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.expofy.*;
import gui.modelo.exposicion.Exposicion;
import gui.vistas.BusquedaExposiciones;
import gui.vistas.Ventana;

/**
 * Clase ControladorBusquedaExposiciones
 * Implementa el control de la vista BusquedaExposiciones.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorBusquedaExposiciones {
    private Ventana frame;
    private BusquedaExposiciones vista;
    private Expofy expofy;

    /**
     * Constructor de la clase ControladorBusquedaExposiciones
     * 
     * @param frame  Ventana principal
     * @param expofy Modelo de datos de la aplicación
     */
    public ControladorBusquedaExposiciones(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.expofy = expofy;
        this.vista = frame.getVistaBusquedaExposiciones();

        mostrarExposiciones();
    }

    /**
     * Muestra las exposiciones en la tabla de la vista
     */
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

    /**
     * Listener para el botón de volver atrás
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.mostrarPanelPrevio();
        }
    };

    /**
     * Devuelve el listener para el botón de volver atrás
     * 
     * @return ActionListener
     */
    public ActionListener getAtrasListener() {
        return atrasListener;
    }

}
