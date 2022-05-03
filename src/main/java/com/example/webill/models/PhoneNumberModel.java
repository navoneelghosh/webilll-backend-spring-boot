package com.example.webill.models;

public class PhoneNumberModel {

    private String username;
    private String phoneNum;

    public PhoneNumberModel(String username, String phoneNum) {
        this.username = username;
        this.phoneNum = phoneNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
