package com.example.fitnessdemo.LZYZYH.Vertical;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fitnessdemo.R;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;

public class CategoryActivity extends AppCompatActivity {
    private VerticalTabLayout mTablayout;
    private VerticalPager mViewpager;
    private List<String> datas = new ArrayList<String>();
    private CategoryActivity.vpsp vpsp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        initView();
        initData();
    }

    //    初始化控件
    private void initView() {
        mTablayout = (VerticalTabLayout) findViewById(R.id.tablayout);
        mViewpager = (VerticalPager) findViewById(R.id.viewpager);
    }

    //    初始化数据
    private void initData() {
        datas.add("推荐");
        datas.add("要闻");
        datas.add("娱乐");
        datas.add("科技");
        datas.add("汽车");
        datas.add("体育");

        //适配器
        vpsp = new vpsp(getSupportFragmentManager());
        mViewpager.setAdapter(vpsp);
        //进行关联
        mTablayout.setupWithViewPager(mViewpager);
        /*mTablayout.setTabBadge(7, 32);
        mTablayout.setTabBadge(2, -1);
        mTablayout.setTabBadge(3, -1);
        mTablayout.setTabBadge(4, -1);*/

    }

    //    自定义适配器
    class vpsp extends FragmentPagerAdapter {


        public vpsp(FragmentManager fm) {
            super(fm);
        }
        //返回选项卡的文本 ，，，添加选项卡

        @Override
        public CharSequence getPageTitle(int position) {
            return datas.get(position);
        }
        //动态创建fragment对象并返回

        @Override
        public Fragment getItem(int position) {
//            创建布局
            vcount vcount = new vcount();
            Bundle bundle = new Bundle();
//            放入值
            bundle.putString("name", datas.get(position));
//            放入布局文件中
            vcount.setArguments(bundle);
            return vcount;
        }
        //返回数量

        @Override
        public int getCount() {
            return datas.size();
        }


    }

}
