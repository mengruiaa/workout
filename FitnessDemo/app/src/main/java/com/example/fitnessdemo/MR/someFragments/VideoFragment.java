package com.example.fitnessdemo.MR.someFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.MR.CourseDetailActivity;
import com.example.fitnessdemo.MR.VideoActivity;
import com.example.fitnessdemo.MR.adapter.CourseListAdapter;
import com.example.fitnessdemo.MR.adapter.VideoListAdapter;
import com.example.fitnessdemo.MR.entity.Course;
import com.example.fitnessdemo.MR.entity.Video;
import com.example.fitnessdemo.R;

import java.util.List;

public class VideoFragment extends Fragment {
    private View root;
    private List<Video> vs;
    public VideoFragment (List<Video> vs){
        this.vs=vs;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.mr_list_fragment3, container, false);
        VideoListAdapter adapter=new VideoListAdapter(getContext(),vs,R.layout.mr_video_item);
        ListView listView=root.findViewById(R.id.course_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), VideoActivity.class);
                intent.putExtra("videoPath",vs.get(position).getVideo_path());
                intent.putExtra("videointro",vs.get(position).getVideo_introduce());
                startActivity(intent);
            }
        });
        return root;
    }
}
