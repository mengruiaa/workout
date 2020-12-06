package com.example.fitnessdemo.ws;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.R;

public class MyInfo extends Fragment {
    private View root;
    private ImageView ivsetting;
    private ImageView ivqrcode;
    private ImageView ivemail;
    private ImageView ivtoouxiang;
    private TextView username;
    private ImageView ivuserinfo;
    private Button btnpay;
    private RelativeLayout yuedong;
    private RelativeLayout usershengti;
    private TextView shoucang;
    private TextView friends;
    private TextView plans;
    private TextView helps;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ws_fragment_myinfo, container, false);
        findview();
        setListener();
        return root;
    }

    private void setListener() {
        MyListener myListener = new MyListener();
        ivuserinfo.setOnClickListener(myListener);
        ivtoouxiang.setOnClickListener(myListener);
        ivemail.setOnClickListener(myListener);
        ivqrcode.setOnClickListener(myListener);
        ivsetting.setOnClickListener(myListener);
        btnpay.setOnClickListener(myListener);
        yuedong.setOnClickListener(myListener);
        shoucang.setOnClickListener(myListener);
        usershengti.setOnClickListener(myListener);
        friends.setOnClickListener(myListener);
        plans.setOnClickListener(myListener);
        helps.setOnClickListener(myListener);
    }

    private void findview() {
        ivsetting = root.findViewById(R.id.myinfo_setting);
        ivqrcode = root.findViewById(R.id.myinfo_qrcode);
        ivemail = root.findViewById(R.id.myinfo_email);
        ivtoouxiang = root.findViewById(R.id.myinfo_touxiang);
        username = root.findViewById(R.id.myinfo_username);
        ivuserinfo = root.findViewById(R.id.myinfo_userinfo);
        btnpay = root.findViewById(R.id.myinfo_pay);
        yuedong = root.findViewById(R.id.layout_yundong);
        usershengti = root.findViewById(R.id.layout_userinfo);
        shoucang = root.findViewById(R.id.user_shoucang);
        friends = root.findViewById(R.id.user_friend);
        plans = root.findViewById(R.id.user_plan);
        helps = root.findViewById(R.id.user_help);

    }

    class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.myinfo_setting:
                    break;
                case R.id.myinfo_qrcode:
                    break;
                case R.id.myinfo_email:
                    break;
                case R.id.myinfo_touxiang:
                    break;
                case R.id.myinfo_userinfo:
                    break;
                case R.id.myinfo_pay:
                    break;
                case R.id.layout_yundong:
                    break;
                case R.id.layout_userinfo:
                    break;
                case R.id.user_shoucang:
                    break;
                case R.id.user_friend:
                    Intent intent = new Intent();
                    intent.setClass(getContext(),FriendActivity.class);
                    startActivity(intent);
                    break;
                case R.id.user_plan:
                    break;
                case R.id.user_help:
                    break;
            }
        }
    }


}
