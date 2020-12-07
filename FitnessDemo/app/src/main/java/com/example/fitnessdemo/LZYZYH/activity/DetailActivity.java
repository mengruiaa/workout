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

import com.example.fitnessdemo.LZYZYH.adapter.SearchAdapter;
import com.example.fitnessdemo.LZYZYH.model.Product;
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

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;


public class DetailActivity extends AppCompatActivity {
//    private ImageView ivShow;
//    private TextView price;
//    private  TextView num;
//    private ImageView ensure;
//    private TextView color;
//    private ImageView img1;
//    private ImageView img2;
//    private ImageView img3;
//    private ImageView img4;
//    private ImageView img5;
//    private ImageView img6;
//    private Button addCar;
    private Gson gson;
    private List<Product> goodsList;
    private ListView lvShowAllGoods;
    private SearchAdapter adapter;

    //    @Override
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e("lzzzz",goodsList.toString());
                    initItem();
                    break;
            }
        }
    };
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_product_detail);


        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL("http://192.168.137.1:8080/JSZS/search");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String pro = reader.readLine();
                    gson=new Gson();
                    Type userListType = new TypeToken<ArrayList<Product>>(){}.getType();
                    goodsList = gson.fromJson(pro, userListType);
                    Log.e("aaa",goodsList.toString());
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void initItem() {
        adapter = new SearchAdapter(goodsList, R.layout.mall_list_item, this);
        lvShowAllGoods.setAdapter(adapter);
        lvShowAllGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
                intent.putExtra("id", goodsList.get(position).getProduct_id());
                startActivity(intent);
            }
        });
    }

}
