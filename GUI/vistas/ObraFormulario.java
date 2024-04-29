package GUI.vistas;

import java.awt.*;

import javax.swing.*;

import GUI.modelo.obra.Estado;

public class ObraFormulario extends JDialog {

    private JTextField obraNombre;
    private JTextField obraAutores;
    private JTextField obraDescripcion;
    private JTextField obraAnio;
    private JCheckBox obraExterna;
    private JTextField obraCuantiaSeguro;
    private JTextField obraNumeroSeguro;
    private JComboBox<Estado> obraEstado;
    private JComboBox<String> obraTipoObra;

    public ObraFormulario() {
        setTitle("Agregar obra");
        setSize(500, 400);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        addCampo("Nombre:", obraNombre = new JTextField(20), panelFormulario, constraints, 0);
        addCampo("Autores:", obraAutores = new JTextField(20), panelFormulario, constraints, 1);
        addCampo("Descripcion:", obraDescripcion = new JTextField(20), panelFormulario, constraints, 2);
        addCampo("Año:", obraAnio = new JTextField(20), panelFormulario, constraints, 3);
        addCampo("Externa:", obraExterna = new JCheckBox(), panelFormulario, constraints, 4);
        addCampo("Cuantia Seguro:", obraCuantiaSeguro = new JTextField(20), panelFormulario, constraints, 5);
        addCampo("Numero Seguro:", obraNumeroSeguro = new JTextField(20), panelFormulario, constraints, 6);
        addCombo("Estado:", obraEstado = new JComboBox<>(Estado.values()), panelFormulario, constraints, 7);
        addCombo("Tipo de Obra:", obraTipoObra = new JComboBox<>(new String[] { "Tipo 1", "Tipo 2", "Tipo 3" }),
                panelFormulario, constraints, 8);

        addBotones(panelFormulario, constraints, 9);

        add(panelFormulario);
        setVisible(true);
    }

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

    private void addCombo(String label, JComboBox<?> combo, JPanel panel, GridBagConstraints constraints, int gridy) {
        JLabel jlabel = new JLabel(label);
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 1;
        panel.add(jlabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = gridy;
        panel.add(combo, constraints);
    }

    private void addBotones(JPanel panel, GridBagConstraints constraints, int gridy) {
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 2;
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);

        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, constraints);
    }

    public String getObraNombre() {
        return obraNombre.getText();
    }

    public String getObraAutores() {
        return obraAutores.getText();
    }

    public String getObraDescripcion() {
        return obraDescripcion.getText();
    }

    public String getObraAnio() {
        return obraAnio.getText();
    }

    public boolean getObraExterna() {
        return obraExterna.isSelected();
    }

    public String getObraCuantiaSeguro() {
        return obraCuantiaSeguro.getText();
    }

    public String getObraNumeroSeguro() {
        return obraNumeroSeguro.getText();
    }

    public Estado getObraEstado() {
        return (Estado) obraEstado.getSelectedItem();
    }

    public String getObraTipoObra() {
        return (String) obraTipoObra.getSelectedItem();
    }

}