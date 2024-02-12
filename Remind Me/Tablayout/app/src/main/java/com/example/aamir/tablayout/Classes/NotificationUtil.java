package com.example.aamir.tablayout.Classes;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.aamir.tablayout.Activities.MainActivity;
import com.example.aamir.tablayout.Database.DatabaseHandler;

/**
 * Created by Aamir on 8/7/2017.
 */

public class NotificationUtil {


    private Context context;
    private NotificationManager notificationManager;
    private int notificationId;

    public NotificationUtil(Context context) {
        this.context = context;
    }

    public void createNotification(int id){

        notification(id,context);
    }

    private void notification(int id,Context context) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        int icon = databaseHandler.getIcon(id);
        String message = databaseHandler.getMessage(id);
        String subject = databaseHandler.getSubject(id);

        notificationId = id;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Intent viewIntent = new Intent(context, MainActivity.class);
        viewIntent.putExtra("NOTIFICATION_ID", id);
        PendingIntent pending = PendingIntent.getActivity(context, id, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setColor(databaseHandler.getColor(id))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentTitle(subject)
                .setContentText(message)
                .setTicker(subject)
                .setContentIntent(pending);

        String soundUri = sharedPreferences.getString("NotificationSound", "content://settings/system/notification_sound");
        if (soundUri.length() != 0) {
            builder.setSound(Uri.parse(soundUri));
        }
        if (sharedPreferences.getBoolean("checkBoxLED", true)) {
            builder.setLights(Color.WHITE, 700, 1500);
            builder.setLights(Color.BLUE, 700, 1500);
        }
        if (sharedPreferences.getBoolean("checkBoxOngoing", false)) {
            builder.setOngoing(true);
        }
        if (sharedPreferences.getBoolean("checkBoxVibrate", true)) {
            long[] pattern = {0, 300, 0};
            builder.setVibrate(pattern);
        }
        if(sharedPreferences.getBoolean("checkBoxPopup", true)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                builder.setPriority(Notification.PRIORITY_MAX);
            }
        }


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());

    }

}
