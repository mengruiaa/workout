package com.example.fitnessdemo.CLB;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnessdemo.CLB.entity.Cyclopedia1;
import com.example.fitnessdemo.CLB.entity.Essay;
import com.example.fitnessdemo.CLB.fragment.FirstFragment;
import com.example.fitnessdemo.CLB.fragment.SecondFragment;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.entity.Course;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.MR.someFragments.ListFragment;
import com.example.fitnessdemo.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    //item名字1
    private String itemName;
    //二级词条名称,简介，人数
    private TextView tvName;
    private TextView tv_brief;
    private TextView tv_Number;
    //水平滑动布局
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout container;
    private String cities[] = new String[]{"健康餐", "增肌饮食", "饮食计划", "懒人食谱", "孕期饮食", "早餐"};
    private ArrayList<String> data = new ArrayList<>();
    private Gson gson;
    private OkHttpClient okHttpClient;
    //文章显示
    private List<Essay> essays;
    //相关课程显示
    private List<CoursePictureShow> cpss=new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //赋值
                    essays = (List<Essay>)msg.obj;
                    System.out.println("essays:"+essays);
                    //获取数据课程
                    initDate2(ConfigUtil.SERVER_HOME+"GetCourseByEssay");
                    break;
                case 2:

                    setDate();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.clb_activity_stair2);
        //初始化Gson
        gson = new Gson();
        //初始化OkHttpClient
        okHttpClient = new OkHttpClient();
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
        itemName = intent.getStringExtra("itemName");
        String attentionNumber="";
        String brief="";
//        tv_brief.setText(brief);
        for(Cyclopedia1 cyo : ConfigUtilCui.list){
            if(cyo.getName().equals(itemName)){
                attentionNumber  = cyo.getAttentionNumber()+" 人关注·2310条内容";
                brief = cyo.getBrief();
            }
        }
        tvName.setText(itemName);
        tv_brief.setText(brief);
        tv_Number.setText(attentionNumber);
        System.out.println(itemName);
        System.out.println(attentionNumber);
        System.out.println(brief);
        slidingTabLayout =findViewById(R.id.sl);
        slViewPage=findViewById(R.id.vp00);
        slFragments = new ArrayList<>();
        //获取数据
        initDate(ConfigUtil.SERVER_HOME+"EssayListServlet");

//        setDate();
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

    private void initDate2(final String s) {
        new Thread(){
            @Override
            public void run() {
                MediaType type = MediaType.parse("text/plain");
                RequestBody requestBody = RequestBody.create(type,itemName);
                Request request = new Request.Builder()
                        .url(s)
                        .post(requestBody)
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
//                        System.out.println("??"+result);
                        Type type= new TypeToken<List<Course>>(){}.getType();
                        List<Course> cs=gson.fromJson(result,type);
                        for(Course c:cs){
                            CoursePictureShow cps=new CoursePictureShow(c.getCourse_id(),c.getName(),ConfigUtil.SERVER_HOME +c.getPicture());
                            cpss.add(cps);
                        }
                        Message msg = handler.obtainMessage();
                        msg.what = 2;
                        handler.sendMessage(msg);

                    }
                });
            }
        }.start();

    }

    private void setDate() {
        slFragments.add(new FirstFragment(essays));
//        slFragments.add(new SecondFragment());
        slFragments.add(new ListFragment(cpss));
        //     无需编写适配器，一行代码关联TabLayout与ViewPager（看这里看这里）
        slidingTabLayout.setViewPager(slViewPage, new String[]{"官方必读", "免费课程"}, this, slFragments);

    }

    private void initDate(final String s) {
        new Thread(){
            @Override
            public void run() {
                MediaType type = MediaType.parse("text/plain");
                RequestBody requestBody = RequestBody.create(type,itemName);
                Request request = new Request.Builder()
                        .url(s)
                        .post(requestBody)
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
//                        System.out.println("??"+result);
                        Type type = new TypeToken<Collection<Essay>>(){}.getType();
                        List<Essay> lists = gson.fromJson(result,type);
                        for( Essay essay : lists){
                            //获取图片名称
                            String picture = essay.getPitcure();
                            //拼接服务器地址（放置imgs文件夹）
                            String netHeader = ConfigUtil.SERVER_HOME+picture;
                            essay.setPitcure(netHeader);
                        }
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.obj = lists;
                        handler.sendMessage(msg);

                    }
                });
            }
        }.start();


    }

    private void findview() {
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        container = (LinearLayout) findViewById(R.id.horizontalScrollViewItemContainer);
        tv_brief = findViewById(R.id.clb_tv_stair2_brief);
        tvName = findViewById(R.id.clb_tv_stair2_1);
        ivBack = findViewById(R.id.clb_iv_stair2_back);
        tv_Number = findViewById(R.id.tv_stair2_number);
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
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(Stair2Activity.this,Stair2Activity.class);
                    intent.putExtra("itemName",data.get(finalI));
                    startActivity(intent);

                }
            });
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
