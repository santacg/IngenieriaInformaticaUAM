package GUI.vistas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Clase ModeloTablaObras.
 * Implementa un modelo de tabla para la vista de obras.
 * 
 * @author Carlos García Santa, Joaquín Abad Díaz y Eduardo Junoy Ortega
 */
public class ModeloTablaObras extends AbstractTableModel {
    private String[] titulos;
    private Object[][] filas;

    /**
     * Constructor de la clase ModeloTablaObras.
     * 
     * @param columnNames Nombres de las columnas.
     * @param data        Datos de las filas.
     */
    public ModeloTablaObras(String[] columnNames, Object[][] data) {
        this.titulos = columnNames;
        this.filas = data;
    }

    /**
     * Añade una fila a la tabla.
     * 
     * @param rowData Datos de la fila.
     */
    public void addRow(Object[] rowData) {
        List<Object[]> filasList = new ArrayList<>(Arrays.asList(filas));
        filasList.add(rowData);
        filas = filasList.toArray(new Object[0][]);
        fireTableDataChanged();
    }

    /**
     * Obtiene el recuento de columnas.
     */
    @Override
    public int getColumnCount() {
        return titulos.length;
    }

    /**
     * Obtiene el recuento de filas.
     */
    @Override
    public int getRowCount() {
        return filas.length;
    }

    /**
     * Obtiene el valor de una celda.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return filas[rowIndex][columnIndex];
    }

    /**
     * Obtiene si la primera columna es editable.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    /**
     * Establece el valor de una celda.
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        filas[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    /**
     * Obtiene el nombre de una columna.
     */
    @Override
    public String getColumnName(int column) {
        return titulos[column];
    }

    /**
     * Obtiene la clase de una columna.
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0)
            return Boolean.class;
        return String.class;
    }
}
