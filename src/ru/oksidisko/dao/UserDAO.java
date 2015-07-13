package ru.oksidisko.dao;

import ru.oksidisko.controller.keys.KeyCategory;
import ru.oksidisko.controller.keys.UniqueKeyProvider;
import ru.oksidisko.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static List<Listener> listeners = new ArrayList<>();
    // for example for case: user deleted but contained in protocols
    public static User EMPTY_USER = new User(-1, "Empty", "Empty");

    private static List<User> users = new ArrayList<>();

    public static void addListener(Listener listener) {
        listeners.add(listener);
    }

    public static void removeListener(Listener listener) {
        listeners.remove(listener);
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

    // future: check user for participation in protocols before remove
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
        notifyUpdated();
    }

    private static void notifyUpdated() {
        for (Listener listener : listeners) {
            listener.usersLoaded();
        }
    }

    public interface Listener {
        void usersLoaded();
    }
}
