package com.anass.jplus.Activities.jcartoonroom;

import static com.anass.jplus.Activities.Comments.ShowsnackLogin;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;
import static com.anass.jplus.Config.Constants.EXTENTIONS;
import static com.anass.jplus.Config.Constants.SOFTWARE_EXTENTIONS;
import static com.anass.jplus.JPLAYER.Player.getIpAddress;
import static com.anass.jplus.SplashScreen.provideApplicationInfo;
import static com.anass.jplus.SplashScreen.provideSnifferCheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PictureInPictureParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.anass.jplus.Activities.Comments;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Adapters.RoomAdapter;
import com.anass.jplus.Config.MediaHelper;
import com.anass.jplus.Config.TinyDB;
import com.anass.jplus.Config.Yts;
import com.anass.jplus.DB.resume_content.ResumeContentDatabase;
import com.anass.jplus.Fragments.Jsocity;
import com.anass.jplus.JPLAYER.Player;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.Models.MessageModel;
import com.anass.jplus.Models.RoomModel;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.Models.YTStreamList;
import com.anass.jplus.R;
import com.android.volley.VolleyError;
import com.dooo.stream.DoooStream;
import com.dooo.stream.Model.StreamList;
import com.github.se_bastiaan.torrentstream.StreamStatus;
import com.github.se_bastiaan.torrentstream.Torrent;
import com.github.se_bastiaan.torrentstream.TorrentOptions;
import com.github.se_bastiaan.torrentstreamserver.TorrentServerListener;
import com.github.se_bastiaan.torrentstreamserver.TorrentStreamNotInitializedException;
import com.github.se_bastiaan.torrentstreamserver.TorrentStreamServer;
import com.github.vkay94.dtpv.DoubleTapPlayerView;
import com.github.vkay94.dtpv.youtube.YouTubeOverlay;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.signal.DialogPropertiesSignal;
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abak.tr.com.boxedverticalseekbar.BoxedVertical;
import me.ertugrul.lib.Forward;
import me.ertugrul.lib.OnAnimationStartListener;
import me.ertugrul.lib.Rewind;

public class WatchRoom extends AppCompatActivity {

    Context context = this;
    private DoubleTapPlayerView playerView;
    private ExoPlayer simpleExoPlayer;

    YouTubeOverlay youtube_overlay;

    private DefaultTrackSelector trackSelector;


    static ProgressDialog progressDialog;


    Map<String, String> defaultRequestProperties = new HashMap<>( );
    String userAgent = "";

    List<StreamList> multiqualityListWeb = new ArrayList<>( );
    WebView webView;

    TextView count_room;
    private String bGljZW5zZV9jb2Rl;

    private TorrentStreamServer torrentStreamServer;

    ImageView goback;

    RecyclerView message_rv;
    EpesodModel episode;
    String roomIDl;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("rooms");

    AuthManager authManager;

