package com.example.webill.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "temp_expenses")
public class Maps {

    private String location_name;
    private double total_expense;
    private int visits;
    private double latitude;
    private double longitude;

    public String getLocation_name() {
        return this.location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public double getTotal_expense() {
        return this.total_expense;
    }

    public void setTotal_expense(double total_expense) {
        this.total_expense = total_expense;
    }

    public int getVisits() {
        return this.visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
