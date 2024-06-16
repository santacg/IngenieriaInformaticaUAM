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

    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Perfil del Empleado");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        add(titleLabel, constraints);
        constraints.gridy++;
    }

    private void addTextField(String label, JTextField textField, GridBagConstraints constraints) {
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        add(new JLabel(label), constraints);

        constraints.gridx = 1;
        add(textField, constraints);

        constraints.gridy++;
    }

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

    public void setControlador(ActionListener guardar, ActionListener cancelar) {
        btnGuardarCambios.addActionListener(guardar);
        btnCancelar.addActionListener(cancelar);
    }

    public String getNombre() {
        return txtNombre.getText();
    }

    public String getNumSS() {
        return txtNumSS.getText();
    }

    public String getNumCuenta() {
        return txtNumCuenta.getText();
    }

    public String getDireccion() {
        return txtDireccion.getText();
    }

    public void setDatosEmpleado(String nombre, String numSS, String numCuenta, String direccion) {
        txtNombre.setText(nombre);
        txtNumSS.setText(numSS);
        txtNumCuenta.setText(numCuenta);
        txtDireccion.setText(direccion);
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtNumSS.setText("");
        txtNumCuenta.setText("");
        txtDireccion.setText("");
    }

    public void actualizarCampos(String nombre, String numSS, String numCuenta, String direccion) {
        txtNombre.setText(nombre);
        txtNumSS.setText(numSS);
        txtNumCuenta.setText(numCuenta);
        txtDireccion.setText(direccion);
    }
}
