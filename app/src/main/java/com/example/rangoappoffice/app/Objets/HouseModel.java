package com.example.rangoappoffice.app.Objets;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class HouseModel  implements Serializable {
    private  String adresse,categori , description,image,loyer,idHouse ,idLocataire,yetAdmin ,lieuReference ,ownUID;
    private Date dateCreated;

    public HouseModel() { }

    public HouseModel(String adresse, String categori, String description, String image, String loyer, String idHouse) {
        this.adresse = adresse;
        this.categori = categori;
        this.description = description;
        this.image = image;
        this.loyer = loyer;
        this.idHouse = idHouse;
    }

    public HouseModel(String idHouse, String image, String adresse, String description, String loyer) {
        this.idHouse = idHouse;
        this.image = image;
        this.adresse = adresse;
        this.description = description;
        this.loyer = loyer;
    }

    public HouseModel(String adresse, String categori, String description, String image, String loyer, String idHouse, String idLocataire, String yetAdmin) {
        this.adresse = adresse;
        this.categori = categori;
        this.description = description;
        this.image = image;
        this.loyer = loyer;
        this.idHouse = idHouse;
        this.idLocataire = idLocataire;
        this.yetAdmin = yetAdmin;
    }

    public String getLieuReference() {
        return lieuReference;
    }

    public void setLieuReference(String lieu_reference) {
        this.lieuReference = lieu_reference;
    }

    public String getIdLocataire() {
        return idLocataire;
    }

    public void setIdLocataire(String idLocataire) {
        this.idLocataire = idLocataire;
    }

    public String getOwnUID() {
        return ownUID;
    }

    public void setOwnUID(String ownUID) {
        this.ownUID = ownUID;
    }

    public String getYetAdmin() {
        return yetAdmin;
    }

    public void setYetAdmin(String yetAdmin) {
        this.yetAdmin = yetAdmin;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLoyer() {
        return loyer;
    }

    public void setLoyer(String loyer) {
        this.loyer = loyer;
    }

    public String getIdHouse() {
        return idHouse;
    }

    public void setIdHouse(String idHouse) {
        this.idHouse = idHouse;
    }

    public String getCategori() {
        return categori;
    }

    public void setCategori(String categori) {
        this.categori = categori;
    }
    @ServerTimestamp
    public Date getDateCreated() { return dateCreated; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated ;}
}
