package com.example.fitnessdemo.MR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.adapter.MyFragmentPagerAdapter;
import com.example.fitnessdemo.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AllCoursesActivity extends AppCompatActivity {
    private List<String> mStrs=new ArrayList<>();
    private Gson gson=new Gson();
    private Handler handler= new Handler(){//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    myFragmentPagerAdapter.setmTitles(mStrs);

                    //将TabLayout与ViewPager绑定在一起
                    mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
                    mTabLayout.setupWithViewPager(mViewPager);
                break;
            }
        }
    };

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ArrayList<Fragment> mFragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.mr_activity_all_courses);

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);
        getAllTypes();
    }
    private void getAllTypes() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.SERVER_HOME + "getAllCourTypes");
                    //获取网络输入流
                    InputStream in = url.openStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取数据
                    String result = reader.readLine();
                    in.close();
                    Type type= new TypeToken<List<String>>(){}.getType();
                    mStrs=gson.fromJson(result,type);
                    Message msg = handler.obtainMessage();
                    //设置Message对象的参数
                    msg.what = 1;

                    //发送Message
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
