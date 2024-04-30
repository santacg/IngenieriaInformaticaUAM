package GUI.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import GUI.modelo.centroExposicion.Empleado;

public class EmpleadoPrincipal extends JPanel {
    private JButton enviarMensajeBoton;
    private JButton cambiarClimatizacionBoton;
    private JButton venderEntradaBoton;
    private JPanel panelPrincipal;
    private JButton cerrarSesionBoton;

    public EmpleadoPrincipal() {
        setLayout(new BorderLayout());
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());

        JPanel panelCerrarSesion = new JPanel();
        panelCerrarSesion.setLayout(new BorderLayout());
        cerrarSesionBoton = new JButton("Cerrar Sesión");
        panelCerrarSesion.add(cerrarSesionBoton, BorderLayout.EAST);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Componentes del formulario
        addTitle(constraints);
        addButtons(constraints);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelCerrarSesion, BorderLayout.NORTH);
    }

    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Bienvenido, [NOMBRE EMPLEADO]");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(titleLabel, constraints);

    }
    public void addButtons(GridBagConstraints constraints) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.enviarMensajeBoton = new JButton("Envío de mensajes");
        this.cambiarClimatizacionBoton = new JButton("Cambiar Climatización");
        this.venderEntradaBoton = new JButton("Venta de entradas");

        buttonPanel.add(this.enviarMensajeBoton);
        buttonPanel.add(this.cambiarClimatizacionBoton);
        buttonPanel.add(this.venderEntradaBoton);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        panelPrincipal.add(buttonPanel, constraints);
    }

    public void setControlador(ActionListener cMensaje, ActionListener cClimatizacion, ActionListener cCerrarSesion) {
        
        this.enviarMensajeBoton.addActionListener(cMensaje);
        this.cambiarClimatizacionBoton.addActionListener(cClimatizacion);
        this.cerrarSesionBoton.addActionListener(cCerrarSesion);
    }


}