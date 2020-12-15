package com.example.fitnessdemo.ZFT;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.entity.History;
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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private View root;
    private List<MyHistory> mHistory;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://表示接收服务端的字符串
                    Gson gson = new GsonBuilder().serializeNulls().setDateFormat("YY:MM:dd").create();
                    String res = (String)msg.obj;
                    Log.i("gson", "handleMessage: " + res);
                    Type type = new TypeToken<List<String>>(){}.getType();
                    List<String> dateStrs = gson.fromJson(res,type);
                    String str="";
                    for (int i=0; i<dateStrs.size(); i++){
                        str = str + dateStrs.get(i) + "\n";
                    }
                    new AlertDialog.Builder(root.getContext()).setTitle("以下是您的打卡日记").setIcon(R.drawable.apphead)
                            .setMessage(str).setPositiveButton("yes",null)
                            .setNegativeButton("back",null).show();
//                        .setView(et).setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(RecoActivity.this,
//                                "身高:" + tv_register_info_height_value.getText().toString() + ";体重:" + tv_register_info_weight_value.getText().toString() + sexinfo
//                                + et.getText().toString(),Toast.LENGTH_SHORT).show();
//                    }
//                }).setNegativeButton("no",null).show();
//                    motions = gson.fromJson(res,type);
                    break;
            }
        }
    };
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHistory;
        TextView tvTime;
        View myHistoryView;
        public ViewHolder(View view) {
            super(view);
            myHistoryView = view;
            ivHistory = view.findViewById(R.id.iv_history);
            tvTime = view.findViewById(R.id.tv_zft_time);
        }
    }

    public HistoryAdapter(List<MyHistory> historyList){
        mHistory = historyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zft_user_diary_item_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ivHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Log.i("planName", "onClick: " + mHistory.get(position).getPlanName());
                Log.i("userPhone", "onClick: " + mHistory.get(position).getUserPhone());
                getDiary(ConfigUtil.SERVER_HOME + "GetDiary" + "?plan_name=" + mHistory.get(position).getPlanName() + "&user_phone=" + ConfigUtil.user_Name);
            }
        });
        return holder;
    }

    private void getDiary(final String s) {
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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        root = holder.myHistoryView;
        MyHistory history = mHistory.get(position);
        holder.tvTime.setText(history.getClockTime() + "次");
        Glide.with(holder.myHistoryView).load(ConfigUtil.SERVER_HOME + history.getImg()).into(holder.ivHistory);
    }

    @Override
    public int getItemCount() {
        return mHistory.size();
    }
}
