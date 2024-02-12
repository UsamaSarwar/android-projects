package com.example.aamir.doctor.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.CityListAdapter.CityListAdapter;
import com.example.aamir.doctor.CityListAdapter.ModalCityList;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseLocation extends AppCompatActivity {

    @BindView(R.id.recyclerView_city_list)
    RecyclerView cityRecyclerView;
    @BindView(R.id.backLocation)
    ImageView back;
    @BindView(R.id.button_retry_location)
    Button retry;
    @BindView(R.id.appName)
    TextView toolBarHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        ButterKnife.bind(this);




        /*SharedPreferences sp1 = getSharedPreferences("User",MODE_PRIVATE);
        if(sp1.getString("name","").equals("e") || sp1.getString("user_name","").equals("e")
                || sp1.getString("password","").equals("e")){
            startActivity(new Intent(ChooseLocation.this,LoginSignup.class));
        }*/

        initListner();


        if(Utility.isOnline(ChooseLocation.this)){

            getCityList();

        }else {

            final android.app.FragmentManager fragmentManager = getFragmentManager();
            final CustomDialogue customDialogue = new CustomDialogue();
            customDialogue.show(fragmentManager, "my Fragment");
            retry.setVisibility(View.VISIBLE);
        }

        setTypeFace();

   }

    private void setTypeFace() {


        Typeface typeFaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeFaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");

        retry.setTypeface(typeFaceRegular);
        toolBarHeading.setTypeface(typeFaceBold);

    }

    private void initListner() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(ChooseLocation.this)){
                    getCityList();
                    retry.setVisibility(View.GONE);
                }else {
                    final android.app.FragmentManager fragmentManager = getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                    retry.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getCityList() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ChooseLocation.this);
        builder.setView(R.layout.loading_dialogue);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        try {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<List<ModalCityList>> call = service.getData();

            call.enqueue(new Callback<List<ModalCityList>>() {
                @Override
                public void onResponse(Call<List<ModalCityList>> call, Response<List<ModalCityList>> response) {
                    retry.setVisibility(View.GONE);
                    List<ModalCityList> list = response.body();
                    dialog.dismiss();
                    setAdapter(list);
                }

                @Override
                public void onFailure(Call<List<ModalCityList>> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(ChooseLocation.this, "Something wrong try again!!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAdapter(List<ModalCityList> list) {

        cityRecyclerView.setHasFixedSize(true);
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseLocation.this));
        CityListAdapter adapter = new CityListAdapter(ChooseLocation.this,list);
        cityRecyclerView.setAdapter(adapter);

    }

}
