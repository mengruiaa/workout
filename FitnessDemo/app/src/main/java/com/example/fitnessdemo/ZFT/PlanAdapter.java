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

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private List<Plan> mPlan;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
        View planView;
        public ViewHolder(View view) {
            super(view);
            planView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruitname);
        }
    }
    public PlanAdapter(List<Plan> planList) {
        mPlan = planList;
    }
    @Override

    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zft_plan_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.planView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent();
                intent.setClass(holder.planView.getContext(),PlanInfoActivity.class);
                intent.putExtra("plan",mPlan.get(position));
                holder.planView.getContext().startActivity(intent);
                Plan plan = mPlan.get(position);
                Toast.makeText(view.getContext(), "you clicked view" + plan.getPlanName(), Toast.LENGTH_SHORT).show();
            }
        });

//        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = holder.getAdapterPosition();
//                Plan plan = mPlan.get(position);
//                Toast.makeText(view.getContext(), "you clicked image" + plan.getPlanName(), Toast.LENGTH_SHORT).show();
//            }
//        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Plan plan = mPlan.get(position);
//        holder.fruitImage.setImageResource(plan.getImageId());
//        holder.fruitName.setText(plan.getPlanName());
        Glide.with(holder.planView).load(ConfigUtil.SERVER_HOME + plan.getPlanImg()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mPlan.size();
    }
}
