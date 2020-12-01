package com.example.fitnessdemo.ZFT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fitnessdemo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class PlanActivity extends AppCompatActivity {
    private Map<String, TextView> textViewMap = new HashMap<>();
    private Map<String, View> vView = new HashMap<>();
    private Bundle bundle = new Bundle();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.zft_activity_plan);
        initViews();
    }
    private void initViews() {

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= findViewById(R.id.zft_viewPager);
        myFragmentPagerAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);
    }
}
