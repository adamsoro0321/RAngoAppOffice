package com.example.rangoappoffice.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rangoappoffice.model.User;

public class LocalData {
    public  static SharedPreferences getUserPrefence(Context context){
        return context.getSharedPreferences("user" , Context.MODE_PRIVATE) ;
    }
    public static void SaveUserInPrefence(User user,Context context){
        SharedPreferences.Editor editor = LocalData.getUserPrefence(context).edit() ;
        editor.putString("img",user.getImageProfil());
        editor.putString("number",user.getPhone()) ;
        editor.putString("name" ,user.getName()) ;
        editor.putString("town" ,user.getTown()) ;
        editor.putString("uid" ,user.getUid()) ;
        editor.commit() ;

    }
    public static User getUserFromPreference(Context context){
        User user = new User();
        SharedPreferences preferences = LocalData.getUserPrefence(context) ;
        user.setImageProfil(preferences.getString("img",null));
        user.setPhone(preferences.getString("number",null));
        user.setName(preferences.getString("name" ,null));
        user.setTown(preferences.getString("town",null));
        user.setUid(preferences.getString("uid",null));
        return  user ;
    }
}
