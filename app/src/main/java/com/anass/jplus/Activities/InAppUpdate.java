package com.anass.jplus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.anass.jplus.Config.HelperUtils;
import com.anass.jplus.Config.Utils;
import com.google.common.io.Files;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;

import org.imaginativeworld.oopsnointernet.dialogs.signal.DialogPropertiesSignal;
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import es.dmoral.toasty.Toasty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InAppUpdate extends AppCompatActivity {

    String updateTitle = null;
    String apkFileUrl = null;
    String whatsNewOnLatestApk = null;


    ProgressBar progressBar;

    TextView eta;
    TextView downloadProgress;


    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_update);



        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        Intent intent = getIntent();
        updateTitle = intent.getExtras().getString("Update_Title");
        whatsNewOnLatestApk = intent.getExtras().getString("Whats_new_on_latest_APK");
        apkFileUrl = intent.getExtras().getString("APK_File_URL");

        TextView updateTitleText = findViewById(R.id.Update_Title);
        updateTitleText.setText(updateTitle);

        List<UpdateList> updateList = new ArrayList<>();
        String[] values = whatsNewOnLatestApk.split(",");
        for (int i = 0; i < values.length; i++) {
            updateList.add(new UpdateList(values[i].trim()));
        }

        RecyclerView updateListRecycleview = findViewById(R.id.Whats_New_Recycleview);
        UpdateListAdepter myadepter = new UpdateListAdepter(this, updateList);
        updateListRecycleview.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        updateListRecycleview.setAdapter(myadepter);



        Button update = findViewById(R.id.UPDATE);

        progressBar = findViewById(R.id.progressBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            progressBar.setMin(1);
        }
        progressBar.setMax(100);
        progressBar.setIndeterminate(true);


        eta = findViewById(R.id.ETA);
        downloadProgress = findViewById(R.id.Download_Progress);


        update.setOnClickListener(view -> {
            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(apkFileUrl));
            intent1.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.setPackage("com.android.chrome");
            startActivity(intent1);


        });

        // No Internet Dialog: Signal
        NoInternetDialogSignal.Builder builder = new NoInternetDialogSignal.Builder(
                this,
                getLifecycle()
        );
        DialogPropertiesSignal properties = builder.getDialogProperties();
        // Optional
        properties.setConnectionCallback(hasActiveConnection -> {
            // ...
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
        builder.build();

    }


    public class UpdateListAdepter extends RecyclerView.Adapter<UpdateListAdepter.MyViewHolder> {

        private Context mContext;
        private List<UpdateList> mData;

        public UpdateListAdepter(Context mContext, List<UpdateList> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.update_list_item,parent,false);
            return new UpdateListAdepter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView Whats_New;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                Whats_New = (TextView) itemView.findViewById(R.id.Whats_New_Text);
            }

            void setText(UpdateList text) {
                Whats_New.setText(text.getWhats_New());
            }
        }
    }



    public class UpdateList {
        private String Whats_New;

        public UpdateList(String whats_New) {
            Whats_New = whats_New;
        }

        public String getWhats_New() {
            return Whats_New;
        }

        public void setWhats_New(String whats_New) {
            Whats_New = whats_New;
        }
    }

}