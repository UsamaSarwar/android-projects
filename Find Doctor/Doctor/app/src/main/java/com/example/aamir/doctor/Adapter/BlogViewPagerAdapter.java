package com.example.aamir.doctor.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Aamir on 8/21/2017.
 */

public class BlogViewPagerAdapter extends FragmentPagerAdapter {


    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> tabNameList = new ArrayList<>();

    public BlogViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addBlogFragment(Fragment fragment, String tabName) {
        this.fragmentArrayList.add(fragment);
        this.tabNameList.add(tabName);
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
