package com.anass.jplus.API.ModelView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.anass.jplus.API.repositories.MovieRepository;
import com.anass.jplus.Models.CatigoriesModel;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.PostModel;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }


    //------------------------ models ------------------------------------//


    public LiveData<List<PostModel>> allposts() {
        return movieRepository.getAllMovies();
    }






    //------------------------ CALLS Movies ------------------------------------//


    public void MoviesApi(int page){
        movieRepository.Movies(page);
    }
    public void SearchMovieApi(String qq,int page){
        movieRepository.SearchMovieApi(qq,page);
    }
    public void MoviesNextPage(){
        movieRepository.NextPageMovies();
    }

    public void TVsApi(int page){
        movieRepository.TVs(page);
    }
    public void trendmApi(int page){
        movieRepository.trendm(page);
    }
    public void GetActionMovie(int page){
        movieRepository.GetActionMovie(page);
    }
    public void GetWatchedMoviesAndSeries(int page){
        movieRepository.GetWatchedMoviesAndSeries(page);
    }
    public void GetGeners(int page){
        movieRepository.GetGeners(page);
    }







    //------------------------ CALLS TVShowsS ------------------------------------//







    //------------------------ Search ------------------------------------//

    public void setQuery(String queryData)
    {
        movieRepository.SearchTeext(queryData);
    }

    public LiveData<String> getquery() {
        return movieRepository.getSearch();
    }

}
