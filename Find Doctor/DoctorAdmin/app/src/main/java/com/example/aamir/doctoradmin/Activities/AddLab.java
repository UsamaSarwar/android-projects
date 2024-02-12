package com.example.aamir.doctoradmin.Activities;

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

import com.example.aamir.doctoradmin.Adapter.CustomCityListAdapter;
import com.example.aamir.doctoradmin.Classes.Utility;
import com.example.aamir.doctoradmin.MainActivity;
import com.example.aamir.doctoradmin.Modals.ModalCityList;
import com.example.aamir.doctoradmin.R;
import com.example.aamir.doctoradmin.Retrofit.APIService;
import com.example.aamir.doctoradmin.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLab extends AppCompatActivity {

    @BindView(R.id.editText_Lab_name)
    EditText labName;
    @BindView(R.id.editText_Lab_phone)
    EditText labPhone;
    @BindView(R.id.editText_Lab_address)
    EditText ladAddress;
    @BindView(R.id.button_Lab_submit)
    Button Submit;
    @BindView(R.id.lab_Spinner)
    Spinner citySpinner;
    int cityId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lab);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getData();

        addTextChangeListner();
        checkFieldsForEmptyValues();
        initListner();
    }




    private void initListner() {
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(AddLab.this)){
                    addLab();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddLab.this);
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

    }

    private void addLab() {
        final ProgressDialog progess = ProgressDialog.show(AddLab.this,"","Adding. Please Wait...",true);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<String> call = service.addLab(cityId,labName.getText().toString(),labPhone.getText().toString()
        ,ladAddress.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progess.dismiss();
                String respons = response.body();
                setFieldToNull();
                Toast.makeText(AddLab.this, respons+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progess.dismiss();
                Toast.makeText(AddLab.this, "Weak internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setFieldToNull() {
        labName.setText("");
        labPhone.setText("");
        ladAddress.setText("");
    }

    private void addTextChangeListner() {

        labName.addTextChangedListener(textWatcher);
        labPhone.addTextChangedListener(textWatcher);
        ladAddress.addTextChangedListener(textWatcher);

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

        if(labName.length()==0 || labPhone.length()==0 || ladAddress.length()==0){
            Submit.setEnabled(false);
        }else {
            Submit.setEnabled(true);
        }

    }



    private void getData() {

        final ProgressDialog progressDialog = ProgressDialog.show(AddLab.this, "",
                "Loading. Please wait...", true);

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<List<ModalCityList>> call = service.getData();

        call.enqueue(new Callback<List<ModalCityList>>() {
            @Override
            public void onResponse(Call<List<ModalCityList>> call, Response<List<ModalCityList>> response) {
                progressDialog.dismiss();
                final List<ModalCityList> list = response.body();
                CustomCityListAdapter adapter = new CustomCityListAdapter(AddLab.this, list);
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
                Toast.makeText(AddLab.this, "Weak internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
