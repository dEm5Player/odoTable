package ru.oksidisko.model;


public class User {
    private String name;
    private String nick;
    private int id;

    public User(int id, String name, String nick) {
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

    public int getId() {
        return id;
    }
}
