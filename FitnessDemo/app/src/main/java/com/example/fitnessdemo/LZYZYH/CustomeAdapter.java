package com.example.fitnessdemo.LZYZYH;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.R;

import java.util.ArrayList;
import java.util.List;

public class CustomeAdapter extends BaseAdapter {
    private List<Product> products = new ArrayList<Product>();
    private int itemLayoutId;
    private Context context;
    public CustomeAdapter(Context context, List<Product> products, int itemLayoutId){
        this.context = context;
        this.products = products;
        this.itemLayoutId = itemLayoutId;
    }
    @Override
    public int getCount() {
        if(null != products)
            return products.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if(null != products)
            return products.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId,null);
        }
        final TextView name = convertView.findViewById(R.id.tv_name);
        TextView text = convertView.findViewById(R.id.tv_text);
        TextView money = convertView.findViewById(R.id.tv_money);
        ImageView img = convertView.findViewById(R.id.iv_show);
        final String n = products.get(position).getName().toString();
        final String t = products.get(position).getImg();
        final float m = products.get(position).getPrice();
        final String s =products.get(position).getUrl();

//        name.setText(n);
//        text.setText(t);
//        money.setText(String.valueOf(m));
//        size.setText(String.valueOf(s));
        img.setImageResource(Integer.parseInt(products.get(position).getImg()));
        notifyDataSetChanged();
        return convertView;
    }
}
