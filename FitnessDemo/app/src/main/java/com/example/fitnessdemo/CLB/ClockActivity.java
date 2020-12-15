package com.example.fitnessdemo.CLB;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.R;

public class ClockActivity extends AppCompatActivity {
    //自定义PopupWindow
    private PopupWindow popupWindow;
    private int timeUsedInSec = 0;
    private boolean paused  =false;
    private String timeUsed ;
    private TextView timeText1;
    private TextView beginRun;
    private Message message;
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
    private void updateClockUI() {
        timeText1.setText(timeUsed);
    }
    public void addTimeUsed() {
        timeUsedInSec=timeUsedInSec+1;
        //+":"+this.getMis()
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
    /**
     * 点击弹框
     *
     * @param
     */
    public void showPop() {
        View contentView = null;
        //设置contentView
        if (popupWindow == null) {
            contentView = LayoutInflater.from(ClockActivity.this).inflate(R.layout.clb_pop_clock, null);
            popupWindow = new PopupWindow(contentView, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT, true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setClippingEnabled(false);
        } else {
            contentView = popupWindow.getContentView();
        }
        //暂停所在的布局
        final LinearLayout llzanting = contentView.findViewById(R.id.clb_ll_zanting);
        //开始结束所在的布局
        final LinearLayout llthis = contentView.findViewById(R.id.clb_ll_this);
        ImageView ivZanTing = contentView.findViewById(R.id.clb_iv_zanting);
        ImageView ivStop = contentView.findViewById(R.id.pop_iv_stop);
        ImageView ivBegin = contentView.findViewById(R.id.pop_iv_begin);
        timeText1 = contentView.findViewById(R.id.timeText1);
        ivZanTing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiHandler.removeMessages(1);
                llzanting.setVisibility(View.GONE);
                llthis.setVisibility(View.VISIBLE);
            }
        });
        ivBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = uiHandler.obtainMessage();
                message.what = 1;
                uiHandler.sendMessage(message);
                llzanting.setVisibility(View.VISIBLE);
                llthis.setVisibility(View.GONE);

            }
        });
        ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeUsedInSec = 0;
                uiHandler.removeMessages(1);
                //更新数据
                timeText1.setText("00:00:00:00");
                popupWindow.dismiss();

            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(ClockActivity.this).inflate(R.layout.activity_clock, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
//        popupWindow.setElevation(1000f);//我将动画位置设置为1000f
//        popupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock1);
        beginRun = findViewById(R.id.clb_tv_begin_run);
        //开始跑步
        beginRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
                message = uiHandler.obtainMessage();
                message.what = 1;
                uiHandler.sendMessage(message);
            }
        });



    }
}
