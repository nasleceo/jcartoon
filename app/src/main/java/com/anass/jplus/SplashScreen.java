package com.anass.jplus;

import static com.anass.jplus.Activities.Auth.Signintojcartoon2.headers;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.anass.jplus.API.BEANLINKS;
import com.anass.jplus.Activities.ADcenter.WelcomPage;
import com.anass.jplus.Activities.Auth.LoginTojcartoon;
import com.anass.jplus.Activities.Auth.Signintojcartoon;
import com.anass.jplus.Activities.Auth.Signintojcartoon2;
import com.anass.jplus.Activities.InAppUpdate;
import com.anass.jplus.Activities.Jdownload.DownloadList;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Config.HelperUtils;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Models.AdsCenter;
import com.anass.jplus.Models.SkipModel;
import com.anass.jplus.Models.UpdateModel;
import com.anass.jplus.Models.adsmodel;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.AdsupdateResponse;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.FacebookSdk;
import com.google.android.gms.ads.MobileAds;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.onesignal.OneSignal;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2core.Downloader;
import com.tonyodev.fetch2okhttp.OkHttpDownloader;

import org.imaginativeworld.oopsnointernet.dialogs.signal.DialogPropertiesSignal;
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.BuildConfig;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {



    private boolean vpnStatus;
    private HelperUtils helperUtils;

    static public ApplicationInfo provideSnifferCheck;
    private SharedPreferences sp;
    AuthManager authManager;

    String notificationData = "";
    private ApiService apiService;

    String latestAPKVersionName;
    String latestAPKVersionCode;
    String apkFileUrl;
    String whatsNewOnLatestApk;
    int updateSkipable;
    int updateType;

    int googleplayAppUpdateType;
    AppUpdateManager appUpdateManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        provideApplicationInfo(getApplication());

        if (provideSnifferCheck != null) {
            Toast.makeText(this, "لا يمكنك تشغيل تطبيقات اللتقاط الشبكة", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }

        StartAppAd.disableSplash();
        StartAppSDK.enableReturnAds(false);

        AndroidNetworking.initialize(getApplicationContext());
        FirebaseApp.initializeApp(getApplicationContext());
        final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .setDownloadConcurrentLimit(5)
                .setHttpDownloader(new OkHttpDownloader(Downloader.FileDownloaderType.PARALLEL))
                .setNamespace("DownloadList")
                .build();
        Fetch.Impl.getInstance(fetchConfiguration);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            AppConfig.url =  "https://anassceo.jcartoon.com/api/";
                            apiService = ApiClient.getRetrofit().create(ApiService.class);

                            // Initialize the Mobile Ads SDK.
                            MobileAds.initialize(SplashScreen.this, initializationStatus -> {});
                            authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
                            Log.d("TAG", "onComplete: "+ AppConfig.url);
                            LoadADS();
                    }
                    }
                });





    }

    private void LoadADS() {

        apiService.skipapi().enqueue(new Callback<SkipModel>( ) {
            @Override
            public void onResponse(Call<SkipModel> call, Response<SkipModel> response) {

                if (response.body() != null){

                    SkipModel skipModel = response.body();

                    Log.d("TAG", "onResponse: "+skipModel.getIsgo());


                    apiService.alladsidandnative().enqueue(new Callback<AdsupdateResponse>( ) {
                        @Override
                        public void onResponse(Call<AdsupdateResponse> call, Response<AdsupdateResponse> response) {
                            if (response.body() != null){


                                adsmodel adsmodel = response.body().getAds();

                                AppConfig.adType = Integer.parseInt(adsmodel.getAdType());
                                AppConfig.adMobNative = adsmodel.getAdMobNative();
                                AppConfig.adMobBanner =adsmodel.getAdMobBanner();
                                AppConfig.adMobInterstitial =adsmodel.getAdMobInterstitial();
                                AppConfig.adMobAppOpenAd = adsmodel.getAdMobAppOpenAd();

                                String StartApp_App_ID = adsmodel.getStartAppAppID();
                                String Admob_APP_ID = adsmodel.getAdmobAPPID();
                                String facebook_app_id =adsmodel.getFacebookAppId();

                                AppConfig.facebook_banner_ads_placement_id = adsmodel.getFacebookBannerAdsPlacementId();
                                AppConfig.facebook_interstitial_ads_placement_id = adsmodel.getFacebookInterstitialAdsPlacementId();

                                AppConfig.AdColony_APP_ID= adsmodel.getAdColonyAppId();
                                AppConfig.AdColony_BANNER_ZONE_ID= adsmodel.getAdColonyBannerZoneId();
                                AppConfig.AdColony_INTERSTITIAL_ZONE_ID= adsmodel.getAdColonyInterstitialZoneId();

                                AppConfig.Unity_Game_ID= adsmodel.getUnityGameId();
                                AppConfig.Unity_Banner_ID= adsmodel.getUnityBannerId();
                                AppConfig.Unity_rewardedVideo_ID = adsmodel.getUnityInterstitialId();

                                AppConfig.Custom_Banner_url=adsmodel.getCustomBannerUrl();
                                AppConfig.Custom_Banner_click_url_type = Integer.parseInt(adsmodel.getCustomBannerClickUrlType());
                                AppConfig.Custom_Banner_click_url=adsmodel.getCustomBannerClickUrl();
                                AppConfig.Custom_Interstitial_url=adsmodel.getCustomInterstitialUrl();
                                AppConfig.Custom_Interstitial_click_url_type= Integer.parseInt(adsmodel.getCustomInterstitialClickUrlType());
                                AppConfig.Custom_Interstitial_click_url=adsmodel.getCustomInterstitialClickUrl();

                                AppConfig.applovin_sdk_key=adsmodel.getApplovinSdkKey();
                                AppConfig.applovin_apiKey=adsmodel.getApplovinApiKey();
                                AppConfig.applovin_Banner_ID=adsmodel.getApplovinBannerID();
                                AppConfig.applovin_Interstitial_ID=adsmodel.getApplovinInterstitialID();
                                AppConfig.ironSource_app_key=adsmodel.getIronSourceAppKey();

                                FacebookSdk.setApplicationId(facebook_app_id);
                                StartAppSDK.init(SplashScreen.this, StartApp_App_ID, false);

                                UpdateModel updateModel = response.body().getUpdate();

                                AppConfig.adsCenter = response.body().getAdscenter();

                                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                apiService.deviceslog(
                                        Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)
                                        ,date
                                        ,currentTime
                                ).enqueue(new Callback<MessageResponse>( ) {
                                    @Override
                                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                        if (Objects.equals(skipModel.getIsgo( ), "false")){
                                            openapp();
                                            // startActivity(new Intent( SplashScreen.this, WelcomPage.class ).putExtra("text",skipModel.getText()));
                                        }else {
                                            getupdate(updateModel);

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                                        Toast.makeText(SplashScreen.this, t.getMessage()+"  اعد الدخول مجددا هناك مشكلة  ", Toast.LENGTH_SHORT).show( );
                                    }
                                });


                            }
                            else {
                                LoadADS();
                            }
                        }

                        @Override
                        public void onFailure(Call<AdsupdateResponse> call, Throwable t) {
                            LoadADS();
                        }
                    });




                }


            }

            @Override
            public void onFailure(Call<SkipModel> call, Throwable t) {
                LoadADS();
            }
        });



    }

    private void getupdate(UpdateModel updateModel) {


        latestAPKVersionName =updateModel.getLatestAPKVersionName();
        latestAPKVersionCode = updateModel.getLatestAPKVersionCode();
        apkFileUrl = updateModel.getaPKFileURL();
        whatsNewOnLatestApk = updateModel.getWhatsNewOnLatestAPK();
        updateSkipable = updateModel.getUpdateSkipable();
        updateType = updateModel.getUpdateType();
        googleplayAppUpdateType =updateModel.getGoogleplayAppUpdateType();


        Log.d("TAG", "getupdate: "+latestAPKVersionName);
        Log.d("AG","getupdate: "+apkFileUrl);
        Log.d("G","getupdate: "+updateSkipable);
        Log.d("TAG", "getupdate: "+updateType);

        String whatsNew = whatsNewOnLatestApk.replace(",", "\n").trim();

        int version = getVersionCode(SplashScreen.this);
        int latestVersionCode;
        try{
            latestVersionCode = Integer.parseInt(latestAPKVersionCode);
        } catch (NumberFormatException e) {
            latestVersionCode = 1;
        }


        if(latestVersionCode > version) {
            if(updateType == 2) {
                appUpdateManager = AppUpdateManagerFactory.create(SplashScreen.this);
                Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
                appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                    if(googleplayAppUpdateType == 0) {
                        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                            try {
                                appUpdateManager.startUpdateFlowForResult(
                                        appUpdateInfo,
                                        AppUpdateType.FLEXIBLE,
                                        this,
                                        15);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }

                            InstallStateUpdatedListener listener = state -> {
                                if (state.installStatus() == InstallStatus.DOWNLOADED) {
                                    Toast.makeText(this, "هناك تحديث جديد", Toast.LENGTH_SHORT).show( );
                                }
                            };
                        }
                    } else if(googleplayAppUpdateType == 1) {
                        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                            try {
                                appUpdateManager.startUpdateFlowForResult(
                                        appUpdateInfo,
                                        AppUpdateType.IMMEDIATE,
                                        this,
                                        15);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } else {
                MaterialDialog mDialog = new MaterialDialog.Builder(SplashScreen.this)
                        .setTitle("تحديث أخر نسخة "+latestAPKVersionName)
                        .setMessage(whatsNew)
                        .setCancelable(false)
                        .setNegativeButton(updateSkipable==0?"خروج":"متابعة", R.drawable.ic_baseline_exit, (dialogInterface, which) -> {
                            if(updateSkipable == 0) { //NO
                                finish();
                            } else if(updateSkipable == 1) { //YES
                                dialogInterface.dismiss();
                                LoadUser();
                            }
                        })
                        .setPositiveButton("تحديث !", R.drawable.ic_baseline_exit, (dialogInterface, which) -> {
                            if(updateType == 0) {
                                Intent intent = new Intent(SplashScreen.this, InAppUpdate.class);
                                intent.putExtra("Update_Title", "Update "+latestAPKVersionName);
                                intent.putExtra("Whats_new_on_latest_APK", whatsNewOnLatestApk);
                                intent.putExtra("APK_File_URL", apkFileUrl);
                                startActivity(intent);
                            } else if(updateType == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(apkFileUrl));
                                startActivity(intent);
                            }
                        })
                        .build();
                mDialog.show();
            }
        } else {
            LoadUser();
        }

    }


    private static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("YourClass", "Package name not found", e);
            return -1; // Handle the error as needed
        }
    }


    private void LoadUser() {


        if (authManager.getUserInfo().getId() > 0){
           LoadData();

        }else {
            Intent intent = new Intent(SplashScreen.this, LoginTojcartoon.class);
            startActivity(intent);
            finish();
        }


    }

    private void LoadData() {

        OneSignal.initWithContext(SplashScreen.this);
        OneSignal.setAppId("09f55b17-7d2f-4623-9c74-ecfdb21dd9c3");
        OneSignal.setExternalUserId(String.valueOf(authManager.getUserInfo().getId()));

        Log.d("TAG", "LoadData: "+AppConfig.url);

        openapp();
    }

    void openapp(){
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveNotification() {
        SharedPreferences sharedPreferences = getSharedPreferences("Notificatin_Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Config", notificationData);
        editor.apply();
    }

    static  public HashMap<String, String> headers (){

        HashMap<String, String> headerssss =  new HashMap<String, String>();
        headerssss.put("Accept","application/json");
        headerssss.put("User-Agent","Jcartoon");

        return  headerssss;
    }




    private PackageInfo pInfo;
    public int getVersionCode() {
        pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {

            Log.i("MYLOG", "NameNotFoundException: "+e.getMessage());
        }
        return pInfo.versionCode;
    }

    @Nullable
    static  public  ApplicationInfo provideApplicationInfo(Application application){
        ApplicationInfo restrictPackageInfo = null;
        final PackageManager pm = application.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals("com.guoshi.httpcanary") ||
                    packageInfo.packageName.equals("org.blokada.alarm.dnschanger") ||
                    packageInfo.packageName.equals("com.adguard.android.contentblocker") ||
                    packageInfo.packageName.equals("com.protectstar.adblocker") ||
                    packageInfo.packageName.equals("tech.httptoolkit.android") ||
                    packageInfo.packageName.equals("tech.httptoolkit.android.v1") ||
                    packageInfo.packageName.equals("com.guoshi.httpcanary.premium") ||
                    packageInfo.packageName.equals("com.hsv.freeadblockerbrowser") ||
                    packageInfo.packageName.equals("s.sdownload.adblockerultimatebrowser") ||
                    packageInfo.packageName.equals("com.egorovandreyrm.pcapremote") ||
                    packageInfo.packageName.equals("com.packagesniffer.frtparlak") ||
                    packageInfo.packageName.equals("jp.co.taosoftware.android.packetcapture") ||
                    packageInfo.packageName.equals("app.greyshirts.sslcapture") ||
                    packageInfo.packageName.equals("com.minhui.networkcapture.pro") ||
                    packageInfo.packageName.equals("com.minhui.networkcapture") ||
                    packageInfo.packageName.equals("com.evbadroid.wicapdemo") ||
                    packageInfo.packageName.equals("com.evbadroid.wicap") ||
                    packageInfo.packageName.equals("com.luckypatchers.luckypatcherinstaller") ||
                    packageInfo.packageName.equals("ru.UbLBBRLf.jSziIaUjL") ||
                    packageInfo.packageName.equals("com.emanuelef.remote_capture") ||
                    packageInfo.packageName.equals("com.minhui.wifianalyzer") ||
                    packageInfo.packageName.equals("com.evbadroid.proxymon")
            ) {
                restrictPackageInfo = packageInfo;
            }
        }

        return restrictPackageInfo;
    }




}