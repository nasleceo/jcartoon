package com.anass.jplus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Activities.ShowInfoActivity;
import com.anass.jplus.Models.NotificationModel;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MYVV> {



    Context context;
    List<NotificationModel> notificationModels;
    int type = 0;

    public NotificationAdapter(Context context, List<NotificationModel> notificationModels) {
        this.context = context;
        this.notificationModels = notificationModels;
    }

    @NonNull
    @Override
    public NotificationAdapter.MYVV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            return new MYVV(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false));

        }else if (viewType == 1){
            return new MYVV(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_cartoon_item,parent,false));

        }else {
            return new MYVV(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_system,parent,false));

        }


    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MYVV holder, int position) {
        NotificationModel notificationModel = notificationModels.get(position );

        if (getItemViewType(position) == 0) {

            if (notificationModel.getNotificationUser( ) != null){
                Glide.with(holder.itemView.getContext( )).load(notificationModel.getNotificationUser( ).getProfil()).into(holder.user_image);
                holder.user_sender.setText(notificationModel.getNotificationUser().getUserspecialName());
            }

            holder.messagofnoti.setText(notificationModel.getText());


        }else  if (getItemViewType(position) == 1) {
            if (notificationModel.getNotificationUser( ) != null){
                Glide.with(holder.itemView.getContext( )).load(notificationModel.getNotificationUser( ).getProfil()).into(holder.user_image);
                holder.user_sender.setText(notificationModel.getNotificationUser().getUserspecialName());
            }

            holder.vb.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    Intent sendDataToDetailsActivity = new Intent(holder.itemView.getContext( ), ShowInfoActivity.class);
                    sendDataToDetailsActivity.putExtra("cartoon", notificationModel.getNotificationCartoon());

                    holder.itemView.getContext( ).startActivity(sendDataToDetailsActivity);



                }
            });

        }else {

            holder.notiftime.setText(notificationModel.getText());
        }


    }

    @Override
    public int getItemCount() {
        return  notificationModels.size() ;
    }

    @Override
    public int getItemViewType(int position) {
        if (notificationModels.get(position).getPlace().equals("user")){

            return 0;
        }else if (notificationModels.get(position).getPlace().equals("cartoon")){

            return 1;
        }else {

            return 2;
        }


    }



    public class MYVV extends RecyclerView.ViewHolder {

        TextView notiftime,user_sender,messagofnoti;
        ImageView vb;
        CircleImageView user_image;
        public MYVV(@NonNull View itemView) {
            super(itemView);

            notiftime = itemView.findViewById(R.id.notiftime);
            user_sender = itemView.findViewById(R.id.user_sender);
            messagofnoti = itemView.findViewById(R.id.messagofnoti);
            vb = itemView.findViewById(R.id.vb);
            user_image = itemView.findViewById(R.id.user_image);
        }
    }
}
