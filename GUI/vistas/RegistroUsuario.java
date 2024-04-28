package GUI.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegistroUsuario extends JPanel {
    private JTextField fieldUsuario;
    private JPasswordField fieldContrasena;
    private JCheckBox chkPublicidad;
    private JButton registrarBtn; 
    private JButton cancelarBtn;

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

    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Registro de Usuario");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleLabel, constraints);
    }

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

    private void addPublicidadCheckBox(GridBagConstraints constraints) {
        chkPublicidad = new JCheckBox("Deseo recibir publicidad");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(chkPublicidad, constraints);
    }

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

    public void setControlador(ActionListener cRegistrar, ActionListener cCancelar) {
        this.registrarBtn.addActionListener(cRegistrar);
        this.cancelarBtn.addActionListener(cCancelar);
    }

    public String getUsuario() {
        return fieldUsuario.getText();
    }

    public String getPassword() {
        return new String(fieldContrasena.getPassword());
    }

    public boolean getPublicidad() {
        return chkPublicidad.isSelected();
    }

    public void update() {
        fieldUsuario.setText("");
        fieldContrasena.setText("");
        chkPublicidad.setSelected(false);
    }
}
