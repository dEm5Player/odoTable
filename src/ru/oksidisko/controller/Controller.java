package ru.oksidisko.controller;

import ru.oksidisko.dao.ProtocolsDAO;
import ru.oksidisko.dao.TopicDAO;
import ru.oksidisko.dao.UserDAO;
import ru.oksidisko.model.ProtocolEntity;
import ru.oksidisko.model.Topic;
import ru.oksidisko.model.User;

import java.util.List;

public class Controller {
    private final UserDAO userDAO = new UserDAO();
    private final TopicDAO topicDAO = new TopicDAO();
    private final ProtocolsDAO protocolsDAO = new ProtocolsDAO();

    public Controller() {

    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<Topic> getAllTopics() {
        return topicDAO.getAllTopics();
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public void addTopic(Topic topic) {
        topicDAO.addTopic(topic);
    }

    public void removeUser(int index) {
        userDAO.removeUser(index);
    }

    public void removeTopic(int index) {
        topicDAO.removeTopic(index);
    }

    public void updateUser(User user, int index) {
        userDAO.updateUser(user, index);
    }

    public void updateTopic(Topic topic, int index) {
        topicDAO.updateTopic(topic, index);
    }

    public List<ProtocolEntity> getProtocolForTopic(Topic topic) {
        return protocolsDAO.getProtocolForTopic(topic);
    }
}
