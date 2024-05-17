package com.anass.jplus.API.Respons;

import com.anass.jplus.Models.CatigoriesModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {

    @SerializedName("data")
    @Expose()
    private List<CatigoriesModel> getigories;


    public List<CatigoriesModel> getGetigories() {
        return getigories;
    }
}
