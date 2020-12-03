package com.example.fitnessdemo.MR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.R;

import java.io.File;

public class CourseDetailActivity extends AppCompatActivity {
    private WebView mWebView;
    private LinearLayout ll_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mr_activity_course_detail);
        intiUI();
        findViewById(R.id.poi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.getSettings().setDomStorageEnabled(true);
                mWebView.loadUrl("https://preview.yozodcs.com/fcscloud/view/preview/zD9nPfRweQhOlLQ9PO7rDQ_uZRL9zCb2j6swQ6B0NFJmm2HD36AN4xoTbQVV7c1g_rlKC6Dc36KxleUZgbr5nPiIjBfd5rL5IPc9W2zux1EKJG4ncmvrFMHHKc9cyXy55QKhhoopwt142tvnEyDVuAwiGrNxmRfDkSKLsB5ruKShPui2rT2Un3M7hQs0FpvtOBtWwZmMBK3kCF9u38WWceE3L4QdDIH6s8TDxem_ChIz0RKKPJir6O-Ls9JnruL5nt_InCOf14NnFRsG74-09Siq56jAK1fEuuD9tj_wYVBCeMwG70ijSkTH0ltaHenosziCamCv01AG7Z0ZNF1qUJICVQQZnTJsidaxuQeNbtm8Srn6KHJ8zKlWaQbyGUzLg1-s-J1x0FEWfryZpR0SnKUpoxmiV63d2_mhn89ebRA78kxbvv7-nzZ9NoAwCj5A-fKR3dLRZbPmat8Ov-AblH-jV00f3qYY/");
            }
        });
    }

    private void intiUI() {
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        mWebView=findViewById(R.id.introduce);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.requestFocusFromTouch();
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        // 在 Activity 销毁的时候，可以先让 WebView 加载null内容，
        // 然后移除 WebView，再销毁 WebView，最后置空。
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ll_root.removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

}
