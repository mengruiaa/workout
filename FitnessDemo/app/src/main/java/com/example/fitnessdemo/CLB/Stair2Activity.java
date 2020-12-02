package com.example.fitnessdemo.CLB;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnessdemo.CLB.fragment.FirstFragment;
import com.example.fitnessdemo.CLB.fragment.SecondFragment;
import com.example.fitnessdemo.CLB.fragment.ThirdFragment;
import com.example.fitnessdemo.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class Stair2Activity extends AppCompatActivity {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager slViewPage;
    private ArrayList<Fragment> slFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clb_activity_stair2);
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        String attentionNumber = intent.getStringExtra("attentionNumber");
        System.out.println(itemName);
        System.out.println(attentionNumber);
        slidingTabLayout =findViewById(R.id.sl);
        slViewPage=findViewById(R.id.vp00);
        slFragments = new ArrayList<>();
        slFragments.add(new FirstFragment());
        slFragments.add(new SecondFragment());
        slFragments.add(new ThirdFragment());
        //     无需编写适配器，一行代码关联TabLayout与ViewPager（看这里看这里）
        slidingTabLayout.setViewPager(slViewPage, new String[]{"官方必读", "免费课程","你好啊"}, this, slFragments);
    }
}
