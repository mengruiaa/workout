package com.example.fitnessdemo.CLB.dialog;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * 自定义viewpager切换动画
 * 如果不设置切换动画，还会是水平方向的动画
 */
public class DefaultTransformer implements ViewPager.PageTransformer {
    public static final String TAG = "simple";
    @Override
    public void transformPage(@NonNull View page, float position) {
        float alpha = 0;
        if(position >= 0 && position <=1){
            alpha = 1 - position;
        }else if(position > -1 && position < 0){
            alpha = position + 1;
        }

        page.setAlpha(alpha);
        float transX = page.getWidth()* -position;
        page.setTranslationX(transX);
        float transY = position * page.getHeight();
        page.setTranslationY(transY);
    }
}
