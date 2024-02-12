package com.example.aamir.doctor.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.aamir.doctor.Database.DbHelper;
import com.example.aamir.doctor.GridMainList.ModalDrCat;
import com.example.aamir.doctor.R;

import java.util.List;

public class SplashScreen extends AppCompatActivity {

    DbHelper dbHelper = new DbHelper(SplashScreen.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash_screen);





        //dbHelper.setSharedPref();



        List<ModalDrCat> list = dbHelper.allCategories();
        dbHelper.close();
        Thread thread = new Thread(){

            @Override
            public void run() {

                try {
                    sleep(700);
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
