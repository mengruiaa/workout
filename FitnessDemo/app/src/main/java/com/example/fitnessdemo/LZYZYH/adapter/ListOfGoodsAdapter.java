package com.example.fitnessdemo.LZYZYH.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;

/**
 * 商品列表LIstView
 *
 * @author
 *
 */
public class ListOfGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> list;
    private int mall_product_item;

    public ListOfGoodsAdapter(Context mContext, List<Product> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public ListOfGoodsAdapter(Context context, List<Product> pro, int mall_product_item) {
        this.mContext=context;
        this.list=pro;
        this.mall_product_item=mall_product_item;
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
        Glide.with(arg1).load(SERVER_HOME + "image/"+list.get(arg0).getProduct_mainimage()).into(heol.iv_show);
        return arg1;
    }

    private class ViewHeol {
        ImageView iv_show;
        TextView txt_biaoti, txt_jiage, txt_yuanjia;
    }

}
