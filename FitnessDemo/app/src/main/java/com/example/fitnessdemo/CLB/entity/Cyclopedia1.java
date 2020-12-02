package com.example.fitnessdemo.CLB.entity;

public class Cyclopedia1 {
    private int id;
    private String name;
    private String parentName;
    private int attentionNumber;
    private String pitcure;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public int getAttentionNumber() {
        return attentionNumber;
    }
    public void setAttentionNumber(int attentionNumber) {
        this.attentionNumber = attentionNumber;
    }
    public String getPitcure() {
        return pitcure;
    }
    public void setPitcure(String pitcure) {
        this.pitcure = pitcure;
    }
    @Override
    public String toString() {
        return "Cyclopedia [id=" + id + ", name=" + name + ", parentName=" + parentName + ", attentionNumber="
                + attentionNumber + ", pitcure=" + pitcure + "]";
    }
    public Cyclopedia1(int id, String name, String parentName, int attentionNumber, String pitcure) {
        super();
        this.id = id;
        this.name = name;
        this.parentName = parentName;
        this.attentionNumber = attentionNumber;
        this.pitcure = pitcure;
    }
    public Cyclopedia1() {

    }
}
