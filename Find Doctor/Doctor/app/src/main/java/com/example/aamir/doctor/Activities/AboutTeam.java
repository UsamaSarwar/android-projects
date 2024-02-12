package com.example.aamir.doctor.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Adapter.TeamAdapter;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.DoctorListAdapter.ModalDrList;
import com.example.aamir.doctor.Modal.TeamModal;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutTeam extends AppCompatActivity {

    @BindView(R.id.back_team)
    ImageView back;
    @BindView(R.id.recyclerView_about_team)
    RecyclerView recyclerView;
    TeamAdapter adapter;
    @BindView(R.id.appName)
    TextView toolBarHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_team);

        ButterKnife.bind(this);






        if(Utility.isOnline(AboutTeam.this)){
            getTeam();
        }else {

            final android.app.FragmentManager fragmentManager = getFragmentManager();
            final CustomDialogue customDialogue = new CustomDialogue();
            customDialogue.show(fragmentManager, "my Fragment");

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Typeface typeFaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
        toolBarHeading.setTypeface(typeFaceBold);




    }

    private void getTeam() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(AboutTeam.this);
        builder.setView(R.layout.loading_dialogue);
        recyclerView.setVisibility(View.INVISIBLE);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        try {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<List<TeamModal>> call = service.getTeam();

            call.enqueue(new Callback<List<TeamModal>>() {
                @Override
                public void onResponse(Call<List<TeamModal>> call, Response<List<TeamModal>> response) {

                    List<TeamModal> list = response.body();
                    dialog.dismiss();
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AboutTeam.this));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    adapter = new TeamAdapter(AboutTeam.this,list);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<TeamModal>> call, Throwable t) {

                    Toast.makeText(AboutTeam.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
