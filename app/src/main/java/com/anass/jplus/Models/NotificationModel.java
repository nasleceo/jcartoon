package com.anass.jplus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("sender_user_id")
    @Expose
    private Object senderUserId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("post_Time")
    @Expose
    private String postTime;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("cartoon_id")
    @Expose
    private Object cartoonId;
    @SerializedName("post_id")
    @Expose
    private Object postId;
    @SerializedName("comment_id")
    @Expose
    private Object commentId;
    @SerializedName("replay_id")
    @Expose
    private Object replayId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("notification_user")
    @Expose
    private UserModel notificationUser;
    @SerializedName("notification_cartoon")
    @Expose
    private CartoonModel notificationCartoon;
    @SerializedName("notification_post")
    @Expose
    private PostModel notificationPost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Object senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Object getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(Object cartoonId) {
        this.cartoonId = cartoonId;
    }

    public Object getPostId() {
        return postId;
    }

    public void setPostId(Object postId) {
        this.postId = postId;
    }

    public Object getCommentId() {
        return commentId;
    }

    public void setCommentId(Object commentId) {
        this.commentId = commentId;
    }

    public Object getReplayId() {
        return replayId;
    }

    public void setReplayId(Object replayId) {
        this.replayId = replayId;
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

    public UserModel getNotificationUser() {
        return notificationUser;
    }

    public CartoonModel getNotificationCartoon() {
        return notificationCartoon;
    }


    public PostModel getNotificationPost() {
        return notificationPost;
    }

}
