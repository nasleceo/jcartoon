package com.anass.jplus.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.anass.jplus.Activities.Comments.ShowsnackLogin;
import static com.anass.jplus.Activities.Settings.SettingsJcartoon.SETTINGS_NAME;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Activities.Profile;
import com.anass.jplus.Activities.Replay;
import com.anass.jplus.Activities.Settings.AuthManager;
import com.anass.jplus.Models.CommentModel;
import com.anass.jplus.Models.UserModel;
import com.anass.jplus.R;
import com.anass.jplus.backend.ApiClient;
import com.anass.jplus.backend.Responses.MessageResponse;
import com.anass.jplus.backend.interfaces.ApiService;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.hendraanggrian.appcompat.socialview.widget.SocialTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter  extends RecyclerView.Adapter<CommentAdapter.myview>  {

    Activity context;
    public int typepost = 0;
    List<CommentModel> commentModels;
    AuthManager authManager;
    ApiService apiService;

    public CommentAdapter(Activity context,List<CommentModel> commentModels) {
        this.context = context;
        this.commentModels = commentModels;
        authManager = new AuthManager(context.getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE));
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int vv) {

            return new myview(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        CommentModel comment = commentModels.get(position);

        holder.content.setText(comment.getContent());

        holder.gotocomments.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent( context, Profile.class )
                        .putExtra("user",comment.getUser( )));
            }
        });

        if (comment.getUser()!= null){
            UserModel user = comment.getUser();

            if (comment.getUser().getId() == authManager.getUserInfo().getId()){
                holder.deletcoment.setVisibility(View.VISIBLE);
                holder.editcoment.setVisibility(View.VISIBLE);
            }

            holder.fullname.setText(user.getName());
            Glide.with(holder.itemView.getContext( )).load(user.getProfil( )).into(holder.gotocomments);

            if (user.getIsverified() == 1){
                holder.show_verefic.setVisibility(View.VISIBLE);
            }



        }

        if (comment.getIshark() == 1){
            holder.showhark.setVisibility(View.VISIBLE);
        }else {
            holder.showhark.setVisibility(View.GONE);
            holder.content.setVisibility(View.VISIBLE);

        }




        holder.showhark.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                holder.content.setVisibility(View.VISIBLE);
                holder.showhark.setVisibility(View.GONE);
            }
        });


        holder.deletcoment.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                DeleteDialog(comment.getId());

            }
        });
        holder.editcoment.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                EditDialog(comment.getId(),comment.getContent());

            }
        });

        holder.likedbtn.setText("( "+comment.getLikers_count()+" ) أعجبني");

        if (authManager.getUserInfo().getId() > 0){
            CheckLiks(comment,holder);

        }


        holder.likedbtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (authManager.getUserInfo().getId() > 0){


                    apiService.LikeComment(comment.getId(),authManager.getUserInfo().getId()).enqueue(new Callback<MessageResponse>( ) {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            if (response.body() != null){
                                String res = response.body().getMessage();

                                if (res.equals("like")){

                                    holder.likedbtn.setTextColor(context.getResources().getColor(R.color.red));

                                }else if (res.equals("uarethesame")) {

                                    Log.d("TAG", "onResponse: "+res);

                                    Toast.makeText(context, "لا يمكنك عمل لنفسك إعجاب", Toast.LENGTH_SHORT).show( );

                                }else {

                                    holder.likedbtn.setTextColor(context.getResources().getColor(R.color.white));

                                }


                            }
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                        }
                    });




                }else {
                    ShowsnackLogin(view, context);
                }


            }
        });

        holder.replay.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                context.startActivity(
                        new Intent( context, Replay.class )
                                .putExtra("commentD",comment.getId()));
            }
        });





    }

    private void EditDialog(int id, String content) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.editcomment);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView delete_file = dialog.findViewById(R.id.delete_file);
        TextInputEditText sendcomentedit = dialog.findViewById(R.id.sendcomentedit);
        sendcomentedit.setText(content);


        delete_file.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (sendcomentedit.getText().toString().isEmpty()){
                    Snackbar.make(view,"الخانة فارغة",3000).show();
                }else{

                    apiService.editcomment(id,sendcomentedit.getText().toString()).enqueue(new Callback<MessageResponse>( ) {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                            if (response.body() != null){
                                String res = response.body().getMessage();

                                if (res.equals("deleted")){

                                    Toast.makeText(context, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show( );
                                    dialog.dismiss();

                                }else {
                                    Toast.makeText(context, "هناك خطأ", Toast.LENGTH_SHORT).show( );
                                    dialog.dismiss();

                                }

                            }




                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(context, "هناك خطأ" + t.getMessage(), Toast.LENGTH_SHORT).show( );
                            dialog.dismiss();
                        }
                    });

                }


            }
        });

        dialog.show();



    }

    private void DeleteDialog( Integer commentId) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.delete_are_you_sure);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        ImageView close = dialog.findViewById(R.id.close);
        ImageView delete_file = dialog.findViewById(R.id.delete_file);
        delete_file.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                apiService.deletcomment(commentId).enqueue(new Callback<MessageResponse>( ) {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (response.body() != null){
                            String res = response.body().getMessage();

                            if (res.equals("deleted")){

                                Toast.makeText(context, "تم الحدف بنجاح", Toast.LENGTH_SHORT).show( );
                                dialog.dismiss();

                            }else {
                                Toast.makeText(context, "هناك مشكلة", Toast.LENGTH_SHORT).show( );


                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(context, "هناك مشكلة"+t.getMessage(), Toast.LENGTH_SHORT).show( );

                    }
                });

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

    private void CheckLiks(CommentModel comment, myview holder) {

        apiService.CheckLikeComment(comment.getId(),authManager.getUserInfo().getId()).enqueue(new Callback<MessageResponse>( ) {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.body() != null){
                    String res = response.body().getMessage();

                    if (!res.equals("like")){

                        holder.likedbtn.setTextColor(context.getResources().getColor(R.color.red));


                    }else {

                        holder.likedbtn.setTextColor(context.getResources().getColor(R.color.white));
                    }


                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });



    }

    @Override
    public int getItemViewType(int position) {
        return typepost;
    }

    /*private void OpenpopMenu() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.comment_menu_item);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = MATCH_PARENT;
        lp.height = MATCH_PARENT;



        dialog.show();
        dialog.getWindow().setAttributes(lp);


    }*/

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public class myview extends RecyclerView.ViewHolder {

        TextView replay,likedbtn,fullname,deletcoment,editcoment;
        SocialTextView content;
        CircleImageView gotocomments;
        LinearLayout showhark;
        ImageView show_verefic;
        public myview(@NonNull View itemView) {
            super(itemView);

            replay = itemView.findViewById(R.id.replaytxt);
            likedbtn = itemView.findViewById(R.id.likecomt);
            content = itemView.findViewById(R.id.content);
            fullname = itemView.findViewById(R.id.fullname);
            gotocomments = itemView.findViewById(R.id.gotocomments);
            show_verefic = itemView.findViewById(R.id.show_verefic);
            showhark = itemView.findViewById(R.id.showhark);
            deletcoment = itemView.findViewById(R.id.deletcoment);
            editcoment = itemView.findViewById(R.id.editcoment);

        }
    }
}
