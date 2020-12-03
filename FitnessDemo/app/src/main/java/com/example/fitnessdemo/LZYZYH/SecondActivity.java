package com.example.fitnessdemo.LZYZYH;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessdemo.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_second);
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        Button button= (Button)findViewById(R.id.sasa);
        button.setText(data);
    }
}
