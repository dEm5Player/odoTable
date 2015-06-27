package ru.oksidisko.model;

import java.util.Date;

public class Topic {
    private final long id;

    private final String name;
    private final Date date;
    public Topic(long id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
}
