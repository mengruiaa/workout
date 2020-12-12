package com.example.fitnessdemo.LZYZYH.activity;
/*
详情界面
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.fitnessdemo.LZYZYH.TuiJian;
import com.example.fitnessdemo.LZYZYH.adapter.SearchAdapter;
import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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


public class DetailActivity extends AppCompatActivity {
    private ImageView ivShow;
    private TextView proPrice;
    private  TextView proNum;

    private Button proEnsure;

    private TextView proColor;
    private ImageView pimg1;
    private ImageView pimg2;

    private ImageView pimg3;
    private ImageView pimg4;
    private ImageView pimg5;
    private TextView proName;
    private ImageView pimg6;
    private Button paddCar;
    private Gson gson;
    private List<Product> goodsList;
    private ListView lvShowAllGoods;
    private SearchAdapter adapter;
    private String etProductSearch;
    private Product pro;
    private String product_link;
    private OkHttpClient okHttpClient=new OkHttpClient();

    private ImageView iv ;

    private Product goods;

        private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
              //      Log.e("lzzzz",goodsList.toString());
                    goods = pro;
                    setDate();
                    break;
            }
        }
    };
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_product_detail);
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
                DetailActivity.this.finish();
            }
        });
        Intent intent = getIntent();
        etProductSearch = intent.getStringExtra("etProductSearch");
        System.out.println(etProductSearch+"!!!!!!!!!!!!");
        findViews();
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
                        gson = new GsonBuilder().serializeNulls().create();
                        Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                        List<Product> lists = gson.fromJson(json,type);
                        System.out.println("数组的大小是"+lists.size());
                        System.out.println("数组是"+lists);
                        pro = lists.get(0);
                        Message msg = handler.obtainMessage();
                        //设置Message对象的参数
                        msg.what = 1;
                        msg.obj = pro;
                        //发送Message
                        handler.sendMessage(msg);
                    }
                });
            }
        }.start();
    }

    private void findViews() {
        ivShow = findViewById(R.id.iv_show);
        proColor = findViewById(R.id.color);
        proEnsure=findViewById(R.id.ensure);
        proName = findViewById(R.id.name);
        proNum = findViewById(R.id.lzy_num);
        proPrice=findViewById(R.id.price);
        pimg1=findViewById(R.id.img1);
        pimg2=findViewById(R.id.img2);
        pimg3=findViewById(R.id.img3);
        pimg4=findViewById(R.id.img4);
        pimg5=findViewById(R.id.img5);
        pimg6=findViewById(R.id.img6);

    }

    private void setDate() {
        proName.setText(goods.getProduct_name());
        proNum.setText(goods.getProduct_quantity() + "");
        proColor.setText(goods.getProduct_color());
        proPrice.setText(goods.getProduct_price()+"");
        String pitcure = SERVER_HOME+"image/"+goods.getProduct_mainimage();
        String p1 = SERVER_HOME+"image/"+goods.getProduct_subimage1();
        String p2 = SERVER_HOME+"image/"+goods.getProduct_subimage2();
        String p3 = SERVER_HOME+"image/"+goods.getProduct_subimage3();
        String p4 = SERVER_HOME+"image/"+goods.getProduct_subimage4();
        String p5 = SERVER_HOME+"image/"+goods.getProduct_subimage5();
        String p6 = SERVER_HOME+"image/"+goods.getProduct_subimage6();
        System.out.println(pitcure);
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this)
                .load(pitcure)
                .apply(requestOptions)
                .into(ivShow);

        Glide.with(this)
                .load(p1)
                .apply(requestOptions)
                .into(pimg1);
        Glide.with(this)
                .load(p2)
                .apply(requestOptions)
                .into(pimg2);
        Glide.with(this)
                .load(p3)
                .apply(requestOptions)
                .into(pimg3);
        Glide.with(this)
                .load(p4)
                .apply(requestOptions)
                .into(pimg4);
        Glide.with(this)
                .load(p5)
                .apply(requestOptions)
                .into(pimg5);
        Glide.with(this)
                .load(p6)
                .apply(requestOptions)
                .into(pimg6);
    }

}
