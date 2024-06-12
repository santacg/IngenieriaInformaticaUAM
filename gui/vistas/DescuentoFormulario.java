package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DescuentoFormulario extends JDialog{
    private JTextField fechaSorteo;
    private JTextField n_entradas;
    private JButton aceptarBtn;
    private JButton cancelarBtn;

    /**
     * Constructor de la clase SalaFormulario
     * 
     * @param accion String que indica la acción a realizar
     */
    public DescuentoFormulario() {
        setSize(600, 700);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        addBotones(panelFormulario, constraints, 8);
        add(panelFormulario);
    }

    /**
     * Método que crea el formulario para añadir una sala
     * 
     * @param panelFormulario JPanel en el que se añadirán los campos
     * @param constraints     GridBagConstraints que definen la posición de los
     *                        campos
     */
    public void formularioAñadirSorteo(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Fecha del sorteo:", fechaSorteo = new JTextField(20), panelFormulario, constraints, 0);
        addCampo("Numero de entradas:", n_entradas = new JTextField(20), panelFormulario, constraints, 7);
    }

    /**
     * Método que añade un campo al formulario
     * 
     * @param label       String que indica el nombre del campo
     * @param comp        Component que se añadirá al formulario
     * @param panel       JPanel en el que se añadirá el campo
     * @param constraints GridBagConstraints que definen la posición del campo
     * @param gridy       int que indica la fila en la que se añadirá el campo
     */
    private void addCampo(String label, Component comp, JPanel panel, GridBagConstraints constraints, int gridy) {
        JLabel jlabel = new JLabel(label);
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 1;
        panel.add(jlabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = gridy;
        panel.add(comp, constraints);
    }

    /**
     * Método que añade los botones de aceptar y cancelar al formulario
     * 
     * @param panel       JPanel en el que se añadirán los botones
     * @param constraints GridBagConstraints que definen la posición de los botones
     * @param gridy       int que indica la fila en la que se añadirán los botones
     */
    private void addBotones(JPanel panel, GridBagConstraints constraints, int gridy) {
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 2;
        this.aceptarBtn = new JButton("Aceptar");
        this.cancelarBtn = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);

        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, constraints);
    }

    /**
     * Método que devuelve el nombre introducido en el formulario
     * 
     * @return String con el nombre introducido
     */
    public String getFechaSorteo() {
        return fechaSorteo.getText();
    }

    /**
     * Método que devuelve el aforo introducido en el formulario
     * 
     * @return String con el aforo introducido
     */
    public String getSorteoEntradas() {
        return n_entradas.getText();
    }

    /**
     * Método que establece los controladores de los botones del formulario
     * 
     * @param cAceptar  ActionListener que se añadirá al botón de aceptar
     * @param cCancelar ActionListener que se añadirá al botón de cancelar
     */
    public void setControlador(ActionListener cAceptar, ActionListener cCancelar) {
        aceptarBtn.addActionListener(cAceptar);
        cancelarBtn.addActionListener(cCancelar);
        setVisible(true);
    }


}
