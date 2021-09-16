package com.example.rangoappoffice.model;

public class CategoriHome {
   private String categori ;

    public CategoriHome() {
    }

    public CategoriHome( String categori) {

        this.categori =categori;

    }


    public String getcategori() {
        return categori;
    }

    public void setcategori(String description) {
        this.categori = description;
    }

}
