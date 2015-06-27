package ru.oksidisko.ui.model;

import ru.oksidisko.model.ProtocolEntity;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ProtocolForTopicTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES = {"FIO", "Nick","Total", "Paid", "end date"};

    private List<ProtocolEntity> data;

    public void setData(List<ProtocolEntity> data) {
        this.data = data;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnIndex >= COLUMN_NAMES.length ? "" : COLUMN_NAMES[columnIndex];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProtocolEntity entity = data.get(rowIndex);
        switch (columnIndex){
            case 0: return entity.getUser().getName();
            case 1: return entity.getUser().getNick();
            case 2: return entity.getTotalAmountToPay();
            case 3: return entity.getPaid();
            case 4: return entity.getEndDate();
            default: return "unknown column";
        }
    }
}
