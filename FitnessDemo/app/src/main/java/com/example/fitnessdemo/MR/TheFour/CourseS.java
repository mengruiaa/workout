package com.example.fitnessdemo.MR.TheFour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.bumptech.glide.Glide;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.AllCoursesActivity;
import com.example.fitnessdemo.MR.RemenActivity;
import com.example.fitnessdemo.MR.SouSuoActivity;
import com.example.fitnessdemo.MR.ZuixinActivity;
import com.example.fitnessdemo.MR.adapter.MyFragmentPagerAdapter;
import com.example.fitnessdemo.MR.entity.Course;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.MR.entity.Video;
import com.example.fitnessdemo.MR.someFragments.ListFragment;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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

public class CourseS extends Fragment {
    private View root;
    private String userName;
    private List<CoursePictureShow> cpss;
    private List<CoursePictureShow> cpss2;
    private ArrayList<Fragment> mFragments=new ArrayList<>();
    private ArrayList<Fragment> mFragments2=new ArrayList<>();
    private ViewPager vp1;
    private ViewPager vp2;
    private Gson gson=new Gson();
    //定义Handler对象属性
    private Handler handler= new Handler(){//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1://如果服务端返回的数据是字符串
                    for(int i=0;i<cpss.size();i+=2){
                        if(cpss.size()-i>=2){
                            ListFragment lf=new ListFragment(cpss.subList(i,i+2));
                            System.out.println("这里结束1");
                            mFragments.add(lf);
                        }else {
                            ListFragment lf2=new ListFragment(cpss.subList(i,cpss.size()));
                            mFragments.add(lf2);
                        }
                    }
                    Log.i("rjjjj1", "onCreateView: "+mFragments.size());
                    MyFragmentPagerAdapter myFragmentPagerAdapter=new MyFragmentPagerAdapter(getFragmentManager());
                    vp2.setAdapter(myFragmentPagerAdapter);
                    vp2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    myFragmentPagerAdapter.setFs(mFragments);
                    myFragmentPagerAdapter.notifyDataSetChanged();
                    System.out.println("这里开始1");

                    for(int i=0;i<cpss2.size();i+=2){
                        if(cpss2.size()-i>=2){
                            ListFragment lf=new ListFragment(cpss2.subList(i,i+2));
                            System.out.println("这里结束2");
                            mFragments2.add(lf);
                        }else {
                            ListFragment lf2=new ListFragment(cpss2.subList(i,cpss2.size()));
                            mFragments2.add(lf2);
                        }
                    }
                    Log.i("rjjjj2", "onCreateView: "+mFragments2.size());
                    MyFragmentPagerAdapter myFragmentPagerAdapter2=new MyFragmentPagerAdapter(getFragmentManager());
                    vp1.setAdapter(myFragmentPagerAdapter2);
                    vp1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    myFragmentPagerAdapter2.setFs(mFragments2);
                    myFragmentPagerAdapter2.notifyDataSetChanged();
                    root.findViewById(R.id.remenquanbu).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(getContext(), RemenActivity.class);
                            intent.putExtra("remen",gson.toJson(cpss2));
                            startActivity(intent);
                        }
                    });
                    root.findViewById(R.id.zuixinquanbu).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(getContext(), ZuixinActivity.class);
                            intent.putExtra("zuixin",gson.toJson(cpss));
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(root==null){
            root = inflater.inflate(R.layout.mr_course, container, false);
            userName= ConfigUtil.user_Name;
            vp1=root.findViewById(R.id.vp1);
            vp2=root.findViewById(R.id.vp2);

            getZuiXin();
            root.findViewById(R.id.sousuo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getContext(), SouSuoActivity.class);
                    startActivity(intent);
                }
            });
            root.findViewById(R.id.quanbu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getContext(), AllCoursesActivity.class);
                    startActivity(intent);
                }
            });
            Animation enterAnimation = new AlphaAnimation(0f, 1f);
            enterAnimation.setDuration(600);
            enterAnimation.setFillAfter(true);

            Animation exitAnimation = new AlphaAnimation(1f, 0f);
            exitAnimation.setDuration(600);
            exitAnimation.setFillAfter(true);
            NewbieGuide.with(this)
                    .setLabel("mengrui01")
                    .addGuidePage(GuidePage.newInstance()
 //                           .setBackgroundColor(0xE6E6FA66)
                            .addHighLight(root.findViewById(R.id.sousuo))
                            .setLayoutRes(R.layout.view_guide_simple,R.id.guan)
                            .setEnterAnimation(enterAnimation)//进入动画
                            .setExitAnimation(exitAnimation))
                    .alwaysShow(true)
                    .show();
            return root;
        }else {
            return root;
        }
    }

    private void getZuiXin() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.SERVER_HOME + "GetZuiXin");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //设置网络请求的方式为POST
                    conn.setRequestMethod("POST");
                    //获取网络输出流
                    OutputStream out = conn.getOutputStream();
                    out.write("最新课程".getBytes());
                    out.close();
                    InputStream in = conn.getInputStream();
                    //使用字符流读取
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取字符信息
                    String result = reader.readLine();
                    Type type= new TypeToken<List<Course>>(){}.getType();
                    List<Course> cs=gson.fromJson(result,type);
                    Log.i("zlrs1", "handleMessage: "+cs.size());

                    cpss=new ArrayList<>();
                    cpss2=new ArrayList<>();
                    for(Course c:cs){
                        CoursePictureShow cps=new CoursePictureShow(c.getCourse_id(),c.getName(),ConfigUtil.SERVER_HOME +c.getPicture());
                        cpss.add(cps);
                    }
                    in.close();
                    URL url2 = new URL(ConfigUtil.SERVER_HOME + "GetZuiXin");
                    HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
                    //设置网络请求的方式为POST
                    conn2.setRequestMethod("POST");
                    //获取网络输出流
                    OutputStream out2 = conn2.getOutputStream();
                    out2.write("热门课程".getBytes());
                    out2.close();
                    InputStream in2 = conn2.getInputStream();
                    //使用字符流读取
                    BufferedReader reader2 = new BufferedReader(
                            new InputStreamReader(in2, "utf-8"));
                    //读取字符信息
                    String result2 = reader2.readLine();
                    Type type2= new TypeToken<List<Course>>(){}.getType();
                    List<Course> cs2=gson.fromJson(result2,type2);
                    Log.i("zlrs2", "handleMessage: "+cs2.size());

                    for(Course c2:cs2){
                        CoursePictureShow cps2=new CoursePictureShow(c2.getCourse_id(),c2.getName(),ConfigUtil.SERVER_HOME +c2.getPicture());
                        cpss2.add(cps2);
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
