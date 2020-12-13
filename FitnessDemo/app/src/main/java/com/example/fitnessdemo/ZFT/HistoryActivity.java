package com.example.fitnessdemo.ZFT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zft_activity_history);
        getHistory(ConfigUtil.SERVER_HOME + "GetHistory" + "?user_phone=" + ConfigUtil.user_Name);

    }

    private void getHistory(String s) {

    }
}
