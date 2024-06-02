package GUI.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.event.ChangeListener;
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
    private JPanel panelTabla;

    private JButton confirmarBoton;

    private JSlider temperaturaSlider;
    private JSlider humedadSlider;

    private JLabel temperaturaValue;
    private JLabel humedadValue;

    /**
     * Constructor de la clase AjustarClimatizacion
     */
    public AjustarClimatizacion() {
        setLayout(new BorderLayout());

        this.gestionSalas = new JPanel();
        gestionSalas.setLayout(new BorderLayout());

        this.panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());

        addSliders();

        add(gestionSalas, BorderLayout.SOUTH);
        add(panelTabla, BorderLayout.CENTER);
    }

    /**
     * Método que añade una tabla de sorteos a la vista.
     * 
     * @param data ArrayList de objetos que contiene los datos de las
     *             sorteos.
     */
    public void addTablaSalas(ArrayList<Object[]> data) {
        String[] titulos = { "Nombre", "Temperatura", "Humedad" };
        Object[][] datos = data.toArray(new Object[0][]);
        tablaSalas = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tablaSalas.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaSalas.setFillsViewportHeight(true);

        this.panelTabla.add(new JScrollPane(tablaSalas), BorderLayout.CENTER);
    }


    /**
     * Método que añade los sliders de temperatura y humedad a la vista
     */
    private void addSliders() {
        JPanel slidersPanel = new JPanel();
        temperaturaSlider = new JSlider(JSlider.HORIZONTAL, 0, 35, 20);
        humedadSlider = new JSlider(JSlider.HORIZONTAL, 10, 90, 50);

        slidersPanel.add(new JLabel("Ajustar Temperatura:"));
        slidersPanel.add(temperaturaSlider);
        slidersPanel.add(temperaturaValue = new JLabel("" + temperaturaSlider.getValue()));
        slidersPanel.add(new JLabel("Ajustar Humedad:"));
        slidersPanel.add(humedadSlider);
        slidersPanel.add(humedadValue = new JLabel("" + humedadSlider.getValue()));
        slidersPanel.add(confirmarBoton = new JButton("Confirmar"));
        slidersPanel.add(atrasBoton = new JButton("Atrás"));
        this.gestionSalas.add(slidersPanel, BorderLayout.SOUTH);

    }

    public void updateTemperatura() {
        temperaturaValue.setText("" + temperaturaSlider.getValue());
    }

    public void updateHumedad() {
        humedadValue.setText("" + humedadSlider.getValue());
    }

    /**
     * Método que establece el controlador de la vista
     * 
     * @param ActionListener cAtras
     */
    public void setControlador(ActionListener cAtras, ActionListener cConfirmar, ChangeListener cTemperatura,
            ChangeListener cHumedad) {
        atrasBoton.addActionListener(cAtras);
        confirmarBoton.addActionListener(cConfirmar);
        temperaturaSlider.addChangeListener(cTemperatura);
        humedadSlider.addChangeListener(cHumedad);
    }

    public JTable getTablaSalas() {
        return tablaSalas;
    }

    public int getTemperatura(){
        return temperaturaSlider.getValue();
    }

    public int getHumedad(){
        return humedadSlider.getValue();
    }

}