package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VentaFormulario extends JDialog {

    private JLabel exposicionNombre;
    private JLabel precioPorEntrada;
    private JComboBox<Integer> nEntradas = new JComboBox<>();

    private JComboBox<String> hora = new JComboBox<>();

    private JButton siguienteBtn;
    private JButton cancelarBtn;

    public VentaFormulario(String nombreExposicion, double precio) {
        setTitle("Comprar entrada");
        setSize(700, 600);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        String[] horas = { "9:00", "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "18:00", "19:00", "20:00" };
        for (String h : horas) {
            hora.addItem(h);
        }
        hora.setSelectedIndex(0);

        for (Integer entrada = 1; entrada <= 25; entrada++) {
            nEntradas.addItem(entrada);
        }
        nEntradas.setSelectedIndex(0);

        addCampo("Exposición:", exposicionNombre = new JLabel(nombreExposicion), panelFormulario, constraints, 0, 1);
        addCampo("Precio:", precioPorEntrada = new JLabel(String.valueOf(precio)), panelFormulario, constraints, 1, 1);
        addCampo("Nº de entradas:", nEntradas, panelFormulario, constraints, 2, 1);
        addCampo("Hora:", hora, panelFormulario, constraints, 3, 1);

        Integer gridy = 11;

        addBotones(panelFormulario, constraints, gridy);
        add(panelFormulario);
    }

    private void addCampo(String label, Component comp, JPanel panel, GridBagConstraints constraints, Integer gridy,
            Integer gridwidth) {
        JLabel jlabel = new JLabel(label);
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        panel.add(jlabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = gridy;
        panel.add(comp, constraints);
    }

    private void addBotones(JPanel panel, GridBagConstraints constraints, Integer gridy) {
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 2;
        this.siguienteBtn = new JButton("Siguiente");
        this.cancelarBtn = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(siguienteBtn);
        buttonPanel.add(cancelarBtn);

        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, constraints);
    }

    public String getExposicionNombre() {
        return exposicionNombre.getText();
    }

    public String getPrecio() {
        return precioPorEntrada.getText();
    }

    public JComboBox<Integer> getNentradas() {
        return nEntradas;
    }

    public JComboBox<String> getHora() {
        return hora;
    }

    public void setControlador(ActionListener cSiguiente, ActionListener cCancelar, ActionListener cNentradas,
            ActionListener cHoras) {
        this.siguienteBtn.addActionListener(cSiguiente);
        this.cancelarBtn.addActionListener(cCancelar);
        this.nEntradas.addActionListener(cNentradas);
        this.hora.addActionListener(cHoras);
        setVisible(true);
    }

}