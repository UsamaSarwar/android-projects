package com.example.aamir.tablayout.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.aamir.tablayout.Fragments.PreferenceFragment;
import com.example.aamir.tablayout.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreferenceActivity extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);



        ButterKnife.bind(this);

        //set toolbar
        setSupportActionBar(toolbar);
        //show back arraow on toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Setting");
        }


        getFragmentManager().beginTransaction().replace(R.id.content_frame, new PreferenceFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
