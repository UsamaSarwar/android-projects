package com.example.aamir.doctor.Activities;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Adapter.ViewPagerAdapter;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.Fragment.HospitalDoctors;
import com.example.aamir.doctor.Fragment.HospitalInfo;
import com.example.aamir.doctor.Fragment.Login;
import com.example.aamir.doctor.Fragment.SignUp;
import com.example.aamir.doctor.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HospitalDetail extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.back)
    ImageView goBack;
    @BindView(R.id.heading)
    TextView actionBarTiltle;
    @BindView(R.id.imageView_hospital_detail)
    ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        ButterKnife.bind(this);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new HospitalInfo(),"Detail");
        viewPagerAdapter.addFragment(new HospitalDoctors(),"Doctors");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        setTypeFace();

       getBundle();


        initListner();
    }

    private void getBundle() {

        Bundle b = getIntent().getExtras();
        Utility.name1 = b.getString("hospital_name");
        Utility.number = b.getString("hospital_phone");
        Utility.services = b.getString("hospital_services");
        Utility.address = b.getString("hospital_address");
        Utility.photo = b.getString("hospital_photo");

        String address =  b.getString("hospital_photo");
        //setting values

        actionBarTiltle.setText(b.getString("hospital_name"));

        Picasso.with(this).load(address).resize(360,175)
                .placeholder(R.drawable.hospital).into(image);



    }

    private void initListner() {

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setTypeFace() {
        Typeface type = Typeface.createFromAsset(getAssets(),"Quicksand-Bold.otf");

        actionBarTiltle.setTypeface(type);


    }
}
