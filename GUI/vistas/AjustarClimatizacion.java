package GUI.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.sala.Sala;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;


public class AjustarClimatizacion extends JPanel{
    private JPanel gestionTemperatura;
    private JPanel gestionHumedad;
    private JButton atrasBoton;
    
    public AjustarClimatizacion() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        this.gestionTemperatura = new JPanel();
        gestionTemperatura.setLayout(new BorderLayout());

        this.gestionHumedad = new JPanel();
        gestionHumedad.setLayout(new BorderLayout());

        JPanel panelBoton = new JPanel();
        this.atrasBoton = new JButton("Atrás");

        panelBoton.add(atrasBoton);
        tabbedPane.addTab("Temperatura", gestionTemperatura);
        tabbedPane.addTab("Humedad", gestionHumedad);

        add(panelBoton, BorderLayout.SOUTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    public void addTemperatura(ArrayList<Object[]> data) {
        String[] titulos = { "Nombre", "Climatizador", "Temperatura" };

        Object[][] datos = data.toArray(new Object[0][]);
        JTable tablaTemperatura = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        });

        tablaTemperatura.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaTemperatura.setFillsViewportHeight(true);

        this.gestionTemperatura.add(new JScrollPane(tablaTemperatura), BorderLayout.CENTER);
    }

    public void addHumedad(ArrayList<Object[]> data) {
        String[] titulos = { "Nombre", "Climatizador", "Humedad" };

        Object[][] datos = data.toArray(new Object[0][]);
        JTable tablaHumedad = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        });

        tablaHumedad.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaHumedad.setFillsViewportHeight(true);

        this.gestionHumedad.add(new JScrollPane(tablaHumedad), BorderLayout.CENTER);
    }

    public void setControlador(ActionListener cAtras) {
        atrasBoton.addActionListener(cAtras);
    }

}
