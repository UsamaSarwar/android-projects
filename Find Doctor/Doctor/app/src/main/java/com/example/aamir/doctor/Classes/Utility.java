package com.example.aamir.doctor.Classes;

import android.content.Context;
import android.graphics.Typeface;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.DoctorsList;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aamir on 5/28/2017.
 */

public class Utility {

    public static String name1,services,number,photo,address;




    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }


    public static String  dateFormat(String date){

        String [] Date = date.split("-");
      /*  if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M) {

            java.util.Calendar calendar = java.util.Calendar.getInstance();
            Log.d("NARRA",calendar.get(calendar.DAY_OF_MONTH)+"");
            if((calendar.get(java.util.Calendar.DAY_OF_MONTH)) == Integer.parseInt(Date[2])){
                return "Today";
            }else if((calendar.get(java.util.Calendar.DAY_OF_MONTH)-1) == Integer.parseInt(Date[2])){
                return "1day ago";
            }

        }*/


        if(Date[1].equals("01")){
            Date[1] = "January";
        }else if(Date[1].equals("02")){
            Date[1] = "February";
        }else if(Date[1].equals("03")){
            Date[1] = "March";
        }else if(Date[1].equals("04")){
            Date[1] = "April";
        }else if(Date[1].equals("05")){
            Date[1] = "May";
        }else if(Date[1].equals("06")){
            Date[1] = "June";
        }else if(Date[1].equals("07")){
            Date[1] = "July";
        }else if(Date[1].equals("08")){
            Date[1] = "August";
        }else if(Date[1].equals("09")){
            Date[1] = "September";
        }else if(Date[1].equals("10")){
            Date[1] = "October";
        }else if(Date[1].equals("11")){
            Date[1] = "November";
        }else if(Date[1].equals("12")){
            Date[1] = "December";
        }else {
            Date[1] = Date[1];
        }

        String date1 = Date[1]+" "+Date[2]+", "+Date[0];
        return date1;
    }


    public static boolean isValidMail(EditText editText,String email,Context context) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        if(!check) {
            Animation.shakeView(editText,context);
            editText.setError("Invalid email");
        }
        return check;
    }

    public static boolean isValidPassword(EditText editText,String phone,Context context) {
        boolean check=false;

            if(phone.length() < 6) {
                // if(phone.length() != 10) {
                Animation.shakeView(editText,context);
                editText.setError("Invalid password, it must have at least 6 characters");
                check = false;
            } else {
                check = true;
            }

        return check;
    }

    public static boolean isInvalidName(EditText editText,String name,Context context){

        boolean check=false;
        if(Pattern.matches("[a-zA-Z ]+", name)) {

           check = true;
        } else {
            Animation.shakeView(editText,context);
            editText.setError("Invalid name");
            check=false;
        }
        return check;
    }

    public static boolean isValidMobile(EditText editText,String phone,Context context) {
        boolean check=false;

            if(phone.length() < 11 || phone.length() > 13) {
                // if(phone.length() != 10) {
                editText.setError("Invalid phone number");
                Animation.shakeView(editText,context);
                check = false;
            } else {
                check = true;
            }

        return check;
    }

    public static boolean isEmpty(EditText editText,String msg, Context context){
        boolean flag = false;
        if(msg.length() < 1){
            editText.setError("Can not be empty");
            Animation.shakeView(editText,context);
            flag = false;
        }else {
            flag = true;
        }

        return flag;

    }

}







