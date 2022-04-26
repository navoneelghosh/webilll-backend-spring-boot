package com.example.webill.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bills")
public class BillModel implements Serializable {

    @Id
    @Column(name = "billId")
    private int billId;
    private double totalAmount;
    private String billName;
    private String date;
    @Column(name = "paid_by")
    private String paidBy;
    private String latitude;
    private String longitude;

    public BillModel() {
    }

    public BillModel(int billId, double totalAmount, String billName, String date, String paidBy, String latitude, String longitude) {
        this.billId = billId;
        this.totalAmount = totalAmount;
        this.billName = billName;
        this.date = date;
        this.paidBy = paidBy;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "BillModel{" +
                "billId=" + billId +
                ", totalAmount=" + totalAmount +
                ", billName='" + billName + '\'' +
                ", date='" + date + '\'' +
                ", paidBy='" + paidBy + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
