package com.anass.jplus.API.Respons;

import com.anass.jplus.Models.CartoonModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchRespons {


    @SerializedName("data")
    @Expose()
    private List<CartoonModel> movis;



    public List<CartoonModel> getMovies(){
         return movis;

    }


}
