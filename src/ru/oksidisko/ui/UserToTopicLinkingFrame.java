package ru.oksidisko.ui;

import ru.oksidisko.controller.Controller;
import ru.oksidisko.model.Topic;
import ru.oksidisko.ui.model.ProtocolForTopicTableModel;

import javax.swing.*;
import java.awt.*;

public class UserToTopicLinkingFrame extends JInternalFrame {
    private final Controller controller;
    private static JFrame owner = null;
    private static final int xOffset = 30, yOffset = 30;
    private final JButton addBtn = new JButton("Add");
    private final JButton updateBtn = new JButton("Update");
    private final JButton deleteBtn = new JButton("Delete");
    private final JButton specialBtn = new JButton("Special");
    private static Topic shownTopic;
    private static JLabel topicTitle = new JLabel("");
    private static JInternalFrame instance = new UserToTopicLinkingFrame();
    private JTable table;

    public UserToTopicLinkingFrame() {
        super("Manage topics",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        controller = new Controller();
        //...Create the GUI and put it in the window...
        initLayout();
        //...Then set the window size or call pack...
        setSize(300,300);

        //Set the window's location.
        setLocation(xOffset, yOffset);
        setListeners();
    }

    private void setListeners() {

    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(topicTitle);

        c.gridy++;
        add(addBtn, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        add(updateBtn, c);

        c.weightx = 0.5;
        c.gridx = 2;
        add(deleteBtn, c);

        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy++;
        add(specialBtn, c);

        JScrollPane scrollPane = new JScrollPane(createTopicParticipantsTable());

        c.fill = GridBagConstraints.BOTH;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
//        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridy++;       //third row
        add(scrollPane, c);
    }

    private JTable createTopicParticipantsTable() {
        ProtocolForTopicTableModel tableModel = new ProtocolForTopicTableModel();
        tableModel.setData(controller.getProtocolForTopic(shownTopic));
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setRowSelectionInterval(0, 0);

        return table;
    }

    public static JInternalFrame getInstance(JFrame frame, Topic topic) {
        shownTopic = topic;
        topicTitle.setText(topic.getName());
        owner = frame;
        return instance;
    }
}
