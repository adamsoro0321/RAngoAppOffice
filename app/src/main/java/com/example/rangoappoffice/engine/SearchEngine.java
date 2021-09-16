package com.example.rangoappoffice.engine;

import com.example.rangoappoffice.api.HouseHelpStore;
import com.google.firebase.firestore.Query;

public class SearchEngine {
    public static Query searchCategori(String s){
        return  HouseHelpStore.getAllHouse() ;
    }
    public static Query searchPise(String s){
        return  HouseHelpStore.getAllHouse().whereEqualTo("loyer" ,s) ;
    }
    public static Query searchLocate(String s){
        return  HouseHelpStore.getAllHouse().whereEqualTo("lieuReference" ,s) ;
    }
}
