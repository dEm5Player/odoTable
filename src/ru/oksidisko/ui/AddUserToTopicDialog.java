package ru.oksidisko.ui;

import ru.oksidisko.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserToTopicDialog extends JDialog {
    private LinkageFrameListener listener;
    private JButton okBtn = new JButton("Ok");
    private JButton cancelBtn = new JButton("Cancel");
    private JTextField fioField = new JTextField();
    private JTextField nameField = new JTextField();
    private User editedUser = null;
    private String[] petStrings = { "Shine", "dEm", "Lesch", "Rabbit", "Pig" };
    private JComboBox petList = new JComboBox<>(petStrings);


    /**
     * Constructor for adding
     * @param owner
     * @param listener
     */
    public AddUserToTopicDialog(JFrame owner, LinkageFrameListener listener) {
        super(owner);
        this.listener = listener;
        setTitle("Add User");
        setModal(true);
        initLayout();
        initListeners();
        setMinimumSize(new Dimension(500, 200));
    }

    /**
     * Constructor for updating
     * @param owner
     * @param listener
     * @param user
     */
    public AddUserToTopicDialog(JFrame owner, LinkageFrameListener listener, User user) {
        this(owner, listener);
        editedUser = user;

        fioField.setText(user.getName());
        nameField.setText(user.getNick());
    }

    private void initListeners() {
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.userAdded(buildUser());
                setVisible(false);
            }
        });
    }

    private User buildUser() {
        return new User(-1, (String)petList.getSelectedItem(), "created in auttd");
    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel fioLabel = new JLabel("FIO");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(fioLabel, c);

        c.weightx = 0.5;
        c.gridx = 1;
        add(petList, c);


        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        add(okBtn, c);

        c.weightx = 0.0;
        c.gridx = 1;
        add(cancelBtn, c);
    }
}
