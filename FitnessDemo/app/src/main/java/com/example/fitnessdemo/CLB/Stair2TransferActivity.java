package com.example.fitnessdemo.CLB;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.CLB.entity.Cyclopedia1;
import com.example.fitnessdemo.ConfigUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Stair2TransferActivity extends AppCompatActivity {
    private Gson gson;
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Cyclopedia1 cyclopedia1 = (Cyclopedia1) msg.obj;
                    System.out.println(cyclopedia1);
                    String name = cyclopedia1.getName();
                    System.out.println(">>>>>>>"+name);
                    String attentionNumber = cyclopedia1.getAttentionNumber()+"";
                    String brief = cyclopedia1.getBrief();
                    //获取item对应的数据名称，跳转activity
                    Intent intent = new Intent();
                    intent.setClass(Stair2TransferActivity.this, Stair2Activity.class);
                    intent.putExtra("itemName",name);
                    intent.putExtra("attentionNumber",attentionNumber);
                    intent.putExtra("brief",brief);
                    startActivity(intent);

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_stair2_transfer);
        gson = new Gson();
        okHttpClient = new OkHttpClient();
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        System.out.println(name);
        initDate(ConfigUtil.SERVER_HOME+"SelectOneCyclopediaServlet",name);

    }
    private void initDate(final String s, final String name) {
        new Thread(){
            @Override
            public void run() {
                MediaType type = MediaType.parse("text/plain");
                RequestBody requestBody = RequestBody.create(type, name);
                Request request = new Request.Builder()
                        .url(s)
                        .post(requestBody)
                        .build();
                //3. 创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4. 提交请求并获取响应
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("clb", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        System.out.println("??"+result);
//                        Type type = new TypeToken<Collection<Essay>>(){}.getType();
                        Cyclopedia1 cyclopedia1 = gson.fromJson(result, Cyclopedia1.class);
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.obj = cyclopedia1;
                        handler.sendMessage(msg);

                    }
                });
            }
        }.start();


    }
}
