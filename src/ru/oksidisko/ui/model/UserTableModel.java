package ru.oksidisko.ui.model;

import ru.oksidisko.model.User;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"Id", "Name",
            "Nick"};

    private List<User> userList = new ArrayList<>();

    public UserTableModel() {
    }


    public List<User> getData() {
        return Collections.unmodifiableList(userList);
    }

    public void setData(List<User> userList) {
        this.userList = userList;
    }

    // interface methods

    @Override
    public int getRowCount() {
        return userList.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnIndex >= COLUMN_NAMES.length ? "" : COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = userList.get(rowIndex);
        switch (columnIndex){
            case 0: return user.getId();
            case 1: return user.getName();
            case 2: return user.getNick();
            default: return "unknown column";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
