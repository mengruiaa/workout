package com.example.fitnessdemo.MR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.entity.MRvideoview;
import com.example.fitnessdemo.R;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class VideoActivity extends AppCompatActivity {
    private VideoView vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.mr_activity_video);
        vv=findViewById(R.id.videoView);
        findViewById(R.id.fanhui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoActivity.this.finish();
            }
        });
        Intent intent = getIntent();
        String path=intent.getStringExtra("videoPath");
        //设置视频控制器
        vv.setMediaController(new MediaController(this));
        //播放完成回调
        vv.setOnCompletionListener( new MyPlayerOnCompletionListener());
        //设置视频路径
        vv.setVideoPath(ConfigUtil.SERVER_HOME + path);
        //开始播放视频
        vv.start();

    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( VideoActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
}
