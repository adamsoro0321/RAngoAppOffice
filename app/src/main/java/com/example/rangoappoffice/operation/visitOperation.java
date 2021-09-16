package com.example.rangoappoffice.operation;

import com.example.rangoappoffice.api.AdminHelper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

public class visitOperation {


    public static CollectionReference initOperation(){

        return  AdminHelper.getOperationCollectionVisit() ;

      }

    public static DocumentReference getGuideOperation(String documentId){
      return visitOperation.initOperation().document(documentId);
      }

}
