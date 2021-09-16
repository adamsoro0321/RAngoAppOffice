package com.example.rangoappoffice.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HouseHelpStore {
    private static final String COLLECTION_NAME ="houses" ;
  private  final FirebaseFirestore db ;

    public HouseHelpStore(FirebaseFirestore db) {
        this.db = db;
    }


    //create collection  & document
    public static CollectionReference getCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME) ;
    }
      public static CollectionReference getHouseRoom(String idHouse){
        return   HouseHelpStore.getCollection().document("allHouse")
                .collection(idHouse).document("houseRoom")
                .collection("house") ;
      }
    public static CollectionReference getImageRoom(String idHouse){
        return   HouseHelpStore.getCollection().document("allHouse")
                .collection(idHouse).document("imageRoom")
                .collection("image") ;
    }
    public static CollectionReference getHouseGuide(String idHouse){
        return   HouseHelpStore.getCollection().document("allHouse")
                .collection(idHouse).document("HouseGuides")
                .collection("Guides") ;
    }

    public static DocumentReference createHouse(String IdHouse){
        return
                HouseHelpStore.getHouseRoom(IdHouse).document(IdHouse);
    }
  public static DocumentReference getHouse(String IdHouse){

      return HouseHelpStore.getCollection()
                .document("dispo").collection("house").document(IdHouse) ;

    }


    public static Query getAllHouse(){
       return getCollection().document("dispo").collection("house") ;
    }




///here we save house rule data and pay data
    public static  CollectionReference getHouseRules(String IdHouse){
        return HouseHelpStore.getCollection().document("allHouse")
                .collection(IdHouse).document("ConditionData").collection("data") ;
    }
    public static CollectionReference getHousePay(String IdHouse){
        return HouseHelpStore.getCollection().document("allHouse")
                .collection(IdHouse).document("PayData").collection("data") ;
    }
    public static CollectionReference getDemarchSetting(String IdHouse){
        return HouseHelpStore.getCollection().document("allHouse")
                .collection(IdHouse).document("demarcheur").collection("data") ;
    }
    public static CollectionReference getBusinessHouseGuide(String IdHouse){
        return HouseHelpStore.getCollection().document("allHouse")
                .collection(IdHouse).document("BusinessHouse").collection("BusinessHouseGuide") ;
    }
    public static CollectionReference getBusinessHouseDemarcheur(String IdHouse){
        return HouseHelpStore.getCollection().document("allHouse")
                .collection(IdHouse).document("BusinessHouse").collection("BusinessHouseDemarcheur") ;
    }
}
