package gui.vistas;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import gui.controlador.*;
import gui.modelo.centroExposicion.*;
import gui.modelo.exposicion.*;
import gui.modelo.obra.Obra;
import gui.modelo.sala.Sala;

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
    private JPanel gestionSalasExposicion;
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

    private SorteoFormulario vistaSorteoFormulario;
    private ControladorSorteoFormulario controladorSorteoFormulario;

    private DescuentoFormulario vistaDescuentoFormulario;
    private ControladorDescuentoFormulario controladorDescuentoFormulario;

    // Obras atributos
    private JButton obraEjecutarBtn;
    private JComboBox<String> obraComboAcciones;
    private JTable tablaObras;
    private JButton obraAgregarBtn;
    private JButton leerObrasCSVBtn;

    // Salas de Exposicion atributos
    private JTable tablaSalasExposicion;

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
    private JButton empleadoConfigurarContraseniaBtn;

    // Sorteos atributos
    private JTable tablaSorteos;
    private JButton sorteoAgregarBtn;

    // Descuentos atributos
    private JTable tablaDescuentos;
    private JButton descuentoAgregarBtn;

    // Atributos de la vista
    private JLabel nombreCentro;
    private JLabel horaApertura;
    private JLabel horaCierre;
    private JButton cerrarSesionBtn;

    /**
     * Constructor de la clase GestorPrincipal.
     */
    public GestorPrincipal() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        this.gestionExposiciones = new JPanel();
        gestionExposiciones.setLayout(new BorderLayout());

        this.gestionSalasExposicion = new JPanel();
        gestionSalasExposicion.setLayout(new BorderLayout());

        this.gestionSalas = new JPanel();
        gestionSalas.setLayout(new BorderLayout());

        this.gestionObras = new JPanel();
        gestionObras.setLayout(new BorderLayout());

        this.gestionEmpleados = new JPanel();
        gestionEmpleados.setLayout(new BorderLayout());

        this.gestionSorteos = new JPanel();
        gestionSorteos.setLayout(new BorderLayout());

        this.gestionDescuentos = new JPanel();
        gestionDescuentos.setLayout(new BorderLayout());

        tabbedPane.addTab("Exposiciones", gestionExposiciones);
        tabbedPane.addTab("Salas de exposición", gestionSalasExposicion);
        tabbedPane.addTab("Salas", gestionSalas);
        tabbedPane.addTab("Obras", gestionObras);
        tabbedPane.addTab("Empleados", gestionEmpleados);
        tabbedPane.addTab("Sorteos", gestionSorteos);
        tabbedPane.addTab("Descuentos", gestionDescuentos);

        JPanel panelSuperior = addPanelInfo();
        ;

        add(tabbedPane, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);
    }

    /**
     * Añade un panel de salas de exposición al gestor principal.
     * 
     * @param centro Centro de exposiciones.
     */
    public void addPanelSalasExposicion(CentroExposicion centro) {
        String[] titulos = { "Exposición", "Fecha Inicio", "Fecha Fin", "Estado", "Sala", "Obras en Sala" };

        Object[][] datos = construirDatosSalasExposicion(centro, titulos);

        this.tablaSalasExposicion = new JTable(new DefaultTableModel(datos, titulos));
        this.gestionSalasExposicion.add(new JScrollPane(tablaSalasExposicion), BorderLayout.CENTER);
    }

    /**
     * Construye los datos de las salas de exposición.
     * 
     * @param centro  Centro de exposiciones.
     * @param titulos Titulos de las columnas.
     * @return Datos de las salas de exposición.
     */
    private Object[][] construirDatosSalasExposicion(CentroExposicion centro, String[] titulos) {
        List<Object[]> data = new ArrayList<>();
        for (Exposicion exposicion : centro.getExposiciones()) {
            for (SalaExposicion salaExposicion : exposicion.getSalas()) {
                StringBuilder nombresObras = new StringBuilder();
                for (Obra obra : salaExposicion.getObras()) {
                    if (nombresObras.length() > 0) {
                        nombresObras.append(", ");
                    }
                    nombresObras.append(obra.getNombre());
                }
                if (nombresObras.length() == 0) {
                    nombresObras.append("Sin obras");
                }
                data.add(new Object[] {
                        exposicion.getNombre(),
                        exposicion.getFechaInicio(),
                        exposicion.getFechaFin(),
                        exposicion.getEstado(),
                        salaExposicion.getSala().getNombre(),
                        nombresObras.toString()
                });
            }
        }
        return data.toArray(new Object[0][]);
    }

    /**
     * Actualiza el panel de salas de exposición al gestor principal.
     * 
     * @param centro Centro de exposiciones.
     */
    public void actualizarTablaSalasExposicion(CentroExposicion centro) {
        DefaultTableModel modelo = (DefaultTableModel) this.tablaSalasExposicion.getModel();
        modelo.setRowCount(0);
        Object[][] datos = construirDatosSalasExposicion(centro,
                new String[] { "Exposición", "Fecha Inicio", "Fecha Fin", "Estado", "Sala", "Obras en Sala" });
        for (Object[] salaData : datos) {
            modelo.addRow(salaData);
        }
        modelo.fireTableDataChanged();
    }

    /**
     * Añade un panel de informacion al gestor principal.
     * 
     * @return JPanel con la información del centro.
     */
    public JPanel addPanelInfo() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        cerrarSesionBtn = new JButton("Cerrar Sesión");
        panelSuperior.add(cerrarSesionBtn, BorderLayout.EAST);

        JPanel panelInfo = new JPanel(new GridLayout(1, 3));
        nombreCentro = new JLabel("Nombre del centro");
        horaApertura = new JLabel("Hora de apertura");
        horaCierre = new JLabel("Hora de cierre");
        panelInfo.add(nombreCentro);
        panelInfo.add(horaApertura);
        panelInfo.add(horaCierre);

        panelSuperior.add(panelInfo, BorderLayout.CENTER);
        return panelSuperior;
    }

    /**
     * Actualiza la informacion del panel.
     * 
     * @param centro Centro de exposiciones.
     */
    public void actualizarInfo(CentroExposicion centro) {
        nombreCentro.setText("Centro de Exposiciones: " + centro.getNombre());
        horaApertura.setText("Hora de apertura: " + centro.getHoraApertura());
        horaCierre.setText("Hora de cierre: " + centro.getHoraCierre());
    }

    /**
     * Añade un panel de descuentos al gestor principal.
     * 
     * @param centro Centro de exposiciones.
     */
    public void addPanelDescuentos(CentroExposicion centro) {
        String[] titulos = { "Exposición", "Descuento (%)", "Cantidad (Días o meses)" };

        // Construir datos para la tabla de descuentos
        Object[][] datos = construirDatosDescuentos(centro, titulos);

        this.tablaDescuentos = new JTable(new DefaultTableModel(datos, titulos));
        this.gestionDescuentos.add(new JScrollPane(tablaDescuentos), BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel();
        this.descuentoAgregarBtn = new JButton("Agregar Descuento");
        JButton descuentoEliminarBtn = new JButton("Eliminar Descuento");

        panelAcciones.add(new JLabel("Acciones: "));
        panelAcciones.add(descuentoAgregarBtn);
        panelAcciones.add(descuentoEliminarBtn);
        this.gestionDescuentos.add(panelAcciones, BorderLayout.SOUTH);
    }

    /**
     * Actualiza eñ panel de descuentos al gestor principal.
     * 
     * @param centro Centro de exposiciones.
     */
    public void actualizarTablaDescuentos(CentroExposicion centro) {
        DefaultTableModel modelo = (DefaultTableModel) this.tablaDescuentos.getModel();
        modelo.setRowCount(0);
        Object[][] datos = construirDatosDescuentos(centro,
                new String[] { "Exposición", "Descuento (%)", "Cantidad días o meses" });
        for (Object[] descuentoData : datos) {
            modelo.addRow(descuentoData);
        }
        modelo.fireTableDataChanged();
    }

    /**
     * Construye los datos de los descuentos.
     * 
     * @param centro  Centro de exposiciones.
     * @param titulos Titulos de las columnas.
     * @return Datos de los descuentos.
     */
    private Object[][] construirDatosDescuentos(CentroExposicion centro, String[] titulos) {
        List<Object[]> data = new ArrayList<>();
        for (Exposicion exposicion : centro.getExposiciones()) {
            Descuento descuento = exposicion.getDescuento();
            if (descuento == null) {
                break;
            }
            data.add(new Object[] {
                    exposicion.getNombre(),
                    descuento.getDescuento(),
                    descuento.getcantidad()
            });
        }
        return data.toArray(new Object[0][]);
    }

    /**
     * Añade un panel de sorteos al gestor principal.
     * 
     * @param centro Centro de exposiciones.
     */
    public void addPanelSorteos(CentroExposicion centro) {
        String[] titulos = { "Fecha del Sorteo", "Exposición Relacionada", "Número de Entradas" };

        Object[][] datos = construirDatosSorteos(centro, titulos);

        this.tablaSorteos = new JTable(new DefaultTableModel(datos, titulos));
        this.gestionSorteos.add(new JScrollPane(tablaSorteos), BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel();
        this.sorteoAgregarBtn = new JButton("Agregar Sorteo");
        JButton sorteoEliminarBtn = new JButton("Eliminar Sorteo");

        panelAcciones.add(new JLabel("Acciones: "));
        panelAcciones.add(sorteoAgregarBtn);
        panelAcciones.add(sorteoEliminarBtn);
        this.gestionSorteos.add(panelAcciones, BorderLayout.SOUTH);
    }

    /**
     * Construye los sorteos.
     * 
     * @param centro Centro de exposiciones.
     */
    private Object[][] construirDatosSorteos(CentroExposicion centro, String[] titulos) {
        List<Object[]> data = new ArrayList<>();
        for (Sorteo sorteo : centro.getSorteos()) {
            data.add(new Object[] {
                    sorteo.getFechaSorteo().toString(),
                    sorteo.getExposicion().getNombre(),
                    sorteo.getN_entradas()
            });
        }
        return data.toArray(new Object[0][]);
    }

    /**
     * Actualiza el panel de sorteos al gestor principal.
     * 
     * @param centro Centro de exposiciones.
     */
    public void actualizarTablaSorteos(CentroExposicion centro) {
        DefaultTableModel modelo = (DefaultTableModel) this.tablaSorteos.getModel();
        modelo.setRowCount(0);
        Object[][] datos = construirDatosSorteos(centro, new String[] { "Fecha del Sorteo", "Exposición Relacionada",
                "Número de Entradas" });
        for (Object[] sorteoData : datos) {
            modelo.addRow(sorteoData);
        }
        modelo.fireTableDataChanged();
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
        this.salaComboAcciones = new JComboBox<>(
                new String[] { "Añadir Sala", "Añadir Subsala", "Eliminar Sala", "Añadir Sala a Exposicion" });
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
                new String[] { "Retirar Obra", "Almacenar Obra", "Exponer Obra", "Restaurar Obra", "Prestar Obra",
                        "Asignar Obra a Sala", "Eliminar Obra de Sala"});
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
        this.empleadoConfigurarContraseniaBtn = new JButton("Configurar contraseña");

        panelAcciones.add(empleadoAgregarBtn);
        panelAcciones.add(empleadoConfigurarContraseniaBtn);
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
     * 
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
     * Devuelve la tabla de empleados.
     * 
     * @return Tabla de empleados.
     */
    public JTable getTablaEmpleados() {
        return this.tablaEmpleados;
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
     * Devuelve la tabla de sorteos.
     * 
     * @return Tabla de sorteos.
     */
    public JTable getTablaSorteos() {
        return this.tablaSorteos;
    }

    /**
     * Devuelve la tabla de descuentos.
     * 
     * @return Tabla de descuentos.
     */
    public JTable getTablaDescuentos() {
        return this.tablaDescuentos;
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
            ActionListener cEmpleadoAgregar, ActionListener cEmpleadoConfigurarContrasenia,
            ActionListener cSorteoAgregar, ActionListener cDescuentoAgregar, ActionListener cCerrarSesion) {
        this.obraEjecutarBtn.addActionListener(cObrasEjecutar);
        this.obraAgregarBtn.addActionListener(cObrasAgregar);
        this.leerObrasCSVBtn.addActionListener(cObrasLeerCSV);
        this.salaEjecutarBtn.addActionListener(cSalasEjecutar);
        this.exposicionEjecutarBtn.addActionListener(cExposicionesEjecutar);
        this.exposicionAgregarBtn.addActionListener(cExposicionesAgregar);
        this.empleadoAgregarBtn.addActionListener(cEmpleadoAgregar);
        this.empleadoConfigurarContraseniaBtn.addActionListener(cEmpleadoConfigurarContrasenia);
        this.sorteoAgregarBtn.addActionListener(cSorteoAgregar);
        this.descuentoAgregarBtn.addActionListener(cDescuentoAgregar);
        this.cerrarSesionBtn.addActionListener(cCerrarSesion);

    }

    /**
     * Elimina los controladores de los botones.
     * 
     * @param cObrasEjecutar        Controlador de ejecutar acciones de obras.
     * @param cObrasAgregar         Controlador de agregar obras.
     * @param cSalasEjecutar        Controlador de ejecutar acciones de salas.
     * @param cExposicionesEjecutar Controlador de ejecutar acciones de
     *                              exposiciones.
     */
    public void removeControlador(ActionListener cObrasEjecutar, ActionListener cObrasAgregar,
            ActionListener cObrasLeerCSV,
            ActionListener cSalasEjecutar, ActionListener cExposicionesEjecutar, ActionListener cExposicionesAgregar,
            ActionListener cEmpleadoAgregar, ActionListener cSorteoAgregar, ActionListener cDescuentoAgregar,
            ActionListener cCerrarSesion, ActionListener cCambiarContrasenia) {
        this.obraEjecutarBtn.removeActionListener(cObrasEjecutar);
        this.obraAgregarBtn.removeActionListener(cObrasAgregar);
        this.leerObrasCSVBtn.removeActionListener(cObrasLeerCSV);
        this.salaEjecutarBtn.removeActionListener(cSalasEjecutar);
        this.exposicionEjecutarBtn.removeActionListener(cExposicionesEjecutar);
        this.exposicionAgregarBtn.removeActionListener(cExposicionesAgregar);
        this.empleadoAgregarBtn.removeActionListener(cEmpleadoAgregar);
        this.sorteoAgregarBtn.removeActionListener(cSorteoAgregar);
        this.descuentoAgregarBtn.removeActionListener(cDescuentoAgregar);
        this.empleadoConfigurarContraseniaBtn.removeActionListener(cCambiarContrasenia);
        this.cerrarSesionBtn.removeActionListener(cCerrarSesion);

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
     * 
     * @return Vista del formulario de empleado.
     */
    public EmpleadoFormulario getVistaEmpleadoFormulario() {
        this.vistaEmpleadoFormulario = new EmpleadoFormulario();
        return this.vistaEmpleadoFormulario;
    }

    /**
     * Devuelve la vista del formulario de sorteo.
     * 
     * @return Vista del formulario de sorteo.
     */
    public SorteoFormulario getVistaSorteoFormulario() {
        this.vistaSorteoFormulario = new SorteoFormulario();
        return this.vistaSorteoFormulario;
    }

    /**
     * Devuelve la vista del formulario de descuento.
     * 
     * @return Vista del formulario de descuento.
     */
    public DescuentoFormulario getVistaDescuentoFormulario() {
        this.vistaDescuentoFormulario = new DescuentoFormulario();
        return this.vistaDescuentoFormulario;
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
     * º
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
        this.vistaEmpleadoFormulario.setControlador(controlador.getGuardarListener(),
                controlador.getCancelarListener());
    }

    /**
     * Establece el controlador del formulario de sorteo.
     * 
     * @param controlador Controlador del formulario de sorteo.
     * @return Controlador del formulario de sorteo.
     */
    public void setControladorSorteoFormulario(ControladorSorteoFormulario controlador) {
        this.controladorSorteoFormulario = controlador;
        if (controlador.getAceptarListener() == null || controlador.getCancelarListener() == null) {
            return;
        }
        this.vistaSorteoFormulario.setControlador(controlador.getAceptarListener(), controlador.getCancelarListener());
    }

    /**
     * Establece el controlador del formulario de descuento.
     * 
     * @param controlador Controlador del formulario de descuento.
     * @return Controlador del formulario de descuento.
     */
    public void setControladorDescuentoFormulario(ControladorDescuentoFormulario controlador) {
        this.controladorDescuentoFormulario = controlador;
        if (controlador.getAceptarListener() == null || controlador.getCancelarListener() == null) {
            return;
        }
        this.vistaDescuentoFormulario.setControlador(controlador.getAceptarListener(),
                controlador.getCancelarListener());
    }
}
