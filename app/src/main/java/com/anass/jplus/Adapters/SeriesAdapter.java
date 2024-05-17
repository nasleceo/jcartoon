package com.anass.jplus.Adapters;


import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.anass.jplus.Activities.ShowInfoActivity;
import com.anass.jplus.Config.Utils;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;
import com.github.abdularis.piv.HorizontalScrollParallaxImageView;
import com.github.abdularis.piv.ScrollTransformImageView;
import com.github.abdularis.piv.transformer.HorizontalScaleTransformer;


import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.MyViewHolder> {

    private Activity context;
    List<CartoonModel> cartoonModels;
    int type = 0;
    int AD_TYPE = 7;
    int limit = 20;

    public SeriesAdapter(Activity context, List<CartoonModel> cartoonModels, int type) {
        this.context = context;
        this.cartoonModels = cartoonModels;
        this.type = type;
        notifyDataSetChanged( );
    }
    public void setLimit(int lim) {
        this.limit = lim;

    }


    public void setFilterList(List<CartoonModel> cartoonModels) {
        this.cartoonModels = cartoonModels;
        notifyDataSetChanged( );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext( )).inflate(R.layout.seire_item_search, parent, false));

        } else if (viewType == 1) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext( )).inflate(R.layout.seire_item_search, parent, false));

        } else if (viewType == 2) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext( )).inflate(R.layout.episode_home_item, parent, false));

        }else if (viewType == 3) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext( )).inflate(R.layout.movie_item, parent, false));

        }else if (viewType ==7) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext( )).inflate(R.layout.ads_item, parent, false));

        }else if (viewType == 9) {

            return new MyViewHolder(LayoutInflater.from(parent.getContext( )).inflate(R.layout.movie_item, parent, false));

        }  else {

            return new MyViewHolder(LayoutInflater.from(parent.getContext( )).inflate(R.layout.nadariat_info_item, parent, false));

        }


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartoonModel w = cartoonModels.get(position);

        int ff = position;

        if (getItemViewType(position) == 0) {

            Glide.with(holder.itemView.getContext( )).load(w.getPoster( )).into(holder.vb);

            holder.vb.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {


                    Intent sendDataToDetailsActivity = new Intent(holder.itemView.getContext( ), ShowInfoActivity.class);
                    sendDataToDetailsActivity.putExtra("cartoon", cartoonModels.get(ff));

                    holder.itemView.getContext( ).startActivity(sendDataToDetailsActivity);


                }
            });

        } else if (getItemViewType(position) == 1) {


            Glide.with(holder.itemView.getContext( )).load(w.getPoster( )).into(holder.vb);


            holder.vb.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    Intent sendDataToDetailsActivity = new Intent(holder.itemView.getContext( ), ShowInfoActivity.class);
                    sendDataToDetailsActivity.putExtra("cartoon", cartoonModels.get(ff));

                    holder.itemView.getContext( ).startActivity(sendDataToDetailsActivity);



                }
            });
        } else if (getItemViewType(position) == 3) {


            Glide.with(holder.itemView.getContext( )).load(w.getPoster( )).into(holder.vb);
            holder.anime_name.setText(w.getTitle());

            holder.vb.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {


                    Intent sendDataToDetailsActivity = new Intent(holder.itemView.getContext( ), ShowInfoActivity.class);
                    sendDataToDetailsActivity.putExtra("cartoon", cartoonModels.get(ff));

                    holder.itemView.getContext( ).startActivity(sendDataToDetailsActivity);


                }
            });
        }else if (getItemViewType(position) == 7) {


            Log.d("TAG", "onBindViewHolder:  ADS ");

        }else if (getItemViewType(position) == 9) {


            Glide.with(holder.itemView.getContext( )).load(w.getPoster( )).into(holder.vb);
            holder.anime_name.setText(w.getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent();
                    intent.putExtra("CartoonID", String.valueOf(w.getId()));
                    intent.putExtra("CartoonName", w.getTitle());
                    intent.putExtra("CartoonImg", w.getPoster());
                    context.setResult(RESULT_OK, intent);
                    context.finish();




                }
            });
        }
        else {

            Glide.with(holder.itemView.getContext( )).load(w.getPoster( )).into(holder.vb);

            holder.vb.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {


                    Intent sendDataToDetailsActivity = new Intent(holder.itemView.getContext( ), ShowInfoActivity.class);
                    sendDataToDetailsActivity.putExtra("cartoon", cartoonModels.get(ff));

                    holder.itemView.getContext( ).startActivity(sendDataToDetailsActivity);


                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
/*
        if (type == 3){
            if ((position+1)  % 5 == 0 && (position+1) != 1) {
                return AD_TYPE;
            }
        }*/
        return type;

    }



    @Override
    public int getItemCount() {
        if(cartoonModels.size() > limit){
            return limit ;
        }
        else
        {
            return cartoonModels.size() ;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView vb;
        private TextView numberofepeandnaemtxt,anime_name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vb = itemView.findViewById(R.id.vb);
            numberofepeandnaemtxt = itemView.findViewById(R.id.numberofepeandnaemtxt);
            anime_name = itemView.findViewById(R.id.anime_name);



        }
    }
}
