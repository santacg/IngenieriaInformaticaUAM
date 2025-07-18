package gui.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase RegistroUsuario.
 * Actúa como la vista de registro de usuario.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class RegistroUsuario extends JPanel {
    private JTextField fieldUsuario;
    private JPasswordField fieldContrasena;
    private JCheckBox chkPublicidad;
    private JButton registrarBtn;
    private JButton cancelarBtn;

    /**
     * Constructor de la clase RegistroUsuario.
     */
    public RegistroUsuario() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Componentes del formulario
        addTitle(constraints);
        addUsuarioField(constraints);
        addPasswordField(constraints);
        addPublicidadCheckBox(constraints);
        addButtons(constraints);
    }

    /**
     * Añade el título de la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Registro de Usuario");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleLabel, constraints);
    }

    /**
     * Añade el campo de texto para el usuario.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addUsuarioField(GridBagConstraints constraints) {
        JLabel labelUsuario = new JLabel("Usuario:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(labelUsuario, constraints);

        fieldUsuario = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(fieldUsuario, constraints);
    }

    /**
     * Añade el campo de texto para la contraseña.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addPasswordField(GridBagConstraints constraints) {
        JLabel labelPassword = new JLabel("Contraseña:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(labelPassword, constraints);

        fieldContrasena = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(fieldContrasena, constraints);
    }

    /**
     * Añade el checkbox para cambiar el estado de la publicidad.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addPublicidadCheckBox(GridBagConstraints constraints) {
        chkPublicidad = new JCheckBox("Deseo recibir publicidad");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(chkPublicidad, constraints);
    }

    /**
     * Añade los botones de la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addButtons(GridBagConstraints constraints) {
        this.registrarBtn = new JButton("Registrarse");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        add(registrarBtn, constraints);

        this.cancelarBtn = new JButton("Cancelar");
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        add(cancelarBtn, constraints);
    }

    /**
     * Añade los controladores a los botones.
     * 
     * @param cRegistrar Controlador del botón de registro.
     * @param cCancelar  Controlador del botón de cancelar.
     */
    public void setControlador(ActionListener cRegistrar, ActionListener cCancelar) {
        this.registrarBtn.addActionListener(cRegistrar);
        this.cancelarBtn.addActionListener(cCancelar);
    }

    /**
     * Devuelve el usuario introducido en el campo de texto.
     * 
     * @return Usuario introducido.
     */
    public String getUsuario() {
        return fieldUsuario.getText();
    }

    /**
     * Devuelve la contraseña introducida en el campo de texto.
     * 
     * @return Contraseña introducida.
     */
    public String getPassword() {
        return new String(fieldContrasena.getPassword());
    }

    /**
     * Devuelve si el usuario ha marcado la casilla de publicidad.
     * 
     * @return True si la casilla está marcada, false en caso contrario.
     */
    public boolean getPublicidad() {
        return chkPublicidad.isSelected();
    }

    /**
     * Limpia los campos de texto y la casilla de publicidad.
     */
    public void update() {
        fieldUsuario.setText("");
        fieldContrasena.setText("");
        chkPublicidad.setSelected(false);
    }
}
