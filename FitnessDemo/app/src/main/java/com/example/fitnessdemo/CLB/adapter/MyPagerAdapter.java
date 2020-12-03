package com.example.fitnessdemo.CLB.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * ViewPager要使用的Adapter
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> datas;
    public void setData(ArrayList<Fragment> datas){
        this.datas = datas;
    }
    public MyPagerAdapter(FragmentManager fm){
        super(fm);

    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return datas == null? null : datas.get(position);
    }

    @Override
    public int getCount() {
        return datas == null? 0 : datas.size();
    }
}
