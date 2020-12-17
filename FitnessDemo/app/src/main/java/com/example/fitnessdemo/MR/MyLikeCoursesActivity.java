package com.example.fitnessdemo.MR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.adapter.CourseListAdapter;
import com.example.fitnessdemo.MR.entity.Course;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.MR.entity.CoursePlan;
import com.example.fitnessdemo.R;
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

public class MyLikeCoursesActivity extends AppCompatActivity {
    private Gson gson=new Gson();
    private OkHttpClient okHttpClient=new OkHttpClient();
    private CourseListAdapter adapter;
    private ListView listView;
    private List<CoursePictureShow> cpss=new ArrayList<>();
    private Handler handler= new Handler(){//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    adapter=new CourseListAdapter(MyLikeCoursesActivity.this,cpss,R.layout.mr_course_picture_item);
                    listView=findViewById(R.id.shoucang);
                    listView.setAdapter(adapter);
                    //listView长按事件
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                                       final int position, long id) {
                            //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                            AlertDialog.Builder builder=new AlertDialog.Builder(MyLikeCoursesActivity.this);
                            builder.setMessage("确定删除?");
                            builder.setTitle("提示");

                            //添加AlertDialog.Builder对象的setPositiveButton()方法
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteMyLike(cpss.get(position).getName());
                                    if(cpss.remove(position)!=null){
                                        System.out.println("success");
                                    }else {
                                        System.out.println("failed");
                                    }
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getBaseContext(), "删除此课程", Toast.LENGTH_SHORT).show();
                                }
                            });

                            //添加AlertDialog.Builder对象的setNegativeButton()方法
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            builder.create().show();
                            return true;
                        }
                    });
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(MyLikeCoursesActivity.this, CourseDetailActivity.class);
                            intent.putExtra("courseName",cpss.get(position).getName());
                            startActivity(intent);
                        }
                    });

                    break;
            }
        }
    };

    private void deleteMyLike(final String courseName) {
        new Thread(){
            @Override
            public void run() {
                //2 创建Request对象
                //1) 使用RequestBody封装请求数据
                //获取待传输数据对应的MIME类型
                MediaType type = MediaType.parse("text/plain");
                //创建RequestBody对象
                RequestBody reqBody = RequestBody.create(type,gson.toJson(new CoursePlan(ConfigUtil.user_Name,courseName)));
                //2) 创建请求对象
                Request request = new Request.Builder()
                        .url(ConfigUtil.SERVER_HOME + "DeleteLikeCourse")
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
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_my_like_courses);
        findViewById(R.id.tianjia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MyLikeCoursesActivity.this,AllCoursesActivity.class);
                startActivity(intent);
                MyLikeCoursesActivity.this.finish();
            }
        });
        getMyLikeCourses();

    }
    private void getMyLikeCourses() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(ConfigUtil.SERVER_HOME + "GetLikeCourses");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //设置网络请求的方式为POST
                    conn.setRequestMethod("POST");
                    //获取网络输出流
                    OutputStream out = conn.getOutputStream();
                    out.write(ConfigUtil.user_Name.getBytes());
                    out.close();
                    InputStream in = conn.getInputStream();
                    //使用字符流读取
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取字符信息
                    String result = reader.readLine();
                    Type type= new TypeToken<List<Course>>(){}.getType();
                    List<Course> cs=gson.fromJson(result,type);
                    for(Course c:cs){
                        CoursePictureShow cps=new CoursePictureShow(c.getCourse_id(),c.getName(),ConfigUtil.SERVER_HOME +c.getPicture());
                        cpss.add(cps);
                    }
                    in.close();
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
