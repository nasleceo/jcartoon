package com.anass.jplus.Activities.ADcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Models.AdsCenter;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class ShowAdsHome extends AppCompatActivity {


    ImageView relat_appbar;
    ImageView photoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ads_home);

        AdsCenter adsCenter = AppConfig.adsCenter;

        relat_appbar = findViewById(R.id.relat_appbar);
        relat_appbar.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        photoview = findViewById(R.id.photoview);
        Glide.with(this).load(adsCenter.getImageDialogAds()).into(photoview);

        photoview.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                String urlString = adsCenter.getLinkDialogAds();
                Log.d("TAG", "onClick: "+urlString);

                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent1.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.chrome");
                startActivity(intent1);

            }
        });




    }
}