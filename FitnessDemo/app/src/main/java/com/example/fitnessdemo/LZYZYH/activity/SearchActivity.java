package com.example.fitnessdemo.LZYZYH.activity;
/*
搜索界面：模糊搜索searchview
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.LZYZYH.adapter.SearchAdapter;
import com.example.fitnessdemo.LZYZYH.fragment.ProductFragment;
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
import java.util.List;

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;

/**
 * 搜索
 *
 * @author
 *
 */
public class SearchActivity extends Activity implements View.OnClickListener  {
    private ListView lv;
    private List<Product> lists=new ArrayList<Product>();
    private SearchAdapter adapter;
    private ListView list_listofgoods;
    private ImageView img_fanhui;
    ArrayList<Product> list;
    ArrayList<Fragment> mFragment=new ArrayList<>();
    Gson gson;
    private boolean state = true;
    private String productName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.mall_search);

        list_listofgoods = (ListView) findViewById(R.id.list_listofgoods);
        img_fanhui = (ImageView) findViewById(R.id.img_fanhui);
        img_fanhui.setOnClickListener(this);
        list = new ArrayList<Product>();
        Intent intent=getIntent();
        productName=intent.getStringExtra("etProductSearch");
        initItem();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(SERVER_HOME + "search");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    //获取网络输出流
                    OutputStream out = conn.getOutputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String pro = reader.readLine();
                    System.out.println(pro);
                    gson=new Gson();
                    Type userListType = new TypeToken<ArrayList<Product>>(){}.getType();
                    list = gson.fromJson(pro, userListType);
                    Log.e("al",list.toString());
                    mFragment.add(new ProductFragment(list));

                    out.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };

    }

    private void initItem() {
        adapter = new SearchAdapter(list,R.layout.mall_list_item,this);
        list_listofgoods.setAdapter(adapter);
        list_listofgoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,DetailActivity.class);
                intent.putExtra("id",list.get(position).getProduct_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_fanhui:
                finish();
                break;
        }
    }
}
