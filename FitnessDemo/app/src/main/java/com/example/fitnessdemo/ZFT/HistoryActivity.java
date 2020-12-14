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

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.entity.History;
import com.example.fitnessdemo.Manifest;
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

public class HistoryActivity extends AppCompatActivity {
    private ImageView ivDiary;
    private TextView tvDiary;
    private Button btnBack;
    private Button btnGotoPlan;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://表示接收服务端的字符串
                    Gson gson = new GsonBuilder().serializeNulls().setDateFormat("YY:MM:dd").create();
                    String res = (String)msg.obj;
                    if (res.equals("null")){
                        Log.i("myHistory", "null");
                    }else{
                        ivDiary.setVisibility(View.GONE);
                        tvDiary.setVisibility(View.GONE);
                        btnGotoPlan.setVisibility(View.GONE);
                        Type type = new TypeToken<List<MyHistory>>(){}.getType();
                        List<MyHistory> histories = gson.fromJson(res,type);
                        RecyclerView recyclerView = findViewById(R.id.rl_userDiary);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(HistoryActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        HistoryAdapter adapter = new HistoryAdapter(histories);
                        recyclerView.setAdapter(adapter);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.zft_activity_history);
        ivDiary = findViewById(R.id.iv_diary);
        tvDiary = findViewById(R.id.tv_zft_diary);
        btnGotoPlan = findViewById(R.id.btn_zft_gotoPlan);
        btnBack = findViewById(R.id.btn_zft_history_back);
        getHistory(ConfigUtil.SERVER_HOME + "GetHistory" + "?user_phone=" + ConfigUtil.user_Name);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnGotoPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(HistoryActivity.this,UserPlanActivity.class);
                intent.putExtra("userName" , ConfigUtil.user_Name);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getHistory(final String s) {
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
