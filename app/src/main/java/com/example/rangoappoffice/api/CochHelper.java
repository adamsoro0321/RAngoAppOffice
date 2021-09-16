package com.example.rangoappoffice.api;

import com.example.rangoappoffice.model.cochChaterMessage;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CochHelper {
    private static final String COLLECTION_NAME ="CochSearch" ;
    private  final FirebaseFirestore db ;

    public CochHelper(FirebaseFirestore db) {
        this.db = db;
    }


    //create collection  & document
    public static CollectionReference getCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME) ;
    }

    public static Task<Void> createchat(String receiverId, String SenderRoom , cochChaterMessage msg){
        return
                CochHelper.getCollection().document("chat").collection(receiverId).document(SenderRoom).collection("message").document().set(msg) ;
    }




}
