package ru.oksidisko.model;

import java.util.Date;

public class ProtocolEntity {
    private long id;
    private User user;
    private double totalAmountToPay;
    private double paid;

    public ProtocolEntity(long id, User user, double totalAmountToPay, double paid, Date endDate) {
        this.id = id;
        this.user = user;
        this.totalAmountToPay = totalAmountToPay;
        this.paid = paid;
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getPaid() {
        return paid;
    }

    public double getTotalAmountToPay() {
        return totalAmountToPay;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    Date endDate;
}
