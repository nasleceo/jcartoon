package com.anass.jplus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentModel {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("tv_id")
    @Expose
    private String tvId;
    @SerializedName("news_id")
    @Expose
    private String newsId;
    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("ishark")
    @Expose
    private Integer ishark;
    @SerializedName("statu")
    @Expose
    private String statu;
    @SerializedName("type")
    @Expose
    private String type;

   @SerializedName("likers_count")
    @Expose
    private int likers_count;

    @SerializedName("replay_count")
    @Expose
    private int replay_count;
    @SerializedName("user")
    @Expose
    private UserModel user;

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getReplay_count() {
        return replay_count;
    }

    public int getLikers_count() {
        return likers_count;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Integer getIshark() {
        return ishark;
    }

    public void setIshark(Integer ishark) {
        this.ishark = ishark;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserModel getUser() {
        return user;
    }
}
