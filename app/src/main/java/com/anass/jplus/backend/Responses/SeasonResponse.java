package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.SeasonModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonResponse {


    @SerializedName("data")
    private List<SeasonModel> seasons;


    public List<SeasonModel> getSeasons() {
        return seasons;
    }
}
