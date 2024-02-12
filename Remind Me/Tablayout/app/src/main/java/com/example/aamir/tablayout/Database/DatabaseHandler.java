package com.example.aamir.tablayout.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aamir.tablayout.Modals.Reminder;

import java.util.ArrayList;

/**
 * Created by Aamir on 8/2/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "reminder_db";
    private static final String REminderTable = "reminder_table";
    private static final String IconTable = "icon_table";
    private static final String ColorTable = "color_table";
    private static final String ContactTable = "contact_table";
    private static final String ICON_ID = "icon_id";
    private static final String CONTACT_ID = "contact_id";
    private static final String REMINDER_ID = "reminder_id";
    private static final String REMINDER_ACTIVE_DEACTIVE = "reminder_active_deactive";
    private static final String REMINDER_SUBJECT = "reminder_subject";
    private static final String REMINDER_TIME_HOUR = "reminder_time_hour";
    private static final String REMINDER_TIME_MINUTES = "reminder_time_minutes";
    private static final String REMINDER_DATE_DAY = "reminder_date_day";
    private static final String REMINDER_DATE_MONTH = "reminder_date_month";
    private static final String REMINDER_DATE_YEAR = "reminder_date_year";
    private static final String REMINDER_PARTICIPANT = "reminder_participant";
    private static final String REMINDER_PARTICIPANT_Name = "reminder_participant_name";
    private static final String REMINDER_MESSAGE = "reminder_msg";
    private static final String REMINDER_ICON = "reminder_icon";
    private static final String REMINDER_ACTIVE_FLAG = "reminder_active_flag";
    private static final String REMINDER_COLOR = "reminder_color";



    private String ReminderQuery = "CREATE TABLE " + REminderTable + " (" +
            REMINDER_ID + " integer PRIMARY KEY, " +
            REMINDER_SUBJECT + " TEXT, " +
            REMINDER_TIME_HOUR + " integer, " +
            REMINDER_TIME_MINUTES + " integer, " +
            REMINDER_DATE_DAY + " integer, " +
            REMINDER_DATE_MONTH + " integer, " +
            REMINDER_DATE_YEAR + " integer, " +
            REMINDER_MESSAGE + " TEXT," +
            REMINDER_ACTIVE_FLAG + " integer," +
            REMINDER_ACTIVE_DEACTIVE + " integer);";

    private String IconQuery = "CREATE TABLE " + IconTable + " (" +
            REMINDER_ID + " integer," +
            REMINDER_ICON + " integer);";

    private String ColorQuery = "CREATE TABLE " + ColorTable + " (" +
            REMINDER_ID + " integer," +
            REMINDER_COLOR + " integer);";


    private String ContactQuery = "CREATE TABLE " + ContactTable + " (" +
            CONTACT_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
            REMINDER_ID + " integer," +
            REMINDER_PARTICIPANT + " TEXT," +
            REMINDER_PARTICIPANT_Name + " TEXT);";


    Context context;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null ,1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(ReminderQuery);
            db.execSQL(IconQuery);
            db.execSQL(ContactQuery);
            db.execSQL(ColorQuery);

            Log.d("LAAAA","sucess");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.rawQuery("drop " + REminderTable, null);
            db.rawQuery("drop " + IconTable, null);
            db.rawQuery("drop " + ContactTable, null);
            db.rawQuery("drop " + ColorTable, null);
            db.execSQL(ReminderQuery);
            db.execSQL(IconQuery);
            db.execSQL(ContactQuery);
            db.execSQL(ColorQuery);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean addReminder(int id, String subject,int minutes,int hour ,int day,int month, int year,String message){

        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(REMINDER_ID,id);
            contentValues.put(REMINDER_SUBJECT, subject);
            contentValues.put(REMINDER_TIME_HOUR, hour);
            contentValues.put(REMINDER_TIME_MINUTES, minutes);
            contentValues.put(REMINDER_DATE_DAY, day);
            contentValues.put(REMINDER_DATE_MONTH, month);
            contentValues.put(REMINDER_DATE_YEAR, year);
            contentValues.put(REMINDER_MESSAGE,message);
            contentValues.put(REMINDER_ACTIVE_FLAG, 1);
            contentValues.put(REMINDER_ACTIVE_DEACTIVE,1);
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Long i = sqLiteDatabase.insert(REminderTable, null, contentValues);

           /* Cursor c = sqLiteDatabase.rawQuery("select * from " + REminderTable, null);
            c.moveToFirst();
            for (int ik = 0; ik < c.getCount(); ik++) {
                Log.d("RESULT : " + ik, c.getString(c.getColumnIndex(REMINDER_ID))+" | "+c.getString(c.getColumnIndex(REMINDER_SUBJECT))
                        +" "+c.getString(c.getColumnIndex(REMINDER_TIME_MINUTES))+":"+c.getString(c.getColumnIndex(REMINDER_TIME_HOUR))
                        +" "+c.getString(c.getColumnIndex(REMINDER_DATE_DAY))+"/"+c.getString(c.getColumnIndex(REMINDER_DATE_MONTH))
                        +"/"+c.getString(c.getColumnIndex(REMINDER_DATE_YEAR))
                        +" "+c.getString(c.getColumnIndex(REMINDER_ACTIVE_FLAG)));

                c.moveToNext();
            }
*/

            if (i <= -1) {
                return false;
            } else {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean updateReminder(int id,String subject,String message,int hour,int minute,int day,
                                  int month,int year,int icon,int color){

        try {

            if(updateIcon(id,icon) && updateColor(id,color)){

                ContentValues contentValues = new ContentValues();
                SQLiteDatabase sqLiteDatabase = getWritableDatabase();

                contentValues.put(REMINDER_SUBJECT,subject);
                contentValues.put(REMINDER_MESSAGE,message);
                contentValues.put(REMINDER_TIME_HOUR,hour);
                contentValues.put(REMINDER_TIME_MINUTES,minute);
                contentValues.put(REMINDER_DATE_DAY,day);
                contentValues.put(REMINDER_DATE_MONTH,month);
                contentValues.put(REMINDER_DATE_YEAR,year);
                contentValues.put(REMINDER_ACTIVE_FLAG,1);
                contentValues.put(REMINDER_ACTIVE_DEACTIVE,1);

                sqLiteDatabase.update(REminderTable,contentValues, REMINDER_ID +" = "+id,null);


                return true;
            }else {
                return false;
            }


        }catch (Exception e){
            e.printStackTrace();
        }


        return true;
    }

    public boolean addIcon(int remId, int icon){
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(REMINDER_ID,remId);
            contentValues.put(REMINDER_ICON,icon);
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Long i = sqLiteDatabase.insert(IconTable, null, contentValues);

            if(i <= -1){
                return false;
            }else{
                return true;
            }


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean addColor(int remId, int color){
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(REMINDER_ID,remId);
            contentValues.put(REMINDER_COLOR,color);
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Long i = sqLiteDatabase.insert(ColorTable, null, contentValues);

            if(i <= -1){
                return false;
            }else{
                return true;
            }


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<Reminder> getAllReminders(int choice) {

        String query = null;
        int ACTIVE = 1;
        int INACTIVE = 0;
        if(choice == 1){
            query = "select * from "+REminderTable+ " where "+REMINDER_ACTIVE_DEACTIVE+" = "+ ACTIVE +
                    " ORDER BY "+ REMINDER_DATE_DAY+" ASC";
        }else {
            query = "select * from "+REminderTable+ " where "+REMINDER_ACTIVE_DEACTIVE+" = "+ INACTIVE +
                    " ORDER BY "+ REMINDER_DATE_DAY+" DESC";
        }
        try {
            ArrayList<Reminder> list = new ArrayList<>();

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor c = sqLiteDatabase.rawQuery(query, null);
            c.moveToFirst();


            for (int i = 0; i < c.getCount(); i++) {
                String subject = c.getString(c.getColumnIndex(REMINDER_SUBJECT));

                String time = c.getString(c.getColumnIndex(REMINDER_TIME_HOUR)) + ":"
                        + c.getString(c.getColumnIndex(REMINDER_TIME_MINUTES));
                String date = c.getString(c.getColumnIndex(REMINDER_DATE_DAY)) + "/"
                        + (c.getString(c.getColumnIndex(REMINDER_DATE_MONTH))) + "/"
                        + c.getString(c.getColumnIndex(REMINDER_DATE_YEAR));
                Log.d("RESDAT",date+"");

                Integer flag = c.getInt(c.getColumnIndex(REMINDER_ACTIVE_FLAG));
                Integer id = c.getInt(c.getColumnIndex(REMINDER_ID));
                String message = c.getString(c.getColumnIndex(REMINDER_MESSAGE));

                list.add(new Reminder(id, subject, time, date, message,flag));
                c.moveToNext();
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteReminder(int id) {
        try {
            String delete = "Delete from " + REminderTable + " where " + REMINDER_ID + " = " + id;
            String deleteIcon = "Delete from " + IconTable + " where " + REMINDER_ID + " = " + id;
            String deleteColor = "Delete from " + ColorTable + " where " + REMINDER_ID + " = " + id;
            getWritableDatabase().execSQL(delete);
            getWritableDatabase().execSQL(deleteIcon);
            getWritableDatabase().execSQL(deleteColor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateFlag(int id, int flag) {
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();

            String update = "Update " + REminderTable + " set " + REMINDER_ACTIVE_FLAG + " = " + flag + " where " + REMINDER_ID
                    + " = " + id;
            Log.i("QUERY UPDATE:", update);
            sqLiteDatabase.execSQL(update);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateActive(int id, int flag) {
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();

            String update = "Update " + REminderTable + " set " + REMINDER_ACTIVE_DEACTIVE + " = " + flag + " where " + REMINDER_ID
                    + " = " + id;
            Log.i("QUERY UPDATE:", update);
            sqLiteDatabase.execSQL(update);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateIcon(int id,int icon){

        try {

            ContentValues contentValues = new ContentValues();
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();

            contentValues.put(REMINDER_ICON,icon);
            sqLiteDatabase.update(IconTable, contentValues ,REMINDER_ID +" = "+id, null );


            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean updateColor(int id , int color){
        try {
            ContentValues cv = new ContentValues();
            SQLiteDatabase sq = getWritableDatabase();

            cv.put(REMINDER_COLOR,color);

            sq.update(ColorTable,cv,REMINDER_ID +" = "+id,null);

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    public String getMessage(int id){
        try {
            String message = "";
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor c = sqLiteDatabase.rawQuery("select "+ REMINDER_MESSAGE +" from " + REminderTable +" where "
                    + REMINDER_ID +" = "+id, null);
            if (c != null && c.moveToFirst()) {
                message = c.getString(c.getColumnIndex(REMINDER_MESSAGE));
                c.close();
            }

            return message;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int getIcon(int id){
        try {
            int  icon = 0;
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor c = sqLiteDatabase.rawQuery("select "+ REMINDER_ICON +" from " + IconTable +" where "
                    + REMINDER_ID +" = "+id, null);
            if (c != null && c.moveToFirst()) {
                icon = c.getInt(c.getColumnIndex(REMINDER_ICON));
                c.close();
            }

            return icon;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public String getSubject(int id){
        try {
            String subject = "";
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor c = sqLiteDatabase.rawQuery("select "+ REMINDER_SUBJECT +" from " + REminderTable +" where "
                    + REMINDER_ID +" = "+id, null);
            if (c != null && c.moveToFirst()) {
                subject = c.getString(c.getColumnIndex(REMINDER_SUBJECT));
                c.close();
            }

            return subject;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    public int getColor(int id){
        try {
            int  color = 0;
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor c = sqLiteDatabase.rawQuery("select "+ REMINDER_COLOR +" from " + ColorTable +" where "
                    + REMINDER_ID +" = "+id, null);
            if (c != null && c.moveToFirst()) {
                color = c.getInt(c.getColumnIndex(REMINDER_COLOR));
                c.close();
            }

            return color;
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



}
