package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.PostModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostResponse {

    @SerializedName("current_page")
    private int current_page;

    @SerializedName("data")
    private List<PostModel> getposts;

    @SerializedName("total")
    private int total;


    public int getCurrent_page() {
        return current_page;
    }

    public List<PostModel> getGetposts() {
        return getposts;
    }

    public int getTotal() {
        return total;
    }
}
