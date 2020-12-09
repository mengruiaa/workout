package com.example.fitnessdemo.CLB;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.fitnessdemo.CLB.entity.Essay;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EssayDetailActivity extends AppCompatActivity {
    private ImageView ivBack;
    //文章标题
    private TextView clb_tv_essay_title;
    //文章人数
    private TextView clb_tv_essay_number;
    //文章子标题
    private TextView clb_tv_essay_childtitle;
    //文章图片
    private ImageView clb_iv_essay_pitcure;
    //文章内容
    private TextView clb_tv_essay_url;
    private Gson gson;
    private OkHttpClient okHttpClient;
    private Essay essay;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    essay = (Essay)msg.obj;
                    System.out.println(essay);
                    setDate();

                    break;

            }
        }
    };

    private void setDate() {
        clb_tv_essay_title.setText(essay.getTitle());
        clb_tv_essay_number.setText(essay.getNumber()+"万人阅读 · 2019/11/12");
        clb_tv_essay_childtitle.setText(essay.getChildTitle());
        String pitcure = essay.getPitcure();
        System.out.println(pitcure);
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this)
                .load(pitcure)
                .apply(requestOptions)
                .into(clb_iv_essay_pitcure);
        clb_tv_essay_url.setText(essay.getUrl());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay_detail);
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        gson = new Gson();
        okHttpClient = new OkHttpClient();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        System.out.println(title);
        findViews();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(EssayDetailActivity.this,Stair2Activity.class);
                intent1.putExtra("itemName", ConfigUtilCui.bobo);
                startActivity(intent1);
                overridePendingTransition(0, 0);//删除动画的代码
            }
        });
        initDate(ConfigUtil.SERVER_HOME+"EssayDetailShowServlet",title);
    }

    private void initDate(final String s, final String title) {
        new Thread(){
            @Override
            public void run() {
                MediaType type = MediaType.parse("text/plain");
                RequestBody requestBody = RequestBody.create(type,title);
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
                        Essay essay = gson.fromJson(result,Essay.class);
                        //获取图片名称
                        String picture = essay.getPitcure();
                        //拼接服务器地址（放置imgs文件夹）
                        String netHeader = ConfigUtil.SERVER_HOME+picture;
                        essay.setPitcure(netHeader);
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.obj = essay;
                        handler.sendMessage(msg);

                    }
                });
            }
        }.start();

    }

    private void findViews() {
        ivBack = findViewById(R.id.clb_iv_essay_back);
        clb_tv_essay_title = findViewById(R.id.clb_tv_essay_title);
        clb_tv_essay_number = findViewById(R.id.clb_tv_essay_number);
        clb_tv_essay_childtitle = findViewById(R.id.clb_tv_essay_childtitle);
        clb_iv_essay_pitcure = findViewById(R.id.clb_iv_essay_pitcure);
        clb_tv_essay_url = findViewById(R.id.clb_tv_essay_url);
    }
}
