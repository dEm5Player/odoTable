package ru.oksidisko.ui;

import ru.oksidisko.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUpdateUserDialog extends JDialog {
    private UserChangeListener listener;
    private JButton okBtn = new JButton("Ok");
    private JButton cancelBtn = new JButton("Cancel");
    private JTextField fioField = new JTextField();
    private JTextField nameField = new JTextField();
    private User editedUser = null;


    /**
     * Constructor for adding
     * @param owner
     * @param listener
     */
    public AddUpdateUserDialog(JFrame owner, UserChangeListener listener) {
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
    public AddUpdateUserDialog(JFrame owner, UserChangeListener listener, User user) {
        this(owner, listener);
        editedUser = user;

        fioField.setText(user.getName());
        nameField.setText(user.getNick());
    }

    private void initListeners() {
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editedUser == null) {
                    listener.userAdded(buildUser());
                } else {
                    listener.userUpdated(buildUser());
                }
                setVisible(false);
            }
        });
    }

    private User buildUser() {
        return new User(-1, fioField.getText(), nameField.getText());
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
        add(fioField, c);


        JLabel nameLabel = new JLabel("Nick");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        add(nameLabel, c);

        c.gridx = 1;
        c.weightx = 0.0;
        add(nameField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        add(okBtn, c);

        c.weightx = 0.0;
        c.gridx = 1;
        add(cancelBtn, c);
    }
}
