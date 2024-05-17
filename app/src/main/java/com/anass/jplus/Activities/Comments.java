package com.anass.jplus.Activities;

import static com.anass.jplus.API.BEANLINKS.BASELINK;
import static com.anass.jplus.Activities.Auth.Signintojcartoon2.headers;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.Activities.Auth.LoginTojcartoon;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Adapters.CommentAdapter;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.CastModel;
import com.anass.jplus.Models.CommentModel;
import com.anass.jplus.Models.PostModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.CommentsResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.anass.jplus.backend.viewmodel.getAlltvsViewModel;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comments extends AppCompatActivity {

    ImageView navigationBar,gotooback;
    CommentAdapter commentAdapter;
    RecyclerView coments_rv;
    getAlltvsViewModel movieListViewModel;
    ProgressBar progresswaitt;
    AuthManager authManager;
    TextView coummentscount;
    List<CommentModel> commentModels  = new ArrayList<>( );

    int curentpage = 1;
    int totalpage = 1;
    CartoonModel cartoonModel;
    CastModel castModel;
    PostModel postModel;
    TextInputLayout textinputacc;
    TextInputEditText sendcomentedit;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comments);
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        inital();



        textinputacc.setEndIconOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (!sendcomentedit.getText( ).toString().isEmpty()){
                    if (authManager.getUserInfo().getId() > 0){

                        if (cartoonModel != null){
                            sendcomentCartoon();
                        }
                        if (postModel != null){
                            sendcomentPost();
                        }

                        if (castModel != null){
                            sendcomentCast();
                        }



                    }else {
                        sendcomentedit.getText().clear();
                        sendcomentedit.clearFocus();
                        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(sendcomentedit.getWindowToken(), 0);

                        ShowsnackLogin(view,Comments.this);

                    }
                }else {
                    Snackbar.make(view,"الخانة فارغة",3000).show();
                }





            }
        });




    }

    public static void ShowsnackLogin(View view, Activity comments) {

        Snackbar.make(view,"يجب تسجيل الدخول أولا",3000)
                .setAction("تسجيل الدخول", new View.OnClickListener( ) {
                    @Override
                    public void onClick(View view) {
                        comments.startActivity(new Intent( comments, LoginTojcartoon.class ));
                    }
                }).show();
    }

    private void sendcomentCartoon() {

        HashMap<String,String> data = new HashMap<>(  );

        data.put("user_id",String.valueOf(authManager.getUserInfo().getId()));
        data.put("content", Objects.requireNonNull(sendcomentedit.getText( )).toString());
        data.put("tv_id", String.valueOf(cartoonModel.getId()));
        if (sendcomentedit.getText( ).toString().contains("حرق")){
            data.put("ishark","1");
        }else {
            data.put("ishark","0");
        }
        data.put("statu","Published");
        data.put("type","cartoon");


        AndroidNetworking.get(BASELINK+"cartoon/addcommentcartoon")
                .addQueryParameter(data)
                .addHeaders(headers)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String res = response.getString("message");
                            if (res.contains("added")){
                                Toast.makeText(Comments.this, "تم إضافة التعليق", Toast.LENGTH_SHORT).show( );
                                sendcomentedit.getText().clear();
                                sendcomentedit.clearFocus();
                                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                in.hideSoftInputFromWindow(sendcomentedit.getWindowToken(), 0);
                                commentModels.clear();
                                progresswaitt.setVisibility(View.VISIBLE);
                                SetupForCartoon();
                            }


                        } catch (JSONException ignored) {

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+anError.getMessage());

                    }
                });

    }
    private void sendcomentCast() {

        Log.d("TAG", "onResponse: sendcomentPost");

        HashMap<String,String> data = new HashMap<>(  );

        data.put("user_id",String.valueOf(authManager.getUserInfo().getId()));
        data.put("content", Objects.requireNonNull(sendcomentedit.getText( )).toString());
        data.put("cast_id", String.valueOf(castModel.getId()));
        if (sendcomentedit.getText( ).toString().contains("حرق")){
            data.put("ishark","1");
        }else{
            data.put("ishark","0");
        }
        data.put("statu","Published");
        data.put("type","cast");


        AndroidNetworking.get(BASELINK+"cartoon/addcommentcast")
                .addQueryParameter(data)
                .addHeaders(headers)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "onResponse: sendcomentPost"+response.toString());

                        try {
                            String res = response.getString("message");
                            if (res.contains("added")){
                                Toast.makeText(Comments.this, "تم إضافة التعليق", Toast.LENGTH_SHORT).show( );
                                sendcomentedit.getText().clear();
                                sendcomentedit.clearFocus();
                                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                in.hideSoftInputFromWindow(sendcomentedit.getWindowToken(), 0);
                                commentModels.clear();
                                commentAdapter.notifyDataSetChanged();
                                progresswaitt.setVisibility(View.VISIBLE);
                                SetupForCast();

                            }


                        } catch (JSONException ignored) {

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+anError.getMessage());

                    }
                });



    }
    private void sendcomentPost() {

        Log.d("TAG", "onResponse: sendcomentPost");

        HashMap<String,String> data = new HashMap<>(  );

        data.put("user_id",String.valueOf(authManager.getUserInfo().getId()));
        data.put("content", Objects.requireNonNull(sendcomentedit.getText( )).toString());
        data.put("post_id", String.valueOf(postModel.getId()));
        if (sendcomentedit.getText( ).toString().contains("حرق")){
            data.put("ishark","1");
        }else{
            data.put("ishark","0");
        }
        data.put("statu","Published");
        data.put("type","post");


        AndroidNetworking.get(BASELINK+"cartoon/addcommentpost")
                .addQueryParameter(data)
                .addHeaders(headers)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "onResponse: sendcomentPost"+response.toString());

                        try {
                            String res = response.getString("message");
                            if (res.contains("added")){
                                Toast.makeText(Comments.this, "تم إضافة التعليق", Toast.LENGTH_SHORT).show( );
                                sendcomentedit.getText().clear();
                                sendcomentedit.clearFocus();
                                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                in.hideSoftInputFromWindow(sendcomentedit.getWindowToken(), 0);
                                progresswaitt.setVisibility(View.VISIBLE);
                                SetupForPosts();

                            }


                        } catch (JSONException ignored) {

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+anError.getMessage());

                    }
                });



    }

    public void SetupForCartoon() {

        apiService.getCartoonCommentsByID(cartoonModel.getId(),curentpage).enqueue(new Callback<CommentsResponse>( ) {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                if (response.body() != null){



                    if (response.body().getComments() != null){

                        progresswaitt.setVisibility(View.GONE);

                        commentAdapter = new CommentAdapter(Comments.this,response.body().getComments( ));
                        coments_rv.setAdapter(commentAdapter);
                        coummentscount.setText("( "+response.body().getComments( ).size()+" ) التعليقات");

                    }else {
                        progresswaitt.setVisibility(View.GONE);
                        coummentscount.setText("( "+0+" ) التعليقات");


                    }
                }else {
                    progresswaitt.setVisibility(View.GONE);
                    coummentscount.setText("( "+0+" ) التعليقات");


                }
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {

            }
        });


    }

    private void inital() {

        cartoonModel = (CartoonModel) getIntent().getSerializableExtra("cartoonID");
        postModel = (PostModel) getIntent().getSerializableExtra("postD");
        castModel = (CastModel) getIntent().getSerializableExtra("cast");

        Log.d("TAG", "inital: "+cartoonModel+postModel+castModel);
        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

        movieListViewModel = new ViewModelProvider(Comments.this).get(getAlltvsViewModel.class);

        progresswaitt = findViewById(R.id.progresswaitt);
        sendcomentedit = findViewById(R.id.sendcomentedit);
        textinputacc = findViewById(R.id.textinputacc);
        navigationBar = findViewById(R.id.navigation_bar);
        coments_rv = findViewById(R.id.coments_rv);
        gotooback = findViewById(R.id.gotooback);
        coummentscount = findViewById(R.id.coummentscount);
        gotooback.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        coments_rv.setLayoutManager(new LinearLayoutManager(Comments.this,LinearLayoutManager.VERTICAL,false));
        coments_rv.setNestedScrollingEnabled(false);



        if (cartoonModel != null){
            SetupForCartoon();
        }
        if (postModel != null){
            SetupForPosts();
        }

        if (castModel != null){
            SetupForCast();
        }


    }

    private void SetupForCast() {
        commentModels.clear();
        movieListViewModel.commentsofcastt(castModel.getId(),curentpage).observe(this, new Observer<CommentsResponse>( ) {
            @Override
            public void onChanged(CommentsResponse commentsResponse) {
                if (commentsResponse != null){


                    if (commentsResponse.getComments() != null){
                        progresswaitt.setVisibility(View.GONE);
                        commentModels.addAll(commentsResponse.getComments( ));
                        commentAdapter.notify();
                        coummentscount.setText("( "+commentModels.size()+" ) التعليقات");

                    }else {
                        progresswaitt.setVisibility(View.GONE);
                        coummentscount.setText("( "+0+" ) التعليقات");


                    }
                }else {
                    progresswaitt.setVisibility(View.GONE);
                    coummentscount.setText("( "+0+" ) التعليقات");


                }

            }
        });

    }

    private void SetupForPosts() {

        Log.d("TAG", "SetupForPosts: "+postModel.getId());

        apiService.getPostCommentsByID(postModel.getId(),curentpage).enqueue(new Callback<CommentsResponse>( ) {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                if (response.body() != null){
                    if (response.body( ).getComments() != null){

                        progresswaitt.setVisibility(View.GONE);
                        commentAdapter = new CommentAdapter(Comments.this,response.body( ).getComments());
                        coments_rv.setAdapter(commentAdapter);
                        coummentscount.setText("( "+response.body( ).getComments().size()+" ) التعليقات");

                    }else {
                        progresswaitt.setVisibility(View.GONE);
                        coummentscount.setText("( "+0+" ) التعليقات");
                        Log.d("TAG", "SetupForPosts: "+response.message());


                    }
                }else {
                    progresswaitt.setVisibility(View.GONE);
                    coummentscount.setText("( "+0+" ) التعليقات");
                    Log.d("TAG", "SetupForPosts: "+response.message());

                }
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {
                Toast.makeText(Comments.this, "مشكلة في الإتصال"+ t.getMessage(), Toast.LENGTH_SHORT).show( );
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