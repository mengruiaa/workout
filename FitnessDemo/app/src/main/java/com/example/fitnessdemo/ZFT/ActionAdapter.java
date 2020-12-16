package com.example.fitnessdemo.ZFT;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.LZYZYH.util.BitmapUtil;
import com.example.fitnessdemo.R;

import java.util.List;

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ViewHolder> {
    List<Motion> mMotion;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivAction;
        TextView tvActionName;
        TextView tvActionStar;
        Button btnAdd;
        View actionView;
        public ViewHolder(View view) {
            super(view);
            actionView = view;
            ivAction = view.findViewById(R.id.iv_actionImg);
            tvActionName = view.findViewById(R.id.tv_actionName);
            tvActionStar = view.findViewById(R.id.tv_actionStar);
            btnAdd = view.findViewById(R.id.btn_addAction);
        }
    }
    public ActionAdapter(List<Motion> motionList) {
        mMotion = motionList;
    }
    @Override
    public ActionAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zft_action_library_item_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positon = holder.getAdapterPosition();
                holder.btnAdd.setText("已添加");
                ActionLibraryActivity.actions.add(mMotion.get(positon));
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ActionAdapter.ViewHolder holder, int position) {
        Motion motion = mMotion.get(position);
        holder.tvActionName.setText(motion.getMotionName());
        holder.tvActionStar.setText("难度 " + motion.getMotionStar() + " ⭐");
        Glide.with(holder.actionView).asBitmap().load(ConfigUtil.SERVER_HOME + motion.getMotionImg()).into(holder.ivAction);
    }
    @Override
    public int getItemCount() {
        return mMotion.size();
    }
}
