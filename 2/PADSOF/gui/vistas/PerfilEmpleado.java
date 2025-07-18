package gui.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase PerfilEmpleado.
 * Implementación de la interfaz gráfica para la vista de perfil del empleado.
 */
public class PerfilEmpleado extends JPanel {
    private JTextField txtNombre;
    private JTextField txtNumSS;
    private JTextField txtNumCuenta;
    private JTextField txtDireccion;
    private JButton btnGuardarCambios;
    private JButton btnCancelar;

    /**
     * Constructor de la clase PerfilEmpleado.
     */
    public PerfilEmpleado() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 5, 10); // Agregado un pequeño ajuste en los insets
        constraints.anchor = GridBagConstraints.WEST;

        addTitle(constraints);
        constraints.gridy++;
        addTextField("Nombre:", txtNombre = new JTextField(20), constraints);
        addTextField("Nº de Seguridad Social:", txtNumSS = new JTextField(20), constraints);
        addTextField("Nº de Cuenta Bancaria:", txtNumCuenta = new JTextField(20), constraints);
        addTextField("Dirección:", txtDireccion = new JTextField(20), constraints);
        addButtons(constraints);
    }

    /**
     * Método para agregar el título de la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Perfil del Empleado");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        add(titleLabel, constraints);
        constraints.gridy++;
    }

    /**
     * Método para agregar un campo de texto.
     * 
     * @param label       Etiqueta del campo de texto.
     * @param textField   Campo de texto.
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addTextField(String label, JTextField textField, GridBagConstraints constraints) {
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        add(new JLabel(label), constraints);

        constraints.gridx = 1;
        add(textField, constraints);

        constraints.gridy++;
    }

    /**
     * Método para agregar los botones de la vista.
     * 
     * @param constraints Constraints para el GridBagLayout.
     */
    private void addButtons(GridBagConstraints constraints) {
        btnGuardarCambios = new JButton("Guardar Cambios");
        btnCancelar = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnGuardarCambios);
        buttonPanel.add(btnCancelar);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy++;
        add(buttonPanel, constraints);
    }

    /**
     * Método para establecer el controlador de la vista.
     * 
     * @param guardar  ActionListener para el botón de guardar cambios.
     * @param cancelar ActionListener para el botón de cancelar.
     */
    public void setControlador(ActionListener guardar, ActionListener cancelar) {
        btnGuardarCambios.addActionListener(guardar);
        btnCancelar.addActionListener(cancelar);
    }

    /**
     * Método para obtener el nombre del empleado.
     * 
     * @return Nombre del empleado.
     */
    public String getNombre() {
        return txtNombre.getText();
    }

    /**
     * Método para obtener el número de la Seguridad Social del empleado.
     * 
     * @return Número de la Seguridad Social del empleado.
     */
    public String getNumSS() {
        return txtNumSS.getText();
    }

    /**
     * Método para obtener el número de cuenta bancaria del empleado.
     * 
     * @return Número de cuenta bancaria del empleado.
     */
    public String getNumCuenta() {
        return txtNumCuenta.getText();
    }

    /**
     * Método para obtener la dirección del empleado.
     * 
     * @return Dirección del empleado.
     */
    public String getDireccion() {
        return txtDireccion.getText();
    }

    /**
     * Método para establecer los datos del empleado en los campos de texto.
     * 
     * @param nombre    Nombre del empleado.
     * @param numSS     Número de la Seguridad Social del empleado.
     * @param numCuenta Número de cuenta bancaria del empleado.
     * @param direccion Dirección del empleado.
     */
    public void setDatosEmpleado(String nombre, String numSS, String numCuenta, String direccion) {
        txtNombre.setText(nombre);
        txtNumSS.setText(numSS);
        txtNumCuenta.setText(numCuenta);
        txtDireccion.setText(direccion);
    }

    /**
     * Método para limpiar los campos de texto.
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtNumSS.setText("");
        txtNumCuenta.setText("");
        txtDireccion.setText("");
    }

    /**
     * Método para actualizar los campos de texto con los datos del empleado.
     * 
     * @param nombre    Nombre del empleado.
     * @param numSS     Número de la Seguridad Social del empleado.
     * @param numCuenta Número de cuenta bancaria del empleado.
     * @param direccion Dirección del empleado.
     */
    public void actualizarCampos(String nombre, String numSS, String numCuenta, String direccion) {
        txtNombre.setText(nombre);
        txtNumSS.setText(numSS);
        txtNumCuenta.setText(numCuenta);
        txtDireccion.setText(direccion);
    }
}
