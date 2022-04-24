package com.example.webill.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_stripe_prod")
public class UserStripeAccount {

    @Id
    private String username;

    private String account_id;
    private String customer_id;

    public UserStripeAccount() {
    }

    public UserStripeAccount(String username, String account_id, String customer_id) {
        this.username = username;
        this.account_id = account_id;
        this.customer_id = customer_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
