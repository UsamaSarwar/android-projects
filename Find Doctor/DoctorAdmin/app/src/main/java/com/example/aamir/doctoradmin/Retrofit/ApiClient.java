package com.example.aamir.doctoradmin.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aamir on 5/21/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "http://developme.comli.com/";
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }


        return retrofit;
    }


}
