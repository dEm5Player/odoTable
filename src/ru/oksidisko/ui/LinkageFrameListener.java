package ru.oksidisko.ui;

import ru.oksidisko.model.User;

public interface LinkageFrameListener {
    void userAdded(User user);
    void userUpdated(User user);
    void userDeleted(User user);
}
