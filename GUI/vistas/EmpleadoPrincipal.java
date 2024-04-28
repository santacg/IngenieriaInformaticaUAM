package GUI.vistas;

import javax.swing.*;
import java.awt.*;

public class EmpleadoPrincipal extends JPanel {
    private JButton enviarMensajeBoton;
    private JButton cambiarClimatizacionBoton;
    private JButton venderEntradaBoton;

    public EmpleadoPrincipal() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Componentes del formulario
        addTitle(constraints);
        addButtons(constraints);
    }

    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Bienvenido, [NOMBRE EMPLEADO]");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;

        add(titleLabel, constraints);

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
        add(buttonPanel, constraints);
    }
}