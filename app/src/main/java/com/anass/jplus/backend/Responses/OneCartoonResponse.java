package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.Cast;
import com.anass.jplus.Models.CastModel;
import com.anass.jplus.Models.NewsModel;
import com.anass.jplus.Models.PostModel;
import com.anass.jplus.Models.RoomModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneCartoonResponse {



    @SerializedName("tv")
    private CartoonModel tv;

    @SerializedName("visit_count")
    private int visit_count;

 @SerializedName("seasoncount")
    private int seasoncount;

 @SerializedName("rate")
    private double rate;

    @SerializedName("type")
    private String type;

    @SerializedName("ads_img")
    private String ads_img;


  @SerializedName("ads_link")
    private String ads_link;


    @SerializedName("rooms")
    private List<RoomModel> rooms;

    @SerializedName("news")
    private List<NewsModel> news;

   @SerializedName("Cast")
    private List<CastModel> Cast;

    @SerializedName("review")
    private List<PostModel> review;



    @SerializedName("tawsia")
    private List<PostModel> tawsia;

    @SerializedName("iktirahat")
    private List<CartoonModel> iktirahat;


    public CartoonModel getTv() {
        return tv;
    }

    public int getVisit_count() {
        return visit_count;
    }

    public int getSeasoncount() {
        return seasoncount;
    }

    public double getRate() {
        return rate;
    }

    public String getType() {
        return type;
    }

    public String getAds_img() {
        return ads_img;
    }

    public String getAds_link() {
        return ads_link;
    }

    public List<RoomModel> getRooms() {
        return rooms;
    }

    public List<NewsModel> getNews() {
        return news;
    }

    public List<CastModel> getCast() {
        return Cast;
    }

    public List<PostModel> getReview() {
        return review;
    }


    public List<PostModel> getTawsia() {
        return tawsia;
    }

    public List<CartoonModel> getIktirahat() {
        return iktirahat;
    }
}
