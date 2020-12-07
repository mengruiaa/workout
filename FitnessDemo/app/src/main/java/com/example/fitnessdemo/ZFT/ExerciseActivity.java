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
import android.widget.ImageView;
import android.widget.TextView;

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

public class ExerciseActivity extends AppCompatActivity {
    private Motion motion;
    private List<Motion> motions;
    private int total=1;
    private ImageView ivMotion;
    private TextView tvMotionName;
    private TextView tvHard;
    private TextView tvPart;
    private TextView tvInst;
    private ImageView ivNext;
    private ImageView ivStart;
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

                    ivNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = 0;
                            for (int i = 0;i<motions.size();i++){
                                if (motions.get(i).getMotionName().equals(motion.getMotionName())){
                                    position = i;
                                    break;
                                }
                            }
                            final int finalPosition = position;
                            if (total < motion.getMotionCount()){
                                motion = motions.get(finalPosition);
                                total++;
                            }else if(total == motion.getMotionCount()){
                                motion = motions.get(finalPosition+1);
                                total = 1;
                            }
                            Glide.with(getApplicationContext()).asGif().load(ConfigUtil.SERVER_HOME + motion.getMotionImg()).into(ivMotion);
                            tvMotionName.setText(motion.getMotionName());
                            tvHard.setText("难度: " + motion.getMotionStar());
                        }
                    });
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zft_activity_exercise);
        Intent intent = getIntent();
        motion = (Motion) intent.getSerializableExtra("motion");
        GetMotions(ConfigUtil.SERVER_HOME + "GetMotions" + "?planName=" + motion.getPlanName());
        ivMotion = findViewById(R.id.iv_motionInfoImg);
        tvMotionName = findViewById(R.id.tv_motionInfoName);
        tvHard = findViewById(R.id.tv_hard);
        tvPart = findViewById(R.id.tv_part);
        tvInst = findViewById(R.id.tv_inst);
        ivNext = findViewById(R.id.iv_next);
        ivStart = findViewById(R.id.zft_start_stop);
        Glide.with(this).asGif().load(ConfigUtil.SERVER_HOME + motion.getMotionImg()).into(ivMotion);
        tvMotionName.setText(motion.getMotionName());
        tvHard.setText(tvHard.getText().toString() + motion.getMotionStar());
        ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivStart.setImageResource(R.drawable.stop);
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
