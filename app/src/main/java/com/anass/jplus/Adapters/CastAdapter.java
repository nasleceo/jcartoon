package com.anass.jplus.Adapters;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Activities.CastShow;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Models.CastModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.interfaces.ApiService;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MYVV> {


    Activity context;
    List<CastModel> castModelList;
    public int type = 2;
    String place = "get";
    FirebaseFirestore db;
    ApiService apiService;
    AuthManager authManager;

    public CastAdapter(Activity context, List<CastModel> castModelList) {
        this.context = context;
        this.castModelList = castModelList;
        db = FirebaseFirestore.getInstance();
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        authManager = new AuthManager(context.getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));

    }

    public void settype(int type) {
        this.type = type;
    }
    public void setplace(String place) {
        this.place = place;
    }


    @NonNull
    @Override
    public CastAdapter.MYVV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1) {
            return new MYVV(LayoutInflater.from(parent.getContext( )).inflate(R.layout.chrachter_item_1 ,parent, false));
        } else {
            return new MYVV(LayoutInflater.from(parent.getContext( )).inflate(R.layout.chrachter_item_2, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.MYVV holder, int position) {

        CastModel cast = castModelList.get(position);

        if (getItemViewType(position) == 2){

            holder.cast_name.setText(cast.getTitle( ));
            Glide.with(holder.itemView.getContext( )).load(cast.getPoster( )).into(holder.show_profile);
            holder.itemView.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {


                        Intent intent = new Intent(context, CastShow.class);
                        intent.putExtra("cast", cast);
                        context.startActivity(intent);



                }
            });
        }else {

            holder.cast_name.setText(cast.getTitle( ));


            apiService.castcountfav(cast.getId()).enqueue(new Callback<Long>( ) {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {

                    if (response.body() != null) {
                        long likesCount = response.body();
                        // Now you can use the likesCount in your UI or business logic
                        holder.likecount.setText(String.valueOf(likesCount));
                    }

                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {

                }
            });


            Glide.with(holder.itemView.getContext( )).load(cast.getPoster( )).into(holder.show_profile);

            holder.itemView.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    if (place.equals("get")){

                        Intent intent = new Intent();
                        intent.putExtra("castID", String.valueOf(cast.getId()));
                        intent.putExtra("castImg", cast.getPoster());
                        intent.putExtra("castID2", String.valueOf(cast.getId()));
                        intent.putExtra("castImg2", cast.getPoster());
                        context.setResult(RESULT_OK, intent);
                        context.finish();
                    }else {
                        Intent intent = new Intent(context, CastShow.class);
                        intent.putExtra("cast", cast);
                        context.startActivity(intent);
                    }



                }
            });

        }


    }





    @Override
    public int getItemViewType(int position) {
        if (type == 1) {
            return 1;
        }
        return 2;
    }

    @Override
    public int getItemCount() {
        return castModelList.size( );
    }

    public class MYVV extends RecyclerView.ViewHolder {

        TextView cast_name,likecount;
        CircleImageView show_profile;


        public MYVV(@NonNull View itemView) {
            super(itemView);

            cast_name = itemView.findViewById(R.id.cast_name);
            show_profile = itemView.findViewById(R.id.show_profile);
            likecount = itemView.findViewById(R.id.likecount);

        }
    }
}
