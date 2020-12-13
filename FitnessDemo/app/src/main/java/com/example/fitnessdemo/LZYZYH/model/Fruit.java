package com.example.fitnessdemo.LZYZYH.model;

public class Fruit {

    private String name;
    private int i;
    private int imageId;
    private String price;

    public Fruit(String name, int imageId, int i,String price) {
        this.name = name;
        this.i=i;
        this.imageId = imageId;
        this.price = price;
    }

    public String getName() {return name; }
    public int geti(){return i;}
    public int getImageId() {
        return imageId;
    }
    public String getPrice(){return price;}
}
