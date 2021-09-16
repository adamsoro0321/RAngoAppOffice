package com.example.rangoappoffice.model;

import com.google.firebase.firestore.FieldValue;

public class Alert{
    private  String uid ;
    private FieldValue value ;
    public Alert(){ }
    public Alert(String uid) {
        this.uid = uid;
        this.value = FieldValue.serverTimestamp() ;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public FieldValue getValue() {
        return value;
    }

    public void setValue(FieldValue value) {
        this.value = value;
    }
}
