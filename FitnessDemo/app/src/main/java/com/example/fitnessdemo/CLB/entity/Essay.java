package com.example.fitnessdemo.CLB.entity;

public class Essay {
    private int id;
    private String title;
    private String type;
    private String date;
    private String pitcure;
    private String parentName;
    private int number;
    private String url;
    public Essay(){

    }

    public Essay(int id, String title, String type, String date, String pitcure, String parentName, int number, String url) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.date = date;
        this.pitcure = pitcure;
        this.parentName = parentName;
        this.number = number;
        this.url = url;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPitcure() {
        return pitcure;
    }

    public void setPitcure(String pitcure) {
        this.pitcure = pitcure;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Essay{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", pitcure='" + pitcure + '\'' +
                ", parentName='" + parentName + '\'' +
                ", number=" + number +
                ", url='" + url + '\'' +
                '}';
    }
}