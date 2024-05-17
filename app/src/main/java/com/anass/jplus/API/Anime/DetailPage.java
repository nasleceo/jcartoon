package com.anass.jplus.API.Anime;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.anass.jplus.Config.Animator;
import com.anass.jplus.Models.CastModel;
import com.anass.jplus.R;
import com.anasskikanime.models.AnimeModel;
import com.anasskikanime.models.GenersModel;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import anasskikanime.Anassget;
import anasskikanime.InterFaces.MyInterFace;

public class DetailPage extends AppCompatActivity {

    ImageView statusBar;
    ImageView navigationBar,backch;
    ImageView anime_cover,anime_poster,changelike,eye,later;
    TextView drama_name,Story,genertxt,epeorfilmtxt,liketxt;
    LinearLayout showall,likeit,addtowatchnow,addtolater;
    RelativeLayout goepisode,addtomylistview,removetomylistview;

    String anime_name;
    String anime_year;
    String anime_type;
    String anime_cover_id;
    ExecutorService executorService;

    Anassget AnassAnimeGetter;

    ArrayList<String> anime_epList;
    ArrayList<String> anime_tags;
    List<CastModel> castModels;


    LinearLayoutManager layoutManager;

    NestedScrollView nestedScrollView;

    AppBarLayout appBarLayout;

    boolean favorited;

    String animeInfo = "";

    DramaDB dramaDB;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        dramaDB = new DramaDB(this);
        executorService =  Executors.newSingleThreadExecutor( );
        AnassAnimeGetter = new Anassget();

        statusBar = findViewById(R.id.status_bar);
        liketxt = findViewById(R.id.liketxt);
        addtowatchnow = findViewById(R.id.addtowatchnow);
        addtolater = findViewById(R.id.addtolater);
        navigationBar = findViewById(R.id.navigation_bar);
        anime_cover = findViewById(R.id.anime_cover);
        goepisode = findViewById(R.id.goepisode);
        addtomylistview = findViewById(R.id.addtomylistview);
        removetomylistview = findViewById(R.id.removetomylistview);

        genertxt = findViewById(R.id.genertxt);
        showall = findViewById(R.id.showall);
        likeit = findViewById(R.id.likeit);
        Story = findViewById(R.id.anime_story);
        epeorfilmtxt = findViewById(R.id.epeorfilmtxt);
        anime_poster = findViewById(R.id.anime_poster);
        changelike = findViewById(R.id.changelike);
        eye = findViewById(R.id.eye);
        later = findViewById(R.id.later);
        drama_name = findViewById(R.id.drama_name);
        backch = findViewById(R.id.backch);


        statusBar.getLayoutParams().height = getStatusBarHeight();
        navigationBar.getLayoutParams().height = getNavigationBarHeight();
        hideStatusBar();


        nestedScrollView = findViewById(R.id.scroll_episodes);