    TextInputLayout textinputacc;
    TextInputEditText sendcomentedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("أرجو الإنتظار");
        progressDialog.setCancelable(false);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_watch_room);
        initial();

        getMessages();

        reccordusers();

        gettotalhowtwatchnow();

        textinputacc.setEndIconOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (!sendcomentedit.getText( ).toString().isEmpty()){
                    if (authManager.getUserInfo().getId() > 0){

                        sendMsg(sendcomentedit.getText( ).toString());

                    }else {
                        sendcomentedit.getText().clear();
                        sendcomentedit.clearFocus();
                        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(sendcomentedit.getWindowToken(), 0);

                        ShowsnackLogin(view, WatchRoom.this);

                    }
                }else {
                    Snackbar.make(view,"الخانة فارغة",3000).show();
                }





            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed( );
        db.child(roomIDl).child("users").child(String.valueOf(authManager.getUserInfo().getId())).setValue(null);
        db.child(roomIDl).child("users").addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getChildrenCount() == 0){
                    db.child(roomIDl).setValue(null);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void gettotalhowtwatchnow() {

        db.child(roomIDl).child("users").addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                count_room.setText(snapshot.getChildrenCount()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void reccordusers() {

        UserModel roomModel = new UserModel();
        roomModel.setId(authManager.getUserInfo().getId());
        roomModel.setName(authManager.getUserInfo().getName());
        roomModel.setProfil(authManager.getUserInfo().getProfil());

        db.child(roomIDl).child("users").child(String.valueOf(roomModel.getId())).setValue(roomModel);

    }

    private void sendMsg(String toString) {

        DatabaseReference databaseReference = db.child(String.valueOf(roomIDl)).child("message");
        String uniqueId = databaseReference.push().getKey();

        MessageModel messageModel = new MessageModel(authManager.getUserInfo().getProfil(),
                toString,
                authManager.getUserInfo().getName(),
                System.currentTimeMillis(),
                authManager.getUserInfo().getId());



        databaseReference.child(uniqueId).setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>( ) {
            @Override
            public void onSuccess(Void unused) {
                sendcomentedit.getText().clear();
                sendcomentedit.clearFocus();
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(sendcomentedit.getWindowToken(), 0);


            }
        }).addOnFailureListener(new OnFailureListener( ) {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WatchRoom.this, e.getMessage()+" هناك مشكلة ", Toast.LENGTH_SHORT).show( );

            }
        });


    }

    private void getMessages() {

        db.child(String.valueOf(roomIDl)).child("message").addValueEventListener(new ValueEventListener( ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MessageModel> roomModels = new ArrayList<>();

                for (DataSnapshot room : snapshot.getChildren()) {
                    String imageProfile = room.child("imageProfile").getValue(String.class);
                    String message = room.child("message").getValue(String.class);
                    String nameUser = room.child("nameUser").getValue(String.class);
                    long time = room.child("time").getValue(Long.class);
                    int userId = room.child("userId").getValue(Integer.class);

                    MessageModel messageModel = new MessageModel(imageProfile, message, nameUser, time, userId);
                    roomModels.add(messageModel);

                    }


                message_rv.setLayoutManager(new LinearLayoutManager(WatchRoom.this,RecyclerView.VERTICAL,false));
                message_rv.setAdapter(new WatchRoomAdapter(roomModels));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void initial() {

        episode = (EpesodModel) getIntent().getSerializableExtra("episode");
        roomIDl = getIntent().getStringExtra("roomid");

        android.util.Log.d("TAG", "initial: "+roomIDl);

        bGljZW5zZV9jb2Rl = "565455";

        provideApplicationInfo(getApplication());

        if (provideSnifferCheck != null) {
            Toast.makeText(this, "لا يمكنك تشغيل تطبيقات اللتقاط الشبكة", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }

        userAgent = WebSettings.getDefaultUserAgent(context);

        sendcomentedit = findViewById(R.id.sendcomentedit);
        textinputacc = findViewById(R.id.textinputacc);
        message_rv = findViewById(R.id.message_rv);
        count_room = findViewById(R.id.count_room);
        playerView = findViewById(R.id.player_view);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

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

        LinearLayout pipcid = findViewById(R.id.pipcid);
        pipcid.setVisibility(View.GONE);
        ConstraintLayout brightnessLayout = findViewById(R.id.brightnessLayout);
        brightnessLayout.setVisibility(View.GONE);

        Prepare_Source(episode.getSource( ), episode.getUrl( ));

        LinearLayout playerSpeed = findViewById(R.id.playerSpeed);
        playerSpeed.setVisibility(View.GONE);

        LinearLayout img_full_scr = findViewById(R.id.img_full_scr);
        img_full_scr.setVisibility(View.GONE);



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
                releasePlayer( );
                handler.removeCallbacks(runnable);
                db.child(roomIDl).child("users").child(String.valueOf(authManager.getUserInfo().getId())).setValue(null);
                db.child(roomIDl).child("users").addValueEventListener(new ValueEventListener( ) {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.getChildrenCount() == 0){
                            db.child(roomIDl).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>( ) {
                                @Override
                                public void onSuccess(Void unused) {
                                    finish( );
                                }
                            });

                        }else {
                            finish( );
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



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



        ConstraintLayout volumeLayout = findViewById(R.id.volumeLayout);
        volumeLayout.setVisibility(View.GONE);


        handler.post(runnable);

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
                    ytMultipleQualityDialog(WatchRoom.this, result);
                }

                @Override
                public void onError(VolleyError error) {
                    releasePlayer( );
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

            String finalurl = "";

            if (!url.contains("embed")) {
                finalurl = url.replace("https://vidpro.net/", "https://vidpro.net/embed-");

            }

            Getlinkofwebview(finalurl);

        } else {
            finishPlayer( );
        }

    }

    public void Getlinkofwebview(String url) {

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
                        releasePlayer( );
                        finish( );
                    });
            progressDialog.hide( );
            builder.show( );
        }
    }

    public void multiQualityPlayer(String extension, String Url, String referer, String cookie) {
        Log.d("test", extension + " : " + Url + " : " + referer + " : " + cookie);

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

    private Handler handler = new Handler( );
    private Runnable runnable = new Runnable( ) {
        @Override
        public void run() {

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
                    releasePlayer( );
                    finish( );
                });
        builder.show( );
    }

    void initializePlayer(MediaSource mediaSource) {
        ExoTrackSelection.Factory videoTrackSelectionFactory = new
                AdaptiveTrackSelection.Factory( );

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        trackSelector = new
                DefaultTrackSelector(WatchRoom.this, videoTrackSelectionFactory);
        RenderersFactory renderersFactory;
        renderersFactory = MediaHelper.buildRenderersFactory(this, sharedPreferences.getBoolean(EXTENTIONS, false)
                , sharedPreferences.getBoolean(SOFTWARE_EXTENTIONS, false));
        simpleExoPlayer = new ExoPlayer.Builder(WatchRoom.this)
                .setRenderersFactory(renderersFactory)
                .setTrackSelector(trackSelector)
                .build( );

        simpleExoPlayer.prepare(mediaSource);
        youtube_overlay.player(simpleExoPlayer);

        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.play();

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


                }
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                //PlayBack Error
            }
        });
    }

    private void finishPlayer() {
        progressDialog.hide( );
        releasePlayer( );
        handler.removeCallbacks(runnable);
        finish( );
    }
    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop( );
            simpleExoPlayer.release( );
            simpleExoPlayer = null;
        }
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
    protected void onDestroy() {
        super.onDestroy( );
        db.child(roomIDl).child("users").child(String.valueOf(authManager.getUserInfo().getId())).setValue(null);

        if (simpleExoPlayer != null){
            simpleExoPlayer.stop( );
        }
        if (torrentStreamServer != null) {
            torrentStreamServer.stopTorrentStream( );
        }
    }

    @Override
    protected void onStop() {
        super.onStop( );
        if (simpleExoPlayer != null){
            simpleExoPlayer.stop( );
        }
    }

    @Override
    protected void onPause() {
        super.onPause( );
        if (simpleExoPlayer != null){
            simpleExoPlayer.pause( );
        }
    }

    public class WatchRoomAdapter extends  RecyclerView.Adapter<WatchRoomAdapter.myview>  {


        List<MessageModel> messageModels;

        public WatchRoomAdapter(List<MessageModel> messageModels) {
            this.messageModels = messageModels;
        }

        @NonNull
        @Override
        public WatchRoomAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 1){
                return new myview(LayoutInflater.from(WatchRoom.this).inflate(R.layout.my_chat_item ,parent, false));

            }else {
                return new myview(LayoutInflater.from(WatchRoom.this).inflate(R.layout.chat_item ,parent, false));

            }


        }

        @Override
        public void onBindViewHolder(@NonNull WatchRoomAdapter.myview holder, int position) {
            MessageModel mesg = messageModels.get(position);

            if (getItemViewType(position) == 1){
                holder.message_body.setText(mesg.getMessage());
            }else {
                holder.name.setText(mesg.getNameUser());
                holder.message_body.setText(mesg.getMessage());

            }


        }

        @Override
        public int getItemCount() {
            return messageModels.size();
        }

        @Override
        public int getItemViewType(int position) {

            if (messageModels.get(position).getUserId() == authManager.getUserInfo().getId()){
                return 1;
            }else {
                return 2;
            }

        }

        public class myview extends RecyclerView.ViewHolder {

            TextView message_body,name;
            public myview(@NonNull View itemView) {
                super(itemView);

                message_body = itemView.findViewById(R.id.message_body);
                name = itemView.findViewById(R.id.name);
            }
        }
    }
}