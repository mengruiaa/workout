package com.example.fitnessdemo.CLB.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessdemo.CLB.entity.Essay;
import com.example.fitnessdemo.R;

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
        if(null != essayList){
            return essayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (null != essayList){
            return essayList.get(i);
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
        //获取控件对象，设置对应属性
        TextView tvTitle = view.findViewById(R.id.clb_tv_stair3_1);;
        TextView type = view.findViewById(R.id.clb_tv_stair3_2);;
        TextView number = view.findViewById(R.id.clb_tv_stair3_3);
        ImageView imageView = view.findViewById(R.id.clb_iv_stair3_1);
//        tvTitle.setText(essayList.get(i).getTitle());
        type.setText(essayList.get(i).getType());
        number.setText(essayList.get(i).getNumber()+"");
        //显示图片
        return view;
    }
}
