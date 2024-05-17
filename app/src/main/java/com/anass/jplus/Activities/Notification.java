package com.anass.jplus.Activities;

import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Adapters.CommentAdapter;
import com.anass.jplus.Adapters.NotificationAdapter;
import com.anass.jplus.Models.NotificationModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.NotificationResponse;
import com.anass.jplus.backend.interfaces.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity {

    ImageView navigationBar,gotooback;
    RecyclerView coments_rv;

    ApiService apiService;
    AuthManager authManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_notification);
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        inital();


    }

    private void inital() {

        navigationBar = findViewById(R.id.navigation_bar);
        navigationBar.getLayoutParams().height = getNavigationBarHeight();
        coments_rv = findViewById(R.id.coments_rv);
        gotooback = findViewById(R.id.gotooback);
        gotooback.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        coments_rv.setLayoutManager(new LinearLayoutManager(Notification.this,LinearLayoutManager.VERTICAL,false));
        coments_rv.setNestedScrollingEnabled(false);


        apiService.notifications(authManager.getUserInfo().getId()).enqueue(new Callback<NotificationResponse>( ) {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.body() != null){

                    if (response.body().getGetnotifactions() != null){

                        NotificationAdapter notificationAdapter = new NotificationAdapter(Notification.this,response.body().getGetnotifactions());
                        coments_rv.setAdapter(notificationAdapter);

                    }


                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

            }
        });





    }

    Point navigationBarSize = new Point();
    public int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarSize.y = getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarSize.y;
    }
}