package es.ucm.fdi.tp.mvc;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PlayerModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String[] colNames;
    private List<String> names;

    public PlayerModel(List<String> playersName) {
        this.colNames = new String[] { "#Player", "Color" };
        names = playersName;
    }

    @Override
    public String getColumnName(int col) {
        return colNames[col];
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public int getRowCount() {
        return names != null ? names.size() : 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rowIndex;
        } else {
            return names.get(rowIndex);
        }
    }

    public void addName(String name) {
        names.add(name);
        refresh();
    }

    private void refresh() {
        fireTableDataChanged();
    }
}
