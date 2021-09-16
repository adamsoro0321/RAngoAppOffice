package com.example.rangoappoffice.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.activity.DistactActivity;
import com.example.rangoappoffice.activity.GuideChatActivity;
import com.example.rangoappoffice.activity.dashboardActivity;
import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.api.NotificationHelper;
import com.example.rangoappoffice.api.PublicRegisterHelper;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.model.LocataireInfo;
import com.google.firebase.firestore.DocumentChange;

public class GuideService extends Service {

    private static final String CHANNEL_ID = "FOR_G";
    private static  final String NoTIF_CHANEL_GUIDE ="guide ";
    private final int NOTIFICTION_ID =200 ;

    public static final int CONSTANT_REQUEST =0012 ;
   public GuideService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext() ,"start service" ,Toast.LENGTH_LONG).show();
       locataireListListener() ;
        forGroung();
        return START_STICKY ;
  }
 @RequiresApi(api = Build.VERSION_CODES.O)
 private void forGroung(){
        String name =getString(R.string.activ_guid_service) ;
        String description =getString(R.string.description_for_service) ;
     Intent notificationIntent = new Intent(this, dashboardActivity.class);
     PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
     NotificationCompat.Builder notification =
             new NotificationCompat.Builder(this ,CHANNEL_ID)
                     .setContentTitle(getText(R.string.notification_title))
                     .setContentText("Guide service activer")
                     .setSmallIcon(R.drawable.icon_rango_min)
                     .setContentIntent(pendingIntent);

// Notification ID cannot be 0.
     startForeground(1, notification.build());
     if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
         NotificationChannel chanel = new NotificationChannel(CHANNEL_ID ,name,NotificationManager.IMPORTANCE_DEFAULT);
         chanel.setDescription(description);
         NotificationManager manager = getSystemService(NotificationManager.class);
         manager.createNotificationChannel(chanel); }
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void locataireListListener(){
           PublicRegisterHelper.getPublicRegister().addSnapshotListener((value, error) -> {
               for (DocumentChange documentChange : value.getDocumentChanges()){
                   if (documentChange.getType() == DocumentChange.Type.ADDED) {
                       LocataireInfo locataireInfo = documentChange.getDocument().toObject(LocataireInfo.class);
                       getHouseInfo(locataireInfo);
                   }
                    }
               }) ;
   }

   private  void getHouseInfo(LocataireInfo locataireInfo){
        String idHouse =locataireInfo.getHouse();
       HouseHelpStore.getHouseRoom(idHouse).document(idHouse).get().addOnSuccessListener(documentSnapshot -> {
           HouseModel house=documentSnapshot.toObject(HouseModel.class) ;
           String houseInfo = documentSnapshot.get("adresse")+" "+documentSnapshot.get("description") ;
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               sendVisualNotification(locataireInfo ,houseInfo ,CONSTANT_REQUEST,house);
           }
       }) ;
   }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendVisualNotification(LocataireInfo locataireInfo,String houseINfo,int CONSTANT_REQUEST,HouseModel houseModel) {
        Intent i = new Intent(this , GuideChatActivity.class) ;
        i.putExtra("house" ,houseModel) ;
        i.putExtra("locatairInfo" ,locataireInfo);
        i.putExtra("requestGuide" ,CONSTANT_REQUEST) ;
        PendingIntent AcceptedRendingIntent = PendingIntent.getActivity(this ,0 , i ,PendingIntent.FLAG_UPDATE_CURRENT);
        //si lhe guide clik sue button c'est qu'il refuse le service

        Intent it = new Intent(getApplicationContext() , DistactActivity.class) ;
        PendingIntent RefusePendingIntentForAction = PendingIntent.getActivity(this ,0 ,it ,PendingIntent.FLAG_CANCEL_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources() ,R.drawable.icon_rango_min);
        NotificationCompat.Builder notification = NotificationHelper.LookForGuideNotification(this ,NoTIF_CHANEL_GUIDE)
                .setLargeIcon(bitmap)
                .setContentText("Pour: "+houseINfo)
                .setFullScreenIntent(AcceptedRendingIntent,true)
                .addAction( R.drawable.ic_launcher_rango ,"Accepter",AcceptedRendingIntent)
                .addAction(R.drawable.ic_launcher_rango ,"Refuser" ,RefusePendingIntentForAction) ;


     //register the channel with system

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.appel_offre) ;
            NotificationChannel channel = new NotificationChannel(NoTIF_CHANEL_GUIDE, name ,NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(getString(R.string.motiv_guide));
            notificationManager.createNotificationChannel(channel); }
       //show a notification
          notificationManager.notify(NOTIFICTION_ID , notification.build());

    }


}