package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.exposicion.*;
import GUI.modelo.obra.Obra;
import GUI.modelo.sala.Sala;

import java.util.ArrayList;
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
    private List<JCheckBox> obras = new ArrayList<>();
    private JPanel panelObras;
    private JButton aceptarBtn;
    private JButton cancelarBtn;
    private JTree treeSalas;

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
                // Formulario
                panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
                panelFormulario.setBorder(BorderFactory.createTitledBorder("Detalles de la exposición"));

                addCampoFlow("Nombre:", nombre = new JTextField(20), panelFormulario);
                addCampoFlow("Fecha inicio (yyyy-mm-dd):", fechaInicio = new JTextField(20), panelFormulario);
                addCampoFlow("Fecha fin (yyyy-mm-dd):", fechaFin = new JTextField(20), panelFormulario);
                addCampoFlow("Descripción:", descipcion = new JTextField(20), panelFormulario);
                addCampoFlow("Tipo de exposición:", tipoExpo = new JList<TipoExpo>(TipoExpo.values()), panelFormulario);
                addCampoFlow("Precio:", precio = new JTextField(20), panelFormulario);

                // Panel obras
                panelObras = new JPanel();
                panelObras.setLayout(new GridLayout(0, 1));
                JScrollPane scrollObras = new JScrollPane(panelObras);
                scrollObras.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollObras.setBorder(BorderFactory.createTitledBorder("Obras"));

                // Panel salas
                JPanel salasPanel = new JPanel(new BorderLayout());
                treeSalas = new JTree();
                JScrollPane scrollSalas = new JScrollPane(treeSalas);
                salasPanel.add(scrollSalas, BorderLayout.CENTER);
                salasPanel.setBorder(BorderFactory.createTitledBorder("Salas"));

                // Panel botones
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                aceptarBtn = new JButton("Aceptar");
                cancelarBtn = new JButton("Cancelar");
                buttonPanel.add(aceptarBtn);
                buttonPanel.add(cancelarBtn);

                panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
                panelPrincipal.add(scrollObras, BorderLayout.CENTER);
                panelPrincipal.add(salasPanel, BorderLayout.SOUTH);
                add(panelPrincipal, BorderLayout.CENTER);
                add(buttonPanel, BorderLayout.SOUTH);

                return;
        }

        addBotones(panelFormulario, constraints, 8);
        add(panelFormulario);
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
     * Método que muestra las obras en el formulario.
     * 
     * @param centroExposicion CentroExposicion que contiene las obras.
     */
    public void mostrarObras(CentroExposicion centroExposicion) {
        Set<Obra> obras = centroExposicion.getObrasAlmacenadas();
        panelObras.removeAll();
        this.obras.clear();

        for (Obra obra : obras) {
            JCheckBox checkBox = new JCheckBox(obra.getNombre());
            this.obras.add(checkBox);
            panelObras.add(checkBox);
        }

        panelObras.revalidate();
        panelObras.repaint();
    }

    /**
     * Método que muestra las salas en el formulario.
     * 
     * @param centroExposicion CentroExposicion que contiene las salas.
     */
    public void mostrarSalas(CentroExposicion centroExposicion) {
        Set<Sala> salas = centroExposicion.getSalas();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Salas");
        for (Sala sala : salas) {
            DefaultMutableTreeNode salaNode = new DefaultMutableTreeNode(sala.getNombre());
            addSubSalas(sala, salaNode);
            root.add(salaNode);
        }
        treeSalas.setModel(new DefaultTreeModel(root));
    }

    /**
     * Método que añade las sub-salas al árbol de salas.
     * 
     * @param sala     Sala a la que se le añaden las sub-salas.
     * @param salaNode Nodo de la sala.
     */
    private void addSubSalas(Sala sala, DefaultMutableTreeNode salaNode) {
        // Me ha costa la vida esto, pero creo que esta bien
        List<Sala> subSalas = sala.getSubSalas();
        if (subSalas != null) {
            for (Sala subSala : subSalas) {
                DefaultMutableTreeNode subSalaNode = new DefaultMutableTreeNode(subSala.getNombre());
                salaNode.add(subSalaNode);
                addSubSalas(subSala, subSalaNode);
            }
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
     * Método que devuelve las obras seleccionadas.
     * @return List<String> con las obras seleccionadas.
     */
    public List<String> getObras() {
        List<String> obrasSeleccionadas = new ArrayList<>();
        for (JCheckBox obra : obras) {
            if (obra.isSelected()) {
                obrasSeleccionadas.add(obra.getText());
            }
        }
        return obrasSeleccionadas;
    }

    /**
     * Metodo que devuelve el JTree de salas.
     * 
     * @return Arbol JTree de salas
     */
    public JTree getTreeSalas() {
        return treeSalas;
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
