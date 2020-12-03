package com.example.fitnessdemo.CLB;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnessdemo.CLB.adapter.CategoryAdapter;
import com.example.fitnessdemo.CLB.adapter.MyPagerAdapter;
import com.example.fitnessdemo.CLB.banner.GlideImageLoader;
import com.example.fitnessdemo.CLB.dialog.MyViewPager;
import com.example.fitnessdemo.CLB.entity.Cyclopedia1;
import com.example.fitnessdemo.CLB.entity.Entry1;
import com.example.fitnessdemo.CLB.fragment.TestFragment;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.ShouYeActivity;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EncyclopaediaActivity extends AppCompatActivity {
    private CategoryAdapter mAdapter;
    private RecyclerView recyclerView;
    private Gson gson;
    private OkHttpClient okHttpClient;
    private MyViewPager viewPager;
    private LinearLayoutManager mLayoutManager;
    private Banner banner;
    private List<Entry1> lists;
    private ImageView ivback;
    private List<Cyclopedia1> cyclopediaLists;
    ArrayList<Fragment> datas = new ArrayList<Fragment>();
    private Handler handler = new Handler(){//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1://如果服务端返回的数据是字符串
                    lists = (List<Entry1>) msg.obj;
                    //获取二级目录所有内容(将下方内容放到initSecondStairCatalog)
                    initSecondStairCatalog();


                    break;
                case 2:
                    cyclopediaLists =(List<Cyclopedia1>) msg.obj;
                    //设置内容
                    System.out.println("走到这里了");
                    System.out.println("成功与否："+lists);
                    System.out.println("成功与否："+cyclopediaLists);
                    //设置Fragment
                    setFragment();
                    //设置一级目录和viewPager
                    setStairCatalog();


                    break;
                case 3:
                    //获取图片资源路径
                    break;
            }
        }
    };

    /**
     * 获取二级目录所有内容
     */
    private void initSecondStairCatalog() {
        System.out.println("fragment测试："+lists);
        Thread thread = new Thread(){
            @Override
            public void run() {
                //实现请求数据.
                Request request = new Request.Builder()
                        .url(ConfigUtil.SERVER_HOME+"CyclopediaListServlet")
//                .post(reqBody)
                        .build();
                //3. 创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4. 提交请求并获取响应
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("clb", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        System.out.println("result"+result);
                        Type type = new TypeToken<Collection<Cyclopedia1>>(){}.getType();
                        List<Cyclopedia1> cyclopedias = gson.fromJson(result,type);
                        for( Cyclopedia1 cyclopedia : cyclopedias){
                            //获取图片名称
                            String picture = cyclopedia.getPitcure();
                            //拼接服务器地址（放置imgs文件夹）
                            String netHeader = ConfigUtil.SERVER_HOME+picture;

                            cyclopedia.setPitcure(netHeader);
                        }
                        System.out.println("修改后的cyclopedias"+cyclopedias);
//                        cyclopediaLists = cyclopedias;
                        Message msg = handler.obtainMessage();
                        msg.what = 2;
                        msg.obj = cyclopedias;
                        handler.sendMessage(msg);

                    }
                });

            }

        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


    /**
     * 设置每一个一级目录对应的Fragment
     */
    private void setFragment() {
        System.out.println("成功与否："+lists);
        System.out.println("成功与否："+cyclopediaLists);
                for(Entry1 entry :lists){
                    System.out.println(lists.size());
                    //获取相应的名字对应的数据源
                    List<Cyclopedia1> specialCyclopedia = new ArrayList<>();
                      for(Cyclopedia1 cyclopedia : cyclopediaLists){
                          if(entry.getName().equals(cyclopedia.getParentName())){
                              Cyclopedia1 cyclopedia1 = new Cyclopedia1();
                              System.out.println(cyclopedia.getId());
                              System.out.println("caocaoocoa:"+cyclopedia.getName());
                              System.out.println("数量"+cyclopedia.getAttentionNumber());
                              String name = cyclopedia.getName();
                              String parentName = cyclopedia.getParentName();
                              String pitcure = cyclopedia.getPitcure();
                              String brief = cyclopedia.getBrief();
                              cyclopedia1.setId(cyclopedia.getId());
                              cyclopedia1.setName(name);
                              cyclopedia1.setParentName(parentName);
                              int attentionNumber = cyclopedia.getAttentionNumber();
                              System.out.println("数量"+attentionNumber);
                              cyclopedia1.setAttentionNumber(attentionNumber);
                              cyclopedia1.setPitcure(pitcure);
                              cyclopedia1.setBrief(brief);
                              specialCyclopedia.add(cyclopedia1);
//                              System.out.println("为什莫没有："+specialCyclopedia);
                          }
                      }
                    TestFragment testFragment = new TestFragment(specialCyclopedia);
                    datas.add(testFragment);
                }



    }

    //获取Recyclerview点击的哪一个
    private int Pos;
    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.clb_activity_encyclopaedia);
        ivback = findViewById(R.id.iv_clb_cusp1);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(EncyclopaediaActivity.this, ShouYeActivity.class);
                startActivity(intent);
            }
        });
        //初始化Gson
        gson = new Gson();
        //初始化OkHttpClient
        okHttpClient = new OkHttpClient();
        //设置轮播图
        setBanner();
        //获取数据(一级目录)
        initStairCatalogData(ConfigUtil.SERVER_HOME +"SelectEntryServlet");

    }
    private void setStairCatalog() {
        recyclerView = findViewById(R.id.follow_category_rv);
//        viewPager = findViewById(R.id.follow_fragment_viewpager);
        viewPager = findViewById(R.id.follow_fragment_viewpager);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
        //给RecyclerView设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);
        //设置动画(默认开启动画？)
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter = new CategoryAdapter(this,lists, new CategoryAdapter.OnItemClickListener(){
            @Override
            public void onClick(int pos) {
                System.out.println("pos"+pos);
                Pos = pos;
                mAdapter.setBg(pos);
                viewPager.setCurrentItem(pos);
            }
        });
        recyclerView.setAdapter(mAdapter);
        //设置滑动事件
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        //
        System.out.println("最后走这里");
        myPagerAdapter.setData(datas);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                recyclerView.smoothScrollToPosition(position);
                mAdapter.setBg(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    //获取数据库数据
    private void initStairCatalogData(final String s) {
        new Thread(){
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(s)
//                .post(reqBody)
                        .build();
                //3. 创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4. 提交请求并获取响应
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("clb", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        System.out.println(result);
                        Type type = new TypeToken<Collection<Entry1>>(){}.getType();
                        List<Entry1> entrys = gson.fromJson(result,type);
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.obj = entrys;
                        handler.sendMessage(msg);

                    }
                });
            }
        }.start();


    }

    private void setBanner() {
        banner =findViewById(R.id.banner);
        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设计图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.clb_baike);
        list.add(R.mipmap.clb_baike);
        banner.setImages(list);
        //设置banner的动画效果
        banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置轮播时间
        banner.setDelayTime(2500);
        //启动
        banner.start();

    }
}

