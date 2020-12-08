package com.example.fitnessdemo.ZFT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitnessdemo.R;

public class DataActivity extends AppCompatActivity {
    private TextView tvName;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.zft_activity_data);
        Intent intent = getIntent();
        btn = findViewById(R.id.btn_keep_going);
        tvName = findViewById(R.id.planName);
        tvName.setText(intent.getStringExtra("planName"));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
