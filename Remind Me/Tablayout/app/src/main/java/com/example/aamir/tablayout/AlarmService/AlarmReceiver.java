package com.example.aamir.tablayout.AlarmService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.example.aamir.tablayout.Classes.NotificationUtil;
import com.example.aamir.tablayout.Database.DatabaseHandler;
import com.example.aamir.tablayout.Fragments.ActiveReminderList;
import com.example.aamir.tablayout.R;

/**
 * Created by ayaan on 30/03/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        String mystring = intent.getExtras().getString("Extra");
        int id = intent.getExtras().getInt("ID");




        if(mystring.equals("alarm on")){
           new NotificationUtil(context).createNotification(id);

            DatabaseHandler database = new DatabaseHandler(context);
            if(database.UpdateActive(id,0) && database.UpdateFlag(id,0)){
                Intent updateIntent = new Intent("BROADCAST_REFRESH");
                LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);
            }else {
                Toast.makeText(context, "Error active change", Toast.LENGTH_SHORT).show();
            }

       }

    }


}
