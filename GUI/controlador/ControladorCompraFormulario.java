package gui.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import gui.modelo.expofy.*;
import gui.modelo.exposicion.*;
import gui.modelo.tarjetaDeCredito.TarjetaDeCredito;
import gui.vistas.*;

/**
 * Clase ControladorCompraFormulario
 * Actúa como controlador de la vista CompraFormulario. Se encarga de gestionar
 * los eventos de la vista y de realizar las operaciones necesarias para
 * completar la compra de entradas.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorCompraFormulario {

    private Expofy expofy;
    private Exposicion exposicion;
    private ClientePrincipal frame;
    private CompraFormulario vista;
    private ClienteRegistrado cliente;
    private ConfirmarCompra vistConfirmarCompra;
    private LocalDate fecha, fechaTarj;
    private Integer hora = 9, nEntradas = 1, cvv;
    private Integer diaExpo = 1, mesExpo = 1, anioExpo = 1990;
    private Integer diaTarj = 1, mesTarj = 1, anioTarj = 1990;
    private JComboBox<Integer> diaExpoCombo, mesExpoCombo, anioExpoCombo;
    private JComboBox<Integer> diaTarjCombo, mesTarjCombo, anioTarjCombo, nEntradasCombo;
    private JComboBox<String> horaCombo;
    private double precioFinal;
    private String numeroTarjetadeCredito, codigoSorteo;

    /**
     * Constructor de la clase ControladorCompraFormulario
     * 
     * @param frame      ClientePrincipal
     * @param expofy     Expofy
     * @param exposicion Exposicion
     * @param cliente    ClienteRegistrado
     */
    public ControladorCompraFormulario(ClientePrincipal frame, Expofy expofy, Exposicion exposicion,
            ClienteRegistrado cliente) {

        this.exposicion = exposicion;
        this.frame = frame;
        this.cliente = cliente;
        this.expofy = expofy;
        this.vista = frame.getVistaCompraFormulario(exposicion.getNombre(), exposicion.getPrecio());

        diaExpoCombo = vista.getDiaExpo();
        mesExpoCombo = vista.getMesExpo();
        anioExpoCombo = vista.getAnioExpo();
        horaCombo = vista.getHora();

        nEntradasCombo = vista.getNentradas();

        diaTarjCombo = vista.getDiaTarj();
        mesTarjCombo = vista.getMesTarj();
        anioTarjCombo = vista.getAnioTarj();

    }

    /**
     * Método que devuelve el ActionListener del botón de siguiente
     * 
     * @return sigueinteListener
     */
    public ActionListener getSiguienteListener() {
        return siguienteListener;
    }

    /**
     * Método que inicializa el ActionListener del botón de siguiente
     */
    private ActionListener siguienteListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String exposicionNombre = vista.getExposicionNombre();
            String precioPorEntrada = vista.getPrecio();
            numeroTarjetadeCredito = vista.getNumeroTarjetadeCredito();
            cvv = vista.getCVV();

            codigoSorteo = vista.getCodigoSorteo();
            if (expofy.validezCodigo(codigoSorteo, cliente)) {
                precioFinal = (exposicion.getPrecio() * (double) nEntradas) - exposicion.getPrecio();
            }
            else{
                precioFinal = (exposicion.getPrecio() * (double) nEntradas);
            }

            fecha = LocalDate.of(anioExpo, mesExpo, diaExpo);
            if (fecha.isBefore(LocalDate.now()) || fecha.isBefore(exposicion.getFechaInicio())
                    || fecha.isAfter(exposicion.getFechaFin())) {
                JOptionPane.showMessageDialog(frame, "La fecha de la exposición no es válida.");
                return;
            }
            fechaTarj = LocalDate.of(anioTarj, mesTarj, diaTarj);
            if (fechaTarj.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(frame, "La tarjeta introducida está caducada.");
                return;
            }
            if (nEntradas <= 0 || nEntradas >= exposicion.getHora(fecha, hora).getnEntradasDisp()) {
                JOptionPane.showMessageDialog(frame, "No hay ese número de entradas disponibles para la exposición.");
                return;
            }
            if (numeroTarjetadeCredito.length() != 16) {
                JOptionPane.showMessageDialog(frame, "El número de la tarjeta de crédito no es válido.");
                return;
            }

            vistConfirmarCompra = new ConfirmarCompra(exposicionNombre, String.valueOf(nEntradas),
                    fecha.toString(), String.valueOf(hora) + ":00", numeroTarjetadeCredito, fechaTarj.toString(),
                    String.valueOf(cvv), String.valueOf(precioFinal));
            vistConfirmarCompra.setControlador(confirmarListener, atrasListener);

        }
    };

    /**
     * Método que devuelve el ActionListener del botón de cancelar
     * 
     * @return cancelarListener
     */
    public ActionListener getCancelarListener() {
        return cancelarListener;
    }

    /**
     * Método que inicializa el ActionListener del botón de cancelar
     */
    private ActionListener cancelarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
        }
    };

    /**
     * Mtodo que inicializa el ActionListener del botón de confirmar
     */
    private ActionListener confirmarListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (expofy.comprarEntrada(cliente, exposicion, fecha, exposicion.getHora(fecha, hora), nEntradas,
                    new TarjetaDeCredito(numeroTarjetadeCredito, fechaTarj, cvv), codigoSorteo)) {
                JOptionPane.showMessageDialog(vista,
                        "La compra se ha realizado con éxito, encontrará el pdf con su entrada en la carpeta tmp");
            } else {
                JOptionPane.showMessageDialog(vista, "Ha habido un error en la compra", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            vistConfirmarCompra.dispose();
            vista.dispose();
        }
    };

    /**
     * Método que inicializa el ActionListener del botón de atras
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vistConfirmarCompra.dispose();
        }
    };

    /**
     * Método que inicializa el ActionListener del botón del dia de la exposición
     */
    private ActionListener diaExpoListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            diaExpo = (Integer) diaExpoCombo.getSelectedItem();
        }
    };

    /**
     * Método que devuelve el ActionListener del botón del dia de la exposición
     * 
     * @return diaExpoListener
     */
    public ActionListener getdiaExpoListener() {
        return diaExpoListener;
    }

    /**
     * Método que inicializa el ActionListener del botón del mes de la exposición
     */
    private ActionListener mesExpoListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            mesExpo = (Integer) mesExpoCombo.getSelectedItem();
        }
    };

    /**
     * Método que devuelve el ActionListener del botón del mes de la exposición
     * 
     * @return mesExpoListener
     */
    public ActionListener getMesExpoListener() {
        return mesExpoListener;
    }

    /**
     * Método que inicializa el ActionListener del botón del año de la exposición
     */
    private ActionListener anioExpoListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            anioExpo = (Integer) anioExpoCombo.getSelectedItem();
        }
    };

    /**
     * Método que devuelve el ActionListener del botón del año de la exposición
     * 
     * @return anioExpoListener
     */
    public ActionListener getAnioExpoListener() {
        return anioExpoListener;
    }

    /**
     * Método que inicializa el ActionListener del botón del dia de la tarjeta
     */
    private ActionListener diaTarjListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            diaTarj = (Integer) diaTarjCombo.getSelectedItem();
        }
    };

    /**
     * Método que devuelve el ActionListener del botón del dia de la tarjeta
     * 
     * @return diaTarjListener
     */
    public ActionListener getdiaTarjListener() {
        return diaTarjListener;
    }

    /**
     * Método que inicializa el ActionListener del botón del mes de la tarjeta
     */
    private ActionListener mesTarjListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            mesTarj = (Integer) mesTarjCombo.getSelectedItem();
        }
    };

    /**
     * Método que devuelve el ActionListener del botón del mes de la tarjeta
     * 
     * @return mesTarjListener
     */
    public ActionListener getMesTarjListener() {
        return mesTarjListener;
    }

    /**
     * Método que inicializa el ActionListener del botón del año de la tarjeta
     */
    private ActionListener anioTarjListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            anioTarj = (Integer) anioTarjCombo.getSelectedItem();
        }
    };

    /**
     * Método que devuelve el ActionListener del botón del año de la tarjeta
     * 
     * @return anioTarjListener
     */
    public ActionListener getAnioTarjListener() {
        return anioTarjListener;
    }

    /**
     * Método que inicializa el ActionListener del botón del número de entradas
     */
    private ActionListener nEntradasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            nEntradas = (Integer) nEntradasCombo.getSelectedItem();
        }
    };

    /**
     * Método que devuelve el ActionListener del botón del número de entradas
     * 
     * @return nEntradasListener
     */
    public ActionListener getNentradasListener() {
        return nEntradasListener;
    }

    /**
     * Método que inicializa el ActionListener del botón de la hora
     */
    private ActionListener horaListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            hora = horaCombo.getSelectedIndex();
            hora += 9;
        }
    };

    /**
     * Método que devuelve el ActionListener del botón de la hora
     * 
     * @return horaListener
     */
    public ActionListener getHoraListener() {
        return horaListener;
    }

}