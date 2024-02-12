package com.example.aamir.doctor.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.aamir.doctor.Adapter.ViewPagerAdapter;
import com.example.aamir.doctor.Fragment.*;
import com.example.aamir.doctor.Fragment.Login;
import com.example.aamir.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginSignup extends AppCompatActivity {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        ButterKnife.bind(this);



        //prevent to auto open keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Login(),"Login");
        viewPagerAdapter.addFragment(new SignUp(),"Sign up");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginSignup.this,MainActivity.class));
    }
}
