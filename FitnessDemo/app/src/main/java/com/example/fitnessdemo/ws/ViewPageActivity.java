package com.example.fitnessdemo.ws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.fitnessdemo.R;

public class ViewPageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout llindicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_viewpage);
        viewPager = findViewById(R.id.viewPager);
        llindicator = findViewById(R.id.ll_indicator);
    }
}
