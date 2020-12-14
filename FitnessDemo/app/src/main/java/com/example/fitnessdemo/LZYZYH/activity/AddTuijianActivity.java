package com.example.fitnessdemo.LZYZYH.activity;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.LZYZYH.adapter.ListOfGoodsAdapter;
import com.example.fitnessdemo.LZYZYH.adapter.ListOfShoucangAdapter;
import com.example.fitnessdemo.LZYZYH.adapter.SearchAdapter;
import com.example.fitnessdemo.LZYZYH.model.Categoryr;
import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
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
import static com.example.fitnessdemo.ConfigUtil.user_Name;

public class AddTuijianActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient=new OkHttpClient();
    private Gson gson=new Gson();
    private ListView lv;
    private ImageView iv;
    List<Categoryr> list =new ArrayList<>();
    ListOfShoucangAdapter adapter;
    private Handler handler= new Handler() {//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    list = (List<Categoryr>)msg.obj;
                    initItem();
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_addtuijian);
        lv=findViewById(R.id.tuijian_goods);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        iv = findViewById(R.id.img_fanhui);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent();
//                i.setClass(DetailActivity.this, TuiJian.class);
//                startActivity(i);
                AddTuijianActivity.this.finish();
            }
        });
        new Thread(){
            @Override
            public void run() {
                System.out.println("SearchShoucangServlet  begin");
                MediaType type = MediaType.parse("text/plain");
                RequestBody requestBody = RequestBody.create(type,user_Name);
                Request request = new Request.Builder()
                        .url(SERVER_HOME + "SearchShoucangServlet")
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
                        System.out.println("SearchShoucangServlet  begin");
                        String json = response.body().string();
                        gson = new GsonBuilder().serializeNulls().create();
                        Type type = new TypeToken<ArrayList<Categoryr>>(){}.getType();
                        List<Categoryr> lists = gson.fromJson(json,type);
                        System.out.println("数组的大小是"+lists.size());
                        System.out.println("数组是"+lists);
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
        adapter = new ListOfShoucangAdapter(list,R.layout.mall_list_item,this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AddTuijianActivity.this,DetailActivity.class);
                intent.putExtra("etProductSearch",list.get(position).getProduct_name());
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTuijianActivity.this);
                builder.setMessage("确定删除商品吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteShoucang(list.get(i).getProduct_name());
                        if(list.remove(i)!=null){
                            System.out.println("成功！");
                        }else{
                            System.out.println("失败！");
                        }
                        adapter.notifyDataSetChanged();

//通知adapter 更新
                        adapter = new ListOfShoucangAdapter(list,R.layout.mall_list_item,AddTuijianActivity.this);
                        lv.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    private void deleteShoucang(final String etProductSearch) {
        new Thread(){
            @Override
            public void run() {
                System.out.println("DeleteShoucangServlet  begin");
                MediaType type = MediaType.parse("text/plain");
                RequestBody requestBody = RequestBody.create(type,user_Name+"&&&"+etProductSearch);
                Request request = new Request.Builder()
                        .url(SERVER_HOME + "DeleteShoucangServlet")
                        .post(requestBody)
                        .build();
                //3. 创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4. 提交请求并获取响应
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
