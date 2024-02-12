package com.fyp.furqan.security;

/**
 * Created by Furqan on 2/22/2016.
 */
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Furqan on 10/20/2015.
 */
public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
private Double prevLat,prevLong;
    private GoogleApiClient mLocationClient;
    private Location mCurrentLocation;
    LocationRequest mLocationRequest;
    private String preName ="Default";

RemindersDbAdapter dbAdapter;
public  double lat;
public     SharedPreferences mpref;
public double lon;
    private Cursor c;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO do something useful
      //  Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();

         mpref = getSharedPreferences("BatteryLowDB", Activity.MODE_PRIVATE);

        prevLat=1.334;
        prevLong=2.343;
        dbAdapter= new RemindersDbAdapter(getApplicationContext());
     //  mLocationClient = new GoogleApiClient.Builder(MyService.this).addApiIfAvailable(LocationServices.API).addConnectionCallbacks(MyService.this)
       ///        .addOnConnectionFailedListener(MyService.this).build();
        //  mLocationClient = new GoogleApiClient.Builder(this)
          //      .addApi(LocationServices.API)
                //.addConnectionCallbacks(this)
            //    .addOnConnectionFailedListener(this)
              //  .build();
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationClient.connect();


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(60000 *5);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest.setFastestInterval(10000);


        return Service.START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
//For Adding Location or Updating
try {
    //Toast.makeText(MyService.this,location.getLongitude()+ "  " + location.getLatitude(), Toast.LENGTH_SHORT).show();
    mCurrentLocation = location;
    if (Locaion_Config.chk == 1) {
        Locaion_Config.stLon = mCurrentLocation.getLongitude();
        Locaion_Config.stLat = mCurrentLocation.getLatitude();
        Locaion_Config.chk = 0;
    }
    dbAdapter.open();
    c = dbAdapter.fetchAllReminders();

    // startManagingCursor(c);
    c.moveToFirst();

    //NEW
    if (c.moveToFirst()) {
        int n = 0;
        int kl = 0;
        while (c.isAfterLast() == false) {
            kl++;

            lat = c.getDouble(c.getColumnIndex(RemindersDbAdapter.LAT));
            lon = c.getDouble(c.getColumnIndex(RemindersDbAdapter.LON));
//Log.d("DATA_LOG",kl +"  "+ c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_TITLE))+" "+lat+"  "+lon);
            if (mCurrentLocation.getLatitude() > (lon - 0.0009088) && mCurrentLocation.getLatitude() < (lon + 0.0009088)) {
                if (mCurrentLocation.getLongitude() > (lat - 0.001059) && mCurrentLocation.getLongitude() < (lat + 0.001059)) {
                    n++;
                    // Toast.makeText(MyService.this,prevLong+ "PreviousSelected", Toast.LENGTH_SHORT).show();
                  // if(!mpref.getString("prevNmae","Default").equals(preName))
                    if ((mCurrentLocation.getLatitude() > (prevLong + 0.0009088)) || (mCurrentLocation.getLatitude() < (prevLong - 0.0009088)) || (mCurrentLocation.getLongitude() < (prevLat - 0.001059)) || (mCurrentLocation.getLongitude() > (prevLat + 0.001059))) {
                        SharedPreferences.Editor editor = mpref.edit();


                        prevLat = lat;
                        prevLong = lon;
                        preName = c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_TITLE));
                        editor.putString("prevName",preName);
                        editor.apply();
                        Intent I = new Intent(getApplicationContext(), PerformTask.class);
                        I.putExtra("InternetFun", c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_INTERNET_MODE)));
                        I.putExtra("ProfileFun", c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_Profile_TIME)));
                        I.putExtra("ScreenFun", c.getString(c.getColumnIndex(RemindersDbAdapter.LOCK_SCRN)));
                        //      Log.d("STRINGPIC", c.getColumnCount() + "");
                        I.putExtra("Wall", c.getString(c.getColumnIndex(RemindersDbAdapter.picCheck)));
                        I.putExtra("Wallpath", c.getString(c.getColumnIndex(RemindersDbAdapter.picPath)));
                        I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //  Log.d("DATA_Stored",preName);


                        Toast.makeText(MyService.this, "You are at " + c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_TITLE)), Toast.LENGTH_SHORT).show();
                        startActivity(I);


                        dbAdapter.close();


                    }

                }
            }
            c.moveToNext();

        }
        if (n == 0) {
            n++;
            if (!preName.equals("Default")) {
                c.moveToFirst();
                prevLat = lat;
                prevLong = lon;
                preName = c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_TITLE));
                Intent I = new Intent(getApplicationContext(), PerformTask.class);
                I.putExtra("InternetFun", c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_INTERNET_MODE)));
                I.putExtra("ProfileFun", c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_Profile_TIME)));
                I.putExtra("ScreenFun", c.getString(c.getColumnIndex(RemindersDbAdapter.LOCK_SCRN)));
                //      Log.d("STRINGPIC", c.getColumnCount() + "");
                I.putExtra("Wall", c.getString(c.getColumnIndex(RemindersDbAdapter.picCheck)));
                I.putExtra("Wallpath", c.getString(c.getColumnIndex(RemindersDbAdapter.picPath)));
                I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //  Log.d("DATA_Stored",preName);


                Toast.makeText(MyService.this, "You are at " + c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_TITLE)), Toast.LENGTH_SHORT).show();
                startActivity(I);
                dbAdapter.close();
            }

        }
    }
    //  AsyncTaskRunner runner = new AsyncTaskRunner();
    //   runner.execute("");
    //  Toast.makeText(this, mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
}
catch (Exception e){

}
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub
      //  Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        try {    //if(servicesConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);
        } catch (Exception e) {//}
            Toast.makeText(MyService.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
      //  Toast.makeText(this, "Disconnected. Please re-connect.",
      //          Toast.LENGTH_SHORT).show();

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub

        return null;
    }



}