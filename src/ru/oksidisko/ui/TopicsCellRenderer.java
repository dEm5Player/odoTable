package ru.oksidisko.ui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;

public class TopicsCellRenderer implements TableCellRenderer {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        switch (column) {
            case 0:
                return new JLabel(value.toString());
            case 1:
                return new JLabel(value.toString());
            case 2: // date
                return new JLabel(dateFormat.format(value));

        }
        return new JLabel();
    }
}
