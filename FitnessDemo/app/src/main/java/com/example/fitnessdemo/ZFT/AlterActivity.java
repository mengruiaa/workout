package com.example.fitnessdemo.ZFT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.GoalRow;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import com.example.fitnessdemo.ConfigUtil;
import com.example.fitnessdemo.MR.ShouYeActivity;
import com.example.fitnessdemo.MR.TheFour.ShowYe;
import com.example.fitnessdemo.R;

import java.io.IOException;

public class AlterActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator;
    private Button btnGoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zft_activity_alter);
        btnGoto = findViewById(R.id.btn_goto_workout);
        btnGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AlterActivity.this, UserPlanActivity.class);
                intent.putExtra("userName", ConfigUtil.user_Name);
                startActivity(intent);
                if (ShowYe.getAlarmStyle()){
                    mMediaPlayer.stop();
                }else {
                    vibrator.cancel();
                }
                finish();
            }
        });
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

        if (ShowYe.getAlarmStyle())
        {
            playAlarm();
        }
        else
        {
            startVibrate();
        }

    }
    private void startVibrate()
    {
        long[] vib =
                { 0, 200, 3000, 500, 2000, 1000 };
        vibrator.vibrate(vib, 4);
    }
    private void playAlarm()
    {
        mMediaPlayer = new MediaPlayer();

        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        try
        {
            mMediaPlayer.setDataSource(this, alert);
        } catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        } catch (SecurityException e)
        {
            e.printStackTrace();
        } catch (IllegalStateException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        mMediaPlayer.setLooping(true);    // 循环播放开
        try
        {
            mMediaPlayer.prepare();     // 后面的是try 和catch ，自动添加的
        } catch (IllegalStateException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        mMediaPlayer.start();// 开始播放
    }
}
