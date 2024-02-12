package com.example.aamir.doctor.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aamir on 5/21/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "http://tabeeb.com.pk/ABX00iDtqXPc/";
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }


        return retrofit;
    }


}
