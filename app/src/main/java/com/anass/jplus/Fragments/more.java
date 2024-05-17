package com.anass.jplus.Fragments;

import static android.content.Context.MODE_PRIVATE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.anass.jplus.Activities.Comments.ShowsnackLogin;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anass.jplus.Activities.ADcenter.WelcomPage;
import com.anass.jplus.Activities.Allcharacters;
import com.anass.jplus.Activities.Auth.LoginTojcartoon;
import com.anass.jplus.Activities.EditProfil;
import com.anass.jplus.Activities.Jdownload.DownloadList;
import com.anass.jplus.Activities.MyList.Mylist;
import com.anass.jplus.Activities.MyList.savedpostdownload;
import com.anass.jplus.Activities.Profile;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Activities.jcartoonroom.AllRooms;
import com.anass.jplus.Activities.jcartoonroom.Myrooms;
import com.anass.jplus.MainActivity;
import com.anass.jplus.Models.RoomModel;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.anass.jplus.SplashScreen;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.interfaces.ApiService;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class more extends Fragment {
    AuthManager authManager;
    private ApiService apiService;

    ImageView goback,navigationBar;
    CircleImageView gotojcartoonprof,user_profil_photo;
    RelativeLayout mylistgo,gotorooms,telegramgo,facebookgo,webgoo,sinout,login,myrooms,tiktokpagegoo,myaccount
            ,mywatchhist,mydownloads,myposts,myfavchrachters,mysavedposts,newsgoo,castgoo,petriongo
            ,adscenter;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("rooms");

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ((MainActivity)getActivity()).showStatusBar();
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        initial(view);
        return view;
    }
    private void initial(View view) {
        authManager = new AuthManager(getActivity().getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        adscenter = view.findViewById(R.id.adscenter);
        petriongo = view.findViewById(R.id.petriongo);
        myaccount = view.findViewById(R.id.myaccount);
        tiktokpagegoo = view.findViewById(R.id.tiktokpagegoo);
        user_profil_photo = view.findViewById(R.id.user_profil_photo);
        myrooms = view.findViewById(R.id.myrooms);
        gotojcartoonprof = view.findViewById(R.id.gotojcartoonprof);
        goback = view.findViewById(R.id.goback);
        sinout = view.findViewById(R.id.sinout);
        login = view.findViewById(R.id.login);
        mywatchhist =  view.findViewById(R.id.mywatchhist);
        mydownloads =  view.findViewById(R.id.mydownloads);
        myposts =  view.findViewById(R.id.myposts);
        myfavchrachters = view.findViewById(R.id.myfavchrachters);
        mysavedposts =  view.findViewById(R.id.mysavedposts);
        newsgoo =  view.findViewById(R.id.newsgoo);
        navigationBar =  view.findViewById(R.id.navigation_bar);
        mylistgo =  view.findViewById(R.id.mylistgo);
        gotorooms =  view.findViewById(R.id.gotorooms);
        telegramgo = view. findViewById(R.id.telegramgo);
        facebookgo =  view.findViewById(R.id.facebookgo);
        webgoo = view. findViewById(R.id.webgoo);
        castgoo = view.findViewById(R.id.castgoo);

        gotojcartoonprof.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                apiService.finduser(1).enqueue(new Callback<UserModel>( ) {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.body() != null){
                            getActivity().runOnUiThread(new Runnable( ) {
                                @Override
                                public void run() {
                                    startActivity(new Intent( getActivity(), Profile.class )
                                            .putExtra("user",response.body()));
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                    }
                });


            }
        });

        if (authManager.getUserInfo().getId() > 0){
            user_profil_photo.setVisibility(View.VISIBLE);
            if (getActivity() != null){
                Glide.with(getActivity()).load(authManager.getUserInfo().getProfil())
                        .into(user_profil_photo);
            }


        }else {
            user_profil_photo.setVisibility(View.GONE);
        }

        TextView myroomcount = view.findViewById(R.id.myroomcount);
        myroomcount.setText("");





        myrooms.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View vv) {
                if (authManager.getUserInfo().getId() > 0){

                    startActivity(new Intent( getActivity(), Myrooms.class ));

                }else {
                    ShowsnackLogin(vv,getActivity());
                }
            }
        });



        mywatchhist.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getActivity(), savedpostdownload.class )
                        .putExtra("type","countinu"));

            }
        });
        mydownloads.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getActivity(), DownloadList.class ));
            }
        });
        myposts.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (authManager.getUserInfo().getId() > 0){

                    startActivity(new Intent( getActivity(), savedpostdownload.class )
                            .putExtra("type","mypost"));

                }else {
                    ShowsnackLogin(view,getActivity());
                }

            }
        });
        myfavchrachters.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (authManager.getUserInfo().getId() > 0){

                      startActivity(new Intent( getActivity(), savedpostdownload.class )
                            .putExtra("type","mycast"));

                }else {
                    ShowsnackLogin(view,getActivity());
                }

            }
        });
        mysavedposts.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (authManager.getUserInfo().getId() > 0){

                    startActivity(new Intent( getActivity(), savedpostdownload.class )
                            .putExtra("type","savedpost"));

                }else {
                    ShowsnackLogin(view,getActivity());
                }

            }
        });
        newsgoo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getActivity(), savedpostdownload.class )
                        .putExtra("type","news"));
            }
        });

        if (authManager.getUserInfo().getId() >0){
            login.setVisibility(View.GONE);
            sinout.setVisibility(View.VISIBLE);
        }else{
            sinout.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);


        }
        sinout.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                authManager.deleteAuth();
                sinout.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
            }
        });
        login.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginTojcartoon.class);
                startActivity(intent);
            }
        });




        mylistgo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (authManager.getUserInfo().getId() > 0){
                    startActivity(new Intent( getActivity(), Mylist.class ));

                }else {
                    ShowsnackLogin(view,getActivity());
                }
            }
        });
        castgoo.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getActivity(), Allcharacters.class ).putExtra("place","all"));
            }
        });

        gotorooms.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getActivity(), AllRooms.class ));

            }
        });

        telegramgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tgintent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(getResources().getString(R.string.telegram)));
                tgintent.setPackage("org.telegram.messenger");
                startActivity(tgintent);

            }
        });
        facebookgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = getResources().getString(R.string.facebookss);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent1.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.chrome");
                startActivity(intent1);
            }
        });
        webgoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = getResources().getString(R.string.site);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent1.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.chrome");
                startActivity(intent1);
            }
        });
        tiktokpagegoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = "https://www.tiktok.com/@jcartoonplus";
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent1.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.chrome");
                startActivity(intent1);
            }
        });
        petriongo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = "https://www.patreon.com/jcartoon";
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent1.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome");
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.android.chrome");
                startActivity(intent1);
            }
        });
        adscenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getActivity(), WelcomPage.class ));

            }
        });
        myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowProfileDialog();
            }
        });
    }

    private void ShowProfileDialog() {


        final Dialog dialog = new Dialog(getActivity(), R.style.MyAlertDialogProfile);
        dialog.setContentView(R.layout.showprofilelayout);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;



        TextView followercount = dialog.findViewById(R.id.followercount);
        getfollowers(followercount);
        TextView followingcount = dialog.findViewById(R.id.followingcount);
        getfollowings(followingcount);



        TextView proff = dialog.findViewById(R.id.proff);
        proff.setText(authManager.getUserInfo().getProfileDesc());

        TextView fullname = dialog.findViewById(R.id.fullname);
        fullname.setText(authManager.getUserInfo().getName());

        CircleImageView show_profile = dialog.findViewById(R.id.show_profile);
        if (getActivity() != null){
            Glide.with(getActivity()).load(authManager.getUserInfo().getProfil())
                    .into(show_profile);
        }

        ImageView show_verefic = dialog.findViewById(R.id.show_verefic);
        if (authManager.getUserInfo().getIsverified() == 1){
            show_verefic.setVisibility(View.VISIBLE);
        }

        RelativeLayout editaccount = dialog.findViewById(R.id.editaccount);
        editaccount.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                startActivity(new Intent( getContext(), EditProfil.class ));
                dialog.dismiss();

            }
        });





        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void getfollowings(TextView followingcount) {

        apiService.getfollowings(authManager.getUserInfo().getId()).enqueue(new Callback<Long>( ) {
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

    private void getfollowers(TextView followercount) {
        apiService.getfollowers(authManager.getUserInfo().getId()).enqueue(new Callback<Long>( ) {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.body() != null){
                    followercount.setText(response.body()+"");
                }


            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });


    }
}