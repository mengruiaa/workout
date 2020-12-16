package com.example.fitnessdemo.ZFT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
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

public class ExerciseActivity extends AppCompatActivity {
    private PopupWindow mPopWindow;
    private boolean flag = false;
    private Motion motion;
    private List<Motion> motions;
    private int total=1;
    private ImageView ivMotion;
    private TextView tvMotionName;
    private TextView tvHard;
    private TextView tvPart;
    private TextView tvInst;
    private TextView tvDesc;
    private TextView tvTime;
    private ImageView ivNext;
    private ImageView ivPre;
    private ImageView ivStart;
    private int n;
    private CountDownTimer countDownTimer;
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

                    break;
                case 2:
                    String obj = (String)msg.obj;
                    Gson g = new GsonBuilder().serializeNulls().setDateFormat("YY:MM:dd").create();
                    MotionInfo motionInfo = g.fromJson(obj,MotionInfo.class);
                    tvPart.setText("部位: " + motionInfo.getMotionPart());
                    tvInst.setText("器械:" + motionInfo.getMotionInst());
                    tvDesc.setText(motionInfo.getMotionDesc());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.zft_activity_exercise);
        Intent intent = getIntent();
        motion = (Motion) intent.getSerializableExtra("motion");
        GetMotions(ConfigUtil.SERVER_HOME + "GetMotions" + "?planName=" + motion.getPlanName());
        ivMotion = findViewById(R.id.iv_motionInfoImg);
        tvMotionName = findViewById(R.id.tv_motionInfoName);
        tvHard = findViewById(R.id.tv_hard);
        tvPart = findViewById(R.id.tv_part);
        tvInst = findViewById(R.id.tv_inst);
        tvDesc = findViewById(R.id.tv_zft_desc);
        ivNext = findViewById(R.id.iv_next);
        ivStart = findViewById(R.id.zft_start_stop);
        ivPre = findViewById(R.id.iv_pre);
        tvTime = findViewById(R.id.zft_tv_time);
        Glide.with(this).asGif().load(ConfigUtil.SERVER_HOME + motion.getMotionImg()).into(ivMotion);
        tvMotionName.setText(motion.getMotionName());
        tvHard.setText(tvHard.getText().toString() + motion.getMotionStar());
        n = motion.getMotionTime();
        countDownTimer = new CountDownTimer(n*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String value = String.valueOf((int) (millisUntilFinished / 1000)+1);
                tvTime.setText("第"+ total + "组" + value + "秒");
                n--;
            }

            @Override
            public void onFinish() {
                String finish = "no";
                int position = 0;
                for (int i = 0;i<motions.size();i++){
                    if (motions.get(i).getMotionName().equals(motion.getMotionName())){
                        position = i;
                        break;
                    }
                }
                if ((position == motions.size() - 1) && total == motion.getMotionCount()){
                    Toast.makeText(getApplicationContext(),"去打卡吧",Toast.LENGTH_SHORT).show();
                    showPopupWindow();
                    finish = "yes";
//                    finish();
                }
                final int finalPosition = position;
                if (total < motion.getMotionCount()){
                    motion = motions.get(finalPosition);
                    total++;
                }else if(total == motion.getMotionCount() && (position != motions.size() - 1)){
                    motion = motions.get(finalPosition+1);
                    total = 1;
                }

                n = motion.getMotionTime();
                Glide.with(getApplicationContext()).asGif().load(ConfigUtil.SERVER_HOME + motion.getMotionImg()).into(ivMotion);
                tvMotionName.setText(motion.getMotionName());
                tvHard.setText("难度: " + motion.getMotionStar());
//                countDownTimer.start();
                if (finish.equals("yes")){
                    countDownTimer.cancel();
                }else {
                    countDownTimer.start();
                }
            }
        };
        countDownTimer.start();
        ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag){
                    ivStart.setImageResource(R.drawable.play);
                    countDownTimer.cancel();
                    flag = true;
                }else if (flag){
                    ivStart.setImageResource(R.drawable.stop);
                    countDownTimer = new CountDownTimer(n*1000,1000) {
                        @Override
                        public void onTick(long l) {
                            String value = String.valueOf((int) (l / 1000)+1);
                            tvTime.setText("第"+ total + "组" + value + "秒");
                            n--;
                        }

                        @Override
                        public void onFinish() {

                        }
                    };
                    countDownTimer.start();
                    flag = false;
                }
            }
        });
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = false;
                ivStart.setImageResource(R.drawable.stop);
                countDownTimer.cancel();
                countDownTimer = null;
                int position = 0;
                for (int i = 0; i < motions.size();i++){
                    if (motions.get(i).getMotionName().equals(motion.getMotionName())){
                        position = i;
                        break;
                    }
                }
                if ((position == 0) && total == 1){
                    finish();
                }
                final int finalPosition = position;
                if (1 == total && position != 0){
                    motion = motions.get(finalPosition-1);
                    total = motions.get(finalPosition-1).getMotionCount();
                }else if(total > 1){
                    motion = motions.get(finalPosition);
                    total--;
                }
                n = motion.getMotionTime();
                Glide.with(getApplicationContext()).asGif().load(ConfigUtil.SERVER_HOME + motion.getMotionImg()).into(ivMotion);
                tvMotionName.setText(motion.getMotionName());
                tvHard.setText("难度: " + motion.getMotionStar());
                GetMotionInfo(ConfigUtil.SERVER_HOME + "GetMotionInfo" + "?motionName=" + motion.getMotionName());
                countDownTimer = new CountDownTimer(30*1000,1000) {
                    @Override
                    public void onTick(long l) {
                        String value = String.valueOf((int) (l / 1000)+1);
                        tvTime.setText("第"+ total + "组" + value + "秒");
                        n--;
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                countDownTimer = new CountDownTimer(n*1000,1000) {
                    @Override
                    public void onTick(long l) {
                        String value = String.valueOf((int) (l / 1000)+1);
                        tvTime.setText("第"+ total + "组" + value + "秒");
                        n--;
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                countDownTimer.start();
            }
        });
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finish = "no";
                flag = false;
                ivStart.setImageResource(R.drawable.stop);
                countDownTimer.cancel();
                countDownTimer = null;
                int position = 0;
                for (int i = 0;i<motions.size();i++){
                    if (motions.get(i).getMotionName().equals(motion.getMotionName())){
                        position = i;
                        break;
                    }
                }
                if ((position == motions.size() - 1) && total == motion.getMotionCount()){
                    Toast.makeText(getApplicationContext(),"去打卡吧",Toast.LENGTH_SHORT).show();
                    showPopupWindow();
                    finish = "yes";
//                    finish();
                }
                final int finalPosition = position;
                if (total < motion.getMotionCount()){
                    motion = motions.get(finalPosition);
                    total++;
                }else if(total == motion.getMotionCount() && (position != motions.size() - 1)){
                    motion = motions.get(finalPosition+1);
                    total = 1;
                }
                n = motion.getMotionTime();
                Glide.with(getApplicationContext()).asGif().load(ConfigUtil.SERVER_HOME + motion.getMotionImg()).into(ivMotion);
                tvMotionName.setText(motion.getMotionName());
                tvHard.setText("难度: " + motion.getMotionStar());
                GetMotionInfo(ConfigUtil.SERVER_HOME + "GetMotionInfo" + "?motionName=" + motion.getMotionName());

                countDownTimer = new CountDownTimer(n*1000,1000) {
                    @Override
                    public void onTick(long l) {
                        String value = String.valueOf((int) (l / 1000)+1);
                        tvTime.setText("第"+ total + "组" + value + "秒");
                        n--;
                    }

                    @Override
                    public void onFinish() {

                    }
                };
//                countDownTimer.start();
                if (finish.equals("yes")){
                    countDownTimer.cancel();
                }else {
                    countDownTimer.start();
                }
            }
        });
        GetMotionInfo(ConfigUtil.SERVER_HOME + "GetMotionInfo" + "?motionName=" + motion.getMotionName());
    }

    private void GetMotionInfo(final String s) {
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
                    msg.what = 2;
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
    private void showPopupWindow() {
//        countDownTimer.cancel();
        View contentView = LayoutInflater.from(ExerciseActivity.this).inflate(R.layout.zft_popuplayout, null);
        Button btn = contentView.findViewById(R.id.btn_yes);

        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setContentView(contentView);
        TextView tv_more_skill = contentView.findViewById(R.id.tv_more_skill);
        View rootview = LayoutInflater.from(ExerciseActivity.this).inflate(R.layout.zft_activity_exercise, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
                MotionActivity.button.setVisibility(View.VISIBLE);
                finish();
            }
        });
    }

}
