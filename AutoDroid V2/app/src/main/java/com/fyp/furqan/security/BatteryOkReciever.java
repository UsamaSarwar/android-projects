package com.fyp.furqan.security;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;

/**
 * Created by Furqan on 3/31/2016.
 */
public class BatteryOkReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wf = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        final SharedPreferences mpref = context.getSharedPreferences("BatteryLowDB", Activity.MODE_PRIVATE);
if(mpref.getString("BT","False").equals("True")){


        ba.enable();

}

        if(mpref.getString("Wifi","False").equals("True")){

            wf.setWifiEnabled(true);

        }


    }
}
