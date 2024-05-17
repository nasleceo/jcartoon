package com.anass.jplus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Models.ChapterModel;
import com.anass.jplus.Models.EpesodModel;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;

import java.util.List;


public class ChapterAdapter  extends RecyclerView.Adapter<ChapterAdapter.MyViewHolder> {

    Context context;
    List<ChapterModel> chapterModels;
    int type = 0;


    public ChapterAdapter(Context context, List<ChapterModel> chapterModels, int type) {
        this.context = context;
        this.chapterModels = chapterModels;
        this.type = type;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ChapterModel ee = chapterModels.get(position);

        if (getItemViewType(position) == 0 ){

            holder.numberofepeandnaemtxt.setText("  الفصل "+ee.getTitle( ) );

            if (ee.getComic()!= null) {
                Glide.with(holder.itemView.getContext( )).load(ee.getComic( ).getPoster( )).into(holder.vb);

            }


        }



    }

    @Override
    public int getItemCount() {
        return chapterModels.size();
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
