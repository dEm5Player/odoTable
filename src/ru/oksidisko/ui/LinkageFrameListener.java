package ru.oksidisko.ui;

import ru.oksidisko.model.ProtocolEntity;
import ru.oksidisko.model.User;

public interface LinkageFrameListener {
    void userAdded(User user);
    void userUpdated(ProtocolEntity entity);
}
