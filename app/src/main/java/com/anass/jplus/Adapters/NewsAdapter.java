package com.anass.jplus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Models.NewsModel;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;
import com.hendraanggrian.appcompat.socialview.widget.SocialTextView;

import java.util.List;
import java.util.zip.Inflater;

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.vbii> {

    Context context;
    int type = 0;

    List<NewsModel> newsModelList;

    public NewsAdapter(Context context,   List<NewsModel> newsModelList) {
        this.context = context;
        this.newsModelList = newsModelList;
    }

    @NonNull
    @Override
    public NewsAdapter.vbii onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new vbii(LayoutInflater.from(context).inflate(R.layout.news_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.vbii holder, int position) {

        NewsModel news = newsModelList.get(position);
        Glide.with(holder.itemView.getContext( )).load(news.getImage()).into(holder.newsimage);
        holder.contentnews.setText(news.getText());


    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }

    public class vbii extends RecyclerView.ViewHolder {

        SocialTextView contentnews;
        ImageView newsimage;

        public vbii(@NonNull View itemView) {
            super(itemView);

            contentnews = itemView.findViewById(R.id.contentnews);
            newsimage = itemView.findViewById(R.id.newsimage);

        }
    }
}
