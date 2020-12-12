package com.example.fitnessdemo.MR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.adapter.CourseListAdapter;
import com.example.fitnessdemo.MR.entity.Course;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
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

public class MyLikeCoursesActivity extends AppCompatActivity {
    private Gson gson=new Gson();
    private List<CoursePictureShow> cpss=new ArrayList<>();
    private Handler handler= new Handler(){//handlerThread.getLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    CourseListAdapter adapter=new CourseListAdapter(MyLikeCoursesActivity.this,cpss,R.layout.mr_course_picture_item);
                    ListView listView=findViewById(R.id.shoucang);
                    listView.setAdapter(adapter);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like_courses);
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
