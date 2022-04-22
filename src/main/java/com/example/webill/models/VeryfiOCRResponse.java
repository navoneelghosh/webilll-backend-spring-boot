package com.example.webill.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VeryfiOCRResponse {

    @JsonProperty(value = "category")
    private String category;
    @JsonProperty(value = "currency_code")
    private String currency_code;
    @JsonProperty(value = "date")
    private String date;
    @JsonProperty(value = "document_reference_number")
    private String document_reference_number;
    @JsonProperty(value = "img_file_name")
    private String img_file_name;
    @JsonProperty(value = "img_thumbnail_url")
    private String img_thumbnail_url;
    @JsonProperty(value = "img_url")
    private String img_url;
    @JsonProperty(value = "invoice_number")
    private String invoice_number;
    @JsonProperty(value = "total")
    private double total;
    @JsonProperty(value = "ocr_text")
    private String ocr_text;
    @JsonProperty(value = "line_items")
    private List<LineItems> line_items;
    @JsonProperty(value = "vendor")
    private Vendor vendor;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocument_reference_number() {
        return document_reference_number;
    }

    public void setDocument_reference_number(String document_reference_number) {
        this.document_reference_number = document_reference_number;
    }

    public String getImg_file_name() {
        return img_file_name;
    }

    public void setImg_file_name(String img_file_name) {
        this.img_file_name = img_file_name;
    }

    public String getImg_thumbnail_url() {
        return img_thumbnail_url;
    }

    public void setImg_thumbnail_url(String img_thumbnail_url) {
        this.img_thumbnail_url = img_thumbnail_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOcr_text() {
        return ocr_text;
    }

    public void setOcr_text(String ocr_text) {
        this.ocr_text = ocr_text;
    }

    public List<LineItems> getLine_items() {
        return line_items;
    }

    public void setLine_items(List<LineItems> line_items) {
        this.line_items = line_items;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
