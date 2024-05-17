package com.anass.jplus.Activities;

import static com.anass.jplus.Activities.AddPost.changestatucolor;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;
import static com.anass.jplus.Constant.AppConfig.adType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Config.Function;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Constant.epeListConst;
import com.anass.jplus.DB.seeepe.SeeEpe;
import com.anass.jplus.DB.seeepe.SeeEpeDatabase;
import com.anass.jplus.JPLAYER.Player;
import com.anass.jplus.JPLAYER.WebPlayer;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.R;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

public class nextbeforepe extends AppCompatActivity {

    AuthManager authManager;
    ImageView backarr,next,backch;
    TextView namechrabar,fhdtitle;
    CartoonModel cartoon;
    EpesodModel episode;
    LinearLayout fhdd;
    StartAppAd startAppAd;
    SeeEpeDatabase db;


    int Current_List_Position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changestatucolor(this);
        setContentView(R.layout.activity_nextbeforepe);
        db = SeeEpeDatabase.getDbInstance(this);

        startAppAd = new StartAppAd(this);
        startAppAd.loadAd(StartAppAd.AdMode.VIDEO);

        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        cartoon = (CartoonModel) getIntent( ).getSerializableExtra("cartoon");
        episode = (EpesodModel) getIntent( ).getSerializableExtra("episode");
        Current_List_Position = getIntent().getIntExtra("Current_List_Position",1);


        namechrabar = findViewById(R.id.namechrabar);


        fhdd = findViewById(R.id.fhdd);
        next = findViewById(R.id.next);
        backarr = findViewById(R.id.backarr);
        fhdtitle = findViewById(R.id.fhdtitle);

        backch = findViewById(R.id.backch);
        backch.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SetEpeLinks(epeListConst.epesodlistof.get(Current_List_Position));

        if (authManager.getUserInfo().getId() > 0){
            if (authManager.getUserInfo().getNoads() == 0){
                loadAd();
            }
        }else{
            loadAd();
        }






        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (authManager.getUserInfo().getId() > 0){
                    if (authManager.getUserInfo().getNoads() == 0){
                        Current_List_Position++;
                        SetEpeLinks(epeListConst.epesodlistof.get(Current_List_Position));
                        startAppAd.showAd(  );
                    }else {
                        Current_List_Position++;
                        SetEpeLinks(epeListConst.epesodlistof.get(Current_List_Position));
                    }
                }else{
                    Current_List_Position++;
                    SetEpeLinks(epeListConst.epesodlistof.get(Current_List_Position));
                    loadAd();
                }



            }
        });

        backarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (authManager.getUserInfo().getId() > 0){
                    if (authManager.getUserInfo().getNoads() == 0){
                        Current_List_Position--;
                        SetEpeLinks(epeListConst.epesodlistof.get(Current_List_Position));
                        startAppAd.showAd(  );
                    }else {
                        Current_List_Position--;
                        SetEpeLinks(epeListConst.epesodlistof.get(Current_List_Position));
                    }
                }else{
                    Current_List_Position--;
                    SetEpeLinks(epeListConst.epesodlistof.get(Current_List_Position));
                    loadAd();
                }




            }
        });




    }

    private void SetEpeLinks(EpesodModel epesodModel) {

        EpesodModel c = epesodModel;

        if (Current_List_Position == epeListConst.epesodlistof.size()-1 ){
            next.setVisibility(View.INVISIBLE);
        }else {
            next.setVisibility(View.VISIBLE);

        }
        if (Current_List_Position == 0){
            backarr.setVisibility(View.INVISIBLE);


        }else {
            backarr.setVisibility(View.VISIBLE);

        }

        if (cartoon.getCountentType().equals("tv")){
            namechrabar.setText(" الحلقة "+epesodModel.getLebel());
        }else {
            namechrabar.setText(cartoon.getTitle());

        }

        fhdtitle.setText(" ( " +epesodModel.getLebel()+" ) " +epesodModel.getSource() + " سيرفر ");
        fhdd.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (db.SeeEpeDao().getResumeContentid(c.getId()) == 0) {
                    db.SeeEpeDao().insert(new SeeEpe(0, c.getId(),
                            c.getSource(),
                            c.getUrl(),
                            c.getLebel()));

                } else {
                    int epeID = db.SeeEpeDao().getResumeContentid(c.getId());
                    db.SeeEpeDao().updateSource(c.getSource(), c.getUrl(), epeID);

                }

                Intent intent = new Intent(nextbeforepe.this, Player.class);
                intent.putExtra("cartoon", cartoon);
                intent.putExtra("episode", epesodModel);
                intent.putExtra("Content_Type", cartoon.getCountentType( ));
                intent.putExtra("Current_List_Position", Current_List_Position);
                intent.putExtra("Next_Ep_Avilable", "No");
                startActivity(intent);

            }
        });


    }


    private void loadAd() {


        if(adType == 1) {   //AdMob
            MobileAds.initialize(nextbeforepe.this, initializationStatus -> {
                // Do nothing
            });
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(nextbeforepe.this, AppConfig.adMobInterstitial, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    interstitialAd.show(nextbeforepe.this);
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback( ) {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent( );

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);

                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                }
            });


        } else if(adType == 2) { //StartApp
            // and show interstitial ad
            StartAppAd startAppAd1 = new StartAppAd(nextbeforepe.this);
            startAppAd1.loadAd(new AdEventListener( ) {
                @Override
                public void onReceiveAd(@NonNull Ad ad) {
                    startAppAd1.showAd();
                }

                @Override
                public void onFailedToReceiveAd(@androidx.annotation.Nullable Ad ad) {

                }
            });


        } else if(adType == 3) { //Facebook

            AudienceNetworkAds.initialize(nextbeforepe.this);



            com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(nextbeforepe.this, AppConfig.facebook_interstitial_ads_placement_id);
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
            IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
                @Override
                public void onUnityAdsAdLoaded(String placementId) {
                    UnityAds.show(nextbeforepe.this, AppConfig.Unity_rewardedVideo_ID, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                        @Override
                        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {

                        }

                        @Override
                        public void onUnityAdsShowStart(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowClick(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {

                        }
                    });
                }

                @Override
                public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {

                }
            };
            UnityAds.initialize (this, AppConfig.Unity_Game_ID, false);
            UnityAds.load(AppConfig.Unity_rewardedVideo_ID, loadListener);


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
        }


    }

}