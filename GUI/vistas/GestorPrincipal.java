package GUI.vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.exposicion.Exposicion;
import GUI.modelo.obra.Obra;
import GUI.modelo.sala.Sala;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class GestorPrincipal extends JPanel {

    private JPanel gestionExposiciones;
    private JPanel gestionSalas;
    private JPanel gestionObras;
    private JPanel gestionEmpleados;
    private JPanel gestionSorteos;
    private JPanel gestionDescuentos;

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

        this.gestionSorteos = new JPanel();

        this.gestionDescuentos = new JPanel();

        tabbedPane.addTab("Exposiciones", gestionExposiciones);
        tabbedPane.addTab("Salas", gestionSalas);
        tabbedPane.addTab("Obras", gestionObras);
        tabbedPane.addTab("Empleados", gestionEmpleados);
        tabbedPane.addTab("Sorteos", gestionSorteos);
        tabbedPane.addTab("Descuentos", gestionDescuentos);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public void addTablaExposiciones(CentroExposicion centro) {
        String[] titulos = { "Nombre", "Descripcion", "Fecha Inicio", "Fecha Fin", "Precio", "Estado",
                "Tipo Exposicion" };

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

        Object[][] datos = data.toArray(new Object[0][]);
        JTable tablaExposiciones = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        });

        tablaExposiciones.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaExposiciones.setFillsViewportHeight(true);

        this.gestionExposiciones.add(new JScrollPane(tablaExposiciones), BorderLayout.CENTER);
    }

    public void addTablaSalas(CentroExposicion centro) {
        String[] titulos = { "Nombre", "Aforo", "Climatizador", "Tomas de corriente", "Ancho", "Largo" };

        List<Object[]> data = new ArrayList<>();
        for (Sala sala : centro.getSalas()) {
            data.add(new Object[] {
                    sala.getNombre(),
                    sala.getAforo(),
                    sala.getClimatizador(),
                    sala.getTomasElectricidad(),
                    sala.getAncho(),
                    sala.getLargo()
            });
        }

        Object[][] datos = data.toArray(new Object[0][]);
        JTable tablaSalas = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        });

        tablaSalas.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaSalas.setFillsViewportHeight(true);

        this.gestionSalas.add(new JScrollPane(tablaSalas), BorderLayout.CENTER);
    }

    public void addPanelObras(CentroExposicion centroExposicion) {
        String[] titulos = { "Seleccionar", "Nombre", "Autor", "Descripcion", "Año", "Externa", "Cuantía Seguro",
                "Número Seguro", "Estado", "Tipo de Obra" };
        Object[][] datos = construirDatosObras(centroExposicion);

        ModeloTablaObras modeloTablaObras = new ModeloTablaObras(titulos, datos);
        JTable tablaObras = new JTable(modeloTablaObras);
        this.gestionObras.add(new JScrollPane(tablaObras), BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel();
        JComboBox<String> comboAcciones = new JComboBox<>(new String[] { "Retirar Obra" });
        JButton ejecutarBtn = new JButton("Ejecutar accion");

        panelAcciones.add(new JLabel("Acciones: "));
        panelAcciones.add(comboAcciones);
        panelAcciones.add(ejecutarBtn);
        this.gestionObras.add(panelAcciones, BorderLayout.SOUTH);
    }

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

    private List<Obra> obtenerObrasSeleccionadas(CentroExposicion centroExposicion, ModeloTablaObras modeloTablaObras) {
        List<Obra> obras = new ArrayList<>();
        Integer i = 0;
        Boolean seleccionado;
        for (i = 0; i < modeloTablaObras.getRowCount(); i++) {
            seleccionado = (Boolean) modeloTablaObras.getValueAt(i, 0);
            if (seleccionado) {
                for (Obra obra : obras) {
                    if (obra.equals(modeloTablaObras.getValueAt(i, 1))) {
                        obras.add(obra);
                        break;
                    }
                }
            }
        }

        return obras;
    }

    private void ejecutarAccionSeleccionada(JComboBox<String> comboAcciones, ModeloTablaObras modeloTablaObras,
            CentroExposicion centroExposicion) {
        String accion = (String) comboAcciones.getSelectedItem();
        List<Obra> obras = obtenerObrasSeleccionadas(centroExposicion, modeloTablaObras);

        switch (accion) {
            case "Retirar Obra":
                for (Obra obra : obras) {
                    obra.retirarObra();
                }
                break;
            default:
                break;
        }

    }

    public void setControlador() {

    }

}
