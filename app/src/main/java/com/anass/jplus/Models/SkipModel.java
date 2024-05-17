package com.anass.jplus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkipModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isgo")
    @Expose
    private String isgo;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("version")
    @Expose
    private Integer version;
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

    public String getIsgo() {
        return isgo;
    }

    public void setIsgo(String isgo) {
        this.isgo = isgo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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


}
