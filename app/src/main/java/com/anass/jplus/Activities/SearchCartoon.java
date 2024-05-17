package com.anass.jplus.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.anass.jplus.Adapters.SeriesAdapter;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.viewmodel.getAlltvsViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchCartoon extends AppCompatActivity {

    SeriesAdapter moviesAdapter;

    TextInputEditText searchview;
    TextView noresult;
    RecyclerView recentepisod_rv,cast_rv;

    List<CartoonModel> moviemodel = new ArrayList<>(  );

    getAlltvsViewModel movieListViewModel;

    int curentpage = 1;
    int totalpage = 1;

    RadioGroup grouiprado;
    public String searchqe = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cartoon);

        movieListViewModel = new ViewModelProvider(this).get(getAlltvsViewModel.class);

        initialviews();
        grouiprado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId){
                    case R.id.tv_select:
                        observAnyChanges(searchqe,"tv");

                        break;
                    case R.id.movie_select:
                        observAnyChanges(searchqe,"movie");

                        break;
                    case R.id.comic_select:
                        observAnyChanges(searchqe,"comic");

                        break;


                }
            }
        });

        searchview.setOnEditorActionListener(new TextView.OnEditorActionListener( ) {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    noresult.setVisibility(View.VISIBLE);
                    noresult.setText("بدأ البحث.....");
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        recentepisod_rv.setLayoutManager(new GridLayoutManager(this, 2));
        observAnyChanges(searchqe,"tv");


    }



    private void performSearch() {

        searchview.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchview.getWindowToken(), 0);

        searchqe = Objects.requireNonNull(searchview.getText( )).toString();

        observAnyChanges(searchqe,"tv");

    }

    public void observAnyChanges(String search,String gener) {
        Log.d("TAG", "observAnyChanges: "+searchqe);

        movieListViewModel.Search(search).observe(this, new Observer<CartoonResponse>( ) {
            @Override
            public void onChanged(CartoonResponse cartoonResponse) {
                if (cartoonResponse != null){
                    if (cartoonResponse.getTvs() != null){
                        noresult.setVisibility(View.GONE);
                        moviemodel.clear();
                        for (CartoonModel car:cartoonResponse.getTvs()) {
                            if (Objects.equals(car.getCountentType( ), gener)){
                                moviemodel.add(car);
                            }

                        }

                        moviesAdapter = new SeriesAdapter(SearchCartoon.this, moviemodel,9);
                        moviesAdapter.setLimit(moviemodel.size());
                        recentepisod_rv.setAdapter(moviesAdapter);
                        for (CartoonModel cc: moviemodel ) {
                            Log.d("TAG", "onChanged: "+cc.getTitle());

                        }

                    }

                }
            }
        });




    }

    private void initialviews() {


        recentepisod_rv = findViewById(R.id.recentepisod_rv);
        searchview = findViewById(R.id.searchinput);
        noresult =findViewById(R.id.noresult);
        grouiprado = findViewById(R.id.grouiprado);
        cast_rv = findViewById(R.id.cast_rv);



    }



}