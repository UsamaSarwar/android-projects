package com.example.aamir.doctor.Retrofit;

import com.example.aamir.doctor.CityListAdapter.ModalCityList;
import com.example.aamir.doctor.Classes.FeedBack;
import com.example.aamir.doctor.Classes.ServerMSG;
import com.example.aamir.doctor.DoctorListAdapter.ModalDrList;
import com.example.aamir.doctor.LabListAdapter.ModalLab;
import com.example.aamir.doctor.Modal.BlogModal;
import com.example.aamir.doctor.Modal.HospitalModal;
import com.example.aamir.doctor.Modal.TeamModal;
import com.example.aamir.doctor.ModalFeedBack;

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

    @POST("team.php/")
    Call<List<TeamModal>> getTeam();

    @FormUrlEncoded
    @POST("blog.php")
    Call<List<BlogModal>> getBlog(@Field("choice") int choice);

    @FormUrlEncoded
    @POST("hospital.php")
    Call<List<HospitalModal>> getHospital(@Field("city_id") int city_id);
   /*@FormUrlEncoded
   @POST("hospital.php")
   Call<List<HospitalModal>> getHospital();*/

    @FormUrlEncoded
    @POST("blog_long_desc.php")
    Call <String> getBlogLongDesc(@Field("blog_id") int blog_id);



    @FormUrlEncoded
    @POST("doctor_list.php/")
    Call<List<ModalDrList>> getSpecificDoctor(@Field("prof_id") int prof_id,@Field("city_id") int city_id
    ,@Field("doc_name") String doc_name);

    @FormUrlEncoded
    @POST("register_user.php/")
    Call<String> regUser(@Field("name") String name,@Field("user_name") String user_name,
                                  @Field("password") String password);


    @FormUrlEncoded
    @POST("feedback.php/")
    Call<String> sendFeed(@Field("doc_id") int doc_id,@Field("user") String user,@Field("feedback") String feedback);

    @FormUrlEncoded
    @POST("get_feedback.php")
    Call<List<ModalFeedBack>> getFeedback(@Field("doc_id") int doc_id);

    @FormUrlEncoded
    @POST("contact_us.php")
    Call<String> sendContact(@Field("name") String name,@Field("phone") String phone
    ,@Field("subject") String subject,@Field("message") String message);


    @FormUrlEncoded
    @POST("login.php/")
    Call<String> userLogin(@Field("user_name") String name,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("labs.php")
    Call<List<ModalLab>> getLabs(@Field("city_id") int city_id);

}
