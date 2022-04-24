package com.example.webill.models;

public class PaymentSheetModel {

    private String setupIntent;
    private String ephemeralKey;
    private String customer;
    private String publishableKey;

    public PaymentSheetModel() {
    }

    public PaymentSheetModel(String setupIntent, String ephemeralKey, String customer, String publishableKey) {
        this.setupIntent = setupIntent;
        this.ephemeralKey = ephemeralKey;
        this.customer = customer;
        this.publishableKey = publishableKey;
    }

    public String getSetupIntent() {
        return setupIntent;
    }

    public void setSetupIntent(String setupIntent) {
        this.setupIntent = setupIntent;
    }

    public String getEphemeralKey() {
        return ephemeralKey;
    }

    public void setEphemeralKey(String ephemeralKey) {
        this.ephemeralKey = ephemeralKey;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPublishableKey() {
        return publishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        this.publishableKey = publishableKey;
    }
}
