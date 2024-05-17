package com.anass.jplus.Adapters;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.anass.jplus.Config.DownloadHelper;
import com.anass.jplus.Constant.ActionListener;
import com.anass.jplus.JPLAYER.Player;
import com.anass.jplus.R;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class DownloadListAdepter extends RecyclerView.Adapter<DownloadListAdepter.ViewHolder> {
    Context mContext;

    @NonNull
    private final List<DownloadData> downloads = new ArrayList<>();
    @NonNull
    private final ActionListener actionListener;

    public DownloadListAdepter(@NonNull final ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.download_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.actionButton.setOnClickListener(null);
        holder.actionButton.setEnabled(true);

        final DownloadData downloadData = downloads.get(position);
        String url = "";
        if (downloadData.download != null) {
            url = downloadData.download.getUrl();
        }
        final Uri uri = Uri.parse(url);

        final Uri fileUri = downloadData.download.getFileUri();

        final Status status = downloadData.download.getStatus();
        final Context context = holder.itemView.getContext();

        Download download = downloadData.download;


        String season = "1";
        if (download.getHeaders( ).get("seasonnumb").contains("الموسم")){
            season = download.getHeaders( ).get("seasonnumb").replace("season","");
        }else {
            season = download.getHeaders( ).get("seasonnumb");
        }
        
        String title = download.getHeaders( ).get("title")+"S"+season+"E"+download.getHeaders( ).get("epenumb");
        holder.titleTextView.setText(title);
        holder.statusTextView.setText(getStatusString(status));

        int progress = downloadData.download.getProgress();
        if (progress == -1) { // Download progress is undermined at the moment.
            progress = 0;
        }
        holder.progressBar.setProgress(progress);
        holder.progressTextView.setText(context.getString(R.string.percent_progress, progress));

        if (downloadData.eta == -1) {
            holder.timeRemainingTextView.setText("");
        } else {
            holder.timeRemainingTextView.setText(DownloadHelper.getETAString(context, downloadData.eta));
        }

        if (downloadData.downloadedBytesPerSecond == 0) {
            holder.downloadedBytesPerSecondTextView.setText("");
        } else {
            holder.downloadedBytesPerSecondTextView.setText(DownloadHelper.getDownloadSpeedString(context, downloadData.downloadedBytesPerSecond));
        }

        switch (status) {
            case COMPLETED: {
                holder.actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.play_button_icon));
                holder.actionButton.setOnClickListener(view -> {
                    String name = downloadData.download.getFileUri().getLastPathSegment().split("\\.")[0];
                    String externtion = downloadData.download.getFileUri().getLastPathSegment().split("\\.")[1];
                    final CharSequence[] items = {"مشغل جي كرتون", "مشغل خارجي"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle(name)
                            .setItems(items, (dialog, which) -> {
                                dialog.dismiss();
                                if(which == 0) {
                                    Intent intent = new Intent(context, Player.class);
                                    intent.putExtra("ContentID", 0);
                                    intent.putExtra("Content_Type", "Downloaded");
                                    intent.putExtra("name", name);
                                    intent.putExtra("source", externtion);
                                    intent.putExtra("url", downloadData.download.getFileUri().toString());
                                    intent.putExtra("skip_available", 0);
                                    intent.putExtra("intro_start", 0);
                                    intent.putExtra("intro_end", 0);

                                    context.startActivity(intent);
                                } else {
                                    Log.d("tag", "onBindViewHolder: "+Uri.parse(downloadData.download.getFile()));
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadData.download.getFile()));
                                    intent.setDataAndType(Uri.parse(downloadData.download.getFile()), "video/*");
                                    context.startActivity(intent);
                                }
                            })
                            .setPositiveButton("إلغاء", (dialog, which) -> dialog.dismiss());
                    builder.show();
                });
                break;
            }
            case FAILED: {
                Log.d("test", downloadData.download.getError().toString());
                holder.actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.fetch_notification_retry));
                holder.actionButton.setOnClickListener(view -> {
                    holder.actionButton.setEnabled(false);
                    actionListener.onRetryDownload(downloadData.download.getId());
                });
                break;
            }
            case PAUSED: {
                holder.actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.refresh));
                holder.actionButton.setOnClickListener(view -> {
                    holder.actionButton.setEnabled(false);
                    actionListener.onResumeDownload(downloadData.download.getId());
                });
                break;
            }
            case DOWNLOADING:
            case QUEUED: {
                holder.actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pause));
                holder.actionButton.setOnClickListener(view -> {
                    holder.actionButton.setEnabled(false);
                    actionListener.onPauseDownload(downloadData.download.getId());
                });
                break;
            }
            case ADDED: {
                holder.actionButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.download_icon));
                holder.actionButton.setOnClickListener(view -> {
                    holder.actionButton.setEnabled(false);
                    actionListener.onResumeDownload(downloadData.download.getId());
                });
                break;
            }
            default: {
                break;
            }
        }

        //Set delete action
        holder.itemView.setOnLongClickListener(v -> {
            final Uri uri12 = downloadData.download.getFileUri();
            new AlertDialog.Builder(context)
                    .setMessage(context.getString(R.string.delete_title))
                    .setPositiveButton("حدف", (dialog, which) -> {
                        actionListener.onRemoveDownload(downloadData.download.getId());
                        DownloadHelper.deleteFileAndContents(new File(downloadData.download.getFile()));
                    })
                    .setNegativeButton("إلغاء", null)
                    .show();

            return true;
        });

    }

    public void addDownload(@NonNull final Download download) {
        boolean found = false;
        DownloadData data = null;
        int dataPosition = -1;
        for (int i = 0; i < downloads.size(); i++) {
            final DownloadData downloadData = downloads.get(i);
            if (downloadData.id == download.getId()) {
                data = downloadData;
                dataPosition = i;
                found = true;
                break;
            }
        }
        if (!found) {
            final DownloadData downloadData = new DownloadData();
            downloadData.id = download.getId();
            downloadData.download = download;
            downloads.add(downloadData);
            notifyItemInserted(downloads.size() - 1);
        } else {
            data.download = download;
            notifyItemChanged(dataPosition);
        }
    }

    @Override
    public int getItemCount() {
        return downloads.size();
    }

    public void update(@NonNull final Download download, long eta, long downloadedBytesPerSecond) {
        for (int position = 0; position < downloads.size(); position++) {
            final DownloadData downloadData = downloads.get(position);
            if (downloadData.id == download.getId()) {
                switch (download.getStatus()) {
                    case REMOVED:
                    case DELETED: {
                        downloads.remove(position);
                        notifyItemRemoved(position);
                        break;
                    }
                    default: {
                        downloadData.download = download;
                        downloadData.eta = eta;
                        downloadData.downloadedBytesPerSecond = downloadedBytesPerSecond;
                        notifyItemChanged(position);
                    }
                }
                return;
            }
        }
    }

    private String getStatusString(Status status) {
        switch (status) {
            case COMPLETED:
                return "مكتمل";
            case DOWNLOADING:
                return "يتحمل";
            case FAILED:
                return "خطأ";
            case PAUSED:
                return "إستئناف";
            case QUEUED:
                return "إنتضر قليلا";
            case REMOVED:
                return "محدوفة";
            case NONE:
                return "الرابط معطل";
            default:
                return "غير معروف";
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView titleTextView;
        final TextView statusTextView;
        public final ProgressBar progressBar;
        public final TextView progressTextView;
        public final ImageView actionButton;
        final TextView timeRemainingTextView;
        final TextView downloadedBytesPerSecondTextView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.Title);
            statusTextView = itemView.findViewById(R.id.status);
            progressBar = itemView.findViewById(R.id.progressBar);
            actionButton = itemView.findViewById(R.id.actionButtonImage);
            progressTextView = itemView.findViewById(R.id.progressTextView);
            timeRemainingTextView = itemView.findViewById(R.id.eta);
            downloadedBytesPerSecondTextView = itemView.findViewById(R.id.speedText);
        }

    }

    public static class DownloadData {
        public int id;
        @Nullable
        public Download download;
        long eta = -1;
        long downloadedBytesPerSecond = 0;

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public String toString() {
            if (download == null) {
                return "";
            }
            return download.toString();
        }

        @Override
        public boolean equals(Object obj) {
            return obj == this || obj instanceof DownloadData && ((DownloadData) obj).id == id;
        }
    }

}