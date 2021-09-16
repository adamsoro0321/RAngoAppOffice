package com.example.rangoappoffice.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rangoappoffice.model.CurrentUsertStatu;

public class DataForDesign {
    public  static final  String DASHBORD_DESIGN="DASHBORD_DESIGN" ;
    public static SharedPreferences getDashbordPrefernce(Context context){
        return context.getSharedPreferences(DASHBORD_DESIGN ,Context.MODE_PRIVATE) ;
    }
public  static void setCurrentUserStatu(Context context, CurrentUsertStatu statu){
        SharedPreferences preferences =DataForDesign.getDashbordPrefernce(context) ;
      SharedPreferences.Editor editor =preferences.edit()  ;
      editor.putBoolean("isGuide",statu.getGuide()) ;
      editor.putBoolean("isDemarcheur",statu.getDemarcheur()) ;
      editor.putBoolean("isPropritaire",statu.getPropritaire());
      editor.putBoolean("isGuideVailable" ,statu.getGuideVailable()) ;
      editor.apply();
}

public  static  CurrentUsertStatu getUserStatuFromPreference(Context context){
        CurrentUsertStatu usertStatu = new CurrentUsertStatu();
        SharedPreferences preferences = DataForDesign.getDashbordPrefernce(context) ;
        usertStatu.setDemarcheur(preferences.getBoolean("isDemarcheur",false));
        usertStatu.setGuide(preferences.getBoolean("isGuide",false));
        usertStatu.setPropritaire(preferences.getBoolean("isPropritaire",false));
        usertStatu.setGuideVailable(preferences.getBoolean("isGuideVailable" ,false));
        return usertStatu ;
}

public static  Boolean getGuideVailable(Context context ){
    SharedPreferences preferences =    DataForDesign.getDashbordPrefernce(context) ;
   return  preferences.getBoolean("isGuideVailable",false) ;
    }
   public static void setGuideVailable(Context context,Boolean guideVailable){
       SharedPreferences preferences =    DataForDesign.getDashbordPrefernce(context) ;
       SharedPreferences.Editor editor =preferences.edit() ;
       editor.putBoolean("isGuideVailable", guideVailable) ;
       editor.apply()  ;
   }
}
