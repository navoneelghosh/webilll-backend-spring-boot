package com.example.webill.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItems {
    @JsonProperty(value = "date")
    private String date;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "discount")
    private double discount;
    @JsonProperty(value = "discount_rate")
    private double discount_rate;
    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "order")
    private int order;
    @JsonProperty(value = "quantity")
    private int quantity;
    @JsonProperty(value = "price")
    private int price;
    @JsonProperty(value = "section")
    private String section;
    @JsonProperty(value = "text")
    private String text;
    @JsonProperty(value = "type")
    private String type;
    @JsonProperty(value = "total")
    private double total;
    @JsonProperty(value = "unit_of_measure")
    private String unit_of_measure;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(double discount_rate) {
        this.discount_rate = discount_rate;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUnit_of_measure() {
        return unit_of_measure;
    }

    public void setUnit_of_measure(String unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }
}
