package ru.oksidisko.ui;

import ru.oksidisko.model.Topic;

public interface TopicChangeListener {
    void topicAdded(Topic user);
    void topicUpdated(Topic user);
}
