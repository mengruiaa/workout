package com.example.fitnessdemo.LZYZYH.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fitnessdemo.LZYZYH.model.Categoryl;
import com.example.fitnessdemo.R;

import java.util.ArrayList;

/**
 * 类目listview，对应categoryActivity
 *
 */
public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Categoryl> list;

    public CategoryAdapter(Context mContext, ArrayList<Categoryl> list) {
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
            arg1 = LayoutInflater.from(mContext).inflate(R.layout.mall_category_listview, null);
            heol.txt_category_list = (TextView) arg1.findViewById(R.id.txt_category_list);
            arg1.setTag(heol);
        } else {
            heol = (ViewHeol) arg1.getTag();
        }
        heol.txt_category_list.setText(list.get(arg0).getCategoryl_name());
        return arg1;
    }

    private class ViewHeol {
        TextView txt_category_list;
    }
}
