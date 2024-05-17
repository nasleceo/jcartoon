package com.anass.jplus.API.Respons;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDB_API {


    @GET("movie/{movie_id}/credits")
    Call<MovieCastRespons> getMovieCast(
            @Path("movie_id")    int movie_id,
            @Query("api_key")    String api_key
    );
    @GET("tv/{tv_id}/credits")
    Call<MovieCastRespons> getTVCast(
            @Path("tv_id")    int movie_id,
            @Query("api_key")    String api_key
    );

}
