package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SalaFormulario extends JDialog {

    private JTextField nombre;
    private JTextField aforo;
    private JTextField humedad;
    private JTextField temperatura;
    private JCheckBox climatizador;
    private JTextField tomasElectricidad;
    private JTextField ancho;
    private JTextField largo;
    private JButton aceptarBtn;
    private JButton cancelarBtn;

    public SalaFormulario(String accion) {
        setTitle("Agregar sala");
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
        }

        addBotones(panelFormulario, constraints, 8);
        add(panelFormulario);
    }

    public void formularioAñadirSala(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Nombre:", nombre = new JTextField(20), panelFormulario, constraints, 0);
        addCampo("Aforo:", aforo = new JTextField(20), panelFormulario, constraints, 1);
        addCampo("Humedad:", humedad = new JTextField(20), panelFormulario, constraints, 2);
        addCampo("Temperatura:", temperatura = new JTextField(20), panelFormulario, constraints, 3);
        addCampo("Climatizador:", climatizador = new JCheckBox(), panelFormulario, constraints, 4);
        addCampo("Tomas de electricidad:", tomasElectricidad = new JTextField(20), panelFormulario, constraints, 5);
        addCampo("Ancho:", ancho = new JTextField(20), panelFormulario, constraints, 6);
        addCampo("Largo:", largo = new JTextField(20), panelFormulario, constraints, 7);
    }

    public void formularioAñadirSubsala(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Aforo: ", aforo = new JTextField(20), panelFormulario, constraints, 0);
        addCampo("Tomas de electricidad: ", tomasElectricidad = new JTextField(20), panelFormulario, constraints, 1);
        addCampo("Ancho: ", ancho = new JTextField(20), panelFormulario, constraints, 2);
        addCampo("Largo: ", largo = new JTextField(20), panelFormulario, constraints, 3);
    }
    
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

    private void addBotones(JPanel panel, GridBagConstraints constraints, int gridy) {
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 2;
        this.aceptarBtn = new JButton("Guardar");
        this.cancelarBtn = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);

        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, constraints);
    }

    public String getNombre() {
        return nombre.getText();
    }

    public Integer getAforo() {
        return Integer.parseInt(aforo.getText());
    }

    public Integer getHumedad() {
        return Integer.parseInt(humedad.getText());
    }

    public Integer getTemperatura() {
        return Integer.parseInt(temperatura.getText());
    }

    public boolean getClimatizador() {
        return climatizador.isSelected();
    }

    public Integer getTomasElectricidad() {
        return Integer.parseInt(tomasElectricidad.getText());
    }

    public Double getAncho() {
        return Double.parseDouble(ancho.getText());
    }

    public Double getLargo() {
        return Double.parseDouble(largo.getText());
    }

    public void setControlador(ActionListener cAceptar, ActionListener cCancelar) {
        aceptarBtn.addActionListener(cAceptar);
        cancelarBtn.addActionListener(cCancelar);
        setVisible(true);
    }

}
