package com.anass.jplus.Models;

public class YTStreamList {
    String name;
    String vid;
    String token;

    public YTStreamList(String name, String vid, String token) {
        this.name = name;
        this.vid = vid;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
