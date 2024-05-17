package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.favouritModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoutirResponse {


    @SerializedName("data")
    private List<favouritModel> favourits;


    public List<favouritModel> getFavourits() {
        return favourits;
    }
}
