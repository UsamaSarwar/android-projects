package com.example.aamir.doctoradmin.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aamir.doctoradmin.MainActivity;
import com.example.aamir.doctoradmin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminFront extends AppCompatActivity {

    @BindView(R.id.button_add_doctor)
    Button addDoctor;
    @BindView(R.id.button_delete_doctor)
    Button delDoctor;
    @BindView(R.id.button_add_lab)
    Button addLab;
    @BindView(R.id.button_delete_lab1)
    Button delLab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_front);
        ButterKnife.bind(this);

        addDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminFront.this, MainActivity.class));
            }
        });
        delDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminFront.this,MainPage.class));
            }
        });

        addLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminFront.this,AddLab.class));
            }
        });

        delLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminFront.this,DelLab.class));
            }
        });

    }
}
