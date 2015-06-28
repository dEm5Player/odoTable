package ru.oksidisko.ui;

import ru.oksidisko.controller.Controller;
import ru.oksidisko.model.ProtocolEntity;
import ru.oksidisko.model.Topic;
import ru.oksidisko.model.User;
import ru.oksidisko.ui.model.ProtocolForTopicTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class UserToTopicLinkingFrame extends JInternalFrame implements ActionListener {
    public static final String SELECT_USER_FOR_UPDATE = "Select topic for update";
    private final Controller controller;
    private static JFrame owner = null;
    private static final int xOffset = 30, yOffset = 30;
    private final JButton addBtn = new JButton("Add User");
    private final JButton updateBtn = new JButton("Update Payment Info");
    private final JButton deleteBtn = new JButton("Delete User");
    private final JButton specialBtn = new JButton("Special");
    private static Topic shownTopic;
    private static JLabel topicTitle = new JLabel("");
    private static JInternalFrame instance = null;
    private JTable table;
    private LinkageFrameListener listener;

    private UserToTopicLinkingFrame() {
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

        listener = new LinkageFrameListener() {
            @Override
            public void userAdded(User user) {
                List<ProtocolEntity> entities = controller.getProtocolForTopic(shownTopic);
                entities.add(new ProtocolEntity(-1,user, 1000, 0, new Date()));
                ((ProtocolForTopicTableModel)table.getModel()).setData(entities);
            }

            @Override
            public void userUpdated(ProtocolEntity newEntity) {
                ProtocolEntity updatedEntity = getSelectedEntity();
                if (updatedEntity != null) {
                    controller.updateLinkedEntity(shownTopic, updatedEntity, newEntity);
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
        if (instance == null)
            instance = new UserToTopicLinkingFrame();
        topicTitle.setText(topic.getName());
        owner = frame;
        return instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            System.out.println("Add button pressed");
            JDialog dialog = new AddUserToTopicDialog(owner, listener);
            dialog.setVisible(true);
        } else if (e.getSource() == updateBtn) {
            System.out.println("Update button pressed");
            ProtocolEntity editedEntity = getSelectedEntity();
            if (editedEntity == null) {
                JOptionPane.showMessageDialog(this, SELECT_USER_FOR_UPDATE);
            } else {
                JDialog dialog = new UpdateUserPaymentsDialog(owner, listener, editedEntity);
                dialog.setVisible(true);
            }
        } else if (e.getSource() == deleteBtn) {
            System.out.println("Delete button pressed");
            int selectedRow = table.getSelectedRow();
            ProtocolEntity deletedEntity = ((ProtocolForTopicTableModel) table.getModel()).getEntityById(selectedRow);
            controller.removeUserFromTopic(shownTopic, deletedEntity);
            table.repaint();
        } else if (e.getSource() == specialBtn) {
            System.out.println("Special button pressed");
        }
    }

    public ProtocolEntity getSelectedEntity() {
        int index = table.getSelectedRow();
        return index < 0 ? null : ((ProtocolForTopicTableModel)table.getModel()).getEntityById(index);
    }

}
