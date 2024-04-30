package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;

public class ConfirmarCompra extends JDialog {

    private JLabel exposicionNombre;
    private JLabel fechaExpo;
    private JLabel hora;

    private JLabel numeroTarjetadeCredito;
    private JLabel fechaTarj;
    private JLabel cvv;

    private JLabel codigoSorteo;

    private JLabel precioFinal;

    private JButton confirmarBtn;
    private JButton atrasBtn;

    public ConfirmarCompra(String nombreExposicion) {
        setTitle("Comprar entrada");
        setSize(700, 600);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        /*
        addCampo("Exposición:", exposicionNombre = new JLabel(nombreExposicion), panelFormulario, constraints, 0, 1);
        addCampoFecha("Fecha:", diaExpo, mesExpo, anioExpo, panelFormulario, constraints, 1);
        addCampo("Hora:", hora, panelFormulario, constraints, 2, 1);
        addCampo("Numero de tarjeta de credito:", numeroTarjetadeCredito = new JTextField(32), panelFormulario,
                constraints, 3, 5);
        addCampoFecha("Fecha de expiración de la tarjeta:", diaTarj, mesTarj, anioExpo, panelFormulario,
                constraints, 4);
        addCampo("CVV:", cvv = new JTextField(32), panelFormulario, constraints, 5, 1);
        addCampo("Código de sorteo:", codigoSorteo = new JTextField(32), panelFormulario, constraints, 6, 1);

        int gridy = 11;

        addBotones(panelFormulario, constraints, gridy);
        add(panelFormulario);
        */
    }

    private void addCampo(String label, Component comp, JPanel panel, GridBagConstraints constraints, int gridy, int gridwidth) {
        JLabel jlabel = new JLabel(label);
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        panel.add(jlabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = gridy;
        panel.add(comp, constraints);
    }

    private void addCampoFecha(String label, JComboBox<Integer> diasBox, JComboBox<String> mesesBox,
            JComboBox<Integer> aniosBox,
            JPanel panel, GridBagConstraints constraints, int gridy) {
        JLabel jlabel = new JLabel(label);
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 1;
        panel.add(jlabel, constraints);

        diasBox = new JComboBox<>();
        for (int dia = 1; dia <= 31; dia++) {
            diasBox.addItem(dia);
        }
        constraints.gridx = 1;
        constraints.gridy = gridy;
        panel.add(diasBox, constraints);
        

        mesesBox = new JComboBox<>();
        String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };
        for (String mes : meses) {
            mesesBox.addItem(mes);
        }
        constraints.gridx = 2;
        constraints.gridy = gridy;
        panel.add(mesesBox, constraints);
        
        aniosBox = new JComboBox<>();
        for (int ano = 1990; ano <= 2100; ano++) {
            aniosBox.addItem(ano);
        }
        constraints.gridx = 3;
        constraints.gridy = gridy;
        panel.add(aniosBox, constraints);
    }
    /*
    private void addCampoFechaTarjeta(String label, JPanel panel, GridBagConstraints constraints, int gridy) {
        JLabel jlabel = new JLabel(label);
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 1;
        panel.add(jlabel, constraints);

        diaExpo = new JComboBox<>();
        constraints.gridx = 1;
        constraints.gridy = gridy;
        panel.add(diaExpo, constraints);

        diaExpo = new JComboBox<>();
        constraints.gridx = 2;
        constraints.gridy = gridy;
        panel.add(mesExpo, constraints);

        diaExpo = new JComboBox<>();
        constraints.gridx = 1;
        constraints.gridy = gridy;
        panel.add(anioExpo, constraints);
    }

    private void addBotones(JPanel panel, GridBagConstraints constraints, int gridy) {
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

    public String getexposicionNombre() {
        return exposicionNombre.getText();
    }
    */

    public void setControlador(ActionListener cConfirmar, ActionListener cAtras) {
        this.confirmarBtn.addActionListener(cConfirmar);
        this.atrasBtn.addActionListener(cAtras);
        setVisible(true);
    }

}