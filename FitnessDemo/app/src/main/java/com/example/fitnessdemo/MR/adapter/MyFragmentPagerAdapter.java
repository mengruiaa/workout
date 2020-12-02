package com.example.fitnessdemo.MR.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fs=new ArrayList<>();

    public ArrayList<Fragment> getFs() {
        return fs;
    }

    public void setFs(ArrayList<Fragment> fs) {
        this.fs = fs;
    }

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fs.get(position);

    }

    @Override
    public int getCount() {
        return fs.size();
    }

}
