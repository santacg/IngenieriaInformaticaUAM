package GUI.vistas;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;


import GUI.controlador.ControladorVentaFormulario;


public class VentaEntradas extends JPanel{
    private JPanel buscarExposiciones;
    private JButton venderBoton;
    private JTable tablaExposiciones;

    private VentaFormulario vistaVentaFormulario;
    private ControladorVentaFormulario controladorVentaFormulario;

    private JButton cerrarSesionBoton;

    /**
     * Constructor de la clase ClientePrincipal.
     */
    public VentaEntradas(){
        setLayout(new BorderLayout());
        this.buscarExposiciones = new JPanel();
        buscarExposiciones.setLayout(new BorderLayout());

        JPanel panelCerrarSesion = new JPanel();
        panelCerrarSesion.setLayout(new BorderLayout());
        cerrarSesionBoton = new JButton("Cerrar Sesión");
        panelCerrarSesion.add(cerrarSesionBoton, BorderLayout.EAST);

        add(panelCerrarSesion, BorderLayout.NORTH);
        add(buscarExposiciones, BorderLayout.CENTER);
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
        venderBoton = new JButton("Vender");
        tablaExposiciones.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaExposiciones.setFillsViewportHeight(true);

        panelBoton.add(venderBoton);
        buscarExposiciones.add(panelBoton, BorderLayout.SOUTH);
        this.buscarExposiciones.add(new JScrollPane(tablaExposiciones), BorderLayout.CENTER);
    }

    /**
     * Método que establece los controladores de los botones de la vista.
     * 
     * @param cVender       Controlador del botón de comprar.
     * @param cActualizar   Controlador del botón de actualizar.
     * @param cCerrarSesion Controlador del botón de cerrar sesión.
     * @param cInscribirse  Controlador del botón de inscribirse.
     */
    public void setControlador(ActionListener cVender, ActionListener cCerrarSesion) {
        venderBoton.addActionListener(cVender);
        cerrarSesionBoton.addActionListener(cCerrarSesion);

    }

    /**
     * Método que devuelve la vista de compra de entradas.
     * 
     * @param exposicionNombre Nombre de la exposición.
     * @param precio           Precio de la entrada.
     * @return Vista de compra de entradas.
     */
    public VentaFormulario getVistaVentaFormulario(String exposicionNombre, double precio) {
        this.vistaVentaFormulario = new VentaFormulario(exposicionNombre, precio);
        return this.vistaVentaFormulario;
    }

    /**
     * Método que establece el controlador de la vista de compra de entradas.
     * 
     * @param controlador Controlador de la vista de compra de entradas.
     */
    public void setVentaFormularioControlador(ControladorVentaFormulario controlador) {
        this.controladorVentaFormulario = controlador;
        this.vistaVentaFormulario.setControlador(controladorVentaFormulario.getSiguienteListener(),
                controladorVentaFormulario.getCancelarListener(), controladorVentaFormulario.getNentradasListener(),
                controladorVentaFormulario.getHoraListener());
    }

    /**
     * Método que devuelve la tabla de exposiciones.
     * 
     * @return Tabla de exposiciones.
     */
    public JTable getTablaExposiciones() {
        return tablaExposiciones;
    }

}
