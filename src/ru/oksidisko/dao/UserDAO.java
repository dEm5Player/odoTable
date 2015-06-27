package ru.oksidisko.dao;

import ru.oksidisko.controller.keys.KeyCategory;
import ru.oksidisko.controller.keys.UniqueKeyProvider;
import ru.oksidisko.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static User EMPTY_USER = new User(-1, "Empty", "Empty");

    private List<User> users = new ArrayList<>();
    {
        users.add(new User(UniqueKeyProvider.generateLongId(KeyCategory.USER), "Andreev Denis", "dEm"));
        users.add(new User(UniqueKeyProvider.generateLongId(KeyCategory.USER), "Antonov Kirill", "Shine"));
        users.add(new User(UniqueKeyProvider.generateLongId(KeyCategory.USER), "Leschinsky Sergey", "Lesch"));
    }

    private static UserDAO instance = new UserDAO();

    public static UserDAO getInstance() {
        return instance;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(new User(UniqueKeyProvider.generateLongId(KeyCategory.USER), user.getName(), user.getNick()));
    }

    public void removeUser(int index) {
        users.remove(index);
    }

    public void updateUser(User user, int index) {
        long id = users.get(index).getId();
        users.remove(index);
        users.add(index, new User(id, user.getName(), user.getNick()));
    }

    public User getUserById(long id) {
        for (User user : users)
            if (user.getId() == id)
                return user;

        return EMPTY_USER;
    }
}
