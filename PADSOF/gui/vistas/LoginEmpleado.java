package gui.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase LoginEmpleado.
 * Implementación de la interfaz gráfica de la vista de login de empleados.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class LoginEmpleado extends JPanel {
    private JTextField empleadoNumSS;
    private JPasswordField fieldPassword;
    private JButton atrasBoton;
    private JButton aceptarBoton;

    /**
     * Constructor de la clase LoginEmpleado.
     */
    public LoginEmpleado() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Componentes del formulario
        addTitle(constraints);
        addNSS(constraints);
        addPasswordField(constraints);
        addButtons(constraints);
    }

    /**
     * Método que añade el título a la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Login de empleados");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;

        add(titleLabel, constraints);

    }

    /**
     * Método que añade el campo de texto para el Nº de Seguridad Social.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addNSS(GridBagConstraints constraints) {
        JLabel labelUser = new JLabel("Nº Seguridad Social:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(labelUser, constraints);

        this.empleadoNumSS = new JTextField(30);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(empleadoNumSS, constraints);
    }

    /**
     * Método que añade el campo de texto para la contraseña.
     * 
     * @param constraints Constraints para el GridBagLayout.
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
     * Método que añade los botones de la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    public void addButtons(GridBagConstraints constraints) {
        aceptarBoton = new JButton("Aceptar");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(aceptarBoton, constraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        atrasBoton = new JButton("Atrás");

        buttonPanel.add(atrasBoton);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(buttonPanel, constraints);
    }

    /**
     * Método que devuelve el Nº de Seguridad Social introducido en el campo de
     * texto.
     * 
     * @return Nº de Seguridad Social introducido.
     */
    public String getNumSS() {
        return this.empleadoNumSS.getText();
    }

    /**
     * Método que devuelve la contraseña introducida en el campo de texto.
     * 
     * @return Contraseña introducida.
     */
    public String getPassword() {
        return new String(this.fieldPassword.getPassword());
    }

    /**
     * Método que establece los controladores de los botones de la vista.
     * 
     * @param cAceptar Controlador del botón de aceptar.
     * @param cAtras   Controlador del botón de atrás.
     */
    public void setControlador(ActionListener cAceptar, ActionListener cAtras) {
        aceptarBoton.addActionListener(cAceptar);
        atrasBoton.addActionListener(cAtras);
    }

    /**
     * Método que limpia los campos de texto de la vista.
     */
    public void update() {
        empleadoNumSS.setText("");
        fieldPassword.setText("");
    }

}