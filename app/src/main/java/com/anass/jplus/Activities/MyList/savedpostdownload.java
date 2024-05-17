package com.anass.jplus.Activities.MyList;

import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;
import static com.anass.jplus.Config.HelperUtils.getYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Adapters.CastAdapter;
import com.anass.jplus.Adapters.ContinuePlayingListAdepter;
import com.anass.jplus.Adapters.NewsAdapter;
import com.anass.jplus.Adapters.PostAdapter;
import com.anass.jplus.DB.resume_content.ResumeContent;
import com.anass.jplus.DB.resume_content.ResumeContentDatabase;
import com.anass.jplus.Models.ContinuePlayingList;
import com.anass.jplus.Models.NewsModel;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.CastResponse;
import com.anass.jplus.backend.Responses.PostResponse;
import com.anass.jplus.backend.Responses.newsresponse;
import com.anass.jplus.backend.interfaces.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class savedpostdownload extends AppCompatActivity {


    TextView texttypeof;
    static public  TextView myempty;

    ImageView backch;
    RecyclerView recy_all;
    String type;

    ApiService apiService;
    AuthManager authManager;

    ResumeContentDatabase db;

    PostAdapter postAdapter;

    public static List<ResumeContent> resumeContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setContentView(R.layout.activity_savedpostdownload);
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));


        intial();


    }

    private void intial() {

        texttypeof = findViewById(R.id.texttypeof);
        recy_all = findViewById(R.id.recy_all);
        myempty = findViewById(R.id.myempty);
        backch = findViewById(R.id.backch);
        backch.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        type = getIntent().getStringExtra("type");

        switch (type){
            case "countinu":
                texttypeof.setText("متابعة المشاهدة");
                getCountinuWatch();
                break;
            case "mypost":
                texttypeof.setText("منشوراتي");
                getMypost();
                break;
            case "mycast":
                texttypeof.setText("شخصياتي");
                getMycast();
                break;
            case "savedpost":
                texttypeof.setText("المنشورات المحفوظة");
                getSavedpost();
                break;
            case "news":
                texttypeof.setText("الأخبار");
                getNews();
                break;
            case "userpost":
                texttypeof.setText("المنشورات");
                getuserpost();
                break;
        }



    }

    private void getuserpost() {

       UserModel user = (UserModel) getIntent().getSerializableExtra("userid");
        apiService.myposts(user.getId()).enqueue(new Callback<PostResponse>( ) {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                if (response.body() != null){

                    if (response.body().getGetposts() != null){

                        postAdapter = new PostAdapter(savedpostdownload.this,response.body().getGetposts());
                        recy_all.setLayoutManager(new LinearLayoutManager(savedpostdownload.this,  RecyclerView.VERTICAL, false));
                        recy_all.setAdapter(postAdapter);
                    }


                }




            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });


    }

    private void getNews() {

        apiService.allnews().enqueue(new Callback<newsresponse>( ) {
            @Override
            public void onResponse(Call<newsresponse> call, Response<newsresponse> response) {
                if (response.body() != null){

                    NewsAdapter newsAdapter = new NewsAdapter(savedpostdownload.this,response.body().getData());
                    recy_all.setLayoutManager(new LinearLayoutManager(savedpostdownload.this,  RecyclerView.VERTICAL, false));
                    recy_all.setAdapter(newsAdapter);

                }
            }

            @Override
            public void onFailure(Call<newsresponse> call, Throwable t) {

            }
        });

    }

    private void getSavedpost() {

        apiService.getfavouritPosts(authManager.getUserInfo().getId()).enqueue(new Callback<PostResponse>( ) {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                if (response.body() != null){

                    if (response.body().getGetposts() != null){

                        postAdapter = new PostAdapter(savedpostdownload.this,response.body().getGetposts());
                        recy_all.setLayoutManager(new LinearLayoutManager(savedpostdownload.this,  RecyclerView.VERTICAL, false));
                        recy_all.setAdapter(postAdapter);
                    }


                }




            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });

    }

    private void getMycast() {

        apiService.getFavouritcast(authManager.getUserInfo().getId()).enqueue(new Callback<CastResponse>( ) {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {

                if (response.body() != null){

                    if (response.body().getCasts() != null){

                        CastAdapter ss = new CastAdapter(savedpostdownload.this,response.body().getCasts());
                        recy_all.setLayoutManager(new GridLayoutManager(savedpostdownload.this,  3,RecyclerView.VERTICAL, false));
                        recy_all.setAdapter(ss);
                    }


                }




            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });


    }

    private void getMypost() {

        apiService.myposts(authManager.getUserInfo().getId()).enqueue(new Callback<PostResponse>( ) {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                if (response.body() != null){

                    if (response.body().getGetposts() != null){

                        postAdapter = new PostAdapter(savedpostdownload.this,response.body().getGetposts());
                        recy_all.setLayoutManager(new LinearLayoutManager(savedpostdownload.this,  RecyclerView.VERTICAL, false));
                        recy_all.setAdapter(postAdapter);
                    }


                }




            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });


    }


    private void getCountinuWatch() {

        db = ResumeContentDatabase.getDbInstance(this.getApplicationContext());
        resumeContents = db.resumeContentDao().getResumeContents();
        loadResumeContents(resumeContents);

    }

    void loadResumeContents(List resumeContents) {
        List<ResumeContent> mData = resumeContents;
        List<ContinuePlayingList> continuePlayingList = new ArrayList<>();

        for (int i=0; i<mData.size(); i++) {

            int id = mData.get(i).getId();
            int contentID = mData.get(i).getContent_id();

            String contentType = mData.get(i).getContent_type();

            String name = mData.get(i).getName();

            int year = mData.get(i).getYear();

            String poster = mData.get(i).getPoster();
            String sourceType = mData.get(i).getSource_type();
            String sourceUrl = mData.get(i).getSource_url();
            String type = mData.get(i).getContent_type();
            long position = mData.get(i).getPosition();
            long duration = mData.get(i).getDuration();

            continuePlayingList.add(new ContinuePlayingList(id, contentID, name, year, poster, sourceType, sourceUrl, type, contentType, position, duration));

            ContinuePlayingListAdepter myadepter = new ContinuePlayingListAdepter(savedpostdownload.this, continuePlayingList);
            recy_all.setLayoutManager(new GridLayoutManager(savedpostdownload.this, 1, RecyclerView.HORIZONTAL, false));
            recy_all.setAdapter(myadepter);

        }

    }

}