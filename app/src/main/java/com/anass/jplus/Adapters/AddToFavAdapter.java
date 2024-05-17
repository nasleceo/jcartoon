package com.anass.jplus.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anass.jplus.Models.addtofavModel;
import com.anass.jplus.R;


import java.util.ArrayList;
import java.util.List;

public class AddToFavAdapter extends RecyclerView.Adapter<AddToFavAdapter.MyViewHolder> {


    private List<addtofavModel> stList;

    public AddToFavAdapter(List<addtofavModel> students) {
        this.stList = students;

    }

    @NonNull
    @Override
    public AddToFavAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_favor_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddToFavAdapter.MyViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.typfav.setText(stList.get(position).getName());

        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        viewHolder.chkSelected.setTag(stList.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                addtofavModel contact = (addtofavModel) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());

            }
        });

    }

    @Override
    public int getItemCount() {
        return stList.size();
    }
    // method to access in activity after updating selection
    public List<addtofavModel> getselected() {
        return stList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView typfav;
        public CheckBox chkSelected;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            typfav = itemView.findViewById(R.id.texts);
            chkSelected = itemView.findViewById(R.id.ss_check);
        }
    }





}
