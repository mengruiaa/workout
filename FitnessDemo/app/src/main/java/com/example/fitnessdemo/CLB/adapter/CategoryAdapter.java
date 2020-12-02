package com.example.fitnessdemo.CLB.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessdemo.CLB.entity.Entry1;
import com.example.fitnessdemo.R;


import java.util.List;

/**
 * 百度百科
 * 一级子目录
 * RecyclerView
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ViewHolder viewHolder;
    private Context mContext;
//    private String[] mTitles=null;
    private List<Entry1> mTitles;
    //设置位置变量，来实现切换操作
    private int selectPosition;
    private OnItemClickListener mListener;
    public int getSelectPosition() {
        return selectPosition;
    }
    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public CategoryAdapter(Context context, List<Entry1> data, OnItemClickListener listener){
//        mTitles = context.getResources().getStringArray(R.array.titles);
        mContext = context;
        this.mInflater=LayoutInflater.from(context);
        this.mTitles=data;
        this.mListener = listener;
    }
    /**
     * item显示类型
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.clb_stair_catalog_item,parent,false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    /**
     * 数据的绑定显示
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.item_tv.setText(mTitles.get(position).getName());
        holder.item_tv.setTextSize(16);
        holder.item_tv.setTextColor(Color.rgb(47,79,79));
        if(position == selectPosition){
            holder.ll_test.setBackgroundColor(Color.rgb(255,255,255));
            holder.item_tv.setTextColor(Color.rgb(000,255,127));
        }else{
            holder.ll_test.setBackgroundColor(Color.rgb(245,245,245));
        }

        //可以在这里获取选中的属性
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println(mTitles.get(position));
                mListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;
        public LinearLayout ll_test;
        public ViewHolder(View view){
            super(view);
            item_tv = (TextView)view.findViewById(R.id.tv_stair);
            ll_test = view.findViewById(R.id.ll_test);
//            ll_test.setBackgroundColor(Color.rgb(203,203,203));

        }
    }
    //外部接口回调监听
    public interface OnItemClickListener{
        void onClick(int pos);
    }
    public void setBg(int sPos){
        this.selectPosition = sPos;
        notifyDataSetChanged();
    }

}
