package com.example.webill.models;

import org.springframework.stereotype.Component;


@Component
public class Constants {

    private int SUCCESS = 200;
    private int FRIENDSHIP_EXISTS = 409;
    private int USERNOTFOUND = 404;
    private int BADREQUEST = 400;

    public Constants() {
    }

    public int getSUCCESS() {
        return SUCCESS;
    }

    public int getFRIENDSHIP_EXISTS() {
        return FRIENDSHIP_EXISTS;
    }

    public int getUSERNOTFOUND() {
        return USERNOTFOUND;
    }

    public int getBADREQUEST() {
        return BADREQUEST;
    }
}
