package com.example.fitnessdemo.ws;

/**
 * 自定义Dialog监听器
 */
public interface PriorityListener {
    /**
     * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
     */
    public void refreshPriorityUI();
}

