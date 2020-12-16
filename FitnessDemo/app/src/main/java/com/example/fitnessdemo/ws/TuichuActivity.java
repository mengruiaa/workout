package com.example.fitnessdemo.ws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessdemo.R;

public class TuichuActivity extends AppCompatActivity {
    private ImageView back;
    private RelativeLayout tuichu;
    private TextView zhuxiao;
    private static final long DELAY_TIME = 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_tuichu);

        back = findViewById(R.id.iv_backtuichu);
        tuichu =findViewById(R.id.tuichu);
        zhuxiao = findViewById(R.id.tuichuzhuxiao);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                setResult(4,intent);
                finish();
            }
        });

        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(TuichuActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TuichuActivity.this,"注销成功",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(TuichuActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                },DELAY_TIME);
            }
        });
    }
}
