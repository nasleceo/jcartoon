package com.anass.jplus.Activities.Jdownload;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.anass.jplus.Config.Utils;
import com.anass.jplus.R;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2.Status;
import com.tonyodev.fetch2core.DownloadBlock;
import com.tonyodev.fetch2core.Func;

import java.io.File;
import java.util.List;

public class DownloadManage {

    private static DownloadManage downloadManage;

    private static Fetch fetch;

    private static NotificationManagerCompat nmc;
    private static NotificationCompat.Builder builder;
    private static PendingIntent pendingIntent;

    private static Context context;

    public static DownloadManage getInstance(Context ctx) {
        context = ctx;

        if (downloadManage == null) {
            context = ctx;

            downloadManage = new DownloadManage( );

            FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(context)
                    .setDownloadConcurrentLimit(3)
                    .build( );

            fetch = Fetch.Impl.getInstance(fetchConfiguration);
            ;

            Intent intent = new Intent(context, DownloadList.class);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            nmc = NotificationManagerCompat.from(context);
            builder = new NotificationCompat.Builder(context, "JSERIES_CHANNEL");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("JSERIES_CHANNEL", "JSERIES", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("JSERIES Download");

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
            }

            fetch.addListener(new FetchListener( ) {
                @Override
                public void onAdded(@NonNull Download download) {

                }

                @Override
                public void onQueued(@NonNull Download download, boolean b) {
                    updateNotifications(download);
                }

                @Override
                public void onWaitingNetwork(@NonNull Download download) {

                }

                @Override
                public void onCompleted(@NonNull Download download) {
                    updateNotifications(download);
                    scanFile(context, download.getFile( ), "mp4");
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
                    updateNotifications(download);
                }

                @Override
                public void onPaused(@NonNull Download download) {
                    updateNotifications(download);
                }

                @Override
                public void onResumed(@NonNull Download download) {
                    updateNotifications(download);
                }

                @Override
                public void onCancelled(@NonNull Download download) {
                }

                @Override
                public void onRemoved(@NonNull Download download) {
                    updateNotifications(download);
                }

                @Override
                public void onDeleted(@NonNull Download download) {
                    updateNotifications(download);
                }
            });
        }

        return downloadManage;
    }

    public static void scanFile(Context ctxt, String x, String mimeType) {
        MediaScannerConnection.scanFile(ctxt, new String[]{x}, new String[]{mimeType}, null);
    }

    public static void updateNotifications(Download download) {
        if (download.getStatus( ) == Status.DOWNLOADING || download.getStatus( ) == Status.QUEUED) {
            String title = download.getHeaders( ).get("anime") + " - " + download.getHeaders( ).get("episode");
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

    boolean exist = false;

    public void addDownloadRequest(String url, File kii, String title, String epetitle){

        String fileName = title + " - " + epetitle + " - "  + ".mp4";
        File file = new File(context.getExternalFilesDir("Downloaded Episodes"), fileName);


        exist = false;
        fetch.getDownloads(new Func<List<Download>>() {
            @Override
            public void call(@NonNull List<Download> result) {
                for(Download d : result){
                    if(d.getUrl().equals(url)){
                        exist = true;
                    }
                }

                if(exist || file.exists()){
                    downloadAlreadyExist();
                }else {
                    final Request request = new Request(url, file.getPath());
                    request.setPriority(Priority.HIGH);
                    request.setNetworkType(NetworkType.ALL);
                    request.addHeader("anime", title);
                    request.addHeader("episode", epetitle);

                    fetch.enqueue(request, new Func<Request>() {
                        @Override
                        public void call(@NonNull Request result) {
                            downloadStarting();
                        }
                    }, new Func<Error>() {
                        @Override
                        public void call(@NonNull Error result) {
                            downloadProblem(result.name());
                        }
                    });
                }
            }
        });
    }

    public Fetch getFetch(){
        return fetch;
    }

    private void downloadStarting(){
        Toast.makeText(context, "بدا التحميل", Toast.LENGTH_SHORT).show();
    }
    private void downloadAlreadyExist(){
        Toast.makeText(context, "تم تحميل هده الحلقة من قبل", Toast.LENGTH_SHORT).show();
    }
    private void downloadProblem(String s){
        Toast.makeText(context, "Download Problem "+"\n"+s, Toast.LENGTH_SHORT).show();
    }
}
