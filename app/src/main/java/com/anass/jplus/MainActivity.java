package com.anass.jplus;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.anass.jplus.Config.config.SETTINGS_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anass.jplus.Activities.ADcenter.ShowAdsHome;
import com.anass.jplus.Config.Animator;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Fragments.Jsocity;
import com.anass.jplus.Fragments.home;
import com.anass.jplus.Fragments.more;

import com.anass.jplus.Fragments.search;
import com.anass.jplus.Models.AdsCenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

public class MainActivity extends AppCompatActivity {

    static public void clicksound(Activity activity){
       MediaPlayer mediaPlayer = MediaPlayer.create(activity,R.raw.click);
        mediaPlayer.start();
    }


    private long pressedTime;
    private Toast backToast;
    private SharedPreferences sp;



    ImageView statusBar;
    ImageView navigationBar;
    BottomNavigationView navi;
    FragmentManager fragmentManager;


    Jsocity jsocity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_main);
        StartAppSDK.init(this, "201688868", false);
        StartAppSDK.enableReturnAds(false);
        StartAppAd.disableSplash();
        sp = getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);

        statusBar = findViewById(R.id.status_bar);
        navigationBar = findViewById(R.id.navigation_bar);
        statusBar.getLayoutParams().height = getStatusBarHeight();
        navigationBar.getLayoutParams().height = getNavigationBarHeight();


        jsocity = new Jsocity();

        navi = findViewById(R.id.navi);

        InslizBottonBar(savedInstanceState);


        if (AppConfig.adsCenter != null){

            AdsCenter  adsCenter = AppConfig.adsCenter;

            if (adsCenter.getImageDialogAds() != null){

                startActivity(new Intent( MainActivity.this, ShowAdsHome.class ));

            }

        }

    }





    private void InslizBottonBar(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            navi.setSelectedItemId(R.id.foru);


            fragmentManager = getSupportFragmentManager();
            home homeFragment = new home();

            fragmentManager.beginTransaction().replace(R.id.fream, homeFragment)
                    .commit();


        }



        MenuItemCompat.setIconTintMode(navi.getMenu().getItem(3), null);

        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                final Fragment[] fragment = {null};
                switch (item.getItemId()) {
                    case R.id.foru:
                        clicksound(MainActivity.this);
                        fragment[0] = new home();
                        break;

                    case R.id.movie:
                        clicksound(MainActivity.this);
                        fragment[0] = new search();
                        break;

                    case R.id.search:
                        clicksound(MainActivity.this);
                        fragment[0] = new Jsocity();
                        break;
                    case R.id.profi:
                        clicksound(MainActivity.this);
                        fragment[0] = new more();
                        //ShowProfileDialog();


                        break;

                }

                if (fragment[0] != null) {
                    fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction().replace(R.id.fream, fragment[0])
                            .commit();
                }

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed( );
        GetOut_press();
    }

    private void ShowProfileDialog() {


            final Dialog dialog = new Dialog(MainActivity.this, R.style.MyAlertDialogProfile);
            dialog.setContentView(R.layout.showprofilelayout);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = MATCH_PARENT;
            lp.height = MATCH_PARENT;





            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }


    private void GetOut_press() {


            final Dialog dialog = new Dialog(MainActivity.this, R.style.MyAlertDialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_exit);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = MATCH_PARENT;
            lp.height = MATCH_PARENT;

            LinearLayout addtofav_btn = dialog.findViewById(R.id.addtofav_btn);
            LinearLayout copylink_btn = dialog.findViewById(R.id.copylink_btn);
        addtofav_btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        copylink_btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });








            dialog.show();
            dialog.getWindow().setAttributes(lp);


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

    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
    Point navigationBarSize = new Point();
    public int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarSize.y = getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarSize.y;
    }

    public void hideStatusBar(){

        if(statusBar.getAlpha() > 0) {
            Animator animator = new Animator();
            animator.AnimateOpacity(statusBar, 0, 400);
            animator.setAnimationListener(new Animator.AnimationListener() {
                @Override
                public void onAnimationFinish() {
                    statusBar.setVisibility(View.GONE);

                }
            });
        }
    }
    public void showStatusBar(){
        if(statusBar.getAlpha() < 1) {
            Animator animator = new Animator();
            animator.AnimateOpacity(statusBar, 1, 400);
            animator.setAnimationListener(new Animator.AnimationListener() {
                @Override
                public void onAnimationFinish() {
                    statusBar.setVisibility(View.VISIBLE);

                }
            });
        }
    }


}