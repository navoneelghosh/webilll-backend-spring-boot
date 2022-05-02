package com.example.webill.models;

public class ChangePhone {
    private String username;
    private String newPhone;

    public ChangePhone() {
    }

    public ChangePhone(String username, String newPhone) {
        this.username = username;
        this.newPhone = newPhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }
}
