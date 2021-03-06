package ru.oksidisko.dao;

import ru.oksidisko.model.ProtocolEntity;
import ru.oksidisko.model.Topic;

import java.util.*;

public class ProtocolsDAO {
    private static Map<Topic, List<ProtocolEntity>> map = new HashMap<>();

    public static List<ProtocolEntity> getProtocolForTopic(Topic topic) {
        List<ProtocolEntity> protocolForTopic = map.get(topic);
        if (protocolForTopic == null) {
            protocolForTopic = new ArrayList<>();
            map.put(topic, protocolForTopic);
        }
        return protocolForTopic;
    }

    public static void setProtocolForTopic(Topic topic, List<ProtocolEntity> protocolForTopic){
        if (topic != null)
            map.put(topic, protocolForTopic);
    }


    public static void updateLinkedEntity(Topic topic, ProtocolEntity updatedEntity, ProtocolEntity newEntity) {
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
            throw new RuntimeException("ProtocolsDAO.updateLinkedEntity() for unexisted topic");
            //protocolForTopic.add(newEntity); // todo: ��� ��� �������� ������ ����?
        }
    }

    public static void removeUserFromTopic(Topic topic, ProtocolEntity entity) {
        map.get(topic).remove(entity);
    }

    public static Map<Topic, List<ProtocolEntity>> getAllProtocols() {
        return map;
    }

    public static void loadProtocols(Map<Topic, List<ProtocolEntity>> newPorotocols) {
        map = newPorotocols;
    }
}