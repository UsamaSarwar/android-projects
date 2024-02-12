package com.example.aamir.doctor.Modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 8/21/2017.
 */

public class BlogModal {

    @SerializedName("blog_id")
    private int blog_id;
    @SerializedName("heading")
    private String heading;
    @SerializedName("short_desc")
    private String short_desc;
    @SerializedName("photo")
    private String photo;
    @SerializedName("datetime")
    private String datetime;


    public BlogModal(int blog_id, String heading, String short_desc, String photo, String datetime) {
        this.blog_id = blog_id;
        this.heading = heading;
        this.short_desc = short_desc;
        this.photo = photo;
        this.datetime = datetime;
    }


    public int getBlog_id() {
        return blog_id;
    }

    public String getHeading() {
        return heading;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDatetime() {
        return datetime;
    }
}
