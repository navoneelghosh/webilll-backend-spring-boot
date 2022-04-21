package com.example.webill.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "friends_prod")
public class Friend {
    private String username1;
    private String username2;

    @Id
    private String friendshipKey;

    public Friend() {
    }

    public Friend(String username1, String username2, String friendshipKey) {
        this.username1 = username1;
        this.username2 = username2;
        this.friendshipKey = friendshipKey;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getFriendshipKey() {
        return friendshipKey;
    }

    public void setFriendshipKey(String friendshipKey) {
        this.friendshipKey = friendshipKey;
    }
}
