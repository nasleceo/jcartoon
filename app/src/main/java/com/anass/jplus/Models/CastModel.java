package com.anass.jplus.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CastModel implements Serializable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("comic_id")
    @Expose
    private String comicId;
    @SerializedName("typeiscomicornot")
    @Expose
    private String typeiscomicornot;
    @SerializedName("statu")
    @Expose
    private String statu;

    @SerializedName("favorites_count")
    @Expose
    private int favorites_count;
   @SerializedName("comic")
    @Expose
    private CartoonModel comic;



    public Integer getId() {
        return id;
    }

    public CartoonModel getComic() {
        return comic;
    }

    public int getFavorites_count() {
        return favorites_count;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getComicId() {
        return comicId;
    }

    public String getTypeiscomicornot() {
        return typeiscomicornot;
    }

    public String getStatu() {
        return statu;
    }


}
