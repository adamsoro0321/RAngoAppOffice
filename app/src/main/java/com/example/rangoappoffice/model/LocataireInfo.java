package com.example.rangoappoffice.model;

import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.Date;

public class LocataireInfo implements Serializable {
    String locataireUId,house ,operationId;
    Date date ;

    public LocataireInfo() {
    }

    public LocataireInfo(String locataireUId, String house) {
        this.locataireUId = locataireUId;
        this.house = house;
    }

    public LocataireInfo(String locataireUId, String house, String operationId) {
        this.locataireUId = locataireUId;
        this.house = house;
        this.operationId = operationId;
    }

    public String getLocataireUId() {
        return locataireUId;
    }

    public void setLocataireUId(String locataireUId) {
        this.locataireUId = locataireUId;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public FieldValue getDate() {
        return FieldValue.serverTimestamp() ;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}
