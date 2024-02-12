package com.fyp.furqan.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Location extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

    }

public  void ADDLOC(View view){

    startActivity(new Intent(getApplicationContext(),Locaion_Config.class));
}

   public void LOCLIST(View view) {
        startActivity(new Intent(getApplicationContext(), Locations_List.class));
    }
}
