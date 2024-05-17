package com.anass.jplus.Config;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.anass.jplus.Activities.Jdownload.DownloadList;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.Models.SeasonModel;
import com.anass.jplus.R;
import com.google.android.material.snackbar.Snackbar;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Status;
import com.tonyodev.fetch2core.DownloadBlock;
import com.tonyodev.fetch2core.Downloader;
import com.tonyodev.fetch2okhttp.OkHttpDownloader;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class DownloadHelper {

    private static NotificationManagerCompat nmc;
    private static NotificationCompat.Builder builder;
    private static PendingIntent pendingIntent;

    public static void startDownload(CartoonModel tvmodel, EpesodModel epesodModel, SeasonModel seasonModel, Context context, View view, String name, String mimeType, String url, String referer, String cookie) {

        Log.d("test", name + " " + url + " " + referer);

        Fetch fetch;
        final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(context)
                .setDownloadConcurrentLimit(5)
                .setHttpDownloader(new OkHttpDownloader(Downloader.FileDownloaderType.PARALLEL))
                .setNamespace("DownloadList")
                .build( );
        fetch = Fetch.Impl.getInstance(fetchConfiguration);


        Intent intentdown = new Intent(context, DownloadList.class);
        pendingIntent = PendingIntent.getActivity(context, 0, intentdown, PendingIntent.FLAG_IMMUTABLE);

        nmc = NotificationManagerCompat.from(context);
        builder = new NotificationCompat.Builder(context, "JSERIES_CHANNEL");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("JSERIES_CHANNEL", "JSERIES", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("JSERIES Download");

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }


        File dDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dFile = new File(dDirectory, "/jcartoon" +
                "/" + "Downloads");
        String file = dFile + "/" + name + "." + mimeType;
        Log.d("TAG", "startDownload: "+dFile);

        com.tonyodev.fetch2.Request request = new com.tonyodev.fetch2.Request(url, file);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("User-Agent", WebSettings.getDefaultUserAgent(context));
        request.addHeader("title", tvmodel.getTitle( ));
        request.addHeader("seasonnumb", seasonModel.getName( ) != null ? seasonModel.getName( ) : "1");
        request.addHeader("epenumb", epesodModel.getLebel( ));

        if (!referer.equals("")) {
            request.addHeader("Referer", referer);
        }
        if (!cookie.equals("")) {
            request.addHeader("Cookie", cookie);
        }

        fetch.addListener(new FetchListener( ) {
            @Override
            public void onAdded(@NonNull Download download) {

            }

            @Override
            public void onQueued(@NonNull Download download, boolean b) {
                updateNot(download, context);
            }

            @Override
            public void onWaitingNetwork(@NonNull Download download) {

            }

            @Override
            public void onCompleted(@NonNull Download download) {
                updateNot(download, context);

            }

            @Override
            public void onError(@NonNull Download download, @NonNull Error error, @Nullable Throwable throwable) {

            }

            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i) {

            }

            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {

            }

            @Override
            public void onProgress(@NonNull Download download, long l, long l1) {
                updateNot(download, context);
            }

            @Override
            public void onPaused(@NonNull Download download) {
                updateNot(download,context);
            }

            @Override
            public void onResumed(@NonNull Download download) {
                updateNot(download, context);
            }

            @Override
            public void onCancelled(@NonNull Download download) {
            }

            @Override
            public void onRemoved(@NonNull Download download) {
                updateNot(download, context);
            }

            @Override
            public void onDeleted(@NonNull Download download) {
                updateNot(download, context);
            }
        });

        fetch.enqueue(request, updatedRequest -> {

            Toast.makeText(context,"بدا التحميل", Toast.LENGTH_SHORT).show( );
            //Request was successfully enqueued for download.


        }, error -> {
            //An error occurred enqueuing the request.
            Toast.makeText(context,"فشل التحميل", Toast.LENGTH_SHORT).show( );

        });


    }

    public static void updateNot(Download download, Context context) {



        if (download.getStatus( ) == Status.DOWNLOADING || download.getStatus( ) == Status.QUEUED) {
            String title = download.getHeaders( ).get("title")+download.getHeaders( ).get("seasonnumb")+"E"+download.getHeaders( ).get("epenumb");
            builder.setContentTitle(Utils.limitString(title, 30))
                    .setContentText(Utils.SpeedCal((float) download.getDownloadedBytesPerSecond( )))
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .setShowWhen(false)
                    .setOnlyAlertOnce(true)
                    .setSmallIcon(R.drawable.ic_download)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            builder.setProgress(100, download.getProgress( ), false);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            nmc.notify(download.getId( ), builder.build( ));
        }

        if(download.getStatus() == Status.COMPLETED){
            builder.setContentText("التحميل مكتمل")
                    .setProgress(0, 0, false)
                    .setOngoing(false)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            nmc.notify(download.getId(), builder.build());
        }

        if(download.getStatus() == Status.PAUSED || download.getStatus() == Status.DELETED || download.getStatus() == Status.REMOVED){
            nmc.cancel(download.getId());
        }
    }

    @NonNull
    public static String getMimeType(@NonNull final Context context, @NonNull final Uri uri) {
        final ContentResolver cR = context.getContentResolver();
        final MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cR.getType(uri));
        if (type == null) {
            type = "*/*";
        }
        return type;
    }

    public static void deleteFileAndContents(@NonNull final File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                final File[] contents = file.listFiles();
                if (contents != null) {
                    for (final File content : contents) {
                        deleteFileAndContents(content);
                    }
                }
            }
            file.delete();
        }
    }

    @NonNull
    public static String getETAString(@NonNull final Context context, final long etaInMilliSeconds) {
        if (etaInMilliSeconds < 0) {
            return "";
        }
        int seconds = (int) (etaInMilliSeconds / 1000);
        long hours = seconds / 3600;
        seconds -= hours * 3600;
        long minutes = seconds / 60;
        seconds -= minutes * 60;
        if (hours > 0) {
            return context.getString(R.string.download_eta_hrs, hours, minutes, seconds);
        } else if (minutes > 0) {
            return context.getString(R.string.download_eta_min, minutes, seconds);
        } else {
            return context.getString(R.string.download_eta_sec, seconds);
        }
    }

    @NonNull
    public static String getDownloadSpeedString(@NonNull final Context context, final long downloadedBytesPerSecond) {
        if (downloadedBytesPerSecond < 0) {
            return "";
        }
        double kb = (double) downloadedBytesPerSecond / (double) 1000;
        double mb = kb / (double) 1000;
        final DecimalFormat decimalFormat = new DecimalFormat(".##");
        if (mb >= 1) {
            return context.getString(R.string.download_speed_mb, decimalFormat.format(mb));
        } else if (kb >= 1) {
            return context.getString(R.string.download_speed_kb, decimalFormat.format(kb));
        } else {
            return context.getString(R.string.download_speed_bytes, downloadedBytesPerSecond);
        }
    }

    @NonNull
    public static File createFile(String filePath) {
        final File file = new File(filePath);
        if (!file.exists()) {
            final File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static int getProgress(long downloaded, long total) {
        if (total < 1) {
            return -1;
        } else if (downloaded < 1) {
            return 0;
        } else if (downloaded >= total) {
            return 100;
        } else {
            return (int) (((double) downloaded / (double) total) * 100);
        }
    }

}
