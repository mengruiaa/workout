package com.example.fitnessdemo.ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.ShouYeActivity;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShentiinfoActivity extends AppCompatActivity {
    private ImageView imgback;
    private ImageView imginfo;
    private TextView tvweight;
    private TextView tvheight;
    private TextView tvbmi;
    private TextView tvxing;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;

    private String result;
    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();
    //定义Handler对象属性
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_shentiinfo);

        findview();
        initinfo();
        initHandler();
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ShentiinfoActivity.this, ShouYeActivity.class);
                intent.setAction("ws");
                startActivity(intent);
            }
        });
        imginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("体重","kg");
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog("身高","cm");
            }
        });

//        imageView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialog("体脂率","%");
//            }
//        });

    }
    private void initHandler() {
        handler = new Handler() {//handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://如果服务端返回的数据是字符串
                        result = (String) msg.obj;
                        User user = new Gson().fromJson(result, User.class);
                        System.out.println(user.toString());
                        tvweight.setText(user.getWeight()+"");
                        tvheight.setText(user.getHeight()+"");
                        int bmi = user.getWeight()/(user.getHeight()*user.getHeight()/10000);
                        System.out.println("BMI:"+bmi);
                        tvbmi.setText(bmi+"");
                        break;
                }
            }
        };
    }

    private void initinfo() {
        User user = new User();
        user.setPhone(ConfigUtil.user_Name);
        System.out.println("用户信息" + user.toString().trim());
        String json = gson.toJson(user);
        //2.创建request对象
        //1) 使用RequestBody封装请求数据
        //获取待传输数据对应的MIME类型
        MediaType type = MediaType.parse("text/plain");
        //创建RequestBody对象
        RequestBody reqBody = RequestBody.create(json, type);
        //2) 创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.SERVER_HOME + "BodyinfoServlet")
                .post(reqBody)
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                System.out.println(result);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }


    public void showDialog(String info,String infodanwei){
        //管理Fragment的添加和显示
        FragmentManager manager = getSupportFragmentManager();
        //事务
        FragmentTransaction transaction = manager.beginTransaction();
        //创建自定义对话框对象
        CustomDialog dialog = new CustomDialog(info,infodanwei);
        //判断是否已添加
        if (!dialog.isAdded()){
            //添加Fragment
            transaction.add(dialog,"dialog");
        }
        //显示对话框
        transaction.show(dialog);
        //提交事务
        transaction.commit();
    }

    private void findview() {
        imgback = findViewById(R.id.iv_back);
        imginfo = findViewById(R.id.img_infojia);
        tvweight =findViewById(R.id.shengti_weight);
        tvheight = findViewById(R.id.shengti_height);
        tvbmi = findViewById(R.id.shengti_bmi);
        tvxing = findViewById(R.id.shengti_tizhi);
        imageView1 = findViewById(R.id.shenti_img2);
        imageView2 = findViewById(R.id.shenti_img4);
        imageView3 = findViewById(R.id.shenti_img8);
    }
}
