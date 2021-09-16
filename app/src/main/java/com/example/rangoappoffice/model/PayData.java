package com.example.rangoappoffice.model;

public class PayData {
    String somme  ,numero ;

    public PayData() {
    }

    public PayData(String somme, String numero) {
        this.somme = somme;
        this.numero = numero;
    }

    public String getSomme() {
        return somme;
    }

    public void setSomme(String somme) {
        this.somme = somme;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
