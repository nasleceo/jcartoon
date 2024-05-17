package com.anass.jplus.Config;

import android.content.Context;
import android.util.Log;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.R;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class JsonReader {

   /* public static CartoonModel convertJsonToModel(Context context){
        InputStream is = context.getResources().openRawResource(R.raw.slider);
        String jsonString = "";
        try {
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();

             jsonString = new String(data, "UTF-8");



        } catch (IOException e) {
            Log.d("TAG", "convertJsonToModel: "+e.getMessage());
        }

        return new Gson().fromJson(jsonString,new TypeToken<CartoonModel>(){}.getType());
    }

    public static List<CartoonModel> Getalltvs(Context context){
        InputStream is = context.getResources().openRawResource(R.raw.tv);
        String jsonString = "";
        try {
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();

            jsonString = new String(data, "UTF-8");



        } catch (IOException e) {
            Log.d("TAG", "convertJsonToModel: "+e.getMessage());
        }

        List<CartoonModel> videos = Arrays.asList(new Gson().fromJson(jsonString, CartoonModel[].class));
        List<CartoonModel> videosss = new Gson().fromJson(jsonString, new TypeToken<List<CartoonModel>>(){}.getType());


        return videos;
    }

    public static List<CartoonModel> GetallMovies(Context context){
        InputStream is = context.getResources().openRawResource(R.raw.movies);
        String jsonString = "";
        try {
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();

            jsonString = new String(data, "UTF-8");



        } catch (IOException e) {
            Log.d("TAG", "convertJsonToModel: "+e.getMessage());
        }

        List<CartoonModel> videos = Arrays.asList(new Gson().fromJson(jsonString, CartoonModel[].class));
        List<CartoonModel> videosss = new Gson().fromJson(jsonString, new TypeToken<List<CartoonModel>>(){}.getType());


        return videos;
    }

    public static List<CartoonModel> GetallComics(Context context){
        InputStream is = context.getResources().openRawResource(R.raw.comics);
        String jsonString = "";
        try {
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();

            jsonString = new String(data, "UTF-8");



        } catch (IOException e) {
            Log.d("TAG", "convertJsonToModel: "+e.getMessage());
        }

        List<CartoonModel> videos = Arrays.asList(new Gson().fromJson(jsonString, CartoonModel[].class));
        List<CartoonModel> videosss = new Gson().fromJson(jsonString, new TypeToken<List<CartoonModel>>(){}.getType());


        return videos;
    }*/

}
