package gui.vistas;

import javax.swing.*;
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
    private JButton inscibirActividadBoton;
    private JPanel panelPrincipal;
    private JButton perfilBoton;
    private JButton cerrarSesionBoton;
    private JButton desbloquearBoton;

    /**
     * Constructor de la clase EmpleadoPrincipal.
     */
    public EmpleadoPrincipal() {
        setLayout(new BorderLayout());
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        cerrarSesionBoton = new JButton("Cerrar Sesión");
        perfilBoton = new JButton("Perfil");
        panelSuperior.add(perfilBoton, BorderLayout.WEST);
        panelSuperior.add(cerrarSesionBoton, BorderLayout.EAST);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        addButtons(constraints);
        add(panelPrincipal, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);
    }

    /**
     * Método que añade el título a la vista.
     * 
     */
    public void addTitle() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Bienvenid@ al panel de empleado, elige una operación a realizar:");
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
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); //
        this.enviarMensajeBoton = new JButton("Envío de mensajes");
        this.cambiarClimatizacionBoton = new JButton("Cambiar Climatización");
        this.venderEntradaBoton = new JButton("Venta de entradas");
        this.desbloquearBoton = new JButton("Clientes bloqueados");
        this.inscibirActividadBoton = new JButton("Inscribir actividad");

        buttonPanel.add(this.enviarMensajeBoton);
        buttonPanel.add(this.cambiarClimatizacionBoton);
        buttonPanel.add(this.venderEntradaBoton);
        buttonPanel.add(this.inscibirActividadBoton);
        buttonPanel.add(this.desbloquearBoton);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(buttonPanel, constraints);
    }

    /**
     * Método que establece los controladores de la vista.
     *
     * @param cMensaje       Controlador del botón de envío de mensajes.
     * @param cClimatizacion Controlador del botón de cambio de climatización.
     * @param cVentaEntradas Controlador del botón de venta de entradas.
     * @param cCerrarSesion  Controlador del botón de cierre de sesión.
     * @param cDesbloquear   Controlador del botón de desbloqueo de cuentas.
     * @param cActividad     Controlador del botón de inscribir en actividad.
     * @param cPerfil        Controlador del botón de perfil.
     */
    public void setControlador(ActionListener cMensaje, ActionListener cClimatizacion, ActionListener cVentaEntradas,
            ActionListener cCerrarSesion, ActionListener cDesbloquear, ActionListener cActividad,
            ActionListener cPerfil) {

        this.enviarMensajeBoton.addActionListener(cMensaje);
        this.cambiarClimatizacionBoton.addActionListener(cClimatizacion);
        this.venderEntradaBoton.addActionListener(cVentaEntradas);
        this.cerrarSesionBoton.addActionListener(cCerrarSesion);
        this.inscibirActividadBoton.addActionListener(cActividad);
        this.desbloquearBoton.addActionListener(cDesbloquear);
        this.perfilBoton.addActionListener(cPerfil);
    }

    /**
     * Método que borra los controladores de la vista.
     *
     * @param cMensaje       Controlador del botón de envío de mensajes.
     * @param cClimatizacion Controlador del botón de cambio de climatización.
     * @param cVentaEntradas Controlador del botón de venta de entradas.
     * @param cCerrarSesion  Controlador del botón de cierre de sesión.
     * @param cDesbloquear   Controlador del botón de desbloqueo de cuentas.
     * @param cActividad     Controlador del botón de inscribir en actividad.
     * @param cPerfil        Controlador del botón de perfil.
     */
    public void removeControlador(ActionListener cMensaje, ActionListener cClimatizacion, ActionListener cVentaEntradas,
            ActionListener cCerrarSesion, ActionListener cDesbloquear, ActionListener cActividad,
            ActionListener cPerfil) {

        this.enviarMensajeBoton.removeActionListener(cMensaje);
        this.cambiarClimatizacionBoton.removeActionListener(cClimatizacion);
        this.venderEntradaBoton.removeActionListener(cVentaEntradas);
        this.cerrarSesionBoton.removeActionListener(cCerrarSesion);
        this.desbloquearBoton.removeActionListener(cDesbloquear);
        this.inscibirActividadBoton.removeActionListener(cActividad);
        this.perfilBoton.removeActionListener(cPerfil);
    }

    /**
     * Método que oculta las funcionalidades según los permisos.
     *
     * @param msj permiso mensaje.
     * @param clmtz permiso climatización.
     * @param venta permiso venta.
     * @param actividad  permiso inscribir en actividad.
     */
    public void hideButtons(boolean msj, boolean clmtz, boolean venta, boolean actividad) {
        enviarMensajeBoton.setVisible(msj);
        cambiarClimatizacionBoton.setVisible(clmtz);
        venderEntradaBoton.setVisible(venta);
        inscibirActividadBoton.setVisible(actividad);
    }
}