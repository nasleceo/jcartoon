package com.anass.jplus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Activities.MyList.savedpostdownload;
import com.anass.jplus.DB.resume_content.ResumeContentDatabase;
import com.anass.jplus.JPLAYER.Player;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.ContinuePlayingList;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContinuePlayingListAdepter extends RecyclerView.Adapter<ContinuePlayingListAdepter.MyViewHolder> {
    private Context context;
    private List<ContinuePlayingList> mData;

    public ContinuePlayingListAdepter(Context context, List<ContinuePlayingList> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.continue_playing_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int ff) {

        int position = ff;

        holder.setTitle(mData.get(position));
        holder.setYear(mData.get(position));
        holder.setImage(mData.get(position));

        holder.Movie_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(context, Player.class);

                CartoonModel cartoonModel = new CartoonModel();
                cartoonModel.setId(mData.get(position).getContentID());
                cartoonModel.setTitle(mData.get(position).getName());
                cartoonModel.setPoster(mData.get(position).getPoster());
                cartoonModel.setYear(mData.get(position).getYear());
                cartoonModel.setPlace(mData.get(position).getContent_type());

                EpesodModel epesodModel = new EpesodModel();
                epesodModel.setSource(mData.get(position).getSourceType());
                epesodModel.setLebel(mData.get(position).getName());
                epesodModel.setUrl(mData.get(position).getSourceUrl());
                epesodModel.setSkipAvailable(0);
                epesodModel.setIntroEnd("");
                epesodModel.setIntroStart("");

                intent.putExtra("cartoon", cartoonModel);
                intent.putExtra("episode", epesodModel);
                intent.putExtra("Content_Type", mData.get(position).getContent_type());
                intent.putExtra("Current_List_Position", position);
                intent.putExtra("Next_Ep_Avilable", "No");

                    context.startActivity(intent);


            }
        });

        holder.deleteItem.setOnClickListener(view -> {
            ResumeContentDatabase db = ResumeContentDatabase.getDbInstance(context);
            db.resumeContentDao().delete(mData.get(position).getId());
            mData.remove(mData.get(position));
            notifyDataSetChanged();

            savedpostdownload.resumeContents = db.resumeContentDao().getResumeContents();

        });

        holder.contentProgress.setMax((int) mData.get(position).getDuration());
        holder.contentProgress.setProgress((int) mData.get(position).getPosition());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView Year;
        ImageView poster;
        View Premium_Tag;
        CardView Movie_Item;
        ImageView deleteItem;
        LinearProgressIndicator contentProgress;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.Movie_list_Title);
            Year = (TextView) itemView.findViewById(R.id.Movie_list_Year);
            poster = (ImageView) itemView.findViewById(R.id.Movie_Item_thumbnail);
            Premium_Tag = (View) itemView.findViewById(R.id.Premium_Tag);
            Movie_Item = itemView.findViewById(R.id.Movie_Item);
            deleteItem = itemView.findViewById(R.id.deleteItem);
            contentProgress = itemView.findViewById(R.id.contentProgress);
        }

        void setTitle(ContinuePlayingList title_text) {
            Title.setText(title_text.getName());
        }

        void setYear(ContinuePlayingList year_text) {
            Year.setText(""+year_text.getYear());
        }

        void setImage(ContinuePlayingList Thumbnail_Image) {
            Glide.with(context)
                    .load(Thumbnail_Image.getPoster())
                    .into(poster);
        }

    }
}
