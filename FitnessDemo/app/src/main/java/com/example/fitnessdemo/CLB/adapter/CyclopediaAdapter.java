package com.example.fitnessdemo.CLB.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.fitnessdemo.CLB.entity.Cyclopedia1;
import com.example.fitnessdemo.R;

import java.util.List;

public class CyclopediaAdapter extends BaseAdapter {
    private Context mContext;//为了加载布局文件
    private List<Cyclopedia1> cyclopediaList;
    private int itemLayoutRes;
    public CyclopediaAdapter(Context mContext, List<Cyclopedia1> cyclopediaList, int itemLayoutRes){
        this.mContext = mContext;
        this.cyclopediaList = cyclopediaList;
        this.itemLayoutRes = itemLayoutRes;
    }
    @Override
    public int getCount() {
        if(null != cyclopediaList){
            return cyclopediaList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (null != cyclopediaList){
            return cyclopediaList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //convertView每一个item的视图对象，需要我们通过加载item的布局文件来进行创建。
        //加载item的布局文件
        if(view==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view=inflater.inflate(itemLayoutRes,null);
        }
        ImageView ivCyclopedia = view.findViewById(R.id.clb_iv_1);
        TextView tvCyclopedia1 = view.findViewById(R.id.clb_tv_1);
        TextView tvCyclopeida2 = view.findViewById(R.id.clb_tv_2);
//        System.out.println("关键1："+cyclopediaList);
        String url = cyclopediaList.get(i).getPitcure();
//        System.out.println(url);
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(view)
                .load(url)
                .apply(requestOptions)
                .into(ivCyclopedia);
        tvCyclopedia1.setText(cyclopediaList.get(i).getName());
        tvCyclopeida2.setText(cyclopediaList.get(i).getAttentionNumber()+" 人关注");

        return view;
    }
}
