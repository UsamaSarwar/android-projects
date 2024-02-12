package com.example.aamir.doctor.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aamir.doctor.GridMainList.ModalDrCat;
import com.example.aamir.doctor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aamir on 5/27/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Find_doctor_db";
    private static final String DOCTOR_CAT_ID = "doctor_cat_id";
    private static final String TABLE_NAME = "Doctor_category";
    private static final String DOCTOR_CAT_ICON = "Doctor_cat_icon";
    private static final String DOCTOR_CAT_NAME = "Doctor_cat_name";

    private String QUERY = "CREATE TABLE "+ TABLE_NAME +" ("+
            DOCTOR_CAT_ID+" INTEGER PRIMARY KEY, "+
            DOCTOR_CAT_ICON+" TEXT, "+
            DOCTOR_CAT_NAME+" TEXT) ;";


    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(QUERY);

            addAllIcons(db);
            setSharedPref(context);
            setSharedPref1(context);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addAllIcons(SQLiteDatabase db) {
        String[] iconName = context.getResources().getStringArray(R.array.doctor_cat_icon);
        String[] docCatName = context.getResources().getStringArray(R.array.doctor_cat_name);

        for(int i=0;i<iconName.length;i++){
            ContentValues values = new ContentValues();
            values.put(DOCTOR_CAT_ID,i+1);
            values.put(DOCTOR_CAT_ICON,iconName[i]);
            values.put(DOCTOR_CAT_NAME,docCatName[i]);
            db.insert(TABLE_NAME,null,values);
        }
    }


    public List<ModalDrCat> allCategories(){
        List<ModalDrCat> docCatList = new ArrayList<>();
        try {
            SQLiteDatabase sqlite = getReadableDatabase();
            Cursor c = sqlite.rawQuery("select * from " +TABLE_NAME,null);
            c.moveToFirst();

            for(int i=0; i<c.getCount();i++){

                int id = c.getInt(c.getColumnIndex(DOCTOR_CAT_ID));
                String icon = c.getString(c.getColumnIndex(DOCTOR_CAT_ICON));
                String drCatName = c.getString(c.getColumnIndex(DOCTOR_CAT_NAME));

                docCatList.add(new ModalDrCat(id,icon,drCatName));
                c.moveToNext();
            }
            sqlite.close();
            return docCatList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.rawQuery("drop " + TABLE_NAME, null);
            db.execSQL(QUERY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSharedPref(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("City",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("city_id",-1);
        editor.putString("city_name","Select City");
        editor.apply();
    }

    public void setSharedPref1(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("User",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name","e");
        editor.putString("user_name","e");
        editor.putString("email","e");
        editor.apply();
    }
}
