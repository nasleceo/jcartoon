package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CartoonModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartoonResponse {

    @SerializedName("current_page")
    private int current_page;

    @SerializedName("data")
    private List<CartoonModel> tvs;

    @SerializedName("total")
    private int total;


    public int getCurrent_page() {
        return current_page;
    }

    public List<CartoonModel> getTvs() {
        return tvs;
    }

    public int getTotal() {
        return total;
    }
}
