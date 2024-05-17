package com.anass.jplus.Fragments;

import static com.anass.jplus.Config.Utils.calculateNoOfColumns;
import static com.anass.jplus.MainActivity.clicksound;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.anass.jplus.API.ModelView.MovieListViewModel;
import com.anass.jplus.API.MovieApi;
import com.anass.jplus.API.Servicy;
import com.anass.jplus.Activities.ShowInfoActivity;
import com.anass.jplus.Adapters.MovieAdapter;
import com.anass.jplus.Adapters.SeriesAdapter;
import com.anass.jplus.Config.GridSpacingItemDecoration;
import com.anass.jplus.Config.Utils;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.viewmodel.getAlltvsViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.ArrayList;
import java.util.List;


public class series extends Fragment {


    RecyclerView recentepisod_rv;
    SeriesAdapter moviesAdapter;
    List<CartoonModel> moviemodel   = new ArrayList<>( );
    ;
    ShimmerFrameLayout shimmmer;
    RelativeLayout movie_list_btn;

    getAlltvsViewModel movieListViewModel;

    int curentpage = 1;
    int totalpage = 1;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_series, container, false);


        recentepisod_rv = view.findViewById(R.id.recentepisod_rv);
        shimmmer = view.findViewById(R.id.shimmmer);
        movie_list_btn = view.findViewById(R.id.movie_list_btn);

        movieListViewModel = new ViewModelProvider(getActivity()).get(getAlltvsViewModel.class);


        moviesAdapter = new SeriesAdapter(getActivity(), moviemodel,3);
        recentepisod_rv.setAdapter(moviesAdapter);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        recentepisod_rv.setLayoutManager(gridLayoutManager);
        recentepisod_rv.addOnScrollListener(new RecyclerView.OnScrollListener( ) {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recentepisod_rv.canScrollVertically(1)){
                    if (curentpage <= totalpage){
                        curentpage += 1;
                        observAnyChanges();
                    }

                }
            }
        });

        GetGener();
        observAnyChanges();
        return view;
    }

    private void GetGener() {

        movie_list_btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {




            }
        });

    }

    private void observAnyChanges() {

        movieListViewModel.getalltvs(curentpage).observe(getActivity( ), new Observer<CartoonResponse>( ) {
            @Override
            public void onChanged(CartoonResponse cartoonModels) {

                if (cartoonModels != null) {
                    totalpage = cartoonModels.getTotal();
                    if (cartoonModels.getTvs( ) != null) {
                        shimmmer.stopShimmer( );
                        shimmmer.setVisibility(View.GONE);
                        int oldCount = moviemodel.size();

                        moviemodel.addAll(cartoonModels.getTvs( ));
                        moviesAdapter.notifyItemRangeInserted(oldCount,moviemodel.size());
                        moviesAdapter.setLimit(oldCount+moviemodel.size());

                    }

                }
            }
        });


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.GoBACK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound(getActivity());
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


}


