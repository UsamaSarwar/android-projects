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

import com.example.aamir.doctoradmin.Adapter.CustomCityListAdapter;
import com.example.aamir.doctoradmin.Adapter.CustomDrListAdapter;
import com.example.aamir.doctoradmin.Adapter.CustomLabListAdapter;
import com.example.aamir.doctoradmin.Modals.ModalCityList;
import com.example.aamir.doctoradmin.Modals.ModalDrList;
import com.example.aamir.doctoradmin.Modals.ModalLabList;
import com.example.aamir.doctoradmin.R;
import com.example.aamir.doctoradmin.Retrofit.APIService;
import com.example.aamir.doctoradmin.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DelLab extends AppCompatActivity {

    @BindView(R.id.lab_del_Spinner)
    Spinner citySpinner;
    @BindView(R.id.findLab)
    Button find;
    @BindView(R.id.lab_list_recyclerView)
    RecyclerView labListRecyclerView;
    int cityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_lab);
        ButterKnife.bind(this);
        getData();

        initListner();
    }

    private void initListner() {

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLabData(cityId);
            }
        });
    }
    private void getLabData(int city) {
        final ProgressDialog dialogue = ProgressDialog.show(DelLab.this,"","Loading...Please wait!",true);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call <List<ModalLabList>> call = service.getLabList(city);

        call.enqueue(new Callback<List<ModalLabList>>() {
            @Override
            public void onResponse(Call<List<ModalLabList>> call, Response<List<ModalLabList>> response) {
                List<ModalLabList> list = response.body();
                labListRecyclerView.setLayoutManager(new LinearLayoutManager(DelLab.this));
                labListRecyclerView.setHasFixedSize(true);
                CustomLabListAdapter adapter = new CustomLabListAdapter(DelLab.this,list);
                labListRecyclerView.setAdapter(adapter);
                dialogue.dismiss();
            }

            @Override
            public void onFailure(Call<List<ModalLabList>> call, Throwable t) {
                dialogue.dismiss();
                Toast.makeText(DelLab.this, "Weak internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData() {

        final ProgressDialog progressDialog = ProgressDialog.show(DelLab.this, "",
                "Loading. Please wait...", true);

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<List<ModalCityList>> call = service.getData();

        call.enqueue(new Callback<List<ModalCityList>>() {
            @Override
            public void onResponse(Call<List<ModalCityList>> call, Response<List<ModalCityList>> response) {
                progressDialog.dismiss();
                final List<ModalCityList> list  = response.body();
                CustomCityListAdapter adapter = new CustomCityListAdapter(DelLab.this,list);
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
                Toast.makeText(DelLab.this, "Weak internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
