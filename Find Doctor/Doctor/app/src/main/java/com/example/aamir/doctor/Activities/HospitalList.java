package com.example.aamir.doctor.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Adapter.HospitalAdapter;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.Modal.HospitalModal;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalList extends AppCompatActivity {

    @BindView(R.id.back_feedback)
    ImageView goBack;
    @BindView(R.id.appName)
    TextView actionBarTitle;
    @BindView(R.id.recyclerView_hospitals)
    RecyclerView recyclerView;
    @BindView(R.id.button_retry)
    Button retry;
    @BindView(R.id.imageView_no_record)
    ImageView noRecord;

    private  static int cityId;

    HospitalAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        ButterKnife.bind(this);

        Typeface typface = Typeface.createFromAsset(getAssets(),"Quicksand-Regular.otf");

       // retry.setTypeface(typface);
        actionBarTitle.setTypeface(typface);

        retry.setTypeface(typface);
        //getting bundle and city id from main activity
        Bundle b = getIntent().getExtras();
        cityId = b.getInt("city_id");

        recyclerView.setLayoutManager(new LinearLayoutManager(HospitalList.this));
        recyclerView.setHasFixedSize(true);


        if(Utility.isOnline(HospitalList.this)){
            getData(cityId);


        }else {
            final android.app.FragmentManager fragmentManager = getFragmentManager();
            final CustomDialogue customDialogue = new CustomDialogue();
            customDialogue.show(fragmentManager, "my Fragment");
            retry.setVisibility(View.VISIBLE);
        }

        initListner();





    }

    private void initListner() {

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.isOnline(HospitalList.this)){
                    retry.setVisibility(View.GONE);
                   getData(cityId);

                }else {
                    final android.app.FragmentManager fragmentManager = getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                    retry.setVisibility(View.VISIBLE);
                }


            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getData(int id) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(HospitalList.this);
        builder.setView(R.layout.loading_dialogue);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        try{

            APIService service = ApiClient.getClient().create(APIService.class);
            Call <List<HospitalModal>> call = service.getHospital(id);

            call.enqueue(new Callback<List<HospitalModal>>() {
                @Override
                public void onResponse(Call<List<HospitalModal>> call, Response<List<HospitalModal>> response) {

                    List<HospitalModal> list = response.body();
                    dialog.dismiss();

                  if(list.size() == 0){
                      noRecord.setVisibility(View.VISIBLE);
                  }else {
                      adapter = new HospitalAdapter(HospitalList.this,list);
                      recyclerView.setAdapter(adapter);
                  }

                }

                @Override
                public void onFailure(Call<List<HospitalModal>> call, Throwable t) {

                    dialog.dismiss();
                    retry.setVisibility(View.VISIBLE);
                }
            });




        }catch (Exception e){
            e.printStackTrace();
        }






    }
}
