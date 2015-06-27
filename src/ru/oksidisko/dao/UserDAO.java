package ru.oksidisko.dao;

import ru.oksidisko.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private List<User> users = new ArrayList<>();

    {
        users.add(new User(1, "Andreev Denis", "dEm"));
        users.add(new User(2, "Antonov Kirill", "Shine"));
        users.add(new User(3, "Leschinsky Sergey", "Lesch"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        int lastIndex = getLastIndex() + 1;
        users.add(new User(lastIndex, user.getName(), user.getNick()));
    }

    public int getLastIndex() {
        int lastIndex = 0;
        for (User user : users) {
            if (user.getId() > lastIndex)
                lastIndex = user.getId();
        }
        return lastIndex;
    }

    public void removeUser(int index) {
        users.remove(index);
    }

    public void updateUser(User user, int index) {
        int id = users.get(index).getId();
        users.remove(index);
        users.add(index, new User(id, user.getName(), user.getNick()));
    }
}
