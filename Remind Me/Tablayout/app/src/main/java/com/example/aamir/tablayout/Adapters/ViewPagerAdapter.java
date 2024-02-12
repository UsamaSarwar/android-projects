package com.example.aamir.tablayout.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.aamir.tablayout.R;

import java.util.ArrayList;

/**
 * Created by Aamir on 8/3/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> tabNameList = new ArrayList<>();


    public void addFragment(Fragment fragment, String tabName){
        this.fragmentArrayList.add(fragment);
        this.tabNameList.add(tabName);
    }
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNameList.get(position);
    }

}
