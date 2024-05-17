package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.EpesodModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EpisodesResponse {


    @SerializedName("data")
    private List<EpesodModel> epesodModels;

    public List<EpesodModel> getEpesodModels() {
        return epesodModels;
    }


}
