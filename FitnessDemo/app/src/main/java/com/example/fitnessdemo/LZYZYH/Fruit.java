package com.example.fitnessdemo.LZYZYH;

public class Fruit {

    private String name;
    private int i;
    private int imageId;

    public Fruit(String name, int imageId, int i) {
        this.name = name;
        this.i=i;
        this.imageId = imageId;
    }

    public String getName() {return name; }
    public int geti(){return i;}
    public int getImageId() {
        return imageId;
    }

}
