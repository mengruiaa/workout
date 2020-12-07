package com.example.fitnessdemo.MR.someFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.MR.CourseDetailActivity;
import com.example.fitnessdemo.MR.adapter.CourseListAdapter;
import com.example.fitnessdemo.MR.entity.Course;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.R;

import java.util.List;

public class IntroduceFragment extends Fragment {
    private View root;
    private Course course;
    public IntroduceFragment (Course course){
        this.course=course;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.mr_course_introduce, container, false);
        TextView name=root.findViewById(R.id.name);
        name.setText(course.getName());
        TextView part=root.findViewById(R.id.part);
        part.setText(course.getBody_part());
        TextView level=root.findViewById(R.id.level);
        level.setText(course.getLevel());
        TextView introduce=root.findViewById(R.id.introduce);
        introduce.setText(course.getIntroduce());
        return root;
    }

}
