package com.anass.jplus.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.anass.jplus.Adapters.CastAdapter;
import com.anass.jplus.Adapters.SeriesAdapter;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.Cast;
import com.anass.jplus.Models.CastModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.Responses.CastResponse;
import com.anass.jplus.backend.viewmodel.getAlltvsViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class Allcharacters extends AppCompatActivity {

    RecyclerView recentepisod_rv;
    CastAdapter moviesAdapter;
    List<CastModel> moviemodel   = new ArrayList<>( );
    ;
    ShimmerFrameLayout shimmmer;

    getAlltvsViewModel movieListViewModel;

    int curentpage = 1;
    int totalpage = 1;

    String place;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcharacters);

        recentepisod_rv = findViewById(R.id.recentepisod_rv);
        shimmmer = findViewById(R.id.shimmmer);

        movieListViewModel = new ViewModelProvider(this).get(getAlltvsViewModel.class);

        place = getIntent().getStringExtra("place");


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);

        recentepisod_rv.setLayoutManager(gridLayoutManager);
        observAnyChanges();


    }

    public void observAnyChanges() {




        movieListViewModel.allcastapi(curentpage).observe(this, new Observer<CastResponse>( ) {
            @Override
            public void onChanged(CastResponse castResponse) {
                if (castResponse != null) {
                    totalpage = castResponse.getTotal();
                    if (castResponse.getCasts( ) != null) {
                        shimmmer.stopShimmer( );
                        shimmmer.setVisibility(View.GONE);
                        moviesAdapter = new CastAdapter(Allcharacters.this, castResponse.getCasts( ));
                        moviesAdapter.settype(1);
                        moviesAdapter.setplace(place);
                        recentepisod_rv.setAdapter(moviesAdapter);

                    }

                }
            }
        });


    }
}