package ru.oksidisko.ui;

import ru.oksidisko.controller.Controller;
import ru.oksidisko.model.User;
import ru.oksidisko.ui.model.UserTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* Used by Starter.java. */
public class ManageUsersFrame extends JInternalFrame implements ActionListener {
    public static final String SELECT_USER_FOR_UPDATE = "Select user for update";
    private static JFrame owner = null;
    private static final int xOffset = 50, yOffset = 50;
    private final Controller controller;
    private final JButton addBtn = new JButton("Add");
    private final JButton updateBtn = new JButton("Update");
    private final JButton deleteBtn = new JButton("Delete");
    private final JButton specialBtn = new JButton("Special");
    private JTable table;
    private UserChangeListener listener;

    private static final JInternalFrame instance = new ManageUsersFrame();


    private ManageUsersFrame() {
        super("Manage users",
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

        listener = new UserChangeListener() {
            @Override
            public void userAdded(User user) {
                System.out.println("userAdded");
                controller.addUser(user);
            }

            @Override
            public void userUpdated(User user) {
                System.out.println("userUpdated");
                int index = getSelectedUserIndex();
                if (index < 0) {
                    System.out.println("ERROR: selected index = " + index);
                } else {
                    controller.updateUser(user, index);
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
        UserTableModel tableModel = new UserTableModel();
        tableModel.setData(controller.getAllUsers());
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setRowSelectionInterval(0, 0);

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
            JDialog dialog = new AddUpdateUserDialog(owner, listener);
            dialog.setVisible(true);
        } else if (e.getSource() == updateBtn) {
            System.out.println("Update button pressed");
            User editedUser = getSelectedUser();
            if (editedUser == null) {
                JOptionPane.showMessageDialog(this, SELECT_USER_FOR_UPDATE);
            } else {
                JDialog dialog = new AddUpdateUserDialog(owner, listener, editedUser);
                dialog.setVisible(true);
            }
        } else if (e.getSource() == deleteBtn) {
            System.out.println("Delete button pressed");
            int selectedRow = table.getSelectedRow();
            controller.removeUser(selectedRow);
            table.repaint();
        } else if (e.getSource() == specialBtn) {
            System.out.println("Special button pressed");
        }
    }

    public User getSelectedUser() {
        int index = table.getSelectedRow();
        return index < 0 ? null : ((UserTableModel)table.getModel()).getData().get(index);
    }

    public int getSelectedUserIndex() {
        return table.getSelectedRow();
    }
}