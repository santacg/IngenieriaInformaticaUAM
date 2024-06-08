package gui.vistas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Clase ObraFormulario.
 * Implementa la interfaz gráfica para el formulario de añadir una obra.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ObraFormulario extends JDialog {

    private JTextField obraNombre;
    private JTextField obraAutores;
    private JTextField obraDescripcion;
    private JTextField obraAnio;
    private JCheckBox obraExterna;
    private JTextField obraCuantiaSeguro;
    private JTextField obraNumeroSeguro;
    private JTextField obraAlto;
    private JTextField obraAncho;
    private JTextField obraRangoTemperatura;
    private JTextField obraRangoHumedad;
    private JTextField cuadroTecnica;
    private JTextField esculturaProfundidad;
    private JTextField esculturaMaterial;
    private JCheckBox fotografiaColor;
    private JTextField audiovisualDuracion;
    private JTextField audiovisualIdioma;
    private String tipoObraSeleccionada;
    private JButton guardarBtn;
    private JButton cancelarBtn;

    /**
     * Constructor de la clase ObraFormulario.
     */
    public ObraFormulario() {
        setTitle("Agregar obra");
        setSize(600, 700);
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
        addCampo("Alto", obraAlto = new JTextField(32), panelFormulario, constraints, 7);
        addCampo("Ancho", obraAncho = new JTextField(32), panelFormulario, constraints, 8);

        if (tipoObraSeleccionada.equals("Escultura") || tipoObraSeleccionada.equals("Cuadro")
                || tipoObraSeleccionada.equals("Fotografia")) {
            addCampo("Rango temperatura", obraRangoTemperatura = new JTextField(32), panelFormulario, constraints, 9);
            addCampo("Rango Humedad", obraRangoHumedad = new JTextField(32), panelFormulario, constraints, 10);
        }

        int gridy = 11;
        switch (tipoObraSeleccionada) {
            case "Cuadro":
                addCampo("Técnica", cuadroTecnica = new JTextField(32), panelFormulario, constraints, gridy++);
                break;
            case "Escultura":
                addCampo("Material", esculturaMaterial = new JTextField(32), panelFormulario, constraints, gridy++);
                addCampo("Profundidad", esculturaProfundidad = new JTextField(32), panelFormulario, constraints,
                        gridy++);
                break;
            case "Fotografia":
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

    /**
     * Muestra un diálogo para seleccionar el tipo de obra a añadir.
     * 
     * @return String con el tipo de obra seleccionado.
     */
    public String formularioTipoObra() {
        String[] opcionesObras = { "Cuadro", "Escultura", "Fotografia", "Audiovisual" };
        JComboBox<String> comboObras = new JComboBox<>(opcionesObras);

        int seleccion = JOptionPane.showConfirmDialog(null, comboObras, "Seleccione un tipo de obra para añadir",
                JOptionPane.OK_CANCEL_OPTION);

        if (seleccion == JOptionPane.OK_OPTION) {
            String obraSeleccionada = (String) comboObras.getSelectedItem();
            return obraSeleccionada;
        }

        return null;
    }

    /**
     * Añade un campo al formulario.
     * 
     * @param label       Etiqueta del campo.
     * @param comp        Componente del campo.
     * @param panel       Panel donde se añade el campo.
     * @param constraints Restricciones del GridBagLayout.
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
     * Añade los botones de guardar y cancelar al formulario.
     * 
     * @param panel       Panel donde se añaden los botones.
     * @param constraints Restricciones del GridBagLayout.
     * @param gridy       Posición en el grid.
     */
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

    /**
     * Devuelve el nombre de la obra introducido en el formulario.
     * 
     * @return String con el nombre de la obra.
     */
    public String getObraNombre() {
        return obraNombre.getText();
    }

    /**
     * Devuelve los autores de la obra introducidos en el formulario.
     * 
     * @return String con los autores de la obra.
     */
    public String getObraAutores() {
        return obraAutores.getText();
    }

    /**
     * Devuelve la descripción de la obra introducida en el formulario.
     * 
     * @return String con la descripción de la obra.
     */
    public String getObraDescripcion() {
        return obraDescripcion.getText();
    }

    /**
     * Devuelve el año de la obra introducido en el formulario.
     * 
     * @return String con el año de la obra.
     */
    public String getObraAnio() {
        return obraAnio.getText();
    }

    /**
     * Devuelve si la obra es externa o no.
     * 
     * @return Booleano que indica si la obra es externa.
     */
    public boolean getObraExterna() {
        return obraExterna.isSelected();
    }

    /**
     * Devuelve la cuantía del seguro de la obra introducida en el formulario.
     * 
     * @return String con la cuantía del seguro de la obra.
     */
    public String getObraCuantiaSeguro() {
        return obraCuantiaSeguro.getText();
    }

    /**
     * Devuelve el número de seguro de la obra introducido en el formulario.
     * 
     * @return String con el número de seguro de la obra.
     */
    public String getObraNumeroSeguro() {
        return obraNumeroSeguro.getText();
    }

    /**
     * Devuelve el tipo de obra seleccionado en el formulario.
     * 
     * @return String con el tipo de obra seleccionado.
     */
    public String getTipoDeObra() {
        return tipoObraSeleccionada;
    }

    /**
     * Devuelve la técnica del cuadro introducida en el formulario.
     * 
     * @return String con la técnica del cuadro.
     */
    public String getCuadroTecnica() {
        return cuadroTecnica.getText();
    }

    /**
     * Devuelve el material de la escultura introducido en el formulario.
     * 
     * @return String con el material de la escultura.
     */
    public String getEsculturaMaterial() {
        return esculturaMaterial.getText();
    }

    /**
     * Devuelve la profundidad de la escultura introducida en el formulario.
     * 
     * @return String con la profundidad de la escultura.
     */
    public String getEsculturaProfundidad() {
        return esculturaProfundidad.getText();
    }

    /**
     * Devuelve si la fotografía es en color o no.
     * 
     * @return Booleano que indica si la fotografía es en color.
     */
    public boolean getFotografiaColor() {
        return fotografiaColor.isSelected();
    }

    /**
     * Devuelve la duración del audiovisual introducida en el formulario.
     * 
     * @return String con la duración del audiovisual.
     */
    public String getAudiovisualDuracion() {
        return audiovisualDuracion.getText();
    }

    /**
     * Devuelve el idioma del audiovisual introducido en el formulario.
     * 
     * @return String con el idioma del audiovisual.
     */
    public String getAudiovisualIdioma() {
        return audiovisualIdioma.getText();
    }

    /**
     * Devuelve el alto de la obra introducido en el formulario.
     * 
     * @return String con el alto de la obra.
     */
    public String getObraAlto() {
        return obraAlto.getText();
    }

    /**
     * Devuelve el ancho de la obra introducido en el formulario.
     * 
     * @return String con el ancho de la obra.
     */
    public String getObraAncho() {
        return obraAncho.getText();
    }

    /**
     * Devuelve el rango de temperatura de la obra introducido en el formulario.
     * 
     * @return String con el rango de temperatura de la obra.
     */
    public String getObraRangoTemperatura() {
        return obraRangoTemperatura.getText();
    }

    /**
     * Devuelve la temperatura minima de la obra introducido en el formulario.
     * 
     * @return String con la temperatura minima de la obra.
     */
    public String getObraTemperaturaMin() {
        String rango = obraRangoTemperatura.getText();
        String[] valores = rango.split("-");
        return valores[0];
    }

    /**
     * Devuelve la temperatura maxima de la obra introducido en el formulario.
     * 
     * @return String con la temperatura maxima de la obra.
     */
    public String getObraTemperaturaMax() {
        String rango = obraRangoTemperatura.getText();
        String[] valores = rango.split("-");
        return valores[1];
    }

    /**
     * Devuelve la humedad minima de la obra introducido en el formulario.
     * 
     * @return String con la humedad minima de la obra.
     */
    public String getObraHumedadMin() {
        String rango = obraRangoHumedad.getText();
        String[] valores = rango.split("-");
        return valores[0];
    }

    /**
     * Devuelve la humedad maxima de la obra introducido en el formulario.
     * 
     * @return String con la humedad maxima de la obra.
     */
    public String getObraHumedadMax() {
        String rango = obraRangoHumedad.getText();
        String[] valores = rango.split("-");
        return valores[1];
    }

    /**
     * Devuelve el rango de humedad de la obra introducido en el formulario.
     * 
     * @return String con el rango de humedad de la obra.
     */
    public String getObraRangoHumedad() {
        return obraRangoHumedad.getText();
    }

    /**
     * Establece los controladores para los botones de guardar y cancelar.
     * 
     * @param cGuardar  Controlador del botón de guardar.
     * @param cCancelar Controlador del botón de cancelar.
     */
    public void setControlador(ActionListener cGuardar, ActionListener cCancelar) {
        this.guardarBtn.addActionListener(cGuardar);
        this.cancelarBtn.addActionListener(cCancelar);
        setVisible(true);
    }

}