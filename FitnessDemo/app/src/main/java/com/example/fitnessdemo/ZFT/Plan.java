package com.example.fitnessdemo.ZFT;

import java.io.Serializable;

public class Plan implements Serializable {
    private int id;
    private String typeName;
    private String planName;
    private int planStar;
    private String planImg;
    private String planinfo;
    private String planinfoImg;
    private String planPeople;
    private String planTime;

    public String getPlaninfoImg() {
        return planinfoImg;
    }

    public void setPlaninfoImg(String planinfoImg) {
        this.planinfoImg = planinfoImg;
    }

    public String getPlanPeople() {
        return planPeople;
    }

    public void setPlanPeople(String planPeople) {
        this.planPeople = planPeople;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getPlaninfo() {
        return planinfo;
    }

    public void setPlaninfo(String planinfo) {
        this.planinfo = planinfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getPlanStar() {
        return planStar;
    }

    public void setPlanStar(int planStar) {
        this.planStar = planStar;
    }

    public String getPlanImg() {
        return planImg;
    }

    public void setPlanImg(String planImg) {
        this.planImg = planImg;
    }
}
