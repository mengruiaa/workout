package com.example.fitnessdemo.ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.LZYZYH.activity.SearchActivity;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FriendinfoActivity extends AppCompatActivity {
    private ImageView back;
    private ImageView touxiang;
    private RelativeLayout message;
    private RelativeLayout cancel;
    private LinearLayout plan;
    private LinearLayout course;
    private LinearLayout friend;
    private TextView sex;
    private TextView age;
    private TextView name;
    private String username;
    private TextView foucsname;
    private String str;
    private Bitmap bitmap;
    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();
    //定义Handler对象属性
    private Handler handler = new Handler() {//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1://如果服务端返回的数据是字符串
                    String result1 = (String) msg.obj;
                    User user = new Gson().fromJson(result1, User.class);
                    name.setText(user.getName());
                    String sexinfo = user.getSex();
                    if (sexinfo.equals("boy")){
                        sex.setText("帅哥");
                    }else if(sexinfo.equals("girl")){
                        sex.setText("美女");
                    }
                    String image = user.getImg();

                    String imgUrl = ConfigUtil.SERVER_HOME + image;
                    //使用网络连接下载图片
                    loadimg(imgUrl);
                    age.setText(user.getAge()+"");
                    break;
                case 2:
                        bitmap = (Bitmap) msg.obj;
                        Glide.with(getApplicationContext())
                                .load(bitmap)
                                .circleCrop()
                                .into(touxiang);

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_friendinfo);
        initview();
        Intent intent = getIntent();
        str = intent.getAction();
        if ("search".equals(str)){
            username = intent.getStringExtra("name");
            foucsname.setText("未关注");
        }else {
            username = intent.getStringExtra("name");
            Log.i("ws", "onCreate: "+username);
            foucsname.setText("已关注");
        }
        initListener();
        initinfo();
        
    }

    private void loadimg(final String imgUrl) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(imgUrl);
                    InputStream in = url.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void initinfo() {
        User user = new User();
        user.setPhone(username);
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

    private void initListener() {
        MyListener myListener = new MyListener();
        cancel.setOnClickListener(myListener);
        back.setOnClickListener(myListener);
        message.setOnClickListener(myListener);
        plan.setOnClickListener(myListener);
        course.setOnClickListener(myListener);
        friend.setOnClickListener(myListener);
    }



    private void initview() {
        foucsname = findViewById(R.id.friendinfo_focustv);
        cancel = findViewById(R.id.friendinfo_cancel);
        back = findViewById(R.id.iv_backfriendinfo);
        touxiang = findViewById(R.id.img_friendinfo);
        message = findViewById(R.id.friendinfo_message);
        plan = findViewById(R.id.friendinfo_plan);
        course = findViewById(R.id.friendinfo_course);
        friend = findViewById(R.id.friendinfo_focus);
        sex = findViewById(R.id.friendinfo_sex);
        age = findViewById(R.id.friendinfo_age);
        name = findViewById(R.id.friendinfo_name);
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_backfriendinfo:
                    if ("search".equals(str)){
                      Intent intent = new Intent();
                      intent.setClass(FriendinfoActivity.this, SearchActivity.class);
                      startActivity(intent);
                    }else {
                        Intent intent = new Intent();
                        intent.setClass(FriendinfoActivity.this, FriendActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.friendinfo_message:

                    break;
                case R.id.friendinfo_focus:
                    Intent intent = new Intent();
                    intent.setClass(FriendinfoActivity.this, SearchActivity.class);
                    intent.putExtra("name",username);
                    startActivity(intent);
                    break;
                case R.id.friendinfo_course:

                    break;
                case R.id.friendinfo_plan:

                    break;
                case R.id.friendinfo_cancel:
                    String states = foucsname.getText().toString();
                    if("已关注".equals(states)){
                        foucsname.setText("未关注");
                        canceluser(username);
                    }else if ("未关注".equals(states)){
                        foucsname.setText("已关注");
                        guanzhuuser(username);
                    }
                    break;

            }
        }
    }
    private void guanzhuuser(final String str) {
        new Thread(){
            @Override
            public void run() {
                Userfocus userfocus = new Userfocus();
                userfocus.setName(ConfigUtil.user_Name);
                userfocus.setFriendname(str);
                System.out.println("用户信息" + userfocus.toString().trim());
                String json = gson.toJson(userfocus);
                //2.创建request对象
                //1) 使用RequestBody封装请求数据
                //获取待传输数据对应的MIME类型
                MediaType type = MediaType.parse("text/plain");
                //创建RequestBody对象
                RequestBody reqBody = RequestBody.create(json, type);
                Request request = new Request.Builder()
                        .url(ConfigUtil.SERVER_HOME + "UserFocusServlet")
                        .post(reqBody)
                        .build();
                //3. 创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4. 异步方式提交请求并获取响应
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.i("ws", "请求失败");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        //获取服务端返回的数据（假设是字符串）
                        String result = response.body().string();
                        Log.i("ws", result);

                        //通知界面信息改变
//                        Toast.makeText(myContext,"成功关注",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();
    }
    private void canceluser(final String str) {
        new Thread(){
            @Override
            public void run() {
                Userfocus userfocus = new Userfocus();
                userfocus.setName(ConfigUtil.user_Name);
                userfocus.setFriendname(str);
                System.out.println("用户信息" + userfocus.toString().trim());
                String json = gson.toJson(userfocus);
                //2.创建request对象
                //1) 使用RequestBody封装请求数据
                //获取待传输数据对应的MIME类型
                MediaType type = MediaType.parse("text/plain");
                //创建RequestBody对象
                RequestBody reqBody = RequestBody.create(json, type);
                Request request = new Request.Builder()
                        .url(ConfigUtil.SERVER_HOME + "UserFoucscancelServlet")
                        .post(reqBody)
                        .build();
                //3. 创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4. 异步方式提交请求并获取响应
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.i("ws", "请求失败");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        //获取服务端返回的数据（假设是字符串）
                        String result = response.body().string();
                        Log.i("ws", result);

                        //通知界面信息改变
//                        Toast.makeText(myContext,"取消关注",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();
    }

}
