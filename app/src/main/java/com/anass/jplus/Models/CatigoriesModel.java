package com.anass.jplus.Models;

public class CatigoriesModel {

    int id;
    String name;
    String icon;
    String status;

    public CatigoriesModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
