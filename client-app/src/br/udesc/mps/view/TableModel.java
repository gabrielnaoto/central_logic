package br.udesc.mps.view;

import javax.swing.table.AbstractTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ignoi
 */
public class TableModel extends AbstractTableModel {

    private Object[] indexes;
    private Object[] values;

    public TableModel() {
        values = new Object[0];
        indexes = new Object[0];
    }

    @Override
    public int getRowCount() {
        return values.length;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return indexes[rowIndex];
        } else {
            return values[rowIndex];
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        values[rowIndex] = aValue;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public String getColumnName(int column) {
        return column == 0 ? "Index" : "Value";
    }

    public void prepare(int offset, int limit) {
        indexes = new Object[limit];
        values = new Object[limit];
        for (int i = 0; i < limit; i++) {
            indexes[i] = i + offset;
        }
        super.fireTableDataChanged();
    }

    public void update(int offset, int limit, Object[] values) {
        indexes = new Object[limit];
        this.values = values;
        for (int i = 0; i < limit; i++) {
            indexes[i] = i + offset;
        }
        super.fireTableDataChanged();
    }

    public void clear() {
        indexes = new Object[0];
        values = new Object[0];
        super.fireTableDataChanged();
    }

    public Object[] getValues() {
        return values;
    }

}
