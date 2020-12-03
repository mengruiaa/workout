package com.example.fitnessdemo.LZYZYH.Vertical;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.R;

public class vcount extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.mall_vcountlayout, null);
        TextView cte = (TextView) inflate.findViewById(R.id.oo);
        Bundle arguments = getArguments();
        String name = arguments.getString("name");
        Log.e("chen", "onCreateView: ------" + name);
        cte.setText("动态fragment："+name);
        return inflate;
    }
}

