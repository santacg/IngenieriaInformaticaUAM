package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.*;

import gui.modelo.centroExposicion.CentroExposicion;
import gui.modelo.exposicion.Exposicion;

public class DescuentoFormulario extends JDialog {
    private String tipoDescuento;
    private JList<String> exposiciones;
    private JTextField descuento;
    private JTextField cantidad;
    private JButton aceptarBtn;
    private JButton cancelarBtn;

    /**
     * Constructor de la clase SalaFormulario
     * 
     * @param accion String que indica la acción a realizar
     */
    public DescuentoFormulario() {
        setSize(600, 700);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        this.tipoDescuento = formularioTipoDescuento();
        if (tipoDescuento == null) {
            dispose();
            return;
        }

        formularioAñadirDescuento(panelFormulario, constraints);

        this.exposiciones = new JList<>();
        JScrollPane scrollExposiciones = new JScrollPane(this.exposiciones);
        scrollExposiciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollExposiciones.setBorder(BorderFactory.createTitledBorder("Exposiciones"));

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(scrollExposiciones, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBotones(buttonPanel);
        panelPrincipal.add(buttonPanel, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Método que crea el formulario para añadir un descuento
     * 
     * @param panelFormulario JPanel en el que se añadirán los campos
     * @param constraints     GridBagConstraints que definen la posición de los
     *                        campos
     */
    public void formularioAñadirDescuento(JPanel panelFormulario, GridBagConstraints constraints) {
        addCampo("Descuento:", descuento = new JTextField(20), panelFormulario, constraints, 0);
        addCampo("Cantidad en días o meses:", cantidad = new JTextField(20), panelFormulario, constraints, 1);
    }

    /**
     * Método que crea un formulario para seleccionar el tipo de descuento
     * 
     * @return String con el tipo de descuento seleccionado
     */
    public String formularioTipoDescuento() {
        String[] opcionesDescuento = { "Descuento por dia", "Descuento por mes" };
        JComboBox<String> comboDescuento = new JComboBox<>(opcionesDescuento);

        int seleccion = JOptionPane.showConfirmDialog(null, comboDescuento, "Seleccione el tipo de descuento",
                JOptionPane.OK_CANCEL_OPTION);

        if (seleccion == JOptionPane.OK_OPTION) {
            return (String) comboDescuento.getSelectedItem();
        }

        return null;
    }

    /**
     * Método que muestra las exposiciones de un centro de exposición
     * 
     * @param centroExposicion CentroExposicion del que se mostrarán las
     *                         exposiciones disponibles
     */
    public void mostrarExposiciones(CentroExposicion centroExposicion) {
        Set<Exposicion> exposicionesSet = centroExposicion.getExposiciones();
        DefaultListModel<String> model = new DefaultListModel<>();

        if (exposicionesSet.isEmpty()) {
            model.addElement("No hay exposiciones disponibles.");
        } else {
            for (Exposicion exposicion : exposicionesSet) {
                model.addElement(exposicion.getNombre());
            }
        }

        exposiciones.setModel(model);
    }

    /**
     * Método que añade un campo al formulario
     * 
     * @param label       String que indica el nombre del campo
     * @param comp        Component que se añadirá al formulario
     * @param panel       JPanel en el que se añadirá el campo
     * @param constraints GridBagConstraints que definen la posición del campo
     * @param gridy       int que indica la fila en la que se añadirá el campo
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
     * Método que añade los botones de aceptar y cancelar al formulario
     * 
     * @param panel JPanel en el que se añadirán los botones
     */
    private void addBotones(JPanel buttonPanel) {
        this.aceptarBtn = new JButton("Aceptar");
        this.cancelarBtn = new JButton("Cancelar");

        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);
    }

    /**
     * Método que devuelve el porcentaje de descuento
     * 
     * @return Double con el porcentaje de descuento
     */
    public Double getDescuento() {
        if (descuento.getText().equals("")) {
            return null;
        }

        try {
            return Double.parseDouble(descuento.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Método que devuelve la cantidad de descuento
     * 
     * @return Integer con la cantidad de descuento
     */
    public Integer getCantidad() {
        if (cantidad.getText().equals("")) {
            return null;
        }

        try {
            return Integer.parseInt(cantidad.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Método que devuelve el tipo de descuento seleccionado
     * 
     * @return String con el tipo de descuento seleccionado
     */
    public String getTipoDescuento() {
        return tipoDescuento;
    }

    /**
     * Método que devuelve la exposición seleccionada en el formulario
     * 
     * @return String con la exposición seleccionada
     */
    public String getSelectedExposicion() {
        return exposiciones.getSelectedValue();
    }

    /**
     * Método que establece los controladores de los botones del formulario
     * 
     * @param cAceptar  ActionListener que se añadirá al botón de aceptar
     * @param cCancelar ActionListener que se añadirá al botón de cancelar
     */
    public void setControlador(ActionListener cAceptar, ActionListener cCancelar) {
        aceptarBtn.addActionListener(cAceptar);
        cancelarBtn.addActionListener(cCancelar);
        setVisible(true);
    }

}
