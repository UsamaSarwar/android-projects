package com.example.aamir.tablayout.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.aamir.tablayout.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash_screen);

        //hidding status bar

        Thread thread = new Thread(){

            @Override
            public void run() {

                try{
                    sleep(2500);
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

        };
        thread.start();
    }
}
