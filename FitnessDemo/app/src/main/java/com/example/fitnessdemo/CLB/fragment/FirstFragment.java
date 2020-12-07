package com.example.fitnessdemo.CLB.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.CLB.ConfigUtilCui;
import com.example.fitnessdemo.CLB.EssayDetailActivity;
import com.example.fitnessdemo.CLB.adapter.EssayAdapter;
import com.example.fitnessdemo.CLB.entity.Essay;
import com.example.fitnessdemo.R;

import java.util.List;
public class FirstFragment extends Fragment {
    private View view;
    //获取传来的数据
    private List<Essay> lists;
    public FirstFragment(){

    }
    public FirstFragment(List<Essay>lists){
        this.lists = lists;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.clb_first_frag,//内容界面的布局界面
                container,
                false);
        //获取ListView对象
        ListView listView = view.findViewById(R.id.lv_essay);
        //初始化listView所用的Adapter
        EssayAdapter essayAdapter = new EssayAdapter(
                getContext(),
                lists,
                R.layout.clb_essay_list_item
        );
        listView.setAdapter(essayAdapter);
        //设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //获取文章标题
                String title = lists.get(i).getTitle();
                //获取文章阅读人数
                String number = lists.get(i).getNumber()+"";
                //获取文章日期
                String date = lists.get(i).getDate();
                //获取文章内容
                Intent intent = new Intent();
                intent.setClass(getContext(), EssayDetailActivity.class);
                intent.putExtra("title",lists.get(i).getTitle());
                ConfigUtilCui.bobo = lists.get(i).getParentName();
                System.out.println("ConfigUtilCui.bobo:"+ConfigUtilCui.bobo);
                startActivity(intent);
            }
        });
        return view;
    }
}
