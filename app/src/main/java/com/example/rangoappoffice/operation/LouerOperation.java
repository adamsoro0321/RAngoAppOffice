package com.example.rangoappoffice.operation;

import com.example.rangoappoffice.api.AdminHelper;
import com.google.firebase.firestore.CollectionReference;

public class LouerOperation {

   public static CollectionReference initLouer(){
        return   AdminHelper.getOperationCollectionLouer() ;
        }



        public static class LouerOperationModel{
       String user ,house ;


            public LouerOperationModel() {
            }

            public LouerOperationModel(String user, String house) {
                this.user = user;
                this.house = house;


            }

            public String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }

            public String getHouse() {
                return house;
            }

            public void setHouse(String house) {
                this.house = house;
            }

        }
}
