package com.example.rangoappoffice.api;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class firestorageHelper {

    public static StorageReference getRefStorage(String IdHouse ,String uuid){
        return FirebaseStorage.getInstance().getReference("ImageForOnHouse").child(IdHouse).child(uuid);
    }
}
