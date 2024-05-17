package com.anass.jplus.Activities;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import static com.anass.jplus.Activities.MyList.Mylist.changestatucolor;
import static com.anass.jplus.Config.ServerController.EXTRA_HEADERS;
import static com.anass.jplus.Config.ServerController.HEADERS;
import static com.anass.jplus.Config.ServerController.POSTER;
import static com.anass.jplus.Config.ServerController.SECURE_URI;
import static com.anass.jplus.Config.ServerController.TITLE;
import static com.anass.jplus.Config.ServerController.USER_AGENT;
import static com.anass.jplus.Config.ServerController.VIDEOTYPE;
import static com.anass.jplus.Config.ServerController.downloadFromAdm;
import static com.anass.jplus.Config.config.SETTINGS_NAME;
import static com.anass.jplus.Constant.AppConfig.adType;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Activities.Settings.SettingsJcartoon;
import com.anass.jplus.Activities.jcartoonroom.CreateRoom;
import com.anass.jplus.Activities.jcartoonroom.Myrooms;
import com.anass.jplus.Adapters.RoomAdapter;
import com.anass.jplus.Config.DownloadHelper;
import com.anass.jplus.Config.ServerController;
import com.anass.jplus.Config.Yts;
import com.anass.jplus.Config.getComicsnextback;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Constant.epeListConst;
import com.anass.jplus.DB.seeepe.SeeEpe;
import com.anass.jplus.DB.seeepe.SeeEpeDatabase;
import com.anass.jplus.JPLAYER.Player;
import com.anass.jplus.JPLAYER.WebPlayer;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.Models.RoomModel;
import com.anass.jplus.Models.SeasonModel;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.YTStreamList;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.SeasonResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.dooo.stream.DoooStream;
import com.dooo.stream.Model.StreamList;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.github.se_bastiaan.torrentstreamserver.TorrentStreamServer;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySeasons extends AppCompatActivity {

    RecyclerView rectmovies;
    ProgressBar progreed;
    private SharedPreferences sp;
    StartAppAd startAppAd;
    List<SeasonModel> epesodModels;
    List<EpesodModel> serverModels;

    //Spinner choseplacesave;
    AutoCompleteTextView auto_complet;
    TextInputLayout nbreplace;
    ImageView backch;

    List<StreamList> multiqualityListWeb = new ArrayList<>( );
    TextView warrnintxt;

    String userAgent = "";
    public static final String VLC_INTENT = "market://details?id=org.videolan.vlc";
    public static final String VLC_PACKAGE_NAME = "org.videolan.vlc";

    String epetitle;
    ServerController serverController;
    CartoonModel cartoonModel;

    private String bGljZW5zZV9jb2Rl = "565455";
    WebView webView;

    private TorrentStreamServer torrentStreamServer;

    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    HashMap<String, String> headers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_seasons);
        changestatucolor(this);
        apiService = ApiClient.getRetrofit( ).create(ApiService.class);
        userAgent = WebSettings.getDefaultUserAgent(MySeasons.this);
        sp = getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);

        serverController = new ServerController(MySeasons.this);

        rectmovies = findViewById(R.id.rectmovies);
        auto_complet = findViewById(R.id.auto_complet);
        progreed = findViewById(R.id.progreed);
        nbreplace = findViewById(R.id.nbreplace);
        backch = findViewById(R.id.backch);
        warrnintxt = findViewById(R.id.warrnintxt);
        //choseplacesave = findViewById(R.id.choseplacesave);

        StartAppAd.init(this, "107796986", "210473406");
        startAppAd = new StartAppAd(this);
        startAppAd.loadAd(StartAppAd.AdMode.VIDEO);

        InitiaStrings( );
        headers = new HashMap<String, String>( );
        headers.put("Accept", "application/json");
        headers.put("User-Agent", "Thunder Client (https://www.thunderclient.com)");


        backch.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                finish( );
            }
        });
        if (cartoonModel.getCountentType( ).equals("tv")) {
            GetSeasons( );
        } else {
            nbreplace.setVisibility(View.GONE);
            warrnintxt.setVisibility(View.GONE);

            GetLinks( );
        }


    }

    private void GetSeasons() {


        nbreplace.setVisibility(View.GONE);

        apiService.getSeasons(cartoonModel.getId( )).enqueue(new Callback<SeasonResponse>( ) {
            @Override
            public void onResponse(Call<SeasonResponse> call, Response<SeasonResponse> response) {
                assert response.body( ) != null;

                List<SeasonModel> seasonModels = response.body( ).getSeasons( );
                epesodModels = new ArrayList<>( );
                ArrayList<String> seasonstilte = new ArrayList<>( );
                progreed.setVisibility(View.GONE);

                for (SeasonModel epesode : seasonModels) {
                    epesodModels.add(epesode);
                    seasonstilte.add(epesode.getName( ).replace("season", " الموسم "));
                }

                getComicsnextback.SeasonsOut.clear( );
                getComicsnextback.SeasonsOut = epesodModels;

                nbreplace.setVisibility(View.VISIBLE);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MySeasons.this,
                        R.layout.custom_spinner, seasonstilte);

                if (epesodModels.size( ) > 0) {
                    auto_complet.setAdapter(adapter);

                    auto_complet.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            warrnintxt.setVisibility(View.GONE);
                            GetEpisdoeFromSeason(epesodModels.get(position), seasonstilte.get(position));
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<SeasonResponse> call, Throwable t) {

            }
        });


    }

    private void GetEpisdoeFromSeason(SeasonModel seasonModel, String seasonname) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MySeasons.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rectmovies.setLayoutManager(linearLayoutManager);
        rectmovies.setNestedScrollingEnabled(false);

        progreed.setVisibility(View.VISIBLE);
        serverModels = new ArrayList<>( );


        apiService.getEpisodes(seasonModel.getId( )).enqueue(new Callback<List<EpesodModel>>( ) {
            @Override
            public void onResponse(Call<List<EpesodModel>> call, Response<List<EpesodModel>> response) {

                progreed.setVisibility(View.GONE);

                epeListConst.epesodlistof.clear();
                epeListConst.epesodlistof = response.body( );

                rectmovies.setAdapter(new epesoedSeasonsAdapter(
                        response.body( ),
                        MySeasons.this,
                        cartoonModel.getTitle( ),
                        cartoonModel,
                        seasonModel,
                        seasonname));
            }

            @Override
            public void onFailure(Call<List<EpesodModel>> call, Throwable t) {

            }
        });


    }

    private void GetLinks() {



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MySeasons.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rectmovies.setLayoutManager(linearLayoutManager);
        rectmovies.setNestedScrollingEnabled(false);

        progreed.setVisibility(View.VISIBLE);
        serverModels = new ArrayList<>( );


        apiService.getMovieEpisodes(cartoonModel.getId( )).enqueue(new Callback<List<EpesodModel>>( ) {
            @Override
            public void onResponse(Call<List<EpesodModel>> call, Response<List<EpesodModel>> response) {

                progreed.setVisibility(View.GONE);

                epeListConst.epesodlistof.clear();
                epeListConst.epesodlistof = response.body( );

                rectmovies.setAdapter(new epesoedSeasonsAdapter(
                        response.body( ),
                        MySeasons.this,
                        cartoonModel.getTitle( ),
                        cartoonModel, new SeasonModel(), ""));
            }

            @Override
            public void onFailure(Call<List<EpesodModel>> call, Throwable t) {

            }
        });


    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission( ), isGranted -> {
                if (isGranted) {
                    //perform
                } else {
                    //request
                }
            });

    private void InitiaStrings() {


        cartoonModel = (CartoonModel) getIntent( ).getSerializableExtra("cartoon");


    }


    public class epesoedSeasonsAdapter extends RecyclerView.Adapter<epesoedSeasonsAdapter.myviewholder> {

        List<EpesodModel> chaptermodelList;
        Context context;

        SeeEpeDatabase db;
        String title, seasonname;
        CartoonModel cartoonModel;
        SeasonModel seasonModel;
        AuthManager authManager;

        private SharedPreferences sp;

        public epesoedSeasonsAdapter(List<EpesodModel> chaptermodelList,
                                     Context context, String title, CartoonModel cartoonModel,
                                     SeasonModel seasonModel, String seasonname) {
            this.chaptermodelList = chaptermodelList;
            this.context = context;
            this.title = title;
            this.cartoonModel = cartoonModel;
            this.seasonModel = seasonModel;
            this.seasonname = seasonname;
            sp = context.getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);
            db = SeeEpeDatabase.getDbInstance(context);
            authManager = new AuthManager(context.getSharedPreferences(SettingsJcartoon.SETTINGS_NAME, MODE_PRIVATE));

        }

        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext( )).inflate(R.layout.chapter_item, parent, false);
            return new myviewholder(view);
        }

        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(@NonNull myviewholder holder, int position) {
            EpesodModel c = chaptermodelList.get(position);


            int gg = position;
            int currentPage = sp.getInt("currentPage" + c.getId(), 1);
            int allduration = sp.getInt("allduration" + c.getId(), 0);
            epetitle = c.getLebel( );


            checkepe(db,holder,c);

            if (cartoonModel.getCountentType( ).equals("movie")) {
                holder.episode.setText(epetitle+ " ( " +c.getSource()+" ) ");
                holder.titleof.setText("");
            } else {

                holder.episode.setText(" الحلقة : " + epetitle+ " ( " +c.getSource()+" ) ");
                if (chaptermodelList.get(position).getVideoCartoon( ) != null) {
                    holder.titleof.setText(chaptermodelList.get(position).getVideoCartoon( ).getTitle( ));
                }
            }


            if (allduration < 6000) {
                holder.showWhems.setMax(0);

            } else {
                holder.showWhems.setMax(allduration);
            }

            holder.showWhems.setProgress(currentPage);

            Glide.with(holder.itemView.getContext( )).load(cartoonModel.getPoster( )).into(holder.thumbnail);

            holder.see_epe.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    if (db.SeeEpeDao().getResumeContentid(c.getId()) == 0) {
                        db.SeeEpeDao().insert(new SeeEpe(0, c.getId(),
                                c.getSource(),
                                c.getUrl(),
                                c.getLebel()));
                        holder.see_epe.setVisibility(View.GONE);
                        holder.see_epe_yes.setVisibility(View.VISIBLE);


                    } else {
                        int epeID = db.SeeEpeDao().getResumeContentid(c.getId());
                        db.SeeEpeDao().delete(epeID);
                        holder.see_epe.setVisibility(View.VISIBLE);
                        holder.see_epe_yes.setVisibility(View.GONE);
                    }
                }
            });

            holder.click.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {

                    if (db.SeeEpeDao().getResumeContentid(c.getId()) == 0) {
                        db.SeeEpeDao().insert(new SeeEpe(0, c.getId(),
                                c.getSource(),
                                c.getUrl(),
                                c.getLebel()));
                        holder.see_epe.setVisibility(View.GONE);
                        holder.see_epe_yes.setVisibility(View.VISIBLE);
                    } else {
                        int epeID = db.SeeEpeDao().getResumeContentid(c.getId());
                        db.SeeEpeDao().updateSource(c.getSource(), c.getUrl(), epeID);
                        holder.see_epe.setVisibility(View.GONE);
                        holder.see_epe_yes.setVisibility(View.VISIBLE);
                    }


                    if (c.getSource().equals("Embed")){
                        startActivity(new Intent(context, WebPlayer.class)
                                .putExtra("link",c.getUrl()));
                    }else{
                        Intent intent = new Intent(context, nextbeforepe.class);

                        intent.putExtra("cartoon", cartoonModel);
                        intent.putExtra("episode", chaptermodelList.get(gg));
                        intent.putExtra("Content_Type", cartoonModel.getCountentType( ));
                        intent.putExtra("Current_List_Position", gg);
                        intent.putExtra("Next_Ep_Avilable", "No");
                        startActivity(intent);

                    }






                }
            });

            holder.creatroom.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {
                    if (authManager.getUserInfo().getId() == 0) {

                        Toast.makeText(MySeasons.this, "يجب تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();

                    }else {

                        Intent intent = new Intent(context, CreateRoom.class);
                        intent.putExtra("cartoon", cartoonModel);
                        intent.putExtra("epe", c);
                        intent.putExtra("season", seasonModel);
                        startActivity(intent);

                   /*     DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("rooms") ;

                        db.addValueEventListener(new ValueEventListener( ) {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                List<RoomModel> roomModels = new ArrayList<>();

                                for (DataSnapshot room : snapshot.getChildren()) {
                                    Integer whoCreateRoomUserId = room.child("whoCreateRoomUserId").getValue(Integer.class);
                                    if (whoCreateRoomUserId != null && whoCreateRoomUserId.equals(authManager.getUserInfo().getId())) {
                                        RoomModel roomModel = new RoomModel(
                                                room.child("id").getValue(String.class),
                                                room.child("title").getValue(String.class),
                                                whoCreateRoomUserId,
                                                room.child("invitaionCode").getValue(String.class),
                                                room.child("timeCreted").getValue(String.class),
                                                room.child("tvId").getValue(Integer.class),
                                                room.child("seasonId").getValue(Integer.class),
                                                room.child("epeId").getValue(Integer.class),
                                                room.child("typeIspublicPrivate").getValue(String.class),
                                                room.child("numberLimit").getValue(Integer.class)
                                        );

                                        roomModels.add(roomModel);
                                    }



                                }


                                int roombunber = roomModels.size();


                                if (authManager.getUserInfo().getRoomsNumber() == roombunber){
                                    ServerController.PleaseWait.dismiss();
                                    Toast.makeText(context, "الحد المسموع هو 5 غرف لكل شخص , احدف غرفة أخرى أو تواصل مع فريق جي كرتون", Toast.LENGTH_LONG).show( );

                                }else {
                                    ServerController.PleaseWait.dismiss();


                                }



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
*/

                    }




                }
            });

            if (c.getSource( ).equals("Streamwish") || c.getSource( ).equals("Embed")) {
                holder.download_epe.setVisibility(View.GONE);
            }

            holder.download_epe.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {

                    if (seasonModel.getName() != null){
                        Prepare_Source(cartoonModel,seasonModel, chaptermodelList.get(position), gg);

                    }else {
                        Prepare_Source(cartoonModel, new SeasonModel(), chaptermodelList.get(position), gg);

                    }

                }
            });


        }



        @Override
        public int getItemCount() {
            return chaptermodelList.size( );
        }

        public class myviewholder extends RecyclerView.ViewHolder {

            TextView episode, titleof;
            ImageView thumbnail, download_epe,creatroom,click,see_epe,see_epe_yes;
            LinearProgressIndicator showWhems;

            public myviewholder(@NonNull View itemView) {
                super(itemView);

                episode = itemView.findViewById(R.id.episode);
                titleof = itemView.findViewById(R.id.anime);
                thumbnail = itemView.findViewById(R.id.thumbnail);
                download_epe = itemView.findViewById(R.id.download_epe);
                showWhems = itemView.findViewById(R.id.showWhems);
                creatroom = itemView.findViewById(R.id.creatroom);
                click = itemView.findViewById(R.id.click);
                see_epe = itemView.findViewById(R.id.see_epe);
                see_epe_yes = itemView.findViewById(R.id.see_epe_yes);


            }


        }
    }




    private void checkepe(SeeEpeDatabase db, epesoedSeasonsAdapter.myviewholder holder, EpesodModel c) {


        if (db.SeeEpeDao().getResumeContentid(c.getId()) == 0) {

            holder.see_epe.setVisibility(View.VISIBLE);
            holder.see_epe_yes.setVisibility(View.GONE);

        } else {
            holder.see_epe.setVisibility(View.GONE);
            holder.see_epe_yes.setVisibility(View.VISIBLE);


        }

    }


    private void OpenDialogDownload(CartoonModel tvmodel, EpesodModel epesodModel, SeasonModel seasonModel, String url, String Lebel, Context context) {
        ServerController.PleaseWait.dismiss( );

        final Dialog dialog = new Dialog(MySeasons.this, R.style.MyAlertDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_download);
        dialog.setCancelable(true);
        dialog.getWindow( ).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams( );
        lp.copyFrom(dialog.getWindow( ).getAttributes( ));
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;

        dialog.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                dialog.dismiss( );
            }
        });

        dialog.findViewById(R.id.JSERIESPlayer).setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Log.d("test", Lebel+" "+url);

                DownloadHelper.startDownload(tvmodel,epesodModel,seasonModel,context, v, Lebel, "mp4", url, "", "");

             /*   DownloadManage.getInstance(context)
                        .addDownloadRequest(url, new File(""), tvmodel.getTitle( ), Lebel);*/
                dialog.dismiss( );

            }
        });
        dialog.findViewById(R.id.vlc).setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                String fileName = tvmodel.getTitle( ) + " - " + Lebel + " - " + ".mp4";

                downloadFromAdm(MySeasons.this, url, fileName);


                dialog.dismiss( );

            }
        });
        dialog.findViewById(R.id.admm).setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Log.d("test", Lebel+" "+url);

                String title = tvmodel.getTitle()+seasonModel.getName()+"E"+epesodModel.getLebel();

                Intent shareVideo = new Intent(Intent.ACTION_VIEW);
                shareVideo.setDataAndType(Uri.parse(url), "video/*");
                shareVideo.setPackage("idm.internet.download.manager");
                shareVideo.putExtra("title", title);
                Bundle headers = new Bundle();
                headers.putString(USER_AGENT, "jcartoon");
                shareVideo.putExtra("android.media.intent.extra.HTTP_HEADERS", headers);
                shareVideo.putExtra(HEADERS, headers);
                shareVideo.putExtra(SECURE_URI, true);
                try {
                    context.startActivity(shareVideo);
                } catch (ActivityNotFoundException ex) {
                   

                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=idm.internet.download.manager")));
                    } catch (ActivityNotFoundException anfe) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=idm.internet.download.manager")));
                    }

                }



                dialog.dismiss( );

            }
        });

        dialog.show( );
        dialog.getWindow( ).setAttributes(lp);
    }


    private void openWithVLC(String url, String s, String poster) {

        Intent shareVideo = new Intent(Intent.ACTION_VIEW);
        shareVideo.setDataAndType(Uri.parse(url), VIDEOTYPE);
        shareVideo.setPackage(VLC_PACKAGE_NAME);
        shareVideo.putExtra(TITLE, s);
        shareVideo.putExtra(POSTER, poster);
        Bundle headers = new Bundle( );
        headers.putString(USER_AGENT, getResources( ).getString(R.string.app_name));
        shareVideo.putExtra(EXTRA_HEADERS, headers);
        shareVideo.putExtra(HEADERS, headers);
        shareVideo.putExtra(SECURE_URI, true);
        try {
            MySeasons.this.startActivity(shareVideo);
        } catch (ActivityNotFoundException ex) {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            String uriString = VLC_INTENT;
            intent.setData(Uri.parse(uriString));
            MySeasons.this.startActivity(intent);
        }

    }

    public void watchVideo(int gg, CartoonModel tvmodel, EpesodModel serverModel, List<EpesodModel> chaptermodelList) {


        Intent intent = new Intent(this, Player.class);

        intent.putExtra("cartoon", tvmodel);
        intent.putExtra("episode", serverModel);
        intent.putExtra("Content_Type", tvmodel.getCountentType( ));
        intent.putExtra("Current_List_Position", gg);
        intent.putExtra("Next_Ep_Avilable", "No");

        startActivity(intent);

    }

    public void openWithMXPlayer(String url) {
        boolean appInstalledOrNot = appInstalledOrNot("com.mxtech.videoplayer.ad");
        boolean appInstalledOrNot2 = appInstalledOrNot("com.mxtech.videoplayer.pro");
        String str2;
        if (appInstalledOrNot || appInstalledOrNot2) {
            String str3;
            if (appInstalledOrNot2) {
                str2 = "com.mxtech.videoplayer.pro";
                str3 = "com.mxtech.videoplayer.ActivityScreen";
            } else {
                str2 = "com.mxtech.videoplayer.ad";
                str3 = "com.mxtech.videoplayer.ad.ActivityScreen";
            }
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "application/x-mpegURL");
                intent.setPackage(str2);
                intent.setClassName(str2, str3);
                startActivity(intent);
                return;
            } catch (Exception e) {
                e.fillInStackTrace( );
                Log.d("errorMx", e.getMessage( ));
                return;
            }
        }
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mxtech.videoplayer.ad")));
        } catch (ActivityNotFoundException e2) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mxtech.videoplayer.ad")));
        }
    }

    public boolean appInstalledOrNot(String str) {
        try {
            getPackageManager( ).getPackageInfo(str, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void Prepare_Source(CartoonModel cartoonModel, SeasonModel seasonModel, EpesodModel epesodModel, int gg) {

        String url = epesodModel.getUrl( );
        String source = epesodModel.getSource( );
        com.google.android.exoplayer2.util.Log.d("test", source + " " + url);


        ServerController.PleaseWait.show(MySeasons.this);


        if (epesodModel.getSource( ).equalsIgnoreCase("JCARTOON")) {

            OpenDialogDownload(cartoonModel,epesodModel, seasonModel, url, epesodModel.getLebel( ), this);

        }
        if (epesodModel.getSource( ).equalsIgnoreCase("embed")) {
            ServerController.PleaseWait.dismiss( );
            Toast.makeText(this, "الرابط للمشاهدة فقط", Toast.LENGTH_SHORT).show( );

        } else if (epesodModel.getSource( ).equalsIgnoreCase("mkv")) {

            OpenDialogDownload(cartoonModel, epesodModel,seasonModel, url, epesodModel.getLebel( ), this);


        } else if (epesodModel.getSource( ).equals("Youtube")) {

            Yts.getlinks(this, url, new Yts.VolleyCallback( ) {
                @Override
                public void onSuccess(List<YTStreamList> result) {
                    ytMultipleQualityDialog(MySeasons.this,cartoonModel,epesodModel,seasonModel, result, epesodModel.getLebel( ));
                }

                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(MySeasons.this, "هناك خطأ بالرابط", Toast.LENGTH_SHORT).show( );
                }
            });
        }  else if (source.equals("Dailymotion") || source.equals("DoodStream") || source.equals("Dropbox") || (source.equals("Fembed"))
                || source.equals("Facebook") || source.equals("GogoAnime") || source.equals("GoogleDrive") || source.equals("MediaShore")
                || source.equals("MixDrop") || source.equals("Onedrive") || source.equals("OKru") || source.equals("StreamTape")
                || source.equals("Twitter") || source.equals("Voesx") || source.equals("VK")
                || source.equals("Voot") || source.equals("Vudeo") || source.equals("Vimeo") || source.equals("YoutubeLive")
                || source.equals("Yandex") || source.equals("Zoro")) {


            new DoooStream(MySeasons.this, bGljZW5zZV9jb2Rl, new DoooStream.OnInitialize( ) {
                @Override
                public void onSuccess(DoooStream doooStream) {
                    doooStream.find(source, url, true, new DoooStream.OnTaskCompleted( ) {
                        @Override
                        public void onSuccess(List<StreamList> streamList) {
                            multiQualityDialog(epesodModel,seasonModel,streamList, epesodModel.getLebel( ));
                        }

                        @Override
                        public void onError() {
                            ServerController.PleaseWait.dismiss( );
                            Toast.makeText(MySeasons.this, "هناك خطأ بالرابط", Toast.LENGTH_SHORT).show( );
                        }
                    });
                }

                @Override
                public void onError(RuntimeException e) {
                    ServerController.PleaseWait.dismiss( );
                    Toast.makeText(MySeasons.this, "  هناك خطأ بالرابط  " + e.getMessage( ), Toast.LENGTH_SHORT).show( );
                }
            });
        } else if (source.equals("Streamwish")) {


            webView = new WebView(MySeasons.this);
            webView.setWebViewClient(new WebViewClient( ) {
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    if (request.getUrl( ).toString( ).toLowerCase( ).contains("master.m3u8")) {

                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });

                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".m3u8")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mp4") && !request.getUrl( ).toString( ).toLowerCase( ).contains("blank.mp4")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mp4")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mkv")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".ts")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    }
                    return super.shouldInterceptRequest(view, request);
                }
            });


            String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36";
            webView.getSettings( ).setUserAgentString(ua);
            webView.getSettings( ).setJavaScriptEnabled(true);
            webView.getSettings( ).setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings( ).setDomStorageEnabled(true);
            webView.loadUrl(url);


        } else if (source.equals("vidpro")) {
            String finalurl = "";

            if (!url.contains("embed")){
                 finalurl = url.replace("https://vidpro.net/","https://vidpro.net/embed-");

            }


            webView = new WebView(MySeasons.this);
            webView.setWebViewClient(new WebViewClient( ) {
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    if (request.getUrl( ).toString( ).toLowerCase( ).contains("master.m3u8")) {

                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });

                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".m3u8")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mp4") && !request.getUrl( ).toString( ).toLowerCase( ).contains("blank.mp4")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mp4")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mkv")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".ts")) {
                        runOnUiThread(new Runnable( ) {
                            public void run() {
                                OpenDialogDownload(cartoonModel, epesodModel, seasonModel, request.getUrl( ).toString( ), epesodModel.getLebel( ), MySeasons.this);
                                webView.destroy( );
                            }
                        });
                    }
                    return super.shouldInterceptRequest(view, request);
                }
            });


            String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36";
            webView.getSettings( ).setUserAgentString(ua);
            webView.getSettings( ).setJavaScriptEnabled(true);
            webView.getSettings( ).setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings( ).setDomStorageEnabled(true);
            webView.loadUrl(finalurl);
        } else {
            ServerController.PleaseWait.dismiss( );
            Toast.makeText(this, "هناك خطأ بالرابط", Toast.LENGTH_SHORT).show( );
        }

    }

    void ytMultipleQualityDialog(Context context, CartoonModel cartoonModel, EpesodModel epesodModel, SeasonModel seasonModel, List<YTStreamList> list, String lebel) {
        ServerController.PleaseWait.dismiss( );
        Collections.reverse(list);
        CharSequence[] name = new CharSequence[list.size( )];
        CharSequence[] vid = new CharSequence[list.size( )];
        CharSequence[] token = new CharSequence[list.size( )];
        for (int i = 0; i < list.size( ); i++) {
            name[i] = list.get(i).getName( );
            vid[i] = list.get(i).getVid( );
            token[i] = list.get(i).getToken( );
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("الجودات المتوفرة")
                .setCancelable(true)
                .setItems(name, new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Yts.getStreamLinks(context, (String) token[which], (String) vid[which], new Yts.VolleyCallback2( ) {

                            @Override
                            public void onSuccess(String result) {

                                OpenDialogDownload(MySeasons.this.cartoonModel, epesodModel, seasonModel, result, lebel, context);

                            }

                            @Override
                            public void onError(VolleyError error) {
                            }
                        });
                    }
                })
                .setPositiveButton("إلغاء", (dialog, which) -> {
                    dialog.dismiss( );
                });
        builder.show( );
    }

    public void multiQualityDialog(EpesodModel epesodModel, SeasonModel seasonModel, List<StreamList> streamList, String lebel) {

        if (streamList.isEmpty( )) {
            ServerController.PleaseWait.dismiss( );
            Toast.makeText(this, "مشكلة بالرابط", Toast.LENGTH_SHORT).show( );
        } else if (streamList.size( ) == 1) {
            multiQualityPlayer(epesodModel, seasonModel, streamList.get(0).getExtension( ), streamList.get(0).getUrl( ),
                    streamList.get(0).getReferer( ), streamList.get(0).getCookie( ), lebel);
            ServerController.PleaseWait.dismiss( );
        } else {
            streamList.size( );
            CharSequence[] name = new CharSequence[streamList.size( )];
            for (int i = 0; i < streamList.size( ); i++) {
                name[i] = streamList.get(i).getQuality( );
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("الجودات")
                    .setCancelable(false)
                    .setItems(name, (dialog, which) -> {
                        multiQualityPlayer(epesodModel, seasonModel,streamList.get(which).getExtension( ), streamList.get(which).getUrl( ),
                                streamList.get(which).getReferer( ), streamList.get(which).getCookie( ), lebel);
                    })
                    .setPositiveButton("إلغاء", (dialog, which) -> {
                        dialog.dismiss( );
                    });
            ServerController.PleaseWait.dismiss( );
            builder.show( );
        }
    }

    public void multiQualityPlayer(EpesodModel epesodModel, SeasonModel seasonModel, String extension, String Url, String referer, String cookie, String lebel) {
        com.google.android.exoplayer2.util.Log.d("test", extension + " : " + Url + " : " + referer + " : " + cookie);


        if (extension.equalsIgnoreCase("m3u8")) {

            OpenDialogDownload(cartoonModel, epesodModel, seasonModel, Url, lebel, this);
        } else if (extension.equalsIgnoreCase("dash")) {
            OpenDialogDownload(cartoonModel, epesodModel, seasonModel, Url, lebel, this);

        } else if (extension.equalsIgnoreCase("mp4")) {
            OpenDialogDownload(cartoonModel, epesodModel, seasonModel, Url, lebel, this);

        } else if (extension.equalsIgnoreCase("mkv")) {
            OpenDialogDownload(cartoonModel, epesodModel, seasonModel, Url, lebel, this);

        } else if (extension.equalsIgnoreCase("Embed")) {

            Toast.makeText(this, "الرابط للمشاهدة فقط", Toast.LENGTH_SHORT).show( );

            /*Intent intent = new Intent(context, EmbedPlayer.class);
            intent.putExtra("url", Url);
            context.startActivity(intent);*/
        }
    }


}