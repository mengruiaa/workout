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
import android.widget.ListView;
import android.widget.Toast;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FriendActivity extends AppCompatActivity {
    private ImageView back;
    private ImageView search;

    //定义Handler对象属性
    private Handler handler;
    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();

    private ListView listView;
    private MyFriendAdapter adapter;
    private List<Usershow> friendList = new ArrayList<>();
    private List<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_friend);

        initview();
        initListener();
        searchfriend();
        initHandler();
    }


    /**
     * 初始化Handler对象
     */
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://如果服务端返回的数据是字符串
                        adapter = new MyFriendAdapter(getApplicationContext(),
                                friendList,
                                R.layout.ws_item_myfriend);
                        listView = findViewById(R.id.lv_myfriend);
                        listView.setAdapter(adapter);
                        break;
                }
            }
        };
    }

    private void searchfriend() {
        //1. 创建OKHTTPClient对象(已经创建好)
        //2. 创建请求对象
        String name = ConfigUtil.user_Name;
        Request request = new Request.Builder()
                .url(ConfigUtil.SERVER_HOME
                        + "UserFriendServlet?name=" + name)
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
                System.out.println("好友列表" + result);
                //1. 得到集合的类型
                Type type = new TypeToken<List<User>>() {
                }.getType();
                //2. 反序列化
                list = gson.fromJson(result, type);
                for (int i = 0; i < list.size(); i++) {
                    Usershow user = new Usershow();
                    user.setName(list.get(i).getName());
                    user.setPhone(list.get(i).getPhone());
                    user.setSex(list.get(i).getSex());
                    user.setAge(list.get(i).getAge());
                    String imgurl = ConfigUtil.SERVER_HOME + list.get(i).getImg();
                    //使用网络连接下载图片
                    URL url = new URL(imgurl);
                    InputStream in = url.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    user.setBitmap(bitmap);
                    friendList.add(user);
                }

                Log.i("ws", result);

                //通知界面信息改变
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = friendList;
                handler.sendMessage(msg);
            }
        });
    }

    private void initListener() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FriendActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                setResult(6, intent);
                finish();
            }
        });
    }

    private void initview() {
        back = findViewById(R.id.iv_backfriend);
        search = findViewById(R.id.iv_friend);
        listView = findViewById(R.id.lv_myfriend);
    }
}
