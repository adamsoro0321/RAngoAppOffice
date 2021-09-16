package com.example.rangoappoffice.api;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class UserLineStat {
     final  FirebaseDatabase database = FirebaseDatabase.getInstance() ;

     final FirebaseAuth auth = FirebaseAuth.getInstance() ;
    final DatabaseReference  connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
    public  void  chekIsLine(){

        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);

                if (connected){

                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

    public void UserOline(){
           DatabaseReference  myconnectonRef = database.getReference("Users/"+auth.getUid()+"/connections") ;
           DatabaseReference lastOnLine = database.getReference("Users/"+auth.getUid()+"/lastOnline");

           DatabaseReference connectRef = database.getReference(".info/connected");

           connectRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   boolean connected = snapshot.getValue(Boolean.class);
                   if (connected){
                       DatabaseReference con = myconnectonRef.push() ;
                       //when this device dsiconnect ,remove it
                       con.onDisconnect().removeValue() ;

                       //when I disconnect , update the last time I was see

                       lastOnLine.onDisconnect().setValue(ServerValue.TIMESTAMP);

                       //add this device to my connectionlist
                       //this value could contain info about the deviceor the timestam

                       con.setValue(Boolean.TRUE);
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           }) ;
    }
}
