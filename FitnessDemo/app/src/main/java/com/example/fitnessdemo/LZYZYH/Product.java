package com.example.fitnessdemo.LZYZYH;

public class Product {
    private String name;
    private String img;
 //   private int id;
    private float price;
    private String url;

    public Product() {
    }

    public Product(String name, String img, float price, String url) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
