package com.example.fitnessdemo.ws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fitnessdemo.R;

public class ProtocolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.ws_activity_protocol);
        ImageView ivpro = findViewById(R.id.iv_protocol);
        ivpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String action = intent.getAction();
                if ("register".equals(action)){
                    Intent intent1 = new Intent();
                    intent1.setClass(ProtocolActivity.this, RegisterActivity.class);
                    startActivity(intent1);
                }else {
                    Intent intent2 = new Intent();
                    intent2.setClass(ProtocolActivity.this, LoginActivity.class);
                    startActivity(intent2);
                }

            }
        });

    }
}
