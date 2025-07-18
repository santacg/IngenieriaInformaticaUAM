package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * ConfirmarCompra
 *
 * Vista para confirmar la compra de entradas.
 * 
 * @author Carlos Garcia Santa, Joaquin Abad Diaz, Eduardo Junoy Ortega
 */
public class ConfirmarCompra extends JDialog {

    private JLabel exposicionNombre;
    private JLabel nEntradas;
    private JLabel fechaExpo;
    private JLabel hora;

    private JLabel numeroTarjetadeCredito;
    private JLabel fechaTarj;
    private JLabel cvv;

    private JLabel codigoSorteo;

    private JLabel precioFinal;

    private JButton confirmarBtn;
    private JButton atrasBtn;

    /**
     * Constructor de la vista.
     * 
     * @param exposicionNombre Nombre de la exposición.
     * @param nEntradas Número de entradas.
     * @param fechaExpo Fecha de la exposición.
     * @param hora Hora de la exposición.
     * @param numeroTarjetadeCredito Número de tarjeta de crédito.
     * @param fechaTarj Fecha de expiración de la tarjeta.
     * @param cvv CVV de la tarjeta.
     * @param precioFinal Precio final de la compra.
     */
    public ConfirmarCompra(String exposicionNombre, String nEntradas, String fechaExpo, String hora,
            String numeroTarjetadeCredito, String fechaTarj, String cvv, String precioFinal) {
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
        addCampo("Numero de tarjeta de credito:", this.numeroTarjetadeCredito = new JLabel(numeroTarjetadeCredito),
                panelFormulario,
                constraints, 4, 5);
        addCampo("Fecha de expiración de la tarjeta:", this.fechaTarj = new JLabel(fechaTarj), panelFormulario,
                constraints, 5, 1);
        addCampo("CVV:", this.cvv = new JLabel(cvv), panelFormulario, constraints, 6, 1);
        addCampo("Precio final:", this.precioFinal = new JLabel(precioFinal), panelFormulario, constraints, 7, 1);

        int gridy = 11;

        addBotones(panelFormulario, constraints, gridy);
        add(panelFormulario);

    }

    /**
     * Añade un campo al formulario.
     * @param label Etiqueta del campo.
     * @param comp Componente del campo.
     * @param panel Panel donde se añade el campo.
     * @param constraints Restricciones del GridBagLayout.
     * @param gridy Posición en el grid. 
     * @param gridwidth Ancho del campo.
     */
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

    /**
     * Añade los botones al formulario.
     * @param panel Panel donde se añaden los botones.
     * @param constraints Restricciones del GridBagLayout.
     * @param gridy Posición en el grid.
     */
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

    /**
     * Devuelve el número de entradas.
     * @return Número de entradas.
     */
    public String getexposicionNombre() {
        return exposicionNombre.getText();
    }

    /**
     * Devuelve el número de entradas.
     * 
     * @param ActionListener cConfirmar Controlador del botón de confirmar.
     * @param ActionListener cAtras Controlador del botón de atras.
     * 
     * @return Número de entradas.
     */
    public void setControlador(ActionListener cConfirmar, ActionListener cAtras) {
        this.confirmarBtn.addActionListener(cConfirmar);
        this.atrasBtn.addActionListener(cAtras);
        setVisible(true);
    }

}