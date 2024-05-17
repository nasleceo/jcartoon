package com.anass.jplus.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.anass.jplus.API.BEANLINKS.BASELINK;
import static com.anass.jplus.Activities.Auth.Signintojcartoon2.headers;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Models.ReplayModel;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.hendraanggrian.appcompat.socialview.widget.SocialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReplayAdapter extends RecyclerView.Adapter<ReplayAdapter.myview> {


    Activity context;
    public int typepost = 0;
    List<ReplayModel> replayModels;
    AuthManager authManager;


    public ReplayAdapter(Activity context,List<ReplayModel> commentModels) {
        this.context = context;
        this.replayModels = commentModels;
        authManager = new AuthManager(context.getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        notifyDataSetChanged( );
    }

    @NonNull
    @Override
    public ReplayAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myview(LayoutInflater.from(parent.getContext()).inflate(R.layout.replay_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReplayAdapter.myview holder, int position) {
        ReplayModel comment = replayModels.get(position);

        holder.content.setText(comment.getContent());

        if (comment.getUser()!= null){
            UserModel user = comment.getUser();

            holder.fullname.setText(user.getName());
            Glide.with(holder.itemView.getContext( )).load(user.getProfil( )).into(holder.gotocomments);

            if (user.getIsverified() == 1){
                holder.show_verefic.setVisibility(View.VISIBLE);
            }



        }

        Log.d("TAG", "onResponse: "+comment.getId());


        holder.likedbtn.setText("( "+comment.getLikers_count()+" ) أعجبني");
        if (authManager.getUserInfo().getId() > 0){
            CheckLiks(comment,holder,authManager.getUserInfo().getId());

        }

        holder.likedbtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (authManager.getUserInfo().getId() > 0){
                    AndroidNetworking.get(BASELINK+"cartoon/likeunlikereplay?user_id="+authManager.getUserInfo().getId()+"&comment_id="+comment.getId())
                            .addHeaders(headers)
                            .setPriority(Priority.LOW)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener( ) {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        String res = response.getString("message");
                                        if (res.equals("like")){

                                            holder.likedbtn.setTextColor(context.getResources().getColor(R.color.red));

                                        }else {
                                            Log.d("TAG", "onResponse: "+res);

                                            holder.likedbtn.setTextColor(context.getResources().getColor(R.color.white));

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


            }
        });

    }

    @Override
    public int getItemCount() {
        return replayModels.size();
    }
    private void CheckLiks(ReplayModel comment, myview holder, int id) {

        AndroidNetworking.get(BASELINK+"cartoon/CheckLikereplay")
                .addQueryParameter("user_id", String.valueOf(id))
                .addQueryParameter("comment_id", String.valueOf(comment.getId()))
                .addHeaders(headers)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "onResponse: sendcomentPost"+response.toString());

                        try {
                            String res = response.getString("message");
                            if (!res.equals("like")){

                                holder.likedbtn.setTextColor(context.getResources().getColor(R.color.red));

                            }else {

                                holder.likedbtn.setTextColor(context.getResources().getColor(R.color.white));
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

    public class myview extends RecyclerView.ViewHolder {
        TextView replay,likedbtn,fullname;
        SocialTextView content;
        CircleImageView gotocomments;
        ImageView show_verefic;
        public myview(@NonNull View itemView) {
            super(itemView);

            replay = itemView.findViewById(R.id.replaytxt);
            likedbtn = itemView.findViewById(R.id.likecomt);
            content = itemView.findViewById(R.id.content);
            fullname = itemView.findViewById(R.id.fullname);
            gotocomments = itemView.findViewById(R.id.gotocomments);
            show_verefic = itemView.findViewById(R.id.show_verefic);

        }
    }
}
