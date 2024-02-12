package com.example.aamir.doctor.CityListAdapter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aamir on 5/28/2017.
 */

public class ModalCityList {

    @SerializedName("city_id")
    int city_id;
    @SerializedName("city_name")
    String city_name;

    public ModalCityList(int city_id, String city_name) {
        this.city_id = city_id;
        this.city_name = city_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public String getCity_name() {
        return city_name;
    }
}
