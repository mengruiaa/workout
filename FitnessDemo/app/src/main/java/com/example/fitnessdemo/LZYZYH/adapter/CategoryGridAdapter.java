package com.example.fitnessdemo.LZYZYH.adapter;
/*
第二个界面的两列
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.LZYZYH.model.CategoryGrid;
import com.example.fitnessdemo.LZYZYH.model.Categoryr;
import com.example.fitnessdemo.R;

import java.util.ArrayList;

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;

public class CategoryGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Categoryr> list;

    public CategoryGridAdapter(Context mContext, ArrayList<Categoryr> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list == null ? 0 : list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHeol heol;
        if (arg1 == null) {
            heol = new ViewHeol();
            arg1 = LayoutInflater.from(mContext).inflate(R.layout.mall_category_gridview, null);
            heol.img_category_gridview = (ImageView) arg1.findViewById(R.id.img_category_gridview);
            heol.txt_categroy_gridview = (TextView) arg1.findViewById(R.id.txt_categroy_gridview);
            heol.txt_categroy_price = (TextView) arg1.findViewById(R.id.txt_category_price);
            arg1.setTag(heol);
        } else {
            heol = (ViewHeol) arg1.getTag();
        }
        heol.txt_categroy_gridview.setText(list.get(arg0).getProduct_name());
        heol.txt_categroy_price.setText(list.get(arg0).getProduct_price()+"");
        Glide.with(arg1).load(SERVER_HOME + "image/"+list.get(arg0).getProduct_mainimage()).into(heol.img_category_gridview);


        return arg1;
    }

    private class ViewHeol {
        ImageView img_category_gridview;
        TextView txt_categroy_gridview, txt_categroy_gridview_jainjie,txt_categroy_price;
    }

}
