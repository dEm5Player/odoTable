package ru.oksidisko.dao;

import ru.oksidisko.controller.keys.KeyCategory;
import ru.oksidisko.controller.keys.UniqueKeyProvider;
import ru.oksidisko.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static User EMPTY_USER = new User(-1, "Empty", "Empty");

    private static List<User> users = new ArrayList<>();
    static {
        users.add(new User(UniqueKeyProvider.generateLongId(KeyCategory.USER), "Andreev Denis", "dEm"));
        users.add(new User(UniqueKeyProvider.generateLongId(KeyCategory.USER), "Antonov Kirill", "Shine"));
        users.add(new User(UniqueKeyProvider.generateLongId(KeyCategory.USER), "Leschinsky Sergey", "Lesch"));
    }

    private static UserDAO instance = new UserDAO();

    public static UserDAO getInstance() {
        return instance;
    }

    public static List<User> getAllUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(new User(UniqueKeyProvider.generateLongId(KeyCategory.USER), user.getName(), user.getNick()));
    }

    public static void removeUser(int index) {
        users.remove(index);
    }

    public static void updateUser(User user, int index) {
        long id = users.get(index).getId();
        users.remove(index);
        users.add(index, new User(id, user.getName(), user.getNick()));
    }

    public static User getUserById(long id) {
        for (User user : users)
            if (user.getId() == id)
                return user;

        return EMPTY_USER;
    }

    public static void loadUsers(List<User> usersToReplace) {
        users = usersToReplace;
    }
}
