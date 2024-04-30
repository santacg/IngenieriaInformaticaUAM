package GUI.vistas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ModeloTablaObras extends AbstractTableModel{
    private String[] titulos;
    private Object[][] filas;

    public ModeloTablaObras(String[] columnNames, Object[][] data) {
        this.titulos = columnNames;
        this.filas = data;
    }

    public void addRow(Object[] rowData) {
        List<Object[]> filasList = new ArrayList<>(Arrays.asList(filas));
        filasList.add(rowData);
        filas = filasList.toArray(new Object[0][]);
        fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return titulos.length;
    }

    @Override
    public int getRowCount() {
        return filas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return filas[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0; 
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        filas[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return titulos[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0)
            return Boolean.class; 
        return String.class;
    }
}
