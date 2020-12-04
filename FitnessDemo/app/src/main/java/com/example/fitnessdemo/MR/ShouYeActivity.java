package com.example.fitnessdemo.MR;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import com.example.fitnessdemo.MR.TheFour.CourseS;
import com.example.fitnessdemo.ws.MyInfo;
import com.example.fitnessdemo.MR.TheFour.ShowYe;
import com.example.fitnessdemo.LZYZYH.TuiJian;
import com.example.fitnessdemo.R;


import java.util.HashMap;
import java.util.Map;

public class ShouYeActivity extends AppCompatActivity {
    private Map<String, ImageView> imageViewMap =new HashMap<>();
    private Map<String, TextView> textViewMap =new HashMap<>();
    private FragmentTabHost tabHost;
    //存用户手机号
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.mr_activity_shou_ye);
        tabHost = findViewById(android.R.id.tabhost);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //将买家账号通过bundle传给其他的Fragment
//        bundle=new Bundle();
//        bundle.putString("userName",ConfigUtil.user_Name);
        initFragmentTabHost();
        //推送发货消息，客户端显示订单页面
//        Intent intent=getIntent();
//        String action=intent.getAction();
//        if("fahuo".equals(action)){
//            //订单是第三个fragment
//            tabHost.setCurrentTab(2);
//        }
    }
    private void initFragmentTabHost(){
        tabHost.setup(this, getSupportFragmentManager(),
                android.R.id.tabcontent);
        TabHost.TabSpec tab1=tabHost.newTabSpec("first")
                .setIndicator(getSpecView("first","首页",R.drawable.first));
        TabHost.TabSpec tab2=tabHost.newTabSpec("second")
                .setIndicator(getSpecView("second","课程",R.drawable.second));
        TabHost.TabSpec tab3=tabHost.newTabSpec("third")
                .setIndicator(getSpecView("third","推荐",R.drawable.third));
        TabHost.TabSpec tab4=tabHost.newTabSpec("four")
                .setIndicator(getSpecView("four","我的",R.drawable.third));

        tabHost.addTab(tab1, ShowYe.class, bundle);
        tabHost.addTab(tab2, CourseS.class,bundle);
        tabHost.addTab(tab3, TuiJian.class,bundle);
        tabHost.addTab(tab4, MyInfo.class,bundle);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "first":
                        imageViewMap.get("first").setImageResource(R.drawable.first1);
                        imageViewMap.get("second").setImageResource(R.drawable.second);
                        imageViewMap.get("third").setImageResource(R.drawable.third);
                        imageViewMap.get("four").setImageResource(R.drawable.four);
                        textViewMap.get("first").setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                        textViewMap.get("second").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("third").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("four").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "second":
                        imageViewMap.get("first").setImageResource(R.drawable.first);
                        imageViewMap.get("second").setImageResource(R.drawable.second1);
                        imageViewMap.get("third").setImageResource(R.drawable.third);
                        imageViewMap.get("four").setImageResource(R.drawable.four);
                        textViewMap.get("first").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("second").setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                        textViewMap.get("third").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("four").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "third":
                        imageViewMap.get("first").setImageResource(R.drawable.first);
                        imageViewMap.get("second").setImageResource(R.drawable.second);
                        imageViewMap.get("third").setImageResource(R.drawable.third1);
                        imageViewMap.get("four").setImageResource(R.drawable.four);
                        textViewMap.get("first").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("second").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("third").setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                        textViewMap.get("four").setTextColor(getResources().getColor(android.R.color.black));
                        break;
                    case "four":
                        imageViewMap.get("first").setImageResource(R.drawable.first);
                        imageViewMap.get("second").setImageResource(R.drawable.second);
                        imageViewMap.get("third").setImageResource(R.drawable.third);
                        imageViewMap.get("four").setImageResource(R.drawable.four1);
                        textViewMap.get("first").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("second").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("third").setTextColor(getResources().getColor(android.R.color.black));
                        textViewMap.get("four").setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                        break;
                }
            }
        });
        tabHost.setCurrentTab(0);
        imageViewMap.get("first").setImageResource(R.drawable.first1);
        textViewMap.get("first").setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
    }

    public View getSpecView(String tag, String title, int picture){
        View view=getLayoutInflater().inflate(R.layout.mr_spec_layout,null);

        ImageView pict=view.findViewById(R.id.picture);
        TextView textView=view.findViewById(R.id.title);

        pict.setImageResource(picture);
        textView.setText(title);

        imageViewMap.put(tag,pict);
        textViewMap.put(tag,textView);
        return view;
    }
}
