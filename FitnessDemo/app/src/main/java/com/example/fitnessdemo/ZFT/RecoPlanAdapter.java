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

public class RecoPlanAdapter extends RecyclerView.Adapter<RecoPlanAdapter.ViewHolder> {
    private List<Plan> mPlan;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView recoImage;
        TextView recoAdd;
        View planView;
        public ViewHolder(View view) {
            super(view);
            planView = view;
            recoImage = (ImageView) view.findViewById(R.id.reco_image);
            recoAdd = (TextView) view.findViewById(R.id.reco_add);
        }
    }
    public RecoPlanAdapter(List<Plan> planList) {
        mPlan = planList;
    }
    public RecoPlanAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zft_reco_item_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.recoImage.setOnClickListener(new View.OnClickListener() {
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
        Glide.with(holder.planView).load(ConfigUtil.SERVER_HOME + plan.getPlanImg()).into(holder.recoImage);
    }

    @Override
    public int getItemCount() {
        return mPlan.size();
    }
}
