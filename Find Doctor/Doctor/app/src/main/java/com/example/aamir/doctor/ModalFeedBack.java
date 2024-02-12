package com.example.aamir.doctor;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 5/31/2017.
 */

public class ModalFeedBack {

    @SerializedName("fb_id")
    private int fb_id;
    @SerializedName("doc_id")
    private int doc_id;
    @SerializedName("user")
    private String user;
    @SerializedName("feedback")
    private String feedBack;
    @SerializedName("time")
    private String time;

    public ModalFeedBack(int fb_id, int doc_id, String user, String feedBack, String time) {
        this.fb_id = fb_id;
        this.doc_id = doc_id;
        this.user = user;
        this.feedBack = feedBack;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public int getFb_id() {
        return fb_id;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public String getTime() {
        return time;
    }
}
