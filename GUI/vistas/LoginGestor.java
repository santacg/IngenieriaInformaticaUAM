package GUI.vistas;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class LoginGestor extends JPanel {
    private JPasswordField fieldPassword;
    private JButton aceptarBtn;
    private JButton atrasBtn;

    public LoginGestor() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Componentes del formulario
        addTitle(constraints);
        addPasswordField(constraints);
        addButtons(constraints);

    }

    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Login Gestor");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;
        add(titleLabel, constraints);

    }

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


    public void addButtons(GridBagConstraints constraints) {
        this.aceptarBtn = new JButton("Aceptar");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(aceptarBtn, constraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.atrasBtn = new JButton("Atrás");

        buttonPanel.add(atrasBtn);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(buttonPanel, constraints);
    }


    public void setControlador(ActionListener cAceptar, ActionListener cAtras) {
        this.aceptarBtn.addActionListener(cAceptar);
        this.atrasBtn.addActionListener(cAtras);
    }

    public String getPassword() {
        return new String(this.fieldPassword.getPassword());
    }

    public void update() {
        this.fieldPassword.setText("");
    }
}