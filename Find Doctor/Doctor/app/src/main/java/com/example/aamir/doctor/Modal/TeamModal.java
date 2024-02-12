package com.example.aamir.doctor.Modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 8/18/2017.
 */

public class TeamModal {

    @SerializedName("person_id")
    private int person_id;
    @SerializedName("person_image")
    private String person_image;
    @SerializedName("person_name")
    private String person_name;
    @SerializedName("person_prof")
    private String person_prof;
    @SerializedName("person_category")
    private String person_category;

    public TeamModal(int person_id, String person_image, String person_name, String person_prof, String person_category) {
        this.person_id = person_id;
        this.person_image = person_image;
        this.person_name = person_name;
        this.person_prof = person_prof;
        this.person_category = person_category;
    }

    public int getPerson_id() {
        return person_id;
    }

    public String getPerson_image() {
        return person_image;
    }

    public String getPerson_name() {
        return person_name;
    }

    public String getPerson_prof() {
        return person_prof;
    }

    public String getPerson_category() {
        return person_category;
    }
}
