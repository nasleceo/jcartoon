package com.anass.jplus.API;

import com.anass.jplus.Constant.AppConfig;

public class BEANLINKS {


    static public String BASELINK = AppConfig.url;




    static public final String BASEURL = "https://api.themoviedb.org/3/";
    static public final String APIKAY = "7ee6665aca1f0ec30abb779045e867cd";
    public static String getImagePathLink(String path){
        return "https://image.tmdb.org/t/p/w500/"+path;
    }





}