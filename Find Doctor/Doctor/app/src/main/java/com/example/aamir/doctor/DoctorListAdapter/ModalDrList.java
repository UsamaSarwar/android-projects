package com.example.aamir.doctor.DoctorListAdapter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 5/27/2017.
 */

public class ModalDrList {

    @SerializedName("doc_id")
    private int doc_id;
    @SerializedName("prof_id")
    private int prof_id;
    @SerializedName("city_id")
    private int city_id;
    @SerializedName("doc_name")
    private String doc_name;
    @SerializedName("doc_edu")
    private String doc_edu;
    @SerializedName("doc_timing")
    private String doc_timing;
    @SerializedName("doc_fee")
    private String doc_fee;
    @SerializedName("experience")
    private String experience;
    @SerializedName("doc_phone")
    private String doc_phone;
    @SerializedName("doc_address")
    private String doc_address;
    @SerializedName("Services")
    private String Services;
    @SerializedName("doc_photo")
    private String doc_photo;
    @SerializedName("category")
    private String category;
    @SerializedName("hospital")
    private String hospital;

    public ModalDrList(int doc_id, int prof_id, int city_id, String doc_name,
                       String doc_edu, String doc_timing, String doc_fee,
                       String experience, String doc_phone, String doc_address,
                       String services, String doc_photo, String category, String hospital) {
        this.doc_id = doc_id;
        this.prof_id = prof_id;
        this.city_id = city_id;
        this.doc_name = doc_name;
        this.doc_edu = doc_edu;
        this.doc_timing = doc_timing;
        this.doc_fee = doc_fee;
        this.experience = experience;
        this.doc_phone = doc_phone;
        this.doc_address = doc_address;
        Services = services;
        this.doc_photo = doc_photo;
        this.category = category;
        this.hospital = hospital;
    }

    public String getHospital() {
        return hospital;
    }

    public String getCategory() {
        return category;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public int getProf_id() {
        return prof_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public String getDoc_edu() {
        return doc_edu;
    }

    public String getDoc_timing() {
        return doc_timing;
    }

    public String getDoc_fee() {
        return doc_fee;
    }

    public String getExperience() {
        return experience;
    }

    public String getDoc_phone() {
        return doc_phone;
    }

    public String getDoc_address() {
        return doc_address;
    }

    public String getServices() {
        return Services;
    }

    public String getDoc_photo() {
        return doc_photo;
    }
}
