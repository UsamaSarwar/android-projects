package com.example.aamir.doctoradmin.Modals;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 6/25/2017.
 */

public class ModalLabList {

    @SerializedName("lab_id")
    int lab_id;
    @SerializedName("lab_name")
    String lab_name;
    @SerializedName("lab_phone")
    String lab_phone;
    @SerializedName("lab_address")
    String lab_address;

    public ModalLabList(int lab_id, String lab_name, String lab_phone, String lab_address) {
        this.lab_id = lab_id;
        this.lab_name = lab_name;
        this.lab_phone = lab_phone;
        this.lab_address = lab_address;
    }

    public int getLab_id() {
        return lab_id;
    }

    public String getLab_name() {
        return lab_name;
    }

    public String getLab_address() {
        return lab_address;
    }
}