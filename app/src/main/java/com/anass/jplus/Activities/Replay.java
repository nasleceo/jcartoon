package com.anass.jplus.Activities;

import static com.anass.jplus.API.BEANLINKS.BASELINK;
import static com.anass.jplus.Activities.Auth.Signintojcartoon2.headers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.Adapters.CommentAdapter;
import com.anass.jplus.Adapters.ReplayAdapter;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.CommentModel;
import com.anass.jplus.Models.PostModel;
import com.anass.jplus.Models.ReplayModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.Responses.CommentsResponse;
import com.anass.jplus.backend.Responses.ReplayResponse;
import com.anass.jplus.backend.viewmodel.getAlltvsViewModel;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Replay extends AppCompatActivity {


    ImageView navigationBar,gotooback;
    ReplayAdapter commentAdapter;
    RecyclerView coments_rv;
    getAlltvsViewModel movieListViewModel;
    ProgressBar progresswaitt;

    TextView coummentscount;
    List<ReplayModel> commentModels  = new ArrayList<>( );

    int curentpage = 1;
    int totalpage = 1;
    int commentD = 14;
    TextInputLayout textinputacc;
    TextInputEditText sendcomentedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);


        inital();



        textinputacc.setEndIconOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                    sendcomentPost();


            }
        });




    }


    private void sendcomentPost() {


        HashMap<String,String> data = new HashMap<>(  );

        data.put("user_id","1");
        data.put("content", Objects.requireNonNull(sendcomentedit.getText( )).toString());
        data.put("comment_id", String.valueOf(commentD));
        data.put("statu","Published");

        Log.d("TAG", "sendcomentPost: "+commentD);

        AndroidNetworking.get(BASELINK+"cartoon/addReplay")
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
                                Toast.makeText(Replay.this, "تم إضافة الرد", Toast.LENGTH_SHORT).show( );
                                sendcomentedit.getText().clear();
                                sendcomentedit.clearFocus();
                                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                in.hideSoftInputFromWindow(sendcomentedit.getWindowToken(), 0);
                                commentModels.clear();
                                commentAdapter.notifyDataSetChanged();
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


    private void inital() {

        commentD = getIntent().getIntExtra("commentD",14);


        movieListViewModel = new ViewModelProvider(Replay.this).get(getAlltvsViewModel.class);

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

        commentAdapter = new ReplayAdapter(Replay.this,commentModels);


        coments_rv.setAdapter(commentAdapter);
        coments_rv.setLayoutManager(new LinearLayoutManager(Replay.this,LinearLayoutManager.VERTICAL,false));
        coments_rv.setNestedScrollingEnabled(false);
        coments_rv.addOnScrollListener(new RecyclerView.OnScrollListener( ) {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!coments_rv.canScrollHorizontally(1)){
                    if (curentpage <= totalpage){
                        curentpage += 1;

                        SetupForPosts();

                    }

                }
            }
        });


            SetupForPosts();



    }

    private void SetupForPosts() {
        movieListViewModel.replaysofcomments(commentD,curentpage).observe(this, new Observer<ReplayResponse>( ) {
            @Override
            public void onChanged(ReplayResponse commentsResponse) {
                if (commentsResponse != null){


                    totalpage = commentsResponse.getTotal();


                    if (commentsResponse.getReplayModels() != null){

                        progresswaitt.setVisibility(View.GONE);
                        int oldCount = commentModels.size();

                        commentModels.addAll(commentsResponse.getReplayModels( ));
                        commentAdapter.notifyItemRangeInserted(oldCount,commentModels.size());
                        coummentscount.setText("( "+commentModels.size()+" ) الردود");

                    }
                }

            }
        });


    }
}