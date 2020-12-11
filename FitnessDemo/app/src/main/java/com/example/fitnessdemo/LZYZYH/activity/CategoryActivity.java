package com.example.fitnessdemo.LZYZYH.activity;

/*
二级菜单界面
 */

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.LZYZYH.TuiJian;
import com.example.fitnessdemo.LZYZYH.adapter.CategoryAdapter;
import com.example.fitnessdemo.LZYZYH.adapter.CategoryGridAdapter;
import com.example.fitnessdemo.LZYZYH.adapter.ListOfGoodsAdapter;
import com.example.fitnessdemo.LZYZYH.model.CategoryGrid;
import com.example.fitnessdemo.LZYZYH.model.Categoryl;
import com.example.fitnessdemo.LZYZYH.model.Categoryr;
import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.LZYZYH.util.ToolUtil;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;
import static com.mob.MobSDK.getContext;

public class CategoryActivity extends AppCompatActivity {
    private ListView list_category;
    private GridView grid_category;
    ArrayList<Categoryl> clist= new ArrayList<>();
    ArrayList<Categoryr> plist= new ArrayList<>();
    private ImageView iv ;

    private OkHttpClient okHttpClient=new OkHttpClient();
    Gson gson = new Gson();
    private List<Integer> t= new ArrayList<>();
    CategoryAdapter adapter;
    private View view2;
    private String typeId;
    private Handler handler= new Handler() {//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    List<Integer>  strs= (List<Integer>) msg.obj;
                    System.out.println("左边的名字："+strs);
                    t=strs;
                    initItem();
                    break;
                case 2:
                    CategoryGridAdapter categoryGridAdapter = new CategoryGridAdapter(getContext(), plist);
                    grid_category.setAdapter(categoryGridAdapter);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_category_activity);
        list_category = findViewById(R.id.list_category);
        grid_category=findViewById(R.id.grid_category);
        iv = findViewById(R.id.img_fanhui);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryActivity.this.finish();
            }
        });
//        initUi();
//        initData();
     //   initEvent();
        Intent intent=getIntent();
        typeId=intent.getStringExtra("typeId");
        new Thread(){
            @Override
            public void run() {
                MediaType type = MediaType.parse("text/plain");
                RequestBody requestBody = RequestBody.create(type,typeId);
                Request request = new Request.Builder()
                        .url(SERVER_HOME + "categoryl")
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
                        String json = response.body().string();
                        System.out.println("我想打的是json分类"+json);
                        Type type = new TypeToken<ArrayList<Categoryl>>(){}.getType();
                        clist = gson.fromJson(json,type);
                        System.out.println("我想打的是分类"+clist);
                        List<Integer> strs=new ArrayList<>();
                        for(Categoryl categoryl:clist){
                            int categorylTypeId = categoryl.getCategoryl_id();
                            strs.add(categorylTypeId);
                        }

                        Message msg = handler.obtainMessage();
                        //设置Message对象的参数
                        msg.what = 1;
                        msg.obj = strs;
                        //发送Message
                        handler.sendMessage(msg);
                    }
                });
            }
        }.start();


    }
    private void initItem() {
        adapter = new CategoryAdapter(this,clist,R.layout.mall_category_listview);
        list_category.setAdapter(adapter);
        new Thread(){
            @Override
            public void run() {
                MediaType type = MediaType.parse("text/plain");
                RequestBody requestBody = RequestBody.create(type, t.get(0) +"");
                System.out.println("position是"+0);
                Request request = new Request.Builder()
                        .url(SERVER_HOME + "Categoryr")
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
                        String json = response.body().string();
                        System.out.println("右面的json分类是"+json);
                        Type type = new TypeToken<ArrayList<Categoryr>>(){}.getType();
                        plist = gson.fromJson(json,type);
                        System.out.println("右面分类是"+plist);
                        Message msg = handler.obtainMessage();
                        //设置Message对象的参数
                        msg.what = 2;
                        //发送Message
                        handler.sendMessage(msg);
                    }
                });
            }
        }.start();
        list_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                new Thread(){
                    @Override
                    public void run() {
                        MediaType type = MediaType.parse("text/plain");
                        RequestBody requestBody = RequestBody.create(type, t.get(arg2) +"");
                        System.out.println("position是"+arg2);
                        Request request = new Request.Builder()
                                .url(SERVER_HOME + "Categoryr")
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
                                String json = response.body().string();
                                System.out.println("右面的json分类是"+json);
                                Type type = new TypeToken<ArrayList<Categoryr>>(){}.getType();
                                plist = gson.fromJson(json,type);
                                System.out.println("右面分类是"+plist);
                                Message msg = handler.obtainMessage();
                                //设置Message对象的参数
                                msg.what = 2;
                                //发送Message
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }.start();

            }
        });
        grid_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(arg1.getContext(), DetailActivity.class);
                intent.putExtra("etProductSearch",plist.get(arg2).getProduct_name());
                startActivity(intent);
            }
        });
    }
    // 事件
//    private void initEvent() {
//        CategoryAdapter categoryAdapter = new CategoryAdapter(this, clist);
//        list_category.setAdapter(categoryAdapter);
//    }

}