package com.anass.jplus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;
import com.chekh.spannedgridlayoutmanager.SpannedGridLayoutManager;

import java.util.List;

public  class SpannedAdapter extends RecyclerView.Adapter<SpannedAdapter.myview> {

    private final boolean[] clickedItems = new boolean[100];

    private Context context;
    List<CartoonModel> cartoonModels;
    int type = 0;
    int limit = 20;

    public SpannedAdapter(Context context, List<CartoonModel> cartoonModels, int type) {
        this.context = context;
        this.cartoonModels = cartoonModels;
        this.type = type;
    }

    @NonNull
    @Override
    public SpannedAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myview(LayoutInflater.from(parent.getContext( )).inflate(R.layout.seire_item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SpannedAdapter.myview holder, int position) {

        CartoonModel w = cartoonModels.get(position);

        int width = clickedItems[position] ? 2 : 1;
        int height = clickedItems[position] ? 2 : 1;

        SpannedGridLayoutManager.SpanSize spanSize = new SpannedGridLayoutManager.SpanSize(width, height);
        holder.itemView.setLayoutParams(new SpannedGridLayoutManager.SpanLayoutParams(spanSize));

        Glide.with(holder.itemView.getContext( )).load(w.getPoster( )).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(cartoonModels.size() > limit){
            return limit;
        }
        else
        {
            return cartoonModels.size();
        }
    }

    public class myview extends RecyclerView.ViewHolder {

        ImageView imageView;

        public myview(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.vb);
        }
    }
}

