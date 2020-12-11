package com.example.fitnessdemo.LZYZYH.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.R;

public class PubuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_product_detail2);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

    }
}

