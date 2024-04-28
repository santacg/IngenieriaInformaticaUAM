package GUI.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.expofy.Expofy;
import GUI.modelo.exposicion.Exposicion;

public class BusquedaExposiciones extends JPanel {

    public BusquedaExposiciones() {
        setLayout(new BorderLayout());
        addExpoTabla();
    }

    private void addExpoTabla() {
        Expofy expofy = Expofy.getInstance();

        String[] titulos = { "Nombre", "Descripcion", "Fecha Inicio", "Fecha Fin", "Precio", "Nombre Centro",
                "Localizacion" };

        List<Object[]> data = new ArrayList<>();
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
        JTable tablaExposiciones = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tablaExposiciones.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaExposiciones.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tablaExposiciones);

        add(scrollPane, BorderLayout.CENTER);
    }

}
