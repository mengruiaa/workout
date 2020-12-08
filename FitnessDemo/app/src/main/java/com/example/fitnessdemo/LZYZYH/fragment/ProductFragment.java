package com.example.fitnessdemo.LZYZYH.fragment;

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

import com.example.fitnessdemo.LZYZYH.activity.DetailActivity;
import com.example.fitnessdemo.LZYZYH.adapter.ListOfGoodsAdapter;
import com.example.fitnessdemo.LZYZYH.model.ListOfGoods;
import com.example.fitnessdemo.LZYZYH.model.Product;
import com.example.fitnessdemo.R;

import java.util.List;

public class ProductFragment extends Fragment {
    private View root;
    private List<Product> pro;
    public ProductFragment (List<Product> pro){
        this.pro=pro;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.mall_search, container, false);
        ListOfGoodsAdapter adapter=new ListOfGoodsAdapter(getContext(),pro,R.layout.mall_product_item);
        ListView listView=root.findViewById(R.id.list_listofgoods);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), DetailActivity.class);
                intent.putExtra("proName",pro.get(position).getProduct_name());
                startActivity(intent);
            }
        });
        return root;
    }
}
