package com.example.fitnessdemo.ws;

public class Userfocus {
    private String name;
    private String friendname;

    public Userfocus(String name, String friendname) {
        this.name = name;
        this.friendname = friendname;
    }

    public Userfocus() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }
}
