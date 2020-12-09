package com.example.fitnessdemo.MR.someFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnessdemo.MR.CourseDetailActivity;
import com.example.fitnessdemo.MR.adapter.CourseListAdapter;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private View root;
    private String userName;
    private List<CoursePictureShow> coursePic;
    public ListFragment (List<CoursePictureShow> cpic){
        this.coursePic=cpic;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.mr_list_fragment, container, false);
        CourseListAdapter adapter=new CourseListAdapter(getContext(),coursePic,R.layout.mr_course_picture_item);
        ListView listView=root.findViewById(R.id.course_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), CourseDetailActivity.class);
                intent.putExtra("courseName",coursePic.get(position).getName());
                startActivity(intent);
            }
        });
        return root;
    }
}
