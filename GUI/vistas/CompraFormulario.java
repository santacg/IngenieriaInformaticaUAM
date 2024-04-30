package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.*;

public class CompraFormulario extends JDialog {

    private JLabel exposicionNombre;
    private JComboBox<Integer> diaExpo;
    private JComboBox<String> mesExpo;
    private JComboBox<Integer> anioExpo;
    private JComboBox<String> hora;

    private JTextField numeroTarjetadeCredito;
    private JComboBox<Integer> diaTarj;
    private JComboBox<String> mesTarj;
    private JComboBox<Integer> anioTarj;
    private JTextField cvv;

    private JTextField codigoSorteo;

    private String tipoObraSeleccionada;
    private JButton siguienteBtn;
    private JButton cancelarBtn;

    public CompraFormulario(String nombreExposicion) {
        setTitle("Comprar entrada");
        setSize(700, 600);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Creación del JComboBox para los meses
        hora = new JComboBox<>();
        String[] horas = { "9:00", "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "18:00", "19:00", "20:00" };
        for (String h : horas) {
            hora.addItem(h);
        }

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
    /*
     * public String getObraAutores() {
     * return obraAutores.getText();
     * }
     * 
     * public String getObraDescripcion() {
     * return obraDescripcion.getText();
     * }
     * 
     * public String getObraAnio() {
     * return obraAnio.getText();
     * }
     * 
     * public boolean getObraExterna() {
     * return obraExterna.isSelected();
     * }
     * 
     * public String getObraCuantiaSeguro() {
     * return obraCuantiaSeguro.getText();
     * }
     * 
     * public String getObraNumeroSeguro() {
     * return obraNumeroSeguro.getText();
     * }
     * 
     * public String getTipoDeObra() {
     * return tipoObraSeleccionada;
     * }
     * 
     * public String getCuadroTecnica() {
     * return cuadroTecnica.getText();
     * }
     * 
     * public String getEsculturaMaterial() {
     * return esculturaMaterial.getText();
     * }
     * 
     * public String getEsculturaProfundidad() {
     * return esculturaProfundidad.getText();
     * }
     * 
     * public boolean getFotografiaColor() {
     * return fotografiaColor.isSelected();
     * }
     * 
     * public String getAudiovisualDuracion() {
     * return audiovisualDuracion.getText();
     * }
     * 
     * public String getAudiovisualIdioma() {
     * return audiovisualIdioma.getText();
     * }
     * 
     * public String getObraAlto() {
     * return obraAlto.getText();
     * }
     * 
     * public String getObraAncho() {
     * return obraAncho.getText();
     * }
     * 
     * public String getObraRangoTemperatura() {
     * return obraRangoTemperatura.getText();
     * }
     * 
     * public String getObraTemperaturaMin() {
     * String rango = obraRangoTemperatura.getText();
     * String[] valores = rango.split("-");
     * return valores[0];
     * }
     * 
     * public String getObraTemperaturaMax() {
     * String rango = obraRangoTemperatura.getText();
     * String[] valores = rango.split("-");
     * return valores[1];
     * }
     * 
     * public String getObraHumedadMin() {
     * String rango = obraRangoHumedad.getText();
     * String[] valores = rango.split("-");
     * return valores[0];
     * }
     * 
     * public String getObraHumedadMax() {
     * String rango = obraRangoHumedad.getText();
     * String[] valores = rango.split("-");
     * return valores[1];
     * }
     * 
     * public String getObraRangoHumedad() {
     * return obraRangoHumedad.getText();
     * }
     * 
     */

    public void setControlador(ActionListener cSiguiente, ActionListener cCancelar) {
        this.siguienteBtn.addActionListener(cSiguiente);
        this.cancelarBtn.addActionListener(cCancelar);
        setVisible(true);
    }

}