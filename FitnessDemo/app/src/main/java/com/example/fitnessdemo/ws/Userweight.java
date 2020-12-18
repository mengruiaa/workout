package com.example.fitnessdemo.ws;

public class Userweight {
    private String phone;
    private String weight;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Userweight{" +
                "phone='" + phone + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
