package com.anass.jplus.backend.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.backend.Repositories.CartoonRepository;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.Responses.CastResponse;
import com.anass.jplus.backend.Responses.ChaptersResponse;
import com.anass.jplus.backend.Responses.CommentsResponse;
import com.anass.jplus.backend.Responses.EpisodesResponse;
import com.anass.jplus.backend.Responses.OneCartoonResponse;
import com.anass.jplus.backend.Responses.PostResponse;
import com.anass.jplus.backend.Responses.ReplayResponse;

public class getAlltvsViewModel  extends ViewModel {

    private CartoonRepository allCartoonRepository;

    public getAlltvsViewModel(){
        allCartoonRepository = new CartoonRepository();
    }


    public LiveData<CartoonResponse> getalltvs(int page){
        return allCartoonRepository.getAllTVs(page);
    }


    public LiveData<CartoonResponse> FilterGener(String gener){
        return allCartoonRepository.FilterGener(gener);
    }
    public LiveData<CartoonResponse> Search(String gener){
        return allCartoonRepository.Search(gener);
    }
  public LiveData<CartoonResponse> getAllcomics(int page){
        return allCartoonRepository.getAllcomics(page);
    }

    public LiveData<CartoonResponse> Toptvs(){
        return allCartoonRepository.toptvtoday();
    }

    public LiveData<CartoonResponse> pinedcartoons(){
        return allCartoonRepository.pinedcartoons();
    }

    public LiveData<CartoonModel> slider(){
        return allCartoonRepository.slider();
    }

    public LiveData<CartoonResponse> topmovies(){
        return allCartoonRepository.topmovies();
    }

    public LiveData<EpisodesResponse> getLastEpisodes(){
        return allCartoonRepository.getLastEpisodes();
    }

    public LiveData<ChaptersResponse> getLastChapters(){
        return allCartoonRepository.getLastChapters();
    }

    public LiveData<CartoonResponse> newrelease(){
        return allCartoonRepository.newrelease();
    }

    public LiveData<CartoonResponse> topcomics(){
        return allCartoonRepository.topcomics();
    }
    public LiveData<CartoonResponse> mosttvandmovieaddedtofavourit(){
        return allCartoonRepository.mosttvandmovieaddedtofavourit();
    }
    public LiveData<CartoonResponse> getAllmovies(int page){
        return allCartoonRepository.getAllmovies(page);
    }

    public LiveData<CastResponse> allcastapi(int page){
        return allCartoonRepository.allcastapi(page);
    }
    public LiveData<OneCartoonResponse> oneTvorCartoonorComic(int page){
        return allCartoonRepository.oneTvorCartoonorComic(page);
    }
    public LiveData<CommentsResponse> getCartoonCommentsByID(int cartoon_id,int page){
        return allCartoonRepository.getCartoonCommentsByID(cartoon_id,page);
    }
    public LiveData<CommentsResponse> commentsofcastt(int cartoon_id,int page){
        return allCartoonRepository.commentsofcastt(cartoon_id,page);
    }
    public LiveData<CommentsResponse> getPostCommentsByID(int cartoon_id,int page){
        return allCartoonRepository.getPostCommentsByID(cartoon_id,page);
    }
    public LiveData<ReplayResponse> replaysofcomments(int cartoon_id, int page){
        return allCartoonRepository.replaysofcomments(cartoon_id,page);
    }
    public LiveData<PostResponse> posts(int page){
        return allCartoonRepository.posts(page);
    }
    public LiveData<PostResponse> mostliked(int page){
        return allCartoonRepository.mostliked(page);
    }
    public LiveData<PostResponse> trendpost(String country,int page){
        return allCartoonRepository.trendpost(country,page);
    }
 public LiveData<PostResponse> random(int page){
        return allCartoonRepository.random(page);
    }
}
