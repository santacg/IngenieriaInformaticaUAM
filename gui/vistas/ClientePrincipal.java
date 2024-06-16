package gui.vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import gui.controlador.ControladorCompraFormulario;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Clase ClientePrincipal.
 * Implementa la interfaz de la vista principal del cliente.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ClientePrincipal extends JPanel {
    private JPanel buscarExposiciones;
    private JButton comprarBoton;
    private JButton filtrarFechaBoton;
    private JButton filtrarTempBoton;
    private JButton filtrarTipoBoton;
    private JButton eliminarFiltrosBoton;
    private JTable tablaExposiciones;

    private CompraFormulario vistaCompraFormulario;
    private ControladorCompraFormulario controladorCompraFormulario;

    private JPanel sorteos;
    private JTable tablaSorteos;

    private JPanel notificaciones;
    private JTable tablaNotificaciones;
    private JButton inscribirseBoton;

    private JPanel perfil;
    private JCheckBox checkBoxPublicidad;
    private JButton actualizarBoton;
    private JTextField fieldContrasena;
    private JTextField fieldContrasenaConfirmar;

    private JButton cerrarSesionBoton;

    /**
     * Constructor de la clase ClientePrincipal.
     */
    public ClientePrincipal() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();
        this.buscarExposiciones = new JPanel();
        buscarExposiciones.setLayout(new BorderLayout());

        this.notificaciones = new JPanel();
        notificaciones.setLayout(new BorderLayout());

        this.sorteos = new JPanel();
        sorteos.setLayout(new BorderLayout());

        this.perfil = new JPanel();

        JPanel panelCerrarSesion = new JPanel();
        panelCerrarSesion.setLayout(new BorderLayout());
        cerrarSesionBoton = new JButton("Cerrar Sesión");
        panelCerrarSesion.add(cerrarSesionBoton, BorderLayout.EAST);

        tabbedPane.add("Exposiciones", buscarExposiciones);
        tabbedPane.add("Sorteos", sorteos);
        tabbedPane.add("Perfil", perfil);
        tabbedPane.add("Notificaciones", notificaciones);
        add(panelCerrarSesion, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

    }

    /**
     * Método que añade una tabla de exposiciones a la vista.
     * 
     * @param data ArrayList de objetos que contiene los datos de las exposiciones.
     */
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

        filtrarFechaBoton = new JButton("Fecha");
        filtrarTempBoton = new JButton("Temporalidad");
        filtrarTipoBoton = new JButton("Tipo de obra");
        eliminarFiltrosBoton = new JButton("Eliminar filtros");
        tablaExposiciones.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaExposiciones.setFillsViewportHeight(true);

        panelBoton.add(comprarBoton);
        panelBoton.add(new JLabel("Filtros:"));
        panelBoton.add(filtrarFechaBoton);
        panelBoton.add(filtrarTempBoton);
        panelBoton.add(filtrarTipoBoton);
        panelBoton.add(eliminarFiltrosBoton);

        buscarExposiciones.add(panelBoton, BorderLayout.SOUTH);
        this.buscarExposiciones.add(new JScrollPane(tablaExposiciones), BorderLayout.CENTER);
    }

    /**
     * Método que añade una tabla de notificaciones a la vista.
     * 
     * @param data ArrayList de objetos que contiene los datos de las
     *             notificaciones.
     */
    public void addTablaNotificaciones(ArrayList<Object[]> data) {
        String[] titulos = { "Fecha", "Mensaje" };

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

    /**
     * Método que añade una tabla de sorteos a la vista.
     * 
     * @param data ArrayList de objetos que contiene los datos de las
     *             sorteos.
     */
    public void addTablaSorteos(ArrayList<Object[]> data) {
        String[] titulos = { "Fecha de inscripción", "Exposición", "Descripcion", "Fecha Inicio", "Fecha Fin",
                "Nombre Centro", "Localizacion" };

        Object[][] datos = data.toArray(new Object[0][]);
        tablaSorteos = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        JPanel panelBoton = new JPanel();
        inscribirseBoton = new JButton("Inscribirse");
        tablaSorteos.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaSorteos.setFillsViewportHeight(true);

        panelBoton.add(inscribirseBoton);
        sorteos.add(panelBoton, BorderLayout.SOUTH);
        this.sorteos.add(new JScrollPane(tablaSorteos), BorderLayout.CENTER);
    }

    /**
     * Método que añade un perfil a la vista.
     * 
     * @param clienteNIF        DNI/NIF del cliente.
     * @param clienteContrasena Contraseña del cliente.
     * @param clientePublicidad Booleano que indica si el cliente quiere recibir
     *                          publicidad.
     */
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

        perfil.add(perfil_data, BorderLayout.CENTER);
        perfil.add(perfil_actualizar, BorderLayout.SOUTH);
    }

    /**
     * Actualiza la tabla de exposiciones.
     * 
     * @param centro Centro de exposiciones.
     */
    public void actualizarTablaExposiciones(ArrayList<Object[]> data) {
        String[] titulos = { "Nombre", "Descripcion", "Fecha Inicio", "Fecha Fin", "Precio", "Nombre Centro",
                "Localizacion" };

        Object[][] datos = data.toArray(new Object[0][]);
        DefaultTableModel model = new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaExposiciones.setModel(model);
    }

    /**
     * Método que establece los controladores de los botones de la vista.
     * 
     * @param cComprar      Controlador del botón de comprar.
     * @param cActualizar   Controlador del botón de actualizar.
     * @param cCerrarSesion Controlador del botón de cerrar sesión.
     * @param cInscribirse  Controlador del botón de inscribirse.
     */
    public void setControlador(ActionListener cComprar, ActionListener cActualizar, ActionListener cCerrarSesion,
            ActionListener cInscribirse, ActionListener cFiltroFecha, ActionListener cFiltroTemp,
            ActionListener cFiltroTipoObra, ActionListener cEliminarFiltros) {
        comprarBoton.addActionListener(cComprar);
        actualizarBoton.addActionListener(cActualizar);
        cerrarSesionBoton.addActionListener(cCerrarSesion);
        inscribirseBoton.addActionListener(cInscribirse);
        filtrarFechaBoton.addActionListener(cFiltroFecha);
        filtrarTempBoton.addActionListener(cFiltroTemp);
        filtrarTipoBoton.addActionListener(cFiltroTipoObra);
        eliminarFiltrosBoton.addActionListener(cEliminarFiltros);
    }

    /**
     * Método que borra los controladores de los botones de la vista.
     * 
     * @param cComprar      Controlador del botón de comprar.
     * @param cActualizar   Controlador del botón de actualizar.
     * @param cCerrarSesion Controlador del botón de cerrar sesión.
     * @param cInscribirse  Controlador del botón de inscribirse.
     */
    public void removeControlador(ActionListener cComprar, ActionListener cActualizar, ActionListener cCerrarSesion,
            ActionListener cInscribirse, ActionListener cFiltroFecha, ActionListener cFiltroTemp,
            ActionListener cFiltroTipoObra, ActionListener cEliminarFiltros) {
        comprarBoton.removeActionListener(cComprar);
        actualizarBoton.removeActionListener(cActualizar);
        cerrarSesionBoton.removeActionListener(cCerrarSesion);
        inscribirseBoton.removeActionListener(cInscribirse);
        filtrarFechaBoton.removeActionListener(cFiltroFecha);
        filtrarTempBoton.removeActionListener(cFiltroTemp);
        filtrarTipoBoton.removeActionListener(cFiltroTipoObra);
        eliminarFiltrosBoton.removeActionListener(cEliminarFiltros);
    }

    /**
     * Método que devuelve la vista de compra de entradas.
     * 
     * @param exposicionNombre Nombre de la exposición.
     * @param precio           Precio de la entrada.
     * @return Vista de compra de entradas.
     */
    public CompraFormulario getVistaCompraFormulario(String exposicionNombre, double precio) {
        this.vistaCompraFormulario = new CompraFormulario(exposicionNombre, precio);
        return this.vistaCompraFormulario;
    }

    /**
     * Método que establece el controlador de la vista de compra de entradas.
     * 
     * @param controlador Controlador de la vista de compra de entradas.
     */
    public void setCompraFormularioControlador(ControladorCompraFormulario controlador) {
        this.controladorCompraFormulario = controlador;
        this.vistaCompraFormulario.setControlador(controladorCompraFormulario.getSiguienteListener(),
                controladorCompraFormulario.getCancelarListener(), controladorCompraFormulario.getdiaExpoListener(),
                controladorCompraFormulario.getMesExpoListener(), controladorCompraFormulario.getAnioExpoListener(),
                controladorCompraFormulario.getdiaTarjListener(), controladorCompraFormulario.getMesTarjListener(),
                controladorCompraFormulario.getAnioTarjListener(), controladorCompraFormulario.getNentradasListener(),
                controladorCompraFormulario.getHoraListener());
    }

    /**
     * Método que devuelve la tabla de exposiciones.
     * 
     * @return Tabla de exposiciones.
     */
    public JTable getTablaExposiciones() {
        return tablaExposiciones;
    }

    /**
     * Método que devuelve la tabla de sorteos.
     * 
     * @return Tabla de sorteos.
     */
    public JTable getTablaSorteos() {
        return tablaSorteos;
    }

    /**
     * Método que devuelve la CheckBox de publicidad.
     * 
     * @return CheckBox de publicidad.
     */
    public JCheckBox getCheckBoxPublicidad() {
        return checkBoxPublicidad;
    }

    /**
     * Método que devuelve la contraseña introducida en el campo de texto.
     * 
     * @return Contraseña introducida en el campo de texto.
     */
    public String getFieldContrasena() {
        return fieldContrasena.getText();
    }

    /**
     * Método que devuelve la confirmación de la contraseña introducida en el campo
     * de texto.
     * 
     * @return Confirmación de la contraseña introducida en el campo de texto.
     */
    public String getFieldContrasenaConfirmar() {
        return fieldContrasenaConfirmar.getText();
    }
}
