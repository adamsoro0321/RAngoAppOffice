package com.example.rangoappoffice.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;


public class SystemServiceNetWork {
    Context context;
 public static  void NetWorkStatut(Context context ,View v){
     ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
     NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

     if(networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
         //make you want with connexion
     }else{
         snackbarShow(v) ;
     }

 }
 
 public static void snackbarShow(View v){
     Snackbar snackbar = Snackbar.make(v ,"pas d'internet" ,Snackbar.LENGTH_INDEFINITE)
                 .setAction("reessayer", v1 -> {

                 }) ;
     snackbar.show();
 }

}
