package com.anass.jplus.Config;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;

import com.anass.jplus.Activities.Jdownload.DownloadManage;
import com.anass.jplus.R;

import java.io.File;

public class ServerController {
    private final Context context;


    public static final String SECURE_URI = "secure_uri";
    public static final String USER_AGENT = "User-Agent";
    public static final String VIDEOTYPE = "video/*";
    public static final String TITLE = "title";
    public static final String POSTER = "poster";
    public static final String EXTRA_HEADERS = "android.media.intent.extra.HTTP_HEADERS";
    public static final String HEADERS = "headers";
    public static final String REFER = "Referer";

    private String MainLink;
    private String EpisodeTilte;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    public ServerController(Context context) {
        this.context = context;

    }

    public void setMainLink(String mainLink) {
        MainLink = mainLink;
    }

/*
    public void setLinksQ(List<Jmodel> linksQ) {
        LinksQ = linksQ;
    }
*/

    public void setRequestPermissionLauncher(ActivityResultLauncher<String> requestPermissionLauncher) {
        this.requestPermissionLauncher = requestPermissionLauncher;
    }

    public void setEpisodeTilte(String episodeTilte) {
        EpisodeTilte = episodeTilte;
    }

 /*   public void ShowMultiQuality(){
        Dialog quality_selector = new Dialog(context);
        quality_selector.setContentView(R.layout.quality_selector_instagram);
        quality_selector.setCancelable(true);


        LinearLayout qualities = quality_selector.findViewById(R.id.quality_list);

        LayoutInflater inflater = LayoutInflater.from(context);

        for (int i = 0; i <  LinksQ.size(); i++) {

            final int fi = i;

            View quality_bar = inflater.inflate(R.layout.quality_selector_epesode_item, null);

            TextView q = quality_bar.findViewById(R.id.quality);
            q.setText(LinksQ.get(i).getQuality());

            ImageView play = quality_bar.findViewById(R.id.playit);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startPlayer(LinksQ.get(fi).getUrl());
                    //openOtherPlayers(LinksQ.get(fi).getUrl());
                    quality_selector.dismiss();


                }
            });

            View download = quality_bar.findViewById(R.id.downloadit);
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (isStoragePermissionGranted()) {
                        showDownloadWay(LinksQ.get(fi).getUrl(),LinksQ.get(fi).getQuality());
                    }
                    else {
                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                    quality_selector.dismiss();
                }
            });

            qualities.addView(quality_bar);
        }


        quality_selector.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        quality_selector.show();
    }
    public void ShowOnQuality(){
        if (MainLink!=null) {

            String finalUrl = MainLink;
            Dialog quality_selector = new Dialog(context);
            quality_selector.setCancelable(true);
            quality_selector.setContentView(R.layout.quality_selector_instagram);



            LinearLayout qualities = quality_selector.findViewById(R.id.quality_list);

            LayoutInflater inflater = LayoutInflater.from(context);

            for (int i = 0; i <  1; i++) {

                final int fi = i;

                View quality_bar = inflater.inflate(R.layout.quality_selector_epesode_item, null);

                TextView q = quality_bar.findViewById(R.id.quality);
                q.setText("المتوفرة");

                ImageView play = quality_bar.findViewById(R.id.playit);
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startPlayer(finalUrl);
                        quality_selector.dismiss();


                    }
                });

                View download = quality_bar.findViewById(R.id.downloadit);
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (isStoragePermissionGranted()) {
                            showDownloadWay(finalUrl,EpisodeTilte);

                        }
                        else {
                            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                        quality_selector.dismiss();

                    }
                });

                qualities.addView(quality_bar);
            }


            quality_selector.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            quality_selector.show();
        }else {
            NoLinkWork();
        }

    }*/

    public void NoLinkWork(){
        Dialog quality_selector = new Dialog(context);
        quality_selector.setCancelable(true);
        quality_selector.setContentView(R.layout.nolinkswork);
        quality_selector.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        quality_selector.show();
    }


    public static void downloadFromAdm(Context context, String url, String s) {

        Intent shareVideo = new Intent(Intent.ACTION_VIEW);
        shareVideo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareVideo.setDataAndType(Uri.parse(url), VIDEOTYPE);
        shareVideo.setPackage("com.dv.adm");
        ComponentName name = new ComponentName("com.dv.adm","com.dv.get.AEditor");
        shareVideo.setComponent(name);
        shareVideo.putExtra("com.android.extra.filename",s);

        Bundle headers = new Bundle();
        shareVideo.putExtra(SECURE_URI, true);
        try {
            context.startActivity(shareVideo);
        } catch (ActivityNotFoundException ex) {
            // Open Play Store if it fails to launch the app because the package doesn't exist.
            // Alternatively you could use PackageManager.getLaunchIntentForPackage() and check for null.
            // You could try catch this and launch the Play Store website if it fails but this shouldn’t
            // fail unless the Play Store is missing.

            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.dv.adm")));
            } catch (ActivityNotFoundException anfe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.dv.adm")));
            }

        }
    }
    public boolean appInstalledOrNot(String str) {
        try {
            context.getPackageManager().getPackageInfo(str, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void openWithMXPlayer(String url) {
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
                context.startActivity(intent);
                return;
            } catch (Exception e) {
                e.fillInStackTrace();
                Log.d("errorMx", e.getMessage());
                return;
            }
        }
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mxtech.videoplayer.ad")));
        } catch (ActivityNotFoundException e2) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mxtech.videoplayer.ad")));
        }
    }
    private void openOtherPlayers(String finalUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(finalUrl), "video/*");
        context.startActivity(intent);
    }

    private void showDownloadWay(String url, String quality){
        Dialog dw = new Dialog(context);
        dw.setContentView(R.layout.download_selector);
        dw.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dw.show();

        View download_simple = dw.findViewById(R.id.downloadit_simple);
        download_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload(url, quality);
                dw.dismiss();
            }
        });
        View download_share = dw.findViewById(R.id.downloadit_share);
        download_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDownload(url);
                dw.dismiss();
            }
        });
    }

    private void startDownload(String url, String quality) {

        DownloadManage.getInstance(context)
                .addDownloadRequest(url, new File(""),EpisodeTilte, quality);

    }

    private void shareDownload(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("video/mp4");
        intent.setData(Uri.parse(url));
        Intent chooser = Intent.createChooser(intent, "Download with ..");
        try {
            context.startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            somethingWrong(e.getMessage());
        }
    }

    private void notValide(){
        Toast.makeText(context, "تحميل الحلقة غير متاح", Toast.LENGTH_SHORT).show();
    }
    private void somethingWrong(String code){
        Toast.makeText(context, "حدث خطأ (رمز "+code+")", Toast.LENGTH_SHORT).show();
    }

    public static class PleaseWait {

        static Dialog wait;

        public static void show(Context context){
            wait = new Dialog(context);
            wait.setCancelable(true);
            wait.setContentView(R.layout.please_wait);
            wait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            wait.show();
        }

        public static void dismiss(){
            wait.dismiss();
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return true;
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }
}
