package ru.oksidisko.ui.model;

import ru.oksidisko.model.Topic;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TopicTableModel extends AbstractTableModel {

    public static final String[] COLUMN_NAMES = {"Id", "Name",
            "Date"};

    private List<Topic> topicList = new ArrayList<>();

    public TopicTableModel() {
    }


    public List<Topic> getData() {
        return Collections.unmodifiableList(topicList);
    }

    public void setData(List<Topic> userList) {
        this.topicList = userList;
    }

    // interface methods

    @Override
    public int getRowCount() {
        return topicList.size();
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
        Topic topic = topicList.get(rowIndex);
        switch (columnIndex){
            case 0: return topic.getId();
            case 1: return topic.getName();
            case 2: return topic.getDate();
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
