package ru.oksidisko.ui;

import ru.oksidisko.model.ProtocolEntity;
import ru.oksidisko.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateUserPaymentsDialog extends JDialog {
    private LinkageFrameListener listener;
    private JButton okBtn = new JButton("Ok");
    private JButton cancelBtn = new JButton("Cancel");
    private JTextField fioField = new JTextField();
    private JTextField nickField = new JTextField();
    private JTextField totalField = new JTextField();
    private ProtocolEntity editedEntity = null;
    private JTextField payedField = new JTextField();


    /**
     * Constructor for adding
     * @param owner
     * @param listener
     */
    public UpdateUserPaymentsDialog(JFrame owner, LinkageFrameListener listener) {
        super(owner);
        this.listener = listener;
        setTitle("Edit User payments");
        setModal(true);
        initLayout();
        initListeners();
        setMinimumSize(new Dimension(500, 200));
    }

    /**
     * Constructor for updating
     * @param owner
     * @param listener
     * @param entity
     */
    public UpdateUserPaymentsDialog(JFrame owner, LinkageFrameListener listener, ProtocolEntity entity) {
        this(owner, listener);
        editedEntity = entity;

        fioField.setText(entity.getUser().getName());
        fioField.setEnabled(false);
        nickField.setText(entity.getUser().getNick());
        nickField.setEnabled(false);
        totalField.setText(String.valueOf(entity.getTotalAmountToPay()));
        payedField.setText(String.valueOf(entity.getPaid()));
    }

    private void initListeners() {
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.userUpdated(buildEntity());
                setVisible(false);
            }
        });
    }

    private ProtocolEntity buildEntity() {
        return new ProtocolEntity(editedEntity.getId(), editedEntity.getUser(),
                Double.parseDouble(totalField.getText()), Double.parseDouble(payedField.getText()), editedEntity.getEndDate());
    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel fioLabel = new JLabel("FIO");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        add(fioLabel, c);

        c.gridx = 1;
        add(fioField, c);

        JLabel nickLabel = new JLabel("Nick");
        c.gridx = 0;
        c.gridy++;
        add(nickLabel, c);

        c.gridx = 1;
        add(nickField, c);


        JLabel totalAmountLabel = new JLabel("Total");
        c.gridx = 0;
        c.gridy++;
        add(totalAmountLabel, c);

        c.gridx = 1;
        add(totalField, c);


        JLabel payedAmountLabel = new JLabel("Paid");
        c.gridx = 0;
        c.gridy++;
        add(payedAmountLabel, c);

        c.gridx = 1;
        add(payedField, c);


        c.gridx = 0;
        c.gridy++;
        add(okBtn, c);

        c.weightx = 0.0;
        c.gridx = 1;
        add(cancelBtn, c);
    }
}
