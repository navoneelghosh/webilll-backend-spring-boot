package com.example.webill.models;

public class UserBalance {

    private double amountOwed;
    private double amountToPay;

    public UserBalance() {
    }

    public UserBalance(double amountOwed, double amountToPay) {
        this.amountOwed = amountOwed;
        this.amountToPay = amountToPay;
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
