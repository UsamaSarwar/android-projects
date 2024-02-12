package com.example.aamir.doctor.Modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aamir on 8/31/2017.
 */

public class HospitalModal {

    @SerializedName("hospital_id")
    private int hospital_id;
    @SerializedName("hospital_name")
    private String hospital_name;
    @SerializedName("hospital_phone")
    private String hospital_phone;
    @SerializedName("hospital_services")
    private String hospital_services;
    @SerializedName("hospital_address")
    private String hospital_address;
    @SerializedName("hospital_photo")
    private String hospital_photo;

    public HospitalModal(int hospital_id, String hospital_name, String hospital_phone, String hospital_services, String hospital_address, String hospital_photo) {
        this.hospital_id = hospital_id;
        this.hospital_name = hospital_name;
        this.hospital_phone = hospital_phone;
        this.hospital_services = hospital_services;
        this.hospital_address = hospital_address;
        this.hospital_photo = hospital_photo;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public String getHospital_phone() {
        return hospital_phone;
    }

    public String getHospital_services() {
        return hospital_services;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public String getHospital_photo() {
        return hospital_photo;
    }
}
