package com.example.fitnessdemo.ZFT;

import android.os.Parcelable;

import java.io.Serializable;

public class Motion implements Serializable {
    private int id;
    private String  planName;
    private String motionName;
    private String motionImg;
    private int motionStar;
    private int motionCount;
    private int motionTime;
    private String motionDesc;
    private String planHead;
    private int restTime;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPlanName() {
        return planName;
    }
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    public String getMotionName() {
        return motionName;
    }
    public void setMotionName(String motionName) {
        this.motionName = motionName;
    }
    public String getMotionImg() {
        return motionImg;
    }
    public void setMotionImg(String motionImg) {
        this.motionImg = motionImg;
    }
    public int getMotionStar() {
        return motionStar;
    }
    public void setMotionStar(int motionStar) {
        this.motionStar = motionStar;
    }
    public int getMotionCount() {
        return motionCount;
    }
    public void setMotionCount(int motionCount) {
        this.motionCount = motionCount;
    }
    public int getMotionTime() {
        return motionTime;
    }
    public void setMotionTime(int motionTime) {
        this.motionTime = motionTime;
    }
    public String getMotionDesc() {
        return motionDesc;
    }
    public void setMotionDesc(String motionDesc) {
        this.motionDesc = motionDesc;
    }
    public String getPlanHead() {
        return planHead;
    }
    public void setPlanHead(String planHead) {
        this.planHead = planHead;
    }
    public int getRestTime() {
        return restTime;
    }
    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }
}
