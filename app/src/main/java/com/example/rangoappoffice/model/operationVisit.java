package com.example.rangoappoffice.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class operationVisit {
   private String locataire ;
   private  String guide,succedOperation ;
   private Date dateInit;
    private Date dateGuideCome;
    float rateGuide ;

    public operationVisit() {
    }

    public operationVisit(String locataire) {
        this.locataire = locataire;
    }


    public String getLocataire() {
        return locataire;
    }

    public void setLocataire(String locataire) {
        this.locataire = locataire;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public Date getDateGuideCome() {
        return dateGuideCome;
    }

    public void setDateGuideCome(Date dateGuideCome) {
        this.dateGuideCome = dateGuideCome;
    }

    @ServerTimestamp public Date getDateInit() { return dateInit; }
    public void setDateCreated(Date dateCreated) { this.dateInit = dateCreated; }

    public float getRateGuide() {
        return rateGuide;
    }

    public void setRateGuide(float rateGuide) {
        this.rateGuide = rateGuide;
    }

    public String getSuccedOperation() {
        return succedOperation;
    }

    public void setSuccedOperation(String succedOperation) {
        this.succedOperation = succedOperation;
    }
}
