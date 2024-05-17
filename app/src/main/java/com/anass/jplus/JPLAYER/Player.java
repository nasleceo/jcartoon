package com.anass.jplus.JPLAYER;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;
import static com.anass.jplus.Config.Constants.AUTOPLAY;
import static com.anass.jplus.Config.Constants.EXTENTIONS;
import static com.anass.jplus.Config.Constants.SOFTWARE_EXTENTIONS;
import static com.anass.jplus.Constant.AppConfig.adType;
import static com.anass.jplus.JPLAYER.WebPlayer.hideSystemPlayerUi;
import static com.anass.jplus.SplashScreen.provideApplicationInfo;
import static com.anass.jplus.SplashScreen.provideSnifferCheck;


import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Activities.ShowInfoActivity;
import com.anass.jplus.Adapters.SideEpeAdapter;
import com.anass.jplus.Config.getComicsnextback;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.bumptech.glide.Glide;
import com.dooo.stream.DoooStream;
import com.dooo.stream.Model.StreamList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PictureInPictureParams;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Rational;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Config.HelperUtils;
import com.anass.jplus.Config.MediaHelper;
import com.anass.jplus.Config.TinyDB;
import com.anass.jplus.Config.Yts;
import com.anass.jplus.Models.YTStreamList;
import com.anass.jplus.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anass.jplus.DB.resume_content.ResumeContent;
import com.anass.jplus.DB.resume_content.ResumeContentDatabase;

import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.github.se_bastiaan.torrentstream.StreamStatus;
import com.github.se_bastiaan.torrentstream.Torrent;
import com.github.se_bastiaan.torrentstream.TorrentOptions;
import com.github.se_bastiaan.torrentstreamserver.TorrentServerListener;
import com.github.se_bastiaan.torrentstreamserver.TorrentStreamNotInitializedException;
import com.github.se_bastiaan.torrentstreamserver.TorrentStreamServer;
import com.github.vkay94.dtpv.DoubleTapPlayerView;
import com.github.vkay94.dtpv.youtube.YouTubeOverlay;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.sidesheet.SideSheetBehavior;
import com.google.android.material.sidesheet.SideSheetCallback;
import com.google.android.material.sidesheet.SideSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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


