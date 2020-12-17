package com.example.fitnessdemo.LZYZYH.Oneweek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.LZYZYH.activity.DetailActivity;
import com.example.fitnessdemo.R;

public class OneWeek1 extends AppCompatActivity {
    private ImageView fanhui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_oneweek1);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        fanhui = findViewById(R.id.img_fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OneWeek1.this.finish();
            }
        });
    }
}
