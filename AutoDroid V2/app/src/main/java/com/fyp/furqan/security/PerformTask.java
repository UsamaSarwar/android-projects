package com.fyp.furqan.security;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class PerformTask extends Activity {
    public         AudioManager am;
    String wapath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perform_task);
        WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
try {
    Intent l = getIntent();
    String internet = l.getExtras().getString("InternetFun");
    String profile = l.getExtras().getString("ProfileFun");
    //  String Screen = l.getExtras().getString("ScreenFun");
    String wallp = l.getExtras().getString("Wall");
    wapath = l.getExtras().getString("Wallpath");
    //  Toast.makeText(PerformTask.this,wapath+ "", Toast.LENGTH_SHORT).show();
    if (internet.equals("Wifi")) {
        if (wifiManager.isWifiEnabled() == false) {
            wifiManager.setWifiEnabled(true);
        }
        turnData(false);
    } else if (internet.equals("3G")) {
        if (wifiManager.isWifiEnabled() == true) {
            wifiManager.setWifiEnabled(false);
        }
        turnData(true);
    } else {
        if (wifiManager.isWifiEnabled() == true) {
            wifiManager.setWifiEnabled(false);
        }
        turnData(false);

    }

    if (profile.equals("Silent")) {
        soundMode(2);
    } else if (profile.equals("Vibrate")) {
        soundMode(3);
    } else {
        soundMode(1);

    }


    if (wallp.equals("1")) {
        Bitmap b = BitmapFactory.decodeFile(wapath);

        try {
            saveBitmap(b);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


/*
    WallpaperManager wpm = WallpaperManager.getInstance(getApplicationContext());
    InputStream ins = null;
    try {
        ins = new URL(wapath).openStream();
    } catch (IOException e) {
        e.printStackTrace();
    }
    try {
        wpm.setStream(ins);
    } catch (IOException e) {
        e.printStackTrace();
        Log.d("MYCHECK",e.getMessage());
    }
*/
    }



    finish();
}
catch (Exception e){
    Toast.makeText(PerformTask.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
}


    }
    void turnData(boolean ON)
    {
        final ConnectivityManager conman = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass;
        try {
            conmanClass = Class.forName(conman.getClass().getName());

        final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManager = iConnectivityManagerField.get(conman);
        final Class iConnectivityManagerClass =  Class.forName(iConnectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);
        setMobileDataEnabledMethod.invoke(iConnectivityManager, ON);
        } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }



    void soundMode(int a){
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

//For Normal mode
        if(a==1)
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

//For Silent mode
        else if(a==2)
            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);

//For Vibrate mode
        else if(a==3)
            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);


    }
    private void saveBitmap(Bitmap bp) throws FileNotFoundException {
        String state = Environment.getExternalStorageState();
        File folder;
        //if there is memory card available code choose that
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            folder=Environment.getExternalStorageDirectory();
        }else{
            folder= Environment.getDataDirectory();
        }
        folder=new File(folder, "/aaaa");
        if(!folder.exists()){
            folder.mkdir();

        }

        File file=new File(folder, "myWall"+".jpg");
    wapath=     file.getAbsolutePath();
        FileOutputStream os=new FileOutputStream(file);

        bp.compress(Bitmap.CompressFormat.JPEG,10, os);
        WallpaperManager wManager = WallpaperManager.getInstance(getApplicationContext());

        try {
            wManager.setBitmap(bp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