import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.signal.DialogPropertiesSignal;
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import abak.tr.com.boxedverticalseekbar.BoxedVertical;
import es.dmoral.toasty.Toasty;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import me.ertugrul.lib.Forward;
import me.ertugrul.lib.OnAnimationStartListener;
import me.ertugrul.lib.Rewind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Player extends AppCompatActivity {
    Context context = this;
    private DoubleTapPlayerView playerView;
    private ExoPlayer simpleExoPlayer;

    YouTubeOverlay youtube_overlay;

    private DefaultTrackSelector trackSelector;

    int skip_available;
    String intro_start;
    String intro_end;
    Button Skip_Intro_btn;

    int Current_List_Position = 0;

    Button Play_Next_btn;

    String Next_Ep_Avilable;

    private PictureInPictureParams.Builder pictureParms;
    Boolean contentLoaded = false;

    static ProgressDialog progressDialog;

    int resumeContentID;

    long resumePosition = 0;

    String userData = null;
    int userId;

    ResumeContentDatabase db;

    PowerManager.WakeLock wakeLock;

    int maxBrightness;

    Map<String, String> defaultRequestProperties = new HashMap<>( );
    String userAgent = "";

    List<StreamList> multiqualityListWeb = new ArrayList<>( );
    WebView webView;

    private String bGljZW5zZV9jb2Rl;

    private TorrentStreamServer torrentStreamServer;

    TinyDB tinyDB;

    CartoonModel cartoon;
    EpesodModel episode;

    private SharedPreferences sp;
    public int NameAndEpesoed;

    private long currentPage;
    private long allduration;

    AuthManager authManager;
    ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        hideSystemPlayerUi(this, true, 0);
        setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Player:No Sleep");
        wakeLock.acquire(300 * 60 * 1000L /*300 minutes*/);

        maxBrightness = getMaxBrightness(this, 1000);

        loadConfig( );


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("أرجو الإنتظار");
        progressDialog.setCancelable(true);


        setContentView(R.layout.activity_player_main);


        tinyDB = new TinyDB(this);
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        userAgent = WebSettings.getDefaultUserAgent(context);

        playerView = findViewById(R.id.player_view);

        youtube_overlay = findViewById(R.id.ytOverlay);
        youtube_overlay.performListener(new YouTubeOverlay.PerformListener( ) {
            @Override
            public void onAnimationStart() {
                youtube_overlay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd() {
                youtube_overlay.setVisibility(View.GONE);
            }

            @SuppressLint("WrongConstant")
            @Nullable
            @Override
            public Boolean shouldForward(@NotNull com.google.android.exoplayer2.Player player, @NotNull DoubleTapPlayerView doubleTapPlayerView, float v) {
                if (player.getPlaybackState( ) == PlaybackState.STATE_ERROR ||
                        player.getPlaybackState( ) == PlaybackState.STATE_NONE ||
                        player.getPlaybackState( ) == PlaybackState.STATE_STOPPED) {

                    playerView.cancelInDoubleTapMode( );
                    return null;
                }

                if (player.getCurrentPosition( ) > 500 && v < playerView.getWidth( ) * 0.35)
                    return false;

                if (player.getCurrentPosition( ) < player.getDuration( ) && v > playerView.getWidth( ) * 0.65)
                    return true;

                return null;
            }
        });




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pictureParms = new PictureInPictureParams.Builder( );
        }

        TextView contentFirstName = findViewById(R.id.contentFirstName);
        LinearLayout epelist = findViewById(R.id.epelist);
        TextView contentSecondName = findViewById(R.id.contentSecondName);
        contentFirstName.setText(cartoon.getTitle( ));

        if (cartoon.getPlace( ).contains("movie")) {
            epelist.setVisibility(View.GONE);
            contentSecondName.setVisibility(View.GONE);

        } else {
            epelist.setVisibility(View.GONE);
            contentSecondName.setText(" الحلقة : " + episode.getLebel( ));

        }

        if (episode.getMessage() != null ){
            findViewById(R.id.warnerliner).setVisibility(View.VISIBLE);
            TextView textView = findViewById(R.id.warner);
            textView.setText(episode.getMessage());
        }


        if (db.resumeContentDao().getResumeContentid(cartoon.getId()) == 0) {
            db.resumeContentDao().insert(new ResumeContent(0, cartoon.getId(),
                    episode.getSource(),
                    episode.getUrl(),
                   cartoon.getPoster(),
                   cartoon.getTitle() ,
                    cartoon.getYear() != null ? cartoon.getYear() : 2001,
                    0,
                    0,
                    cartoon.getCountentType()));
            resumeContentID = db.resumeContentDao().getResumeContentid(cartoon.getId());
        } else {
            resumeContentID = db.resumeContentDao().getResumeContentid(cartoon.getId());
            db.resumeContentDao().updateSource(episode.getSource(), episode.getUrl(), cartoon.getCountentType(), resumeContentID);
        }


        if (authManager.getUserInfo().getId() > 0) {
            apiService.watchlog(
                    cartoon.getId(),
                    cartoon.getCountentTypeint(),
                    episode.getId(),
                    String.valueOf(authManager.getUserInfo().getId())
            ).enqueue(new Callback<MessageResponse>( ) {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {

                }
            });
        } else {
            apiService.watchlog(cartoon.getId(),cartoon.getCountentTypeint(),episode.getId(),Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)).enqueue(new Callback<MessageResponse>( ) {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {

                }
            });
        }

        Prepare_Source(episode.getSource( ), episode.getUrl( ));

        skip_available = episode.getSkipAvailable( );
        intro_start = episode.getIntroStart( );
        intro_end = episode.getIntroEnd( );

        if (cartoon.getCountentType( ) != null || getIntent( ).getExtras( ).getString("Current_List_Position") != null || getIntent( ).getExtras( ).getString("Next_Ep_Avilable") != null) {

            Current_List_Position = getIntent( ).getExtras( ).getInt("Current_List_Position");
            Next_Ep_Avilable = getIntent( ).getExtras( ).getString("Next_Ep_Avilable");

        }



        LinearLayout img_full_scr = findViewById(R.id.img_full_scr);
        img_full_scr.setOnClickListener(view -> {
            if (playerView.getResizeMode( ) == AspectRatioFrameLayout.RESIZE_MODE_FILL) {
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            } else if (playerView.getResizeMode( ) == AspectRatioFrameLayout.RESIZE_MODE_FIT) {
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            } else {
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            }
        });

        LinearLayout pipcid = findViewById(R.id.pipcid);
        pipcid.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                checkpipmode( );
            }
        });

