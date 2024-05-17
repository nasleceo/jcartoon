package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.CastModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {

    @SerializedName("current_page")
    private int current_page;

    @SerializedName("data")
    private List<CastModel> casts;

    @SerializedName("total")
    private int total;


    public int getCurrent_page() {
        return current_page;
    }

    public List<CastModel> getCasts() {
        return casts;
    }

    public int getTotal() {
        return total;
    }
}
