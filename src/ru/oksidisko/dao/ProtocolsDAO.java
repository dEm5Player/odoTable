package ru.oksidisko.dao;

import ru.oksidisko.model.ProtocolEntity;
import ru.oksidisko.model.Topic;
import ru.oksidisko.model.User;

import java.util.*;

public class ProtocolsDAO {
    private HashMap<Topic, List<ProtocolEntity>> map = new HashMap<>();


    {
        UserDAO userDAO = UserDAO.getInstance();
        TopicDAO topicDAO = TopicDAO.getInstance();

        ProtocolEntity STAB_ENTITY = new ProtocolEntity(-1, userDAO.getUserById(1), 1000, 850, new Date());
        ProtocolEntity STAB_ENTITY2 = new ProtocolEntity(-1, userDAO.getUserById(1), 900, 350, new Date());
        List<ProtocolEntity> list = new ArrayList<>();
        list.add(STAB_ENTITY);
        list.add(STAB_ENTITY2);

        map.put(topicDAO.getTopicById(1), list);
    }

    public List<ProtocolEntity> getProtocolForTopic(Topic topic) {
        List<ProtocolEntity> protocolForTopic = map.get(topic);
        if (protocolForTopic == null) {
            protocolForTopic = new ArrayList<>();
            map.put(topic, protocolForTopic);
        }
        return protocolForTopic;
    }

    public void setProtocolForTopic(Topic topic, List<ProtocolEntity> protocolForTopic){
        if (topic != null)
            map.put(topic, protocolForTopic);
    }


    public void updateLinkedEntity(Topic topic, ProtocolEntity updatedEntity, ProtocolEntity newEntity) {
        List<ProtocolEntity> protocolForTopic = map.get(topic);
        if (!protocolForTopic.isEmpty()) {
            int index = 0;
            for (ProtocolEntity protocolEntity : protocolForTopic) {
                if (protocolEntity.getUser().getName().equals(updatedEntity.getUser().getName())) {
                    break;
                }
                index++;
            }
            protocolForTopic.remove(index);
            protocolForTopic.add(index, newEntity);
        } else {
            protocolForTopic.add(newEntity); // todo: или тут эксепшен должен быть?
        }
    }

    public void removeUserFromTopic(Topic topic, ProtocolEntity entity) {
        map.get(topic).remove(entity);
    }
}
