package ru.oksidisko.ui;

import ru.oksidisko.controller.Controller;
import ru.oksidisko.model.Topic;
import ru.oksidisko.ui.model.TopicTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageTopicsFrame extends JInternalFrame implements ActionListener {
    private static final String SELECT_TOPIC_FOR_UPDATE = "Select topic for update";
    private static JFrame owner = null;
    static final int xOffset = 30, yOffset = 30;
    private final JButton addBtn = new JButton("Add");
    private final JButton updateBtn = new JButton("Update");
    private final JButton deleteBtn = new JButton("Delete");
    private final JButton specialBtn = new JButton("Edit Topic Participants");
    private static JInternalFrame instance = new ManageTopicsFrame();
    private final Controller controller;
    private TopicChangeListener listener;
    private JTable table;

    private ManageTopicsFrame() {
        super("Manage topics",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        controller = Controller.getInstance();
        //...Create the GUI and put it in the window...
        initLayout();
        //...Then set the window size or call pack...
        setSize(300,300);

        //Set the window's location.
        setLocation(xOffset, yOffset);
        setListeners();
    }

    private void setListeners() {
        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        specialBtn.addActionListener(this);

        listener = new TopicChangeListener() {
            @Override
            public void topicAdded(Topic topic) {
                System.out.println("userAdded");
                controller.addTopic(topic);
            }

            @Override
            public void topicUpdated(Topic topic) {
                System.out.println("userUpdated");
                int index = getSelectedTopicIndex();
                if (index < 0) {
                    System.out.println("ERROR: selected index = " + index);
                } else {
                    controller.updateTopic(topic, index);
                }
            }
        };
    }
    private void initLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(addBtn, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        add(updateBtn, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        add(deleteBtn, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        add(specialBtn, c);

        JScrollPane scrollPane = new JScrollPane(createUsersTable());

        c.fill = GridBagConstraints.BOTH;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridy = 2;       //third row
        add(scrollPane, c);
    }

    private JTable createUsersTable() {

        TopicTableModel tableModel = new TopicTableModel();
        tableModel.setData(controller.getAllTopics());
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        return table;
    }

    public static JInternalFrame getInstance(JFrame frame) {
        owner = frame;
        return instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            System.out.println("Add button pressed");
            JDialog dialog = new AddUpdateTopicDialog(owner, listener);
            dialog.setVisible(true);
        } else if (e.getSource() == updateBtn) {
            System.out.println("Update button pressed");
            Topic editedTopic = getSelectedTopic();
            if (editedTopic == null) {
                JOptionPane.showMessageDialog(this, SELECT_TOPIC_FOR_UPDATE);
            } else {
                JDialog dialog = new AddUpdateTopicDialog(owner, listener, editedTopic);
                dialog.setVisible(true);
            }
        } else if (e.getSource() == deleteBtn) {
            System.out.println("Delete button pressed");
            int selectedRow = table.getSelectedRow();
            controller.removeTopic(selectedRow);
            table.repaint();
        } else if (e.getSource() == specialBtn) {
            System.out.println("Special button pressed");
            Topic editedTopic = getSelectedTopic();
            if (editedTopic != null) {
                JInternalFrame frame = UserToTopicLinkingFrame.getInstance(owner, editedTopic);
                frame.setVisible(true); //necessary as of 1.3
                Starter.getDesktop().add(frame);
                try {
                    frame.setSelected(true);
                } catch (java.beans.PropertyVetoException ignored) {
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please, select topic",
                        "Usage",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    public Topic getSelectedTopic() {
        int index = table.getSelectedRow();
        return index < 0 ? null : ((TopicTableModel)table.getModel()).getData().get(index);

    }

    public int getSelectedTopicIndex() {
        return table.getSelectedRow();
    }
}
