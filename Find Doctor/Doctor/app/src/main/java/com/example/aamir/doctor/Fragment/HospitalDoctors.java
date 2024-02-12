package com.example.aamir.doctor.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.HospitalList;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.DoctorListAdapter.DoctorListAdapter;
import com.example.aamir.doctor.DoctorListAdapter.ModalDrList;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aamir on 8/31/2017.
 */

public class HospitalDoctors extends Fragment{

    @BindView(R.id.button_hospital_drs_retry)
    Button retry;
    @BindView(R.id.layout_not_found)
    LinearLayout layoutNotFound;
    @BindView(R.id.recyclerView_hospitals)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    DoctorListAdapter adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hospital_doctor,container,false);

        ButterKnife.bind(this,view);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"Quicksand-Regular.otf");
        retry.setTypeface(typeface);


        initListner();


        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(Utility.isOnline(getActivity())){

            getData();

        }else {

            retry.setVisibility(View.VISIBLE);
        }




        return view;
    }


    private void initListner() {

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.isOnline(getActivity())){
                    getData();
                    retry.setVisibility(View.GONE);

                }else {
                    final android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                    retry.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void getData() {

        SharedPreferences sp = getActivity().getSharedPreferences("City", Context.MODE_PRIVATE);
        final int city_id = sp.getInt("city_id",0);

        progressBar.setVisibility(View.VISIBLE);

        try {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<List<ModalDrList>> call = service.getSpecificDoctor(-2,city_id,Utility.name1);

            call.enqueue(new Callback<List<ModalDrList>>() {
                @Override
                public void onResponse(Call<List<ModalDrList>> call, Response<List<ModalDrList>> response) {

                    List<ModalDrList> list = response.body();

                    progressBar.setVisibility(View.GONE);
                    if(list.size() == 0){
                        layoutNotFound.setVisibility(View.VISIBLE);
                    }else {

                        adapter = new DoctorListAdapter(getActivity(),list);
                        recyclerView.setAdapter(adapter);
                    }

                }

                @Override
                public void onFailure(Call<List<ModalDrList>> call, Throwable t) {

                    progressBar.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }



    }




}
