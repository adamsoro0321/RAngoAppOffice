package com.example.rangoappoffice.model;

public class CurrentUsertStatu {
    Boolean Guide=false ,Demarcheur =false,Propritaire =false  ,GuideVailable=false ;

    public CurrentUsertStatu() {
    }


    public CurrentUsertStatu(Boolean isGuide, Boolean isDemarcheur, Boolean isPropritaire, Boolean isGuideVailable) {
        this.Guide = isGuide;
        this.Demarcheur = isDemarcheur;
        this.Propritaire = isPropritaire;
        this.GuideVailable = isGuideVailable;
    }

    public Boolean getGuide() {
        return Guide;
    }

    public void setGuide(Boolean guide) {
        Guide = guide;
    }

    public Boolean getDemarcheur() {
        return Demarcheur;
    }

    public void setDemarcheur(Boolean demarcheur) {
        Demarcheur = demarcheur;
    }

    public Boolean getPropritaire() {
        return Propritaire;
    }

    public void setPropritaire(Boolean propritaire) {
        Propritaire = propritaire;
    }

    public Boolean getGuideVailable() {
        return GuideVailable;
    }

    public void setGuideVailable(Boolean guideVailable) {
        GuideVailable = guideVailable;
    }
}
