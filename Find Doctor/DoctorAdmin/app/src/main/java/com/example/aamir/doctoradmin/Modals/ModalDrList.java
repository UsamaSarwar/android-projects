package com.example.aamir.doctoradmin.Modals;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 6/18/2017.
 */

public class ModalDrList {

    @SerializedName("doc_id")
    int doc_id;
    @SerializedName("doc_name")
    String doc_name;
    @SerializedName("doc_address")
    String doc_address;
    @SerializedName("hospital")
    String hospital;

    public ModalDrList(int doc_id, String doc_name, String doc_address, String hospital) {
        this.doc_id = doc_id;
        this.doc_name = doc_name;
        this.doc_address = doc_address;
        this.hospital = hospital;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public String getDoc_address() {
        return doc_address;
    }

    public String getHospital() {
        return hospital;
    }
}
