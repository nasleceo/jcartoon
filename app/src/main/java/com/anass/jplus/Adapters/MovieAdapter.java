package com.anass.jplus.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anass.jplus.Activities.ShowInfoActivity;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.favouritModel;
import com.anass.jplus.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<CartoonModel> dramaModelList;
    List<favouritModel> favouritModel;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setmodels(List<favouritModel> favouritModel){
        this.favouritModel = favouritModel;
        notifyDataSetChanged();

    }



    @Override
    public int getCount() {
        if (favouritModel != null){
            return favouritModel.size();
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

        CartoonModel cartoonModel = favouritModel.get(i).getCartoon();
        if (cartoonModel != null){
            //Get Cover
            Glide.with(context).load(cartoonModel.getPoster())
                    .into(im);

            name.setText(cartoonModel.getTitle());


            view.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    Intent sendDataToDetailsActivity = new Intent(context, ShowInfoActivity.class);
                    sendDataToDetailsActivity.putExtra("cartoon", cartoonModel);


                    context.startActivity(sendDataToDetailsActivity);
                }
            });
        }




        return view;
    }


}
