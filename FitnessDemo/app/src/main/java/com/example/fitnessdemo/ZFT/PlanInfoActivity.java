package com.example.fitnessdemo.ZFT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class PlanInfoActivity extends AppCompatActivity {
    private Plan plan;
    private ImageView ivPlanInfoImg;
    private TextView tvPlanInfo;
    private TextView tvPlanPeople;
    private TextView tvPlanTime;
    private Button btnStart;
    private Button btnAdd;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://表示接收服务端的字符串
                    String res = (String)msg.obj;
                    if (res.equals("had")){
                        Toast.makeText(PlanInfoActivity.this,"you have added",Toast.LENGTH_SHORT).show();
                    }else if (res.equals("successfully")){
                        Toast.makeText(PlanInfoActivity.this,"added successfully",Toast.LENGTH_SHORT).show();
                    }else if (res.equals("failed")){
                        Toast.makeText(PlanInfoActivity.this,"added failed",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.zft_activity_plan_info);
        Intent intent = getIntent();
        plan = (Plan) intent.getSerializableExtra("plan");
        findViews();
        setListener();
        String planInfoImg = plan.getPlaninfoImg();
        String planInfo = plan.getPlaninfo();
        String planPeople = plan.getPlanPeople();
        String planTime = plan.getPlanTime();
        Glide.with(this).load(ConfigUtil.SERVER_HOME + planInfoImg).into(ivPlanInfoImg);
        tvPlanInfo.setText(planInfo);
        tvPlanPeople.setText(tvPlanPeople.getText().toString() + planPeople);
        tvPlanTime.setText(tvPlanTime.getText().toString() + planTime);
    }

    private void setListener() {
        MyListener myListener = new MyListener();
        btnStart.setOnClickListener(myListener);
        btnAdd.setOnClickListener(myListener);
    }

    private void findViews() {
        ivPlanInfoImg = findViewById(R.id.iv_planInfo_img);
        tvPlanInfo = findViewById(R.id.tv_planInfo);
        tvPlanPeople = findViewById(R.id.tv_peopleWhoCan);
        tvPlanTime = findViewById(R.id.tv_planTime);
        btnStart = findViewById(R.id.btn_startPlan);
        btnAdd = findViewById(R.id.btn_addPlan);
    }
    class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_startPlan:
                    Intent intent = new Intent();
                    intent.setClass(PlanInfoActivity.this,MotionActivity.class);
                    intent.putExtra("plan",plan);
                    startActivity(intent);
                    break;
                case R.id.btn_addPlan:
                    addMyPlan(ConfigUtil.SERVER_HOME + "AddPlan" + "?user_phone=" + ConfigUtil.user_Name + "&plan_name=" + plan.getPlanName());
                    break;
            }
        }
    }

    private void addMyPlan(final String s) {
        new Thread(){
            @Override
            public void run() {
                //使用网络连接，接收服务端发送的字符串
                try {
                    //创建URL对象
                    URL url = new URL(s);
                    //获取URLConnection连接对象
                    URLConnection conn = url.openConnection();
                    //获取网络输入流
                    InputStream in = conn.getInputStream();
                    //使用字符流读取
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取字符信息
                    String str = reader.readLine();
                    //关闭流
                    reader.close();
                    in.close();
                    //借助于Message，把收到的字符串显示在页面上
                    //创建Message对象
                    Message msg = new Message();
                    //设置Message对象的参数
                    msg.what = 1;
                    msg.obj = str;
                    //发送Message
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
