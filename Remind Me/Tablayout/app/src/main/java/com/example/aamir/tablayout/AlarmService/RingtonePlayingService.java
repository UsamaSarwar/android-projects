package com.example.aamir.tablayout.AlarmService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.widget.RemoteViews;

import com.example.aamir.tablayout.Database.DatabaseHandler;
import com.example.aamir.tablayout.R;

import static android.R.attr.id;


/**
 * Created by ayaan on 30/03/2017.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer mediaPlayer;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notificationId;
    private RemoteViews remoteViews;
    private Context context;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String state = intent.getExtras().getString("Extra");
        int id = intent.getExtras().getInt("ID");
        assert state != null;
        switch (state) {
            case "alarm on":{

                DatabaseHandler databaseHandler = new DatabaseHandler(this);
                int icon = databaseHandler.getIcon(id);
                String message = databaseHandler.getMessage(id);
                String subject = databaseHandler.getSubject(id);
                databaseHandler.close();
                notification(subject,message,icon);
                ringTone();


                break;
            }


            case "alarm off":
                break;
            default:
                break;
        }



        return START_NOT_STICKY;
    }

    private void notification(String subject,String message,int icon) {

        this.context = this;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        notificationId = id;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(subject)
                .setContentText(message)
                .setTicker(subject);

        long[] pattern = {0, 300, 0};
        builder.setLights(Color.BLUE, 700, 1500);
        builder.setVibrate(pattern);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        notificationManager.notify(notificationId,builder.build());


    }


    public void sms(String number, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        String[] numbers = number.split(",");
        for (int i = 0; i < numbers.length; i++) {
            smsManager.sendTextMessage(numbers[i],null, message, null, null);
        }
    }

    public void ringTone(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
