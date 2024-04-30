package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CompraFormulario extends JDialog {

    private JLabel exposicionNombre;
    private JLabel precioPorEntrada;
    private JComboBox<Integer> nEntradas = new JComboBox<>();

    private JComboBox<Integer> diaExpo = new JComboBox<>();
    private JComboBox<Integer> mesExpo = new JComboBox<>();
    private JComboBox<Integer> anioExpo = new JComboBox<>();
    private JComboBox<String> hora = new JComboBox<>();

    private JTextField numeroTarjetadeCredito;
    private JComboBox<Integer> diaTarj  = new JComboBox<>();
    private JComboBox<Integer> mesTarj = new JComboBox<>();
    private JComboBox<Integer> anioTarj = new JComboBox<>();
    private JTextField cvv;

    private JTextField codigoSorteo;

    private JButton siguienteBtn;
    private JButton cancelarBtn;

    public CompraFormulario(String nombreExposicion, double precio) {
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
        addCampoFecha("Fecha:", diaExpo, mesExpo, anioExpo, panelFormulario, constraints, 3);
        addCampo("Hora:", hora, panelFormulario, constraints, 4, 1);
        addCampo("Numero de tarjeta de credito:", numeroTarjetadeCredito = new JTextField(32), panelFormulario,
                constraints, 5, 5);
        addCampoFecha("Fecha de expiración de la tarjeta:", diaTarj, mesTarj, anioTarj, panelFormulario,
                constraints, 6);
        addCampo("CVV:", cvv = new JTextField(32), panelFormulario, constraints, 7, 1);
        addCampo("Código de sorteo:", codigoSorteo = new JTextField(32), panelFormulario, constraints, 8, 1);
        
        diaExpo.setSelectedIndex(0);
        mesExpo.setSelectedIndex(0);
        anioExpo.setSelectedIndex(0);
        diaTarj.setSelectedIndex(0);
        mesTarj.setSelectedIndex(0);
        anioTarj.setSelectedIndex(0);
        
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

    private void addCampoFecha(String label, JComboBox<Integer> diasBox, JComboBox<Integer> mesesBox,
            JComboBox<Integer> aniosBox,
            JPanel panel, GridBagConstraints constraints, Integer gridy) {
        JLabel jlabel = new JLabel(label);
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 1;
        panel.add(jlabel, constraints);

        for (Integer dia = 1; dia <= 31; dia++) {
            diasBox.addItem(dia);
        }
        constraints.gridx = 1;
        constraints.gridy = gridy;
        panel.add(diasBox, constraints);

        for (Integer mesnum = 1; mesnum <= 12; mesnum++) {
            mesesBox.addItem(mesnum);
        }
        constraints.gridx = 2;
        constraints.gridy = gridy;
        panel.add(mesesBox, constraints);

        for (Integer ano = 1990; ano <= 2100; ano++) {
            aniosBox.addItem(ano);
        }
        constraints.gridx = 3;
        constraints.gridy = gridy;
        panel.add(aniosBox, constraints);
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

    public JComboBox<Integer> getDiaExpo() {
        return diaExpo;
    }

    public JComboBox<Integer> getMesExpo() {
        return mesExpo;
    }

    public JComboBox<Integer> getAnioExpo() {
        return anioExpo;
    }

    public JComboBox<String> getHora() {
        return hora;
    }

    public String getNumeroTarjetadeCredito() {
        return numeroTarjetadeCredito.getText();
    }

    public JComboBox<Integer> getDiaTarj() {
        return diaTarj;
    }

    public JComboBox<Integer> getMesTarj() {
        return mesTarj;
    }

    public JComboBox<Integer> getAnioTarj() {
        return anioTarj;
    }

    public Integer getCVV() {
        if (cvv.getText().equals("")) {
            return 0;
        }
        return Integer.parseInt(cvv.getText());
    }

    public String getCodigoSorteo() {
        return codigoSorteo.getText();
    }

    public void setControlador(ActionListener cSiguiente, ActionListener cCancelar, ActionListener cDiaExpo,
            ActionListener cMesExpo, ActionListener cAnioExpo, ActionListener cDiaTarj, ActionListener cMesTarj,
            ActionListener cAnioTarj, ActionListener cNentradas, ActionListener cHoras) {
        this.siguienteBtn.addActionListener(cSiguiente);
        this.cancelarBtn.addActionListener(cCancelar);
        this.diaExpo.addActionListener(cDiaExpo);
        this.mesExpo.addActionListener(cMesExpo);
        this.anioExpo.addActionListener(cAnioExpo);
        this.diaTarj.addActionListener(cDiaTarj);
        this.mesTarj.addActionListener(cMesTarj);
        this.anioTarj.addActionListener(cAnioTarj);
        this.nEntradas.addActionListener(cNentradas);
        this.hora.addActionListener(cHoras);
        setVisible(true);
    }

}