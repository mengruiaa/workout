package com.example.fitnessdemo.ZFT;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;

import java.util.List;

public class MotionAdapter  extends RecyclerView.Adapter<MotionAdapter.ViewHolder> {
    private List<Motion> mMotion;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView MotionImg;
        TextView motionName;
        TextView motionStar;
        TextView motionCount;
        TextView motionTime;
        TextView restTime;
        View motionView;
        public ViewHolder(View view) {
            super(view);
            motionView = view;
            MotionImg = view.findViewById(R.id.iv_motionImg);
            motionName = view.findViewById(R.id.tv_motionName);
            motionStar = view.findViewById(R.id.tv_motionStar);
            motionCount = view.findViewById(R.id.tv_motionCount);
            motionTime = view.findViewById(R.id.tv_motionTime);
            restTime = view.findViewById(R.id.tv_restTime);
        }
    }

    public MotionAdapter(List<Motion> motionList) {
            mMotion = motionList;
        }
        @Override

        public MotionAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zft_motion_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.motionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    Intent intent = new Intent();
                    intent.setClass(holder.motionView.getContext(),ExerciseActivity.class);
                    intent.putExtra("motion",mMotion.get(position));
                    holder.motionView.getContext().startActivity(intent);
                }
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(MotionAdapter.ViewHolder holder, int position) {
            Motion motion = mMotion.get(position);
            holder.motionName.setText(motion.getMotionName());
            holder.motionCount.setText(motion.getMotionCount() + holder.motionCount.getText().toString());
            holder.motionTime.setText(motion.getMotionTime()+"");
            holder.restTime.setText(holder.restTime.getText().toString() + motion.getRestTime() + "″");
            holder.motionStar.setText("难度 " + motion.getMotionStar() + " ⭐");
            Glide.with(holder.motionView).asBitmap().load(ConfigUtil.SERVER_HOME + motion.getMotionImg()).into(holder.MotionImg);
        }

        @Override
        public int getItemCount() {
            return mMotion.size();
        }
}
