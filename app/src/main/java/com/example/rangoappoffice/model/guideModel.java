package com.example.rangoappoffice.model;

import com.google.firebase.firestore.FieldValue;

import java.util.Date;

public class guideModel {

   private Boolean Guide ,Vailable ;
   private String guideUId,img ,name;
   private java.util.Date timestamp ;

    public guideModel() {
    }
  public guideModel(Boolean isGuide, Boolean isVailable , String uidUser) {
        this.Guide = isGuide;
        this.Vailable = isVailable;
        this.guideUId = uidUser;
    }

    public guideModel(Boolean isGuide, Boolean isVailable, String guideUId, String operationId) {
        this.Guide = isGuide;
        this.Vailable = isVailable;
        this.guideUId = guideUId;
         }

    public Boolean getGuide() {
        return Guide;
    }

    public void setGuide(Boolean guide) {
        Guide = guide;
    }

    public Boolean getVailable() {
        return Vailable;
    }

    public void setVailable(Boolean vailable) {
        Vailable = vailable;
    }

    public String getGuideUId() {
        return guideUId;
    }

    public void setGuideUId(String guideUId) {
        this.guideUId = guideUId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public FieldValue getTimestamp() {
        return FieldValue.serverTimestamp() ;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
