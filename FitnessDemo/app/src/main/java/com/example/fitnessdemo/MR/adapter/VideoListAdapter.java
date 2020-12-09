package com.example.fitnessdemo.MR.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.MR.entity.CoursePictureShow;
import com.example.fitnessdemo.MR.entity.Video;
import com.example.fitnessdemo.R;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends BaseAdapter {
    private Context myContext;
    private List<Video> vss=new ArrayList<>();
    private int itemLayout;

    public VideoListAdapter(Context myContext, List<Video> vss, int itemLayout) {
        this.myContext = myContext;
        this.vss = vss;
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        if(vss!=null){
            return vss.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(vss!=null){
            return vss.get(i);
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

        TextView num=view.findViewById(R.id.num);
        num.setText((i+1)+"");
        TextView name=view.findViewById(R.id.name);
        name.setText(vss.get(i).getVideo_name());
        return view;
    }
}
