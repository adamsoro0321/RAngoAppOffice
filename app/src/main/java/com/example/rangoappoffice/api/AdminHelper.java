package com.example.rangoappoffice.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHelper {
    private static final String COLLECTION_NAME = "admin" ;

    public static CollectionReference getUserCollection(){
        return FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME) ;
    }

    public static DocumentReference getVisiteCondition(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("conditions")
                .document("visiteContidion") ;
    }

    public static DocumentReference getLouerCondition(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("conditions")
                .document("louerCondition") ;
    }
    public static CollectionReference  getOperationCollectionVisit(){
        return AdminHelper.getUserCollection()
                .document("operation")
                .collection("visit");
    }

    public static CollectionReference  getOperationCollectionLouer(){
        return  AdminHelper.getUserCollection()
                .document("operation")
                .collection("louer");
    }

    public static DocumentReference getMainActivityRsc(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("call")
                .document("mainActivity") ;
    }
    public static DocumentReference getTerrainVent(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("haveTerrainVente")
                .document("terrain") ;
    }
    public static DocumentReference getMaisonVent(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("haveMaisonVente")
                .document("maison") ;
    }
    public static DocumentReference getNumber(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("AdminContact")
                .document("numbers") ;
    }


    public static CollectionReference getLoyer(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("loyer");
    }
    public static CollectionReference getCategoriMaison(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("categorimaison");
    }
   //Business caller

    public static DocumentReference getBusinessCaller(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("call")
                .document("businessCaller") ;
    }
    public static DocumentReference guideCaller(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("call")
                .document("guideCaller") ;
    }
    public static DocumentReference getDemarcheur(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("call")
                .document("demarCaller") ;
    }
    //before chat witch user assistant
    public static DocumentReference assistant(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("assistant")
                .document("index") ;
    }

    public static CollectionReference SignalSort(){
        return   AdminHelper.getUserCollection()
                .document("signal")
                .collection("locataireSrot");
    }
    public static CollectionReference saveGuideNote(){
        return   AdminHelper.getUserCollection()
                .document("GuideNote")
                .collection("note");
    }




    //collection of comment sender
    public static CollectionReference commentRoom(){
        return   AdminHelper.getUserCollection()
                .document("listenUser")
                .collection("commentaire");
    }

    //collection user chat
    public static CollectionReference chatRoom(String userUId){
        return   AdminHelper.getUserCollection()
                .document("chatRoom")
                .collection(userUId);
    }

    public static CollectionReference ListenUser(){
        return   AdminHelper.getUserCollection()
                .document("listenUser")
                .collection("Listen");
    }

    //ressource for final louer

    public static DocumentReference finalLouerString(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("call")
                .document("PayGuide") ;
    }

    //

    public static DocumentReference getPayAndRulString(){
        return   AdminHelper.getUserCollection()
                .document("ressource")
                .collection("call")
                .document("PAYRULDOC") ;
    }
}
