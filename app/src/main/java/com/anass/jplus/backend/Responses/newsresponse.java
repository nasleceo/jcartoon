package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.NewsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class newsresponse {

    @SerializedName("data")
    private List<NewsModel> data;


    public List<NewsModel> getData() {
        return data;
    }
}
