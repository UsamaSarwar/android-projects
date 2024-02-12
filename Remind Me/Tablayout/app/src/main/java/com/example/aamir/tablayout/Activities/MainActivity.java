package com.example.aamir.tablayout.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.aamir.tablayout.Fragments.ActiveReminderList;
import com.example.aamir.tablayout.Fragments.DeActiveReminderList;
import com.example.aamir.tablayout.R;
import com.example.aamir.tablayout.Adapters.ViewPagerAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ActiveReminderList(),"");
        viewPagerAdapter.addFragment(new DeActiveReminderList(),"");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.Setting){
            startActivity(new Intent(MainActivity.this,PreferenceActivity.class));
        }
        if(item.getItemId() == R.id.About){
            startActivity(new Intent(MainActivity.this,AboutUs.class));
        }

        if(item.getItemId() == R.id.Share){
            share();
        }
        if(item.getItemId() == R.id.Option_mic){
            startActivity(new Intent(MainActivity.this,ApiActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void share() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = getString(R.string.app_name)+"\nLet me recommend you this application\n\n";
            sAux = sAux + getString(R.string.app_url)+" \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose one"));
        } catch(Exception e) {
            //e.toString();
        }

    }

    int []tabIcons = {R.drawable.ic_notifications_white_24dp,R.drawable.ic_notifications_off_white_24dp};
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


}
