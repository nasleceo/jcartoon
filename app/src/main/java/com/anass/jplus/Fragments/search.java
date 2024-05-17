package com.anass.jplus.Fragments;

import static com.anass.jplus.Constant.AppConfig.adType;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.anass.jplus.API.ModelView.MovieListViewModel;
import com.anass.jplus.API.MovieApi;
import com.anass.jplus.API.Servicy;
import com.anass.jplus.Activities.ShowInfoActivity;
import com.anass.jplus.Adapters.MovieAdapter;
import com.anass.jplus.Adapters.SeriesAdapter;
import com.anass.jplus.Config.Utils;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.viewmodel.getAlltvsViewModel;
import com.bumptech.glide.Glide;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.textfield.TextInputEditText;

import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class search extends Fragment {

    SeriesAdapter moviesAdapter;

    TextInputEditText searchview;
    TextView noresult;
    RecyclerView recentepisod_rv,cast_rv;

    List<CartoonModel> moviemodel = new ArrayList<>(  );

    getAlltvsViewModel movieListViewModel;

    int curentpage = 1;
    int totalpage = 1;

    RadioGroup grouiprado;
    public String searchqe = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_search, container, false);

        movieListViewModel = new ViewModelProvider(getActivity()).get(getAlltvsViewModel.class);

        initialviews(v);
        grouiprado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId){
                    case R.id.tv_select:
                        observAnyChanges(searchqe,"tv");

                        break;
                    case R.id.movie_select:
                        observAnyChanges(searchqe,"movie");

                        break;
                    case R.id.comic_select:
                        observAnyChanges(searchqe,"comic");

                        break;


                }
            }
        });

        searchview.setOnEditorActionListener(new TextView.OnEditorActionListener( ) {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    noresult.setVisibility(View.VISIBLE);
                    noresult.setText("بدأ البحث.....");
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        recentepisod_rv.setLayoutManager(new GridLayoutManager(getActivity(), 3,LinearLayoutManager.VERTICAL,false));
        observAnyChanges(searchqe,"tv");




        return  v;
    }



    private void performSearch() {

        searchview.clearFocus();
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchview.getWindowToken(), 0);

        searchqe = Objects.requireNonNull(searchview.getText( )).toString();

        observAnyChanges(searchqe,"tv");

    }



    /*private void GetSearch(String s) {

        Call<MovieSearchRespons> responsCall = movieApi
                .searchmo(
                        s
                );
        Log.d("TAG", "GetSearch: "+responsCall.toString());

        responsCall.enqueue(new Callback<MovieSearchRespons>() {
            @Override
            public void onResponse(Call<MovieSearchRespons> call, Response<MovieSearchRespons> response) {
                if (response.code()== 200){

                    List<SerieModel> serieModels = new ArrayList<>(response.body().getMovies());
                    Log.d("TAG", "GetSearch: "+serieModels.toString());


                    moviemodel = new ArrayList<>(  );
                    moviesAdapter = new SeriesAdapter(getActivity(),serieModels,1);

                    recentepisod_rv.setAdapter(moviesAdapter);

                }
            }

            @Override
            public void onFailure(Call<MovieSearchRespons> call, Throwable t) {


            }
        });


    }*/





    private void observAnyChanges(String search,String gener) {
        Log.d("TAG", "observAnyChanges: "+searchqe);

        movieListViewModel.Search(search).observe(getActivity( ), new Observer<CartoonResponse>( ) {
            @Override
            public void onChanged(CartoonResponse cartoonResponse) {
                if (cartoonResponse != null){
                    if (cartoonResponse.getTvs() != null){
                        noresult.setVisibility(View.GONE);
                        moviemodel.clear();
                        for (CartoonModel car:cartoonResponse.getTvs()) {
                            if (Objects.equals(car.getCountentType( ), gener)){
                                moviemodel.add(car);
                            }

                        }

                        moviesAdapter = new SeriesAdapter(getActivity(), moviemodel,1);
                        moviesAdapter.setLimit(moviemodel.size());
                        recentepisod_rv.setAdapter(moviesAdapter);


                    }

                }
            }
        });




    }

    private void initialviews(View v) {


        recentepisod_rv = v.findViewById(R.id.recentepisod_rv);
        searchview = v.findViewById(R.id.searchinput);
        noresult = v.findViewById(R.id.noresult);
        grouiprado = v.findViewById(R.id.grouiprado);
        cast_rv = v.findViewById(R.id.cast_rv);


        loadAd(v);

    }

    private void loadAd(View view) {
        Context context = getContext();
        RelativeLayout adViewLayout = view.findViewById(R.id.reads);


            Banner startAppBanner = new Banner(context);
            RelativeLayout.LayoutParams bannerParameters =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
            // Add to main Layout
            adViewLayout.addView(startAppBanner, bannerParameters);

    }



}