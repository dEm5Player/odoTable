package ru.oksidisko.ui;

import ru.oksidisko.model.Topic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AddUpdateTopicDialog extends JDialog {
    private Topic editedTopic;
    private TopicChangeListener listener;
    private JButton okBtn = new JButton("Ok");
    private JButton cancelBtn = new JButton("Cancel");
    private JTextField titleField = new JTextField();
    private JTextField dateField = new JTextField();


    public AddUpdateTopicDialog(JFrame owner, TopicChangeListener listener) {
        super(owner);
        this.listener = listener;
        setTitle("Add Topic");
        setModal(true);
        initLayout();
        initListeners();
        setMinimumSize(new Dimension(500, 200));
    }

    public AddUpdateTopicDialog(JFrame owner, TopicChangeListener listener, Topic topic) {
        this(owner, listener);
        editedTopic = topic;

        titleField.setText(topic.getName());
        dateField.setText(topic.getDate().toString());
    }

    private void initListeners() {
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editedTopic == null) {
                    listener.topicAdded(buildTopic());
                } else {
                    listener.topicUpdated(buildTopic());
                }
                setVisible(false);
            }
        });
    }

    private Topic buildTopic() {
        return new Topic(-1, titleField.getText(), new Date());
    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel titleLabel = new JLabel("Title");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(titleLabel, c);

        c.weightx = 0.5;
        c.gridx = 1;
        add(titleField, c);


        JLabel nameLabel = new JLabel("Date");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        add(nameLabel, c);

        c.gridx = 1;
        c.weightx = 0.0;
        add(dateField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        add(okBtn, c);

        c.weightx = 0.0;
        c.gridx = 1;
        add(cancelBtn, c);
    }
}
