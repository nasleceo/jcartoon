package com.anass.jplus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReplayModel {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("comment_id")
    @Expose
    private String comment_id;

    @SerializedName("statu")
    @Expose
    private String statu;
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("likers_count")
    @Expose
    private int likers_count;

    @SerializedName("user")
    @Expose
    private UserModel user;

    public int getLikers_count() {
        return likers_count;
    }

    public UserModel getUser() {
        return user;
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getComment_id() {
        return comment_id;
    }

    public String getStatu() {
        return statu;
    }

    public String getType() {
        return type;
    }
}
