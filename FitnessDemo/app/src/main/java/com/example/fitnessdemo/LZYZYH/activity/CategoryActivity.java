package com.example.fitnessdemo.LZYZYH.activity;

/*
二级菜单界面
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.LZYZYH.adapter.CategoryAdapter;
import com.example.fitnessdemo.LZYZYH.adapter.CategoryGridAdapter;
import com.example.fitnessdemo.LZYZYH.adapter.ListOfGoodsAdapter;
import com.example.fitnessdemo.LZYZYH.model.CategoryGrid;
import com.example.fitnessdemo.LZYZYH.model.Categoryl;
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

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;
import static com.mob.MobSDK.getContext;

public class CategoryActivity extends AppCompatActivity {
    private ListView list_category;
    private GridView grid_category;
    ArrayList<Categoryl> list= new ArrayList<>();
    Gson gson = new Gson();
    CategoryAdapter adapter;
    private View view2;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_category_activity);

        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
//        initUi();
//        initData();
        initEvent();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(SERVER_HOME + "categoryl");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    //获取网络输出流
//                    OutputStream out = conn.getOutputStream();
//                    out.write(productName.getBytes());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String pro = reader.readLine();
                    System.out.println(pro+"lllllllllllllll");
                    gson=new Gson();
                    Type userListType = new TypeToken<ArrayList<Product>>(){}.getType();
                    list_category = gson.fromJson(pro, userListType);
                    Log.e("al",list_category.toString());
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
        adapter = new CategoryAdapter(this,list,R.layout.mall_list_item);
        list_category.setAdapter(adapter);
//        list_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(CategoryActivity.this,DetailActivity.class);
//                intent.putExtra("etProductName",list.get(position).getProduct_name());
//                startActivity(intent);
//            }
//        });


        list_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                TextView txt = (TextView) arg1.findViewById(R.id.txt_category_list);
                String name = txt.getText().toString();
                ArrayList<CategoryGrid> gridlist2 = new ArrayList<CategoryGrid>();
//                if (name.equals("智能设备")) {
//                    for (int i = 0; i < img_name.length; i++) {
//                        CategoryGrid model = new CategoryGrid();
//                        model.setImg_grid(ToolUtil.getPropThumnail(img_name[i], getContext()));
//                        model.setTxt_name(txt_name[i]);
//                        model.setTxt_name_jianjie(txt_name_jianjie[i]);
//                        gridlist2.add(model);
//                    }
//                } else {
//                    for (int i = 0; i < img_name.length; i++) {
//                        CategoryGrid model = new CategoryGrid();
//                        model.setImg_grid(ToolUtil.getPropThumnail(img_name[i], getContext()));
//                        model.setTxt_name(txt_name[i] + i);
//                        model.setTxt_name_jianjie(txt_name_jianjie[i]);
//                        gridlist2.add(model);
//                    }
//                }
                CategoryGridAdapter categoryGridAdapter = new CategoryGridAdapter(getContext(), gridlist2);
                grid_category.setAdapter(categoryGridAdapter);
            }
        });
        grid_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(arg1.getContext(), DetailActivity.class);
                startActivity(intent);
            }
        });
    }


    // 初始化控件
//    private void initUi() {
//        list_category = (ListView) findViewById(R.id.list_category);
//        grid_category = (GridView) findViewById(R.id.grid_category);
//        list_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                TextView txt = (TextView) arg1.findViewById(R.id.txt_category_list);
//                String name = txt.getText().toString();
//                ArrayList<CategoryGrid> gridlist2 = new ArrayList<CategoryGrid>();
//                if (name.equals("智能设备")) {
//                    for (int i = 0; i < img_name.length; i++) {
//                        CategoryGrid model = new CategoryGrid();
//                        model.setImg_grid(ToolUtil.getPropThumnail(img_name[i], getContext()));
//                        model.setTxt_name(txt_name[i]);
//                        model.setTxt_name_jianjie(txt_name_jianjie[i]);
//                        gridlist2.add(model);
//                    }
//                } else {
//                    for (int i = 0; i < img_name.length; i++) {
//                        CategoryGrid model = new CategoryGrid();
//                        model.setImg_grid(ToolUtil.getPropThumnail(img_name[i], getContext()));
//                        model.setTxt_name(txt_name[i] + i);
//                        model.setTxt_name_jianjie(txt_name_jianjie[i]);
//                        gridlist2.add(model);
//                    }
//                }
//                CategoryGridAdapter categoryGridAdapter = new CategoryGridAdapter(getContext(), gridlist2);
//                grid_category.setAdapter(categoryGridAdapter);
//            }
//        });
//        grid_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                Intent intent = new Intent(arg1.getContext(), DetailActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    // 数据
//    private void initData() {
//        list = new ArrayList<Categoryl>();
//        for (int i = 0; i < name.length; i++) {
//            Categoryl category = new Categoryl();
//            category.setCategoryl_name(name[i]);
//            list.add(category);
//        }
//        gridlist = new ArrayList<CategoryGrid>();
//        for (int i = 0; i < img_name.length; i++) {
//            CategoryGrid model = new CategoryGrid();
//            model.setImg_grid(ToolUtil.getPropThumnail(img_name[i], this));
//            model.setTxt_name(txt_name[i]);
//            model.setTxt_name_jianjie(txt_name_jianjie[i]);
//            gridlist.add(model);
//        }
//    }

    // 事件
    private void initEvent() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, list);
        list_category.setAdapter(categoryAdapter);
//        CategoryGridAdapter categoryGridAdapter = new CategoryGridAdapter(this, gridlist);
//        grid_category.setAdapter(categoryGridAdapter);
    }

}