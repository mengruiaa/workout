package com.example.fitnessdemo.CLB;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnessdemo.CLB.fragment.FirstFragment;
import com.example.fitnessdemo.CLB.fragment.SecondFragment;
import com.example.fitnessdemo.CLB.fragment.ThirdFragment;
import com.example.fitnessdemo.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class Stair2Activity extends AppCompatActivity {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager slViewPage;
    private ArrayList<Fragment> slFragments;
    private Button btn_guanzhu;
    private GridView gridView;
    private List<String> listRelationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.clb_activity_stair2);
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        String attentionNumber = intent.getStringExtra("attentionNumber");
        System.out.println(itemName);
        System.out.println(attentionNumber);
        slidingTabLayout =findViewById(R.id.sl);
        slViewPage=findViewById(R.id.vp00);
        slFragments = new ArrayList<>();
        slFragments.add(new FirstFragment());
        slFragments.add(new SecondFragment());
        //     无需编写适配器，一行代码关联TabLayout与ViewPager（看这里看这里）
        slidingTabLayout.setViewPager(slViewPage, new String[]{"官方必读", "免费课程"}, this, slFragments);
        btn_guanzhu = findViewById(R.id.clb_btn_stair2_1);
        btn_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_guanzhu.getText().equals("关注")){
                    btn_guanzhu.setText("已关注");
                }else{
                    btn_guanzhu.setText("关注");
                }

            }
        });
        setGridData();
        setGridView();
    }

    private void setGridView() {
        int size = listRelationText.size();
        System.out.println(size);
        int length = 100;
        //获取屏幕大小
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        System.out.println(density);
        int gridviewWidth = (int) (size * (length + 4) * density);
        System.out.println(gridviewWidth);
        int itemWidth = (int) (length * density);
        System.out.println(itemWidth);

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        //第一个参数为宽的设置，第二个参数为高的设置。
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
//        mLayout.addView(textView, p);
//        gridView.setLayoutParams(p); // 设置GirdView布局参数,横向布局的关键
//        gridView.setColumnWidth(itemWidth); // 设置列表项宽
//        gridView.setHorizontalSpacing(5); // 设置列表项水平间距
//        gridView.setStretchMode(GridView.NO_STRETCH);
//        gridView.setNumColumns(size); // 设置列数量=列表集合数
//
//        GridViewAdapter adapter = new GridViewAdapter(getApplicationContext(),
//                listRelationText);
//        gridView.setAdapter(adapter);

    }

    private void setGridData() {
        listRelationText = new ArrayList<>();
        String name1 = "你好";
        String name2 = "肌肉";
        String name3 = "红烧肉";
        listRelationText.add(name1);
        listRelationText.add(name2);
        listRelationText.add(name3);


    }
    /**GirdView 数据适配器*/
    public class GridViewAdapter extends BaseAdapter {
        Context context;
        List<String> list;
        public GridViewAdapter(Context context, List<String> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.clb_grid_list_item, null);
            TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
            tvCity.setText(list.get(position));
            return convertView;
        }
    }


}
