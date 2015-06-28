package ru.oksidisko.controller;

import ru.oksidisko.dao.ProtocolsDAO;
import ru.oksidisko.dao.TopicDAO;
import ru.oksidisko.dao.UserDAO;
import ru.oksidisko.model.ProtocolEntity;
import ru.oksidisko.model.Topic;
import ru.oksidisko.model.User;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class ConfigSaver {
    public void saveToFile() {
        try {
            DataInputStream saveFile = new DataInputStream( new FileInputStream("data.txt"));
            List<User> users = UserDAO.getAllUsers();
            List<Topic> topics = TopicDAO.getAllTopics();
            Map<Topic, List<ProtocolEntity>> protocols = ProtocolsDAO.getAllProtocols();

            // save users
            saveUsers(users, saveFile);
            // save topics
            saveTopics(topics, saveFile);
            // save protocols
            saveProtocols(protocols, saveFile);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveProtocols(Map<Topic, List<ProtocolEntity>> protocols, DataInputStream file) {

    }

    private void saveTopics(List<Topic> topics, DataInputStream file) {

    }

    private void saveUsers(List<User> users, DataInputStream file) {

    }
}
