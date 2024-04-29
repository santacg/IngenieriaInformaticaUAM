package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EnviarMensajes extends JPanel {
    private JTextField mensaje;
    private JTextField destinatario;
    private JButton atrasBoton;
    private JButton enviarBoton;

    public EnviarMensajes() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Componentes del formulario
        addTitle(constraints);
        addDestinatario(constraints);
        addMensaje(constraints);
        addButtons(constraints);
    }
    
    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Envío de mensajes");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;

        add(titleLabel, constraints);
    }

    private void addDestinatario(GridBagConstraints constraints) {
        JLabel labelDestinatario = new JLabel("Destinatario:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(labelDestinatario, constraints);

        this.destinatario = new JTextField(30);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(destinatario, constraints);
    }
    private void addMensaje(GridBagConstraints constraints) {
        JLabel labelMensaje = new JLabel("Mensaje:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(labelMensaje, constraints);

        this.mensaje = new JTextField(30);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(mensaje, constraints);
    }

    private void addButtons(GridBagConstraints constraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.atrasBoton = new JButton("Atrás");
        this.enviarBoton = new JButton("Enviar");

        buttonPanel.add(this.atrasBoton);
        buttonPanel.add(this.enviarBoton);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(buttonPanel, constraints);
    }

    public void setControlador(ActionListener cEnviar, ActionListener cAtras) {
        this.atrasBoton.addActionListener(cAtras);
        this.enviarBoton.addActionListener(cEnviar);
    }

    public String getMensaje() {
        return this.mensaje.getText();
    }

    public String getDestinatario() {
        return this.destinatario.getText();
    }
    public void update() {
        this.mensaje.setText("");
        this.destinatario.setText("");
    }
}
