package com.example.fitnessdemo.ZFT;

public class Plan {
    private int id;
    private String typeName;
    private String planName;
    private int planStar;
    private String planImg;
    private String planinfo;
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
