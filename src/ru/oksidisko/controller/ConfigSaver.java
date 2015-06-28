package ru.oksidisko.controller;

import ru.oksidisko.dao.ProtocolsDAO;
import ru.oksidisko.dao.TopicDAO;
import ru.oksidisko.dao.UserDAO;
import ru.oksidisko.model.ProtocolEntity;
import ru.oksidisko.model.Topic;
import ru.oksidisko.model.User;

import java.io.*;
import java.util.*;

public class ConfigSaver {

    public static final String USERS = "d:/users.txt";
    public static final String TOPICS = "d:/topics.txt";
    public static final String PROTOCOLS = "d:/protocols.txt";

    public void restoreFromFile() {
        try {
            DataInputStream loadFileUsers = new DataInputStream(new FileInputStream(USERS));
            DataInputStream loadFileTopics = new DataInputStream(new FileInputStream(TOPICS));
            DataInputStream loadFileProtocols = new DataInputStream(new FileInputStream(PROTOCOLS));

            List<User> users = loadUsers(loadFileUsers);
            List<Topic> topics = loadTopics(loadFileTopics);
            Map<Topic, List<ProtocolEntity>> protocols = loadProtocols(loadFileProtocols);

            UserDAO.loadUsers(users);
            TopicDAO.loadTopics(topics);
            ProtocolsDAO.loadProtocols(protocols);

            closeConnections(loadFileUsers);
            closeConnections(loadFileTopics);
            closeConnections(loadFileProtocols);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    private void closeConnections(DataInputStream stream) {
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void saveToFile() {
        try {
            DataOutputStream saveFileUsers = new DataOutputStream(new FileOutputStream(USERS));
            DataOutputStream saveFileTopics = new DataOutputStream(new FileOutputStream(TOPICS));
            DataOutputStream saveFileProtocols = new DataOutputStream(new FileOutputStream(PROTOCOLS));

            List<User> users = UserDAO.getAllUsers();
            List<Topic> topics = TopicDAO.getAllTopics();
            Map<Topic, List<ProtocolEntity>> protocols = ProtocolsDAO.getAllProtocols();

            // save users
            saveUsers(users, saveFileUsers);
            // save topics
            saveTopics(topics, saveFileTopics);
            // save protocols
            saveProtocols(protocols, saveFileProtocols);

            closeConnections(saveFileUsers);
            closeConnections(saveFileTopics);
            closeConnections(saveFileProtocols);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void closeConnections(DataOutputStream stream) {
        try {
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void saveUsers(List<User> users, DataOutputStream stream) {
        try {
            stream.writeInt(users.size());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        for (User user : users) {
            saveUser(user, stream);
        }

    }

    private List<User> loadUsers(DataInputStream stream) {
        List<User> userList = new ArrayList<>();
        try {
            int size = stream.readInt();
            System.out.println("loadUsers size = " + size);
            for (int i = 0; i < size; i++) {
                System.out.println("i=" + i);
                User user = loadUser(stream);
                userList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return userList;
    }

    private List<Topic> loadTopics(DataInputStream file) {
        List<Topic> topicList = new ArrayList<>();
        try {
            int size = file.readInt();
            System.out.println("loadTopics size = " + size);
            for (int i = 0; i < size; i++) {
                System.out.println("i = " + i);
                Topic topic = loadTopic(file);
                topicList.add(topic);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return topicList;
    }

    private Map<Topic, List<ProtocolEntity>> loadProtocols(DataInputStream file) {
        Map<Topic, List<ProtocolEntity>> map = new HashMap<>();
        try {
            int size = file.readInt();
            System.out.println("loadProtocols size = " + size);
            for (int i = 0; i < size; i++) {
                Topic topic = loadTopic(file);
                int entitiesSize = file.readInt();
                List<ProtocolEntity> protocolEntities = new ArrayList<>();
                for (int j = 0; j < entitiesSize; j++){
                    long protocolId = file.readLong();
                    long userId = file.readLong();
                    double total = file.readDouble();
                    double paid = file.readDouble();
                    Date endDate = new Date(file.readLong());
                    ProtocolEntity entity = new ProtocolEntity(protocolId, UserDAO.getUserById(userId), total, paid, endDate);
                    protocolEntities.add(entity);
                }
                map.put(topic, protocolEntities);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return map;
    }

    private Topic loadTopic(DataInputStream file) {
        try {
            long id = file.readLong();
            String name = file.readUTF();
            Date date = new Date(file.readLong());

            return new Topic(id, name, date);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    private User loadUser(DataInputStream file) {
        try {
            long id = file.readLong();
            String name = file.readUTF();
            String nick = file.readUTF();

            return new User(id, name, nick);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    private void saveUser(User user, DataOutputStream file) {
        long id = user.getId();
        String name = user.getName();
        String nick = user.getNick();

        try {
            file.writeLong(id);
            file.writeUTF(name);
            file.writeUTF(nick);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void saveTopics(List<Topic> topics, DataOutputStream file) {
        try {
            file.writeInt(topics.size());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        for (Topic topic : topics) {
            saveTopic(topic, file);
        }
    }

    private void saveTopic(Topic topic, DataOutputStream file) {
        long id = topic.getId();
        String name = topic.getName();
        Date date = topic.getDate();

        try {
            file.writeLong(id);
            file.writeUTF(name);
            file.writeLong(date.getTime());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    private void saveProtocols(Map<Topic, List<ProtocolEntity>> protocols, DataOutputStream file) {
        try {
            file.writeInt(protocols.size());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        for (Map.Entry<Topic, List<ProtocolEntity>> entry : protocols.entrySet()) {
            saveTopic(entry.getKey(), file);

            saveProtocolList(entry.getValue(), file);
        }
    }

    private void saveProtocolList(List<ProtocolEntity> list, DataOutputStream file) {
        try {
            file.writeInt(list.size());

            for(ProtocolEntity entity : list) {
                long protocolId = entity.getId();
                long userId = entity.getUser().getId();
                double total = entity.getTotalAmountToPay();
                double paid = entity.getPaid();
                long endDate = entity.getEndDate().getTime();

                file.writeLong(protocolId);
                file.writeLong(userId);
                file.writeDouble(total);
                file.writeDouble(paid);
                file.writeLong(endDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }
}
