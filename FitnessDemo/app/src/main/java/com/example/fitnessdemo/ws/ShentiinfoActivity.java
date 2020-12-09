package com.example.fitnessdemo.ws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessdemo.R;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;

public class ShentiinfoActivity extends AppCompatActivity {
    private ImageView imgback;
    private ImageView imginfo;
    private TextView tvweight;
    private TextView tvheight;
    private TextView tvbmi;
    private TextView tvxing;


    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();
    //定义Handler对象属性
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_shentiinfo);

        findview();
        final Intent intent = getIntent();
        String str = intent.getStringExtra("info");
        User user = new Gson().fromJson(str, User.class);
        System.out.println(user.toString());
        tvweight.setText(user.getWeight()+"");
        tvheight.setText(user.getHeight()+"");
        int bmi = user.getWeight()/(user.getHeight()*user.getHeight()/10000);
        System.out.println("BMI:"+bmi);
        tvbmi.setText(bmi+"");
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("return","您的信息我以成功收到!!");
                setResult(22,intent);
                finish();
            }
        });
        imginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void findview() {
        imgback = findViewById(R.id.iv_back);
        imginfo = findViewById(R.id.img_infojia);
        tvweight =findViewById(R.id.shengti_weight);
        tvheight = findViewById(R.id.shengti_height);
        tvbmi = findViewById(R.id.shengti_bmi);
        tvxing  = findViewById(R.id.shengti_tizhi);
    }
}
