package com.example.fitnessdemo.MR.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.R;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends BaseAdapter {
    private Context myContext;
    private List<CoursePictureShow> courseShows=new ArrayList<>();
    private int itemLayout;

    public CourseListAdapter(Context myContext, List<CoursePictureShow> courseShows, int itemLayout) {
        this.myContext = myContext;
        this.courseShows = courseShows;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        if(courseShows!=null){
            return courseShows.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(courseShows!=null){
            return courseShows.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=LayoutInflater.from(myContext);
        view=inflater.inflate(itemLayout,null);

        ImageView picture=view.findViewById(R.id.picture);
        System.out.println(courseShows.get(i).getPicture());
        Glide.with(view).load(courseShows.get(i).getPicture()).into(picture);
        return view;
    }
}
