package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;

import javax.swing.*;

import gui.modelo.exposicion.*;

/**
 * Clase ExposicionFormulario.
 * Actúa como vista para añadir una exposición.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ExposicionFormulario extends JDialog {

    private JTextField nombre;
    private JTextField fechaInicio;
    private JTextField fechaFin;
    private JTextField descipcion;
    private JRadioButton temporalBtn;
    private JRadioButton permanenteBtn;
    private ButtonGroup tipoExpoGroup;
    private JPanel tipoExpoPanel;
    private JTextField precio;
    private JButton aceptarBtn;
    private JButton cancelarBtn;

    /**
     * Constructor de la clase ExposicionFormulario.
     * 
     * @param accion String que indica la acción a realizar.
     */
    public ExposicionFormulario(String accion) {
        setTitle("Añadir Exposición");
        setSize(600, 700);
        setModal(true);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        switch (accion) {
            case "Cancelar Exposicion":
                formularioCancelarExposicion(panelFormulario, constraints);
                break;
            case "Prorrogar Exposicion":
                formularioProrrogarExposicion(panelFormulario, constraints);
                break;
            case "Cerrar Temporalmente":
                formularioCerrarExposicion(panelFormulario, constraints);
                break;
            case "Establecer como Temporal":
                formularioExposicionTemporal(panelFormulario, constraints);
                break;
            case "Agregar Exposicion":
                JPanel panelPrincipal = new JPanel(new BorderLayout());
                // Formulario
                panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
                panelFormulario.setBorder(BorderFactory.createTitledBorder("Detalles de la exposición"));

                addCampoFlow("Nombre:", nombre = new JTextField(20), panelFormulario);
                addCampoFlow("Fecha inicio (yyyy-mm-dd):", fechaInicio = new JTextField(20), panelFormulario);
                addCampoFlow("Fecha fin (yyyy-mm-dd):", fechaFin = new JTextField(20), panelFormulario);
                addCampoFlow("Descripción:", descipcion = new JTextField(20), panelFormulario);
                setupTipoExpoPanel();
                panelFormulario.add(tipoExpoPanel);
                addCampoFlow("Precio:", precio = new JTextField(20), panelFormulario);

                // Panel botones
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                aceptarBtn = new JButton("Aceptar");
                cancelarBtn = new JButton("Cancelar");
                buttonPanel.add(aceptarBtn);
                buttonPanel.add(cancelarBtn);

                panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
                add(panelPrincipal, BorderLayout.CENTER);
                add(buttonPanel, BorderLayout.SOUTH);

                return;
            default:
                panelFormulario.add(new JLabel("Confirma o cancela la acción " + accion + ":"));
                break;
        }

        addBotones(panelFormulario, constraints, 8);
        add(panelFormulario);
    }

    /**
     * Método que crea el formulario para añadir una exposición.
     */
    private void setupTipoExpoPanel() {
        tipoExpoGroup = new ButtonGroup();
        tipoExpoPanel = new JPanel();
        tipoExpoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tipoExpoPanel.setBorder(BorderFactory.createTitledBorder("Tipo de Exposición"));

        temporalBtn = new JRadioButton("Temporal");
        permanenteBtn = new JRadioButton("Permanente");

        tipoExpoGroup.add(temporalBtn);
        tipoExpoGroup.add(permanenteBtn);

        tipoExpoPanel.add(temporalBtn);
        tipoExpoPanel.add(permanenteBtn);

        temporalBtn.setSelected(true);
    }

    /**
     * Añade un campo al formulario pero con FlowLayout.
     * 
     * @param label     La etiqueta del campo.
     * @param field     El campo.
     * @param container El contenedor.
     */
    private void addCampoFlow(String label, JComponent field, Container container) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        container.add(panel);
    }

    /**
     * Método que crea el formulario para cancelar una exposición.
     */
    public void formularioCancelarExposicion(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Fecha cancelación (yyyy-mm-dd):", fechaInicio = new JTextField(20), panelFormulario, constraints, 1);
    }

    /**
     * Método que crea el formulario para prorrogar una exposición.
     * 
     * @param panelFormulario panel del formulario.
     * @param constraints contrains aplicadas.
     */
    public void formularioProrrogarExposicion(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Nueva fecha fin (yyyy-mm-dd):", fechaFin = new JTextField(20), panelFormulario, constraints, 2);
    }

    /**
     * Método que crea el formulario para cerrar temporalmente una exposición.
     */
    public void formularioCerrarExposicion(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Fecha incio cierre temporal (yyyy-mm-dd)", fechaInicio = new JTextField(20), panelFormulario,
                constraints, 1);
        addCampo("Fecha fin cierre temporal (yyyy-mm-dd):", fechaFin = new JTextField(20), panelFormulario, constraints,
                2);
    }

    /**
     * Método que crea el formulario para establecer una exposición como temporal.
     */
    public void formularioExposicionTemporal(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Fecha fin exposición (yyyy-mm-dd):", fechaFin = new JTextField(20), panelFormulario, constraints, 2);
    }

    /**
     * Método que añade un campo al formulario.
     * 
     * @param label       String que indica el nombre del campo.
     * @param comp        Componente que se añade al formulario.
     * @param panel       Panel al que se añade el campo.
     * @param constraints Constraints que se aplican al campo.
     * @param gridy       Posición en el grid.
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
     * Método que añade los botones al formulario.
     * 
     * @param panel       Panel al que se añaden los botones.
     * @param constraints Constraints que se aplican a los botones.
     * @param gridy       Posición en el grid.
     */
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

    /**
     * Método que devuelve el nombre de la exposición.
     * 
     * @return String con el nombre de la exposición.
     */
    public String getNombre() {
        return nombre.getText();
    }

    /**
     * Método que devuelve la fecha de inicio de la exposición.
     * 
     * @return LocalDate con la fecha de inicio de la exposición.
     */
    public LocalDate getFechaInicio() {
        if (fechaInicio.getText().equals("")) {
            return null;
        }

        try {
            return LocalDate.parse(fechaInicio.getText());
        } catch (DateTimeException e) {
            return null;
        }
    }

    /**
     * Método que devuelve la fecha de fin de la exposición.
     * 
     * @return LocalDate con la fecha de fin de la exposición.
     */
    public LocalDate getFechaFin() {
        if (fechaFin.getText().equals("")) {
            return null;
        }

        try {
            return LocalDate.parse(fechaFin.getText());
        } catch (DateTimeException e) {
            return null;
        }
    }

    /**
     * Método que devuelve la descripción de la exposición.
     * 
     * @return String con la descripción de la exposición.
     */
    public String getDescripcion() {
        return descipcion.getText();
    }

    /**
     * Método que devuelve el precio de la exposición.
     * 
     * @return Double con el precio de la exposición.
     */
    public Double getPrecio() {
        if (precio.getText().equals("")) {
            return null;
        }

        try {
            return Double.parseDouble(precio.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Método que devuelve el tipo de exposición seleccionado.
     * 
     * @return String con el tipo de exposición seleccionado.
     */
    public TipoExpo getTipoSeleccionado() {
        if (temporalBtn.isSelected()) {
            return TipoExpo.TEMPORAL;
        } else {
            return TipoExpo.PERMANENTE;
        }
    }
    /**
     * Método que establece el controlador de la vista.
     * 
     * @param cAceptar  ActionListener que controla el botón de aceptar.
     * @param cCancelar ActionListener que controla el botón de cancelar.
     */
    public void setControlador(ActionListener cAceptar, ActionListener cCancelar) {
        aceptarBtn.addActionListener(cAceptar);
        cancelarBtn.addActionListener(cCancelar);
        setVisible(true);
    }
}
