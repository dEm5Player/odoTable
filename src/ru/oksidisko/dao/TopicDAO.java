package ru.oksidisko.dao;

import ru.oksidisko.controller.keys.KeyCategory;
import ru.oksidisko.controller.keys.UniqueKeyProvider;
import ru.oksidisko.model.Topic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopicDAO {
    private static List<Listener> listeners = new ArrayList<>();
    private static final Topic EMPTY_TOPIC = new Topic(-1, "Empty", new Date());
    private static List<Topic> topics = new ArrayList<>();

    private static TopicDAO instance = new TopicDAO();

    public static void addListener(Listener listener) {
        listeners.add(listener);
    }

    public static void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public static List<Topic> getAllTopics() {
        return topics;
    }

    public static void addTopic(Topic topic) {
        topics.add(new Topic(UniqueKeyProvider.generateLongId(KeyCategory.TOPIC), topic.getName(), topic.getDate()));
    }

    public static void removeTopic(int index) {
        topics.remove(index);
    }

    public static void updateTopic(Topic topic, int index) {
        long id = topics.get(index).getId();
        topics.remove(index);
        topics.add(index, new Topic(id, topic.getName(), topic.getDate()));
    }

    public static Topic getTopicById(int id) {
        for (Topic topic : topics)
            if (topic.getId() == id)
                return topic;

        return EMPTY_TOPIC;
    }

    public static void loadTopics(List<Topic> topicsToLoad) {
        topics = new ArrayList<Topic>(topicsToLoad);
        notifyUpdated();
    }

    private static void notifyUpdated() {
        for (Listener listener : listeners) {
            listener.topicsLoaded();
        }
    }

    public interface Listener {
        void topicsLoaded();
    }
}
