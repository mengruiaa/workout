package com.example.fitnessdemo.LZYZYH;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnessdemo.LZYZYH.activity.CategoryActivity;
import com.example.fitnessdemo.LZYZYH.activity.ListOfGoodsActivity;
import com.example.fitnessdemo.LZYZYH.activity.SearchActivity;
import com.example.fitnessdemo.LZYZYH.adapter.FruitAdapter;
import com.example.fitnessdemo.LZYZYH.model.Fruit;
import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuiJian extends Fragment {

    private ViewPager viewPager;  //轮播图模块
    private int[] mImg;
    private int[] mImg_id;
    private String[] mDec;
    private LinearLayout product_item;
    private ArrayList<ImageView> mImgList;
    private LinearLayout ll_dots_container;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private EditText etProductSearch;
    private Button btnProductSearch;

    private List<Fruit> fruitList = new ArrayList<Fruit>();
    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();
    private static String result;


    private LinearLayout cate1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mall_home,
                container,
                false);
        viewPager = view.findViewById(R.id.loopviewpager);
        ll_dots_container = view.findViewById(R.id.ll_dots_loop);
        product_item = view.findViewById(R.id.oneweek1);
        cate1 = view.findViewById(R.id.cate1);
        etProductSearch = view.findViewById(R.id.et_product_search);
        btnProductSearch = view.findViewById(R.id.btn_product_search);
        initLoopView();  //实现轮播图
        productItemClick();

        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = getActivity().openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write("<font color='black'><b>野小兽智能动感单车家用室内减肥器材</b></font><<br/><font color='red'>￥1999</font>\n");
            writer.write("ac\n");
            writer.write("<font color='black'><b>小米米家走步机多功能家用折叠小型室内健身跑步机</b></font><br/><br/><font color='red'>￥1799</font>\n");
            writer.write("g\n");
            writer.write("<font color='black'><b>麦瑞克家用多功能踏步机磁控健身器瘦腿瘦身健步踩踏板机室内静音</b></font><br/><br/><font color='red'>￥729</font>\n");
            writer.write("f\n");
            writer.write("<font color='black'><b>VFU高强度背心大胸防下垂运动内衣跑步防震聚拢文胸定型健身bra女</b></font><br/><br/><font color='red'>￥188</font>\n");
            writer.write("o\n");
            writer.write("<font color='black'><b>野小兽智能动感单车家用室内减肥器材</b></font><br/><br/><font color='red'>￥1999</font>\n");
            writer.write("ad\n");
            writer.write("<font color='black'><b>小米米家走步机多功能家用折叠小型室内健身跑步机</b></font><br/><br/><font color='red'>￥1799</font>\n");
            writer.write("ae\n");
            writer.write("<font color='black'><b>麦瑞克家用多功能踏步机磁控健身器瘦腿瘦身健步踩踏板机室内静音</b></font><br/><br/><font color='red'>￥729</font>\n");
            writer.write("f\n");
            writer.write("<font color='black'><b>VFU高强度背心大胸防下垂运动内衣跑步防震聚拢文胸定型健身bra女</b></font><br/><font color='red'>￥188</font>\n");
            writer.write("o\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writer!=null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        try {
            initFruits();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView =view.findViewById(R.id.l_recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);


        return view;
    }
    //配合子线程更新UI线程
    public static String updateUIThread(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        result = bundle.getString("product");
        return result;


    }

    /*
    *实现轮播图
     */
    private void initLoopView() {

        // 图片资源id数组
        mImg = new int[]{
                R.drawable.lun1,
                R.drawable.lun2,
                R.drawable.lun1,
                R.drawable.lun2,
                R.drawable.lun1
        };

        // 文本描述
        mDec = new String[]{
                "Test1",
                "Test2",
                "Test3",
                "Test4",
                "Test5"
        };

        mImg_id = new int[]{
                R.id.pager_img1,
                R.id.pager_img2,
                R.id.pager_img3,
                R.id.pager_img4,
                R.id.pager_img5
        };

        // 初始化要展示的5个ImageView
        mImgList = new ArrayList<ImageView>();
        ImageView imageView;
        View dotView;
        LinearLayout.LayoutParams layoutParams;
        for(int i=0;i<mImg.length;i++){
            //初始化要显示的图片对象
            imageView = new ImageView(getContext());
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(new PagerOnClickListener(getActivity().getApplicationContext()));
            mImgList.add(imageView);
            //加引导点
            dotView = new View(getContext());
            dotView.setBackgroundResource(R.drawable.dot);
            layoutParams = new LinearLayout.LayoutParams(10,10);
            if(i!=0){
                layoutParams.leftMargin=10;
            }
            //设置默认所有都不可用
            dotView.setEnabled(false);
            ll_dots_container.addView(dotView,layoutParams);
        }

        ll_dots_container.getChildAt(0).setEnabled(true);
        previousSelectedPosition=0;
        //设置适配器
        viewPager.setAdapter(new LoopViewAdapter(mImgList));
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) %mImgList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        viewPager.setCurrentItem(currentPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int newPosition = i % mImgList.size();
                ll_dots_container.getChildAt(previousSelectedPosition).setEnabled(false);
                ll_dots_container.getChildAt(newPosition).setEnabled(true);
                previousSelectedPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // 开启轮询
        new Thread(){
            public void run(){
                isRunning = true;
                while(isRunning){
                    try{
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //下一条
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
//                        }
//                    });
                }
            }
        }.start();

    }
    private void productItemClick(){
        cate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent();
                i1.setClass(getContext(), CategoryActivity.class);
                startActivity(i1);
            }
        });
        btnProductSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent();
                String s = etProductSearch.getText().toString();
                i2.putExtra("etProductSearch",s);
                i2.setClass(getContext(), ListOfGoodsActivity.class);
                startActivity(i2);
            }
        });
    }
    /*
    家庭健身房
     */
    private void initFruits() throws FileNotFoundException {
        ArrayList<String> a=load();
        for (int i = 0; i < a.size(); ) {
            //Fruit apple =new Fruit("apple",R.drawable.apple_pic);
            Fruit apple = new Fruit(a.get(i), getResources().getIdentifier(a.get(i+1),"drawable", "com.example.fitnessdemo"),i);
            fruitList.add(apple);
            i+=2;

        }
    }


    public ArrayList<String> load() throws FileNotFoundException {
        FileInputStream in ;
        BufferedReader reader = null;
        ArrayList<String> content =new ArrayList<String>();
        try {
            in = getActivity().openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            while (line != null) {
                content.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }
}

