package GUI.vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.expofy.Expofy;
import GUI.modelo.exposicion.Exposicion;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GestorPrincipal extends JPanel {

    private JPanel gestionExposiciones;
    private JPanel gestionSalas;
    private JPanel gestionObras;
    private JPanel gestionEmpleados;
    private JPanel gestionSorteos;
    private JPanel gestionDescuentos;
    private Set<Exposicion> exposiciones = new HashSet<>();


    public GestorPrincipal() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        gestionExposiciones();

        this.gestionSalas = new JPanel();
        gestionSalas.add(new JLabel("Gestión de salas"));

        this.gestionObras = new JPanel();
        gestionObras.add(new JLabel("Gestión de obras"));

        this.gestionEmpleados = new JPanel();
        gestionEmpleados.add(new JLabel("Gestión de empleados"));

        this.gestionSorteos = new JPanel();
        gestionSorteos.add(new JLabel("Gestión de sorteos"));

        this.gestionDescuentos = new JPanel();
        gestionDescuentos.add(new JLabel("Gestión de descuentos"));

        tabbedPane.addTab("Exposiciones", gestionExposiciones);
        tabbedPane.addTab("Salas", gestionSalas);
        tabbedPane.addTab("Obras", gestionObras);
        tabbedPane.addTab("Empleados", gestionEmpleados);
        tabbedPane.addTab("Sorteos", gestionSorteos);
        tabbedPane.addTab("Descuentos", gestionDescuentos);

        add(tabbedPane, BorderLayout.CENTER);
    }
    
    public void setExposiciones(Set<Exposicion> exposiciones) {
        this.exposiciones = exposiciones;
    }

    public void gestionExposiciones() {
        this.gestionExposiciones = new JPanel();
        gestionExposiciones.add(new JLabel("Gestión de exposiciones"));

        gestionExposiciones.setLayout(new BorderLayout());

        String[] titulo = {"Nombre", "Descripcion", "Fecha Inicio", "Fecha Fin", "Precio", "Estado"};
        JTable tablaExposiciones = new JTable(new DefaultTableModel(titulo, 0));
        tablaExposiciones.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaExposiciones.setFillsViewportHeight(true);

        for (Exposicion exposicion : exposiciones) {
            ((DefaultTableModel) tablaExposiciones.getModel()).addRow(new Object[]{
                    exposicion.getNombre(),
                    exposicion.getDescripcion(),
                    exposicion.getFechaInicio(),
                    exposicion.getFechaFin(),
                    exposicion.getPrecio(),
                    exposicion.getEstado()
            });
        }

        this.gestionExposiciones.add(new JScrollPane(tablaExposiciones), BorderLayout.CENTER);
    }


    public void setControlador() {

    }


}
