package com.example.rangoappoffice.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.activity.GuideChatActivity;
import com.example.rangoappoffice.activity.MainActivity;
import com.example.rangoappoffice.api.NotificationHelper;
import com.example.rangoappoffice.api.UserHelper;

public class GuideChooseService extends Service {
    private  static final String CHOOSE_CHANNEL_ID="choose_channel_id" ;
    private  static  final int CHOOSE_NOTIFIED_ID=222 ;
    public GuideChooseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotifChoose();
        }
        return START_STICKY;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void LocataireChooseMe(){
        UserHelper.ChooseListener(MainActivity.currentUser).addSnapshotListener((value, error) ->{
            Toast.makeText(getApplicationContext() ,"you set Up me" ,Toast.LENGTH_LONG).show();
        } ) ;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void NotifChoose(){
         Intent intent = new Intent(this, GuideChatActivity.class);
         PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = NotificationHelper.GuideIsChooseNotification(this ,CHOOSE_CHANNEL_ID);
        startForeground(CHOOSE_NOTIFIED_ID ,builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.guide_chosi);
            String description = getString(R.string.alert_offre_accept);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHOOSE_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


    }

}