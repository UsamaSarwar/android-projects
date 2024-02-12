package com.example.aamir.doctor.Classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 5/30/2017.
 */

public class FeedBack {

    @SerializedName("message")
    private String message;

    public FeedBack(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
