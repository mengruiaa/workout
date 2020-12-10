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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.adapter.MyFragmentPagerAdapter;
import com.example.fitnessdemo.MR.entity.Course;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.MR.someFragments.ListFragment;
import com.example.fitnessdemo.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
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
                    Log.i("yuhailongniubls", "handleMessage: "+mFragments.size());
                    MyFragmentPagerAdapter myFragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager());
                    mViewPager.setAdapter(myFragmentPagerAdapter);
                    myFragmentPagerAdapter.setmTitles(mStrs);
                    myFragmentPagerAdapter.setFs(mFragments);
                    myFragmentPagerAdapter.notifyDataSetChanged();
                    tb.setupWithViewPager(mViewPager);
                    break;
            }
        }
    };

    private TabLayout tb;
    private ViewPager mViewPager;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ArrayList<Fragment> mFragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.mr_activity_all_courses);
        findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCoursesActivity.this.finish();
            }
        });

        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        tb=findViewById(R.id.tabLayout_cs);
        findViewById(R.id.sousuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllCoursesActivity.this, SouSuoActivity.class);
                startActivity(intent);
            }
        });
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
                    Log.i("yuhailongniubls", "run: "+mStrs);

                    URL url2;
                    InputStream in2;
                    BufferedReader reader2;
                    String result2;
                    Type type2;
                    List<Course> cs2;
                    CoursePictureShow cps;
                    HttpURLConnection conn2;
                    OutputStream out;

                    for(String mstr:mStrs){
                        if(mstr.equals("全部")){

                            url2 = new URL(ConfigUtil.SERVER_HOME + "getALL");
                            //获取网络输入流
                            in2 = url2.openStream();
                            reader2 = new BufferedReader(
                                    new InputStreamReader(in2, "utf-8"));
                            //读取数据
                            result2 = reader2.readLine();
                            in2.close();
                            type2= new TypeToken<List<Course>>(){}.getType();
                            cs2=gson.fromJson(result2,type2);
                            List<CoursePictureShow> cpss=new ArrayList<>();
                            for(Course c:cs2){
                                cps=new CoursePictureShow(c.getCourse_id(),c.getName(),ConfigUtil.SERVER_HOME +c.getPicture());
                                cpss.add(cps);
                            }
                            System.out.println("quan部"+cpss);
                            mFragments.add(new ListFragment(cpss));

                        }else {
                            url2 = new URL(ConfigUtil.SERVER_HOME + "getATypeCourses");

                            conn2 = (HttpURLConnection) url2.openConnection();
                            //设置网络请求的方式为POST
                            conn2.setRequestMethod("POST");
                            //获取网络输出流
                            out = conn2.getOutputStream();
                            out.write(mstr.getBytes());
                            out.close();
                            in2 = conn2.getInputStream();
                            reader2 = new BufferedReader(
                                    new InputStreamReader(in2, "utf-8"));
                            //读取数据
                            result2 = reader2.readLine();
                            in2.close();
                            type2= new TypeToken<List<Course>>(){}.getType();
                            cs2=gson.fromJson(result2,type2);
                            List<CoursePictureShow> cpss=new ArrayList<>();
                            for(Course c:cs2){
                                cps=new CoursePictureShow(c.getCourse_id(),c.getName(),ConfigUtil.SERVER_HOME +c.getPicture());
                                cpss.add(cps);
                            }
                            mFragments.add(new ListFragment(cpss));
                        }
                    }

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
