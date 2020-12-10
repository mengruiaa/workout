package com.example.fitnessdemo.ZFT;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class RecoPlanAdapter extends RecyclerView.Adapter<RecoPlanAdapter.ViewHolder> {
    private View root;
    private List<Plan> mPlan;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://表示接收服务端的字符串
                    String res = (String)msg.obj;
                    if (res.equals("had")){
                        Toast.makeText(root.getContext(),"you have added",Toast.LENGTH_SHORT).show();
                    }else if (res.equals("successfully")){
                        Toast.makeText(root.getContext(),"added successfully",Toast.LENGTH_SHORT).show();
                    }else if (res.equals("failed")){
                        Toast.makeText(root.getContext(),"added failed",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
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

        holder.recoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Plan plan = mPlan.get(position);
                addMyPlan(ConfigUtil.SERVER_HOME + "AddPlan" + "?user_phone=" + ConfigUtil.user_Name + "&plan_name=" + plan.getPlanName());
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        root = holder.planView;
        Plan plan = mPlan.get(position);
//        holder.fruitImage.setImageResource(plan.getImageId());
//        holder.fruitName.setText(plan.getPlanName());
        Glide.with(holder.planView).load(ConfigUtil.SERVER_HOME + plan.getPlanImg()).into(holder.recoImage);
    }

    @Override
    public int getItemCount() {
        return mPlan.size();
    }
    private void addMyPlan(final String s) {
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
                    //借助于Message，把收到的字符串显示在页面上
                    //创建Message对象
                    Message msg = new Message();
                    //设置Message对象的参数
                    msg.what = 1;
                    msg.obj = str;
                    //发送Message
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
