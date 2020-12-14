package com.example.fitnessdemo.ZFT;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class MyPlansAdapter extends RecyclerView.Adapter<MyPlansAdapter.ViewHolder>{
    private List<Plan> mPlan;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView myImage;
        Button btnDelete;
        View myPlanView;
        public ViewHolder(View view) {
            super(view);
            myPlanView = view;
            myImage = view.findViewById(R.id.user_plan_img);
            btnDelete = view.findViewById(R.id.btn_deleteMyPlan);
        }
    }
    public MyPlansAdapter(List<Plan> planList,TextView tvNote,Button btnAdd) {
        mPlan = planList;
        tvNote = tvNote;
        btnAdd = btnAdd;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zft_user_plan_item_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent();
                intent.setClass(holder.myPlanView.getContext(),PlanInfoActivity.class);
                intent.putExtra("plan",mPlan.get(position));
                holder.myPlanView.getContext().startActivity(intent);
                Plan plan = mPlan.get(position);
                Toast.makeText(view.getContext(), "you clicked view" + plan.getPlanName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                deleteMyPlan(ConfigUtil.SERVER_HOME + "DeleteUserPlanServlet" + "?plan_name=" + mPlan.get(position).getPlanName() + "&phone=" + ConfigUtil.user_Name);
                mPlan.remove(position);
                notifyDataSetChanged();
                if (mPlan.size()==0){
                    UserPlanActivity.tvNote.setVisibility(View.VISIBLE);
                    UserPlanActivity.btnAdd.setVisibility(View.VISIBLE);
                }
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

    private void deleteMyPlan(final String s) {
        new Thread(){
            @Override
            public void run() {
                //使用网络连接，接收服务端发送的字符串
                try {
                    //创建URL对象
                    URL url = new URL(s);
                    //获取URLConnection连接对象
                    URLConnection conn = url.openConnection();
                    //获取网络输入流
                    InputStream in = conn.getInputStream();
                    //使用字符流读取
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in, "utf-8"));
                    //读取字符信息
                    String str = reader.readLine();
                    //关闭流
                    reader.close();
                    in.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Plan plan = mPlan.get(position);
//        holder.fruitImage.setImageResource(plan.getImageId());
//        holder.fruitName.setText(plan.getPlanName());
        Glide.with(holder.myPlanView).load(ConfigUtil.SERVER_HOME + plan.getPlanImg()).into(holder.myImage);
    }

    @Override
    public int getItemCount() {
        return mPlan.size();
    }
}
