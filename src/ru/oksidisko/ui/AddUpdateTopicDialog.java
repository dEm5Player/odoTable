package ru.oksidisko.ui;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ru.oksidisko.model.Topic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class AddUpdateTopicDialog extends JDialog {
    private Topic editedTopic;
    private TopicChangeListener listener;
    private JButton okBtn = new JButton("Ok");
    private JButton cancelBtn = new JButton("Cancel");
    private JTextField titleField = new JTextField();
    private JDatePicker datePicker;


    public AddUpdateTopicDialog(JFrame owner, TopicChangeListener listener) {
        super(owner);
        this.listener = listener;
        datePicker = (new JDateComponentFactory()).createJDatePicker();
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
        datePicker.setTextEditable(true);
        datePicker.setShowYearButtons(true);
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
        return new Topic(-1, titleField.getText(), dateDateFromPicker(datePicker));
    }

    private Date dateDateFromPicker(JDatePicker datePicker) {
        DateModel model = datePicker.getModel();
        int year = model.getYear();
        int month = model.getMonth();
        int day = model.getDay();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 1);
        return calendar.getTime();
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
        add((JComponent)datePicker, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        add(okBtn, c);

        c.weightx = 0.0;
        c.gridx = 1;
        add(cancelBtn, c);
    }
}
