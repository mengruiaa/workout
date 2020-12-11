package com.example.fitnessdemo.MR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.adapter.MyFragmentPagerAdapter;
import com.example.fitnessdemo.MR.entity.Course;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.MR.entity.CoursePlan;
import com.example.fitnessdemo.MR.entity.Video;
import com.example.fitnessdemo.MR.someFragments.IntroduceFragment;
import com.example.fitnessdemo.MR.someFragments.PlanFragment;
import com.example.fitnessdemo.MR.someFragments.VideoFragment;
import com.example.fitnessdemo.R;
import com.example.fitnessdemo.ZFT.Plan;
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

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CourseDetailActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient=new OkHttpClient();
    private Gson gson=new Gson();
    private String courseName;
    private SlidingTabLayout tb;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments=new ArrayList<>();
    private Handler handler= new Handler(){//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    tb.setViewPager(mViewPager, new String[]{"课程简介", "视频列表","相关计划"}, CourseDetailActivity.this, mFragments);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.mr_activity_course_detail);
        findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseDetailActivity.this.finish();
            }
        });
        Intent intent=getIntent();
        courseName= intent.getStringExtra("courseName");
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        tb=findViewById(R.id.tabLayout_cs);
        findViewById(R.id.btn_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( CourseDetailActivity.this, "收藏课程成功", Toast.LENGTH_SHORT).show();
                new Thread(){
                    @Override
                    public void run() {
                        addLikeCourses();
//                        Intent intent1=new Intent(CourseDetailActivity.this,MyLikeCoursesActivity.class);
//                        startActivity(intent1);
                    }
                }.start();
            }
        });
        getIntroduce();

    }

    private void addLikeCourses() {
        //2 创建Request对象
        //1) 使用RequestBody封装请求数据
        //获取待传输数据对应的MIME类型
        MediaType type = MediaType.parse("text/plain");
        //创建RequestBody对象
        RequestBody reqBody = RequestBody.create(type,gson.toJson(new CoursePlan(ConfigUtil.user_Name,courseName)));
        //2) 创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.SERVER_HOME + "AddLikeCourse")
                .post(reqBody)
                .build();
        //3. 创建CALL对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getIntroduce() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.SERVER_HOME + "GetIntroduce");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //设置网络请求的方式为POST
                    conn.setRequestMethod("POST");
                    //获取网络输出流
                    OutputStream out = conn.getOutputStream();
                    out.write(courseName.getBytes());
                    out.close();
                    InputStream in = conn.getInputStream();
                    //使用字符流读取
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取字符信息
                    String result = reader.readLine();
                    Course cs=gson.fromJson(result,Course.class);
                    in.close();
                    mFragments.add(new IntroduceFragment(cs));
                    //拿视频
                    URL url2 = new URL(ConfigUtil.SERVER_HOME + "GetVideos");
                    //获取网络输入流
                    InputStream in2 = url2.openStream();
                    BufferedReader reader2 = new BufferedReader(
                            new InputStreamReader(in2, "utf-8"));
                    //读取数据
                    String result2 = reader2.readLine();
                    in2.close();
                    Type type2= new TypeToken<List<Video>>(){}.getType();
                    List<Video> cs2=gson.fromJson(result2,type2);
                    mFragments.add(new VideoFragment(cs2));
                    //相关课程获取
                    URL url3 = new URL(ConfigUtil.SERVER_HOME + "GetZftPlan");
                    //获取网络输入流
                    InputStream in3 = url3.openStream();
                    BufferedReader reader3 = new BufferedReader(
                            new InputStreamReader(in3, "utf-8"));
                    //读取数据
                    String result3 = reader3.readLine();
                    in3.close();
                    Type type3= new TypeToken<List<Plan>>(){}.getType();
                    List<Plan> pz=gson.fromJson(result3,type3);
                    mFragments.add(new PlanFragment(pz));





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
