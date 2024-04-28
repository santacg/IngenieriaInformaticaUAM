package GUI.vistas;

import javax.swing.*;
import java.awt.*;

public class ClientePrincipal extends JPanel {
    private JButton sorteosBoton;
    private JButton perfilBoton;
    private JButton exposicionesBoton;

    public ClientePrincipal() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Componentes del formulario
        addTitle(constraints);
        addButtons(constraints);
    }

    private void addTitle(GridBagConstraints constraints) {
        JLabel titleLabel = new JLabel("Bienvenido, [NOMBRE CLIENTE]");
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
        this.sorteosBoton = new JButton("Búsqueda de exposiciones");
        this.exposicionesBoton = new JButton("Sorteos");
        this.perfilBoton = new JButton("Venta de entradas");

        buttonPanel.add(this.sorteosBoton);
        buttonPanel.add(this.exposicionesBoton);
        buttonPanel.add(this.perfilBoton);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(buttonPanel, constraints);
    }
}