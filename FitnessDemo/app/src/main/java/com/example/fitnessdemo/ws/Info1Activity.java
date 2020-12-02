package com.example.fitnessdemo.ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class Info1Activity extends AppCompatActivity {
    private ImageView imgonejian;
    private ImageView imgoneplus;
    private ImageView imgtwojian;
    private ImageView imgtwoplus;
    private ImageView imgthreejian;
    private ImageView imgthreeplus;
    private ImageView imgman;
    private ImageView imggirl;
    private EditText infoone;
    private EditText infotwo;
    private EditText infothree;
    private Button btnnext;
    private String sex = null;
    //定义Handler对象属性
    private Handler handler;
    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_info1);
        //初始化Handler
        initHandler();
        findview();
        setListener();
    }

    private void setListener() {
        MyListener myListener = new MyListener();
        imgonejian.setOnClickListener(myListener);
        imgoneplus.setOnClickListener(myListener);
        imgtwojian.setOnClickListener(myListener);
        imgtwoplus.setOnClickListener(myListener);
        imgthreejian.setOnClickListener(myListener);
        imgthreeplus.setOnClickListener(myListener);
        btnnext.setOnClickListener(myListener);
        imggirl.setOnClickListener(myListener);
        imgman.setOnClickListener(myListener);
    }

    private void findview() {
        imgman =findViewById(R.id.info_man);
        imggirl = findViewById(R.id.info_girl);
        imgonejian = findViewById(R.id.info_weightjian);
        imgoneplus = findViewById(R.id.info_weightplus);
        infoone = findViewById(R.id.info_weightedit);
        imgtwojian = findViewById(R.id.info_heightjian);
        imgtwoplus = findViewById(R.id.info_heightplus);
        infotwo = findViewById(R.id.info_heightedit);
        imgthreejian = findViewById(R.id.info_agejian);
        imgthreeplus = findViewById(R.id.info_ageplus);
        infothree = findViewById(R.id.info_ageedit);
        btnnext =findViewById(R.id.info_next);
    }

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int one =Integer.parseInt(infoone.getText()+"");
            int two =Integer.parseInt(infotwo.getText()+"");
            int three =Integer.parseInt(infothree.getText()+"");
            switch (view.getId()){
                case R.id.info_weightjian:
                    infoone.setText(--one+"");
                    break;
                case R.id.info_weightplus:
                    infoone.setText(++one+"");
                    break;
                case R.id.info_heightjian:
                    infotwo.setText(--two+"");
                    break;
                case R.id.info_heightplus:
                    infotwo.setText(++two+"");
                    break;
                case R.id.info_agejian:
                    infothree.setText(--three+"");
                    break;
                case R.id.info_ageplus:
                    infothree.setText(++three+"");
                    break;
                case R.id.info_girl:
                    imggirl.setImageResource(R.drawable.girlws3);
                    imgman.setImageResource(R.drawable.boyws3);
                    sex = "girl";
                    break;
                case R.id.info_man:
                    imggirl.setImageResource(R.drawable.girlws4);
                    imgman.setImageResource(R.drawable.boyws4);
                    sex = "boy";
                    break;
                case R.id.info_next:
                    if (sex!=null){
                        userinfo();
                    }else {
                        Toast.makeText(Info1Activity.this,"请选择性别",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
    /**
     * 初始化Handler对象
     */
    private void initHandler() {
        handler = new Handler() {//handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://如果服务端返回的数据是字符串
                        String result = (String) msg.obj;
                        if ("该账户信息完善".equals(result)) {
                            Intent intent = new Intent();
                            intent.setClass(Info1Activity.this, ShouYeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Info1Activity.this, "该账户信息有误", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
    }

    private void userinfo() {
        //1.将注册信息转gson
        User user = new User();
        user.setPhone(ConfigUtil.user_Name);
        int weight = Integer.parseInt(infoone.getText().toString());
        user.setWeight(weight);
        int height = Integer.parseInt(infotwo.getText().toString());
        user.setHeight(height);
        int age =  Integer.parseInt(infothree.getText().toString());
        user.setAge(age);
        user.setSex(sex);
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
                .url(ConfigUtil.SERVER_HOME + "UserInfoServet")
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
}
