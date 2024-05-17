package com.anass.jplus.Activities;

import static com.anass.jplus.Activities.Comments.ShowsnackLogin;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.Activities.MyList.savedpostdownload;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    private ApiService apiService;

    CircleImageView userimg;
    private AuthManager authManager;
    ImageView verify,facebookacc,instaacc,twitteracc,backch,morenow;
    TextView nameuser,userid,addate,profildesc,commentcount,postscount,followescount,followingcount
            ,nolist;
    RelativeLayout postesgo,flollownow,unflollow,progreswait;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_profile);

        apiService = ApiClient.getRetrofit().create(ApiService.class);
        userModel = (UserModel) getIntent().getSerializableExtra("user");
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        inti();
        getinfo();




    }

    private void getinfo() {

        Glide.with(Profile.this).load(userModel.getProfil()).into(userimg);
        userimg.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, ViewPhoto.class);
                intent.putExtra("photo", userModel.getProfil( ));
                startActivity(intent);
            }
        });

        if (userModel.getIsverified() == 1){
            verify.setVisibility(View.VISIBLE);
        }

        nameuser.setText(userModel.getName());
        userid.setText("@"+userModel.getUserspecialName());
        profildesc.setText(userModel.getProfileDesc());
        postesgo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                startActivity(new Intent( Profile.this, savedpostdownload.class )
                        .putExtra("type","userpost").putExtra("userid",userModel));

            }
        });

        if (authManager.getUserInfo().getId() > 0){
            checkfollowing();
        }



        flollownow.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {


                if (authManager.getUserInfo().getId() > 0){

                    if (userModel.getId() != authManager.getUserInfo().getId() ){

                        flollownow.setVisibility(View.GONE);
                        progreswait.setVisibility(View.VISIBLE);
                        apiService.Followunfollowuser(String.valueOf(authManager.getUserInfo().getId()),String.valueOf(userModel.getId())).enqueue(new Callback<MessageResponse>( ) {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                                if (response.body() != null){
                                    String msj = response.body().getMessage();


                                    if (msj.equals("follow")){
                                        progreswait.setVisibility(View.GONE);
                                        unflollow.setVisibility(View.VISIBLE);
                                    }else {
                                        unflollow.setVisibility(View.GONE);
                                        progreswait.setVisibility(View.GONE);
                                        flollownow.setVisibility(View.VISIBLE);
                                    }


                                }


                            }

                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {

                            }
                        });

                    }


                }else {
                    ShowsnackLogin(view,Profile.this);

                }



            }
        });

        unflollow.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (authManager.getUserInfo().getId() > 0){
                    unflollow.setVisibility(View.GONE);
                    progreswait.setVisibility(View.VISIBLE);
                    apiService.Followunfollowuser(String.valueOf(authManager.getUserInfo().getId()),String.valueOf(userModel.getId())).enqueue(new Callback<MessageResponse>( ) {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                            if (response.body() != null){
                                String msj = response.body().getMessage();


                                if (msj.equals("follow")){
                                    progreswait.setVisibility(View.GONE);
                                    unflollow.setVisibility(View.VISIBLE);
                                }else {
                                    unflollow.setVisibility(View.GONE);
                                    progreswait.setVisibility(View.GONE);
                                    flollownow.setVisibility(View.VISIBLE);
                                }


                            }


                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                        }
                    });


                }else {
                    ShowsnackLogin(view,Profile.this);

                }
            }
        });


        getfollowings(userModel);
        getfollowers(userModel);
        getpostcount(userModel);
        getcoumcount(userModel);


    }

    private void checkfollowing() {


        apiService.CheckFollow(authManager.getUserInfo().getId(),userModel.getId()).enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.body() != null){
                    String msj = response.body().getMessage();

                    if (msj.equals("unfollow")){

                        flollownow.setVisibility(View.GONE);
                        unflollow.setVisibility(View.VISIBLE);
                    }else {
                        unflollow.setVisibility(View.GONE);
                        flollownow.setVisibility(View.VISIBLE);
                    }


                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });



    }


    private void getcoumcount(UserModel userModel) {

        apiService.commentcount(userModel.getId()).enqueue(new Callback<Long>( ) {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.body() != null){
                    commentcount.setText(response.body()+"");
                }


            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });


    }

    private void getpostcount(UserModel userModel) {

        apiService.postcount(userModel.getId()).enqueue(new Callback<Long>( ) {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.body() != null){
                    postscount.setText(response.body()+"");
                }


            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });

    }

    private void getfollowers(UserModel userModel) {

        apiService.getfollowers(userModel.getId()).enqueue(new Callback<Long>( ) {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.body() != null){
                    followescount.setText(response.body()+"");
                }


            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });

    }

    private void getfollowings(UserModel userModel) {

        apiService.getfollowings(userModel.getId()).enqueue(new Callback<Long>( ) {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.body() != null){
                    followingcount.setText(response.body()+"");
                }

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });

    }

    public void inti() {

        userimg = findViewById(R.id.userimg);
        verify = findViewById(R.id.verify);
        nameuser = findViewById(R.id.nameuser);
        userid = findViewById(R.id.userid);
        addate = findViewById(R.id.addate);
        profildesc = findViewById(R.id.profildesc);
        postesgo = findViewById(R.id.postesgo);
        flollownow = findViewById(R.id.flollownow);
        unflollow = findViewById(R.id.unflollow);
        progreswait = findViewById(R.id.progreswait);
        commentcount = findViewById(R.id.commentcount);
        postscount = findViewById(R.id.postscount);
        followescount = findViewById(R.id.followescount);
        followingcount = findViewById(R.id.followingcount);
        facebookacc = findViewById(R.id.facebookacc);
        instaacc = findViewById(R.id.instaacc);
        twitteracc = findViewById(R.id.twitteracc);
        nolist = findViewById(R.id.nolist);
        backch = findViewById(R.id.backch);
        morenow = findViewById(R.id.morenow);

        backch.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        morenow.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(Profile.this, view, Gravity.END);

                // Inflate the menu resource (assuming you have a menu resource file)
                popupMenu.getMenuInflater().inflate(R.menu.your_popup_menu, popupMenu.getMenu());

                // Set a click listener for the menu items
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item clicks
                        switch (item.getItemId()) {
                            case R.id.report_item:
                                // Handle the "تبليغ" (Report) item click
                                // Add your code for reporting here
                                return true;
                            // Add other menu items if needed
                            default:
                                return false;
                        }
                    }
                });

                // Show the PopupMenu
                popupMenu.show();

            }
        });

    }
}