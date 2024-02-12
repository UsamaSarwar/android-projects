package com.fyp.furqan.security;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class RemindersDbAdapter {

	private static final String DATABASE_NAME = "data";                     
    private static final String DATABASE_TABLE = "AutoBase";
    private static final int DATABASE_VERSION = 1;                          
    
    public static final String KEY_TITLE = "title";                         
    public static final String KEY_INTERNET_MODE = "Internet_Mode";
    public static final String KEY_Profile_TIME = "Profile_Mode"; 
    public static final String KEY_ROWID = "d";
    public static final String LOCK_SCRN ="screen";
    public static final String LAT ="latitude";
    public static final String LON ="longitude";
    public static final String picCheck ="pickCheck";
    public static final String picPath ="pickPath";


    private DatabaseHelper mDbHelper;                                      
    private SQLiteDatabase mDb;                                            
    private static final String DATABASE_CREATE =                          
    "create table " + DATABASE_TABLE + " ("
            + KEY_ROWID + " integer primary key autoincrement, "
            + KEY_TITLE + " text not null, "
            + KEY_INTERNET_MODE +  " text not null, "
            + KEY_Profile_TIME +  " text not null, "
            + LAT +  " double not null, "
            + LON +  " double not null, "
            + picCheck +  " text not null, "
            + picPath +  " text , "
            + LOCK_SCRN + " text not null);";
   private final Context mCtx;                                                
   public RemindersDbAdapter(Context ctx) {                                   
           this.mCtx = ctx;
            }
       //
       // inner class DataBaseHelper
       //
   private static class DatabaseHelper extends SQLiteOpenHelper {              
       DatabaseHelper(Context context) {
           super(context, DATABASE_NAME, null, DATABASE_VERSION);          
       }
       @Override
       public void onCreate(SQLiteDatabase db) {


           db.execSQL(DATABASE_CREATE);
           ContentValues initialValues = new ContentValues();
           initialValues.put(KEY_TITLE, "Default");
           initialValues.put(KEY_INTERNET_MODE, "Wifi");
           initialValues.put(KEY_Profile_TIME, "Normal");
           initialValues.put(LOCK_SCRN, "1");
           initialValues.put(LAT,"0");
           initialValues.put(LON,"0");
           initialValues.put(picCheck,"0");
           initialValues.put(picPath, "abc");
           db.insert(DATABASE_TABLE, null, initialValues);
       }
       
       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {                                      
           // Not used, but you could upgrade the database with ALTER           
//Scripts 
       }
}
   public RemindersDbAdapter open() throws SQLException {
       mDbHelper = new DatabaseHelper(mCtx);
       mDb = mDbHelper.getWritableDatabase();
       return this;
}
   public void close() {
	      mDbHelper.close();
	}
   //
   // Add new Reminder
  //
   long  createReminder(String title, String mode, String reminderDateTime,String ScrnLock,Double lon,Double lat,String pic,String path) {


      //String DBInsertQu = "insert into reminder values((select count(*) from reminder)+1,\'"+title+"\',\'"+mode+"\',\'"+reminderDateTime+"\',"+lat+","+lon+",\'"+ScrnLock+"\');";
      // Log.d("TAG", DBInsertQu);


//       mDb.execSQL(DBInsertQu);



       ContentValues initialValues = new ContentValues();
		           initialValues.put(KEY_TITLE, title);
		           initialValues.put(KEY_INTERNET_MODE, mode);
		           initialValues.put(KEY_Profile_TIME, reminderDateTime);
                   initialValues.put(LOCK_SCRN, ScrnLock);
       initialValues.put(LAT,lat);
       initialValues.put(LON,lon);
       initialValues.put(picCheck,pic);
       initialValues.put(picPath,path);
      return 	mDb.insert(DATABASE_TABLE, null, initialValues);
             //  mDb.insert(DATABASE_TABLE,null, initialValues);

		 }
   //
   // Delete Reminder 
   public boolean deleteReminder(long rowId) {                            
	      
	        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;     
	    }
   public boolean deleteAllNumber() {                            
	      
       return mDb.delete(DATABASE_TABLE, null, null) > 0;     
   }
   //
   // Fetch all data from database..
   public Cursor fetchAllReminders() {                                    
       return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
               KEY_INTERNET_MODE, KEY_Profile_TIME,LOCK_SCRN,LON,LAT,picCheck,picPath}, null, null, null, null, null);
   }
   //
   // Fetch single Reminder data from data base
   public Cursor fetchReminder(long rowId) throws SQLException {          
       Cursor mCursor =
               mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                       KEY_TITLE, KEY_INTERNET_MODE, KEY_Profile_TIME,LOCK_SCRN,LAT,LON}, KEY_ROWID + "=" +
                       rowId, null,null, null, null, null);
       if (mCursor != null) {
           mCursor.moveToFirst();                                         
       }
       return mCursor;
   }
   
  // Update Reminder data 
   public boolean updateReminder(long rowId, String title, String internet, String
		   profile,String lockScreen,Double lon,Double lat,String pic,String path) {
		           ContentValues args = new ContentValues();                          
		           args.put(KEY_TITLE, title);
		           args.put(KEY_INTERNET_MODE, internet);
		           args.put(KEY_Profile_TIME, profile);
                   args.put(LOCK_SCRN, lockScreen);
       args.put(LAT,lat);
       args.put(LON,lon);
      args.put(picCheck,pic);
       args.put(picPath,path);

       return
		         mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0; 
		       }
}
