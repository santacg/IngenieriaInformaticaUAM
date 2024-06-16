package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Clase SalaFormulario
 * Implementa la interfaz gráfica de un formulario para añadir o eliminar salas
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class SalaFormulario extends JDialog {

    private JTextField nombre;
    private JTextField aforo;
    private JCheckBox climatizador;
    private JTextField tomasElectricidad;
    private JTextField ancho;
    private JTextField largo;
    private JTextField alto;
    private JButton aceptarBtn;
    private JButton cancelarBtn;

    /**
     * Constructor de la clase SalaFormulario
     * 
     * @param accion String que indica la acción a realizar
     */
    public SalaFormulario(String accion) {
        setSize(600, 700);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        switch (accion) {
            case "Añadir Sala":
                formularioAñadirSala(panelFormulario, constraints);
                break;
            case "Añadir Subsala":
                formularioAñadirSubsala(panelFormulario, constraints);
                break;
            case "Eliminar Sala":
                confirmarEliminacion(panelFormulario, constraints);
                break;
            case "Añadir Sala a Exposicion":
                confirmarAdicion(panelFormulario, constraints);
                break;
        }

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
    public void formularioAñadirSala(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Nombre:", nombre = new JTextField(20), panelFormulario, constraints, 0);
        addCampo("Aforo:", aforo = new JTextField(20), panelFormulario, constraints, 1);
        addCampo("Climatizador:", climatizador = new JCheckBox(), panelFormulario, constraints, 4);
        addCampo("Tomas de electricidad:", tomasElectricidad = new JTextField(20), panelFormulario, constraints, 5);
        addCampo("Ancho:", ancho = new JTextField(20), panelFormulario, constraints, 6);
        addCampo("Largo:", largo = new JTextField(20), panelFormulario, constraints, 7);
        addCampo("Alto:", alto = new JTextField(20), panelFormulario, constraints, 8);
    }

    /**
     * Método que crea el formulario para añadir una subsala
     * 
     * @param panelFormulario JPanel en el que se añadirán los campos
     * @param constraints     GridBagConstraints que definen la posición de los
     *                        campos
     */
    public void formularioAñadirSubsala(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Aforo: ", aforo = new JTextField(20), panelFormulario, constraints, 0);
        addCampo("Tomas de electricidad: ", tomasElectricidad = new JTextField(20), panelFormulario, constraints, 1);
        addCampo("Ancho: ", ancho = new JTextField(20), panelFormulario, constraints, 2);
        addCampo("Largo: ", largo = new JTextField(20), panelFormulario, constraints, 3);
    }

    /**
     * Método que crea el formulario para confirmar la eliminación de una sala
     * 
     * @param panelFormulario JPanel en el que se añadirán los campos
     * @param constraints     GridBagConstraints que definen la posición de los
     *                        campos
     */
    public void confirmarEliminacion(JPanel panelFormulario, GridBagConstraints constraints) {
        panelFormulario.add(new JLabel("¿Estás seguro de que quieres eliminar esta sala?"), constraints);
    }

    /**
     * Método que crea el formulario para confirmar la adición de una sala a una
     * 
     * @param panelFormulario JPanel en el que se añadirán los campos
     * @param constraints     GridBagConstraints que definen la posición de los
     *                        campos
     */
    public void confirmarAdicion(JPanel panelFormulario, GridBagConstraints constraints) {
        panelFormulario.add(
                new JLabel(
                        "Tienes que elegir una de las siguientes exposiciones a las que añadir la sala seleccionada"),
                constraints);
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
    public String getNombre() {
        return nombre.getText();
    }

    /**
     * Método que devuelve el aforo introducido en el formulario
     * 
     * @return Integer con el aforo introducido o null
     */
    public Integer getAforo() {
        if (aforo.getText().equals("")) {
            return null;
        }

        try {
            return Integer.parseInt(aforo.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Método que devuelve si el climatizador está seleccionado en el formulario
     * 
     * @return Boolean que indica si el climatizador está seleccionado
     */
    public boolean getClimatizador() {
        return climatizador.isSelected();
    }

    /**
     * Método que devuelve las tomas de electricidad introducidas en el formulario
     * 
     * @return Integer con las tomas de electricidad introducidas o null
     */
    public Integer getTomasElectricidad() {
        if (tomasElectricidad.getText().equals("")) {
            return null;
        }

        try {
            return Integer.parseInt(tomasElectricidad.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Método que devuelve el ancho introducido en el formulario
     * 
     * @return Double con el ancho introducido o null
     */
    public Double getAncho() {
        if (ancho.getText().equals("")) {
            return null;
        }

        try {
            return Double.parseDouble(ancho.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Método que devuelve el largo introducido en el formulario
     * 
     * @return Double con el largo introducido o null
     */
    public Double getLargo() {
        if (largo.getText().equals("")) {
            return null;
        }

        try {
            return Double.parseDouble(largo.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Método que devuelve el alto introducido en el formulario
     * 
     * @return Double con el alto introducido o null
     */
    public Double getAlto() {
        if (alto.getText().equals("")) {
            return null;
        }

        try {
            return Double.parseDouble(alto.getText());
        } catch (NumberFormatException e) {
            return null;
        }
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
