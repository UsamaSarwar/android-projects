package com.example.aamir.doctor.Activities;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.DoctorListAdapter.DoctorListAdapter;
import com.example.aamir.doctor.DoctorListAdapter.ModalDrList;
import com.example.aamir.doctor.LabListAdapter.CustomLabListAdapter;
import com.example.aamir.doctor.LabListAdapter.ModalLab;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabPage extends AppCompatActivity {

    @BindView(R.id.lab_page_back)
    ImageView back;
    @BindView(R.id.lab_page_button_retry)
    Button retry;
    @BindView(R.id.appName)
    TextView labSearch;
    @BindView(R.id.recyclerView_lab_list)
    RecyclerView recyclerViewLabList;
    @BindView(R.id.imageView_no_record)
            ImageView noRecord;


    int cityID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_page);
        ButterKnife.bind(this);



        Bundle b = getIntent().getExtras();
        if(b!=null){
            cityID = b.getInt("city_id");

        }

        if(Utility.isOnline(LabPage.this)){
            getLabsList(cityID);
        }else {

            final android.app.FragmentManager fragmentManager = getFragmentManager();
            final CustomDialogue customDialogue = new CustomDialogue();
            customDialogue.show(fragmentManager, "my Fragment");
            retry.setVisibility(View.VISIBLE);

        }

        initListner();

        setTypeFace();


    }

    private void setTypeFace() {

        Typeface typeFaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
        Typeface typeFaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");

        labSearch.setTypeface(typeFaceBold);

        retry.setTypeface(typeFaceRegular);


    }

    private void initListner() {

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(LabPage.this)){
                    getLabsList(cityID);
                    retry.setVisibility(View.GONE);
                }else {
                    final android.app.FragmentManager fragmentManager = getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                    retry.setVisibility(View.VISIBLE);
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getLabsList(int cityID){

        final AlertDialog.Builder builder = new AlertDialog.Builder(LabPage.this);
        builder.setView(R.layout.loading_dialogue);
        recyclerViewLabList.setVisibility(View.INVISIBLE);
        final AlertDialog alertDialog = builder.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        try {
            APIService service = ApiClient.getClient().create(APIService.class);

            Call<List<ModalLab>> call = service.getLabs(cityID);

            call.enqueue(new Callback<List<ModalLab>>() {
                @Override
                public void onResponse(Call<List<ModalLab>> call, Response<List<ModalLab>> response) {

                    List<ModalLab> modalLabs = response.body();
                    alertDialog.dismiss();
                    if(modalLabs.size()==0){
                       noRecord.setVisibility(View.VISIBLE);
                    }else{

                        retry.setVisibility(View.GONE);
                        recyclerViewLabList.setVisibility(View.VISIBLE);

                        recyclerViewLabList.setHasFixedSize(true);
                        recyclerViewLabList.setLayoutManager(new LinearLayoutManager(LabPage.this));
                        CustomLabListAdapter drAdapter = new CustomLabListAdapter(LabPage.this,modalLabs);
                        recyclerViewLabList.setAdapter(drAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<ModalLab>> call, Throwable t) {
                    alertDialog.dismiss();
                    Toast.makeText(LabPage.this, "Something wrong try again!!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
