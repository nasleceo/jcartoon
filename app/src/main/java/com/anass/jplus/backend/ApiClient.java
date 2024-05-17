package com.anass.jplus.backend;

import static com.anass.jplus.API.BEANLINKS.BASELINK;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit == null ){
            return retrofit  = new Retrofit.Builder()
                    .baseUrl(BASELINK)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }



}
