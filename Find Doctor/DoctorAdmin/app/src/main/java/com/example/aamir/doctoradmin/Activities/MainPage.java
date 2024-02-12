package com.example.aamir.doctoradmin.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aamir.doctoradmin.Adapter.CustomCatListAdapter;
import com.example.aamir.doctoradmin.Adapter.CustomCityListAdapter;
import com.example.aamir.doctoradmin.Adapter.CustomDrListAdapter;
import com.example.aamir.doctoradmin.MainActivity;
import com.example.aamir.doctoradmin.Modals.ModalCatList;
import com.example.aamir.doctoradmin.Modals.ModalCityList;
import com.example.aamir.doctoradmin.Modals.ModalDrList;
import com.example.aamir.doctoradmin.R;
import com.example.aamir.doctoradmin.Retrofit.APIService;
import com.example.aamir.doctoradmin.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPage extends AppCompatActivity {

    @BindView(R.id.catSpinner)
    Spinner catSpinner;
    @BindView(R.id.citySpinner)
    Spinner citySpinner;
    @BindView(R.id.find)
    Button find;
    @BindView(R.id.drListRecyclerView)
    RecyclerView drListRecyclerView;
    int profId,cityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);

        getData();


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDrData(profId,cityId);
            }
        });


    }

    private void getDrData(int pro,int city) {
        final ProgressDialog dialogue = ProgressDialog.show(MainPage.this,"","Loading...Please wait!",true);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call <List<ModalDrList>> call = service.getDrList(pro,city);

        call.enqueue(new Callback<List<ModalDrList>>() {
            @Override
            public void onResponse(Call<List<ModalDrList>> call, Response<List<ModalDrList>> response) {
                List<ModalDrList> list = response.body();
                drListRecyclerView.setLayoutManager(new LinearLayoutManager(MainPage.this));
                drListRecyclerView.setHasFixedSize(true);
                CustomDrListAdapter adapter = new CustomDrListAdapter(MainPage.this,list);
                drListRecyclerView.setAdapter(adapter);
                dialogue.dismiss();
            }

            @Override
            public void onFailure(Call<List<ModalDrList>> call, Throwable t) {
                dialogue.dismiss();
                Toast.makeText(MainPage.this, "Weak internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData() {

        final ProgressDialog progressDialog = ProgressDialog.show(MainPage.this, "",
                "Loading. Please wait...", true);

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<List<ModalCityList>> call = service.getData();

        call.enqueue(new Callback<List<ModalCityList>>() {
            @Override
            public void onResponse(Call<List<ModalCityList>> call, Response<List<ModalCityList>> response) {

                final List<ModalCityList> list  = response.body();
                CustomCityListAdapter adapter = new CustomCityListAdapter(MainPage.this,list);
                citySpinner.setAdapter(adapter);
                citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        cityId = list.get(position).getCity_id();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModalCityList>> call, Throwable t) {
                Toast.makeText(MainPage.this, "Weak internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        Call<List<ModalCatList>> callDrCat = service.getCatList();

        callDrCat.enqueue(new Callback<List<ModalCatList>>() {
            @Override
            public void onResponse(Call<List<ModalCatList>> call, Response<List<ModalCatList>> response) {
                final List<ModalCatList> catList = response.body();

                CustomCatListAdapter adapter = new CustomCatListAdapter(MainPage.this,catList);

                catSpinner.setAdapter(adapter);

                progressDialog.dismiss();

                catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        profId = catList.get(position).getProf_id();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModalCatList>> call, Throwable t) {
                Toast.makeText(MainPage.this, "Weak internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}
