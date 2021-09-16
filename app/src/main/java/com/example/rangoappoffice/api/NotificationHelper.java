package com.example.rangoappoffice.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;

import com.example.rangoappoffice.R;

public class NotificationHelper {


    public  static NotificationCompat.Builder LookForGuideNotification(Context context , String NoTIF_CHANEL_GUIDE){
      String s = context.getString(R.string.notif_guide_groupe) ;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources() ,R.drawable.icon_rango_min);
        return new NotificationCompat.Builder(context ,NoTIF_CHANEL_GUIDE)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Besoin de Guide")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(bitmap)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setVibrate(new long[]{4000 ,100 ,100 ,100})
                .setGroup(s)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));
    }


    public static NotificationCompat.Builder GuideIsChooseNotification(Context context ,String CHOOSE_CHANNEL_ID){
        String s = context.getString(R.string.notif_guide_groupe) ;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources() ,R.drawable.icon_rango_min);
        return new NotificationCompat.Builder(context, CHOOSE_CHANNEL_ID)
                .setLargeIcon(bitmap)
                .setContentTitle("Guide Service")
                .setContentText("Votre offre est chosi")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setGroup(s)
                .setAutoCancel(true);
    }
}
