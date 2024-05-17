package com.anass.jplus.Activities.MyList;


import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Adapters.MovieAdapter;
import com.anass.jplus.Config.HelperUtils;
import com.anass.jplus.Config.Utils;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.CartoonResponse;
import com.anass.jplus.backend.Responses.FavoutirResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mylist extends AppCompatActivity {

    TextView myempty;
    List<CartoonModel> dramafinal ;
    String[] listtype = {"المفضلة", "تم مشاهدتها","أرغب بمشاهدتها","أشاهدها حاليا","أكملها مستقبلا"};

    String[] listtype2 = {"favcartoon","watchdone","iwantwatch","watchnow","watchlater"};
    ImageView backch;
    GridView list_rv;

    MovieAdapter movieAdapter;
    ApiService apiService;

    AuthManager authManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_mylist);
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        TabLayout tabLayout = findViewById(R.id.tablay);

        backch =  findViewById(R.id.backch);
        list_rv =  findViewById(R.id.list_rv);
        myempty = findViewById(R.id.myempty);
        dramafinal = new ArrayList<>();

        Utils.FixColumnsWidth(list_rv,150,this);
        movieAdapter = new MovieAdapter(Mylist.this);

        backch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for (String title : listtype) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(title);
            tabLayout.addTab(tab);
        }

        getlist(listtype2[0]);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle tab selection change
                int position = tab.getPosition();
                // Implement your logic based on the selected tab
                switch (position) {
                    case 0:
                        getlist(listtype2[position]);
                        break;
                    case 1:
                        getlist(listtype2[position]);
                        break;
                    case 2:
                        getlist(listtype2[position]);
                        break;
                    case 3:
                        getlist(listtype2[position]);
                        break;
                    case 4:
                        getlist(listtype2[position]);
                        break;
                    // Add cases for other tabs as needed
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselection (if needed)
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselection (if needed)
            }
        });


    }

    private void getlist(String s) {



        apiService.getFavouritCartoon(authManager.getUserInfo().getId(),s).enqueue(new Callback<FavoutirResponse>( ) {
            @Override
            public void onResponse(Call<FavoutirResponse> call, Response<FavoutirResponse> response) {

                if (response.body() != null){

                        if (response.body().getFavourits( ) != null) {

                            movieAdapter.setmodels(response.body().getFavourits( ));
                            list_rv.setAdapter(movieAdapter);

                        }

                }


            }

            @Override
            public void onFailure(Call<FavoutirResponse> call, Throwable t) {

            }
        });

    }


    public static void changestatucolor(Activity activity) {
        Window window = activity.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(activity,R.color.amoled_primary_color));
        }
    }

}