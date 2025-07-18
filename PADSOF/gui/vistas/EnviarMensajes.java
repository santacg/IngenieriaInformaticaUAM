package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Clase EnviarMensajes.
 * Implementa la interfaz de la vista principal de un empleado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class EnviarMensajes extends JPanel {
    private JTextField mensaje;
    private JTextField destinatario;
    private JButton atrasBoton;
    private JButton enviarBoton;

    /**
     * Constructor de la clase EnviarMensajes.
     */
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

    /**
     * Añade el título de la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Envío de mensajes");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;

        add(titleLabel, constraints);
    }

    /**
     * Añade el campo de texto para el destinatario.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addDestinatario(GridBagConstraints constraints) {
        JLabel labelDestinatario = new JLabel("NIF del destinatario:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(labelDestinatario, constraints);

        JLabel labelTodos = new JLabel(
                "(Vacío para enviar a todos los clientes que permitan publicidad o NIFs separados por espacios para enviar a varios clientes)");
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        add(labelTodos, constraints);

        this.destinatario = new JTextField(30);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(destinatario, constraints);
    }

    /**
     * Añade el campo de texto para el mensaje.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addMensaje(GridBagConstraints constraints) {
        JLabel labelMensaje = new JLabel("Mensaje:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(labelMensaje, constraints);

        this.mensaje = new JTextField(30);
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(mensaje, constraints);
    }

    /**
     * Añade los botones de la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addButtons(GridBagConstraints constraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.atrasBoton = new JButton("Atrás");
        this.enviarBoton = new JButton("Enviar");

        buttonPanel.add(this.enviarBoton);
        buttonPanel.add(this.atrasBoton);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(buttonPanel, constraints);
    }

    /**
     * Establece los controladores de los botones.
     * 
     * @param cEnviar Controlador del botón de enviar.
     * @param cAtras  Controlador del botón de atrás.
     */
    public void setControlador(ActionListener cEnviar, ActionListener cAtras) {
        this.enviarBoton.addActionListener(cEnviar);
        this.atrasBoton.addActionListener(cAtras);
    }

    /**
     * Devuelve el mensaje introducido por el usuario.
     * 
     * @return Mensaje introducido por el usuario.
     */
    public String getMensaje() {
        return this.mensaje.getText();
    }

    /**
     * Devuelve el destinatario introducido por el usuario.
     * 
     * @return Destinatario introducido por el usuario.
     */
    public String getDestinatario() {
        return this.destinatario.getText();
    }

    /**
     * Actualiza y limpia los campos de texto de la vista.
     */
    public void update() {
        mensaje.setText("");
        destinatario.setText("");
    }
}
