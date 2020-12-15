package com.example.fitnessdemo.MR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.entity.History;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SearchGetActivity extends AppCompatActivity {
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient=new OkHttpClient();
    private Gson gson=new Gson();
    private History history;
    private String search;
    private Handler handler= new Handler(){//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    Intent intent=new Intent(SearchGetActivity.this, CourseDetailActivity.class);
                    intent.putExtra("courseName",search);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.mr_activity_search_get);
        Intent intent=getIntent();
        if(intent.getAction().equals("addc")){
            search= intent.getStringExtra("name");
            history=new History(ConfigUtil.user_Name,search);
            new Thread(){
                @Override
                public void run() {
                    addSearchHistory();
                }
            }.start();
        }else{
            search= intent.getStringExtra("searchName");
            Intent intent2=new Intent(SearchGetActivity.this, CourseDetailActivity.class);
            intent2.putExtra("courseName",search);
            startActivity(intent2);
        }
    }

    private void addSearchHistory() {
        //2 创建Request对象
        //1) 使用RequestBody封装请求数据
        //获取待传输数据对应的MIME类型
        MediaType type = MediaType.parse("text/plain");
        //创建RequestBody对象
        RequestBody reqBody = RequestBody.create(type,gson.toJson(history));
        //2) 创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.SERVER_HOME + "AddHistory")
                .post(reqBody)
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage();
        //设置Message对象的参数
        msg.what = 1;

        //发送Message
        handler.sendMessage(msg);
    }
}
