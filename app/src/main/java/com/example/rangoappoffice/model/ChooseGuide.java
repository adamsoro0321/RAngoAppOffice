package com.example.rangoappoffice.model;

import com.google.firebase.firestore.FieldValue;

import java.util.Date;

public class ChooseGuide {
    String serviceType,locataireUid ;
    Date date ;

    public ChooseGuide() {
    }

    public ChooseGuide(String serviceType, String locataireUid) {
        this.serviceType = serviceType;
        this.locataireUid = locataireUid;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getLocataireUid() {
        return locataireUid;
    }

    public void setLocataireUid(String locataireUid) {
        this.locataireUid = locataireUid;
    }

    public FieldValue getDate() {
        return FieldValue.serverTimestamp() ;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
