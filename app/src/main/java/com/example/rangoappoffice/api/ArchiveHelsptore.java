package com.example.rangoappoffice.api;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ArchiveHelsptore {
    public static CollectionReference getArchive(){
     return   FirebaseFirestore.getInstance().collection("Archive") ;
    }
    public  static  CollectionReference getAchiveHouse(){
      return   ArchiveHelsptore.getArchive().document("houseArchive")
              .collection("archive") ;
    }
}
