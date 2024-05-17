package com.anass.jplus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.Models.ChapterModel;
import com.anass.jplus.Models.PagesModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.ChapterResponse;
import com.anass.jplus.backend.Responses.PagesResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchChapter extends AppCompatActivity {


    private ApiService apiService;
    private String chapterID;

    LinearLayout myspiner;
    ViewPager2 viewPager;
    ProgressBar progreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_chapter);

        apiService = ApiClient.getRetrofit( ).create(ApiService.class);
        ChapterModel chapterModel = (ChapterModel) getIntent().getSerializableExtra("chapter");
        myspiner = findViewById(R.id.myspiner);
        viewPager = findViewById(R.id.viewPager);
        progreed = findViewById(R.id.progreed);


        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        Log.d("TAG", "onCreate: "+chapterModel.getId());


        getPages(chapterModel);
        getChapters(chapterModel);

    }

    private void getChapters(ChapterModel chapterModel) {

        apiService.chapters(chapterModel.getComic().getId()).enqueue(new Callback<ChapterResponse>( ) {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {

                if (response.body() != null){

                    myspiner.setOnClickListener(new View.OnClickListener( ) {
                        @Override
                        public void onClick(View view) {
                            List<ChapterModel> pagesModels = response.body().getChapterModels();

                            showPagesDialog(pagesModels);

                        }
                    });

                }



            }

            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {

            }
        });


    }

    private void getPages(ChapterModel chapterModel) {

        apiService.pages(chapterModel.getId()).enqueue(new Callback<PagesResponse>( ) {
            @Override
            public void onResponse(Call<PagesResponse> call, Response<PagesResponse> response) {

                if (response.body() != null){
                    progreed.setVisibility(View.GONE);
                    PagesAdapter pagesAdapter = new PagesAdapter(response.body().getChapterModels());
                    viewPager.setAdapter(pagesAdapter);


                }


            }

            @Override
            public void onFailure(Call<PagesResponse> call, Throwable t) {

            }
        });



    }

    public class PagesAdapter extends RecyclerView.Adapter<PagesAdapter.PageViewHolder> {

        private List<PagesModel> pagesList;

        public PagesAdapter(List<PagesModel> pagesList) {
            this.pagesList = pagesList;
        }

        @NonNull
        @Override
        public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false);
            return new PageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
            PagesModel currentPage = pagesList.get(position);


            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // mDblTapScale in PageImageView is 1.5 currently, so set this as our limit
            int max = Math.round(1.5f * Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels));

            Picasso.get().load(currentPage.getDirectLink())
                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .tag(WatchChapter.this)
                    .resize(max, max)
                    .centerInside()
                    .onlyScaleDown()
                    .into(holder.photoview);

        }

        @Override
        public int getItemCount() {
            return pagesList.size();
        }

        public  class PageViewHolder extends RecyclerView.ViewHolder {
            PhotoView photoview;

            public PageViewHolder(@NonNull View itemView) {
                super(itemView);
                photoview = itemView.findViewById(R.id.photoview);
                // Initialize other views as needed
            }
        }
    }

    private void showPagesDialog(List<ChapterModel> pagesModels) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WatchChapter.this); // Replace 'this' with your activity or context
        builder.setTitle("الأعداد");

        // Create a string array to store titles
        String[] pageTitles = new String[pagesModels.size()];
        for (int i = 0; i < pagesModels.size(); i++) {
            pageTitles[i] = pagesModels.get(i).getTitle();
        }

        builder.setItems(pageTitles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ChapterModel selectedPage = pagesModels.get(which);
                getPages(selectedPage);

                
                
            }
        });

        builder.setNegativeButton("إلغاء", null); // Optional cancel button

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}