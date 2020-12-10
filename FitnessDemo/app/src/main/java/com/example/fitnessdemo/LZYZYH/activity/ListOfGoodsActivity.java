package com.example.fitnessdemo.LZYZYH.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.LZYZYH.TuiJian;
import com.example.fitnessdemo.LZYZYH.adapter.ListOfGoodsAdapter;
import com.example.fitnessdemo.LZYZYH.adapter.SearchAdapter;
import com.example.fitnessdemo.LZYZYH.fragment.ProductFragment;
import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;

/**
 * 商品列表界面
 *
 * @author
 *
 */
public class ListOfGoodsActivity extends Activity implements View.OnClickListener {
    private ListView list_listofgoods;
    private ImageView img_fanhui;
    ArrayList<Product> list;
    Gson gson;
    private boolean state = true;
    ListOfGoodsAdapter adapter;
    private String productName;
    private Handler handler= new Handler() {//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    initItem();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_search);
        //取消应用标题栏

        list_listofgoods = (ListView) findViewById(R.id.list_listofgoods);
        img_fanhui = (ImageView) findViewById(R.id.img_fanhui);
        img_fanhui.setOnClickListener(this);
        list = new ArrayList<Product>();
        Intent intent=getIntent();
        productName=intent.getStringExtra("etProductSearch");

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(SERVER_HOME + "product");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    //获取网络输出流
//                    OutputStream out = conn.getOutputStream();
//                    out.write(productName.getBytes());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String pro = reader.readLine();
                    gson=new Gson();
                    Type userListType = new TypeToken<ArrayList<Product>>(){}.getType();
                    list = gson.fromJson(pro, userListType);
                //    Log.e("al",list.toString());
                    Message msg = handler.obtainMessage();
                    //设置Message对象的参数
                    msg.what = 1;
                    //发送Message
                    handler.sendMessage(msg);
                 //   out.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }
    private void initItem() {
        adapter = new ListOfGoodsAdapter(this,list,R.layout.mall_list_item);
        list_listofgoods.setAdapter(adapter);
        list_listofgoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListOfGoodsActivity.this,DetailActivity.class);
                intent.putExtra("etProductName",list.get(position).getProduct_name());
                startActivity(intent);
            }
        });
    }
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.img_fanhui:
                Intent i = new Intent();
                i.setClass(this, TuiJian.class);
                startActivity(i);
                break;
        }

    }

    // 菜单


}