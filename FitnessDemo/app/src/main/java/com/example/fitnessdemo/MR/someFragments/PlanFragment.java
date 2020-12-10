package com.example.fitnessdemo.MR.someFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessdemo.MR.CourseDetailActivity;
import com.example.fitnessdemo.MR.adapter.CourseListAdapter;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.R;
import com.example.fitnessdemo.ZFT.Plan;
import com.example.fitnessdemo.ZFT.PlanAdapter;

import java.util.List;

public class PlanFragment extends Fragment {
    private View root;
    private String userName;
    private List<Plan> pss;
    public PlanFragment (List<Plan> cpic){
        this.pss=cpic;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.zft_activity_recycle, container, false);
        RecyclerView recyclerView=root.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        PlanAdapter planAdapter=new PlanAdapter(pss);
        recyclerView.setAdapter(planAdapter);
       // CourseListAdapter adapter=new CourseListAdapter(getContext(),coursePic,R.layout.mr_course_picture_item);
      //  ListView listView=root.findViewById(R.id.course_list);
        //listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent=new Intent(getContext(), CourseDetailActivity.class);
//                intent.putExtra("courseName",coursePic.get(position).getName());
//                startActivity(intent);
//            }
//        });
        return root;
    }
}
