package com.example.aamir.tablayout.AlarmService;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Aamir on 5/7/2017.
 */

public class AlarmManager {

    public void setAlarm(int alarmId,Context context, java.util.Calendar calendar, int click){

        Intent alarmIntent = new Intent(context,AlarmReceiver.class);
        if (click == 1) {
            alarmIntent.putExtra("Extra","alarm on");
            alarmIntent.putExtra("ID",alarmId);
            PendingIntent sender = PendingIntent.getBroadcast(context,alarmId, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);


        }
        else {
            android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmIntent.putExtra("Extra","alarm off");
            PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(context,alarmId,
                    alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            context.sendBroadcast(alarmIntent);
            alarmManager.cancel(cancelPendingIntent);
        }

    }
}
