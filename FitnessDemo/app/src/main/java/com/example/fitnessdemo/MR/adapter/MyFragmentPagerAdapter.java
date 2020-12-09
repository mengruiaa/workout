package com.example.fitnessdemo.MR.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<String> mTitles=new ArrayList<>();
    private ArrayList<Fragment> fs=new ArrayList<>();

    public List<String> getmTitles() {
        return mTitles;
    }

    public void setmTitles(List<String> mTitles) {
        this.mTitles = mTitles;
    }

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
//        Bundle bundle = new Bundle();
//        bundle.putString("tab",mTitles.get(position));
//        fs.get(position).setArguments(bundle);
        return fs.get(position);

    }

    @Override
    public int getCount() {
        return fs.size();
    }
    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}
