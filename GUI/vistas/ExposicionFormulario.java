package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.imageio.plugins.tiff.TIFFDirectory;
import javax.swing.*;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.exposicion.*;
import GUI.modelo.obra.Obra;
import GUI.modelo.sala.Sala;
import java.util.List;
import java.util.Set;

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
    private JTextField descripcion;
    private JList<TipoExpo> tipoExpo;
    private JTextField precio;
    private JComboBox<String> salas;
    private List<JCheckBox> obras;
    private JPanel panelObras;
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
        setLocationRelativeTo(null);
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
                JPanel northPanel = new JPanel(new GridBagLayout());
                GridBagConstraints constraintsAgregar = new GridBagConstraints();
                constraints.fill = GridBagConstraints.HORIZONTAL;
                constraints.insets = new Insets(10, 10, 10, 10);

                nombre = new JTextField(10);
                fechaInicio = new JTextField(10);
                fechaFin = new JTextField(10);
                descripcion = new JTextField(10);
                precio = new JTextField(10);
                tipoExpo = new JList<>(); // Aquí se debería configurar el modelo de la lista.
                salas = new JComboBox<>();

                int gridy = 0;
                addCampo("Nombre:", nombre, northPanel, constraintsAgregar, gridy++);
                addCampo("Fecha de Inicio:", fechaInicio, northPanel, constraintsAgregar, gridy++);
                addCampo("Fecha de Fin:", fechaFin, northPanel, constraintsAgregar, gridy++);
                addCampo("Descripción:", descripcion, northPanel, constraintsAgregar, gridy++);
                addCampo("Tipo:", new JScrollPane(tipoExpo), northPanel, constraintsAgregar, gridy++);
                addCampo("Precio:", precio, northPanel, constraintsAgregar, gridy++);

                panelFormulario.add(northPanel, BorderLayout.NORTH);

                panelObras = new JPanel();
                panelObras.setLayout(new GridLayout(0, 1)); // Lista de checkboxes vertical
                JScrollPane scrollPaneObras = new JScrollPane(panelObras);
                scrollPaneObras.setPreferredSize(new Dimension(450, 0));

                JPanel centerPanel = new JPanel(new BorderLayout());
                centerPanel.add(scrollPaneObras, BorderLayout.CENTER);

                JPanel eastPanel = new JPanel(new BorderLayout());
                eastPanel.add(salas, BorderLayout.NORTH);
                eastPanel.setPreferredSize(new Dimension(150, 0));

                centerPanel.add(eastPanel, BorderLayout.EAST);

                panelFormulario.add(centerPanel, BorderLayout.CENTER);

                JPanel southPanel = new JPanel();
                aceptarBtn = new JButton("Aceptar");
                cancelarBtn = new JButton("Cancelar");
                southPanel.add(aceptarBtn);
                southPanel.add(cancelarBtn);

                panelFormulario.add(southPanel, BorderLayout.SOUTH);
                break;
        }

        addBotones(panelFormulario, constraints, 8);
        add(panelFormulario);
    }

    public void mostrarObras(CentroExposicion centroExposicion) {
        Set<Obra> obras = centroExposicion.getObras();
        panelObras.removeAll();
        for (Obra obra : obras) {
            JCheckBox checkBox = new JCheckBox(obra.getNombre());
            panelObras.add(checkBox);
        }
        panelObras.revalidate();
        panelObras.repaint();
    }

    public void mostrarSalas(CentroExposicion centroExposicion) {
        Set<Sala> salasDisponibles = centroExposicion.getSalas();
        salas.removeAllItems();
        for (Sala sala : salasDisponibles) {
            salas.addItem(sala.getNombre());
        }
    }

    /**
     * Método que crea el formulario para cancelar una exposición.
     */
    public void formularioCancelarExposicion(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Fecha cancelación (yyyy-mm-dd):", fechaInicio = new JTextField(20), panelFormulario, constraints, 1);
    }

    /**
     * Método que crea el formulario para prorrogar una exposición.
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
        return LocalDate.parse(fechaInicio.getText());
    }

    /**
     * Método que devuelve la fecha de fin de la exposición.
     * 
     * @return LocalDate con la fecha de fin de la exposición.
     */
    public LocalDate getFechaFin() {
        return LocalDate.parse(fechaFin.getText());
    }

    /**
     * Método que devuelve la descripción de la exposición.
     * 
     * @return String con la descripción de la exposición.
     */
    public String getDescripcion() {
        return descripcion.getText();
    }

    /**
     * Método que devuelve el tipo de exposición.
     * 
     * @return TipoExpo con el tipo de exposición.
     */
    public TipoExpo getTipoExpo() {
        return tipoExpo.getSelectedValue();
    }

    /**
     * Método que devuelve el precio de la exposición.
     * 
     * @return Double con el precio de la exposición.
     */
    public Double getPrecio() {
        return Double.parseDouble(precio.getText());
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
