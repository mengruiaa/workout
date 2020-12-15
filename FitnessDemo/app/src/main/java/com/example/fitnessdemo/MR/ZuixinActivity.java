package com.example.fitnessdemo.MR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fitnessdemo.MR.adapter.CourseListAdapter;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ZuixinActivity extends AppCompatActivity {
    private Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.activity_zuixin);
        Intent intent=getIntent();
        String r=intent.getStringExtra("zuixin");
        Type type2= new TypeToken<List<CoursePictureShow>>(){}.getType();
        final List<CoursePictureShow> cs2=gson.fromJson(r,type2);
        CourseListAdapter adapter=new CourseListAdapter(this,cs2,R.layout.mr_course_picture_item);
        ListView listView=findViewById(R.id.zuixin);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ZuixinActivity.this, CourseDetailActivity.class);
                intent.putExtra("courseName",cs2.get(position).getName());
                startActivity(intent);
            }
        });
    }
}
