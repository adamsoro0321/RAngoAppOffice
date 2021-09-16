package com.example.rangoappoffice.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PublicRegisterHelper {
    private static final  String COLLECTION_NAME="PUBLIC_REGISTER";
    private static final  String DOC_NAME="register" ;
    private static final  String OFFRE_LISTE="ListOffre";
    public static CollectionReference getPublicRegister(){
        return FirebaseFirestore.getInstance()
                .collection(COLLECTION_NAME).document(DOC_NAME).collection(OFFRE_LISTE) ;
    }
}
