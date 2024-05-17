package com.anass.jplus.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.anass.jplus.API.Anime.DetailPage;
import com.anass.jplus.Activities.ShowInfoActivity;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.R;
import com.anasskikanime.models.AnimeModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.MyView> {

    Activity context;
    List<AnimeModel> animeModels;
    int limit = 0;
    public AnimeAdapter(Activity context) {
        this.context = context;
    }

    public void setmodelsAnimes(List<AnimeModel> dramaModelList){
        this.animeModels = dramaModelList;
        notifyDataSetChanged();
    }

    public void  Getlimit(int limit) {
        this.limit = limit;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seire_item_search,
                parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int i) {
        AnimeModel w = animeModels.get(i);

        int opp = i;

        holder.vb.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: "+animeModels.get(opp).getTitle());
                context.startActivity(new Intent(context, DetailPage.class)
                        .putExtra("img",animeModels.get(opp).getImg())
                        .putExtra("title",animeModels.get(opp).getTitle())
                        .putExtra("type",animeModels.get(opp).getType())
                        .putExtra("link",animeModels.get(opp).getPageLink()));

            }
        });

        holder.anime_name.setText(w.getEpisodestitle());
        holder.fullname.setText(w.getTitle());
            Glide.with(holder.itemView.getContext( )).load(w.getImg( )).into(holder.vb);

    }

    @Override
    public int getItemCount() {
        if (animeModels != null){
            if (limit == 1){
                return animeModels.size();
            }else {
                return Math.min(animeModels.size( ), 20);

            }

        }
        return limit;
    }

    public class MyView extends RecyclerView.ViewHolder {

        private ImageView vb;
        private TextView fullname,anime_name;
        public MyView(@NonNull View view) {
            super(view);

            vb = itemView.findViewById(R.id.vb);
            fullname = itemView.findViewById(R.id.fullname);
            anime_name = itemView.findViewById(R.id.anime_name);
        }
    }
}
