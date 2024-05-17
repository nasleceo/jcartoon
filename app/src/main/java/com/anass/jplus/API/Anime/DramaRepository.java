package com.anass.jplus.API.Anime;

import androidx.lifecycle.LiveData;

import com.anasskikanime.models.AnimeModel;

import java.util.List;

public class DramaRepository {


    private static DramaRepository instance;

    private DramaClient dramaClient;

    private static String URl,type,gener,statu,year,dramalisturl;
    private static int nPage,moviepage,REcentepepage,dramalistpage,Searchpagen,multipage,tdramapage
    ,showspage,uploadpage,recenetaddPage,comingdramaPage,asianmoviePage,recentaddedDramaPage;
    private static int nID;

    public static DramaRepository getInstance(){
        if (instance == null){
            instance = new DramaRepository();
        }
        return instance;
    }

    private DramaRepository(){
        dramaClient = DramaClient.getInstance();
    }

    public LiveData<List<AnimeModel>> getDrama(){
        return dramaClient.GetDramas();
    }

    public LiveData<List<AnimeModel>> getDramalist(){
        return dramaClient.GetDrama();
    }

    public LiveData<List<AnimeModel>> Getrecentaddedmodel(){
        return dramaClient.Getrecentaddedmodel();
    }


    public LiveData<List<AnimeModel>> Getasianmoviemodel(){
        return dramaClient.Getasianmoviemodel();
    }
    public LiveData<List<AnimeModel>> Getcomingdramamodel(){
        return dramaClient.Getcomingdramamodel();
    }
    public LiveData<List<AnimeModel>> Showmodel(){
        return dramaClient.Showmodel();
    }
    public LiveData<List<AnimeModel>> SearchModel(){
        return dramaClient.SearchModel();
    }



    public void Search(String q,int page){
        URl = q;
        Searchpagen = page;
        dramaClient.SearchCall(q,page);

    }
    public void NextPageSearch(){

        Search(URl,Searchpagen+1);
    }
    public void SearchMulty(String getTasnif,String getType,String getStatu,
                            String getYear,int pageNumber){
        multipage = pageNumber;
        type = getTasnif;
        gener = getType;
        statu = getStatu;
        year = getYear;


      /*  dramaClient.SearchMultyCall(getTasnif,
                getType,getStatu,getYear,pageNumber);*/

    }
    public void NextPageSearchMulty(){
        SearchMulty(type,gener,statu,year,multipage+1);
    }
    public void RecentEpisode(int page){
        REcentepepage = page;
        dramaClient.RecentEpisodesCall(page);

    }
    public void NextPage(){
        dramaClient.RecentEpisodesCall(REcentepepage+1);


    }





    public void Dramalist(int page){
        dramalistpage = page;
        dramaClient.DramalistCall(page);

    }
    public void NextPageDramalist(){
        Dramalist(dramalistpage+1);
    }

    public void recentaddedCall(String q,int page){
        URl = q;
        recentaddedDramaPage = page;
        dramaClient.recentaddedCall(q,page);

    }
    public void NextrecentaddedCall(){
        recentaddedCall(URl,recentaddedDramaPage+1);
    }
    public void asianmovieCall(String q,int page){
        URl = q;
        asianmoviePage = page;
        dramaClient.asianmovieCall(q,page);

    }
    public void NextasianmovieCall(){
        asianmovieCall(URl,asianmoviePage+1);
    }
    public void comingdramaCall(String q,int page){
        URl = q;
        comingdramaPage = page;
        dramaClient.comingdramaCall(q,page);

    }

    public void ShowCall(String q,int page){
        URl = q;
        showspage = page;
        dramaClient.ShowCall(q,page);

    }

}
