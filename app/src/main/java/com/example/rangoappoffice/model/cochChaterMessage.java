package com.example.rangoappoffice.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class cochChaterMessage {

    private String message;
    private Date dateCreated;
    private User userSender;
    private String urlImage;

    public cochChaterMessage () { }

    public cochChaterMessage (String message, User userSender) {
        this.message = message;
        this.userSender = userSender;
    }
    public cochChaterMessage (String message) {
        this.message = message;

    }
    public cochChaterMessage (String message, String urlImage, User userSender) {
        this.message = message;
        this.urlImage = urlImage;
        this.userSender = userSender;
    }

    // --- GETTERS ---
    public String getMessage() { return message; }
    @ServerTimestamp public Date getDateCreated() { return dateCreated; }
    public User getUserSender() { return userSender; }
    public String getUrlImage() { return urlImage; }

    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(User userSender) { this.userSender = userSender; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
}