package com.fyp.furqan.security;

import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Furqan on 3/31/2016.
 */
public class BatteryLowReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    ///////////////////////////////

        final SharedPreferences mpref = context.getSharedPreferences("BatteryLow", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mpref.edit();

        WifiManager wf = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        Method mtd =null;

        //3G Connection Off
        if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()){

            try {
                mtd= cm.getClass().getDeclaredMethod("setMobileDataEnabled",boolean.class);
                mtd.setAccessible(false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        //Wifi Connection Off
        if (wf.isWifiEnabled() == true) {
            editor.putString("Wifi","True");

            wf.setWifiEnabled(false);
        }
        else{
            editor.putString("Wifi","False");

        }

        //BlueTooth Connection Off
        if (ba.isEnabled()) {
            editor.putString("BT","True");
            ba.disable();
        }
        else{
            editor.putString("BT","False");

        }

        // For Running Applications
        ActivityManager actvityManager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();
        Toast.makeText(context, procInfos.size()+"", Toast.LENGTH_SHORT).show();
        editor.apply();

/////////////////////////////////
        try
        {
            String ph=mpref.getString("Phone","");

            String msg=mpref.getString("Message","");

            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(ph, null, msg, null, null);

        }
        catch (Exception e)
        {

        }

       };
    }

