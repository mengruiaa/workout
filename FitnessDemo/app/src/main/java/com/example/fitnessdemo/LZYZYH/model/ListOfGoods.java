package com.example.fitnessdemo.LZYZYH.model;

import android.graphics.Bitmap;

import com.example.fitnessdemo.LZYZYH.util.BitmapUtil;

public class ListOfGoods {
    private int id;

    /**
     * 标题
     */
    private String biaoti;
    /**
     * 现价
     */
    private String jiage;
    /**
     * 原价
     */
    private String yuanjia;
    private Bitmap img;
    public ListOfGoods(){

    }

    public ListOfGoods(int goodsId, String biaoti, String price) {
        this.id = goodsId;
        this.biaoti = biaoti;
        this.jiage = price;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getJiage() {
        return jiage;
    }

    public void setJiage(String jiage) {
        this.jiage = jiage;
    }

    public String getYuanjia() {
        return yuanjia;
    }

    public void setYuanjia(String yuanjia) {
        this.yuanjia = yuanjia;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
