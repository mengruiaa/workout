package com.example.fitnessdemo.MR.entity;

import android.graphics.Bitmap;

public class CoursePictureShow {
    private int couese_id;
    private String name;
    private String picture;

    public CoursePictureShow(int couese_id, String name, String picture) {
        this.couese_id = couese_id;
        this.name = name;
        this.picture = picture;
    }

    public int getCouese_id() {
        return couese_id;
    }

    public void setCouese_id(int couese_id) {
        this.couese_id = couese_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
