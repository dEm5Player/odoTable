package ru.oksidisko.dao;

import ru.oksidisko.model.Topic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TopicDAO {
    private List<Topic> topics = new ArrayList<>();

    public List<Topic> getAllTopics() {
        Calendar cal = Calendar.getInstance();
        cal.set(2016, Calendar.JANUARY, 15);
        Date date = cal.getTime();


        topics.add(new Topic(1, "Vizov", date));

        cal.set(2016, Calendar.FEBRUARY, 25);
        date = cal.getTime();
        topics.add(new Topic(2, "Camp", date));

        cal.set(2016, Calendar.JULY, 7);
        date = cal.getTime();
        topics.add(new Topic(3, "Equipment", date));
        return topics;
    }

    public void addTopic(Topic topic) {
        long lastIndex = getLastIndex() + 1;
        topics.add(new Topic(lastIndex, topic.getName(), topic.getDate()));
    }

    public long getLastIndex() {
        long lastIndex = 0;
        for (Topic topic : topics) {
            if (topic.getId() > lastIndex)
                lastIndex = topic.getId();
        }
        return lastIndex;
    }

    public void removeTopic(int index) {
        topics.remove(index);
    }

    public void updateTopic(Topic topic, int index) {
        long id = topics.get(index).getId();
        topics.remove(index);
        topics.add(index, new Topic(id, topic.getName(), topic.getDate()));
    }
}
