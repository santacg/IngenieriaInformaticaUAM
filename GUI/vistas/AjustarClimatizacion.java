package GUI.vistas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import GUI.modelo.centroExposicion.CentroExposicion;
import GUI.modelo.sala.Sala;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase AjustarClimatizacion
 * Implementación de la vista de ajuste de climatización
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class AjustarClimatizacion extends JPanel {
    private JButton atrasBoton;
    private JTable tablaSalas;
    private JPanel gestionSalas;
    private CentroExposicion centroExposicion;

    /**
     * Constructor de la clase AjustarClimatizacion
     */
    public AjustarClimatizacion() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        this.gestionSalas = new JPanel();
        gestionSalas.setLayout(new BorderLayout());

        JPanel panelBoton = new JPanel();
        this.atrasBoton = new JButton("Atrás");

        panelBoton.add(atrasBoton);
        tabbedPane.addTab("Salas", gestionSalas);

        addSliders();

        add(panelBoton, BorderLayout.SOUTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Método que añade la tabla de salas a la vista
     * 
     * @param centro CentroExposicion
     */
    public void addTemperaturaHumedad(CentroExposicion centro) {
        String[] titulos = { "Nombre", "Temperatura", "Humedad" };
        Object[][] datos = construirDatosSalas(centro);

        this.tablaSalas = new JTable(new DefaultTableModel(datos, titulos));
        this.gestionSalas.add(new JScrollPane(tablaSalas), BorderLayout.CENTER);
    }

    /**
     * Método que actualiza los datos de la tabla de salas
     * 
     * @param centro CentroExposicion
     */
    private Object[][] construirDatosSalas(CentroExposicion centroExposicion) {
        List<Object[]> data = new ArrayList<>();
        for (Sala sala : centroExposicion.getSalas()) {
            addSalasRecursivo(data, sala);
        }
        return data.toArray(new Object[0][]);
    }

    /**
     * Método que añade las salas recursivamente a la tabla
     * 
     * @param data List<Object[]>
     * @param sala Sala
     */
    private void addSalasRecursivo(List<Object[]> data, Sala sala) {
        data.add(new Object[] {
                Boolean.FALSE,
                sala.getNombre(),
                sala.getTemperatura(),
                sala.getHumedad()
        });

        for (Sala subSala : sala.getSubSalas()) {
            addSalasRecursivo(data, subSala);
        }
    }

    /**
     * Método que añade los sliders de temperatura y humedad a la vista
     */
    private void addSliders() {
        JPanel slidersPanel = new JPanel();
        JSlider temperaturaSlider = new JSlider(JSlider.HORIZONTAL, 0, 35, 20);
        JSlider humedadSlider = new JSlider(JSlider.HORIZONTAL, 10, 90, 50);

        slidersPanel.add(new JLabel("Ajustar Temperatura:"));
        slidersPanel.add(temperaturaSlider);
        slidersPanel.add(new JLabel("Ajustar Humedad:"));
        slidersPanel.add(humedadSlider);
        this.gestionSalas.add(slidersPanel, BorderLayout.SOUTH);

    }

    /**
     * Método que establece el controlador de la vista
     * @param ActionListener cAtras
     */
    public void setControlador(ActionListener cAtras) {
        atrasBoton.addActionListener(cAtras);
    }

}