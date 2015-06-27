package ru.oksidisko.model;


public class User {
    private String name;
    private String nick;
    private long id;

    public User(long id, String name, String nick) {
        this.id = id;
        this.name = name;
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public long getId() {
        return id;
    }
}
