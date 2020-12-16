package com.example.fitnessdemo.ws;

import android.graphics.Bitmap;

public class Usershow {
    private String name;
    private String phone;
    private int age;
    private String sex;
    private Bitmap bitmap;

    public Usershow(String name, String phone, Bitmap bitmap, int age, String sex) {
        this.name = name;
        this.phone = phone;
        this.bitmap = bitmap;
        this.age = age;
        this.sex = sex;
    }

    public Usershow() {
    }

    public Usershow(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "Usershow{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", bitmap=" + bitmap +
                '}';
    }
}
