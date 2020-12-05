package com.example.fitnessdemo.LZYZYH.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fitnessdemo.R;

/**
 * 搜索
 *
 * @author
 *
 */
public class SearchActivity extends Activity implements View.OnClickListener {
    private ImageView img_souse,img_fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_search);
        initView();
    }

    private void initView() {
        img_souse = (ImageView) findViewById(R.id.img_souse);
        img_fanhui = (ImageView) findViewById(R.id.img_fanhui);
        img_fanhui.setOnClickListener(this);
        img_souse.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.img_souse:
                Intent intent = new Intent(arg0.getContext(), ListOfGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.img_fanhui:
                finish();
                break;
        }
    }

}