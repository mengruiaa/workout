package com.example.fitnessdemo.LZYZYH.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.fitnessdemo.LZYZYH.adapter.ListOfGoodsAdapter;
import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;

/**
 * 商品列表
 *
 * @author
 *
 */
public class ListOfGoodsActivity extends Activity implements View.OnClickListener {
    private ListView list_listofgoods;
    private ImageView img_fanhui;
    private RelativeLayout relative_shaixuan;
    ArrayList<ListOfGoods> list;
    private PopupWindow popupWindow;
    private boolean state = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_search);
        list_listofgoods = (ListView) findViewById(R.id.list_listofgoods);
        img_fanhui = (ImageView) findViewById(R.id.img_fanhui);
        img_fanhui.setOnClickListener(this);
        list = new ArrayList<ListOfGoods>();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(SERVER_HOME + "/" + "ProductServlet");
                    url.openStream();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };

    }

    // 初始化
//    private void initView() {
//
//        for (int i = 0; i < 10; i++) {
//            ListOfGoods goodsModel = new ListOfGoods();
//            goodsModel.setBiaoti("【天猫预售】乐视手机1S32G金 Letv/乐视X500芈月传版乐1S" + i);
//            goodsModel.setJiage("￥1099.0");
//            goodsModel.setYuanjia("￥1299.0");
//            list.add(goodsModel);
//        }
//    }

    // 事件
    private void initEvent() {
        ListOfGoodsAdapter listOfGoodsAdapter = new ListOfGoodsAdapter(this, list);
        list_listofgoods.setAdapter(listOfGoodsAdapter);
        list_listofgoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(ListOfGoodsActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.img_fanhui:
                finish();
                break;
        }

    }

    // 菜单


}