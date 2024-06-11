package gui.controlador;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.modelo.expofy.ClienteRegistrado;
import gui.modelo.expofy.Expofy;
import gui.modelo.exposicion.Exposicion;
import gui.vistas.DesbloqueoClientes;
import gui.vistas.Ventana;

import java.awt.event.ActionEvent;

/**
 * Clase ControladorDesbloqueoClientes
 * Controlador de la vista DesbloqueoClientes.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ControladorDesbloqueoClientes {
    private Ventana frame;
    private DesbloqueoClientes vista;
    private Expofy expofy;

    /**
     * Constructor de la clase ControladorDesbloqueoClientes.
     * 
     * @param frame  Ventana en la que se muestra la vista.
     * @param expofy expofy de exposición que se va a modificar.
     */
    public ControladorDesbloqueoClientes(Ventana frame, Expofy expofy) {
        this.frame = frame;
        this.frame.setCartaDesbloqueoClientes();
        this.expofy = expofy;
        this.vista = frame.getVistaDesbloqueoClientes();

        mostrarClientes();
    }

    /**
     * Método que muestra los sorteos en la vista.
     */
    public void mostrarClientes() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (ClienteRegistrado cliente : expofy.getClientesRegistrados()) {
            if (cliente.getBloqueada()) {
                data.add(new Object[] { cliente.getNIF()});
            }
            vista.addTablaClientes(data);
        }
    }

    /**
     * Devuelve un ActionListener que va a la panatalla anterior
     * 
     * @return ActionListener que va a la panatalla anterior
     */
    private ActionListener atrasListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.mostrarPanel(frame.getEmpleadoPrincipal());
        }
    };

    /**
     * Devuelve un ActionListener que ajusta la temperatura y humedad del expofy de
     * exposición.
     * 
     * @return ActionListener que ajusta la temperatura y humedad del expofy de
     *         exposición.
     */
    private ActionListener desbloquearListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = vista.getTablaClientes().getSelectedRow();
            if (selectedRow >= 0) {
                vista.getTablaClientes().clearSelection();
                String nifCliente = (String) vista.getTablaClientes().getValueAt(selectedRow, 0);
                ClienteRegistrado cliente = expofy.getClienteRegistrado(nifCliente);
                cliente.desbloquearCuenta();
                vista.removeAll();
                frame.setCartaDesbloqueoClientes();
                ControladorDesbloqueoClientes controladorDesbloqueoClientes = new ControladorDesbloqueoClientes(
                        frame,
                        expofy);
                frame.setControladorDesbloqueoClientes(controladorDesbloqueoClientes);
                frame.mostrarPanel(frame.getDesbloqueoClientes());
                JOptionPane.showMessageDialog(frame, "Se ha cambiado la configuración del cliente.");

            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona un cliente.");
            }
        }
    };


    /**
     * Devuelve un ActionListener que va a la panatalla anterior
     * 
     * @return ActionListener que va a la panatalla anterior
     */
    public ActionListener getAtrasListener() {
        return atrasListener;
    }

    /**
     * Devuelve un ActionListener que ajusta la temperatura y humedad del expofy de
     * exposición.
     * 
     * @return ActionListener que ajusta la temperatura y humedad del expofy de
     *         exposición.
     */
    public ActionListener getDesbloquearListener() {
        return desbloquearListener;
    }

}
