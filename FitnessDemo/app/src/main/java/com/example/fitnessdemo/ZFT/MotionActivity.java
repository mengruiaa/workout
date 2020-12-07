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
import android.widget.TextView;

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

public class MotionActivity extends AppCompatActivity {
    private TextView tvHead;
    private TextView tvDesc;
    private Button btnStarMotion;
    private List<Motion> motions;
    private MotionAdapter motionAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://表示接收服务端的字符串
                    Gson gson = new GsonBuilder().serializeNulls().setDateFormat("YY:MM:dd").create();
                    String res = (String)msg.obj;
                    Log.i("gson", "handleMessage: " + res);
                    Type type = new TypeToken<List<Motion>>(){}.getType();
                    motions = gson.fromJson(res,type);
                    tvHead.setText(motions.get(1).getPlanHead());
                    tvDesc.setText(motions.get(1).getMotionDesc());
                    RecyclerView recyclerView = findViewById(R.id.zft_motion_recycler_view);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MotionActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    motionAdapter = new MotionAdapter(motions);
                    recyclerView.setAdapter(motionAdapter);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.zft_activity_motion);
        tvHead = findViewById(R.id.tv_motionHead);
        tvDesc = findViewById(R.id.tv_planDesc);
        btnStarMotion = findViewById(R.id.btn_startMotion);
        Intent intent = getIntent();
        Plan plan = (Plan)intent.getSerializableExtra("plan");
        GetMotions(ConfigUtil.SERVER_HOME + "GetMotions" + "?planName=" + plan.getPlanName());
        btnStarMotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MotionActivity.this,ExerciseActivity.class);
                intent.putExtra("motion",motions.get(0));
                startActivity(intent);
            }
        });
    }

    private void GetMotions(final String s) {
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
