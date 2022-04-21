package com.example.webill.models;

import javax.persistence.*;

@Entity
@Table(name = "users_prod")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String gender;

    public Users(String username, String email, String password, String phone, String address, String gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
    }

    public Users() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
