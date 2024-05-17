package com.anass.jplus.API;



import static com.anass.jplus.API.BEANLINKS.BASELINK;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicy {


    private static Retrofit.Builder retrofBuilder = new Retrofit.Builder()
            .baseUrl(BASELINK)
            .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = retrofBuilder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }





}
