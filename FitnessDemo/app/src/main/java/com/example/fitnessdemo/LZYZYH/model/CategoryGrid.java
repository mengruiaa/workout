package com.example.fitnessdemo.LZYZYH.model;

import android.graphics.Bitmap;

public class CategoryGrid {

    private Bitmap img_grid;
    private String txt_name;
    private float txt_price;

    public String getTxt_name() {
        return txt_name;
    }

    public void setTxt_name(String txt_name) {
        this.txt_name = txt_name;
    }

    public float getTxt_price() {
        return txt_price;
    }

    public void setTxt_price(float txt_price) {
        this.txt_price = txt_price;
    }

    public Bitmap getImg_grid() {
        return img_grid;
    }

    public void setImg_grid(Bitmap img_grid) {
        this.img_grid = img_grid;
    }

}
