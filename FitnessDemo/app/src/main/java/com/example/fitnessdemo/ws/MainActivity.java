package com.example.fitnessdemo.ws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fitnessdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_main);
    }
}
