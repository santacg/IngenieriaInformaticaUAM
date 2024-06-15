package gui.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;

/**
 * Clase DesbloqueoClientes
 * Implementación de la vista de ajuste de climatización
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class DesbloqueoClientes extends JPanel {
    private JButton atrasBoton;
    private JTable tablaClientes;
    private JPanel gestionClientes;
    private JPanel panelTabla;

    private JButton DesbloquearBoton;

    /**
     * Constructor de la clase DesbloqueoClientes
     */
    public DesbloqueoClientes() {
        setLayout(new BorderLayout());

        this.gestionClientes = new JPanel();
        gestionClientes.setLayout(new BorderLayout());

        this.panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout());

        addButtons();

        add(gestionClientes, BorderLayout.SOUTH);
        add(panelTabla, BorderLayout.CENTER);
    }

    /**
     * Método que añade una tabla de sorteos a la vista.
     * 
     * @param data ArrayList de objetos que contiene los datos de las
     *             sorteos.
     */
    public void addTablaClientes(ArrayList<Object[]> data) {
        String[] titulos = { "NIF"};
        Object[][] datos = data.toArray(new Object[0][]);
        tablaClientes = new JTable(new DefaultTableModel(datos, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tablaClientes.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tablaClientes.setFillsViewportHeight(true);

        this.panelTabla.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
    }


    /**
     * Método que añade los sliders de temperatura y humedad a la vista
     */
    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(DesbloquearBoton = new JButton("Desbloquear"));
        buttonPanel.add(atrasBoton = new JButton("Atrás"));
        this.gestionClientes.add(buttonPanel, BorderLayout.SOUTH);

    }

    /**
     * Método que establece el controlador de la vista
     * 
     * @param ActionListener cAtras
     */
    public void setControlador(ActionListener cAtras, ActionListener cDesbloquear) {
        atrasBoton.addActionListener(cAtras);
        DesbloquearBoton.addActionListener(cDesbloquear);
    }

    public JTable getTablaClientes() {
        return tablaClientes;
    }

}