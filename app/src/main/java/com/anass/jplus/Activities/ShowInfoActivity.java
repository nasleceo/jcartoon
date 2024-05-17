package com.anass.jplus.Activities;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import static com.anass.jplus.API.BEANLINKS.BASELINK;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;
import static com.anass.jplus.Constant.AppConfig.adType;
import static com.anass.jplus.SplashScreen.headers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.anass.jplus.API.MovieApi;
import com.anass.jplus.API.Respons.TMDB_API;
import com.anass.jplus.API.Servicy;
import com.anass.jplus.API.TMDBAPI;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Adapters.AddToFavAdapter;
import com.anass.jplus.Adapters.CastAdapter;
import com.anass.jplus.Adapters.NewsAdapter;
import com.anass.jplus.Adapters.PostAdapter;
import com.anass.jplus.Adapters.SeriesAdapter;
import com.anass.jplus.Config.Utils;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Models.CastModel;
import com.anass.jplus.Models.NewsModel;
import com.anass.jplus.Models.PostModel;
import com.anass.jplus.Models.SeasonModel;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.ServerModel;
import com.anass.jplus.Models.addtofavModel;
import com.anass.jplus.R;

import com.anass.jplus.backend.Responses.OneCartoonResponse;
import com.anass.jplus.backend.viewmodel.getAlltvsViewModel;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


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
import com.xw.repo.BubbleSeekBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class ShowInfoActivity extends AppCompatActivity {



    RelativeLayout adViewLayout;

    Boolean ilikeit = false;
    ImageView navigationBar;
    ImageView gooback,likeicon,imgviews;
    TextView titleser,yeartxt,viewstxt,storytxt,genertxt,seasonscouct,reatetxt,agetxt,seasonpint;
    RelativeLayout watchepenoew,addtofav,gotocomments;
    LinearLayout loadscreen,addratebtn,pin_rlt,review_rlt,tawsiat_rlt,news_rlt;

    MovieApi movieApi;
    TMDB_API tmdb_api;
    RecyclerView pin_rv,cast_rv,review_rv,tawsiat_rv,news_rv;
    ProgressBar showscreeprog;
    List<CartoonModel> cartoonModelList;

    getAlltvsViewModel movieListViewModel;

    static  public InterstitialAd minterstitia;

    static public StartAppAd startAppAd;

    AuthManager authManager;
    CartoonModel cartoon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_show_info);

        initial();


        TextView showepeorcomc_txt = findViewById(R.id.showepeorcomc_txt);
        Glide.with(ShowInfoActivity.this).load(cartoon.getPoster()).into(imgviews);
        titleser.setText(cartoon.getTitle());

        if (cartoon.getCountentType().equals("comic")){
            showepeorcomc_txt.setText("الأعداد");
        }
        if (cartoon.getYear() == null){
            yeartxt.setText("");
        }else{
            yeartxt.setText(""+cartoon.getYear());

        }


        if (cartoon.getAge() == null){
            agetxt.setText("");
        }else {
            agetxt.setText(""+cartoon.getAge());

        }

        storytxt.setText(cartoon.getStory());
        genertxt.setText(cartoon.getGener());

            loadAd();


        movieListViewModel.oneTvorCartoonorComic(cartoon.getId()).observe(ShowInfoActivity.this, new Observer<OneCartoonResponse>( ) {
            @Override
            public void onChanged(OneCartoonResponse cartoonModels) {

                if (cartoonModels != null) {

                    if (cartoonModels.getTv( ) != null) {
                        loadscreen.setVisibility(View.VISIBLE);
                        showscreeprog.setVisibility(View.GONE);

                        CartoonModel cartoon = cartoonModels.getTv( );
                        String views = format(cartoonModels.getVisit_count());
                        viewstxt.setText(views);
                        reatetxt.setText(""+cartoonModels.getRate()+"/10");
                        if (cartoon.getCountentType().equals("tv")){
                            seasonscouct.setText(" عدد المواسم "+cartoonModels.getSeasoncount());
                            seasonpint.setVisibility(View.VISIBLE);

                        }else {
                            seasonscouct.setText("");
                            seasonpint.setVisibility(View.GONE);

                        }

                        GetCast(cartoonModels.getCast());
                        GetMoreLikeThis(cartoonModels.getIktirahat());
                       // GetReview(cartoonModels.getReview());
                        //GetTawsiat(cartoonModels.getTawsia());
                        Getnews(cartoonModels.getNews());
                        AddToFavBTN(ShowInfoActivity.this.cartoon);

                    }

                }
            }
        });

        addratebtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Rate();
            }
        });




        }

    private void AddToFavBTN(CartoonModel cartoonModel) {
        //CheckFav(cartoonModel.getId());

        addtofav.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (authManager.getUserInfo().getId() == 0) {

                    Toast.makeText(ShowInfoActivity.this, "يجب تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();

                }else {
                    AddToFavor_List(cartoonModel);

                }




            }
        });


    }
    private void AddToFavor_List(CartoonModel id) {

        final Dialog dialog = new Dialog(ShowInfoActivity.this, R.style.MyAlertDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.addtofavouritlayout);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;

        RecyclerView listView = dialog.findViewById(R.id.listfavourtadd);
        LinearLayout addtofav_btn = dialog.findViewById(R.id.addtofav_btn);
        String[] favou_list = new String[]{"المفضلة", "تم مشاهدتها","أرغب بمشاهدتها","أشاهدها حاليا","أكملها مستقبلا"};
        String[] favou_lis_backeend = new String[]{"favcartoon", "watchdone","iwantwatch","watchnow","watchlater"};

        List<addtofavModel> final_res = new ArrayList<>(  );
        for (String ss:  favou_list) {
            addtofavModel  addtofavModel =new addtofavModel(  );
            addtofavModel.setName(ss);
            final_res.add(addtofavModel);
        }


        listView.setLayoutManager(new LinearLayoutManager(ShowInfoActivity.this,LinearLayoutManager.VERTICAL,false));
        AddToFavAdapter mAdapter = new AddToFavAdapter(final_res);
        listView.setAdapter(mAdapter);


        addtofav_btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                List<addtofavModel> stList = ((AddToFavAdapter) mAdapter)
                        .getselected();

                List<String> selected = new ArrayList<>(  );

                for (int i = 0; i < stList.size(); i++) {
                    addtofavModel singleStudent = stList.get(i);

                    if (singleStudent.isSelected( )) {
                        selected.add(favou_lis_backeend[i]);

                    }

                }

                if (selected.size() > 0){
                    for (String type:selected ) {
                        AddTofav(id.getId(),type);
                    }
                    dialog.dismiss();
                }else {
                    Toast.makeText(ShowInfoActivity.this, "إختر مكان المفضلة", Toast.LENGTH_SHORT).show( );
                }




            }
        });







        dialog.show();
        dialog.getWindow().setAttributes(lp);



    }
   /* private void CheckFav(int id) {

        AndroidNetworking.get(BASELINK+"cartoon/checkFavorite")
                .addQueryParameter("user_id", "1")
                .addQueryParameter("tv_id", String.valueOf(id))
                .addHeaders(headers())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String msj = response.getString("message");
                            switch (msj){

                                case "exist":
                                    likeicon.setImageDrawable(getResources().getDrawable(R.drawable.check));
                                    break;

                                case "no_exist":
                                    likeicon.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
                                    break;

                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ShowInfoActivity.this, R.string.favouriot_error, Toast.LENGTH_SHORT).show();

                    }
                });

    }*/

    private void AddTofav(int id,String type) {

        AndroidNetworking.get(BASELINK+"cartoon/addFavorite")
                .addQueryParameter("user_id", String.valueOf(authManager.getUserInfo().getId()))
                .addQueryParameter("type", type)
                .addQueryParameter("tv_id", String.valueOf(id))
                .addHeaders(headers())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String msj = response.getString("message");
                            switch (msj){

                                case "added":
                                   // likeicon.setImageDrawable(getResources().getDrawable(R.drawable.check));
                                    Toast.makeText(ShowInfoActivity.this, R.string.favouritadded_sent, Toast.LENGTH_SHORT).show();
                                    break;

                                case "exist":
                                   // likeicon.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
                                    Toast.makeText(ShowInfoActivity.this, R.string.favouritremov_sent, Toast.LENGTH_SHORT).show();

                                    break;

                                case "error":
                                    Toast.makeText(ShowInfoActivity.this, R.string.favouriot_error, Toast.LENGTH_SHORT).show();

                                    break;
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ShowInfoActivity.this, R.string.favouriot_error, Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void Getnews(List<NewsModel> newsModels) {


        if (newsModels.size() > 0 ){
            news_rlt.setVisibility(View.VISIBLE);
            news_rv.setLayoutManager(new LinearLayoutManager(ShowInfoActivity.this,LinearLayoutManager.VERTICAL,false));

            news_rv.setAdapter(new NewsAdapter(ShowInfoActivity.this,newsModels));
        }



    }

    private void GetTawsiat(List<PostModel> postkey) {

        if (postkey.size() > 0 ){
            tawsiat_rlt.setVisibility(View.VISIBLE);
            tawsiat_rv.setLayoutManager(new LinearLayoutManager(ShowInfoActivity.this,LinearLayoutManager.HORIZONTAL,false));

            tawsiat_rv.setAdapter(new PostAdapter(ShowInfoActivity.this,postkey));


        }


    }

    private void GetCast(List<CastModel> id) {

        LinearLayoutManager ll = new LinearLayoutManager(ShowInfoActivity.this);
        ll.setOrientation(RecyclerView.HORIZONTAL);
        cast_rv.setLayoutManager(ll);

        cast_rv.setAdapter(new CastAdapter(ShowInfoActivity.this,id));


    }

    private void GetReview(List<PostModel> postkey) {

        if (postkey.size() > 0){
            review_rlt.setVisibility(View.VISIBLE);
            review_rv.setLayoutManager(new LinearLayoutManager(ShowInfoActivity.this,LinearLayoutManager.HORIZONTAL,false));

            review_rv.setAdapter(new PostAdapter(ShowInfoActivity.this,postkey));

        }



    }

    int progressValue = 1;
    private void Rate() {

        Dialog mBuilder = new Dialog(ShowInfoActivity.this);
//                View mView = getLayoutInflater().inflate(R.layout.dialog_addrat, null);

        mBuilder.setContentView(R.layout.dialog_addrat);
        mBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        BubbleSeekBar bubbleSeekBar = mBuilder.findViewById(R.id.bubbleSeekBar);
        TextView ratbarcount = mBuilder.findViewById(R.id.ratbarcount);
        LinearLayout savemyrat = mBuilder.findViewById(R.id.loginbtn);

        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                progressValue = progress;
                ratbarcount.setText(progress+"");


            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });


        savemyrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (progressValue == 0){


                    Toast.makeText(ShowInfoActivity.this, "يجب أن تختار من 1 إلي 10", Toast.LENGTH_SHORT).show();

                }else {


                    if (authManager.getUserInfo().getId() > 0){

                        reatetxt.setText(progressValue+"/10");



                        AndroidNetworking.get(BASELINK+"cartoon/addrate")
                                .addQueryParameter("user_id", String.valueOf(authManager.getUserInfo().getId()))
                                .addQueryParameter("jcartoonrate", String.valueOf(progressValue))
                                .addQueryParameter("tv_id", String.valueOf(cartoon.getId()))
                                .addHeaders(headers())
                                .setPriority(Priority.LOW)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener( ) {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Log.d("ret err", "onResponse: "+response.toString());
                                        Toast.makeText(ShowInfoActivity.this, R.string.review_sent, Toast.LENGTH_SHORT).show();
                                        mBuilder.dismiss();
                                    }

                                    @Override
                                    public void onError(ANError anError) {

                                        Toast.makeText(ShowInfoActivity.this, R.string.review_error, Toast.LENGTH_SHORT).show();

                                    }
                                });




                    }else {

                        Toast.makeText(ShowInfoActivity.this, "سجل الدخول حتي نحفظ تقيمك", Toast.LENGTH_LONG).show();

                        reatetxt.setText(progressValue+"/10");

                        mBuilder.dismiss();
                    }


                }


            }
        });


        mBuilder.show();



    }

    private void onSentReview(CartoonModel serieDetail) {


        if (authManager.getUserInfo().getId() == 0) {

            Toast.makeText(this, "يجب تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();
            return;
        }


        final Dialog dialog = new Dialog(ShowInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_review);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        lp.gravity = Gravity.BOTTOM;
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;


        TextView reviewMovieName = dialog.findViewById(R.id.movietitle);

        TextView userRating = dialog.findViewById(R.id.userRating);


        BubbleSeekBar storySeekbar = dialog.findViewById(R.id.storySeekbar);

        BubbleSeekBar caractersSeekbar = dialog.findViewById(R.id.caractersSeekbar);

        BubbleSeekBar mottionsdesginSeekbar = dialog.findViewById(R.id.mottionsdesginSeekbar);

        BubbleSeekBar musicSeekbar = dialog.findViewById(R.id.musicSeekbar);

        reviewMovieName.setText(serieDetail.getTitle());


        storySeekbar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                userRating.setText(Utils.getAvg(storySeekbar.getProgressFloat(),
                        caractersSeekbar.getProgressFloat()
                        ,mottionsdesginSeekbar.getProgressFloat()
                        ,musicSeekbar.getProgressFloat(),false)+getString(R.string.byteen));

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

                //

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                //
            }
        });


        caractersSeekbar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                userRating.setText(Utils.getAvg(storySeekbar.getProgressFloat(),
                        caractersSeekbar.getProgressFloat()
                        ,mottionsdesginSeekbar.getProgressFloat()
                        ,musicSeekbar.getProgressFloat(),false)+"/10");

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                //
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                //
            }
        });


        mottionsdesginSeekbar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                userRating.setText(Utils.getAvg(storySeekbar.getProgressFloat(),
                        caractersSeekbar.getProgressFloat()
                        ,mottionsdesginSeekbar.getProgressFloat()
                        ,musicSeekbar.getProgressFloat(),false)+"/10");

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                //
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                //
            }
        });


        musicSeekbar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                userRating.setText(Utils.getAvg(storySeekbar.getProgressFloat(),
                        caractersSeekbar.getProgressFloat()
                        ,mottionsdesginSeekbar.getProgressFloat()
                        ,musicSeekbar.getProgressFloat(),false)+"/10");

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                //
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                //
            }
        });


        Button sendReport = dialog.findViewById(R.id.view_report);

        dialog.findViewById(R.id.bt_close).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.bt_close).setOnClickListener(y -> {

            dialog.dismiss();
        });
        sendReport.setOnClickListener(v -> {


            /// الكرتون تم تقيمه وتم اضافة تقيم عليه
            if (serieDetail.getVoteavrage() !=0){

                double newUserRating = Math.round(serieDetail.getVoteavrage() + Utils.getAvg(storySeekbar.getProgressFloat(),
                        caractersSeekbar.getProgressFloat()
                        ,mottionsdesginSeekbar.getProgressFloat()
                        ,musicSeekbar.getProgressFloat(),false)) /2;


                AndroidNetworking.get(BASELINK+"cartoon/addrate")
                        .addQueryParameter("user_id", String.valueOf(authManager.getUserInfo().getId()))
                        .addQueryParameter("jcartoonrate", String.valueOf(newUserRating))
                        .addQueryParameter("tv_id", String.valueOf(serieDetail.getId()))
                        .addHeaders(headers())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener( ) {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("ret err", "onResponse: "+response.toString());
                                Toast.makeText(ShowInfoActivity.this, R.string.review_sent, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(ANError anError) {

                                Toast.makeText(ShowInfoActivity.this, R.string.review_error, Toast.LENGTH_SHORT).show();

                            }
                        });




                /// أول تقيم
            }  else {

                double newUserRating = Math.round(Utils.getAvg(storySeekbar.getProgressFloat(),
                        caractersSeekbar.getProgressFloat()
                        ,mottionsdesginSeekbar.getProgressFloat()
                        ,musicSeekbar.getProgressFloat(),false)) /2;

                AndroidNetworking.get(BASELINK+"cartoon/addrate")
                        .addQueryParameter("user_id",String.valueOf(authManager.getUserInfo().getId()))
                        .addQueryParameter("jcartoonrate", String.valueOf(newUserRating))
                        .addQueryParameter("tv_id", String.valueOf(serieDetail.getId()))
                        .addHeaders(headers())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener( ) {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("ret err", "onResponse: "+response.toString());

                                Toast.makeText(ShowInfoActivity.this, R.string.review_sent, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(ShowInfoActivity.this, R.string.review_error, Toast.LENGTH_SHORT).show();

                            }
                        });


            }




        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);




    }


    private void GetMoreLikeThis(List<CartoonModel> cartoonModels) {

        if (cartoonModels.size() > 0){
            pin_rlt.setVisibility(View.VISIBLE);
            LinearLayoutManager ll = new LinearLayoutManager(ShowInfoActivity.this);
            ll.setOrientation(RecyclerView.HORIZONTAL);
            pin_rv.setLayoutManager(ll);

            pin_rv.setAdapter(new SeriesAdapter(ShowInfoActivity.this, cartoonModels,0));
        }



    }


    private void GetFierstEpisode(String link) {
        List<ServerModel> firestEpe = new ArrayList<>();
        List<SeasonModel> Seasons = new ArrayList<>();



        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("User-Agent","Thunder Client (https://www.thunderclient.com)");


     /*   if (!place.contains("tv")){
            watchepenoew.setEnabled(false);

            AndroidNetworking.get(BEANLINKS.BASELINK+"movie/{id}")
                    .addPathParameter("id", link)
                    .addHeaders(headers)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsObject(CartoonModel.class , new ParsedRequestListener<CartoonModel>(){

                        @Override
                        public void onResponse(CartoonModel movie) {

                            String formatted = format(movie.getViews());
                            viewstxt.setText(formatted);
                            showscreeprog.setVisibility(View.GONE);

                            loadscreen.setVisibility(View.VISIBLE);


                            seasonscouct.setText("فيلم");

                            for (ServerModel episode: movie.getVideos()) {
                                firestEpe.add(episode);
                                getComicsnextback.Epesodes.add(episode);
                            }

                            watchepenoew.setEnabled(true);

                            watchepenoew.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {



                                }
                            });
                        }


                        @Override
                        public void onError(ANError anError) {

                        }
                    });


        }else {
            watchepenoew.setEnabled(false);
            AndroidNetworking.get(BEANLINKS.BASELINK+"tv/{id}")
                    .addPathParameter("id", link)
                    .addHeaders(headers)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsObject(CartoonModel.class , new ParsedRequestListener<CartoonModel>(){

                        @Override
                        public void onResponse(CartoonModel movie) {
                            loadscreen.setVisibility(View.VISIBLE);
                            showscreeprog.setVisibility(View.GONE);

                            String formatted = format(movie.getViews());
                            viewstxt.setText(formatted);




                            seasonscouct.setText( movie.getSeasons().size()+" المواسم");

                            for (SeasonModel episode: movie.getSeasons()) {
                                Seasons.add(episode);
                                getComicsnextback.SeasonsOut.add(episode);
                            }
                            watchepenoew.setEnabled(true);

                            watchepenoew.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {



                                    Intent sendDataToDetailsActivity = new Intent(ShowInfoActivity.this, MySeasons.class);

                                    sendDataToDetailsActivity.putExtra("tvmodel",movie);

                                    startActivity(sendDataToDetailsActivity);
                                    CheckAds(ShowInfoActivity.this);
                                    if (minterstitia != null){
                                        minterstitia.show(ShowInfoActivity.this);
                                    }

                                }
                            });
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });


        }

*/







    }

    static public void CheckAds(Activity context) {

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, "ca-app-pub-1830744893983649/9995298807", adRequest, new InterstitialAdLoadCallback( ) {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC, new AdEventListener( ) {
                    @Override
                    public void onReceiveAd(@NonNull Ad ad) {
                        startAppAd.showAd();
                    }

                    @Override
                    public void onFailedToReceiveAd(@Nullable Ad ad) {

                    }
                });
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                minterstitia = interstitialAd;
                minterstitia.setFullScreenContentCallback(new FullScreenContentCallback( ) {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent( );
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);


                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent( );
                    }
                });
            }
        });



    }

    private void initial() {

        movieApi = Servicy.getMovieApi();
        tmdb_api = TMDBAPI.getMovieApi();
        startAppAd = new StartAppAd(this);

        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        cartoon = (CartoonModel) getIntent().getSerializableExtra("cartoon");
        movieListViewModel = new ViewModelProvider(ShowInfoActivity.this).get(getAlltvsViewModel.class);
        navigationBar = findViewById(R.id.navigation_bar);
        navigationBar.getLayoutParams().height = getNavigationBarHeight();

        gooback = findViewById(R.id.gooback);
        loadscreen = findViewById(R.id.loadscreen);
        pin_rlt = findViewById(R.id.pin_rlt);
        review_rlt = findViewById(R.id.review_rlt);
        tawsiat_rlt = findViewById(R.id.tawsiat_rlt);
        news_rlt = findViewById(R.id.news_rlt);
        cast_rv = findViewById(R.id.cast_rv);
        titleser = findViewById(R.id.titleser);
        yeartxt = findViewById(R.id.yeartxt);
        pin_rv = findViewById(R.id.pin_rv);
        viewstxt = findViewById(R.id.viewstxt);
        watchepenoew = findViewById(R.id.Gotoepusode);
        showscreeprog = findViewById(R.id.showscreeprog);
        storytxt = findViewById(R.id.storytxt);
        genertxt = findViewById(R.id.genertxt);
        seasonscouct = findViewById(R.id.seasonscouct);
        seasonpint = findViewById(R.id.seasonpint);
        reatetxt = findViewById(R.id.reatetxt);
        addtofav = findViewById(R.id.addtofav);
        agetxt = findViewById(R.id.agetxt);
        imgviews = findViewById(R.id.imgviews);
        addratebtn = findViewById(R.id.addratebtn);
        review_rv = findViewById(R.id.review_rv);
        tawsiat_rv = findViewById(R.id.tawsiat_rv);
        news_rv = findViewById(R.id.news_rv);
        likeicon = findViewById(R.id.likeicon);
        gotocomments = findViewById(R.id.gotocomments);


        gooback.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        watchepenoew.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {


                if (!cartoon.getCountentType().equals("comic")){
                    startActivity(new Intent( ShowInfoActivity.this, MySeasons.class ).putExtra("cartoon", cartoon));
                }else{
                    startActivity(new Intent( ShowInfoActivity.this, Chapters.class ).putExtra("comic", cartoon));

                }

            }
        });

        gotocomments.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                startActivity(
                        new Intent( ShowInfoActivity.this, Comments.class )
                                .putExtra("cartoonID", cartoon));

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        startAppAd.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        startAppAd.onPause();
    }


    private void Likeit(CartoonModel cartoonModel) {


/*
        if (jseriesDB.checkIfMyListExists(postkey)){

            boolean added = jseriesDB.DeletMyListDB(postkey);
            if (added){
                Toast.makeText(ShowInfoActivity.this, "Removed from My List", Toast.LENGTH_SHORT).show();
                likeicon.setImageDrawable(getResources().getDrawable(R.drawable.pluss));
                CheckLikeStat(String.valueOf(cartoonModel.getId()));
            }
        }else {

            boolean added = jseriesDB.AddtoMyListdDB(cartoonModel);
            if (added){
                Toast.makeText(ShowInfoActivity.this, "Added To My List", Toast.LENGTH_SHORT).show();
                likeicon.setImageDrawable(getResources().getDrawable(R.drawable.check));
                CheckLikeStat(String.valueOf(cartoonModel.getId()));
            }

        }*/
    }

    private void CheckLikeStat(String postkey) {

      /*  if (jseriesDB.checkIfMyListExists(postkey)){

            likeicon.setImageDrawable(getResources().getDrawable(R.drawable.check));

        }else {

            likeicon.setImageDrawable(getResources().getDrawable(R.drawable.pluss));

        }
*/

    }



    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }


    Point navigationBarSize = new Point();
    public int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarSize.y = getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarSize.y;
    }


    private void loadAd() {
        Context context = ShowInfoActivity.this;
        adViewLayout = findViewById(R.id.adViewLayout);
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
    }

}