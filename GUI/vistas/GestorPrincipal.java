package GUI.vistas;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import GUI.controlador.*;
import GUI.modelo.centroExposicion.*;
import GUI.modelo.exposicion.Exposicion;
import GUI.modelo.obra.Obra;
import GUI.modelo.sala.Sala;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase GestorPrincipal.
 * Implementa la interfaz de usuario para la gestión de exposiciones, salas y
 * obras.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class GestorPrincipal extends JPanel {

    private JPanel gestionExposiciones;
    private JPanel gestionSalas;
    private JPanel gestionObras;
    private JPanel gestionEmpleados;
    private JPanel gestionSorteos;
    private JPanel gestionDescuentos;

    private ObraFormulario vistaObraFormulario;
    private ControladorObraFormulario controladorObraFormulario;

    private SalaFormulario vistaSalaFormulario;
    private ControladorSalaFormulario controladorSalaFormulario;

    private ExposicionFormulario vistaExposicionFormulario;
    private ControladorExposicionFormulario controladorExposicionFormulario;

    private EmpleadoFormulario vistaEmpleadoFormulario;
    private ControladorEmpleadoFormulario controladorEmpleadoFormulario;

    // Obras atributos
    private JButton obraEjecutarBtn;
    private JComboBox<String> obraComboAcciones;
    private JTable tablaObras;
    private JButton obraAgregarBtn;
    private JButton leerObrasCSVBtn;

    // Salas atributos
    private JButton salaEjecutarBtn;
    private JComboBox<String> salaComboAcciones;
    private JTable tablaSalas;

    // Expociones atributos
    private JButton exposicionEjecutarBtn;
    private JComboBox<String> exposicionComboAcciones;
    private JTable tablaExposiciones;
    private JButton exposicionAgregarBtn;

    // Empleados atributos
    private JTable tablaEmpleados;
    private JButton empleadoAgregarBtn;

    private JButton cerrarSesionBtn;

    /**
     * Constructor de la clase GestorPrincipal.
     */
    public GestorPrincipal() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        this.gestionExposiciones = new JPanel();
        gestionExposiciones.setLayout(new BorderLayout());

        this.gestionSalas = new JPanel();
        gestionSalas.setLayout(new BorderLayout());

        this.gestionObras = new JPanel();
        gestionObras.setLayout(new BorderLayout());

        this.gestionEmpleados = new JPanel();
        gestionEmpleados.setLayout(new BorderLayout());

        this.gestionSorteos = new JPanel();

        this.gestionDescuentos = new JPanel();

        tabbedPane.addTab("Exposiciones", gestionExposiciones);
        tabbedPane.addTab("Salas", gestionSalas);
        tabbedPane.addTab("Obras", gestionObras);
        tabbedPane.addTab("Empleados", gestionEmpleados);
        tabbedPane.addTab("Sorteos", gestionSorteos);
        tabbedPane.addTab("Descuentos", gestionDescuentos);

        JPanel panelCerrarSesion = new JPanel();
        panelCerrarSesion.setLayout(new BorderLayout());
        cerrarSesionBtn = new JButton("Cerrar Sesión");
        panelCerrarSesion.add(cerrarSesionBtn, BorderLayout.EAST);

        add(tabbedPane, BorderLayout.CENTER);
        add(panelCerrarSesion, BorderLayout.NORTH);
    }

    /**
     * Añade un panel de exposiciones al gestor principal.
     * 
     * @param centro Centro de exposiciones.
     */
    public void addPanelExposiciones(CentroExposicion centro) {
        // Tabla
        String[] titulos = { "Nombre", "Descripcion", "Fecha Inicio", "Fecha Fin", "Precio", "Estado",
                "Tipo Exposicion" };
        Object[][] datos = construirDatosExposiciones(centro, titulos);

        this.tablaExposiciones = new JTable(new DefaultTableModel(datos, titulos));
        this.gestionExposiciones.add(new JScrollPane(tablaExposiciones), BorderLayout.CENTER);

        // Lista acciones y botones
        JPanel panelAcciones = new JPanel();
        this.exposicionComboAcciones = new JComboBox<>(
                new String[] { "Publicar Exposicion", "Cancelar Exposicion", "Prorrogar Exposicion",
                        "Cerrar Temporalmente", "Establecer como Temporal", "Establecer como Permanente" });
        this.exposicionEjecutarBtn = new JButton("Ejecutar accion");
        this.exposicionAgregarBtn = new JButton("Agregar exposicion");

        panelAcciones.add(new JLabel("Acciones: "));
        panelAcciones.add(this.exposicionComboAcciones);
        panelAcciones.add(exposicionEjecutarBtn);
        panelAcciones.add(exposicionAgregarBtn);
        this.gestionExposiciones.add(panelAcciones, BorderLayout.SOUTH);
    }

    /**
     * Construye los datos de las exposiciones.
     * 
     * @param centro  Centro de exposiciones.
     * @param titulos Titulos de las columnas.
     * @return Datos de las exposiciones.
     */
    private Object[][] construirDatosExposiciones(CentroExposicion centro, String[] titulos) {
        List<Object[]> data = new ArrayList<>();
        for (Exposicion exposicion : centro.getExposiciones()) {
            data.add(new Object[] {
                    exposicion.getNombre(),
                    exposicion.getDescripcion(),
                    exposicion.getFechaInicio(),
                    exposicion.getFechaFin(),
                    exposicion.getPrecio(),
                    exposicion.getEstado(),
                    exposicion.getTipo()
            });
        }
        return data.toArray(new Object[0][]);
    }

    /**
     * Añade una fila a la tabla de exposiciones.
     * 
     * @param exposicionData Datos de la exposición.
     */
    public void añadirFilaTablaExposiciones(Object[] exposicionData) {
        DefaultTableModel modelo = (DefaultTableModel) this.tablaExposiciones.getModel();
        modelo.addRow(exposicionData);
        modelo.fireTableDataChanged();
    }

    /**
     * Actualiza la tabla de exposiciones.
     * 
     * @param centro Centro de exposiciones.
     */
    public void actualizarTablaExposiciones(CentroExposicion centro) {
        DefaultTableModel modelo = (DefaultTableModel) this.tablaExposiciones.getModel();
        modelo.setRowCount(0);
        Object[][] datos = construirDatosExposiciones(centro, new String[] { "Nombre", "Descripcion", "Fecha Inicio",
                "Fecha Fin", "Precio", "Estado", "Tipo Exposicion" });
        for (Object[] exposicionData : datos) {
            modelo.addRow(exposicionData);
        }
        modelo.fireTableDataChanged();
    }

    /**
     * Añade un panel de salas al gestor principal.
     * 
     * @param centro Centro de exposiciones.
     */
    public void addPanelSalas(CentroExposicion centro) {
        // Tabla
        String[] titulos = { "Nombre", "Aforo", "Climatizador", "Tomas de corriente", "Ancho", "Largo" };
        Object[][] datos = construirDatosSalas(centro);

        this.tablaSalas = new JTable(new DefaultTableModel(datos, titulos));
        this.gestionSalas.add(new JScrollPane(tablaSalas), BorderLayout.CENTER);

        // Lista acciones y botones
        JPanel panelAcciones = new JPanel();
        this.salaComboAcciones = new JComboBox<>(new String[] { "Añadir Sala", "Añadir Subsala", "Eliminar Sala" });
        this.salaEjecutarBtn = new JButton("Ejecutar accion");

        panelAcciones.add(new JLabel("Acciones: "));
        panelAcciones.add(this.salaComboAcciones);
        panelAcciones.add(salaEjecutarBtn);
        this.gestionSalas.add(panelAcciones, BorderLayout.SOUTH);
    }

    /**
     * Construye los datos de las salas.
     * 
     * @param centroExposicion Centro de exposiciones.
     * @return Datos de las salas.
     */
    private Object[][] construirDatosSalas(CentroExposicion centroExposicion) {
        List<Object[]> data = new ArrayList<>();
        for (Sala sala : centroExposicion.getSalas()) {
            addSalasRecursivo(data, sala);
        }
        return data.toArray(new Object[0][]);
    }

    /**
     * Añade las salas recursivamente.
     * 
     * @param data Datos de las salas.
     * @param sala Sala.
     */
    private void addSalasRecursivo(List<Object[]> data, Sala sala) {
        data.add(new Object[] {
                sala.getNombre(),
                sala.getAforo(),
                sala.getClimatizador(),
                sala.getTomasElectricidad(),
                sala.getAncho(),
                sala.getLargo()
        });

        for (Sala subSala : sala.getSubSalas()) {
            addSalasRecursivo(data, subSala);
        }
    }

    /**
     * Añade una fila a la tabla de salas.
     * 
     * @param salaData Datos de la sala.
     */
    public void añadirFilaTablaSalas(Object[] salaData) {
        DefaultTableModel modelo = (DefaultTableModel) this.tablaSalas.getModel();
        modelo.addRow(salaData);
        modelo.fireTableDataChanged();
    }

    /**
     * Actualiza la tabla de salas.
     * 
     * @param centroExposicion Centro de exposiciones.
     */
    public void actualizarTablaSalas(CentroExposicion centroExposicion) {
        DefaultTableModel modelo = (DefaultTableModel) this.tablaSalas.getModel();
        modelo.setRowCount(0);
        Object[][] datos = construirDatosSalas(centroExposicion);
        for (Object[] salaData : datos) {
            modelo.addRow(salaData);
        }
        modelo.fireTableDataChanged();
    }

    /**
     * Añade un panel de obras al gestor principal.
     * 
     * @param centroExposicion Centro de exposiciones.
     */
    public void addPanelObras(CentroExposicion centroExposicion) {
        // Tabla
        String[] titulos = { "Seleccionar", "Nombre", "Autor", "Descripcion", "Año", "Externa", "Cuantía Seguro",
                "Número Seguro", "Estado", "Tipo de Obra" };
        Object[][] datos = construirDatosObras(centroExposicion);

        AbstractTableModel modeloTablaObras = new ModeloTablaObras(titulos, datos);
        this.tablaObras = new JTable(modeloTablaObras);
        this.gestionObras.add(new JScrollPane(tablaObras), BorderLayout.CENTER);

        // Lista acciones y botones
        JPanel panelAcciones = new JPanel();
        this.obraComboAcciones = new JComboBox<>(
                new String[] { "Retirar Obra", "Almacenar Obra", "Exponer Obra", "Restaurar Obra", "Prestar Obra" });
        this.obraEjecutarBtn = new JButton("Ejecutar accion");
        this.obraAgregarBtn = new JButton("Agregar obra");
        this.leerObrasCSVBtn = new JButton("Leer Obras desde CSV");

        panelAcciones.add(new JLabel("Acciones: "));
        panelAcciones.add(this.obraComboAcciones);
        panelAcciones.add(obraEjecutarBtn);
        panelAcciones.add(obraAgregarBtn);
        panelAcciones.add(leerObrasCSVBtn);
        this.gestionObras.add(panelAcciones, BorderLayout.SOUTH);
    }

    /**
     * Construye los datos de las obras.
     * 
     * @param centroExposicion Centro de exposiciones.
     * @return Datos de las obras.
     */
    private Object[][] construirDatosObras(CentroExposicion centroExposicion) {
        List<Object[]> data = new ArrayList<>();
        for (Obra obra : centroExposicion.getObras()) {
            data.add(new Object[] {
                    Boolean.FALSE,
                    obra.getNombre(),
                    String.join(", ", obra.getAutores()),
                    obra.getDescripcion(),
                    obra.getAnio(),
                    obra.getExterna(),
                    obra.getCuantiaSeguro(),
                    obra.getNumeroSeguro(),
                    obra.getEstado().toString(),
                    obra.getTipoObra()
            });
        }
        return data.toArray(new Object[0][]);
    }

    /**
     * Añade una fila a la tabla de obras.
     * 
     * @param obraData Datos de la obra.
     */
    public void añadirFilaTablaObras(Object[] obraData) {
        ModeloTablaObras modelo = (ModeloTablaObras) this.tablaObras.getModel();
        modelo.addRow(obraData);
        modelo.fireTableDataChanged();
    }

    /**
     * Actualiza la tabla de obras.
     * 
     * @param centroExposicion Centro de exposiciones.
     */
    public void actualizarTablaObras(CentroExposicion centroExposicion) {
        ModeloTablaObras modelo = (ModeloTablaObras) this.tablaObras.getModel();
        modelo.setRowCountToNone();
        Object[][] datos = construirDatosObras(centroExposicion);
        for (Object[] obraData : datos) {
            modelo.addRow(obraData);
        }
        modelo.fireTableDataChanged();
    }

    /**
     * Añade un panel de empleados al gestor principal.
     * 
     * @param centro Centro de exposiciones.
     */
    public void addPanelEmpleados(CentroExposicion centro) {
        // Tabla
        String[] titulos = { "NIF", "Nombre", "Numero SS", "Numero de cuenta", "Direccion", "Permiso venta",
                "Permiso control",
                "Permiso mensajes" };
        Object[][] datos = construirDatosEmpleados(centro, titulos);
        List<Empleado> empleados = new ArrayList<>(centro.getEmpleados());

        AbstractTableModel modeloTablaEmpleados = new ModeloTablaEmpleados(titulos, datos, empleados);
        this.tablaEmpleados = new JTable(modeloTablaEmpleados);
        // Para editar las checkboxes dinamicamente
        JCheckBox checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultCellEditor checkBoxEditor = new DefaultCellEditor(checkBox);
        tablaEmpleados.getColumnModel().getColumn(5).setCellEditor(checkBoxEditor);
        tablaEmpleados.getColumnModel().getColumn(6).setCellEditor(checkBoxEditor);
        tablaEmpleados.getColumnModel().getColumn(7).setCellEditor(checkBoxEditor);

        this.gestionEmpleados.add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);

        // Lista acciones y botones
        JPanel panelAcciones = new JPanel();
        this.empleadoAgregarBtn = new JButton("Agregar empleado");

        panelAcciones.add(empleadoAgregarBtn);
        this.gestionEmpleados.add(panelAcciones, BorderLayout.SOUTH);
    }

    /**
     * Construye los datos de los empleados.
     * 
     * @param centro  Centro de exposiciones.
     * @param titulos Titulos de las columnas.
     * @return Datos de los empleados.
     */
    private Object[][] construirDatosEmpleados(CentroExposicion centro, String[] titulos) {
        List<Object[]> data = new ArrayList<>();
        for (Empleado empleado : centro.getEmpleados()) {
            data.add(new Object[] {
                    empleado.getNIF(),
                    empleado.getNombre(),
                    empleado.getNumSS(),
                    empleado.getNumCuenta(),
                    empleado.getDireccion(),
                    empleado.getPermisoVenta(),
                    empleado.getPermisoControl(),
                    empleado.getPermisoMensajes()
            });
        }
        return data.toArray(new Object[0][]);
    }

    /**
     * Añade una fila a la tabla de empleados.
     * @param empleadoData Datos del empleado.
     */
    public void añadirFilaTablaEmpleados(Object[] empleadoData) {
        ModeloTablaEmpleados modelo = (ModeloTablaEmpleados) this.tablaEmpleados.getModel();
        modelo.addRow(empleadoData);
        modelo.fireTableDataChanged();
    }

    /**
     * Devuelve la obra seleccionada.
     * 
     * @return String con la obra seleccionada.
     */
    public String getObraAccionSeleccionada() {
        return this.obraComboAcciones.getSelectedItem().toString();
    }

    /**
     * Devuelve la sala seleccionada.
     * 
     * @return String con la sala seleccionada.
     */
    public String getSalaAccionSeleccionada() {
        return this.salaComboAcciones.getSelectedItem().toString();
    }

    /**
     * Devuelve la exposición seleccionada.
     * 
     * @return String con la exposición seleccionada.
     */
    public String getExposicionAccionSeleccionada() {
        return this.exposicionComboAcciones.getSelectedItem().toString();
    }

    /**
     * Devuelve la tabla de obras.
     * 
     * @return Tabla de obras.
     */
    public JTable getTablaObras() {
        return this.tablaObras;
    }

    /**
     * Devuelvela tabla de salas.
     * 
     * @return Tabla de salas.
     */
    public JTable getTablaSalas() {
        return this.tablaSalas;
    }

    /**
     * Devuelve la tabla de exposiciones.
     * 
     * @return Tabla de exposiciones.
     */
    public JTable getTablaExposiciones() {
        return this.tablaExposiciones;
    }

    /**
     * Deselecciona todas las filas de la tabla.
     * 
     * @param tabla Tabla a deseleccionar.
     */
    public void deseleccionarTabla() {
        for (int i = 0; i < this.tablaObras.getRowCount(); i++) {
            this.tablaObras.setValueAt(false, i, 0);
        }
    }

    /**
     * Establece los controladores de los botones.
     * 
     * @param cObrasEjecutar        Controlador de ejecutar acciones de obras.
     * @param cObrasAgregar         Controlador de agregar obras.
     * @param cSalasEjecutar        Controlador de ejecutar acciones de salas.
     * @param cExposicionesEjecutar Controlador de ejecutar acciones de
     *                              exposiciones.
     */
    public void setControlador(ActionListener cObrasEjecutar, ActionListener cObrasAgregar,
            ActionListener cObrasLeerCSV,
            ActionListener cSalasEjecutar, ActionListener cExposicionesEjecutar, ActionListener cExposicionesAgregar,
            ActionListener cEmpleadoAgregar, ActionListener cCerrarSesion) {
        this.obraEjecutarBtn.addActionListener(cObrasEjecutar);
        this.obraAgregarBtn.addActionListener(cObrasAgregar);
        this.leerObrasCSVBtn.addActionListener(cObrasLeerCSV);
        this.salaEjecutarBtn.addActionListener(cSalasEjecutar);
        this.exposicionEjecutarBtn.addActionListener(cExposicionesEjecutar);
        this.exposicionAgregarBtn.addActionListener(cExposicionesAgregar);
        this.empleadoAgregarBtn.addActionListener(cEmpleadoAgregar);
        this.cerrarSesionBtn.addActionListener(cCerrarSesion);
    }

    /**
     * Devuelve la vista del formulario de obra.
     * 
     * @return Vista del formulario de obra.
     */
    public ObraFormulario getVistaObraFormulario() {
        this.vistaObraFormulario = new ObraFormulario();
        return this.vistaObraFormulario;
    }

    /**
     * Devuelve la vista del formulario de sala.
     * 
     * @param accion Acción a realizar.
     * @return Vista del formulario de sala.
     */
    public SalaFormulario getVistaSalaFormulario(String accion) {
        this.vistaSalaFormulario = new SalaFormulario(accion);
        return this.vistaSalaFormulario;
    }

    /**
     * Devuelve la vista del formulario de exposición.
     * 
     * @param accion Acción a realizar.
     * @return Vista del formulario de exposición.
     */
    public ExposicionFormulario getVistaExposicionFormulario(String accion) {
        this.vistaExposicionFormulario = new ExposicionFormulario(accion);
        return this.vistaExposicionFormulario;
    }

    /**
     * Devuelve la vista del formulario de empleado.
     * @return Vista del formulario de empleado.
     */
    public EmpleadoFormulario getVistaEmpleadoFormulario() {
        this.vistaEmpleadoFormulario = new EmpleadoFormulario();
        return this.vistaEmpleadoFormulario;
    }

    /**
     * Establece el controlador del formulario de obra.
     * 
     * @param controlador Controlador del formulario de obra.
     * @return Controlador del formulario de obra.
     */
    public void setControladorObraFormulario(ControladorObraFormulario controlador) {
        this.controladorObraFormulario = controlador;
        if (controlador.getGuardarListener() == null || controlador.getCancelarListener() == null) {
            return;
        }
        this.vistaObraFormulario.setControlador(controlador.getGuardarListener(), controlador.getCancelarListener());
    }

    /**
     * Establece el controlador del formulario de sala.
     * 
     * @param controlador Controlador del formulario de sala.
     * @return Controlador del formulario de sala.
     */
    public void setControladorSalaFormulario(ControladorSalaFormulario controlador) {
        this.controladorSalaFormulario = controlador;
        if (controlador.getAceptarListener() == null || controlador.getCancelarListener() == null) {
            return;
        }
        this.vistaSalaFormulario.setControlador(controlador.getAceptarListener(), controlador.getCancelarListener());
    }

    /**
     * Establece el controlador del formulario de exposición.
     * 
     * @param controlador Controlador del formulario de exposición.
     * @return Controlador del formulario de exposición.
     */
    public void setControladorExposicionFormulario(ControladorExposicionFormulario controlador) {
        this.controladorExposicionFormulario = controlador;
        if (controlador.getAceptarListener() == null || controlador.getCancelarListener() == null) {
            return;
        }
        this.vistaExposicionFormulario.setControlador(controlador.getAceptarListener(),
                controlador.getCancelarListener());
    }


    /**
     * Establece el controlador del formulario de empleado.
     * 
     * @param controlador Controlador del formulario de empleado.
     * @return Controlador del formulario de empleado.
     */
    public void setControladorEmpleadoFormulario(ControladorEmpleadoFormulario controlador) {
        this.controladorEmpleadoFormulario = controlador;
        if (controlador.getGuardarListener() == null || controlador.getCancelarListener() == null) {
            return;
        }
        this.vistaEmpleadoFormulario.setControlador(controlador.getGuardarListener(), controlador.getCancelarListener());
    }

}
