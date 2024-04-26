package GUI.vistas;

import javax.swing.*;
import java.awt.*;

public class PantallaPrincipal extends JPanel {
    public PantallaPrincipal() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        addTitle(constraints);
        addUserFields(constraints);
        addPasswordField(constraints);
        addAcceptButton(constraints);
        addButtons(constraints);
    }

    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Expofy");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleLabel, constraints);
    }

    private void addUserFields(GridBagConstraints constraints) {
        JLabel labelUser = new JLabel("Usuario:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(labelUser, constraints);

        JTextField textUser = new JTextField(30);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(textUser, constraints);
    }

    private void addPasswordField(GridBagConstraints constraints) {
        JLabel labelPassword = new JLabel("Contraseña:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(labelPassword, constraints);

        JPasswordField fieldPassword = new JPasswordField(30);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(fieldPassword, constraints);
    }

    private void addAcceptButton(GridBagConstraints constraints) {
        JButton acceptButton = new JButton("Aceptar");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(acceptButton, constraints);
    }

    public void addButtons(GridBagConstraints constraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton buscarBoton = new JButton("Buscar exposicion");
        JButton gestorBoton = new JButton("Gestor");
        JButton empleadoBoton = new JButton("Empleado");
        JButton registrarBoton = new JButton("Registrarse");

        buttonPanel.add(buscarBoton);
        buttonPanel.add(gestorBoton);
        buttonPanel.add(empleadoBoton);
        buttonPanel.add(registrarBoton);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(buttonPanel, constraints);
    }

}
