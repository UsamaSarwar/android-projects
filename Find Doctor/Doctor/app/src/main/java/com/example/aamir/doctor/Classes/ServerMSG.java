package com.example.aamir.doctor.Classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 5/30/2017.
 */

public class ServerMSG {

    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;


    public ServerMSG(int success, String message) {
        this.success = success;
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
