package com.example.fitnessdemo.LZYZYH;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.fitnessdemo.LZYZYH.adapter.MyAdapter;
import com.example.fitnessdemo.LZYZYH.model.Fruit;
import com.example.fitnessdemo.LZYZYH.model.Icon;
import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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

    private SmartRefreshLayout refreshlayout;

    private MyAdapter mAdapter;

    private List<Fruit> fruitList = new ArrayList<Fruit>();
    private Map<String, ImageView> imageViewMap = new HashMap<>();
    private Map<String, TextView> textViewMap = new HashMap<>();
    private TextView findAll;
    private static String result;
    private Context mContext;
    private GridView grid_photo;
    private ArrayList<Icon> mData = null;


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
      //  cate1 = view.findViewById(R.id.cate1);
        etProductSearch = view.findViewById(R.id.et_product_search);
        btnProductSearch = view.findViewById(R.id.btn_product_search);
        findAll = view.findViewById(R.id.findAll);
        initLoopView();  //实现轮播图
        productItemClick();

        mContext = getContext();
        grid_photo = (GridView) view.findViewById(R.id.grid_button_menu);

        mData = new ArrayList<Icon>();
        mData.add(new Icon(R.drawable.pinpai, "品牌精选"));
        mData.add(new Icon(R.drawable.zhineng, "智能硬件"));
        mData.add(new Icon(R.drawable.woman, "女子服饰"));
        mData.add(new Icon(R.drawable.man, "男子服饰"));
        mData.add(new Icon(R.drawable.sport, "运动装备"));
        mData.add(new Icon(R.drawable.qingshi, "轻食代餐"));
        mData.add(new Icon(R.drawable.huwai, "运动生活"));
        mData.add(new Icon(R.drawable.shenghuo, "健身器材"));

        mAdapter = new MyAdapter<Icon>(mData, R.layout.button_menu_item) {
            @Override
            public void bindView(ViewHolder holder, Icon  obj) {

                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());

            }
        };

        grid_photo.setAdapter(mAdapter);

        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i4 = new Intent();
                i4.putExtra("typeId",position+1+"");
                i4.setClass(getContext(), CategoryActivity.class);
                startActivity(i4);
            }
        });



        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = getActivity().openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write("免打孔家用单杠健身引体向上室内体育用品门上墙体");
            writer.write("\n<font color='red'>￥49.9</font>\n");
            writer.write("ag\n");
            writer.write("小米体脂秤2智能精准减肥电子称迷你健康家用体重秤脂肪秤");
            writer.write("\n<font color='red'>￥99</font>\n");
            writer.write("j\n");
            writer.write("麦瑞克家用多功能踏步机磁控健身器瘦腿瘦身健步踩踏板机室内静音");
            writer.write("\n<font color='red'>￥729</font>\n");
            writer.write("f\n");
            writer.write("安德玛官方UA Armour男硬汉装运动健身修身紧身裤1289577");
            writer.write("\n<font color='red'>￥169</font>\n");
            writer.write("ac\n");
            writer.write("迪卡侬计数跳绳成人运动专用男女健身专业燃脂儿童小学生绳子FICS");
            writer.write("\n<font color='red'>￥59.9</font>\n");
            writer.write("ae\n");
            writer.write("迪卡侬悬挂式训练带多功能拉力绳正品家用健身阻力带力量训练CROB");
            writer.write("\n<font color='red'>￥99.9</font>\n");
            writer.write("ai\n");
            writer.write("VFU高强度背心大胸防下垂运动内衣跑步防震聚拢文胸定型健身bra女");
            writer.write("\n<font color='red'>￥188</font>\n");
            writer.write("o\n");
            writer.write("小米米家走步机多功能家用折叠小型室内健身跑步机");
            writer.write("\n<font color='red'>￥1799</font>\n");
            writer.write("g\n");
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
        System.out.println(fruitList.size()+"lzy");
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
////                    下一条
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
        btnProductSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent();
                String s = etProductSearch.getText().toString();
                i2.putExtra("etProductSearch",s);
                System.out.println(s);
                i2.setClass(getContext(), SearchActivity.class);
                startActivity(i2);
            }
        });
        findAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent();
                i3.setClass(getContext(), ListOfGoodsActivity.class);
                startActivity(i3);
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
            Fruit apple = new Fruit(a.get(i), getResources().getIdentifier(a.get(i+2),"drawable", "com.example.fitnessdemo"),i,a.get(i+1));
            fruitList.add(apple);
            i+=3;

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

