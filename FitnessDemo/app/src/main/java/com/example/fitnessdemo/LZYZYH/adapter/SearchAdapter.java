package com.example.fitnessdemo.LZYZYH.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.R;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private List<ListOfGoods> goodsList;
    private int resourceId;
    private Context context;
    private EditText cakeNameSearch;
    private EditText cakeSizeSearch;
    private EditText cakePriceSearch;
    private Button btnSearch;
    public SearchAdapter(List<ListOfGoods> goodsList, int resourceId, Context context) {
        this.goodsList = goodsList;
        this.resourceId = resourceId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view) {
            //加载item的布局文件
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resourceId, viewGroup,false);
        }

        TextView tvName = view.findViewById(R.id.txt_biaoti);
        ImageView ivPicture = view.findViewById(R.id.img_list_tupian);
        ivPicture.setImageBitmap(goodsList.get(i).getImg());
        TextView tvPrice = view.findViewById(R.id.txt_jiage);
        tvPrice.setText(goodsList.get(i).getJiage() + "");
        tvName.setText(goodsList.get(i).getBiaoti());
//        //获取用户头像
//        Bitmap picture = BitmapFactory.decodeFile(cakeList.get(i).getImgId());
//        //显示用户头像
//        ivPicture.setImageBitmap(picture);

        return view;
    }
}
