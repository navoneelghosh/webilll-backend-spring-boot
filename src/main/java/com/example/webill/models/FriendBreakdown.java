package com.example.webill.models;

public class FriendBreakdown {

    private String friend_username;
    private double amountOwed;
    private double amountToPay;

    public FriendBreakdown() {
    }

    public FriendBreakdown(String friend_username, double amountOwed, double amountToPay) {
        this.friend_username = friend_username;
        this.amountOwed = amountOwed;
        this.amountToPay = amountToPay;
    }

    public String getFriend_username() {
        return friend_username;
    }

    public void setFriend_username(String friend_username) {
        this.friend_username = friend_username;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(double amountOwed) {
        this.amountOwed = amountOwed;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }
}
