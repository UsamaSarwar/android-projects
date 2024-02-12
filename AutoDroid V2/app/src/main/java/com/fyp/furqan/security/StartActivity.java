package com.fyp.furqan.security;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class StartActivity extends Activity {
    public static Drawable d,k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
     //   startService();


    }

public void starSecurity(View view){

    startActivity(new Intent(getApplicationContext(), mainfile.class));

}

    public void startLoc(View view){
        startActivity(new Intent(getApplicationContext(), Location.class));

    }
    public void startBat(View view){
        startActivity(new Intent(getApplicationContext(), Battery_Low_Activity.class));

    }
    public void Meeting(View view){

        startActivity(new Intent(getApplicationContext(),MeetingActivity.class));
    }
}
