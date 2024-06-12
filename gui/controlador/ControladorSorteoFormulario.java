package gui.controlador;

import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import gui.modelo.centroExposicion.*;
import gui.modelo.exposicion.Exposicion;
import gui.vistas.GestorPrincipal;
import gui.vistas.SorteoFormulario;

public class ControladorSorteoFormulario {
    private SorteoFormulario vista;
    private GestorPrincipal frame;
    private CentroExposicion centroExposicion;

    /**
     * Constructor de la clase ControladorSalaFormulario
     * 
     * @param frame            GestorPrincipal
     * @param centroExposicion CentroExposicion
     * @param accion           String
     */
    public ControladorSorteoFormulario(GestorPrincipal frame, CentroExposicion centroExposicion) {
        this.frame = frame;
        this.vista = frame.getVistaSorteoFormulario();
        this.centroExposicion = centroExposicion;
        mostrarExpo();
    }

    public void mostrarExpo() {
        vista.mostrarExposiciones(centroExposicion);
    }

    /**
     * Método que inicializa el listener del botón aceptar
     */
    private ActionListener aceptarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String tipoSorteo = vista.getTipoSorteo();

            LocalDate fechaSorteo = vista.getFechaSorteo();
            if (fechaSorteo == null) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha del sorteo o introducirla correctamente.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer nEntradas = vista.getSorteoEntradas();
            if (nEntradas == null || nEntradas <= 0) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar el número de entradas o introducir un número de entradas correcto.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            if (tipoSorteo.equals("Sorteo por día y hora") && (vista.getDiaSorteo() == null || vista.getHoraSorteo().equals(""))) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar el día y la hora del sorteo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tipoSorteo.equals("Sorteo por fechas") && (vista.getFechaInicioSorteo() == null || vista.getFechaFinSorteo() == null)) {
                JOptionPane.showMessageDialog(vista, "Debes rellenar la fecha de inicio y fin del sorteo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Exposicion exposicion = centroExposicion.getExposicionPorNombre(vista.getSelectedExposicion());
            if (exposicion == null) {
                JOptionPane.showMessageDialog(vista, "Debes seleccionar una exposición.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Boolean res = true;
            switch (tipoSorteo) {
                case "Sorteo por día y hora":
                    res = centroExposicion.confgiurarSorteoDiaHora(exposicion, fechaSorteo, nEntradas, vista.getDiaSorteo(), null);
                    break;
                case "Sorteo por fechas":
                    res = centroExposicion.confgiurarSorteoFechas(exposicion, fechaSorteo, nEntradas, vista.getFechaInicioSorteo(), vista.getFechaFinSorteo());
                    break;
                case "Sorteo por exposición":
                    res = centroExposicion.confgiurarSorteoExposicion(exposicion, fechaSorteo, nEntradas);
                    break;
            }

            if (res == false) {
                JOptionPane.showMessageDialog(vista, "No se ha podido configurar el sorteo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            frame.actualizarTablaSorteos(centroExposicion);
            vista.dispose();
        }
    };

    /**
     * Método que inicializa el listener del botón cancelar
     */
    private ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
        }
    };

    /**
     * Método que devuelve el listener del botón aceptar
     * 
     * @return ActionListener
     */
    public ActionListener getAceptarListener() {
        return aceptarListener;
    }

    /**
     * Método que devuelve el listener del botón cancelar
     * 
     * @return ActionListener
     */
    public ActionListener getCancelarListener() {
        return cancelarListener;
    }

}
