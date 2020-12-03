package com.example.fitnessdemo.CLB.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.fitnessdemo.CLB.entity.Cyclopedia1;
import com.example.fitnessdemo.CLB.entity.Essay;

import java.util.List;

public class EssayAdapter extends BaseAdapter {
    private Context mContext;//为了加载布局文件
    private List<Essay> essayList;
    private int itemLayoutRes;
    public EssayAdapter(Context mContext,List<Essay> essayList, int itemLayoutRes){
        this.mContext = mContext;
        this.essayList = essayList;
        this.itemLayoutRes = itemLayoutRes;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //convertView每一个item的视图对象，需要我们通过加载item的布局文件来进行创建。
        //加载item的布局文件
        if(view==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view=inflater.inflate(itemLayoutRes,null);
        }
        //获取控件对象，设置对应属性
        return view;
    }
}