        backch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        GetIntentsAndExtract();

    }

    private void GetIntentsAndExtract() {
        AnimeModel dramaModel = new AnimeModel();

        String img = getIntent().getStringExtra("img");
        String title = getIntent().getStringExtra("title");
        String link = getIntent().getStringExtra("link");
        String type = getIntent().getStringExtra("type");
        drama_name.setText(title);



        epeorfilmtxt.setText("الحلقات");
        dramaModel.setTitle(title);
        dramaModel.setPageLink(link);
        dramaModel.setImg(img);
        dramaModel.setType(type);
        dramaModel.setStatus("ss");

        CheckLikeStat(link,"ss");


        likeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dramaModel.setTitle(title);
                dramaModel.setPageLink(link);
                dramaModel.setImg(img);
                dramaModel.setType(type);
                dramaModel.setStatus("like");

                Likeit(dramaModel);
                CheckLikeStat(link,"ss");
            }
        });

        addtowatchnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dramaModel.setTitle(title);
                dramaModel.setPageLink(link);
                dramaModel.setImg(img);
                dramaModel.setType(type);
                dramaModel.setStatus("watchit");

                watchit(dramaModel);
                CheckLikeStat(link,"watchit");
            }
        });
        addtolater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dramaModel.setTitle(title);
                dramaModel.setPageLink(link);
                dramaModel.setImg(img);
                dramaModel.setType(type);
                dramaModel.setStatus("laterwatch");

                laterwatch(dramaModel);
                CheckLikeStat(link,"watchit");
            }
        });

        Glide.with(DetailPage.this).load(img).into(anime_cover);
        Glide.with(DetailPage.this).load(img).into(anime_poster);

       ExtractInfo(link,type);

    }

    private void laterwatch(AnimeModel movieModel) {

        if (movieModel != null){
            String postkey = movieModel.getPageLink();

            if (dramaDB.checkIfDramaExists(postkey)){

                if (movieModel.getStatus().contains("laterwatch")){
                    boolean added = dramaDB.DeletDrama(postkey);
                    if (added){
                        Toast.makeText(DetailPage.this, "إزالة من قائمتي", Toast.LENGTH_SHORT).show();
                    }
                    later.setImageDrawable(getResources().getDrawable(R.drawable.timepast));
                    TextView dd = findViewById(R.id.addtolatertxt);
                    dd.setText(" إضافة الى \nالمشاهدة لاحقا");
                }



            }else {

                if (movieModel.getStatus().contains("laterwatch")){
                    boolean added = dramaDB.AddToDramaList(movieModel);
                    if (added){
                        Toast.makeText(DetailPage.this, "إضافة لقائمتي", Toast.LENGTH_SHORT).show();
                    }
                    later.setImageDrawable(getResources().getDrawable(R.drawable.clocknine));
                    TextView dd = findViewById(R.id.addtolatertxt);

                    dd.setText("إزالة من\nالمشاهدة لاحقا");

                }
            }


        }
    }

    private void watchit(AnimeModel movieModel) {

        if (movieModel != null){
            String postkey = movieModel.getPageLink();

            if (dramaDB.checkIfDramaExists(movieModel.getPageLink())){

                if (movieModel.getStatus().contains("watchit")){
                    boolean added = dramaDB.DeletDrama(postkey);
                    if (added){
                        Toast.makeText(DetailPage.this, "إزالة من قائمتي", Toast.LENGTH_SHORT).show();

                    }
                    eye.setImageDrawable(getResources().getDrawable(R.drawable.eyeclose));
                    TextView ff = findViewById(R.id.watchtxt);
                   ff.setText(" إضافة الى \nالمشاهدة حاليا");
                }



            }else {

                if (movieModel.getStatus().contains("watchit")){
                    boolean added = dramaDB.AddToDramaList(movieModel);
                    if (added){
                        Toast.makeText(DetailPage.this, "إضافة لقائمتي", Toast.LENGTH_SHORT).show();
                    }
                    eye.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    TextView ff = findViewById(R.id.watchtxt);

                    ff.setText("إزالة من\nالمشاهدة لاحقا");

                }
                }


        }
    }

    private void CheckLikeStat(String link,String type) {

            if (dramaDB.checkIfDramaExists(link))
        {


            if (type.equals("watchit")){

                TextView ff = findViewById(R.id.watchtxt);

                eye.setImageDrawable(getResources().getDrawable(R.drawable.eyeclose));
                ff.setText("إزالة من\nالمشاهدة حاليا");


            }else if (type.equals("laterwatch")){

                TextView dd = findViewById(R.id.addtolatertxt);

                later.setImageDrawable(getResources().getDrawable(R.drawable.clocknine));
                dd.setText(" إضافة الى \nالمشاهدة حاليا");


            }else if (type.equals("like")){


                changelike.setImageDrawable(getResources().getDrawable(R.drawable.heartfill));
                liketxt.setText("إزالة من\nالمفضلة");


            }



            }else{
                if (type.equals("watchit")){
                    eye.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    TextView ff = findViewById(R.id.watchtxt);
                    ff.setText(" إضافة الى \nالمشاهدة حاليا");

                }else if (type.equals("laterwatch")){
                    later.setImageDrawable(getResources().getDrawable(R.drawable.timepast));
                    TextView dd = findViewById(R.id.addtolatertxt);
                    dd.setText(" إضافة الى \nالمشاهدة لاحقا");

                }else  if (type.equals("like")){
                    changelike.setImageDrawable(getResources().getDrawable(R.drawable.heartcrack));
                    liketxt.setText("إضافة الى\nالمفضلة");
                }




            }

        }

    private void Likeit(AnimeModel movieModel) {

        if (movieModel != null){
            String postkey = movieModel.getPageLink();

            if (dramaDB.checkIfDramaExists(movieModel.getPageLink())){

                if (movieModel.getStatus().contains("like")){
                    boolean added = dramaDB.DeletDrama(postkey);
                    if (added){
                        Toast.makeText(DetailPage.this, "إزالة من قائمتي", Toast.LENGTH_SHORT).show();

                    }
                    changelike.setImageDrawable(getResources().getDrawable(R.drawable.heartcrack));
                    liketxt.setText("إضافة الى\nالمفضلة");
                }



            }else {
                if (movieModel.getStatus().contains("like")){
                    boolean added = dramaDB.AddToDramaList(movieModel);
                    if (added){
                        Toast.makeText(DetailPage.this, "إضافة لقائمتي", Toast.LENGTH_SHORT).show();

                    }
                    changelike.setImageDrawable(getResources().getDrawable(R.drawable.heartfill));
                    liketxt.setText("إزالة من\nالمفضلة");

                }



            }
        }
    }
    String infos = "";
    String generss = "";

    private void ExtractInfo(String link, String type) {


        executorService.execute(new Runnable( ) {
            @Override
            public void run() {

                try {


                    AnassAnimeGetter.FetchXSanime().GetAnimeInfo(link, new MyInterFace.OnAnimeInfoGet( ) {
                        @Override
                        public void OnAnimeInfoGet(String title, String story, String img,
                                                   List<String> geners,
                                                   List<GenersModel> moreinfos,
                                                   List<GenersModel> Episodes) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    Story.setText(story);
                                    for (GenersModel inf:moreinfos) {
                                        infos += inf.getGenertitle()+  System.lineSeparator();
                                    }
                                    genertxt.setText(infos);
                                    for (String inf : geners) {
                                        Log.d("TAG", "run:  "+inf);
                                    }
                                    goepisode.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (link.contains("https://v.xsanime.com/movie/")){
                                                API.allEpesodes.clear();
                                                API.allEpesodes = Episodes;

                                                Log.d("TAG", "onClick: "+link);

                                                try {
                                                    AnassAnimeGetter.FetchAnimelek().GetServerFromLink(link, new MyInterFace.OnAnimeLinksGet( ) {
                                                        @Override
                                                        public void OnAnimeLinksGet(List<GenersModel> downloads, List<GenersModel> list1) {

                                                            runOnUiThread(new Runnable( ) {
                                                                @Override
                                                                public void run() {

                                                                    Log.d("TAG", "run: "+downloads.toString());
                                                                    Log.d("TAG", "run: "+list1.toString());


                                                                }
                                                            });

                                                        }
                                                    });
                                                } catch (IOException e) {

                                                }

                                            }else {
                                                API.allEpesodes.clear();
                                                API.allEpesodes = Episodes;


                                                for (GenersModel ff:Episodes
                                                     ) {
                                                    Log.d("TAG", "onClick: "+ff.getGenerlink());

                                                }


                                            }


                                        }
                                    });
                                    showall.setVisibility(View.VISIBLE);
                                    backch.setVisibility(View.VISIBLE);

                                }
                            });
                        }
                    });

                } catch (IOException e) {

                    Snackbar.make(findViewById(android.R.id.content),"صبيب الإنترنت ضعيف",Snackbar.LENGTH_LONG).setAction("حاول مجددا", new View.OnClickListener( ) {
                        @Override
                        public void onClick(View v) {

                            recreate();

                        }
                    }).show();

                }

            }
        });



    }

    private void Showunitiads() {
    }

    private ArrayList<String> Getgeners(String animelink) throws IOException {

        ArrayList<String> finals = new ArrayList<>();

        final Document doc = Jsoup.connect(animelink).timeout(20000).get();

        Elements body =  doc.select("div.anime-info-container");

        for(Element e : body.select("div.anime-info-left div.anime-details ul.anime-genres")) {

            String gener = e.select("ul.anime-genres li a").text();
            String finalgeners = gener.replace(" ", ",");
            finals.add(finalgeners);
        }

        Log.d("TAG", "Getgeners: "+ finals );

        return finals;
    }


    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
    public int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
    public void hideStatusBar(){
        if(statusBar.getAlpha() > 0) {
            Animator animator = new Animator();
            animator.AnimateOpacity(statusBar, 0, 300);
            animator.setAnimationListener(new Animator.AnimationListener() {
                @Override
                public void onAnimationFinish() {

                }
            });
        }
    }

}