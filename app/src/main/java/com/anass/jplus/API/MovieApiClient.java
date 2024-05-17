package com.anass.jplus.API;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.anass.jplus.API.Respons.CategoriesResponse;
import com.anass.jplus.API.Respons.MovieSearchRespons;
import com.anass.jplus.Models.CatigoriesModel;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.PostModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //LiveData
    private MutableLiveData<List<CartoonModel>> SerarchedMovies,mTVsHOWS,mTrending,mAction,mswatched;
    private MutableLiveData<List<CatigoriesModel>> GenersModel;
    private MutableLiveData<List<PostModel>> mMovies;
    private MutableLiveData<String> searchqq ;
    private static MovieApiClient instance;


    private RetriveSearchMoviesRunnable retriveMoviesRunnable;
    private RetriveTrendingm retriveTrendingm;
    private RetriveActionm retriveActionm;
    private RetriveWatchedMoviesAndSeries retriveWatchedMoviesAndSeries;
    private RetriveGetGeners retriveGetGeners;



    public static MovieApiClient getInstance(){
        if (instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }



    private MovieApiClient(){
        SerarchedMovies = new MutableLiveData<>();
        mMovies = new MutableLiveData<>();
        mTVsHOWS = new MutableLiveData<>();
        mTrending = new MutableLiveData<>();
        mAction = new MutableLiveData<>();
        mswatched = new MutableLiveData<>();
        GenersModel = new MutableLiveData<>();



    }

    public LiveData<List<CartoonModel>> getSearchedMoives(){
        return SerarchedMovies;
    }
    public LiveData<List<PostModel>> getMoives(){
        return mMovies;
    }
    public LiveData<List<CartoonModel>> getTVS(){
        return mTVsHOWS;
    }
    public LiveData<List<CartoonModel>> getTrending(){
        return mTrending;
    }
    public LiveData<List<CartoonModel>> getmAction(){
        return mAction;
    }
    public LiveData<List<CartoonModel>> getmswatched(){
        return mswatched;
    }
    public LiveData<List<CatigoriesModel>> GenersModel(){
        return GenersModel;
    }
    public LiveData<String> getSearchQue(){
        return searchqq;
    }


    public void SetSearch(String ss){
        searchqq.setValue(ss);
    }
    public void SearchMovieApi(String query, int pageNumber){

        if (retriveMoviesRunnable != null){
            retriveMoviesRunnable = null;
        }

        retriveMoviesRunnable = new RetriveSearchMoviesRunnable(query,pageNumber);

        final Future myHandler = AppExecutors.getInstance().getmNetworkIO().submit(retriveMoviesRunnable);

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);

            }
        },3000, TimeUnit.MILLISECONDS);
    }
    private class RetriveSearchMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelReq;

        public RetriveSearchMoviesRunnable(String query, int pageNumber) {
            Log.v("tag","cancelling Search Req"+query);

            this.query = query;
            this.pageNumber = pageNumber;
            cancelReq = false;
        }

        @Override
        public void run() {

            try {
                Response response = getMovies(query,pageNumber).execute();

                if (cancelReq){
                    return;
                }

                if (response.code() == 200){
                    List<CartoonModel> list = new ArrayList<>(((MovieSearchRespons)response.body()).getMovies());

                    SerarchedMovies.postValue(list);

                }else {
                    String eeror = response.errorBody().string();
                    Log.v("tag","Error  "+eeror);
                    SerarchedMovies.postValue(null);
                }



            } catch (IOException e) {
                e.printStackTrace();
                SerarchedMovies.postValue(null);
            }


        }

        private Call<MovieSearchRespons> getMovies(String qq , int pageNumber){
            return Servicy.getMovieApi().searchmo(
                    qq);
        }

        private void cancelrequest(){
            Log.v("tag","cancelling Search Req");
            cancelReq = true;
        }
    }



    public void GetMoviesApi(int pagenumber){


    }

    public void GetTVsHOWSsApi(int pagenumber){


    }


    public void GetTrendingApi(int pagenumber){

        if (retriveTrendingm != null){
            retriveTrendingm = null;
        }

        retriveTrendingm = new RetriveTrendingm(pagenumber);

        final Future myHandler2 = AppExecutors.getInstance().getmNetworkIO().submit(retriveTrendingm);

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);

            }
        },3000, TimeUnit.MILLISECONDS);
    }
    private class RetriveTrendingm implements Runnable {

        private int pageNumber;
        boolean cancelReq;

        public RetriveTrendingm( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelReq = false;
        }

        @Override
        public void run() {

            try {
                Response response = getMovies(pageNumber).execute();
                Log.v("tag","msg  "+response.body().toString());
                if (cancelReq){
                    return;
                }

                if (response.code() == 200){
                    List<CartoonModel> list = new ArrayList<>(((MovieSearchRespons)response.body()).getMovies());

                    if (pageNumber == 1){
                        mTrending.postValue(list);
                    }else {
                        List<CartoonModel> currentMovies = mTrending.getValue();
                        currentMovies.addAll(list);
                        mTrending.postValue(currentMovies);
                    }

                }else {
                    String eeror = response.errorBody().string();
                    Log.v("tag","Error  "+eeror);
                    mTrending.postValue(null);
                }



            } catch (IOException e) {
                e.printStackTrace();
                mTrending.postValue(null);
            }


        }

        private Call<MovieSearchRespons> getMovies(int pn){
            return Servicy.getMovieApi().getTrendingMovie(


            );
        }

        private void cancelrequest(){
            Log.v("tag","cancelling Search Req");
            cancelReq = true;
        }
    }

    public void GetActionMovieApi(int pagenumber){

        if (retriveActionm != null){
            retriveActionm = null;
        }

        retriveActionm = new RetriveActionm(pagenumber);

        final Future myHandler2 = AppExecutors.getInstance().getmNetworkIO().submit(retriveActionm);

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);

            }
        },3000, TimeUnit.MILLISECONDS);
    }
    private class RetriveActionm implements Runnable {

        private int pageNumber;
        boolean cancelReq;

        public RetriveActionm( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelReq = false;
        }

        @Override
        public void run() {

            try {
                Response response = getMovies(pageNumber).execute();
                Log.v("tag","msg  "+response.body().toString());
                if (cancelReq){
                    return;
                }

                if (response.code() == 200){
                    List<CartoonModel> list = new ArrayList<>(((MovieSearchRespons)response.body()).getMovies());

                    if (pageNumber == 1){
                        mAction.postValue(list);
                    }else {
                        List<CartoonModel> currentMovies = mAction.getValue();
                        currentMovies.addAll(list);
                        mAction.postValue(currentMovies);
                    }

                }else {
                    String eeror = response.errorBody().string();
                    Log.v("tag","Error  "+eeror);
                    mAction.postValue(null);
                }



            } catch (IOException e) {
                e.printStackTrace();
                mAction.postValue(null);
            }


        }

        private Call<MovieSearchRespons> getMovies(int pn){
            return Servicy.getMovieApi().getactionmovie();
        }

        private void cancelrequest(){
            Log.v("tag","cancelling Search Req");
            cancelReq = true;
        }
    }


    public void GetWatchedMoviesAndSeries(int pagenumber){

        if (retriveWatchedMoviesAndSeries != null){
            retriveWatchedMoviesAndSeries = null;
        }

        retriveWatchedMoviesAndSeries = new RetriveWatchedMoviesAndSeries(pagenumber);

        final Future myHandler2 = AppExecutors.getInstance().getmNetworkIO().submit(retriveWatchedMoviesAndSeries);

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);

            }
        },3000, TimeUnit.MILLISECONDS);
    }
    private class RetriveWatchedMoviesAndSeries implements Runnable {

        private int pageNumber;
        boolean cancelReq;

        public RetriveWatchedMoviesAndSeries( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelReq = false;
        }

        @Override
        public void run() {

            try {
                Response response = getMovies(pageNumber).execute();
                Log.v("tag","msg  "+response.body().toString());
                if (cancelReq){
                    return;
                }

                if (response.code() == 200){
                    List<CartoonModel> list = new ArrayList<>(((MovieSearchRespons)response.body()).getMovies());

                    if (pageNumber == 1){
                        mswatched.postValue(list);
                    }else {
                        List<CartoonModel> currentMovies = mswatched.getValue();
                        currentMovies.addAll(list);
                        mswatched.postValue(currentMovies);
                    }

                }else {
                    String eeror = response.errorBody().string();
                    Log.v("tag","Error  "+eeror);
                    mswatched.postValue(null);
                }



            } catch (IOException e) {
                e.printStackTrace();
                mswatched.postValue(null);
            }


        }

        private Call<MovieSearchRespons> getMovies(int pn){
            return Servicy.getMovieApi().getWatchedMoviesAndSeries();
        }

        private void cancelrequest(){
            Log.v("tag","cancelling Search Req");
            cancelReq = true;
        }
    }

    public void GetGeners(int pagenumber){

        if (retriveGetGeners != null){
            retriveGetGeners = null;
        }

        retriveGetGeners = new RetriveGetGeners(pagenumber);

        final Future myHandler2 = AppExecutors.getInstance().getmNetworkIO().submit(retriveGetGeners);

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);

            }
        },3000, TimeUnit.MILLISECONDS);
    }
    private class RetriveGetGeners implements Runnable {

        private int pageNumber;
        boolean cancelReq;

        public RetriveGetGeners( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelReq = false;
        }

        @Override
        public void run() {

            try {
                Response response = getMovies(pageNumber).execute();
                Log.v("tag","msg  "+response.body().toString());
                if (cancelReq){
                    return;
                }

                if (response.code() == 200){
                    List<CatigoriesModel> list = new ArrayList<>(((CategoriesResponse)response.body()).getGetigories());

                    if (pageNumber == 1){
                        GenersModel.postValue(list);
                    }else {
                        List<CatigoriesModel> currentMovies = GenersModel.getValue();
                        currentMovies.addAll(list);
                        GenersModel.postValue(currentMovies);
                    }

                }else {
                    String eeror = response.errorBody().string();
                    Log.v("tag","Error  "+eeror);
                    GenersModel.postValue(null);
                }



            } catch (IOException e) {
                e.printStackTrace();
                GenersModel.postValue(null);
            }


        }

        private Call<CategoriesResponse> getMovies(int pn){
            return Servicy.getMovieApi().getgeners();
        }

        private void cancelrequest(){
            Log.v("tag","cancelling Search Req");
            cancelReq = true;
        }
    }
}

