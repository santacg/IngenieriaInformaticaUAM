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
    private JTextField descipcion;
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
                JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

                // Panel de formulario (West)
                JPanel panelFormulario1 = new JPanel();
                panelFormulario1.setLayout(new BoxLayout(panelFormulario1, BoxLayout.Y_AXIS));
                panelFormulario1.setBorder(BorderFactory.createTitledBorder("Detalles de la Exposición"));
                inicializarCamposFormulario(panelFormulario1);

                // Panel de obras (Center)
                panelObras = new JPanel(new GridLayout(0, 1));
                JScrollPane scrollObras = new JScrollPane(panelObras);
                scrollObras.setBorder(BorderFactory.createTitledBorder("Obras"));
                scrollObras.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollObras.setPreferredSize(new Dimension(250, 600));

                // Panel de salas (East)
                JPanel panelSalas = new JPanel(new BorderLayout());
                salas = new JComboBox<>();
                JScrollPane scrollSalas = new JScrollPane(salas);
                scrollSalas.setBorder(BorderFactory.createTitledBorder("Salas"));
                scrollSalas.setPreferredSize(new Dimension(200, 200));
                panelSalas.add(scrollSalas);

                // Panel de botones (South)
                JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
                aceptarBtn = new JButton("Aceptar");
                cancelarBtn = new JButton("Cancelar");
                panelBotones.add(aceptarBtn);
                panelBotones.add(cancelarBtn);

                panelPrincipal.add(panelFormulario1, BorderLayout.WEST);
                panelPrincipal.add(scrollObras, BorderLayout.CENTER);
                panelPrincipal.add(panelSalas, BorderLayout.EAST);
                panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

                add(panelPrincipal);
                return;
        }

        addBotones(panelFormulario, constraints, 8);
        add(panelFormulario);
    }

    private void inicializarCamposFormulario(JPanel panel) {
        nombre = new JTextField(20);
        fechaInicio = new JTextField(20);
        fechaFin = new JTextField(20);
        descipcion = new JTextField(20);
        precio = new JTextField(20);
        tipoExpo = new JList<>(TipoExpo.values());

        addField("Nombre:", nombre, panel);
        addField("Fecha inicio (yyyy-mm-dd):", fechaInicio, panel);
        addField("Fecha fin (yyyy-mm-dd):", fechaFin, panel);
        addField("Descripción:", descipcion, panel);
        addField("Tipo de exposición:", tipoExpo, panel);
        addField("Precio:", precio, panel);
    }

    private void addField(String label, JComponent field, Container container) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        container.add(panel);
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
        return descipcion.getText();
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
