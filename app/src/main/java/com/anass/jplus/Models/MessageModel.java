package com.anass.jplus.Models;

public class MessageModel {

    private String imageProfile;
    private String message;
    private String nameUser;
    private long time;
    private int userId;

    public MessageModel(String imageProfile, String message, String nameUser, long time, int userId) {
        this.imageProfile = imageProfile;
        this.message = message;
        this.nameUser = nameUser;
        this.time = time;
        this.userId = userId;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
