package com.example.fitnessdemo.CLB;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.R;

public class ClockActivity extends AppCompatActivity {
//    private int timeUsedInSec =350000;
    //自定义PopupWindow
    private PopupWindow canclePop;
    private int timeUsedInSec = 0;
    private boolean paused  =false;
    private String timeUsed ;
    private TextView timeText;
    private Button btnStop;
    private Button btnBegin;
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
    /**
     * 点击弹框
     *
     * @param
     */
    public void showPop() {
        View contentView = null;
        //设置contentView
        if (canclePop == null) {
            contentView = LayoutInflater.from(ClockActivity.this).inflate(R.layout.clb_pop_clock, null);
            canclePop = new PopupWindow(contentView, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT, true);
            canclePop.setFocusable(true);
            canclePop.setOutsideTouchable(false);
            canclePop.setClippingEnabled(false);
        } else {
            contentView = canclePop.getContentView();
        }
//        TextView tvBackHome = contentView.findViewById(R.id.tv_back_home);
//        TextView tvCancle = contentView.findViewById(R.id.tv_cancle);
//
//        tvBackHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //点击返回的逻辑
//                canclePop.dismiss();
//            }
//        });
//        tvCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                canclePop.dismiss();
//            }
//        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(ClockActivity.this).inflate(R.layout.activity_clock, null);
        canclePop.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        timeText = findViewById(R.id.timeText);
        btnStop = findViewById(R.id.btn_stop);
        btnBegin = findViewById(R.id.btn_begin);

//        uiHandler.sendMessage(message);
        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = uiHandler.obtainMessage();
                message.what = 1;
                uiHandler.sendMessage(message);
                showPop();

            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiHandler.removeMessages(1);
            }
        });


    }
}