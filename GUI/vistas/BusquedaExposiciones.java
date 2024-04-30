package GUI.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.expofy.Expofy;
import GUI.modelo.exposicion.Exposicion;

public class BusquedaExposiciones extends JPanel {

    private JPanel tabla;
    private JButton atrasBoton;
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

    public void setControlador(ActionListener cAtras) {
        atrasBoton.addActionListener(cAtras);
    }

}
