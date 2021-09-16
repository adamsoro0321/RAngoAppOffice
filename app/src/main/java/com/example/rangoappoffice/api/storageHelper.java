package com.example.rangoappoffice.api;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class storageHelper {



    public static FirebaseStorage getStorageReference(){
        return FirebaseStorage.getInstance()  ;
    }

    public static StorageReference getStorageRef( String idHouse){
       return storageHelper.getStorageReference()
               .getReference().child("house").child(idHouse).child(idHouse) ;
    }
    public static StorageReference getImageRoom(String idHouse ,int i){
        return  storageHelper.getStorageRef(idHouse).child(idHouse+i);
    }


    public  static StorageReference updateProfil(String user){
        return storageHelper.getStorageReference().getReference().child("profiles").child(user) ;
    }
    public static StorageReference getVenteRef( String idHouse){
        return storageHelper.getStorageReference()
                .getReference().child("house").child("vente").child(idHouse).child(idHouse) ;
    }
    public static StorageReference getVenteImageRoom(String idHouse ,int i){
        return  storageHelper.getStorageReference()
                .getReference().child("house").child("vente").child(idHouse).child(idHouse+i);
    }


}
