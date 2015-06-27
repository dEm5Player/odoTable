package ru.oksidisko.dao;

import ru.oksidisko.model.ProtocolEntity;
import ru.oksidisko.model.Topic;
import ru.oksidisko.model.User;

import java.util.*;

public class ProtocolsDAO {
    private HashMap<Topic, List<ProtocolEntity>> map = new HashMap<>();
    private long tmpId = -1;

    private ProtocolEntity STAB_ENTITY = new ProtocolEntity(-1, new User(-2, "Test Name", "test nick"), 1000, 850, new Date());
    private ProtocolEntity STAB_ENTITY2 = new ProtocolEntity(-1, new User(-3, "Test Name2", "test nick2"), 900, 350, new Date());
    private List<ProtocolEntity> list = new ArrayList<>();
    {
        list.add(STAB_ENTITY);
        list.add(STAB_ENTITY2);
    }
    public List<ProtocolEntity> getProtocolForTopic(Topic topic) {
//        List<ProtocolEntity> protocolForTopic = map.get(topic);
//        if (topic == null) {
//            protocolForTopic = new ArrayList<>();
//            map.put(topic, protocolForTopic);
//        }
//        return protocolForTopic;
        return list;
    }

    public void setProtocolForTopic(Topic topic, List<ProtocolEntity> protocolForTopic){
        if (topic != null)
            map.put(topic, protocolForTopic);
    }


    public void updateLinkedEntity(Topic shownTopic, ProtocolEntity updatedEntity, ProtocolEntity newEntity) {
//        List<ProtocolEntity> protocolForTopic = map.get(topic);
        int index = 0;
        for (ProtocolEntity protocolEntity : list) {
            if (protocolEntity.getUser().getName().equals(updatedEntity.getUser().getName())) {
                break;
            }
            index++;
        }
        list.remove(index);
        list.add(index, newEntity);
    }

    public void removeUserFromTopic(Topic topic, ProtocolEntity entity) {
        //map.get(topic).remove(user);
/*        ProtocolEntity removedEntity = null;
        for (ProtocolEntity protocolEntity : list) {
            if (Objects.equals(protocolEntity.getUser().getName(), user.getName())) {
                removedEntity = protocolEntity;
                break;
            }
        }*/
        if (entity != null)
            list.remove(entity);
    }
}