/*
        epelist.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                showSideSheet( );

            }
        });
*/


        ImageView Back_btn_img = findViewById(R.id.Back_btn_img);
        Back_btn_img.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                isBackPressed = true;
                sp.edit().putInt("currentPage"+ NameAndEpesoed, (int) simpleExoPlayer.getCurrentPosition()).apply();
                sp.edit().putInt("allduration"+ NameAndEpesoed, (int) simpleExoPlayer.getDuration()).apply();

                releasePlayer( );
                handler.removeCallbacks(runnable);
                finish( );
            }
        });

        Skip_Intro_btn = findViewById(R.id.Skip_Intro_btn);
        Skip_Intro_btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                simpleExoPlayer.seekTo(Get_mil_From_Time(intro_end));
            }
        });

        handler.post(runnable);


        Play_Next_btn = findViewById(R.id.Play_Next_btn);
        Play_Next_btn.setVisibility(View.GONE);
        Play_Next_btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (cartoon.getCountentType( ).equals("tv")) {
                    if (Next_Ep_Avilable.equals("Yes")) {
                        Play_Next_btn.setText("Playing Now");


                    }
                }
            }
        });

        // No Internet Dialog: Signal
        NoInternetDialogSignal.Builder builder = new NoInternetDialogSignal.Builder(
                this,
                getLifecycle( )
        );
        DialogPropertiesSignal properties = builder.getDialogProperties( );
        properties.setConnectionCallback(new ConnectionCallback( ) { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });
        properties.setCancelable(true); // Optional
        properties.setNoInternetConnectionTitle("No Internet"); // Optional
        properties.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        properties.setShowInternetOnButtons(true); // Optional
        properties.setPleaseTurnOnText("Please turn on"); // Optional
        properties.setWifiOnButtonText("Wifi"); // Optional
        properties.setMobileDataOnButtonText("Mobile data"); // Optional

        properties.setOnAirplaneModeTitle("No Internet"); // Optional
        properties.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        properties.setPleaseTurnOffText("Please turn off"); // Optional
        properties.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        properties.setShowAirplaneModeOffButtons(true); // Optional
        builder.build( );


        BoxedVertical brightness = findViewById(R.id.brightness);
        ConstraintLayout brightnessLayout = findViewById(R.id.brightnessLayout);
        brightness.setMax(maxBrightness);
        brightness.setValue(getBrightness(this));
        setBrightness(Player.this, getBrightness(this));
        brightness.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener( ) {
            @Override
            public void onPointsChanged(BoxedVertical boxedPoints, int points) {
                setBrightness(Player.this, boxedPoints.getValue( ));
            }

            @Override
            public void onStartTrackingTouch(BoxedVertical boxedPoints) {

            }

            @Override
            public void onStopTrackingTouch(BoxedVertical boxedPoints) {

            }
        });

        BoxedVertical volume = findViewById(R.id.volume);
        ConstraintLayout volumeLayout = findViewById(R.id.volumeLayout);

        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeLevel = am.getStreamVolume(AudioManager.STREAM_MUSIC);

        volume.setMax(maxVolume);
        volume.setValue(volumeLevel);
        volume.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener( ) {
            @Override
            public void onPointsChanged(BoxedVertical boxedPoints, int points) {
                am.setStreamVolume(AudioManager.STREAM_MUSIC, boxedPoints.getValue( ), 0);
            }

            @Override
            public void onStartTrackingTouch(BoxedVertical boxedPoints) {

            }

            @Override
            public void onStopTrackingTouch(BoxedVertical boxedPoints) {

            }
        });
    }

    public void showSideSheet() {

        SideSheetDialog sideSheetDialog = new SideSheetDialog(this);
        sideSheetDialog.getBehavior( ).addCallback(new SideSheetCallback( ) {
            @Override
            public void onStateChanged(@NonNull View sheet, int newState) {
                if (newState == SideSheetBehavior.STATE_DRAGGING) {
                    sideSheetDialog.getBehavior( ).setState(SideSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View sheet, float slideOffset) {

            }
        });

        View inflater = LayoutInflater.from(this).inflate(R.layout.episodeside, null);
        ImageButton btnClose = inflater.findViewById(R.id.btn_close);
        RecyclerView epelist_rv = inflater.findViewById(R.id.epelist_rv);
        btnClose.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                sideSheetDialog.dismiss( );
            }
        });

        epelist_rv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        epelist_rv.setAdapter(new SideEpeAdapter(this,cartoon.getTitle(),Current_List_Position));



        sideSheetDialog.setCancelable(false);
        sideSheetDialog.setCanceledOnTouchOutside(true);
        sideSheetDialog.setContentView(inflater);
        sideSheetDialog.show( );


    }

    int getMaxBrightness(Context context, int defaultValue) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            Field[] fields = powerManager.getClass( ).getDeclaredFields( );
            for (Field field : fields) {
                if (field.getName( ).equals("BRIGHTNESS_ON")) {
                    field.setAccessible(true);
                    try {
                        return (int) field.get(powerManager);
                    } catch (IllegalAccessException e) {
                        return defaultValue;
                    }
                }
            }
        }
        return defaultValue;
    }

    public void checkpipmode() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Rational asperrat = new Rational(playerView.getWidth( ), playerView.getHeight( ));
            pictureParms.setAspectRatio(asperrat).build( );
            enterPictureInPictureMode(pictureParms.build( ));

        } else {

            android.util.Log.d("TAG", "checkpipmode:  Doesnt suppoert");
        }


    }

    public void setBrightness(Context context, int brightness) {
        //ContentResolver cResolver = context.getContentResolver();
        //Settings.System.putInt(cResolver,  Settings.System.SCREEN_BRIGHTNESS,brightness);

        WindowManager.LayoutParams layout = getWindow( ).getAttributes( );
        layout.screenBrightness = (float) brightness / maxBrightness;
        getWindow( ).setAttributes(layout);

    }

    public static int getBrightness(Context context) {
        ContentResolver cResolver = context.getContentResolver( );
        try {
            return Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            return 0;
        }
    }


    private Handler handler = new Handler( );
    private Runnable runnable = new Runnable( ) {
        @Override
        public void run() {
            if (simpleExoPlayer != null) {
                if (simpleExoPlayer.isPlaying( )) {
                    long apprxDuration = simpleExoPlayer.getDuration( ) - 5000;
                    if (simpleExoPlayer.getCurrentPosition( ) > apprxDuration) {
                        db.resumeContentDao().delete(resumeContentID);
                    } else {
                        db.resumeContentDao().update(simpleExoPlayer.getCurrentPosition(), resumeContentID);
                    }
                }
            }

           /* //Skip Feature
            if (skip_available == 1) {
                if (simpleExoPlayer != null) {
                    if (intro_start.equals("") || intro_start.equals("0") || intro_start.equals(null) || intro_end.equals("") || intro_end.equals("0") || intro_end.equals(null)) {
                        Skip_Intro_btn.setVisibility(View.GONE);
                    } else {
                        if (Get_mil_From_Time(intro_start) < simpleExoPlayer.getContentPosition( ) && Get_mil_From_Time(intro_end) > simpleExoPlayer.getContentPosition( )) {
                            Skip_Intro_btn.setVisibility(View.VISIBLE);
                        } else {
                            Skip_Intro_btn.setVisibility(View.GONE);
                        }
                    }
                }

            } else {
                Skip_Intro_btn.setVisibility(View.GONE);
            }
*/

            ////
            if (!multiqualityListWeb.isEmpty( )) {
                multiQualityDialog(multiqualityListWeb);
                webView.destroy( );
                multiqualityListWeb.clear( );
            }

            // Repeat every 1 seconds
            handler.postDelayed(runnable, 1000);
        }
    };

    static public long Get_mil_From_Time(String Time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:SS");
        Date parsed_date = null;
        try {
            parsed_date = format.parse(Time);
        } catch (ParseException e) {
            e.printStackTrace( );
        }
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        SimpleDateFormat min = new SimpleDateFormat("mm");
        SimpleDateFormat sec = new SimpleDateFormat("SS");

        String Hour = hour.format(parsed_date);
        String Min = min.format(parsed_date);
        String Sec = sec.format(parsed_date);


        long m_hour = 0;
        long m_min = 0;
        long m_sec = 0;
        if (!Hour.equals("00")) {
            m_hour = Integer.parseInt(Hour) * 3600000;
        }

        if (!Min.equals("00")) {
            m_min = Integer.parseInt(Min) * 60000;
        }

        if (!Sec.equals("00")) {
            m_sec = Integer.parseInt(Sec) * 1000;
        }

        Long F_mil = m_hour + m_min + m_sec;

        return F_mil;
    }

    public static InetAddress getIpAddress(Context context) throws UnknownHostException {
        WifiManager wifiMgr = (WifiManager) context.getApplicationContext( ).getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo( );
        int ip = wifiInfo.getIpAddress( );

        if (ip == 0) {
            return null;
        } else {
            byte[] ipAddress = convertIpAddress(ip);
            return InetAddress.getByAddress(ipAddress);
        }
    }

    private static byte[] convertIpAddress(int ip) {
        return new byte[]{
                (byte) (ip & 0xFF),
                (byte) ((ip >> 8) & 0xFF),
                (byte) ((ip >> 16) & 0xFF),
                (byte) ((ip >> 24) & 0xFF)
        };
    }

    void Prepare_Source(String source, String url) {
        Log.d("test", source + " " + url);

        DataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory( )
                .setUserAgent(userAgent)
                .setKeepPostFor302Redirects(true)
                .setAllowCrossProtocolRedirects(true)
                .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
                .setDefaultRequestProperties(defaultRequestProperties);
        DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(context, httpDataSourceFactory);


        if (source.equalsIgnoreCase("JCARTOON")) {
            MediaItem mediaItem = new MediaItem.Builder( )
                    .setMimeType(MimeTypes.APPLICATION_MP4)
                    .setUri(url)
                    .build( );
            MediaSource progressiveMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem);
            initializePlayer(progressiveMediaSource);
        } else if (source.equalsIgnoreCase("mkv")) {
            MediaItem mediaItem = new MediaItem.Builder( )
                    .setMimeType(MimeTypes.APPLICATION_MATROSKA)
                    .setUri(url)
                    .build( );
            MediaSource progressiveMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem);
            initializePlayer(progressiveMediaSource);
        } else if (source.equals("Youtube")) {
            progressDialog.show( );
            Yts.getlinks(this, url, new Yts.VolleyCallback( ) {
                @Override
                public void onSuccess(List<YTStreamList> result) {
                    ytMultipleQualityDialog(Player.this, result);
                }

                @Override
                public void onError(VolleyError error) {
                    isBackPressed = true;
                    releasePlayer( );
                    handler.removeCallbacks(runnable);
                    finish( );
                }
            });
        } else if (source.equals("Torrent")) {
            progressDialog.show( );
            TorrentOptions torrentOptions = new TorrentOptions.Builder( )
                    .saveLocation(getFilesDir( ))
                    .removeFilesAfterStop(true)
                    .build( );
            String ipAddress = "127.0.0.1";
            try {
                InetAddress inetAddress = getIpAddress(this);
                if (inetAddress != null) {
                    ipAddress = inetAddress.getHostAddress( );
                }
            } catch (UnknownHostException e) {
                e.printStackTrace( );
            }

            torrentStreamServer = TorrentStreamServer.getInstance( );
            torrentStreamServer.setTorrentOptions(torrentOptions);
            torrentStreamServer.setServerHost(ipAddress);
            torrentStreamServer.setServerPort(8080);
            torrentStreamServer.startTorrentStream( );
            torrentStreamServer.addListener(new TorrentServerListener( ) {
                @Override
                public void onServerReady(String url) {
                    progressDialog.dismiss( );
                    List<StreamList> streamList = new ArrayList<>( );
                    streamList.add(new StreamList("Normal", "mp4", url, "", ""));
                    multiQualityDialog(streamList);
                }

                @Override
                public void onStreamPrepared(Torrent torrent) {
                }

                @Override
                public void onStreamStarted(Torrent torrent) {

                }

                @Override
                public void onStreamError(Torrent torrent, Exception e) {
                    progressDialog.dismiss( );
                    finishPlayer( );
                }

                @Override
                public void onStreamReady(Torrent torrent) {

                }

                @Override
                public void onStreamProgress(Torrent torrent, StreamStatus status) {

                }

                @Override
                public void onStreamStopped() {

                }
            });

            try {
                torrentStreamServer.startStream(url);
            } catch (IOException | TorrentStreamNotInitializedException e) {
                e.printStackTrace( );
            }
        } else if (source.equals("Dailymotion") || source.equals("DoodStream") || source.equals("Dropbox") || (source.equals("Fembed"))
                || source.equals("Facebook") || source.equals("GogoAnime") || source.equals("GoogleDrive") || source.equals("MediaShore")
                || source.equals("MixDrop") || source.equals("Onedrive") || source.equals("OKru") || source.equals("StreamTape")
                || source.equals("Twitter") || source.equals("Voesx") || source.equals("VK")
                || source.equals("Voot") || source.equals("Vudeo") || source.equals("Vimeo") || source.equals("YoutubeLive")
                || source.equals("Yandex") || source.equals("Zoro")) {
            progressDialog.show( );

            new DoooStream(context, bGljZW5zZV9jb2Rl, new DoooStream.OnInitialize( ) {
                @Override
                public void onSuccess(DoooStream doooStream) {
                    doooStream.find(source, url, false, new DoooStream.OnTaskCompleted( ) {
                        @Override
                        public void onSuccess(List<StreamList> streamList) {
                            multiQualityDialog(streamList);
                        }

                        @Override
                        public void onError() {
                            progressDialog.dismiss( );
                            finishPlayer( );
                        }
                    });
                }

                @Override
                public void onError(RuntimeException e) {
                    progressDialog.dismiss( );
                    finishPlayer( );
                }
            });
        } else if (source.equals("Streamwish")) {

            String finalurl = url;

            if (url.contains("egtpgrvh")) {
                finalurl = url.replace("https://egtpgrvh.sbs", "https://streamwish.to");

            }

            progressDialog.show( );
            webView = new WebView(context);
            webView.setWebViewClient(new WebViewClient( ) {
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    if (request.getUrl( ).toString( ).toLowerCase( ).contains("master.m3u8")) {
                        if (multiqualityListWeb.isEmpty( )) {
                            multiqualityListWeb.add(new StreamList("Auto", "m3u8", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                        }
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".m3u8")) {
                        if (multiqualityListWeb.isEmpty( )) {
                            multiqualityListWeb.add(new StreamList("Auto", "m3u8", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                        }
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mp4") && !request.getUrl( ).toString( ).toLowerCase( ).contains("blank.mp4")) {
                        if (multiqualityListWeb.isEmpty( )) {
                            multiqualityListWeb.add(new StreamList("Auto", "mp4", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                        }
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mp4")) {
                        if (multiqualityListWeb.isEmpty( )) {
                            multiqualityListWeb.add(new StreamList("Auto", "mp4", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                        }
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mkv")) {
                        if (multiqualityListWeb.isEmpty( )) {
                            multiqualityListWeb.add(new StreamList("Auto", "mkv", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                        }
                    } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".ts")) {
                        if (multiqualityListWeb.isEmpty( )) {
                            multiqualityListWeb.add(new StreamList("Auto", "ts", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                        }
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

        } else if (source.equals("vidpro")) {


            Getlinkofwebview(url);

        } else {
            finishPlayer( );
        }

    }

    public void Getlinkofwebview(String url) {

        progressDialog.show( );
        webView = new WebView(context);
        webView.setWebViewClient(new WebViewClient( ) {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (request.getUrl( ).toString( ).toLowerCase( ).contains("master.m3u8")) {
                    if (multiqualityListWeb.isEmpty( )) {
                        multiqualityListWeb.add(new StreamList("Auto", "m3u8", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                    }
                } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".m3u8")) {
                    if (multiqualityListWeb.isEmpty( )) {
                        multiqualityListWeb.add(new StreamList("Auto", "m3u8", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                    }
                } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mp4") && !request.getUrl( ).toString( ).toLowerCase( ).contains("blank.mp4")) {
                    if (multiqualityListWeb.isEmpty( )) {
                        multiqualityListWeb.add(new StreamList("Auto", "mp4", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                    }
                } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mp4")) {
                    if (multiqualityListWeb.isEmpty( )) {
                        multiqualityListWeb.add(new StreamList("Auto", "mp4", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                    }
                } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".mkv")) {
                    if (multiqualityListWeb.isEmpty( )) {
                        multiqualityListWeb.add(new StreamList("Auto", "mkv", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                    }
                } else if (request.getUrl( ).toString( ).toLowerCase( ).contains(".ts")) {
                    if (multiqualityListWeb.isEmpty( )) {
                        multiqualityListWeb.add(new StreamList("Auto", "ts", request.getUrl( ).toString( ), request.getUrl( ).toString( ), ""));
                    }
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
    }


    public void multiQualityDialog(List<StreamList> streamList) {

        if (streamList.isEmpty( )) {
            finishPlayer( );
        } else if (streamList.size( ) == 1) {
            multiQualityPlayer(streamList.get(0).getExtension( ), streamList.get(0).getUrl( ), streamList.get(0).getReferer( ), streamList.get(0).getCookie( ));
            progressDialog.hide( );
        } else {
            streamList.size( );
            CharSequence[] name = new CharSequence[streamList.size( )];
            for (int i = 0; i < streamList.size( ); i++) {
                name[i] = streamList.get(i).getQuality( );
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Quality!")
                    .setCancelable(false)
                    .setItems(name, (dialog, which) -> {
                        multiQualityPlayer(streamList.get(which).getExtension( ), streamList.get(which).getUrl( ), streamList.get(which).getReferer( ), streamList.get(which).getCookie( ));
                    })
                    .setPositiveButton("Close", (dialog, which) -> {
                        isBackPressed = true;
                        releasePlayer( );
                        handler.removeCallbacks(runnable);
                        finish( );
                    });
            progressDialog.hide( );
            builder.show( );
        }
    }

    public void multiQualityPlayer(String extension, String Url, String referer, String cookie) {
        Log.d("test",  Url );

        if (!referer.equals("")) {
            defaultRequestProperties.put("Referer", referer);
        }
        if (!cookie.equals("")) {
            defaultRequestProperties.put("Cookie", cookie);
        }


        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory( )
                .setUserAgent(userAgent)
                .setKeepPostFor302Redirects(true)
                .setAllowCrossProtocolRedirects(true)
                .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
                .setDefaultRequestProperties(defaultRequestProperties);


        if (extension.equalsIgnoreCase("m3u8")) {
            MediaItem mediaItem = new MediaItem.Builder( )
                    .setMimeType(MimeTypes.APPLICATION_M3U8)
                    .setUri(Url)
                    .build( );
            MediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .setAllowChunklessPreparation(true)
                    .createMediaSource(mediaItem);
            initializePlayer(hlsMediaSource);
        } else if (extension.equalsIgnoreCase("dash")) {
            MediaItem mediaItem = new MediaItem.Builder( )
                    .setMimeType(MimeTypes.APPLICATION_MPD)
                    .setUri(Url)
                    .build( );
            MediaSource dashMediaSource = new DashMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem);
            initializePlayer(dashMediaSource);
        } else if (extension.equalsIgnoreCase("mp4")) {
            MediaItem mediaItem = new MediaItem.Builder( )
                    .setMimeType(MimeTypes.APPLICATION_MP4)
                    .setUri(Url)
                    .build( );
            MediaSource progressiveMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem);
            initializePlayer(progressiveMediaSource);
        } else if (extension.equalsIgnoreCase("mkv")) {
            MediaItem mediaItem = new MediaItem.Builder( )
                    .setMimeType(MimeTypes.APPLICATION_MATROSKA)
                    .setUri(Url)
                    .build( );
            MediaSource progressiveMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem);
            initializePlayer(progressiveMediaSource);
        } else if (extension.equalsIgnoreCase("Embed")) {
            finishPlayer( );
            finish( );
            /*Intent intent = new Intent(context, EmbedPlayer.class);
            intent.putExtra("url", Url);
            context.startActivity(intent);*/
        }
    }

    void ytMultipleQualityDialog(Context context, List<YTStreamList> list) {
        progressDialog.dismiss( );
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
                .setTitle("Quality!")
                .setCancelable(false)
                .setItems(name, new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Yts.getStreamLinks(context, (String) token[which], (String) vid[which], new Yts.VolleyCallback2( ) {

                            @Override
                            public void onSuccess(String result) {
                                DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory( )
                                        .setUserAgent(userAgent)
                                        .setKeepPostFor302Redirects(true)
                                        .setAllowCrossProtocolRedirects(true)
                                        .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                                        .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
                                        .setDefaultRequestProperties(defaultRequestProperties);
                                MediaItem mediaItem = new MediaItem.Builder( )
                                        .setMimeType(MimeTypes.APPLICATION_MP4)
                                        .setUri(result)
                                        .build( );
                                MediaSource progressiveMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                                        .createMediaSource(mediaItem);
                                initializePlayer(progressiveMediaSource);
                            }

                            @Override
                            public void onError(VolleyError error) {
                            }
                        });
                    }
                })
                .setPositiveButton("Close", (dialog, which) -> {
                    isBackPressed = true;
                    releasePlayer( );
                    handler.removeCallbacks(runnable);
                    finish( );
                });
        builder.show( );
    }

    void initializePlayer(MediaSource mediaSource) {
        ExoTrackSelection.Factory videoTrackSelectionFactory = new
                AdaptiveTrackSelection.Factory( );

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        trackSelector = new
                DefaultTrackSelector(Player.this, videoTrackSelectionFactory);
        RenderersFactory renderersFactory;
        renderersFactory = MediaHelper.buildRenderersFactory(this, sharedPreferences.getBoolean(EXTENTIONS, false)
                , sharedPreferences.getBoolean(SOFTWARE_EXTENTIONS, false));
        simpleExoPlayer = new ExoPlayer.Builder(Player.this)
                .setRenderersFactory(renderersFactory)
                .setTrackSelector(trackSelector)
                .build( );

        simpleExoPlayer.prepare(mediaSource);
        youtube_overlay.player(simpleExoPlayer);

        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);

        if (currentPage == 0){
            simpleExoPlayer.play();

        }else {
            simpleExoPlayer.seekTo(currentPage);
            simpleExoPlayer.play();

        }

        //Player Speed


        LinearLayout playerSpeed = findViewById(R.id.playerSpeed);
        String grpname[] = {"0.5x", "1.0x", "1.5x", "2.0x"};
        float playerSpeedgroup[] = {0.5f, 1f, 1.5f, 2f};
        final int[] selectedGorup = {1};
        final int[] tempSelectedGorup = {1};
        playerSpeed.setOnClickListener(view -> {
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
            //alt_bld.setIcon(R.drawable.ic_baseline_watch);
            alt_bld.setTitle("Select Player Speed");
            alt_bld.setSingleChoiceItems(grpname, selectedGorup[0], new DialogInterface
                            .OnClickListener( ) {
                        public void onClick(DialogInterface dialog, int item) {
                            tempSelectedGorup[0] = item;

                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener( ) {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PlaybackParameters param = new PlaybackParameters(playerSpeedgroup[tempSelectedGorup[0]]);
                            simpleExoPlayer.setPlaybackParameters(param);
                            selectedGorup[0] = tempSelectedGorup[0];
                            dialog.dismiss( );
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener( ) {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss( );
                        }
                    });
            AlertDialog alert = alt_bld.create( );
            alert.show( );
        });


        Forward forward = findViewById(R.id.seek_ffwd);
        Rewind exo_rew = findViewById(R.id.seek_rew);
        forward.setOnAnimationStartListener(new OnAnimationStartListener( ) {
            @Override
            public void onAnimationStart() {
                simpleExoPlayer.seekTo(simpleExoPlayer.getCurrentPosition( ) + 10000);


            }
        });
        exo_rew.setOnAnimationStartListener(new OnAnimationStartListener( ) {
            @Override
            public void onAnimationStart() {
                long num = simpleExoPlayer.getCurrentPosition() - 10000;
                if (num < 0) {

                    simpleExoPlayer.seekTo(0);


                } else {

                    simpleExoPlayer.seekTo(simpleExoPlayer.getCurrentPosition() - 10000);

                }
            }
        });

        simpleExoPlayer.addListener(new com.google.android.exoplayer2.Player.Listener( ) {

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == com.google.android.exoplayer2.Player.STATE_READY) {
                    // Active playback.
                    contentLoaded = true;

                    db.resumeContentDao().updateDuration(simpleExoPlayer.getDuration(), resumeContentID);


                }
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                //PlayBack Error
            }
        });


    }


    private void pausePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(tinyDB.getBoolean(AUTOPLAY));
            simpleExoPlayer.getPlaybackState( );
            simpleExoPlayer.pause( );
        }
    }

    private void startPlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(tinyDB.getBoolean(AUTOPLAY));
            simpleExoPlayer.getPlaybackState( );
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop( );
            simpleExoPlayer = null;
        }
    }

    Boolean isBackPressed = false;



    @Override
    protected void onPause() {
        super.onPause( );
        if (simpleExoPlayer != null) {
            if (simpleExoPlayer.isPlaying( )) {
                long apprxDuration = simpleExoPlayer.getDuration( ) - 5000;
                if (simpleExoPlayer.getCurrentPosition( ) > apprxDuration) {
                   db.resumeContentDao().delete(resumeContentID);
                } else {
                    db.resumeContentDao().update(simpleExoPlayer.getCurrentPosition(), resumeContentID);
                }
            }
        }



    }



    private void loadConfig() {
        db = ResumeContentDatabase.getDbInstance(this.getApplicationContext());

        bGljZW5zZV9jb2Rl = "565455";
        cartoon = (CartoonModel) getIntent( ).getSerializableExtra("cartoon");
        episode = (EpesodModel) getIntent( ).getSerializableExtra("episode");
        sp = getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);
        currentPage = db.resumeContentDao().getResumeContentPosition(cartoon.getId());
       // currentPage = sp.getInt("currentPage"+NameAndEpesoed,1);

    }

    private void finishPlayer() {
        progressDialog.hide( );
        isBackPressed = true;
        releasePlayer( );
        handler.removeCallbacks(runnable);
        finish( );
    }

    @Override
    protected void onResume() {
        super.onResume();

        provideApplicationInfo(getApplication());

        if (provideSnifferCheck != null) {
            Toast.makeText(this, "لا يمكنك تشغيل تطبيقات اللتقاط الشبكة", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop( );
        if (simpleExoPlayer != null){
            simpleExoPlayer.stop( );
        }
        if (wakeLock != null) {
            try {
                wakeLock.release( );
            } catch (Throwable ignored) {
            }
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint( );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isInPictureInPictureMode( )) {
                checkpipmode( );
            } else {
                android.util.Log.d("TAG", "onUserLeaveHint: nu suppert");
            }
        }
    }


    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, @NonNull Configuration newConfig) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
            if (isInPictureInPictureMode) {
                android.util.Log.d("TAG", "onPictureInPictureModeChanged: enter pip");
                LinearLayout bottom_layout = findViewById(R.id.bottom_layout);
                bottom_layout.setVisibility(View.GONE);
                simpleExoPlayer.play( );


                Forward forward = findViewById(R.id.seek_ffwd);
                forward.setVisibility(View.GONE);
                Rewind exo_rew = findViewById(R.id.seek_rew);
                exo_rew.setVisibility(View.GONE);



                RelativeLayout header = findViewById(R.id.header);
                header.setVisibility(View.GONE);


                ConstraintLayout volumeLayout = findViewById(R.id.volumeLayout);
                volumeLayout.setVisibility(View.GONE);
                ConstraintLayout brightnessLayout = findViewById(R.id.brightnessLayout);
                brightnessLayout.setVisibility(View.GONE);

            } else {
                android.util.Log.d("TAG", "onPictureInPictureModeChanged: exited pip");
                LinearLayout bottom_layout = findViewById(R.id.bottom_layout);
                bottom_layout.setVisibility(View.VISIBLE);
                RelativeLayout header = findViewById(R.id.header);
                header.setVisibility(View.VISIBLE);
                Forward forward = findViewById(R.id.seek_ffwd);
                forward.setVisibility(View.VISIBLE);
                Rewind exo_rew = findViewById(R.id.seek_rew);
                exo_rew.setVisibility(View.VISIBLE);

                ConstraintLayout volumeLayout = findViewById(R.id.volumeLayout);
                volumeLayout.setVisibility(View.VISIBLE);
                ConstraintLayout brightnessLayout = findViewById(R.id.brightnessLayout);
                brightnessLayout.setVisibility(View.VISIBLE);
            }


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy( );
        if (simpleExoPlayer != null){
            simpleExoPlayer.stop( );
        }
        if (torrentStreamServer != null) {
            torrentStreamServer.stopTorrentStream( );
        }
    }


}