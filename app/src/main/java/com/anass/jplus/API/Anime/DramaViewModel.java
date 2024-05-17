package com.anass.jplus.API.Anime;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anasskikanime.models.AnimeModel;

import java.util.List;

public class DramaViewModel extends ViewModel {

    DramaRepository movieRepository;

    public DramaViewModel() {
        movieRepository = DramaRepository.getInstance();
    }

    // Models
    public LiveData<List<AnimeModel>> getRecentEpisode() {
        return movieRepository.getDrama();
    }

    public LiveData<List<AnimeModel>> getDRama() {
        return movieRepository.getDramalist();
    }
    public LiveData<List<AnimeModel>> Getrecentaddedmodel() {
        return movieRepository.Getrecentaddedmodel();
    }
    public LiveData<List<AnimeModel>> Getasianmoviemodel() {
        return movieRepository.Getasianmoviemodel();
    }
    public LiveData<List<AnimeModel>> Getcomingdramamodel() {
        return movieRepository.Getcomingdramamodel();
    }
   public LiveData<List<AnimeModel>> Showmodel() {
        return movieRepository.Showmodel();
    }

    public LiveData<List<AnimeModel>> SearchModel() {
        return movieRepository.SearchModel();
    }

    //Getdata

    public void SearchGo(String q,int page){
        movieRepository.Search(q,page);
    }
    public void SearchMulty(String getTasnif,String getType,String getStatu,
                            String getYear,int pageNumber){
        movieRepository.SearchMulty(getTasnif,
                getType,getStatu,getYear,pageNumber);
    }

    public void NextPageSearch(){
        movieRepository.NextPageSearch();
    }

    public void RecentEpisodeGo(int page){
        movieRepository.RecentEpisode(page);
    }
    public void RecentEpisodeNextPage(){
        movieRepository.NextPage();
    }


    public void DramaGo(int page){
        movieRepository.Dramalist(page);
    }
    public void DramaNextPage(){
        movieRepository.NextPageDramalist();
    }

    public void recentaddedCall(String q,int page){
        movieRepository.recentaddedCall(q,page);
    }
    public void asianmovieCall(String q,int page){
        movieRepository.asianmovieCall(q,page);
    }
    public void NextasianmovieCall(){
        movieRepository.NextasianmovieCall();
    }
    public void comingdramaCall(String q,int page){
        movieRepository.comingdramaCall(q,page);
    }

    public void ShowCall(String q,int page){
        movieRepository.ShowCall(q,page);
    }
}
