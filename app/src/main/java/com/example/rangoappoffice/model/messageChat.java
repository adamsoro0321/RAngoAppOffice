package com.example.rangoappoffice.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class messageChat {

     private String msg ,userUID ;
    private Date dateCreated;

    public messageChat() {
    }

    public messageChat(String msg, String userUID) {
        this.msg = msg;
        this.userUID = userUID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    @ServerTimestamp
    public Date getDateCreated() { return dateCreated; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
}
