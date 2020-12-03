package com.example.fitnessdemo.CLB.entity;

public class Entry1 {
    private int id;
    private String name;
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
    public Entry1() {

    }
    public Entry1(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Entry [id=" + id + ", name=" + name + "]";
    }
}
