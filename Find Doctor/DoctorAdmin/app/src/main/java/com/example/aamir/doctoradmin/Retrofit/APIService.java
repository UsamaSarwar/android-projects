package com.example.aamir.doctoradmin.Retrofit;


import com.example.aamir.doctoradmin.Modals.ModalCatList;
import com.example.aamir.doctoradmin.Modals.ModalCityList;
import com.example.aamir.doctoradmin.Modals.ModalDrList;
import com.example.aamir.doctoradmin.Modals.ModalLabList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Aamir on 5/21/2017.
 */

public interface APIService {

    @POST("city_list.php/")
    Call<List<ModalCityList>> getData();


    @POST("prof_list.php/")
    Call<List<ModalCatList>> getCatList();

    @FormUrlEncoded
    @POST("dr_list.php/")
    Call<List<ModalDrList>> getDrList(@Field("prof_id") int prof_id,@Field("city_id") int city_id);

    @FormUrlEncoded
    @POST("labs.php/")
    Call<List<ModalLabList>> getLabList(@Field("city_id") int city_id);


    @FormUrlEncoded
    @POST("add_doctor.php/")
    Call<String> addDoctor(@Field("prof_id") int prof_id, @Field("city_id") int city_id,
                           @Field("doc_name") String doc_name, @Field("doc_edu") String doc_edu
            , @Field("doc_timing") String doc_timing, @Field("doc_fee") String doc_fee,
                           @Field("experience") String experience, @Field("doc_phone") String doc_phone,
                           @Field("doc_address") String doc_address, @Field("Services") String Services
            , @Field("category") String category
            , @Field("hospital") String hospital);




    @FormUrlEncoded
    @POST("del_doctor.php/")
    Call<String> deleteDoctor(@Field("doc_id") int doc_id);

    @FormUrlEncoded
    @POST("del_lab.php/")
    Call<String> deleteLab(@Field("lab_id") int lab_id);


    @FormUrlEncoded
    @POST("add_lab.php/")
    Call<String> addLab(@Field("city_id") int city_id,@Field("lab_name") String lab_name,
                        @Field("lab_phone") String lab_phone,@Field("lab_address") String lab_address);

}
