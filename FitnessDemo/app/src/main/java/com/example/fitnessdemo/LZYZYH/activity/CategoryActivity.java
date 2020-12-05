package com.example.fitnessdemo.LZYZYH.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.LZYZYH.adapter.CategoryAdapter;
import com.example.fitnessdemo.LZYZYH.adapter.CategoryGridAdapter;
import com.example.fitnessdemo.LZYZYH.model.Category;
import com.example.fitnessdemo.LZYZYH.model.CategoryGrid;
import com.example.fitnessdemo.LZYZYH.util.ToolUtil;
import com.example.fitnessdemo.R;

import java.util.ArrayList;

import static com.mob.MobSDK.getContext;

//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//import com.example.fitnessdemo.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import q.rorbin.verticaltablayout.VerticalTabLayout;
//
//
//public class CategoryActivity extends AppCompatActivity {
//    private VerticalTabLayout mTablayout;
//    private VerticalPager mViewpager;
//    private List<String> datas = new ArrayList<String>();
//    private CategoryActivity.vpsp vpsp;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mall_category_activity);
//        initView();
//        initData();
//    }
//
//    //    初始化控件
//    private void initView() {
//        mTablayout = (VerticalTabLayout) findViewById(R.id.tablayout);
//        mViewpager = (VerticalPager) findViewById(R.id.viewpager);
//    }
//
//    //    初始化数据
//    private void initData() {
//        datas.add("智能单车");
//        datas.add("跑步机");
//        datas.add("智能秤");
//        datas.add("智能手环");
//
//        //适配器
//        vpsp = new vpsp(getSupportFragmentManager());
//        mViewpager.setAdapter(vpsp);
//        //进行关联
//        mTablayout.setupWithViewPager(mViewpager);
//        /*mTablayout.setTabBadge(7, 32);
//        mTablayout.setTabBadge(2, -1);
//        mTablayout.setTabBadge(3, -1);
//        mTablayout.setTabBadge(4, -1);*/
//
//    }
//
//    //    自定义适配器
//    class vpsp extends FragmentPagerAdapter {
//
//
//        public vpsp(FragmentManager fm) {
//            super(fm);
//        }
//        //返回选项卡的文本 ，，，添加选项卡
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return datas.get(position);
//        }
//        //动态创建fragment对象并返回
//
//        @Override
//        public Fragment getItem(int position) {
////            创建布局
//            vcount vcount = new vcount();
//            Bundle bundle = new Bundle();
////            放入值
//            bundle.putString("name", datas.get(position));
////            放入布局文件中
//            vcount.setArguments(bundle);
//            return vcount;
//        }
//        //返回数量
//
//        @Override
//        public int getCount() {
//            return datas.size();
//        }
//
//
//    }
//
//}
public class CategoryActivity extends AppCompatActivity {
    private ListView list_category;
    private GridView grid_category;
    private EditText ed_soushuo;
    private String[] name = { "智能设备", "品牌精选", "女子服饰", "新品推荐", "新品推荐", "新品推荐", "新品推荐", "新品推荐" };
    private ArrayList<Category> list;
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
        ed_soushuo = (EditText) findViewById(R.id.ed_soushuo);
        ed_soushuo.setInputType(InputType.TYPE_NULL);
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
        ed_soushuo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                if (arg1) {
                    // 此处为得到焦点时的处理内容
                    Intent intent = new Intent(arg0.getContext(), SearchActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ed_soushuo.clearFocus();
    }

    // 数据
    private void initData() {
        list = new ArrayList<Category>();
        for (int i = 0; i < name.length; i++) {
            Category category = new Category();
            category.setName(name[i]);
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
