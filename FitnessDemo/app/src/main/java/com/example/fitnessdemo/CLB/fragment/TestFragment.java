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

import com.example.fitnessdemo.CLB.Stair2Activity;
import com.example.fitnessdemo.CLB.adapter.CyclopediaAdapter;
import com.example.fitnessdemo.CLB.entity.Cyclopedia1;
import com.example.fitnessdemo.R;

import java.util.List;

public class TestFragment extends Fragment {
    private View view;
    //获取传来的数据
    private List<Cyclopedia1> lists;
    public TestFragment(){

    }
    public TestFragment(List<Cyclopedia1> lists){
        this.lists = lists;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.clb_fragment_test,//内容界面的布局界面
                container,//根视图对象
                false);//手动调用
        ListView lvCake = view.findViewById(R.id.lv_cyclopedia);
        System.out.println("zhe:"+lists);
        CyclopediaAdapter cyclopediaAdapter = new CyclopediaAdapter(
                getContext(),
                lists,
                R.layout.clb_cyclopedia_list_item
        );
        lvCake.setAdapter(cyclopediaAdapter);
        lvCake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println("i"+i);
//                System.out.println(lists.get(i).getName());
                String name = lists.get(i).getName();
                String attentionNumber = lists.get(i).getAttentionNumber()+"";
                String brief = lists.get(i).getBrief();
                //获取item对应的数据名称，跳转activity
                Intent intent = new Intent();
                intent.setClass(getContext(), Stair2Activity.class);
                intent.putExtra("itemName",name);
                intent.putExtra("attentionNumber",attentionNumber);
                intent.putExtra("brief",brief);

                startActivity(intent);
            }
        });



        return view;
    }

    /**
     * 获取数据，从lists中获取数据显示。。。。。。
     */

}
