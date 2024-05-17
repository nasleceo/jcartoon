package com.anass.jplus.API.repositories;

import androidx.lifecycle.LiveData;


import com.anass.jplus.API.MovieApiClient;
import com.anass.jplus.Models.CatigoriesModel;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.PostModel;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private static String Qerie;
    private static int MoviesPage,tvsPage;
    private static int nID;

    public static MovieRepository getInstance(){
        if (instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<CartoonModel>> getSearcheMoives(){
        return movieApiClient.getSearchedMoives();
    }


    //-----------------Moives All ---------------//


    public LiveData<List<PostModel>> getAllMovies(){
        return movieApiClient.getMoives();
    }

    public void Movies(int page){
        MoviesPage = page;
        movieApiClient.GetMoviesApi(page);
    }

  public void SearchMovieApi(String qq,int page){
        MoviesPage = page;
        movieApiClient.SearchMovieApi(qq,page);
    }

    public void NextPageMovies(){
        movieApiClient.GetMoviesApi(MoviesPage+1);
    }


    public LiveData<List<CartoonModel>> geTALLTVs(){
        return movieApiClient.getTVS();
    }

    public void TVs(int page){
        tvsPage = page;
        movieApiClient.GetTVsHOWSsApi(page);
    }



    public LiveData<List<CartoonModel>> geTTrends(){
        return movieApiClient.getTrending();
    }

    public void trendm(int page){
        tvsPage = page;
        movieApiClient.GetTrendingApi(page);
    }


    public LiveData<List<CartoonModel>> getmAction(){
        return movieApiClient.getmAction();
    }

    public void GetActionMovie(int page){
        tvsPage = page;
        movieApiClient.GetActionMovieApi(page);
    }

    public LiveData<List<CartoonModel>> getmswatched(){
        return movieApiClient.getmswatched();
    }

    public void GetWatchedMoviesAndSeries(int page){
        tvsPage = page;
        movieApiClient.GetWatchedMoviesAndSeries(page);
    }

    public LiveData<List<CatigoriesModel>> GenersModel(){
        return movieApiClient.GenersModel();
    }

    public void GetGeners(int page){
        tvsPage = page;
        movieApiClient.GetGeners(page);
    }




    public LiveData<String> getSearch(){
        return movieApiClient.getSearchQue();
    }
    public void SearchTeext(String ss){
        movieApiClient.SetSearch(ss);
    }



}
