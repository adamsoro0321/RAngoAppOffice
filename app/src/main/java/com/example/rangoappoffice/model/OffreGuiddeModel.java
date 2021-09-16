package com.example.rangoappoffice.model;

import com.google.firebase.firestore.FieldValue;

import java.util.Date;
public class OffreGuiddeModel {
    String img ,tarif ,condition ,uid;
   private Date date ;

    public OffreGuiddeModel() {
    }


    public OffreGuiddeModel( String tarif, String condition, String uid) {

        this.tarif = tarif;
        this.condition = condition;
        this.uid = uid;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public FieldValue getDate() {
        return FieldValue.serverTimestamp() ;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
