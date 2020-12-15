package com.example.fitnessdemo.ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.ShouYeActivity;
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

public class SearchActivity extends AppCompatActivity {

    private ImageView back;
    private EditText searchinfo;
    private ImageView del;
    private TextView soussuoinfo;
    private RelativeLayout search;
    private ListView friends;


    //定义Handler对象属性
    private Handler handler;
    private Gson gson = new Gson();
    //定义OKHTTPClient对象属性
    private OkHttpClient okHttpClient = new OkHttpClient();


    private ListView listView;
    private Friendadapter adapter;
    private List<Usershow> searchList = new ArrayList<>();
    private List<User> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_search);

        initview();
        initListener();
        //初始化Handler
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
                        if (searchList.size()< 1) {
                            Toast.makeText(SearchActivity.this, "账户信息不存在", Toast.LENGTH_SHORT).show();
                            friends.setVisibility(View.VISIBLE);
                        } else {
                            adapter = new Friendadapter(getApplicationContext(),
                                    searchList,
                                    R.layout.ws_item_friends);
                            listView = findViewById(R.id.lv_friend);
                            listView.setAdapter(adapter);
                            friends.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        };
    }

    private void initListener() {
        searchinfo.addTextChangedListener(textWatcher);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchinfo.setText("");
                searchList.clear();
                friends.setVisibility(View.GONE);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setVisibility(View.GONE);
                new Thread() {
                    @Override
                    public void run() {
                        searchfriend();
                    }
                }.start();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SearchActivity.this,FriendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void searchfriend() {
        //1. 创建OKHTTPClient对象(已经创建好)
        //2. 创建请求对象
        String name = searchinfo.getEditableText().toString();
        Request request = new Request.Builder()
                .url(ConfigUtil.SERVER_HOME
                        + "UserSearchServlet?name=" + name)
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
                if (result.length()> 8){
                //1. 得到集合的类型
                Type type = new TypeToken<List<User>>() {}.getType();
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
                        searchList.add(user);
                    }

                    Log.i("ws", result);

                }


                //通知界面信息改变
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = searchList;
                handler.sendMessage(msg);
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (searchinfo.getEditableText().length() >= 1) {
                search.setVisibility(View.VISIBLE);
                soussuoinfo.setText(searchinfo.getEditableText());
            } else {
                search.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (searchinfo.getEditableText().length() >= 1) {
                del.setVisibility(View.VISIBLE);
                soussuoinfo.setText(searchinfo.getEditableText());
                search.setVisibility(View.VISIBLE);
            } else {
                del.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
            }
        }
    };

    private void initview() {
        soussuoinfo = findViewById(R.id.tv_sousouinfo);
        back = findViewById(R.id.iv_backsearch);
        searchinfo = findViewById(R.id.edit_search);
        del = findViewById(R.id.del);
        search = findViewById(R.id.layout_search);
        friends = findViewById(R.id.lv_friend);
    }
}
