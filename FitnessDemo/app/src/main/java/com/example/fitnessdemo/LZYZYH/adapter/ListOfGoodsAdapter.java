package com.example.fitnessdemo.LZYZYH.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.R;

import java.util.ArrayList;

/**
 * 商品列表LIstView
 *
 * @author
 *
 */
public class ListOfGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Product> list;

    public ListOfGoodsAdapter(Context mContext, ArrayList<Product> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list == null ? 0 : list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHeol heol;
        if (arg1==null) {
            heol = new ViewHeol();
            arg1 = LayoutInflater.from(mContext).inflate(R.layout.mall_list_item, null);
            heol.iv_show = arg1.findViewById(R.id.iv_show);
            heol.txt_biaoti= (TextView) arg1.findViewById(R.id.tv_name);
            heol.txt_jiage = (TextView) arg1.findViewById(R.id.tv_money);
            arg1.setTag(heol);
        }else {
            heol = (ViewHeol) arg1.getTag();
        }
        heol.txt_biaoti.setText(list.get(arg0).getProduct_name());
        heol.txt_jiage.setText(list.get(arg0).getProduct_price()+"");

        return arg1;
    }

    private class ViewHeol {
        ImageView iv_show;
        TextView txt_biaoti, txt_jiage, txt_yuanjia;
    }

}
