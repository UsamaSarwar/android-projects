package com.example.aamir.doctor.Activities;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aamir.doctor.Adapter.BlogViewPagerAdapter;
import com.example.aamir.doctor.Adapter.ViewPagerAdapter;
import com.example.aamir.doctor.Fragment.*;
import com.example.aamir.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Blog extends AppCompatActivity {

    @BindView(R.id.tabLayout_blog)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_blog)
    ViewPager viewPager;
    @BindView(R.id.back_blog)
    ImageView back;
    @BindView(R.id.heading)
    TextView actionBarHeading;

    BlogViewPagerAdapter blogViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"Quicksand-Bold.otf");
        actionBarHeading.setTypeface(typeface);

        blogViewPagerAdapter = new BlogViewPagerAdapter(getSupportFragmentManager());
        blogViewPagerAdapter.addBlogFragment(new LatestBlog(),"Latest");
        blogViewPagerAdapter.addBlogFragment(new AllBlog(),"See All");
        viewPager.setAdapter(blogViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
