package com.anass.jplus.Adapters;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import static com.anass.jplus.API.BEANLINKS.BASELINK;
import static com.anass.jplus.Activities.Auth.Signintojcartoon2.headers;
import static com.anass.jplus.Activities.Comments.ShowsnackLogin;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;
import static com.anass.jplus.Activities.ShowInfoActivity.format;
import static com.anass.jplus.Constant.AppConfig.adType;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.anass.jplus.Activities.Comments;
import com.anass.jplus.Activities.Profile;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Activities.ShowInfoActivity;
import com.anass.jplus.Activities.ViewPhoto;
import com.anass.jplus.Constant.AppConfig;
import com.anass.jplus.Models.CastModel;
import com.anass.jplus.Models.PostModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.CommentsResponse;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hendraanggrian.appcompat.socialview.widget.SocialTextView;

import com.startapp.sdk.ads.banner.Banner;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.myview>  {

   Activity context;

   List<PostModel> postModels;
   AuthManager authManager;
   ApiService apiService;
    FirebaseFirestore db;
    CollectionReference likesCollection;
    private boolean isLiked = false;


    public PostAdapter(Activity context,List<PostModel> postModels) {
        this.context = context;
        this.postModels = postModels;
        if (context != null) {
            authManager = new AuthManager(context.getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        }
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        db = FirebaseFirestore.getInstance();
        likesCollection = db.collection("postlikes");

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int vv) {
        return new myview(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.myview holder, int position) {

        PostModel post = postModels.get(position);

        int ff = position;

        getlikecountfirebase(post,holder);


    /*    if (authManager.getUserInfo().getId() > 0){
            checkLikeState(post,holder);
        }
*/

        if (post.getIshark() == 1){
            holder.content.setVisibility(View.GONE);
            holder.showhark.setVisibility(View.VISIBLE);
        }else {
            holder.content.setVisibility(View.VISIBLE);
            holder.showhark.setVisibility(View.GONE);
        }
        holder.showhark.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                holder.content.setVisibility(View.VISIBLE);
                if (post.getPoster() != null ){
                    Glide.with(holder.itemView.getContext( )).load(post.getPoster( )).into(holder.post_image);
                    holder.post_image.setVisibility(View.VISIBLE);
                }
                holder.showhark.setVisibility(View.GONE);


            }
        });

        holder.post_image.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewPhoto.class);
                intent.putExtra("photo",post.getPoster( ));
                context.startActivity(intent);
            }
        });


     /*   holder.likebtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (authManager.getUserInfo().getId() <= 0){

                    ShowsnackLogin(view,context);

                }else {

                    likepost(post,holder);

                }


            }
        });

        holder.unlikebtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (authManager.getUserInfo().getId() <= 0){

                    ShowsnackLogin(view,context);

                }else {
                    likepost(post,holder);

                }
            }
        });
*/

        if (post.getPoste_user( ) != null){
            Glide.with(holder.itemView.getContext( )).load(post.getPoste_user( ).getProfil( )).into(holder.user_profil_post);
            holder.user_profil_post.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent( context, Profile.class )
                            .putExtra("user",post.getPoste_user( )));
                }
            });

            holder.fullname.setText(post.getPoste_user().getName());
            holder.username.setText(post.getPoste_user().getUserspecialName());


        }

        if (post.getPoster() != null ){
            if (post.getIshark() == 1){
                holder.post_image.setVisibility(View.GONE);
            }else {
                holder.content.setVisibility(View.VISIBLE);
                holder.showhark.setVisibility(View.GONE);
                holder.post_image.setVisibility(View.VISIBLE);

                Glide.with(holder.itemView.getContext( )).load(post.getPoster( )).into(holder.post_image);
                holder.post_image.setOnClickListener(new View.OnClickListener( ) {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ViewPhoto.class);
                        intent.putExtra("photo",post.getPoster( ));
                        context.startActivity(intent);
                    }
                });
            }

        }

        holder.postdate.setText(post.getPostTime());
        holder.content.setText(post.getText());

        String views = format(post.getViews_count());
        holder.viewstxt.setText(views);

        holder.moreoption_menu.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                OpenpopMenu(post);
            }
        });


        if (post.getActivecomments() != 0 ){
            holder.commntsbtn.setVisibility(View.GONE);
        }

        holder.commntsbtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent( context, Comments.class ).putExtra("postD",post));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener( ) {
            @Override
            public boolean onLongClick(View view) {
                if (authManager.getUserInfo().getId() > 0){
                    String userid = String.valueOf(authManager.getUserInfo().getId());

                    if (post.getUserId().equals(userid) ){
                        DeletePostDialog(post.getId(),ff,postModels);
                    }

                }else {
                    ShowsnackLogin(view, context);
                }



                return true;
            }
        });



    }



    private void getlikecountfirebase(PostModel post, myview holder) {


        apiService.getPostCommentsByID(post.getId(),1).enqueue(new Callback<CommentsResponse>( ) {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                if (response.body() != null){

                    if (response.body( ).getComments() != null){

                        holder.likecount_txt.setText(response.body( ).getComments().size()+"  التعليقات");

                    }
                }
            }

            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {

            }
        });




    }

    private void CheckLikemowajaha(PostModel post, myview holder) {

        AndroidNetworking.get(BASELINK+"cartoon/CheckLikeposts")
                .addQueryParameter("post_id", String.valueOf(post.getId()))
                .addQueryParameter("user_id", String.valueOf(authManager.getUserInfo().getId()))
                .addHeaders(headers)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            if (!response.get("message").equals("like")){
                                holder.unlikebtn.setVisibility(View.VISIBLE);
                                holder.likebtn.setVisibility(View.GONE);

                            }else{
                                holder.unlikebtn.setVisibility(View.GONE);
                                holder.likebtn.setVisibility(View.VISIBLE);
                            }


                        } catch (JSONException e) {
                            Log.d("TAG", "onResponse: "+e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+anError.getMessage());

                    }
                });

    }

    private void CheckLikeposts(PostModel post, myview holder) {

        AndroidNetworking.get(BASELINK+"cartoon/CheckLikeposts")
                .addQueryParameter("post_id", String.valueOf(post.getId()))
                .addQueryParameter("user_id", String.valueOf(authManager.getUserInfo().getId()))
                .addHeaders(headers)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {


                        if (response.toString().contains("drtha")){
                            holder.unlikebtn.setVisibility(View.VISIBLE);
                            holder.likebtn.setVisibility(View.GONE);

                        }else{
                            holder.unlikebtn.setVisibility(View.GONE);
                            holder.likebtn.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+anError.getMessage());

                    }
                });
    }

    private void cast1like(PostModel post, myview holder) {

        CastModel castModel = post.getPoste_cast( );

        AndroidNetworking.get(BASELINK+"cartoon/likeunlikemowajaha")
                .addQueryParameter("post_id", String.valueOf(post.getId()))
                .addQueryParameter("cast1", String.valueOf(castModel.getId()))
                .addQueryParameter("user_id", String.valueOf(authManager.getUserInfo().getId()))
                .addHeaders(headers)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            String message = response.getString("message");
                            Log.d("TAG", "message: "+message);

                            if (!message.equals("like")){
                                holder.unlikebtncast1.setVisibility(View.VISIBLE);
                                holder.likebtncast1.setVisibility(View.GONE);

                            }else{
                                holder.unlikebtncast1.setVisibility(View.GONE);
                                holder.likebtncast1.setVisibility(View.VISIBLE);
                            }


                        } catch (JSONException e) {

                            Log.d("TAG", "onResponse: "+e.getMessage());
                            String message = e.getMessage();
                            Log.d("TAG", "message: "+message);

                            if (!message.equals("like")){
                                holder.unlikebtncast1.setVisibility(View.VISIBLE);
                                holder.likebtncast1.setVisibility(View.GONE);

                            }else{
                                holder.unlikebtncast1.setVisibility(View.GONE);
                                holder.likebtncast1.setVisibility(View.VISIBLE);
                            }

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+anError.getMessage());

                    }
                });


    }

    private void DeletePostDialog(int id, int ff, List<PostModel> postModels) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.deletepost);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        ImageView delete_file = dialog.findViewById(R.id.delete_file);
        ImageView close = dialog.findViewById(R.id.close);
        delete_file.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Deletepost(id,ff,postModels);
                dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        dialog.show();

    }

    private void Deletepost(int id, int ff, List<PostModel> postModels) {

        AndroidNetworking.get(BASELINK+"cartoon/deletpost/{id}")
                .addPathParameter("id", String.valueOf(id))
                .addHeaders(headers)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "تم الحدف بنجاح", Toast.LENGTH_SHORT).show( );
                        postModels.remove(ff);
                        notifyItemRemoved(ff);

                   /*     try {

                            if (response.get("message").equals("deleted")){
                                Toast.makeText(context, "تم الحدف بنجاح", Toast.LENGTH_SHORT).show( );
                                notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            Toast.makeText(context, "حدث خطأ", Toast.LENGTH_SHORT).show( );

                            Log.d("TAG", "onResponse: "+e.getMessage());
                        }*/

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+anError.getMessage());

                    }
                });


    }

    @Override
    public int getItemViewType(int position) {
        PostModel post = postModels.get(position);
        if (position>1 && (position+1) % 7 == 0) {
            return 10;
        }

        switch (post.getType( )) {

            case "nadariat":
            case "review":
                return 6;
            case "mawajaha":
                return 1;
            case "tawsia":
                return 3;
            case "post":
                return 0;
            default:
                return 2;
        }


    }

    private void OpenpopMenu(PostModel post) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.menuposter_item);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;


        LinearLayout savepost = dialog.findViewById(R.id.savepost);
        savepost.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                AndroidNetworking.get(BASELINK+"cartoon/savepost")
                        .addQueryParameter("post_id", String.valueOf(post.getId()))
                        .addQueryParameter("user_id", String.valueOf(authManager.getUserInfo().getId()))
                        .addHeaders(headers)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener( ) {
                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    if (response.get("message").equals("favorite")){
                                        Toast.makeText(context, "تم إضافة المنشور إلي المفضلة", Toast.LENGTH_SHORT).show( );
                                        dialog.dismiss();
                                    }else{
                                        Toast.makeText(context, "تم إزالة المنشور من المفضلة", Toast.LENGTH_SHORT).show( );
                                        dialog.dismiss();
                                    }


                                } catch (JSONException e) {
                                    Log.d("TAG", "onResponse: "+e.getMessage());
                                    dialog.dismiss();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("TAG", "onResponse: "+anError.getMessage());
                                dialog.dismiss();

                            }
                        });
            }
        });

        LinearLayout repopost = dialog.findViewById(R.id.repopost);
        repopost.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                AndroidNetworking.get(BASELINK+"cartoon/reportpost")
                        .addQueryParameter("post_id", String.valueOf(post.getId()))
                        .addQueryParameter("type", "post")
                        .addQueryParameter("text", "مشكلة في المنشور")
                        .addHeaders(headers)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener( ) {
                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    if (response.get("message").equals("report")){
                                        Toast.makeText(context, "تم التبليغ", Toast.LENGTH_SHORT).show( );
                                        dialog.dismiss();
                                    }else{
                                        dialog.dismiss();
                                    }


                                } catch (JSONException e) {
                                    Log.d("TAG", "onResponse: "+e.getMessage());
                                    dialog.dismiss();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("TAG", "onResponse: "+anError.getMessage());
                                dialog.dismiss();

                            }
                        });
            }
        });



        dialog.show();
        dialog.getWindow().setAttributes(lp);


    }

    @Override
    public int getItemCount() {
        return postModels.size();
    }

    public void getItem(int i) {
        PostModel post = postModels.get(i);
        Log.d("TAG", "getItem: "+post.getId());
        AddView(post);
    }

    private void AddView(PostModel post) {

        AndroidNetworking.get(BASELINK+"cartoon/addviewtopost/{id}")
                .addPathParameter("id", String.valueOf(post.getId()))
                .addHeaders(headers)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener( ) {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("TAG", "onResponse: "+response.toString());

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TAG", "onResponse: "+anError.getMessage());

                    }
                });


    }

    private void checkLikeState(PostModel post, myview holder) {

        int userId = authManager.getUserInfo().getId(); // Replace with the actual user ID
        getlikecountfirebase(post,holder);

        apiService.CheckLikeposts(post.getId(),userId).enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if (response.body() != null){
                    String res = response.body().getMessage();

                    if (res.equals("drtha")){

                        holder.likebtn.setVisibility(View.GONE);
                        holder.unlikebtn.setVisibility(View.VISIBLE);

                    }else {

                        holder.likebtn.setVisibility(View.VISIBLE);
                        holder.unlikebtn.setVisibility(View.GONE);

                    }



                }



            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });


    }

    private void unlikepost(PostModel post, myview holder) {

        Integer castModelId = post.getId();
        int userId = authManager.getUserInfo().getId();

        apiService.likeunlikepost(castModelId,userId).enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.body() != null){
                    String res = response.body().getMessage();

                    if (res.equals("like")){

                        holder.likebtn.setVisibility(View.GONE);
                        holder.unlikebtn.setVisibility(View.VISIBLE);

                    }else {

                        holder.likebtn.setVisibility(View.VISIBLE);
                        holder.unlikebtn.setVisibility(View.GONE);

                    }

                    getlikecountfirebase(post,holder);

                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable e) {
                Toast.makeText(context, e.getMessage()+"  هناك مشكلة  ", Toast.LENGTH_SHORT).show( );

            }
        });


    }

    public  void likepost(PostModel postModel, myview holder){

        Log.d(TAG, "likepost: "+postModel.getId());

        Integer castModelId = postModel.getId();
        int userId = authManager.getUserInfo().getId();

        apiService.likeunlikepost(castModelId,userId).enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.body() != null){
                    String res = response.body().getMessage();

                    if (res.equals("like")){

                        holder.likebtn.setVisibility(View.GONE);
                        holder.unlikebtn.setVisibility(View.VISIBLE);

                    }else {

                        holder.likebtn.setVisibility(View.VISIBLE);
                        holder.unlikebtn.setVisibility(View.GONE);

                    }

                    checkLikeState(postModel,holder);

                }


            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable e) {
                Toast.makeText(context, e.getMessage()+"  هناك مشكلة  ", Toast.LENGTH_SHORT).show( );

            }
        });


    }

    private void updateLikeUI(PostModel postModel, myview holder) {
        Log.d(TAG, "onClick: "+isLiked);

        if (isLiked) {
            holder.unlikebtn.setVisibility(View.VISIBLE);
            holder.likebtn.setVisibility(View.GONE);
        } else {
            holder.unlikebtn.setVisibility(View.GONE);
            holder.likebtn.setVisibility(View.VISIBLE);
        }

        // Update any other UI components if needed
        getlikecountfirebase(postModel,holder);


    }

    public class myview extends RecyclerView.ViewHolder {

        CircleImageView user_profil_post,cast1,cast2;
        TextView fullname,cartoon_title,likecount_txt,username,postdate,viewstxt,likecountcast1,likecountcast2;
        ImageView cartoon_id_photo,vb;
        SocialTextView content;
        PhotoView post_image;

        RecyclerView profileuserss;
        LinearLayout moreoption_menu,morajaa,commently,showhark;
        RelativeLayout likebtn,adViewLayout,unlikebtn,mowajahapost,commntsbtn,likebtncast1,likebtncast2,unlikebtncast1,unlikebtncast2;
        public myview(@NonNull View itemView) {
            super(itemView);

            adViewLayout = itemView.findViewById(R.id.reads);
            user_profil_post = itemView.findViewById(R.id.user_profil_post);
            viewstxt = itemView.findViewById(R.id.viewstxt);
            cast1 = itemView.findViewById(R.id.cast1);
            cast2 = itemView.findViewById(R.id.cast2);
            likecountcast1 = itemView.findViewById(R.id.likecountcast1);
            likecountcast2 = itemView.findViewById(R.id.likecountcast2);
            unlikebtncast1 = itemView.findViewById(R.id.unlikebtncast1);
            unlikebtncast2 = itemView.findViewById(R.id.unlikebtncast2);
            username = itemView.findViewById(R.id.username);
            postdate = itemView.findViewById(R.id.postdate);
            fullname = itemView.findViewById(R.id.fullname);
            cartoon_title = itemView.findViewById(R.id.cartoon_title);
            cartoon_id_photo = itemView.findViewById(R.id.cartoon_id_photo);
            content = itemView.findViewById(R.id.content);
            vb = itemView.findViewById(R.id.vb);
            commently = itemView.findViewById(R.id.commently);
            likebtncast1 = itemView.findViewById(R.id.likebtncast1);
            likebtncast2 = itemView.findViewById(R.id.likebtncast2);
            post_image = itemView.findViewById(R.id.post_image);
            showhark = itemView.findViewById(R.id.showhark);

            profileuserss = itemView.findViewById(R.id.profileuserss);
            moreoption_menu = itemView.findViewById(R.id.moreoption_menu);
            likebtn = itemView.findViewById(R.id.likebtn);
            unlikebtn = itemView.findViewById(R.id.unlikebtn);
            mowajahapost = itemView.findViewById(R.id.mowajahapost);
            morajaa = itemView.findViewById(R.id.morajaa);
            commntsbtn = itemView.findViewById(R.id.commntsbtn);
            likecount_txt = itemView.findViewById(R.id.likecount_txt);
            likebtncast2 = itemView.findViewById(R.id.likebtncast2);
        }
    }

    public class LikeModel {
        private Integer postId;
        private int userid;
        private boolean liked;

        public LikeModel(Integer postId, int userid, boolean liked) {
            this.postId = postId;
            this.userid = userid;
            this.liked = liked;
        }


        public Integer getPostId() {
            return postId;
        }

        public void setPostId(Integer postId) {
            this.postId = postId;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }
    }
}
