package com.anass.jplus.API.Anime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anass.jplus.R;
import com.anasskikanime.models.AnimeModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class AnimeGridAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<AnimeModel> animeModels;


    public AnimeGridAdapter( Context context) {
        this.context = context;
    }


    public void setmodelsAnimes(List<AnimeModel> dramaModelList){
        this.animeModels = dramaModelList;
        notifyDataSetChanged();

    }



    @Override
    public int getCount() {
        if (animeModels != null){
            return animeModels.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        if(layoutInflater == null){
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = layoutInflater.inflate(R.layout.movie_item, null);
        }

        ImageView im = view.findViewById(R.id.vb);
        TextView name = view.findViewById(R.id.anime_name);

        //Get Cover
        Glide.with(context).load(animeModels.get(i).getImg())
                .into(im);
        name.setText(animeModels.get(i).getTitle());



        return view;
    }

}
