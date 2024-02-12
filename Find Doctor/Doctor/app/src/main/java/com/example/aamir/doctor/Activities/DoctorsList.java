package com.example.aamir.doctor.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsList extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView goBack;
    @BindView(R.id.recyclerView_doctor_list)
    RecyclerView drListrecyclerView;
    @BindView(R.id.search_by_name_city)
    SearchView searchByName;
    @BindView(R.id.button_retry)
    Button retry;
    @BindView(R.id.textView_catName)
    TextView catName;
    @BindView(R.id.imageView_no_record)
            ImageView noRecord;

    int cityID;
    int catId;
    String send,catNameText;

    Typeface typefaceBold, typefaceRegular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        ButterKnife.bind(this);

        typefaceBold  = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
        typefaceRegular  = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");

        retry.setTypeface(typefaceRegular);

        initListner();

        getBundle();

        if(Utility.isOnline(DoctorsList.this)){
            getDoctorList(catId,cityID,"");
        }else {

            final android.app.FragmentManager fragmentManager = getFragmentManager();
            final CustomDialogue customDialogue = new CustomDialogue();
            customDialogue.show(fragmentManager, "my Fragment");
            retry.setVisibility(View.VISIBLE);
        }


    }

    private void getBundle() {

        Bundle b = getIntent().getExtras();
        if(b!=null){
            cityID = b.getInt("city_id");
            catId = b.getInt("cat_id");
            send = b.getString("send");
            catNameText = b.getString("cat_name");
            catName.setText(catNameText);
            catName.setTypeface(typefaceBold);


        }
    }

    private void getDoctorList(int cat_id, int city_id, String doc_name) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(DoctorsList.this);
        builder.setView(R.layout.loading_dialogue);
        drListrecyclerView.setVisibility(View.INVISIBLE);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        try {
            APIService service = ApiClient.getClient().create(APIService.class);

            Call<List<ModalDrList>> call = service.getSpecificDoctor(cat_id,city_id,doc_name);

            call.enqueue(new Callback<List<ModalDrList>>() {
                @Override
                public void onResponse(Call<List<ModalDrList>> call, Response<List<ModalDrList>> response) {
                    List<ModalDrList> drList = response.body();

                    dialog.dismiss();
                    if(drList.size()==0){
                       noRecord.setVisibility(View.VISIBLE);
                    }else{

                        retry.setVisibility(View.GONE);


                        drListrecyclerView.setVisibility(View.VISIBLE);
                        drListrecyclerView.setHasFixedSize(true);
                        drListrecyclerView.setLayoutManager(new LinearLayoutManager(DoctorsList.this));
                        DoctorListAdapter drAdapter = new DoctorListAdapter(DoctorsList.this,drList);
                        drListrecyclerView.setAdapter(drAdapter);
                        searchByName.clearFocus();
                    }

                }

                @Override
                public void onFailure(Call<List<ModalDrList>> call, Throwable t) {
                    Toast.makeText(DoctorsList.this, "Something wrong try again!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initListner() {
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(DoctorsList.this)){
                    getDoctorList(catId,cityID,"");
                    retry.setVisibility(View.GONE);
                }else {
                    final android.app.FragmentManager fragmentManager = getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                    retry.setVisibility(View.VISIBLE);
                }

            }
        });


        searchByName.setQueryHint("Search by name...");
        searchByName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(Utility.isOnline(DoctorsList.this)){
                    getDoctorList(-1,cityID,query);

                }else {
                    final android.app.FragmentManager fragmentManager = getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }


}
