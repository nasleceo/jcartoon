package com.anass.jplus.Activities;

import static android.content.ContentValues.TAG;
import static com.anass.jplus.API.BEANLINKS.BASELINK;
import static com.anass.jplus.Activities.Auth.Signintojcartoon2.headers;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Adapters.SeriesAdapter;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.CastModel;
import com.anass.jplus.Models.PostModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastShow extends AppCompatActivity {

    TextView titlechrach,namechrabar,cartoon_title;
    CircleImageView chrachtreimg;
    ImageView wronginfo,addtofavchrach,commchrach,back,chrachtreimgtow,cartoon_id_photo,imgbg;
    public boolean IsInMyFavorit = false ;
    SeriesAdapter serieAdapter;
    List<CartoonModel> epesodModel;
    ProgressBar datavisi;
    TextView likecoutercharc;
    AuthManager authManager;
    private ApiService apiService;
    CastModel castModel;
    LinearLayout morajaa;

    FirebaseFirestore db;
    private boolean isLiked = false;
    ScrollView scale;
    ProgressBar datavisiss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.activity_cast_show);
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        authManager = new AuthManager(getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        db = FirebaseFirestore.getInstance();
        castModel = (CastModel) getIntent().getSerializableExtra("cast");
        titlechrach = findViewById(R.id.titlechrach);
        chrachtreimgtow = findViewById(R.id.chrachtreimgtow);
        namechrabar = findViewById(R.id.namechrabar);
        chrachtreimg = findViewById(R.id.chrachtreimg);
        wronginfo = findViewById(R.id.wronginfo);
        datavisiss = findViewById(R.id.datavisiss);
        morajaa = findViewById(R.id.morajaa);
        addtofavchrach = findViewById(R.id.addtofavchrach);
        commchrach = findViewById(R.id.commchrach);
        back = findViewById(R.id.backch);
        datavisi = findViewById(R.id.datavisiss);
        cartoon_id_photo = findViewById(R.id.cartoon_id_photo);
        likecoutercharc = findViewById(R.id.likecoutercharc);
        cartoon_title = findViewById(R.id.cartoon_title);
        scale = findViewById(R.id.scale);
        imgbg = findViewById(R.id.imgbg);

        titlechrach.setText(castModel.getTitle());
        namechrabar.setText(castModel.getTitle());

        Glide.with(this).load(castModel.getPoster()).into(chrachtreimg);
        Glide.with(this).load(castModel.getPoster()).into(chrachtreimgtow);
        Glide.with(this).load(castModel.getPoster()).into(imgbg);


        if (castModel.getComic() != null){
            Glide.with(this).load(castModel.getComic().getPoster()).into(cartoon_id_photo);
            cartoon_title.setText(castModel.getComic().getTitle());

        }

        chrachtreimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CastShow.this, ViewPhoto.class);
                intent.putExtra("photo",castModel.getPoster());
                startActivity(intent);

            }
        });

        if (authManager.getUserInfo().getId() > 0){
            checkLikeState();
        }

        getFavoritesCount(castModel.getId());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        commchrach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(CastShow.this, Comments.class);
                in.putExtra("cast",castModel);
                startActivity(in);

            }
        });

        addtofavchrach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (authManager.getUserInfo().getId() <= 0){

                    Toast.makeText(CastShow.this, "من فضلك سجل الدخول أولا", Toast.LENGTH_SHORT).show();

                }else {
                        likeCastModel();

                }




            }
        });




    }


    private void checkLikeState() {
        // Assuming castModel is an instance of your CastModel class
        Integer castModelId = castModel.getId();
        int userId = authManager.getUserInfo().getId(); // Replace with the actual user ID

        apiService.savecast(castModelId,userId).enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if (response.body() != null){
                    String res = response.body().getMessage();

                    if (res.equals("favorite")){

                        Glide.with(CastShow.this).load(getResources().getDrawable(R.drawable.likecheck)).into(addtofavchrach);

                    }else {

                        Glide.with(CastShow.this).load(getResources().getDrawable(R.drawable.like)).into(addtofavchrach);

                    }

                    getFavoritesCount(castModelId);

                }


            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });


    }


    private void likeCastModel() {

        Integer castModelId = castModel.getId();
        int userId = authManager.getUserInfo().getId();

        apiService.Checksavecast(castModelId,userId).enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if (response.body() != null){
                    String res = response.body().getMessage();

                    if (res.equals("unfavorite")){

                        Glide.with(CastShow.this).load(getResources().getDrawable(R.drawable.likecheck)).into(addtofavchrach);


                    }else {

                        Glide.with(CastShow.this).load(getResources().getDrawable(R.drawable.like)).into(addtofavchrach);


                    }

                    getFavoritesCount(castModelId);

                }


            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });


    }


    private void getFavoritesCount(int id) {


        apiService.castcountfav(id).enqueue(new Callback<Long>( ) {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.body() != null) {
                    long likesCount = response.body();
                    // Now you can use the likesCount in your UI or business logic
                    likecoutercharc.setText(String.valueOf(likesCount));
                    scale.setVisibility(View.VISIBLE);
                    datavisiss.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });



    }


}