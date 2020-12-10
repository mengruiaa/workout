package com.example.fitnessdemo.LZYZYH.activity;
/*
搜索界面：模糊搜索searchview
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessdemo.LZYZYH.adapter.SearchAdapter;
import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
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

/**
 * 搜索
 *
 * @author
 *
 */
public class SearchActivity extends AppCompatActivity  {
    private SearchAdapter adapter;
    private ListView list_listofgoods;
    private ImageView img_fanhui;
    List<Product> list ;
    private OkHttpClient okHttpClient=new OkHttpClient();
    private Gson gson = new Gson();
    private boolean state = true;
    private String etProductSearch;
    private Handler handler= new Handler() {//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    list = (List<Product>)msg.obj;
                    initItem();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_search);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        list_listofgoods = (ListView) findViewById(R.id.list_listofgoods);
        img_fanhui = (ImageView) findViewById(R.id.img_fanhui);
//        img_fanhui.setOnClickListener(this);
        list = new ArrayList<Product>();
        Intent intent=getIntent();
        etProductSearch=intent.getStringExtra("etProductSearch");
        System.out.println("aaaa"+etProductSearch);


        new Thread(){
            @Override
            public void run() {
                MediaType type = MediaType.parse("text/plain");
                RequestBody requestBody = RequestBody.create(type,etProductSearch);
                Request request = new Request.Builder()
                        .url(SERVER_HOME + "search")
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
                        System.out.println(json);
                         Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                         List<Product> lists = gson.fromJson(json,type);

                        Message msg = handler.obtainMessage();
                        //设置Message对象的参数
                        msg.what = 1;
                        msg.obj = lists;
                        //发送Message
                        handler.sendMessage(msg);
                    }
                });
            }
        }.start();

    }

    private void initItem() {
        adapter = new SearchAdapter(list,R.layout.mall_list_item,this);
        list_listofgoods.setAdapter(adapter);
        list_listofgoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,DetailActivity.class);
                intent.putExtra("etProductSearch",list.get(position).getProduct_name());
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.img_fanhui:
//                finish();
//                break;
//        }
//    }
}
