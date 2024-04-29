package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;

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
    private JTextField cuadroTecnica;
    private JTextField esculturaAltura;
    private JTextField esculturaMaterial;
    private JCheckBox fotografiaColor;
    private JTextField audiovisualDuracion;
    private JTextField audiovisualIdioma;
    private JComboBox<Estado> obraEstado;
    private String tipoObraSeleccionada;
    private JButton guardarBtn;
    private JButton cancelarBtn;

    public ObraFormulario() {
        setTitle("Agregar obra");
        setSize(500, 400);
        setLocationRelativeTo(null); // Centrar el formulario
        setModal(true); // Hacer el diálogo modal para bloquear otras ventanas hasta que se cierre

        tipoObraSeleccionada = formularioTipoObra();
        if (tipoObraSeleccionada == null) {
            dispose();
            return;
        }

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        addCampo("Nombre:", obraNombre = new JTextField(32), panelFormulario, constraints, 0);
        addCampo("Autores:", obraAutores = new JTextField(32), panelFormulario, constraints, 1);
        addCampo("Descripcion:", obraDescripcion = new JTextField(32), panelFormulario, constraints, 2);
        addCampo("Año:", obraAnio = new JTextField(32), panelFormulario, constraints, 3);
        addCampo("Externa:", obraExterna = new JCheckBox(), panelFormulario, constraints, 4);
        addCampo("Cuantia Seguro:", obraCuantiaSeguro = new JTextField(32), panelFormulario, constraints, 5);
        addCampo("Numero Seguro:", obraNumeroSeguro = new JTextField(32), panelFormulario, constraints, 6);

        int gridy = 8;
        switch (tipoObraSeleccionada) {
            case "Cuadro":
                addCampo("Técnica", cuadroTecnica = new JTextField(32), panelFormulario, constraints, gridy++);
                break;
            case "Escultura":
                addCampo("Material", esculturaMaterial = new JTextField(32), panelFormulario, constraints, gridy++);
                addCampo("Altura", esculturaAltura = new JTextField(32), panelFormulario, constraints, gridy++);
                break;
            case "Fotografía":
                addCampo("Color", fotografiaColor = new JCheckBox(), panelFormulario, constraints, gridy++);
                break;
            case "Audiovisual":
                addCampo("Duración", audiovisualDuracion = new JTextField(32), panelFormulario, constraints, gridy++);
                addCampo("Idioma", audiovisualIdioma = new JTextField(32), panelFormulario, constraints, gridy++);
                break;
        }

        addBotones(panelFormulario, constraints, gridy);
        add(panelFormulario);
    }

    public String formularioTipoObra() {
        String[] opcionesObras = { "Cuadro", "Escultura", "Fotografía", "Audiovisual" };
        JComboBox<String> comboObras = new JComboBox<>(opcionesObras);

        int seleccion = JOptionPane.showConfirmDialog(null, comboObras, "Seleccione un tipo de obra para añadir",
                JOptionPane.OK_CANCEL_OPTION);

        if (seleccion == JOptionPane.OK_OPTION) {
            String obraSeleccionada = (String) comboObras.getSelectedItem();
            return obraSeleccionada;
        }

        return null;
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

    private void addBotones(JPanel panel, GridBagConstraints constraints, int gridy) {
        constraints.gridx = 0;
        constraints.gridy = gridy;
        constraints.gridwidth = 2;
        this.guardarBtn = new JButton("Guardar");
        this.cancelarBtn = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(guardarBtn);
        buttonPanel.add(cancelarBtn);

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

    public String getTipoDeObra() {
        return tipoObraSeleccionada;
    }

    public String getCuadroTecnica() {
        return cuadroTecnica.getText();
    }

    public String getEsculturaMaterial() {
        return esculturaMaterial.getText();
    }

    public String getEsculturaAltura() {
        return esculturaAltura.getText();
    }

    public boolean getFotografiaColor() {
        return fotografiaColor.isSelected();
    }

    public String getAudiovisualDuracion() {
        return audiovisualDuracion.getText();
    }

    public String getAudiovisualIdioma() {
        return audiovisualIdioma.getText();
    }

    public void setControlador(ActionListener cGuardar, ActionListener cCancelar) {
        this.guardarBtn.addActionListener(cGuardar);
        this.cancelarBtn.addActionListener(cCancelar);
        setVisible(true);
    }

}