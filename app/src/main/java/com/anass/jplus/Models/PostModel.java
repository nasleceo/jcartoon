package com.anass.jplus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("post_Time")
    @Expose
    private String postTime;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("ishark")
    @Expose
    private Integer ishark;
    @SerializedName("activecomments")
    @Expose
    private Integer activecomments;
    @SerializedName("tv_id")
    @Expose
    private String tvId;
    @SerializedName("tawsiat_tv_id")
    @Expose
    private int tawsiatTvId;
    @SerializedName("cast_id")
    @Expose
    private int castId;
    @SerializedName("cast_id_2")
    @Expose
    private int castId2;
    @SerializedName("likers_count")
    @Expose
    private Integer likers_count;

    @SerializedName("views_count")
    @Expose
    private Integer views_count;

    @SerializedName("favoriters_count")
    @Expose
    private Integer favoriters_count;

    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("poste_user")
    @Expose
    private UserModel poste_user;

    @SerializedName("poste_cartoon")
    @Expose
    private CartoonModel poste_cartoon;

    @SerializedName("poste_cast")
    @Expose
    private CastModel poste_cast;

    @SerializedName("poste_cast_tani")
    @Expose
    private CastModel poste_cast_tani;

    @SerializedName("poste_comments")
    @Expose
    private List<CommentModel> poste_comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CommentModel> getPoste_comments() {
        return poste_comments;
    }

    public UserModel getPoste_user() {
        return poste_user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIshark() {
        return ishark;
    }

    public void setIshark(Integer ishark) {
        this.ishark = ishark;
    }

    public Integer getActivecomments() {
        return activecomments;
    }

    public void setActivecomments(Integer activecomments) {
        this.activecomments = activecomments;
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public int getTawsiatTvId() {
        return tawsiatTvId;
    }

    public void setTawsiatTvId(int tawsiatTvId) {
        this.tawsiatTvId = tawsiatTvId;
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public int getCastId2() {
        return castId2;
    }

    public void setCastId2(int castId2) {
        this.castId2 = castId2;
    }

    public Integer getLikers_count() {
        return likers_count;
    }

    public Integer getViews_count() {
        return views_count;
    }

    public Integer getFavoriters_count() {
        return favoriters_count;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public CastModel getPoste_cast() {
        return poste_cast;
    }

    public CastModel getPoste_cast_tani() {
        return poste_cast_tani;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CartoonModel getPoste_cartoon() {
        return poste_cartoon;
    }
}