package com.example.fitnessdemo.CLB;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;

public class EssayDetailActivity extends AppCompatActivity {
    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay_detail);
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        final Intent intent = getIntent();
        findViews();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(EssayDetailActivity.this,Stair2TransferActivity.class);
                intent1.putExtra("name", ConfigUtil.bobo);
                startActivity(intent1);
//                overridePendingTransition(0, 0);//删除动画的代码
            }
        });
    }

    private void findViews() {
        ivBack = findViewById(R.id.clb_iv_essay_back);
    }
}
