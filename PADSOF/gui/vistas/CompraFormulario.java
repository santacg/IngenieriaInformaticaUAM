package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * CompraFormulario
 * 
 * Vista para el formulario de compra de entradas
 * 
 * @author Carlos García Santa, Joaquín Abád Díaz, Eduardo Junoy Ortega
 */
public class CompraFormulario extends JDialog {

    private JLabel exposicionNombre;
    private JLabel precioPorEntrada;
    private JComboBox<Integer> nEntradas = new JComboBox<>();

    private JComboBox<Integer> diaExpo = new JComboBox<>();
    private JComboBox<Integer> mesExpo = new JComboBox<>();
    private JComboBox<Integer> anioExpo = new JComboBox<>();
    private JComboBox<String> hora = new JComboBox<>();

    private JTextField numeroTarjetadeCredito;
    private JComboBox<Integer> diaTarj = new JComboBox<>();
    private JComboBox<Integer> mesTarj = new JComboBox<>();
    private JComboBox<Integer> anioTarj = new JComboBox<>();
    private JTextField cvv;

    private JTextField codigoSorteo;

    private JButton siguienteBtn;
    private JButton cancelarBtn;

    /**
     * Constructor de la clase CompraFormulario
     * 
     * @param nombreExposicion Nombre de la exposición
     * @param precio           Precio de la entrada
     */
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

    /**
     * Método para añadir un campo al formulario
     * 
     * @param label       Etiqueta del campo
     * @param comp        Componente del campo
     * @param panel       Panel donde se añadirá el campo
     * @param constraints Restricciones del GridBagLayout
     * @param gridy       Posición en el eje Y
     * @param gridwidth   Ancho del campo
     * 
     */
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

    /**
     * Método para añadir un campo de fecha al formulario
     * 
     * @param label    Etiqueta del campo
     * @param diasBox  ComboBox de los días
     * @param mesesBox ComboBox de los meses
     * @param aniosBox ComboBox de los años
     * @param panel    Panel donde se añadirá el campo
     * @param constraints Restricciones del GridBagLayout
     * @param gridy    Posición en el eje Y
     */
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

    /**
     * Método para añadir los botones de la vista
     * 
     * @param panel       Panel donde se añadirán los botones
     * @param constraints Restricciones del GridBagLayout
     * @param gridy       Posición en el eje Y
     */
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

    /**
     * Método para obtener el nombre de la exposición
     * 
     * @return Nombre de la exposición
     */
    public String getExposicionNombre() {
        return exposicionNombre.getText();
    }

    /**
     * Método para obtener el precio de la entrada
     * 
     * @return Precio de la entrada
     */
    public String getPrecio() {
        return precioPorEntrada.getText();
    }

    /**
     * Método para obtener el número de entradas
     * 
     * @return Número de entradas
     */
    public JComboBox<Integer> getNentradas() {
        return nEntradas;
    }

    /**
     * Método para obtener el día de la exposición
     * 
     * @return Día de la exposición
     */
    public JComboBox<Integer> getDiaExpo() {
        return diaExpo;
    }

    /**
     * Método para obtener el mes de la exposición
     * 
     * @return Mes de la exposición
     */
    public JComboBox<Integer> getMesExpo() {
        return mesExpo;
    }

    /**
     * Método para obtener el año de la exposición
     * 
     * @return Año de la exposición
     */
    public JComboBox<Integer> getAnioExpo() {
        return anioExpo;
    }

    /**
     * Método para obtener la hora de la exposición
     * 
     * @return Hora de la exposición
     */
    public JComboBox<String> getHora() {
        return hora;
    }

    /**
     * Método para obtener el número de tarjeta de crédito
     * 
     * @return Número de tarjeta de crédito
     */
    public String getNumeroTarjetadeCredito() {
        return numeroTarjetadeCredito.getText();
    }

    /**
     * Método para obtener el día de la tarjeta
     * 
     * @return Día de la tarjeta
     */
    public JComboBox<Integer> getDiaTarj() {
        return diaTarj;
    }

    /** 
     * Método para obtener el mes de la tarjeta
     * 
     * @return Mes de la tarjeta
    */
    public JComboBox<Integer> getMesTarj() {
        return mesTarj;
    }

    /**
     * Método para obtener el año de la tarjeta
     * 
     * @return Año de la tarjeta
     */
    public JComboBox<Integer> getAnioTarj() {
        return anioTarj;
    }

    /**
     * Método para obtener el CVV
     * 
     * @return CVV
     */
    public Integer getCVV() {
        if (cvv.getText().equals("")) {
            return 0;
        }
        return Integer.parseInt(cvv.getText());
    }

    /**
     * Método para obtener el código de sorteo
     * 
     * @return Código de sorteo
     */
    public String getCodigoSorteo() {
        return codigoSorteo.getText();
    }

    /**
     * Metodo para establecer el controlador de la vista
     * 
     * @param cSiguiente Controlador del botón siguiente
     * @param cCancelar  Controlador del botón cancelar
     * @param cDiaExpo   Controlador del día de la exposición
     * @param cMesExpo   Controlador del mes de la exposición
     * @param cAnioExpo  Controlador del año de la exposición
     * @param cDiaTarj   Controlador del día de la tarjeta
     * @param cMesTarj   Controlador del mes de la tarjeta
     * @param cAnioTarj  Controlador del año de la tarjeta
     * @param cNentradas Controlador del número de entradas
     * @param cHoras     Controlador de las horas
     */
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