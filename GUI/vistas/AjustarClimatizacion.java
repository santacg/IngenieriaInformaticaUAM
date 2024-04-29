package GUI.vistas;

import java.awt.BorderLayout;
import java.awt.Color;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.sala.Sala;

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
    
    public AjustarClimatizacion() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        this.gestionTemperatura = new JPanel();
        gestionTemperatura.setLayout(new BorderLayout());

        this.gestionHumedad = new JPanel();
        gestionHumedad.setLayout(new BorderLayout());

        tabbedPane.addTab("Temperatura", gestionTemperatura);
        tabbedPane.addTab("Humedad", gestionHumedad);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public void addTemperatura(CentroExposicion centro) {
        String[] titulos = { "Nombre", "Climatizador", "Temperatura" };

        List<Object[]> data = new ArrayList<>();
        for (Sala sala : centro.getSalas()) {
            data.add(new Object[] {
                    sala.getNombre(),
                    sala.getClimatizador(),
                    sala.getTemperatura()
            });
        }

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

    public void addHumedad(CentroExposicion centro) {
        String[] titulos = { "Nombre", "Climatizador", "Humedad" };

        List<Object[]> data = new ArrayList<>();
        for (Sala sala : centro.getSalas()) {
            data.add(new Object[] {
                    sala.getNombre(),
                    sala.getClimatizador(),
                    sala.getHumedad()
            });
        }

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
}
