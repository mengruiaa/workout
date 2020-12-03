package com.example.fitnessdemo.CLB;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnessdemo.CLB.fragment.FirstFragment;
import com.example.fitnessdemo.CLB.fragment.SecondFragment;
import com.example.fitnessdemo.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stair2Activity extends AppCompatActivity {
    //滑动tab
    private SlidingTabLayout slidingTabLayout;
    //与滑动tab对应的viewPager
    private ViewPager slViewPage;
    //viewpager对应的fragment
    private ArrayList<Fragment> slFragments;
    //后退图片
    private ImageView ivBack;
    //关注按钮
    private Button btn_guanzhu;
    //二级词条简介
    private TextView tv_brief;
    //水平滑动布局
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout container;
    private String cities[] = new String[]{"乐趣啊啊", "游玩", "运动", "跑步", "午餐", "早餐"};
    private ArrayList<String> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.clb_activity_stair2);
        //获取布局id
        findview();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Stair2Activity.this,EncyclopaediaActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        String attentionNumber = intent.getStringExtra("attentionNumber");
        String brief = intent.getStringExtra("brief");
        tv_brief.setText(brief);
        System.out.println(itemName);
        System.out.println(attentionNumber);
        System.out.println(brief);
        slidingTabLayout =findViewById(R.id.sl);
        slViewPage=findViewById(R.id.vp00);
        slFragments = new ArrayList<>();
        slFragments.add(new FirstFragment());
        slFragments.add(new SecondFragment());
        //     无需编写适配器，一行代码关联TabLayout与ViewPager（看这里看这里）
        slidingTabLayout.setViewPager(slViewPage, new String[]{"官方必读", "免费课程"}, this, slFragments);
        btn_guanzhu = findViewById(R.id.clb_btn_stair2_1);
        btn_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_guanzhu.getText().equals("关注")){
                    btn_guanzhu.setText("已关注");
                }else{
                    btn_guanzhu.setText("关注");
                }

            }
        });
        //设置相关数据
        bindData();
        //初始化控件布局
        setUIRef();
        //绑定scrowView
        bindHZSWData();



    }

    private void findview() {
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        container = (LinearLayout) findViewById(R.id.horizontalScrollViewItemContainer);
        tv_brief = findViewById(R.id.clb_tv_stair2_brief);
        ivBack = findViewById(R.id.clb_iv_stair2_back);
    }

    //将集合中的数据绑定到HorizontalScrollView上
    private void bindHZSWData(){	//为布局中textview设置好相关属性
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;  //设置居中显示
        //设置边距
        layoutParams.setMargins(20, 10, 20, 10);
        for (int i = 0; i < data.size(); i++)
        {
            TextView textView = new TextView(this);
            textView.setText(data.get(i));
            Drawable drawable = getResources().getDrawable(R.drawable.sharp2);
            textView.setBackground(drawable);
            textView.setTextSize(14f);
            textView.setPadding(35,16,35,16);
            //设置宽度
//            textView.setWidth(200);
            textView.setTextColor(Color.rgb(128,128,128));
            textView.setLayoutParams(layoutParams);
            container.addView(textView);
            container.invalidate();
        }
    }

    //初始化布局中的控件
    private void setUIRef()
    {
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        container = (LinearLayout) findViewById(R.id.horizontalScrollViewItemContainer);
//        testTextView = (TextView) findViewById(R.id.testTextView);
    }
    //将字符串数组与集合绑定起来
    private void bindData() {
        //add all cities to our ArrayList
        Collections.addAll(data, cities);

    }


}
