package com.example.fitnessdemo.MR.TheFour;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.CLB.EncyclopaediaActivity;
import com.example.fitnessdemo.R;
import com.example.fitnessdemo.ZFT.PlanActivity;


public class ShowYe extends Fragment {
    private View root;
//    private Button btnLook;
//    private Button btnKonw;
//    private String userName;
    //健身百科对象
    private LinearLayout llClock1;
    //推荐计划对象
    private LinearLayout llClock2;
    //我的计划对象
    private LinearLayout llClock3;
    private int timeUsedInSec = 0;
    private boolean paused  =false;
    private String timeUsed ;
    private TextView timeText;
    private ImageView ivStop;
    private ImageView ivBegin;
    private Message message;
    private int condition = 0;
    private Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {   //what的值为1而且paused为false时将Message送主线程
                case 1:
                    if (!paused)
                    {
                        addTimeUsed();    //计时函数
                        updateClockUI();  //更新UI线程的数据
                    }
                    //uiHandler.sendEmptyMessageDelayed(1, 1000);
                    uiHandler.sendEmptyMessageDelayed(1, 1);//延时1毫秒后给what传1
                    break;
                default:
                    break;
            }
        }

    };
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_clock, container, false);
        llClock1 = root.findViewById(R.id.ll_clock_1);
        llClock2 = root.findViewById(R.id.ll_clock_2);
        llClock3 = root.findViewById(R.id.ll_clock_3);
        //跳转健身百科
        llClock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(root.getContext(), EncyclopaediaActivity.class);
                startActivity(intent);
            }
        });
        //跳转我的计划
        llClock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(root.getContext(), PlanActivity.class);
                startActivity(intent);
            }
        });
//        btnLook = root.findViewById(R.id.btn_lookPlan);
//        btnKonw = root.findViewById(R.id.btn_konw);
//        btnKonw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(root.getContext(), EncyclopaediaActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnLook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(root.getContext(), PlanActivity.class);
//                startActivity(intent);
//            }
//        });
        timeText = root.findViewById(R.id.timeText);
        ivStop = root.findViewById(R.id.iv_pp_stop);
        ivBegin = root.findViewById(R.id.iv_pp_begin);

//        uiHandler.sendMessage(message);
        ivBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = uiHandler.obtainMessage();
                message.what = 1;
                if(condition ==0){
                    //开始计时
                    uiHandler.sendMessage(message);
                    ivBegin.setBackground(getResources().getDrawable(R.drawable.clb_zanting2));
                    condition = 1;

                }else{
                    //结束计时
                    uiHandler.removeMessages(1);
                    ivBegin.setBackground(getResources().getDrawable(R.drawable.clb_begin4));
                    condition = 0;
                }

//                showPop();

            }
        });
        ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeUsedInSec = 0;
                uiHandler.removeMessages(1);
                ivBegin.setBackground(getResources().getDrawable(R.drawable.clb_begin4));
                //更新数据
                timeText.setText("00:00:00:00");
            }
        });
        return root;
    }
    private void updateClockUI() {
//        minText.setText(getMin()+":");
//        secText.setText(getSec());
//        misText.setText(getMis());
        timeText.setText(timeUsed);
    }
    public void addTimeUsed() {
        timeUsedInSec=timeUsedInSec+1;
        timeUsed = this.getHour()+":"+this.getMin() + ":" + this.getSec()+":"+this.getMis();
    }
    public CharSequence getHour(){
        int hour = timeUsedInSec / (6000*60);
        return hour <10 ? "0"+hour : String.valueOf(hour);
    }
    public CharSequence getMin() {
//        int min = timeUsedInSec / 6000;
        int min = (timeUsedInSec / 6000) % 60;
//        return String.valueOf(timeUsedInSec / 6000);
        return min < 10 ? "0"+ min : String.valueOf(min);
    }

    public CharSequence getSec() {
        int sec = (timeUsedInSec/100) % 60;
        return sec < 10 ? "0" + sec : String.valueOf(sec);
    }
    public CharSequence getMis(){
        int mis=timeUsedInSec;
        mis=mis%100;
        return mis < 10 ? "0" + mis : String.valueOf(mis);
    }
}
