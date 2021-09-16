package com.example.rangoappoffice.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity {



  protected FirebaseAuth firebaseAuth(){
    return FirebaseAuth.getInstance();
      }

    protected FirebaseUser getCurrentUser(){
     return this.firebaseAuth().getCurrentUser() ;
 }


}
