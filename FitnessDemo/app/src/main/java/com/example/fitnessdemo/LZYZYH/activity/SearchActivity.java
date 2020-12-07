package com.example.fitnessdemo.LZYZYH.activity;
/*
搜索界面：模糊搜索searchview
 */
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.LZYZYH.adapter.SearchAdapter;
import com.example.fitnessdemo.LZYZYH.model.Product;

import java.util.ArrayList;
import java.util.List;

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;

/**
 * 搜索
 *
 * @author
 *
 */
public class SearchActivity extends AppCompatActivity {
    private ListView lv;
    private List<Product> lists=new ArrayList<Product>();
    private EditText et_search;
    private SearchAdapter adapter;
    private Drawable imgClear;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        initViews();
    }

    private void initViews() {
        
    }
}
