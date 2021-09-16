package com.example.rangoappoffice.api;

import com.example.rangoappoffice.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserHelper {
    private static final String COLLECTION_NAME = "Users" ;
    private static final String DOCUMENT_COUNT = "compte" ;
  //get database root for user
    public static CollectionReference getUserCollection(){
        return FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME) ;
    }

    public static CollectionReference getCompte(String userUID){
        return  UserHelper.getUserCollection()
                .document(DOCUMENT_COUNT).collection(userUID);
    }

    public  static DocumentReference getUserDoc(String userUID){
      return   UserHelper.getCompte(userUID).document("userMain")
              .collection("user").document(userUID);
    }

    public  static  CollectionReference getHouseLouerCollection(String userUID){
       return UserHelper.getCompte(userUID).document("maisonLouer")
                .collection("maisons") ;
    }

    public static CollectionReference getGuideContactCollection(String userUID){
       return UserHelper.getCompte(userUID).document("guideContact")
                .collection("guide") ;
    }

    public  static  CollectionReference getHouseGiveCollection(String userUID){
        return UserHelper.getCompte(userUID).document("HouseGive")
                .collection("house") ;
    }
  public static String getCurrentUser(){
        return FirebaseAuth.getInstance().getUid() ;
    }

     public static CollectionReference offcialGuide(){
        return   UserHelper.getUserCollection().document("businessUser")
                .collection("guide") ;
     }
    public static CollectionReference IamBusinessGuide( String uid){
        return UserHelper.getCompte(uid)
                .document("iAmbusinessUser")
                .collection("guide") ;
    }
    public static CollectionReference waitingGuide(String uid){
        return  UserHelper.getCompte(uid).document("waitingGuide").collection("listGuide") ;
    }
    public static CollectionReference waitingGuideProposition(String LOcataireuid,String OffreId){
        return  UserHelper.getCompte(LOcataireuid).document("waitingGuide")
                .collection("listGuide").document(OffreId).collection("offreList");
    }

    public static CollectionReference IamBusinessDemar(String uid){
        return UserHelper.getCompte(uid)
                .document("iAmbusinessUser")
                .collection("demarche") ;
    }

    public  static DocumentReference getBusinessUser(){
        return  UserHelper.getUserCollection().document("businessUser") ;
    }
    public  static CollectionReference getBusinessUserDemarch(){
        return  UserHelper.getBusinessUser().collection("demarche") ;
    }
    public static CollectionReference getHouseVisit(){
        return UserHelper.getCompte(MainActivity.currentUser)
                .document("houseVisit").collection("house") ;
    }

    public static CollectionReference getGensGuide(String guideUId){
        return UserHelper.getCompte(guideUId)
                .document("gensGuide")
                .collection("listGens") ;
    }
    public static CollectionReference ChooseListener(String guideUId){
        return UserHelper.getCompte(guideUId)
                .document("iAmbusinessUser")
                .collection("chooseListener");
    }
    public static CollectionReference chatWithGuide(String uidLocataire ,String uidGuide){
        return UserHelper.getCompte(uidLocataire)
                .document("chatWithGuide")
                .collection(uidGuide) ;
    }
    //Listen collection
    public static  CollectionReference guideListenHere(String UID){
       return     UserHelper.getCompte(UID).document("guide")
               .collection("listLocataire");
    }
    public  static  CollectionReference locataireListenHere(String UId){
        return UserHelper.getCompte(UId).document("locataire")
                .collection("ListeGuide") ;
    }

    //save locataire in owener house

    public  static  CollectionReference MyLocataire(String UId){
        return UserHelper.getCompte(UId).document("Mylocataire")
                .collection("Locataires") ;
    }
    public  static  CollectionReference AlertFromLocataitre(String UId){
        return UserHelper.getCompte(UId).document("Mylocataire")
                .collection("Louer") ;
    }
    public  static  CollectionReference MyOwner(String UId){
        return UserHelper.getCompte(UId).document("MyOwner")
                .collection("Owners") ;
    }
    public  static  CollectionReference MesMaison(){
        return UserHelper.getCompte(MainActivity.currentUser).document("MesMaisons")
                .collection("maisons") ;
    }


}
