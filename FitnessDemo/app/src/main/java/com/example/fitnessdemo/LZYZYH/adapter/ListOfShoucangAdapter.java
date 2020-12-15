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

import com.bumptech.glide.Glide;
import com.example.fitnessdemo.LZYZYH.model.Categoryr;
import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.R;

import java.util.List;

import static com.example.fitnessdemo.ConfigUtil.SERVER_HOME;

public class ListOfShoucangAdapter extends BaseAdapter {
    private List<Categoryr> goodsList;
    private int resourceId;
    private Context context;
    private EditText cakeNameSearch;
    private EditText cakeSizeSearch;
    private EditText cakePriceSearch;
    private Button btnSearch;
    public ListOfShoucangAdapter(List<Categoryr> goodsList, int resourceId, Context context) {
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

        TextView tvName = view.findViewById(R.id.tv_name);
        ImageView ivPicture = view.findViewById(R.id.iv_show);
        //  ivPicture.setImageBitmap(goodsList.get(i).getProduct_mainimage());
        TextView tvPrice = view.findViewById(R.id.tv_money);
        tvPrice.setText(goodsList.get(i).getProduct_price() + "");
        tvName.setText(goodsList.get(i).getProduct_name());
        Glide.with(view).load(SERVER_HOME + "image/"+goodsList.get(i).getProduct_mainimage()).into(ivPicture);

//        //获取用户头像
//        Bitmap picture = BitmapFactory.decodeFile(cakeList.get(i).getImgId());
//        //显示用户头像
//        ivPicture.setImageBitmap(picture);

        return view;
    }
}
