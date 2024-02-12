package com.example.aamir.doctoradmin.Modals;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 6/10/2017.
 */

public class ModalCatList {

    @SerializedName("prof_id")
    private int prof_id;
    @SerializedName("prof_title")
    private String prof_title;

    public ModalCatList(int prof_id, String prof_title) {
        this.prof_id = prof_id;
        this.prof_title = prof_title;
    }

    public int getProf_id() {
        return prof_id;
    }

    public String getProf_name() {
        return prof_title;
    }
}
