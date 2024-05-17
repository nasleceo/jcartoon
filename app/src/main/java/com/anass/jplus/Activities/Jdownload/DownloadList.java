package com.anass.jplus.Activities.Jdownload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.anass.jplus.Adapters.DownloadListAdepter;
import com.anass.jplus.Config.HelperUtils;
import com.anass.jplus.Config.TinyDB;
import com.anass.jplus.Constant.ActionListener;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.tonyodev.fetch2.AbstractFetchListener;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2core.Downloader;
import com.tonyodev.fetch2okhttp.OkHttpDownloader;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class DownloadList extends AppCompatActivity implements ActionListener {

    private static final long UNKNOWN_REMAINING_TIME = -1;
    private static final long UNKNOWN_DOWNLOADED_BYTES_PER_SECOND = 0;

    private Fetch fetch;
    private DownloadListAdepter fileAdapter;

    RecyclerView recyclerView;

    SwitchCompat networkSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloades);

        setUpViews();
        final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .setDownloadConcurrentLimit(5)
                .setHttpDownloader(new OkHttpDownloader(Downloader.FileDownloaderType.PARALLEL))
                .setNamespace("DownloadList")
                .build();
        fetch = Fetch.Impl.getInstance(fetchConfiguration);

        LinearLayout settingItem = findViewById(R.id.settingItem);
        settingItem.setOnClickListener(view -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.download_settings_dialog);

            LinearLayout DownloadStoragelinearLayout = bottomSheetDialog.findViewById(R.id.DownloadStoragelinearLayout);
            DownloadStoragelinearLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(AppConfig.primeryThemeColor)));

            LinearProgressIndicator storageIndicator = bottomSheetDialog.findViewById(R.id.storageIndicator);
            storageIndicator.setIndicatorColor(Color.parseColor("#008DFC"));


            networkSwitch = bottomSheetDialog.findViewById(R.id.networkSwitch);
            TinyDB tinyDB = new TinyDB(this);
            networkSwitch.setChecked(tinyDB.getBoolean("networkSwitch"));
            networkSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                tinyDB.putBoolean("networkSwitch", isChecked);
                if (isChecked) {
                    fetch.setGlobalNetworkType(NetworkType.WIFI_ONLY);
                } else {
                    fetch.setGlobalNetworkType(NetworkType.ALL);
                }
            });

            long totalSize = new File(getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getTotalSpace();
            long totalSizetotMb = totalSize / (1024 * 1024);
            storageIndicator.setMax((int) totalSizetotMb);

            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long bytesAvailable;
            if (android.os.Build.VERSION.SDK_INT >=
                    android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
            } else {
                bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
            }
            long megAvailable = bytesAvailable / (1024 * 1024);
            storageIndicator.setProgress((int) megAvailable);

            TextView totalTextView = bottomSheetDialog.findViewById(R.id.totalTextView);
            totalTextView.setText(HelperUtils.formatFileSize(totalSize));

            TextView freeTextView = bottomSheetDialog.findViewById(R.id.freeTextView);
            freeTextView.setText(HelperUtils.formatFileSize(bytesAvailable));

            bottomSheetDialog.show();
        });

        setColorTheme(Color.parseColor(AppConfig.primeryThemeColor));
    }

    void setColorTheme(int color) {
        TextView titleText = findViewById(R.id.titleText);
        titleText.setTextColor(color);

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.comment_tag_bg);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);

        LinearLayout settingItem = findViewById(R.id.settingItem);
        settingItem.setBackground(wrappedDrawable);
    }

    private void setUpViews() {
        recyclerView = findViewById(R.id.downloadListRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fileAdapter = new DownloadListAdepter(this);
        recyclerView.setAdapter(fileAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetch.getDownloads(downloads -> {
            final ArrayList<Download> list = new ArrayList<>(downloads);
            Collections.sort(list, (first, second) -> Long.compare(first.getCreated(), second.getCreated()));
            Collections.reverse(list);
            for (Download download : list) {
                fileAdapter.addDownload(download);
            }

            if (downloads.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
            }
        }).addListener(fetchListener);
    }

    private final FetchListener fetchListener = new AbstractFetchListener() {
        @Override
        public void onAdded(@NotNull Download download) {
            fileAdapter.addDownload(download);
        }

        @Override
        public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
            fileAdapter.update(download, UNKNOWN_REMAINING_TIME, UNKNOWN_DOWNLOADED_BYTES_PER_SECOND);
        }

        @Override
        public void onCompleted(@NotNull Download download) {
            fileAdapter.update(download, UNKNOWN_REMAINING_TIME, UNKNOWN_DOWNLOADED_BYTES_PER_SECOND);
        }

        @Override
        public void onError(@NotNull Download download, @NotNull Error error, @org.jetbrains.annotations.Nullable Throwable throwable) {
            super.onError(download, error, throwable);
            fileAdapter.update(download, UNKNOWN_REMAINING_TIME, UNKNOWN_DOWNLOADED_BYTES_PER_SECOND);
        }

        @Override
        public void onProgress(@NotNull Download download, long etaInMilliseconds, long downloadedBytesPerSecond) {
            fileAdapter.update(download, etaInMilliseconds, downloadedBytesPerSecond);
        }

        @Override
        public void onPaused(@NotNull Download download) {
            fileAdapter.update(download, UNKNOWN_REMAINING_TIME, UNKNOWN_DOWNLOADED_BYTES_PER_SECOND);
        }

        @Override
        public void onResumed(@NotNull Download download) {
            fileAdapter.update(download, UNKNOWN_REMAINING_TIME, UNKNOWN_DOWNLOADED_BYTES_PER_SECOND);
        }

        @Override
        public void onCancelled(@NotNull Download download) {
            fileAdapter.update(download, UNKNOWN_REMAINING_TIME, UNKNOWN_DOWNLOADED_BYTES_PER_SECOND);
        }

        @Override
        public void onRemoved(@NotNull Download download) {
            fileAdapter.update(download, UNKNOWN_REMAINING_TIME, UNKNOWN_DOWNLOADED_BYTES_PER_SECOND);
        }

        @Override
        public void onDeleted(@NotNull Download download) {
            fileAdapter.update(download, UNKNOWN_REMAINING_TIME, UNKNOWN_DOWNLOADED_BYTES_PER_SECOND);
        }
    };

    @Override
    public void onPauseDownload(int id) {
        fetch.pause(id);
    }

    @Override
    public void onResumeDownload(int id) {
        fetch.resume(id);
    }

    @Override
    public void onRemoveDownload(int id) {
        fetch.remove(id);
    }

    @Override
    public void onRetryDownload(int id) {
        fetch.retry(id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed( );
        finish( );
    }


}