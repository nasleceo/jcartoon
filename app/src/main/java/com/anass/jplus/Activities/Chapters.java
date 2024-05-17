package com.anass.jplus.Activities;

import static com.anass.jplus.Activities.MyList.Mylist.changestatucolor;
import static com.anass.jplus.Config.config.SETTINGS_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.Config.ServerController;
import com.anass.jplus.Config.getComicsnextback;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.ChapterModel;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.Responses.ChapterResponse;
import com.anass.jplus.backend.Responses.PagesResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.bumptech.glide.Glide;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.startapp.sdk.adsbase.StartAppAd;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chapters extends AppCompatActivity {


    TextView episode;
    RecyclerView rectmovies;
    ProgressBar progreed;
    private SharedPreferences sp;
    StartAppAd startAppAd;
    List<ChapterModel> chapterModels;
    ImageView backch;

    String epetitle;
    ServerController serverController;
    CartoonModel comic;


    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);


        changestatucolor(this);
        apiService = ApiClient.getRetrofit( ).create(ApiService.class);
        sp = getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);

        serverController = new ServerController(Chapters.this);

        rectmovies = findViewById(R.id.rectmovies);
        progreed = findViewById(R.id.progreed);
        episode = findViewById(R.id.episode);
        backch = findViewById(R.id.backch);

        StartAppAd.init(this, "107796986", "210473406");
        startAppAd = new StartAppAd(this);
        startAppAd.loadAd(StartAppAd.AdMode.VIDEO);

        InitiaStrings( );

        backch.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                finish( );
            }
        });


        GetChapters();




    }

    private void InitiaStrings() {

        comic = (CartoonModel) getIntent( ).getSerializableExtra("comic");

    }

    private void GetChapters() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Chapters.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rectmovies.setLayoutManager(linearLayoutManager);
        rectmovies.setNestedScrollingEnabled(false);

        progreed.setVisibility(View.VISIBLE);
        chapterModels = new ArrayList<>( );

        apiService.chapters(comic.getId()).enqueue(new Callback<ChapterResponse>( ) {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {

                if (response.body() != null){

                    progreed.setVisibility(View.GONE);
                    int size = response.body().getChapterModels().size();
                    episode.setText(" الإعداد ("+size+") ");

                    rectmovies.setAdapter(new ChaptersAdapter(
                            response.body().getChapterModels(),
                            Chapters.this));


                }



            }

            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {

            }
        });





    }
    public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.myviewholder> {

        List<ChapterModel> chaptermodelList;
        Activity context;
        String title;

        public ChaptersAdapter(List<ChapterModel> chaptermodelList, Activity context) {
            this.chaptermodelList = chaptermodelList;
            this.context = context;
        }

        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext( )).inflate(R.layout.issue_item, parent, false);
            return new myviewholder(view);
        }

        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(@NonNull myviewholder holder, int position) {
            ChapterModel c = chaptermodelList.get(position);


            epetitle = c.getTitle( );

            holder.episode.setText("  العدد  "+epetitle);

            holder.click.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    context.startActivity(new Intent( context,WatchChapter.class ).putExtra("chapter",c));

                }});
            holder.download_epe.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View v) {


                }
            });


        }


        @Override
        public int getItemCount() {
            return chaptermodelList.size( );
        }

        public class myviewholder extends RecyclerView.ViewHolder {

            TextView episode, titleof;
            ImageView click, download_epe;
            LinearProgressIndicator showWhems;

            public myviewholder(@NonNull View itemView) {
                super(itemView);

                episode = itemView.findViewById(R.id.episode);
                titleof = itemView.findViewById(R.id.anime);
                click = itemView.findViewById(R.id.click);
                download_epe = itemView.findViewById(R.id.download_epe);
                showWhems = itemView.findViewById(R.id.showWhems);


            }


        }
    }


}