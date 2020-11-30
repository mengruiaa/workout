package com.example.fitnessdemo;

import android.app.Application;

import com.mob.MobSDK;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        MobSDK.init(this);
    }
}

