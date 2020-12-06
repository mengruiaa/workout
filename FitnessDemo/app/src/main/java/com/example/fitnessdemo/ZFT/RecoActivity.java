package com.example.fitnessdemo.ZFT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessdemo.R;

public class RecoActivity extends AppCompatActivity {
    private RulerView ruler_height;   //身高的view
    private RulerView ruler_weight ;  //体重的view
    private CheckBox sex;
    private TextView tv_register_info_height_value,tv_register_info_weight_value;
    private Button btnCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zft_activity_reco);
        ruler_height=findViewById(R.id.ruler_height);
        ruler_weight=findViewById(R.id.ruler_weight);
        btnCheck = findViewById(R.id.zft_btn_check);
        sex = findViewById(R.id.btn_register_info_sex);

        tv_register_info_height_value= findViewById(R.id.tv_register_info_height_value);
        tv_register_info_weight_value= findViewById(R.id.tv_register_info_weight_value);


        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_height_value.setText(value+"");
            }
        });


        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_weight_value.setText(value+"");
            }
        });

        ruler_height.setValue(165, 80, 250, 1);

        ruler_weight.setValue(55, 20, 200, 0.1f);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sexinfo;
                if (sex.isChecked()){
                    sexinfo = "girl";
                }else{
                    sexinfo = "boy";
                }
                Toast.makeText(RecoActivity.this,
                        "身高:" + tv_register_info_height_value.getText().toString() + ";体重:" + tv_register_info_weight_value.getText().toString() + sexinfo
                        ,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
