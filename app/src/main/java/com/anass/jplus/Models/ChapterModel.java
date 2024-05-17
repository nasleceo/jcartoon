package com.anass.jplus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChapterModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("comic_id")
    @Expose
    private String comicId;
    @SerializedName("direct_link")
    @Expose
    private String directLink;
    @SerializedName("chapters_folder_link")
    @Expose
    private String chaptersFolderLink;
    @SerializedName("statu")
    @Expose
    private String  statu;

    @SerializedName("comic")
    @Expose
    private CartoonModel  comic;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getDirectLink() {
        return directLink;
    }

    public void setDirectLink(String directLink) {
        this.directLink = directLink;
    }

    public String getChaptersFolderLink() {
        return chaptersFolderLink;
    }

    public void setChaptersFolderLink(String chaptersFolderLink) {
        this.chaptersFolderLink = chaptersFolderLink;
    }

    public CartoonModel getComic() {
        return comic;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

}