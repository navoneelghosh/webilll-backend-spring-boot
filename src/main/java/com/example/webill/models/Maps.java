package com.example.webill.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Maps {

    @Id
    private int expense_id;
    private String location_name;
    private double total_expense;
    private int visits;
    private double latitude;
    private double longitude;

    public Maps() {
    }

    public Maps(int expense_id, String location_name, double total_expense, int visits, String latitude, String longitude) {
        this.expense_id = expense_id;
        this.location_name = location_name;
        this.total_expense = total_expense;
        this.visits = visits;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public double getTotal_expense() {
        return total_expense;
    }

    public void setTotal_expense(double total_expense) {
        this.total_expense = total_expense;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
