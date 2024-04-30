package GUI.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase PantallaPrincipal.
 * Implementa la vista de la pantalla principal de la aplicación.
 *  
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class PantallaPrincipal extends JPanel {
    private JButton aceptarBoton;
    private JButton buscarBoton;
    private JButton gestorBoton;
    private JButton empleadoBoton;
    private JButton registrarBoton;
    private JTextField textUser;
    private JPasswordField fieldPassword;

    /**
     * Construye la vista de la pantalla principal de la aplicación.
     */
    public PantallaPrincipal() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        addTitle(constraints);
        addUserFields(constraints);
        addPasswordField(constraints);
        addButtons(constraints);
    }

    /**
     * Añade el título de la pantalla principal.
     * 
     * @param constraints Restricciones de la cuadrícula.
     */
    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Expofy");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleLabel, constraints);
    }

    /**
     * Añade los campos de texto para el usuario.
     * 
     * @param constraints Restricciones de la cuadrícula.
     */
    private void addUserFields(GridBagConstraints constraints) {
        JLabel labelUser = new JLabel("Usuario:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(labelUser, constraints);

        this.textUser = new JTextField(30);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(textUser, constraints);
    }

    /**
     * Añade el campo de texto para la contraseña.
     * 
     * @param constraints Restricciones de la cuadrícula.
     */
    private void addPasswordField(GridBagConstraints constraints) {
        JLabel labelPassword = new JLabel("Contraseña:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(labelPassword, constraints);

        this.fieldPassword = new JPasswordField(30);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(fieldPassword, constraints);
    }
    
    /**
     * Añade los botones de la pantalla principal.
     * 
     * @param constraints Restricciones de la cuadrícula.
     */
    public void addButtons(GridBagConstraints constraints) {
        this.aceptarBoton = new JButton("Aceptar");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(aceptarBoton, constraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.buscarBoton = new JButton("Buscar exposicion");
        this.gestorBoton = new JButton("Gestor");
        this.empleadoBoton = new JButton("Empleado");
        this.registrarBoton = new JButton("Registrarse");

        buttonPanel.add(this.buscarBoton);
        buttonPanel.add(this.gestorBoton);
        buttonPanel.add(this.empleadoBoton);
        buttonPanel.add(this.registrarBoton);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(buttonPanel, constraints);
    }

    /**
     * Establece los controladores de los botones de la pantalla principal.
     * 
     * @param cBuscar Controlador del botón de buscar exposición.
     * @param cAceptar Controlador del botón de aceptar.
     * @param cGestor Controlador del botón de gestor.
     * @param cEmpleado Controlador del botón de empleado.
     * @param cRegistrar Controlador del botón de registrarse.
     */
    public void setControlador(ActionListener cBuscar, ActionListener cAceptar, ActionListener cGestor,
            ActionListener cEmpleado, ActionListener cRegistrar) {
        this.buscarBoton.addActionListener(cBuscar);
        this.aceptarBoton.addActionListener(cAceptar);
        this.gestorBoton.addActionListener(cGestor);
        this.empleadoBoton.addActionListener(cEmpleado);
        this.registrarBoton.addActionListener(cRegistrar);
    }

    /**
     * Devuelve el usuario introducido en el campo de texto.
     * 
     * @return Usuario introducido.
     */
    public String getUsuario() {
        return this.textUser.getText();
    }

    /**
     * Devuelve la contraseña introducida en el campo de texto.
     * 
     * @return Contraseña introducida.
     */
    public String getPassword() {
        return new String(this.fieldPassword.getPassword());
    }

    /**
     * Limpia los campos de texto de la pantalla principal.
     */
    public void update() {
        this.textUser.setText("");
        this.fieldPassword.setText("");
    }  
}
