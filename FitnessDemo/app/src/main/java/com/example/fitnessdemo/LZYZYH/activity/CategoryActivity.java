package com.example.fitnessdemo.LZYZYH.activity;

/*
二级菜单界面
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.LZYZYH.adapter.CategoryAdapter;
import com.example.fitnessdemo.LZYZYH.adapter.CategoryGridAdapter;
import com.example.fitnessdemo.LZYZYH.model.CategoryGrid;
import com.example.fitnessdemo.LZYZYH.model.Categoryl;
import com.example.fitnessdemo.LZYZYH.util.ToolUtil;
import com.example.fitnessdemo.R;

import java.util.ArrayList;
import static com.mob.MobSDK.getContext;

public class CategoryActivity extends AppCompatActivity {
    private ListView list_category;
    private GridView grid_category;
   // private EditText ed_soushuo;
    private String[] name = { "智能设备", "品牌精选", "女子服饰", "新品推荐", "新品推荐", "新品推荐", "新品推荐", "新品推荐" };
    private ArrayList<Categoryl> list;
    private ArrayList<CategoryGrid> gridlist;
    private int[] img_name = { R.drawable.pinpai, R.drawable.zhineng, R.drawable.woman,
            R.drawable.man, R.drawable.sport, R.drawable.shenghuo, R.drawable.pinpai,
            R.drawable.woman };
    private String[] txt_name = { "智能手环", "多彩智能手环", "心率智能手环", "彩心智能手环", "多彩心率智能手环", "多彩心率智能手环", "多彩心率智能手环",
            "多彩心率智能手环" };
    private String[] txt_name_jianjie = { "手环课监制无氧运动", "bong2s 心率手环无氧运动", "氧运动",
            "bong2s 心率手环课监制无氧运动", "bong2s 心率手环课监制无氧运动", "bong2s 心率手环课监制无氧运动", "bong2s 心率手环课监制无氧运动",
            "bong2s 心率手环课监制无氧运动" };
    private View view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_category_activity);
        initUi();
        initData();
        initEvent();
        initData();
    }

    // 初始化控件
    private void initUi() {
        list_category = (ListView) findViewById(R.id.list_category);
        grid_category = (GridView) findViewById(R.id.grid_category);
        list_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                TextView txt = (TextView) arg1.findViewById(R.id.txt_category_list);
                String name = txt.getText().toString();
                ArrayList<CategoryGrid> gridlist2 = new ArrayList<CategoryGrid>();
                if (name.equals("智能设备")) {
                    for (int i = 0; i < img_name.length; i++) {
                        CategoryGrid model = new CategoryGrid();
                        model.setImg_grid(ToolUtil.getPropThumnail(img_name[i], getContext()));
                        model.setTxt_name(txt_name[i]);
                        model.setTxt_name_jianjie(txt_name_jianjie[i]);
                        gridlist2.add(model);
                    }
                } else {
                    for (int i = 0; i < img_name.length; i++) {
                        CategoryGrid model = new CategoryGrid();
                        model.setImg_grid(ToolUtil.getPropThumnail(img_name[i], getContext()));
                        model.setTxt_name(txt_name[i] + i);
                        model.setTxt_name_jianjie(txt_name_jianjie[i]);
                        gridlist2.add(model);
                    }
                }
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

    // 数据
    private void initData() {
        list = new ArrayList<Categoryl>();
        for (int i = 0; i < name.length; i++) {
            Categoryl category = new Categoryl();
            category.setCategoryl_name(name[i]);
            list.add(category);
        }
        gridlist = new ArrayList<CategoryGrid>();
        for (int i = 0; i < img_name.length; i++) {
            CategoryGrid model = new CategoryGrid();
            model.setImg_grid(ToolUtil.getPropThumnail(img_name[i], this));
            model.setTxt_name(txt_name[i]);
            model.setTxt_name_jianjie(txt_name_jianjie[i]);
            gridlist.add(model);
        }
    }

    // 事件
    private void initEvent() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, list);
        list_category.setAdapter(categoryAdapter);
        CategoryGridAdapter categoryGridAdapter = new CategoryGridAdapter(this, gridlist);
        grid_category.setAdapter(categoryGridAdapter);
    }

}