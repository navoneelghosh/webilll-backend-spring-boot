package com.example.webill.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "temp_expenses")
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

    public Maps(int expense_id, String location_name, double total_expense, int visits, double latitude, double longitude) {
        this.expense_id = expense_id;
        this.location_name = location_name;
        this.total_expense = total_expense;
        this.visits = visits;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

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
