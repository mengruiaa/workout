package com.example.fitnessdemo.MR.TheFour;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.CLB.EncyclopaediaActivity;
import com.example.fitnessdemo.R;
import com.example.fitnessdemo.ZFT.AlarmReceiver;
import com.example.fitnessdemo.ZFT.PlanActivity;
import com.example.fitnessdemo.ZFT.RecoActivity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ShowYe extends Fragment {
    //闹钟设置
    private Button btn;									// 申明设置时钟按钮
    private ToggleButton btn_enClk;						// 申明开启\关闭按钮
    private ToggleButton togbtn_AlarmStyle;

    private SharedPreferences sharedData;
    SharedPreferences.Editor edit;
    private static boolean alarmStyle = true;			// 闹钟提示方式 (true:铃声;false:振动)

    Calendar c = Calendar.getInstance();

    final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public static ShowYe getInstance() {
        return getInstance();
    };
    static String shakeSenseValue;

    public static void setAlarmStyle(boolean style)
    {
        alarmStyle = style;
    }

    public static boolean getAlarmStyle()
    {
        return alarmStyle;
    }

    private void loadData()
    {
        sharedData = getActivity().getSharedPreferences("main_activity", Context.MODE_PRIVATE);
        edit = sharedData.edit();
        btn.setText(sharedData.getString("time",
                sdf.format(new Date(c.getTimeInMillis()))));
        btn_enClk.setChecked(sharedData.getBoolean("on_off", false));
    }

    private void saveData()
    {
        edit.putString("time", btn.getText().toString());
        edit.putBoolean("on_off", btn_enClk.isChecked());
        edit.commit();
    }

    private View root;
//    private Button btnLook;
//    private Button btnKonw;
//    private String userName;
    //健身百科对象
    private RelativeLayout llClock1;
    //推荐计划对象
    private RelativeLayout llClock2;
    //我的计划对象
    private RelativeLayout llClock3;
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
        //跳转推荐计划
        llClock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(root.getContext(), RecoActivity.class);
                startActivity(intent);
            }
        });
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


        shakeSenseValue = getResources().getString(R.string.shakeSenseValue_2);
        String timeOnBtn = "";

        timeOnBtn = sdf.format(new Date(c.getTimeInMillis()));

        ButtonListener buttonListener = new ButtonListener();	// 注册设置时间按钮监听事件
        btn = (Button)root.findViewById(R.id.btn_setClock);
        btn.setText(timeOnBtn);
        btn.setOnClickListener(buttonListener);

        btn_enClk = (ToggleButton) root.findViewById(R.id.btn_enClk); // 注册开启关闭按钮监听事件
        btn_enClk.setOnClickListener(buttonListener);

        loadData();
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

    class ButtonListener implements View.OnClickListener
    {
        private TimePicker timePicker;			// 申明时间控件

        private PendingIntent pi;
        private Intent intent;
        AlarmManager alarmManager;
        LayoutInflater inflater;
        LinearLayout setAlarmLayout;

        /**
         * 在ButtonListener构造方法中加载对话框的布局
         */
        public ButtonListener()
        {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);		// 用于加载alertdialog布局
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            setAlarmLayout = (LinearLayout) inflater.inflate(
                    R.layout.alarm_dialog, null);
        }

        private void enableClk()
        {
            timePicker = (TimePicker) setAlarmLayout
                    .findViewById(R.id.timepicker);
            c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());        // 设置闹钟小时数
            c.set(Calendar.MINUTE, timePicker.getCurrentMinute());            // 设置闹钟的分钟数
            c.set(Calendar.SECOND, 0); // 设置闹钟的秒数
            c.set(Calendar.MILLISECOND, 0); // 设置闹钟的毫秒数

            // if (c.getTimeInMillis() - System.currentTimeMillis() < 0)
            // {
            // c.roll(Calendar.DATE, 1);
            // }

            btn.setText(sdf.format(new Date(c.getTimeInMillis())));
            intent = new Intent(getActivity(), AlarmReceiver.class);    // 创建Intent对象
            Log.i("time", "enableClk: " + "闹钟");
            pi = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);    // 创建PendingIntent
            Log.i("time", "enableClk: " + "闹钟2");

            alarmManager.setRepeating(AlarmManager.RTC,    // 设置闹钟，当前时间就唤醒
                    c.getTimeInMillis(), 24 * 60 * 60 * 1000, pi);
        }

        private void disableClk()
        {
            alarmManager.cancel(pi);
        }

        @Override
        public void onClick(View v)
        {

            switch (v.getId())
            {
                case R.id.btn_setClock:

                    setAlarmLayout = (LinearLayout) inflater.inflate(
                            R.layout.alarm_dialog, null);

                    togbtn_AlarmStyle = (ToggleButton) setAlarmLayout
                            .findViewById(R.id.togbtn_alarm_style);
                    togbtn_AlarmStyle.setChecked(sharedData.getBoolean("style",
                            false));
                    timePicker = (TimePicker) setAlarmLayout
                            .findViewById(R.id.timepicker);
                    timePicker.setIs24HourView(true);

                    new AlertDialog.Builder(getContext())
                            .setView(setAlarmLayout)
                            .setTitle("设置闹钟时间")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which)
                                        {
                                            enableClk();
                                            disableClk();
                                            enableClk();
                                            if (togbtn_AlarmStyle.isChecked())
                                            {
                                                setAlarmStyle(true);
                                            }
                                            else
                                            {
                                                setAlarmStyle(false);
                                            }

                                            edit.putBoolean("style",
                                                    togbtn_AlarmStyle.isChecked());
                                            btn_enClk.setChecked(true);
                                            Toast.makeText(getContext(),
                                                    "闹钟设置成功", Toast.LENGTH_LONG)
                                                    .show();// 提示用户
                                        }
                                    }).setNegativeButton("取消", null).show();
                    break;

                case R.id.btn_enClk:
                    if (btn_enClk.isChecked())
                    {
                        enableClk();
                    }
                    else
                    {
                        enableClk();
                        disableClk();
                    }
                    break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }
}
