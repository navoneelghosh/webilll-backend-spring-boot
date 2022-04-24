package com.example.webill.models;

import javax.persistence.*;

@Entity
@Table(name = "bill_split")
public class Bill_Split {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bill_split_id;
    private int billId;
    private String usernameFrom;
    private String usernameTo;
    private double amount;
    private String isSettled;

    public Bill_Split() {
    }

    public Bill_Split(int bill_split_id, int billId, String usernameFrom, String usernameTo, double amount, String isSettled) {
        this.bill_split_id = bill_split_id;
        this.billId = billId;
        this.usernameFrom = usernameFrom;
        this.usernameTo = usernameTo;
        this.amount = amount;
        this.isSettled = isSettled;
    }

    public int getBill_split_id() {
        return bill_split_id;
    }

    public void setBill_split_id(int bill_split_id) {
        this.bill_split_id = bill_split_id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public void setUsernameFrom(String usernameFrom) {
        this.usernameFrom = usernameFrom;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(String isSettled) {
        this.isSettled = isSettled;
    }
}
