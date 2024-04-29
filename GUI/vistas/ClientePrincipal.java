package GUI.vistas;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.expofy.*;
import GUI.modelo.exposicion.Exposicion;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientePrincipal extends JPanel {
    private JPanel buscarExposiciones;
    private JButton comprarBoton;
    private JPanel sorteos;
    private JPanel perfil;
    private JTable tablaExposiciones;
    public ClientePrincipal() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        this.buscarExposiciones = new JPanel();
        buscarExposiciones.setLayout(new BorderLayout());
        this.sorteos = new JPanel();
        this.perfil = new JPanel();

        tabbedPane.add("Exposiciones",buscarExposiciones);
        tabbedPane.add("Sorteos",sorteos);
        tabbedPane.add("Perfil",perfil);

        add(tabbedPane, BorderLayout.CENTER);

    }

    public void addTablaExposiciones(Expofy expofy) {
        String[] titulos = { "Nombre", "Descripcion", "Fecha Inicio", "Fecha Fin", "Precio", "Nombre Centro",
                "Localizacion" };

        ArrayList<Object[]> data = new ArrayList<>();
        for (CentroExposicion centro : expofy.getCentrosExposicion()) {
            for (Exposicion exposicion : centro.getExposiciones()) {
                data.add(new Object[] {
                        exposicion.getNombre(),
                        exposicion.getDescripcion(),
                        exposicion.getFechaInicio(),
                        exposicion.getFechaFin(),
                        exposicion.getPrecio(),
                        centro.getNombre(),
                        centro.getLocalizacion()
                });
            }
        }
        Object[][] datos = data.toArray(new Object[0][]);
        tablaExposiciones = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        JPanel panelBoton = new JPanel();
        comprarBoton = new JButton("Comprar");
        tablaExposiciones.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaExposiciones.setFillsViewportHeight(true);

        tablaExposiciones.getSelectedRow();
        panelBoton.add(comprarBoton);
        buscarExposiciones.add(panelBoton, BorderLayout.SOUTH);
        this.buscarExposiciones.add(new JScrollPane(tablaExposiciones), BorderLayout.CENTER);
    }

    public void addPerfil(ClienteRegistrado cliente) {
        perfil.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel titleLabel = new JLabel("Perfil");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;
        
        perfil.add(titleLabel);

        JLabel labelUser = new JLabel("DNI/NIF: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        perfil.add(labelUser, constraints);

        JLabel name = new JLabel(cliente.getNIF());
        constraints.gridx = 1;
        constraints.gridy = 1;
        perfil.add(name, constraints);

        JLabel labelPassword = new JLabel("Contraseña: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        perfil.add(labelPassword, constraints);

        JLabel password = new JLabel(cliente.getContrasenia());
        constraints.gridx = 1;
        constraints.gridy = 2;
        perfil.add(password, constraints);

        JLabel labelPublicidad = new JLabel("Publicidad: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        perfil.add(labelPublicidad, constraints);

        JCheckBox checkBoxPublicidad = new JCheckBox();
        if (checkBoxPublicidad.isSelected()) {
            cliente.setPublicidad(true);
        }
        else{
            cliente.setPublicidad(true);
        }
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        perfil.add(checkBoxPublicidad, constraints);
    }

    public void setControlador(ActionListener cComprar) {
        comprarBoton.addActionListener(cComprar);
    }

    public JTable getTablaExposiciones(){
        return tablaExposiciones;
    }
}
