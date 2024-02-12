package com.example.aamir.tablayout.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aamir on 8/4/2017.
 */

public class Utility {



    public static int getAlarmId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int alarmId = preferences.getInt("ALARM", 1);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ALARM", alarmId + 1).apply();

        return alarmId;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String  dateFormat(String date){

        String [] Date = date.split("/");

        Calendar calendar = Calendar.getInstance();

        if(Integer.parseInt(Date[0])==calendar.get(Calendar.DAY_OF_MONTH)){
            return "TODAY";
        }else if(Integer.parseInt(Date[0])==calendar.get(Calendar.DAY_OF_MONTH)+1){
            return "TOMORROW";
        }else if(Integer.parseInt(Date[0])==calendar.get(Calendar.DAY_OF_MONTH)-1){
            return "YESTERDAY";
        }

        /*String day1 = String.valueOf(day);
        if(day1.equals(day)){
            Date[0] = "TODAY";
            return Date[1];
        }*/


        if(Date[1].equals("0")){
            Date[1] = "January";
        }else if(Date[1].equals("1")){
            Date[1] = "February";
        }else if(Date[1].equals("2")){
            Date[1] = "March";
        }else if(Date[1].equals("3")){
            Date[1] = "April";
        }else if(Date[1].equals("4")){
            Date[1] = "May";
        }else if(Date[1].equals("5")){
            Date[1] = "June";
        }else if(Date[1].equals("6")){
            Date[1] = "July";
        }else if(Date[1].equals("7")){
            Date[1] = "August";
        }else if(Date[1].equals("8")){
            Date[1] = "September";
        }else if(Date[1].equals("9")){
            Date[1] = "October";
        }else if(Date[1].equals("10")){
            Date[1] = "November";
        }else if(Date[1].equals("11")){
            Date[1] = "December";
        }else {
            Date[1] = Date[1];
        }


        String date1 = Date[1]+" "+Date[0]+", "+Date[2];
        return date1;
    }

    public static String timeFormat(String tim){

        String [] time= tim.split(":");
        String format = null;
        String hour;
        int timeHour = Integer.parseInt(time[0]);
        int timeMinute = Integer.parseInt(time[1]);

        if(timeHour>12){
            timeHour = timeHour%12;
            format = "PM";
            if(timeHour == 0){
                timeHour = 1;
            }
        }else{
            if(timeHour == 0){
                timeHour = 1;
            }
            format = "AM";
        }

        if(timeMinute<10){
            hour = "0"+timeMinute;
        }else {
            hour = timeMinute+"";
        }


        return timeHour+":"+hour+" "+format;
    }

}
