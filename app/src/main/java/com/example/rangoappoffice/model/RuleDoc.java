package com.example.rangoappoffice.model;

public class RuleDoc {
    String rul ,caution ,uidOwner;
    Boolean isChater ;

    public RuleDoc() {
    }

    public RuleDoc(String rul, String caution, Boolean isChater,String UidOwner) {
        this.rul = rul;
        this.caution = caution;
        this.isChater = isChater;
        this.uidOwner =UidOwner ;
    }

    public String getUidOwner() {
        return uidOwner;
    }

    public void setUidOwner(String uidOwner) {
        this.uidOwner = uidOwner;
    }

    public String getRul() {
        return rul;
    }

    public void setRul(String rul) {
        this.rul = rul;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public Boolean getChater() {
        return isChater;
    }

    public void setChater(Boolean chater) {
        isChater = chater;
    }
}
