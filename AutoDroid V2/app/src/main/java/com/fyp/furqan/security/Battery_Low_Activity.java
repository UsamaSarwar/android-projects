package com.fyp.furqan.security;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Battery_Low_Activity extends Activity {
SharedPreferences mpref ;
EditText a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery__low_);
       mpref = getSharedPreferences("BatteryLowDB", Activity.MODE_PRIVATE);

        a= (EditText) findViewById(R.id.BatteryPhone);
        b= (EditText) findViewById(R.id.BatteryMesage);

        final String phone=       mpref.getString("Phone", "");
        final String msg=       mpref.getString("Message", "");

        a.setText(phone);
        b.setText(msg);
    }


 public   void  savePrefs(View view){
       SharedPreferences.Editor editor = mpref.edit();
editor.putString("Phone",a.getText().toString() );
       editor.putString("Message",b.getText().toString() );
editor.apply();
     Toast.makeText(Battery_Low_Activity.this, "Saved", Toast.LENGTH_SHORT).show();
finish();
   }
}
