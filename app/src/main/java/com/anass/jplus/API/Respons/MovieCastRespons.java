package com.anass.jplus.API.Respons;

import com.anass.jplus.Models.CastModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCastRespons {

    @SerializedName("id")
    @Expose()
    private int id;

    @SerializedName("cast")
    @Expose()
    private List<CastModel> casts;



    public List<CastModel> getCasts() {
        return casts;
    }
}
