package com.example.aamir.doctoradmin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aamir.doctoradmin.Adapter.CustomCatListAdapter;
import com.example.aamir.doctoradmin.Adapter.CustomCityListAdapter;
import com.example.aamir.doctoradmin.Classes.Utility;
import com.example.aamir.doctoradmin.Modals.ModalCatList;
import com.example.aamir.doctoradmin.Modals.ModalCityList;
import com.example.aamir.doctoradmin.Retrofit.APIService;
import com.example.aamir.doctoradmin.Retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.Spinner)
           Spinner citySpinner;
    @BindView(R.id.Spinner2)
            Spinner drCatSpinner;
    @BindView(R.id.editText_dr_name)
    EditText drName;
    @BindView(R.id.editText_dr_edu)
    EditText drEdu;
    @BindView(R.id.editText_dr_timing)
    EditText drTiming;
    @BindView(R.id.editText_dr_fee)
    EditText drFee;
    @BindView(R.id.editText_dr_experience)
    EditText drExperience;
    @BindView(R.id.editText_dr_phone)
    EditText drPhone;
    @BindView(R.id.editText_dr_address)
    EditText drAddress;
    @BindView(R.id.editText_dr_services)
    EditText drServices;
    @BindView(R.id.editText_dr_category)
    EditText drCat;
    @BindView(R.id.editText_dr_hospital)
    EditText drHospital;
    @BindView(R.id.button_submit)
    Button submit;
    @BindView(R.id.retry)
            Button retry;

    int profId,cityId;


    ArrayList<ModalCityList> cityList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getData();

        addTextChangeListner();

        checkFieldsForEmptyValues();

        initListner();



    }

    private void addTextChangeListner() {

        drName.addTextChangedListener(textWatcher);
        drEdu.addTextChangedListener(textWatcher);
        drTiming.addTextChangedListener(textWatcher);
        drFee.addTextChangedListener(textWatcher);
        drExperience.addTextChangedListener(textWatcher);
        drPhone.addTextChangedListener(textWatcher);
        drAddress.addTextChangedListener(textWatcher);
        drServices.addTextChangedListener(textWatcher);
        drCat.addTextChangedListener(textWatcher);
        drHospital.addTextChangedListener(textWatcher);

    }

    private void initListner() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(MainActivity.this)){
                    addDoctor();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("No Internet");
                    builder.setMessage("Need an internet connection...");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.show();
                }
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void addDoctor() {
        final ProgressDialog progess = ProgressDialog.show(MainActivity.this,"","Adding. Please Wait...",true);

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<String> call = service.addDoctor(profId,cityId,drName.getText().toString(),drEdu.getText().toString(),
                drTiming.getText().toString(),drFee.getText().toString(),drExperience.getText().toString(),
                drPhone.getText().toString(),drAddress.getText().toString(),drServices.getText().toString(),
                drCat.getText().toString(),drHospital.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String respons = response.body();
                progess.dismiss();
                setFieldToNull();
                Toast.makeText(MainActivity.this, respons+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progess.dismiss();
                Toast.makeText(MainActivity.this, "Weak internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setFieldToNull() {
        drName.setText("");
        drEdu.setText("");
        drTiming.setText("");
        drFee.setText("");
        drFee.setText("");
        drExperience.setText("");
        drPhone.setText("");
        drAddress.setText("");
        drServices.setText("");
        drCat.setText("");
        drHospital.setText("");
    }

    private void getData() {

        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "",
                "Loading. Please wait...", true);

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<List<ModalCityList>> call = service.getData();

        call.enqueue(new Callback<List<ModalCityList>>() {
            @Override
            public void onResponse(Call<List<ModalCityList>> call, Response<List<ModalCityList>> response) {

                final List<ModalCityList> list  = response.body();
                CustomCityListAdapter adapter = new CustomCityListAdapter(MainActivity.this,list);
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
                Toast.makeText(MainActivity.this, "Weak internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        Call<List<ModalCatList>> callDrCat = service.getCatList();

        callDrCat.enqueue(new Callback<List<ModalCatList>>() {
            @Override
            public void onResponse(Call<List<ModalCatList>> call, Response<List<ModalCatList>> response) {
                final List<ModalCatList> catList = response.body();

                CustomCatListAdapter adapter = new CustomCatListAdapter(MainActivity.this,catList);

                drCatSpinner.setAdapter(adapter);

                progressDialog.dismiss();

                drCatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Toast.makeText(MainActivity.this, "Weak internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmptyValues();
        }
    };

    private void checkFieldsForEmptyValues() {

        if(drName.length()==0 || drEdu.length()==0 || drTiming.length()==0 || drFee.length()==0
                || drExperience.length()==0 || drPhone.length()==0 || drAddress.length()==0
                || drServices.length()==0 ||drCat.length()==0 ||drHospital.length()==0){
            submit.setEnabled(false);
        }else {
            submit.setEnabled(true);
        }

    }
}
