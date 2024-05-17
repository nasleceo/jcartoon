package com.anass.jplus.Fragments;

import static com.anass.jplus.Constant.AppConfig.adType;
import static com.anass.jplus.MainActivity.clicksound;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.anass.jplus.API.Anime.AnimeGridAdapter;
import com.anass.jplus.API.Anime.DramaViewModel;
import com.anass.jplus.Adapters.AnimeAdapter;
import com.anass.jplus.Adapters.SeriesAdapter;
import com.anass.jplus.Config.Utils;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.R;
import com.anasskikanime.models.AnimeModel;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.textfield.TextInputEditText;

import com.startapp.sdk.ads.banner.Banner;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import java.util.ArrayList;
import java.util.List;


public class Animes extends Fragment {

    private DramaViewModel recentEpisodeListViewModel;
    RecyclerView lastepe_rv,anime_rv,movie_rv;
    RelativeLayout adViewLayout;
    TextInputEditText searchinput ;

    LinearLayout recentepelist,animelist,movielist;
    GridView result_rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_animes, container, false);
        initivia(view);

        loadBanner();


        recentEpisodeListViewModel.getRecentEpisode().observe(getActivity( ), new Observer<List<AnimeModel>>( ) {
            @Override
            public void onChanged(List<AnimeModel> animeModels) {
                if (animeModels != null){

                    AnimeAdapter showAdater = new AnimeAdapter(getActivity());
                    showAdater.setmodelsAnimes(animeModels);
                    showAdater.Getlimit(1);
                    lastepe_rv.setAdapter(showAdater);



                }
            }
        });
        recentEpisodeListViewModel.getDRama().observe(getActivity( ), new Observer<List<AnimeModel>>( ) {
            @Override
            public void onChanged(List<AnimeModel> animeModels) {
                if (animeModels != null){

                    AnimeAdapter showAdater = new AnimeAdapter(getActivity());
                    showAdater.setmodelsAnimes(animeModels);
                    showAdater.Getlimit(1);
                    anime_rv.setAdapter(showAdater);



                }
            }
        });
         recentEpisodeListViewModel.Getasianmoviemodel().observe(getActivity( ), new Observer<List<AnimeModel>>( ) {
            @Override
            public void onChanged(List<AnimeModel> animeModels) {
                if (animeModels != null){

                    AnimeAdapter showAdater = new AnimeAdapter(getActivity());
                    showAdater.setmodelsAnimes(animeModels);
                    showAdater.Getlimit(1);
                    movie_rv.setAdapter(showAdater);



                }
            }
        });
         recentEpisodeListViewModel.SearchModel().observe(getActivity( ), new Observer<List<AnimeModel>>( ) {
            @Override
            public void onChanged(List<AnimeModel> animeModels) {
                if (animeModels != null){

                    AnimeGridAdapter animeGridAdapter = new AnimeGridAdapter(getContext());
                    animeGridAdapter.setmodelsAnimes(animeModels);
                    result_rv.setAdapter(animeGridAdapter);




                }
            }
        });


        searchinput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                recentEpisodeListViewModel.SearchGo(s.toString(),1);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                result_rv.setVisibility(View.VISIBLE);
                movielist.setVisibility(View.GONE);
                animelist.setVisibility(View.GONE);
                recentepelist.setVisibility(View.GONE);

                String text = s.toString();

                if (text.equals("")) {
                    result_rv.setVisibility(View.GONE);
                    movielist.setVisibility(View.VISIBLE);
                    animelist.setVisibility(View.VISIBLE);
                    recentepelist.setVisibility(View.VISIBLE);
                }




            }
        });



        return view;
    }

    private void initivia(View view) {

        lastepe_rv = view.findViewById(R.id.lastepe_rv);
        anime_rv = view.findViewById(R.id.anime_rv);
        movie_rv = view.findViewById(R.id.movie_rv);
        searchinput = view.findViewById(R.id.searchinput);
        movielist = view.findViewById(R.id.movielist);
        recentepelist = view.findViewById(R.id.recentepelist);
        animelist = view.findViewById(R.id.animelist);
        result_rv = view.findViewById(R.id.result_rv);


        adViewLayout = view.findViewById(R.id.adViewLayout);


        anime_rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        lastepe_rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        movie_rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        recentEpisodeListViewModel =  new ViewModelProvider(this).get(DramaViewModel.class);
        Utils.FixColumnsWidth(result_rv, 170, getContext());

        recentEpisodeListViewModel.RecentEpisodeGo(1);
        recentEpisodeListViewModel.DramaGo(1);
        recentEpisodeListViewModel.asianmovieCall("",1);
    }


    private void loadBanner() {

        Context context = getContext();

        if(adType == 1) {   //AdMob
            MobileAds.initialize(getContext(), initializationStatus -> {
                // Do nothing
            });
            //Banner ad
            AdView mAdView = new AdView(context);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(AppConfig.adMobBanner);
            adViewLayout.addView(mAdView);
            AdRequest bannerAdRequest = new AdRequest.Builder().build();
            mAdView.loadAd(bannerAdRequest);
        } else if(adType == 2) { //StartApp
            // and show interstitial ad

            // Define StartApp Banner
            Banner startAppBanner = new Banner(context);
            RelativeLayout.LayoutParams bannerParameters =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
            bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            // Add to main Layout
            adViewLayout.addView(startAppBanner, bannerParameters);
        } else if(adType == 3) { //Facebook

            AudienceNetworkAds.initialize(context);

            com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(getContext(), AppConfig.facebook_interstitial_ads_placement_id);
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {

                @Override
                public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {

                }

                @Override
                public void onAdLoaded(com.facebook.ads.Ad ad) {
                    interstitialAd.show();
                }

                @Override
                public void onAdClicked(com.facebook.ads.Ad ad) {

                }

                @Override
                public void onLoggingImpression(com.facebook.ads.Ad ad) {

                }

                @Override
                public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(com.facebook.ads.Ad ad) {

                }
            };
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());


        } else if(adType == 5) { //unityads


            BannerView bannerView = new BannerView(getActivity(), AppConfig.Unity_Banner_ID, new UnityBannerSize(320, 50));
            bannerView.load();
            adViewLayout.addView(bannerView);
        } else if(adType == 6) { //Custom Ads
            adViewLayout.setVisibility(View.GONE);


        } else if(adType == 7) { // AppLovin Ads
           /* AppLovinSdk.getInstance( context ).setMediationProvider( "max" );
            AppLovinSdk.getInstance(context).getSettings().setTestDeviceAdvertisingIds(Arrays.asList("b4d923d0-7f39-41a4-a3cb-0f16ce8e3058"));
            AppLovinSdk.initializeSdk( context, configuration -> {
                MaxAdView adView = new MaxAdView( AppConfig.applovin_Banner_ID, this );
                adViewLayout.addView(adView);
                adView.loadAd();

                MaxInterstitialAd interstitialAd = new MaxInterstitialAd( AppConfig.applovin_Interstitial_ID, this );
                interstitialAd.loadAd();
                interstitialAd.setListener(new MaxAdListener() {
                    int retryAttempt = 0;
                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        retryAttempt = 0;
                        interstitialAd.showAd();
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        retryAttempt++;
                        long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

                        new Handler().postDelayed( new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                interstitialAd.loadAd();
                            }
                        }, delayMillis );
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                        interstitialAd.loadAd();
                    }
                });
            });*/
        }  else {
            adViewLayout.setVisibility(View.GONE);
        }


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.GoBACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}