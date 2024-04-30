package GUI.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

/**
 * Clase BusquedaExposiciones.
 * Actúa como vista de la búsqueda de exposiciones.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class BusquedaExposiciones extends JPanel {

    private JPanel tabla;
    private JButton atrasBoton;

    /**
     * Constructor de la clase BusquedaExposiciones.
     */
    public BusquedaExposiciones() {
        setLayout(new BorderLayout());
        tabla = new JPanel();
        tabla.setLayout(new BorderLayout());

        JPanel panelBoton = new JPanel();
        atrasBoton = new JButton("Atrás");

        panelBoton.add(atrasBoton);

        add(tabla, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }

    /**
     * Añade una tabla con las exposiciones encontradas.
     * 
     * @param data ArrayList con los datos de las exposiciones.
     */
    public void addTablaExposiciones(ArrayList<Object[]> data) {
        String[] titulos = { "Nombre", "Descripcion", "Fecha Inicio", "Fecha Fin", "Precio", "Nombre Centro",
                "Localizacion" };

        Object[][] datos = data.toArray(new Object[0][]);
        JTable tablaExposiciones = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tablaExposiciones.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaExposiciones.setFillsViewportHeight(true);

        tabla.add(new JScrollPane(tablaExposiciones), BorderLayout.CENTER);
    }

    /**
     * Establece el controlador de la vista.
     * 
     * @param cAtras ActionListener que gestiona el botón de atrás.
     */
    public void setControlador(ActionListener cAtras) {
        atrasBoton.addActionListener(cAtras);
    }

}
