package GUI.vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientePrincipal extends JPanel {
    private JPanel buscarExposiciones;
    private JButton comprarBoton;
    private JTable tablaExposiciones;

    
    private JPanel sorteos;

    private JPanel notificaciones;
    private JTable tablaNotificaciones;
    
    private JPanel perfil;
    private JCheckBox checkBoxPublicidad;
    private JButton actualizarBoton;
    private JTextField fieldContrasena;
    private JTextField fieldContrasenaConfirmar;

    private JButton cerrarSesionBoton;
    
    public ClientePrincipal() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        this.buscarExposiciones = new JPanel();
        buscarExposiciones.setLayout(new BorderLayout());

        this.notificaciones = new JPanel();
        notificaciones.setLayout(new BorderLayout());

        this.sorteos = new JPanel();

        this.perfil = new JPanel();

        JPanel panelCerrarSesion = new JPanel();
        panelCerrarSesion.setLayout(new BorderLayout());
        cerrarSesionBoton = new JButton("Cerrar Sesión");
        panelCerrarSesion.add(cerrarSesionBoton, BorderLayout.EAST);

        tabbedPane.add("Exposiciones",buscarExposiciones);
        tabbedPane.add("Sorteos",sorteos);
        tabbedPane.add("Perfil",perfil);
        tabbedPane.add("Notificaciones",notificaciones);
        add(panelCerrarSesion, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

    }

    public void addTablaExposiciones(ArrayList<Object[]> data) {
        String[] titulos = { "Nombre", "Descripcion", "Fecha Inicio", "Fecha Fin", "Precio", "Nombre Centro",
                "Localizacion" };

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

        panelBoton.add(comprarBoton);
        buscarExposiciones.add(panelBoton, BorderLayout.SOUTH);
        this.buscarExposiciones.add(new JScrollPane(tablaExposiciones), BorderLayout.CENTER);
    }

    public void addTablaNotificaciones(ArrayList<Object[]> data) {
        String[] titulos = { "Fecha", "Mensaje"};

        Object[][] datos = data.toArray(new Object[0][]);
        tablaNotificaciones = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tablaNotificaciones.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaNotificaciones.setFillsViewportHeight(true);

        this.notificaciones.add(new JScrollPane(tablaNotificaciones), BorderLayout.CENTER);
    }

    public void addPerfil(String clienteNIF, String clienteContrasena, boolean clientePublicidad) {
        perfil.setLayout(new BorderLayout());
        JPanel perfil_actualizar = new JPanel();
        perfil_actualizar.setLayout(new GridBagLayout());

        JPanel perfil_data = new JPanel();
        perfil_data.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel titleLabel = new JLabel("Perfil");
        titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;
        
        perfil_data.add(titleLabel);

        JLabel labelUser = new JLabel("DNI/NIF: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        perfil_data.add(labelUser, constraints);

        JLabel name = new JLabel(clienteNIF);
        constraints.gridx = 1;
        constraints.gridy = 1;
        perfil_data.add(name, constraints);

        JLabel labelPassword = new JLabel("Contraseña: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        perfil_data.add(labelPassword, constraints);

        JLabel password = new JLabel(clienteContrasena);
        constraints.gridx = 1;
        constraints.gridy = 2;
        perfil_data.add(password, constraints);

        JLabel labelPublicidad = new JLabel("Publicidad: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        perfil_data.add(labelPublicidad, constraints);

        checkBoxPublicidad = new JCheckBox();
        checkBoxPublicidad.setSelected(clientePublicidad);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        perfil_data.add(checkBoxPublicidad, constraints);
        

        JLabel lablelChangePassword = new JLabel("Nueva Contraseña: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        perfil_actualizar.add(lablelChangePassword, constraints);

        fieldContrasena = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        perfil_actualizar.add(fieldContrasena, constraints);

        JLabel lablelChangePasswordConfirmar = new JLabel("Repetir Nueva Contraseña: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        perfil_actualizar.add(lablelChangePasswordConfirmar, constraints);

        fieldContrasenaConfirmar = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        perfil_actualizar.add(fieldContrasenaConfirmar, constraints);

        actualizarBoton = new JButton("Actualizar datos");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        perfil_actualizar.add(actualizarBoton, constraints);

        perfil.add(perfil_data,BorderLayout.CENTER);
        perfil.add(perfil_actualizar,BorderLayout.SOUTH);
    }

    public void setControlador(ActionListener cComprar, ActionListener cActualizar, ActionListener cCerrarSesion) {
        comprarBoton.addActionListener(cComprar);
        actualizarBoton.addActionListener(cActualizar);
        cerrarSesionBoton.addActionListener(cCerrarSesion);
    }

    public JTable getTablaExposiciones(){
        return tablaExposiciones;
    }

    public JCheckBox getCheckBoxPublicidad(){
        return checkBoxPublicidad;
    }

    public String getFieldContrasena(){
        return fieldContrasena.getText();
    }
    public String getFieldContrasenaConfirmar(){
        return fieldContrasenaConfirmar.getText();
    }
}
