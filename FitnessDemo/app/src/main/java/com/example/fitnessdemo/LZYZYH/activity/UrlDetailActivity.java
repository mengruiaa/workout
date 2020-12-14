package com.example.fitnessdemo.LZYZYH.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.fitnessdemo.R;

public class UrlDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mall_activity_url);
        //取消应用标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //获得控件
        WebView webView = (WebView) findViewById(R.id.wv_webview);
        //访问网页
        Intent i = getIntent();
        String url = i.getStringExtra("url");
        webView.requestFocus();// 使页面获取焦点，防止点击无响应
        webView.loadUrl(url);
        webView.getSettings().setDomStorageEnabled(true);

        WebSettings wetSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wetSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                view.getSettings().setDomStorageEnabled(true);

                //返回true
                return true;
            }
        });
    }


}
