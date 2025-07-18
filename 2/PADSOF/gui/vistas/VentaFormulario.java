package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Clase VentaFormulario
 * Implementa la interfaz gráfica de un formulario para ventas.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class VentaFormulario extends JDialog {

    private JLabel exposicionNombre;
    private JLabel precioPorEntrada;
    private JComboBox<Integer> nEntradas = new JComboBox<>();

    private JComboBox<String> hora = new JComboBox<>();

    private JButton siguienteBtn;
    private JButton cancelarBtn;

    /**
     * Constructor de la clase SorteoFormulario
     * 
     */
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

    /**
     * Método auxiliar para añadir un campo al formulario
     * 
     * @param label       Etiqueta del campo
     * @param comp        Componente del campo
     * @param panel       Panel al que añadir el campo
     * @param constraints Restricciones de diseño
     * @param gridy       Fila en la que añadir el campo
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
     * Método auxiliar para añadir los botones al formulario
     * 
     * @param panel       Panel al que añadir los botones
     * @param constraints Restricciones de diseño
     * @param gridy       Fila en la que añadir los botones
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
     * Método para obtener el nombre de la exposicion seleccionada
     * 
     * @return String con el nombre de la exposicion seleccionada
     */ 
    public String getExposicionNombre() {
        return exposicionNombre.getText();
    }

    /**
     * Método para obtener el precio
     * 
     * @return String con el precio de las entradas 
     */ 
    public String getPrecio() {
        return precioPorEntrada.getText();
    }

    /**
     * Método para obtener el número de entradas seleccionado
     * 
     * @return Integer con el número de entradas seleccionado 
     */
    public JComboBox<Integer> getNentradas() {
        return nEntradas;
    }

    /**
     * Método para obtener la hora seleccionada
     * 
     * @return String con la hora seleccionada 
     */
    public JComboBox<String> getHora() {
        return hora;
    }

    /**
     * Método para establecer los controladores de los botones
     * 
     * @param cSiguiente ActionListener para el botón de siguiente
     * @param cCancelar ActionListener para el botón de cancelar
     * @param cNentradas ActionListener para el JComboBox de número de entradas
     * @param cHoras ActionListener para el JComboBox de horas
     */
    public void setControlador(ActionListener cSiguiente, ActionListener cCancelar, ActionListener cNentradas,
            ActionListener cHoras) {
        this.siguienteBtn.addActionListener(cSiguiente);
        this.cancelarBtn.addActionListener(cCancelar);
        this.nEntradas.addActionListener(cNentradas);
        this.hora.addActionListener(cHoras);
        setVisible(true);
    }

}