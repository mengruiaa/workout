package com.example.fitnessdemo.MR.TheFour;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessdemo.CLB.EncyclopaediaActivity;
import com.example.fitnessdemo.R;
import com.example.fitnessdemo.ZFT.PlanActivity;


public class ShowYe extends Fragment {
    private View root;
    private Button btnLook;
    private Button btnKonw;
    private String userName;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.mr_shouye, container, false);
        btnLook = root.findViewById(R.id.btn_lookPlan);
        btnKonw = root.findViewById(R.id.btn_konw);
        btnKonw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(root.getContext(), EncyclopaediaActivity.class);
                startActivity(intent);
            }
        });
        btnLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(root.getContext(), PlanActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
