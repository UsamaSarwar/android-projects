package com.fyp.furqan.security;

import android.content.Intent;

/**
 * Created by Furqan on 4/1/2016.
 */
public class Application extends android.app.Application  {


    @Override
    public void onCreate() {

        super.onCreate();
       startService(new Intent(getApplicationContext(), MyService.class));



    }

}
