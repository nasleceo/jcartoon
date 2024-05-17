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

import com.anass.jplus.JPLAYER.Player;
import com.anass.jplus.JPLAYER.WebPlayer;
import com.anass.jplus.Models.ChapterModel;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class EpisodeAdapter  extends RecyclerView.Adapter<EpisodeAdapter.MyViewHolder> {

    Context context;
    List<EpesodModel> epesodModelList;
    List<ChapterModel> chapterModels;
    int type = 0;


    public EpisodeAdapter(Context context, List<EpesodModel> epesodModelList, int type) {
        this.context = context;
        this.epesodModelList = epesodModelList;
        this.type = type;
    }



    @NonNull
    @Override
    public EpisodeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_home_item,parent,false));

        } else if (viewType == 1) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item,parent,false));

        }  else {

            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item,parent,false));

        }
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.MyViewHolder holder, int position) {

        EpesodModel ee = epesodModelList.get(position);

        if (getItemViewType(position) == 0 ){

            holder.numberofepeandnaemtxt.setText("  الحلقة "+ee.getLebel( ));

            if (ee.getVideoCartoon() != null) {
                Glide.with(holder.itemView.getContext( )).load(ee.getVideoCartoon( ).getPoster( )).into(holder.vb);

            }

            holder.itemView.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    if (ee.getSource().equals("Embed")){
                        context.startActivity(new Intent(context, WebPlayer.class)
                                .putExtra("link",ee.getUrl()));
                    }else {

                        Intent intent = new Intent(context, Player.class);

                        intent.putExtra("cartoon", ee.getVideoCartoon());
                        intent.putExtra("episode", ee);
                        intent.putExtra("Content_Type", ee.getVideoCartoon().getCountentType( ));
                        intent.putExtra("Current_List_Position", 0);
                        intent.putExtra("Next_Ep_Avilable", "No");

                        context.startActivity(intent);
                    }




                }
            });


        }



    }

    @Override
    public int getItemCount() {
        return epesodModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView numberofepeandnaemtxt;
        ImageView vb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numberofepeandnaemtxt = itemView.findViewById(R.id.numberofepeandnaemtxt);
            vb = itemView.findViewById(R.id.vb);


        }
    }
}
