package com.example.rangoappoffice.model;

public class UserStatu {
    String uid ,statu ;
    public UserStatu() {
    }


    public UserStatu(String uid, String statu) {
        this.uid = uid;
        this.statu = statu;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }
}
