package com.anass.jplus.API.Anime;

import static android.content.Context.MODE_PRIVATE;

import static com.anass.jplus.Config.config.SETTINGS_NAME;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anass.jplus.API.SitesManager;
import com.anasskikanime.models.AnimeModel;
import com.anasskikanime.sites.Addanime;
import com.anasskikanime.sites.Anime4up;
import com.anasskikanime.sites.Animerco;
import com.anasskikanime.sites.Animetitans;
import com.anasskikanime.sites.Arabsama;
import com.anasskikanime.sites.OctAnime;
import com.anasskikanime.sites.Okanime;
import com.anasskikanime.sites.WitAnime;
import com.anasskikanime.sites.XSanime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import anasskikanime.Anassget;
import anasskikanime.InterFaces.MyInterFace;

public class DramaClient {

    ExecutorService executorService;

    SitesManager sitesManager;


    private MutableLiveData<List<AnimeModel>> dramaModel,SearchModel,
            dramalist,recentaddedmodel,Showmodel,asianmoviemodel,comingdramamodel;

    private static DramaClient instance;
    Anassget AnassAnimeGetter;
    Anime4up anime4up;
    WitAnime witAnime;
    OctAnime octAnime;
    Animerco animerco;
    Okanime okanime;
    Animetitans animetitans;
    XSanime xSanime;
    Addanime addanime;
    Arabsama arabsama;
    public static DramaClient getInstance(){
        if (instance == null){
            instance = new DramaClient();
        }
        return instance;
    }
    public Activity activity;
    public void   GetContext(Activity activity){
        sitesManager = new SitesManager(activity.getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

    }



    private DramaClient() {
        executorService =  Executors.newSingleThreadExecutor( );
        dramaModel = new MutableLiveData<>();
        dramalist = new MutableLiveData<>();

        AnassAnimeGetter= new Anassget();

        recentaddedmodel = new MutableLiveData<>();
        asianmoviemodel = new MutableLiveData<>();
        comingdramamodel = new MutableLiveData<>();

        Showmodel = new MutableLiveData<>();
        SearchModel = new MutableLiveData<>();
    }


    public LiveData<List<AnimeModel>> GetDramas(){
        return dramaModel;
    }

    public LiveData<List<AnimeModel>> GetDrama(){
        return dramalist;
    }

    public LiveData<List<AnimeModel>> Getrecentaddedmodel(){
        return recentaddedmodel;
    }

    public LiveData<List<AnimeModel>> Getasianmoviemodel(){
        return asianmoviemodel;
    }

    public LiveData<List<AnimeModel>> Getcomingdramamodel(){
        return comingdramamodel;
    }

    public LiveData<List<AnimeModel>> Showmodel(){
        return Showmodel;
    }
    public LiveData<List<AnimeModel>> SearchModel(){

        return SearchModel;
    }








    public void SearchCall(String search, int pageNumber){
        List<AnimeModel> myanimes = new ArrayList<>(  );

        executorService.execute(new Runnable( ) {
            @Override
            public void run() {

                try {
                    AnassAnimeGetter.FetchXSanime().SearchForAnime(search,pageNumber, new MyInterFace.OnAnimeListGet( ) {
                        @Override
                        public void OnAnimeListGet(List<AnimeModel> list) {


                            try {
                                for (AnimeModel anime  :  list  ) {
                                    AnimeModel animeModel = new AnimeModel();
                                    animeModel.setTitle(anime.getTitle());
                                    Log.d("TAG", "OnAnimeListGet: "+anime.getTitle());
                                    animeModel.setPageLink(anime.getPageLink());
                                    animeModel.setImg(anime.getImg());
                                    animeModel.setType("xsanime");
                                    myanimes.add(animeModel);
                                }


                                SearchModel.postValue(myanimes);

                            } catch (Exception e) {
                                e.printStackTrace();
                                SearchModel.postValue(null);
                            }
                        }
                    });

                } catch (IOException e) {
                    SearchModel.postValue(null);

                }

            }
        });




    }

    public void RecentEpisodesCall(int pageNumber){

        List<AnimeModel> myanimes = new ArrayList<>(  );


        executorService.execute(new Runnable( ) {
            @Override
            public void run() {

                try {
                    switch ("xsanime"){

                        case "anime4up":

                            AnassAnimeGetter.FetchAnime4up().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {

                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setEpisodeLink(anime.getEpisodeLink());
                                            animeModel.setEpisodestitle(anime.getEpisodestitle());
                                            animeModel.setType("anime4up");
                                            myanimes.add(animeModel);
                                        }


                                        dramaModel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramaModel.postValue(null);
                                    }
                                }
                            });


                            break;
                        case "witanime":

                            AnassAnimeGetter.FetchWitAnime().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setEpisodeLink(anime.getEpisodeLink());
                                            animeModel.setEpisodestitle(anime.getEpisodestitle());
                                            animeModel.setType("witanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramaModel.postValue(myanimes);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramaModel.postValue(null);
                                    }
                                }
                            });
                            break;
                        case "octanime":
                            AnassAnimeGetter.FetchOctAnime().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("octanime");
                                            animeModel.setEpisodeLink(anime.getEpisodeLink());
                                            animeModel.setEpisodestitle(anime.getEpisodestitle());
                                            myanimes.add(animeModel);
                                        }


                                        dramaModel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramaModel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animerco":
                            AnassAnimeGetter.FetchAnimerco().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setEpisodeLink(anime.getEpisodeLink());
                                            animeModel.setEpisodestitle(anime.getEpisodestitle());
                                            animeModel.setType("witanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramaModel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramaModel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animelek":
                            AnassAnimeGetter.FetchAnimelek().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setEpisodeLink(anime.getEpisodeLink());
                                            animeModel.setEpisodestitle(anime.getEpisodestitle());
                                            animeModel.setType("animelek");
                                            myanimes.add(animeModel);
                                        }


                                        dramaModel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramaModel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "okanime":
                            AnassAnimeGetter.FetchOkanime().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setEpisodeLink(anime.getEpisodeLink());
                                            animeModel.setEpisodestitle(anime.getEpisodestitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("okanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramaModel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramaModel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "Animetitas":
                            AnassAnimeGetter.FetchAnimetitans().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setEpisodeLink(anime.getEpisodeLink());
                                            animeModel.setEpisodestitle(anime.getEpisodestitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("Animetitas");
                                            myanimes.add(animeModel);
                                        }


                                        dramaModel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramaModel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "xsanime":
                            AnassAnimeGetter.FetchXSanime().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setEpisodeLink(anime.getEpisodeLink());
                                            animeModel.setEpisodestitle(anime.getEpisodestitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("xsanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramaModel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramaModel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "addanime":
                            AnassAnimeGetter.FetchAddanime().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setEpisodeLink(anime.getEpisodeLink());
                                            animeModel.setEpisodestitle(anime.getEpisodestitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("addanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramaModel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramaModel.postValue(null);
                                    }
                                }
                            });

                            break;
                            case "arabsama":
                                AnassAnimeGetter.FetchArabsama().GetLastEpisodes(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                    @Override
                                    public void OnAnimeListGet(List<AnimeModel> list) {

                                        Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());

                                        try {
                                            for (AnimeModel anime  :  list  ) {
                                                AnimeModel animeModel = new AnimeModel();
                                                animeModel.setTitle(anime.getTitle());
                                                animeModel.setEpisodeLink(anime.getEpisodeLink());
                                                animeModel.setEpisodestitle(anime.getEpisodestitle());
                                                animeModel.setPageLink(anime.getPageLink());
                                                animeModel.setImg(anime.getImg());
                                                animeModel.setType("arabsama");
                                                myanimes.add(animeModel);
                                            }


                                            dramaModel.postValue(myanimes);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            dramaModel.postValue(null);
                                        }
                                    }
                                });

                                break;


                    }




                } catch (IOException e) {
                    dramaModel.postValue(null);

                }

            }
        });



    }



    public void DramalistCall( int pageNumber){
        List<AnimeModel> myanimes = new ArrayList<>(  );

        executorService.execute(new Runnable( ) {
            @Override
            public void run() {



                try {

                    switch ("xsanime"){

                        case "anime4up":

                            AnassAnimeGetter.FetchAnime4up().GetNextPage(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {

                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("anime4up");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);

                                    } catch (Exception e) {
                                        dramalist.postValue(null);
                                    }
                                }
                            });


                            break;
                        case "witanime":

                            AnassAnimeGetter.FetchWitAnime().GetNextPage(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {

                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("witanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramalist.postValue(null);
                                    }
                                }
                            });
                            break;
                        case "octanime":
                            AnassAnimeGetter.FetchOctAnime().GetNextPageAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {

                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("octanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramalist.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animerco":
                            AnassAnimeGetter.FetchAnimerco().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {

                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animerco");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramalist.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animelek":
                            AnassAnimeGetter.FetchAnimelek().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animelek");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramalist.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "okanime":
                            AnassAnimeGetter.FetchOkanime().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("okanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramalist.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "Animetitas":
                            AnassAnimeGetter.FetchAnimetitans().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("Animetitas");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramalist.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "xsanime":
                            AnassAnimeGetter.FetchXSanime().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("xsanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramalist.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "addanime":
                            AnassAnimeGetter.FetchAddanime().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("addanime");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramalist.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "arabsama":
                            AnassAnimeGetter.FetchArabsama().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("arabsama");
                                            myanimes.add(animeModel);
                                        }


                                        dramalist.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dramalist.postValue(null);
                                    }
                                }
                            });

                            break;


                    }



                } catch (IOException e) {
                    dramalist.postValue(null);

                }

            }
        });


    }

    public void  recentaddedCall(String url, int pageNumber){

        List<AnimeModel> myanimes = new ArrayList<>(  );


        executorService.execute(new Runnable( ) {
            @Override
            public void run() {

                try {




                    switch (sitesManager.getSite().getGenertitle()){

                        case "anime4up":

                            AnassAnimeGetter.FetchAnime4up().GetAnimeAddedRecently( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("anime4up");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });


                            break;
                        case "witanime":

                            AnassAnimeGetter.FetchWitAnime().GetAnimeAddedRecently( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("witanime");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });
                            break;
                        case "octanime":
                            AnassAnimeGetter.FetchOctAnime().GetNextPageAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("octanime");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animerco":
                            AnassAnimeGetter.FetchAnimerco().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animerco");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animelek":
                            AnassAnimeGetter.FetchAnimelek().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animelek");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "okanime":
                            AnassAnimeGetter.FetchOkanime().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("okanime");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "Animetitas":
                            AnassAnimeGetter.FetchAnimetitans().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("Animetitas");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "xsanime":
                            AnassAnimeGetter.FetchXSanime().GetAnimeTo3radalan( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("xsanime");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "addanime":
                            AnassAnimeGetter.FetchAddanime().GetAnimeTo3radalan( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("addanime");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "arabsama":
                            AnassAnimeGetter.FetchArabsama().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("arabsama");
                                            myanimes.add(animeModel);
                                        }


                                        recentaddedmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        recentaddedmodel.postValue(null);
                                    }
                                }
                            });

                            break;


                    }



                } catch (IOException e) {
                    recentaddedmodel.postValue(null);

                }

            }
        });



    }
    public void  asianmovieCall(String url, int pageNumber){

        List<AnimeModel> myanimes = new ArrayList<>(  );


        executorService.execute(new Runnable( ) {
            @Override
            public void run() {

                try {

                    switch ("xsanime"){

                        case "anime4up":

                            AnassAnimeGetter.FetchAnime4up().GetNextPageMovies(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("anime4up");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });


                            break;
                        case "witanime":

                            AnassAnimeGetter.FetchWitAnime().GetMovieList( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("anime4up");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });
                            break;
                        case "octanime":
                            AnassAnimeGetter.FetchOctAnime().GetMovieList( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("octanime");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animerco":
                            AnassAnimeGetter.FetchAnimerco().GetMovieList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {

                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animerco");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animelek":
                            AnassAnimeGetter.FetchAnimelek().GetMovieList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animelek");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "okanime":
                            AnassAnimeGetter.FetchOkanime().GetMovieList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("okanime");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "Animetitas":
                            AnassAnimeGetter.FetchAnimetitans().GetMovieList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("Animetitas");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "xsanime":
                            AnassAnimeGetter.FetchXSanime().GetMovieList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("xsanime");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "addanime":
                            AnassAnimeGetter.FetchAddanime().GetMovieList( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("addanime");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "arabsama":
                            AnassAnimeGetter.FetchArabsama().GetMovieList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("arabsama");
                                            myanimes.add(animeModel);
                                        }


                                        asianmoviemodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        asianmoviemodel.postValue(null);
                                    }
                                }
                            });

                            break;


                    }



                } catch (IOException e) {
                    asianmoviemodel.postValue(null);



                }

            }


        });


    }
    public void  comingdramaCall(String url, int pageNumber){
        List<AnimeModel> myanimes = new ArrayList<>(  );

        executorService.execute(new Runnable( ) {
            @Override
            public void run() {

                try {
                    switch (sitesManager.getSite().getGenertitle()){

                        case "anime4up":

                            AnassAnimeGetter.FetchAnime4up().GetAnimemoktamila( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("anime4up");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });


                            break;
                        case "witanime":

                            AnassAnimeGetter.FetchWitAnime().GetAnimemoktamila( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("witanime");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });
                            break;
                        case "octanime":
                            AnassAnimeGetter.FetchOctAnime().GetNextPageAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("octanime");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animerco":
                            AnassAnimeGetter.FetchAnimerco().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {
                                    Log.d("TAG", "OnAnimeListGet: " +list.get(0).getTitle());


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animerco");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animelek":
                            AnassAnimeGetter.FetchAnimelek().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animelek");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "okanime":
                            AnassAnimeGetter.FetchOkanime().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("okanime");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "Animetitas":
                            AnassAnimeGetter.FetchAnimetitans().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("Animetitas");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "xsanime":
                            AnassAnimeGetter.FetchXSanime().GetAnimemoktamila( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("xsanime");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "addanime":
                            AnassAnimeGetter.FetchAddanime().GetAnimemoktamila( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("addanime");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "arabsama":
                            AnassAnimeGetter.FetchArabsama().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("arabsama");
                                            myanimes.add(animeModel);
                                        }


                                        comingdramamodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        comingdramamodel.postValue(null);
                                    }
                                }
                            });

                            break;


                    }


                } catch (IOException e) {
                    comingdramamodel.postValue(null);

                }

            }


        });




    }

    public void  ShowCall(String url, int pageNumber){
        List<AnimeModel> myanimes = new ArrayList<>(  );

        executorService.execute(new Runnable( ) {
            @Override
            public void run() {

                try {



                    switch (sitesManager.getSite().getGenertitle()){

                        case "anime4up":

                            AnassAnimeGetter.FetchAnime4up().GetAnimeAddedRecently( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("anime4up");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });


                            break;
                        case "witanime":

                            AnassAnimeGetter.FetchWitAnime().GetAnimeAddedRecently( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("witanime");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });
                            break;
                        case "octanime":
                            AnassAnimeGetter.FetchOctAnime().GetNextPageAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("octanime");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animerco":
                            AnassAnimeGetter.FetchAnimerco().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animerco");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "animelek":
                            AnassAnimeGetter.FetchAnimelek().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {


                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("animelek");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "okanime":
                            AnassAnimeGetter.FetchOkanime().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("okanime");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "Animetitas":
                            AnassAnimeGetter.FetchAnimetitans().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("Animetitas");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "xsanime":
                            AnassAnimeGetter.FetchXSanime().GetAnimeTo3radalan( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("xsanime");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "addanime":
                            AnassAnimeGetter.FetchAddanime().GetAnimeTo3radalan( new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("addanime");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });

                            break;
                        case "arabsama":
                            AnassAnimeGetter.FetchArabsama().GetAnimeList(pageNumber, new MyInterFace.OnAnimeListGet( ) {
                                @Override
                                public void OnAnimeListGet(List<AnimeModel> list) {



                                    try {
                                        for (AnimeModel anime  :  list  ) {
                                            AnimeModel animeModel = new AnimeModel();
                                            animeModel.setTitle(anime.getTitle());
                                            animeModel.setPageLink(anime.getPageLink());
                                            animeModel.setImg(anime.getImg());
                                            animeModel.setType("arabsama");
                                            myanimes.add(animeModel);
                                        }


                                        Showmodel.postValue(myanimes);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Showmodel.postValue(null);
                                    }
                                }
                            });

                            break;


                    }


                } catch (IOException e) {
                    Showmodel.postValue(null);
                }

            }
        });



    }





}
