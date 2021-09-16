package com.example.rangoappoffice.api;

import android.content.Context;
import android.content.Intent;

import com.example.rangoappoffice.ui.login.LoginActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class CommonMethode {

    public  static  void  gotoLogin(Context context){
        context.startActivity(new Intent(context , LoginActivity.class));
    }

    public static String numberFormat(double d){
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRENCH) ;
        return numberFormat.format(d) ;
    }
    public static  String ConvertAnFormat(String s){
        s=s.replaceAll("\\s","");
        double d = Double.parseDouble(s);
        return CommonMethode.numberFormat(d) ;
    }
}
