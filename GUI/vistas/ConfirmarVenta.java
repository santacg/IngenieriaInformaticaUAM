package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;

public class ConfirmarVenta extends JDialog {

    private JLabel exposicionNombre;
    private JLabel nEntradas;
    private JLabel fechaExpo;
    private JLabel hora;

    private JLabel precioFinal;

    private JButton confirmarBtn;
    private JButton atrasBtn;

    public ConfirmarVenta(String exposicionNombre, String nEntradas, String fechaExpo, String hora,
            String precioFinal) {
        setTitle("Comprar entrada");
        setSize(700, 600);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        addCampo("Exposición:", this.exposicionNombre = new JLabel(exposicionNombre), panelFormulario, constraints, 0,
                1);
        addCampo("Nº de entradas:", this.nEntradas = new JLabel(nEntradas), panelFormulario, constraints, 1,
                1);
        addCampo("Fecha:", this.fechaExpo = new JLabel(fechaExpo), panelFormulario, constraints, 2, 1);
        addCampo("Hora:", this.hora = new JLabel(hora), panelFormulario, constraints, 3, 1);
        addCampo("Precio final:", this.precioFinal = new JLabel(precioFinal), panelFormulario, constraints, 4, 1);

        int gridy = 11;

        addBotones(panelFormulario, constraints, gridy);
        add(panelFormulario);

    }

    private void addCampo(String label, Component comp, JPanel panel, GridBagConstraints constraints, int gridy,
            int gridwidth) {
        JLabel jlabel = new JLabel(label);
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        panel.add(jlabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = gridy;
        panel.add(comp, constraints);
    }

    private void addBotones(JPanel panel, GridBagConstraints constraints, int gridy) {
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 2;
        this.confirmarBtn = new JButton("Confirmar");
        this.atrasBtn = new JButton("Atras");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmarBtn);
        buttonPanel.add(atrasBtn);

        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, constraints);
    }

    public String getexposicionNombre() {
        return exposicionNombre.getText();
    }

    public void setControlador(ActionListener cConfirmar, ActionListener cAtras) {
        this.confirmarBtn.addActionListener(cConfirmar);
        this.atrasBtn.addActionListener(cAtras);
        setVisible(true);
    }

}