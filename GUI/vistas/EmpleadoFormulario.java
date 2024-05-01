package GUI.vistas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Clase EmpleadoFormulario
 * Implementa la vista de formulario de empleado.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class EmpleadoFormulario extends JDialog {
    private JTextField empleadoNIF;
    private JTextField empleadoNombre;
    private JTextField empleadoNumSS;
    private JTextField empleadoNumCuenta;
    private JTextField empleadoDireccion;
    private JCheckBox permisoVenta;
    private JCheckBox permisoControl;
    private JCheckBox permisoMensajes;
    private JButton guardarBtn;
    private JButton cancelarBtn;

    /**
     * COnstructor de la clase EmpleadoFormulario.
     */
    public EmpleadoFormulario() {
        setTitle("Agregar empleado");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setModal(true);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        addCampo("NIF:", this.empleadoNIF = new JTextField(20), panelFormulario, constraints, 0);
        addCampo("Nombre:", this.empleadoNombre = new JTextField(20), panelFormulario, constraints, 1);
        addCampo("Número de la Seguridad Social:", this.empleadoNumSS = new JTextField(20), panelFormulario, constraints,
                2);
        addCampo("Número de cuenta:", this.empleadoNumCuenta = new JTextField(20), panelFormulario, constraints, 3);
        addCampo("Dirección:", this.empleadoDireccion = new JTextField(20), panelFormulario, constraints, 4);
        addCampo("Permiso de venta:", this.permisoVenta = new JCheckBox(), panelFormulario, constraints, 5);
        addCampo("Permiso de control:", this.permisoControl = new JCheckBox(), panelFormulario, constraints, 6);
        addCampo("Permiso de mensajes:", this.permisoMensajes = new JCheckBox(), panelFormulario, constraints, 7);

        addBotones(panelFormulario, constraints, 8);
        add(panelFormulario);
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
     * Metodo que devuelve el NIF del empleado
     * @return String con el NIF del empleado
     */
    public String getEmpleadoNIF() {
        return empleadoNIF.getText();
    }

    /**
     * Metodo que devuelve el nombre del empleado
     * @return String con el nombre del empleado
     */
    public String getEmpleadoNombre() {
        return empleadoNombre.getText();
    }

    /**
     * Metodo que devuelve el numero de la seguridad social del empleado
     * @return String con el numero de la seguridad social del empleado
     */
    public String getEmpleadoNumSS() {
        return empleadoNumSS.getText();
    }

    /**
     * Metodo que devuelve la direccion del empleado
     * @return String con la direccion del empleado
     */
    public String getEmpleadoDireccion() {
        return empleadoDireccion.getText();
    }

    /**
     * Metodo que devuelve si el empleado tiene permiso de venta
     * @return boolean con si el empleado tiene permiso de venta
     */
    public boolean getPermisoVenta() {
        return permisoVenta.isSelected();
    }

    /**
     * Metodo que devuelve si el empleado tiene permiso de control
     * @return boolean con si el empleado tiene permiso de control
     */
    public boolean getPermisoControl() {
        return permisoControl.isSelected();
    }

    /**
     * Metodo que devuelve si el empleado tiene permiso de mensajes
     * @return boolean con si el empleado tiene permiso de mensajes
     */
    public boolean getPermisoMensajes() {
        return permisoMensajes.isSelected();
    }

    /**
     * Metodo que devuelve el numero de cuenta del empleado
     * @return String con el numero de cuenta del empleado
     */
    public String getEmpleadoNumCuenta() {
        return empleadoNumCuenta.getText();
    }

    /**
     * Metodo que establece el controlador del formulario del empleado
     * 
     * @param cGuardar ActionListener con el controlador del boton de guardar
     * @param cCancelar ActionListener con el controlador del boton de cancelar
     * 
     */
    public void setControlador(ActionListener cGuardar, ActionListener cCancelar) {
        guardarBtn.addActionListener(cGuardar);
        cancelarBtn.addActionListener(cCancelar);
        setVisible(true);
    }
}
