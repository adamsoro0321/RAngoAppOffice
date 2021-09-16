package com.example.rangoappoffice.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    String uid , name ,phone , town, imageProfil ,fcmToken;
    private Date dateCreated;


    public User() {
    }
    public User(String uid) {
        this.uid = uid ;
    }

    public User(String uid ,String phone) {
        this.uid = uid ;
        this.phone = phone ;
    }
    public User(String uid, String name, String phone, String imageProfil) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.imageProfil = imageProfil;
    }
    public User(String uid, String name, String phone, String imageProfil ,String town,String fcmToken) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.imageProfil = imageProfil;
        this.fcmToken = fcmToken;
        this.town =town ;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageProfil() {
        return imageProfil;
    }

    public void setImageProfil(String imageProfil) {
        this.imageProfil = imageProfil;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @ServerTimestamp
    public Date getDateCreated() { return dateCreated; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated ;}
}
