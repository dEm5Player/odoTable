package ru.oksidisko.ui;

import ru.oksidisko.model.User;

public interface UserChangeListener {
    void userAdded(User user);
    void userUpdated(User user);
}
