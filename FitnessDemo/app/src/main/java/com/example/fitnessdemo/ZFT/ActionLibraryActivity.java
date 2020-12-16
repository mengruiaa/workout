
package com.example.fitnessdemo.ZFT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.ArrayList;
import java.util.List;

public class ActionLibraryActivity extends AppCompatActivity {
    public static List<Motion> actions = new ArrayList<>();
    private List<Motion> motions;
    private ActionAdapter actionAdapter;
    private Button btnFinish;
    private Button btnYes;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://表示接收服务端的字符串
                    Gson gson = new GsonBuilder().serializeNulls().setDateFormat("YY:MM:dd").create();
                    String res = (String)msg.obj;
                    Log.i("action", "handleMessage: " + res);
                    Type type = new TypeToken<List<Motion>>(){}.getType();
                    motions = gson.fromJson(res,type);
                    RecyclerView recyclerView = findViewById(R.id.rlv_action);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ActionLibraryActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    actionAdapter = new ActionAdapter(motions);
                    recyclerView.setAdapter(actionAdapter);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.zft_activity_action_library);
        btnYes = findViewById(R.id.btn_yesToAdd);
        btnFinish = findViewById(R.id.btn_finish);
        getActions(ConfigUtil.SERVER_HOME + "GetActions");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Gson gson = new Gson();
                final String[] name = {""};
                final EditText et = new EditText(ActionLibraryActivity.this);
                System.out.println(gson.toJson(actions));
                new AlertDialog.Builder(ActionLibraryActivity.this).setTitle("输入您的计划名称").setView(et).setIcon(R.drawable.apphead)
                        .setPositiveButton("yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        name[0] = et.getText().toString().trim();
                        addAction(ConfigUtil.SERVER_HOME + "AddAction" + "?actionList=" + gson.toJson(actions) + "&user_phone=" + ConfigUtil.user_Name + "&plan_name=" + name[0]);
                        finish();
                    }
                }).setNegativeButton("back",null).show();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addAction(final String s) {
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
//                    Message msg = new Message();
//                    //设置Message对象的参数
//                    msg.what = 1;
//                    msg.obj = str;
//                    //发送Message
//                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getActions(final String s) {
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
    protected void onDestroy() {
        super.onDestroy();
        actions = new ArrayList<>();
    }
}
