package gui.vistas;

import javax.swing.*;

import gui.controlador.ControladorCompraFormulario;
import gui.modelo.centroExposicion.Empleado;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase EmpleadoPrincipal.
 * Implementa la interfaz de la vista principal de un empleado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class EmpleadoPrincipal extends JPanel {
    private JButton enviarMensajeBoton;
    private JButton cambiarClimatizacionBoton;
    private JButton venderEntradaBoton;
    private JPanel panelPrincipal;
    private JButton cerrarSesionBoton;
    private JButton desbloquearBoton;

    /**
     * Constructor de la clase EmpleadoPrincipal.
     */
    public EmpleadoPrincipal() {
        setLayout(new BorderLayout());
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());

        JPanel panelCerrarSesion = new JPanel();
        panelCerrarSesion.setLayout(new BorderLayout());
        cerrarSesionBoton = new JButton("Cerrar Sesión");
        panelCerrarSesion.add(cerrarSesionBoton, BorderLayout.EAST);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Componentes del formulario
        addButtons(constraints);
        // addTitle(constraints);
        add(panelPrincipal, BorderLayout.CENTER);
        add(panelCerrarSesion, BorderLayout.NORTH);
    }

    /**
     * Método que añade el título a la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    public void addTitle(String name) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Bienvenido, " + name);
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(titleLabel, constraints);
    }

    /**
     * Método que añade los botones a la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    public void addButtons(GridBagConstraints constraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.enviarMensajeBoton = new JButton("Envío de mensajes");
        this.cambiarClimatizacionBoton = new JButton("Cambiar Climatización");
        this.venderEntradaBoton = new JButton("Venta de entradas");
        this.desbloquearBoton = new JButton("Clientes bloqueados");

        buttonPanel.add(this.enviarMensajeBoton);
        buttonPanel.add(this.cambiarClimatizacionBoton);
        buttonPanel.add(this.venderEntradaBoton);
        buttonPanel.add(this.desbloquearBoton);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        panelPrincipal.add(buttonPanel, constraints);
    }

    /**
     * Método que establece los controladores de la vista.
     *
     * @param cMensaje       Controlador del botón de envío de mensajes.
     * @param cClimatizacion Controlador del botón de cambio de climatización.
     * @param cVentaEntradas Controlador del botón de venta de entradas.
     * @param cCerrarSesion  Controlador del botón de cierre de sesión.
     */
    public void setControlador(ActionListener cMensaje, ActionListener cClimatizacion, ActionListener cVentaEntradas,
            ActionListener cCerrarSesion, ActionListener cDesbloquear) {

        this.enviarMensajeBoton.addActionListener(cMensaje);
        this.cambiarClimatizacionBoton.addActionListener(cClimatizacion);
        this.venderEntradaBoton.addActionListener(cVentaEntradas);
        this.cerrarSesionBoton.addActionListener(cCerrarSesion);
        this.desbloquearBoton.addActionListener(cDesbloquear);
    }

    /**
     * Método que borra los controladores de la vista.
     *
     * @param cMensaje       Controlador del botón de envío de mensajes.
     * @param cClimatizacion Controlador del botón de cambio de climatización.
     * @param cVentaEntradas Controlador del botón de venta de entradas.
     * @param cCerrarSesion  Controlador del botón de cierre de sesión.
     */
    public void removeControlador(ActionListener cMensaje, ActionListener cClimatizacion, ActionListener cVentaEntradas,
            ActionListener cCerrarSesion) {

        this.enviarMensajeBoton.removeActionListener(cMensaje);
        this.cambiarClimatizacionBoton.removeActionListener(cClimatizacion);
        this.venderEntradaBoton.removeActionListener(cVentaEntradas);
        this.cerrarSesionBoton.removeActionListener(cCerrarSesion);
    }

    public void hideButtons(boolean msj, boolean clmtz, boolean venta) {
        enviarMensajeBoton.setVisible(msj);
        cambiarClimatizacionBoton.setVisible(clmtz);
        venderEntradaBoton.setVisible(venta);
    }
}